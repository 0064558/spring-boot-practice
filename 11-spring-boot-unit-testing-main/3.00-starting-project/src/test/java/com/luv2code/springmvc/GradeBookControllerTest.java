package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
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

    // Mock do serviço StudentAndGradeServiceTest para ser usado nos testes do controlador
    @Mock
    private StudentAndGradeService studentAndGradeServiceMock;

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
        jdbc.execute("insert into student(firstname, lastname, email_address) " +
                "values ('Eric', 'Roby', 'eric.roby@luv2code_school.com')");
    }

    @Test
    public void getStudentHttpRequest() throws Exception {
        CollegeStudent student1 = new CollegeStudent("Eric", "Roby", "eric.roby@luv2code_school.com");
        CollegeStudent student2 = new CollegeStudent("Chad", "Darby", "chad.darby@luv2code_school.com");

        List<CollegeStudent> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        // Mockando o método getGradebook() do serviço StudentAndGradeService para retornar a lista de estudantes criada acima
        when(studentAndGradeServiceMock.getGradebook()).thenReturn(students);

        // Verificando se o método getGradebook() do serviço retorna a lista de estudantes esperada
        assertIterableEquals(students, studentAndGradeServiceMock.getGradebook());

        // Simulando uma requisição HTTP GET para o endpoint "/" do controlador e verificando se o status da resposta é 200 OK
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");
    }

    @Test
    public void createStudentHttpRequest() throws Exception {
        // Simulando uma requisição HTTP POST para o endpoint "/studentInformation" do controlador com os parâmetros do estudante
        MvcResult mvcResult = this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstName", request.getParameterValues("firstName"))
                        .param("lastName", request.getParameterValues("lastName"))
                        .param("email_address", request.getParameterValues("emailAddress")))
                        .andExpect(status().isOk())
                        .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "studentInformation");
    }

    @AfterEach
    // Limpando o banco de dados após cada teste
    public void setupAfterTransaction() {
        jdbc.execute("delete from student");
        jdbc.execute("alter table student alter column id restart with 1");
    }
}
