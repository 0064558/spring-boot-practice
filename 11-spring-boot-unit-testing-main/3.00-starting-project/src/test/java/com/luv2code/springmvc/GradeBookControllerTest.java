package com.luv2code.springmvc;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring6.expression.Mvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradeBookControllerTest {

    // MockHttpServletRequest é usado para simular requisições HTTP durante os testes
    private static MockHttpServletRequest request;

    // JdbcTemplate é usado para executar comandos SQL diretamente no banco de dados durante os testes
    @Autowired
    private JdbcTemplate jdbc;

    // MockMvc é usado para simular requisições HTTP para os endpoints do controlador durante os testes
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private StudentAndGradeService studentAndGradeService;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    @Autowired
    private StudentAndGradeService studentService;

    // Mock do serviço StudentAndGradeServiceTest para ser usado nos testes do controlador
    @Mock
    private StudentAndGradeService studentCreateServiceMock;

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHystoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Eric");
        request.setParameter("lastname", "Roby");
        request.setParameter("email_address", "eric.roby@luv2code_school.com");
    }

    @BeforeEach
    // Configurando o banco de dados antes de cada teste
    public void beforeEach() {
        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddScienceGrade);
        jdbc.execute(sqlAddHystoryGrade);
    }

    @Test
    public void getStudentHttpRequest() throws Exception {
        CollegeStudent student1 = new CollegeStudent("Eric", "Roby", "eric.roby@luv2code_school.com");
        CollegeStudent student2 = new CollegeStudent("Chad", "Darby", "chad.darby@luv2code_school.com");

        List<CollegeStudent> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        // Mockando o método getGradebook() do serviço StudentAndGradeService para retornar a lista de estudantes criada acima
        when(studentCreateServiceMock.getGradebook()).thenReturn(students);

        // Verificando se o método getGradebook() do serviço retorna a lista de estudantes esperada
        assertIterableEquals(students, studentCreateServiceMock.getGradebook());

        // Simulando uma requisição HTTP GET para o endpoint "/" do controlador e verificando se o status da resposta é 200 OK
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");
    }

    @Test
    // Teste para criar um estudante via requisição HTTP POST e verificar se ele foi salvo corretamente no banco de dados
    public void createStudentHttpRequest() throws Exception {
        CollegeStudent student1 = new CollegeStudent("Eric", "Roby", "eric.roby@luv2code_school.com");

        List<CollegeStudent> students = new ArrayList<>();
        students.add(student1);

        when(studentCreateServiceMock.getGradebook()).thenReturn(students);

        assertIterableEquals(students, studentCreateServiceMock.getGradebook());

        MvcResult mvcResult = this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstname", "Eric")
                        .param("lastname", "Roby")
                        .param("email_address", "eric.roby@luv2code_school.com"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");

        CollegeStudent verifyStudent = studentDao.findByEmailAddress("eric.roby@luv2code_school.com");

        assertNotNull(verifyStudent, "Student should not be null");
    }

    @Test
    // Teste para deletar um estudante via requisição HTTP GET e verificar se ele foi removido corretamente do banco de dados
    public void deleteStudentHttpRequest() throws Exception {
        // Verificando se o estudante com id 1 está presente no banco de dados antes de deletar
        assertTrue(studentDao.findById(1).isPresent());

        // Simulando uma requisição HTTP GET para o endpoint "/delete/student/{id}" do controlador e verificando se o status da resposta é 200 OK
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/delete/student/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        // Verificando se a view retornada é "index" e se o estudante com id 1 não está mais presente no banco de dados após a deleção
        ModelAndView mav = mvcResult.getModelAndView();

        // Verificando se a view retornada é "index"
        ModelAndViewAssert.assertViewName(mav, "index");

        // Verificando se o estudante com id 1 não está mais presente no banco de dados após a deleção
        assertFalse(studentDao.findById(1).isPresent());
    }

    @Test
    public void deleteStudentHttpRequestErrorPage() throws Exception {
        // Verificando se o estudante com id 0 não está presente no banco de dados antes de tentar deletar
        assertFalse(studentDao.findById(0).isPresent());

        // Simulando uma requisição HTTP GET para o endpoint "/delete/student/{id}" do controlador e verificando se o status da resposta é 200 OK
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/delete/student/{id}", 0))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");
    }

    @Test
    public void studentInformationHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "studentInformation");
    }

    @Test
    public void studentInformationHttpStudentDoesNotExistRequest() throws Exception {
        assertFalse(studentDao.findById(0).isPresent());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 0))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");
    }

    @Test
    public void createValidGradeHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());

        GradebookCollegeStudent student = studentService.studentInformation(1);

        assertEquals(1, student.getStudentGrades().getMathGradeResults().size());

        MvcResult mvcResult = this.mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.50")
                .param("gradeType", "math")
                .param("studentId", "1")).andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "studentInformation");

        student = studentService.studentInformation(1);

        assertEquals(2, student.getStudentGrades().getMathGradeResults().size());
    }

    @Test
    public void createAValidGradeHttpRequestStudentDoesNotExistEmptyResponse() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.50")
                .param("gradeType", "history")
                .param("studentId", "0")).andExpect(status().isOk()).andReturn();

        ModelAndView mav = result.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");
    }

    @Test
    public void createANonValidGradeHttpRequestGradeTypeDoesNotExistEmptyResponse() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.50")
                .param("gradeType", "literature")
                .param("studentId", "1")).andExpect(status().isOk()).andReturn();

        ModelAndView mav = result.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");
    }

    @AfterEach
    // Limpando o banco de dados após cada teste
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
        jdbc.execute(sqlDeleteStudent);
    }
}
