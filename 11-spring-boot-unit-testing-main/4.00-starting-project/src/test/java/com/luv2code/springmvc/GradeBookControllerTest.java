package com.luv2code.springmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class GradeBookControllerTest {

    // MockHttpServletRequest para simular requisições HTTP durante os testes
    private static MockHttpServletRequest request;

    @PersistenceContext
    // Injetando o EntityManager para gerenciar entidades JPA
    private EntityManager entityManager;

    // Mock do serviço StudentAndGradeService para simular o comportamento do serviço durante os testes
    @Mock
    private StudentAndGradeService studentAndGradeService;

    // Injetando o MockMvc para simular requisições HTTP e testar os endpoints do controlador
    @Autowired
    private MockMvc mockMvc;

    // Injetando o ObjectMapper para converter objetos Java em JSON e vice-versa durante os testes
    @Autowired
    ObjectMapper objectMapper;

    // Injetando o CollegeStudent para criar instâncias de estudantes durante os testes
    @Autowired
    private CollegeStudent student;

    // Injetando o JdbcTemplate para executar consultas SQL diretamente no banco de dados durante os testes
    @Autowired
    private JdbcTemplate jdbc;

    // Injetando os DAOs para acessar os dados de estudantes e notas durante os testes
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradeDao;

    @Autowired
    private ScienceGradesDao scienceGradeDao;

    @Autowired
    private HistoryGradesDao historyGradeDao;

    @Autowired
    private StudentAndGradeService studentService;

    // Definindo o tipo de mídia para JSON UTF-8, utilizado nas requisições HTTP durante os testes
    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    // Injetando os scripts SQL para criar e deletar dados de teste no banco de dados

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    // Configuração inicial do MockHttpServletRequest antes de todos os testes
    @BeforeAll
    public static void setup() {

        request = new MockHttpServletRequest();

        request.setParameter("firstname", "Chad");
        request.setParameter("lastname", "Darby");
        request.setParameter("emailAddress", "chad.darby@luv2code_school.com");
    }

    // Configuração do banco de dados antes de cada teste, inserindo dados necessários para os testes
    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddScienceGrade);
        jdbc.execute(sqlAddHistoryGrade);
    }

    // Teste para Get Students
    @Test
    public void getStudentsHttpRequest() throws Exception {
        // Simulando uma requisição GET para o endpoint "/" e verificando se o status da resposta é OK (200) e se o tipo de conteúdo é JSON UTF-8 e se o tamanho do array JSON retornado é 1
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(APPLICATION_JSON_UTF8)))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    // Teste para criar um estudante via requisição HTTP
    @Test
    public void createStudentHttpRequest() throws Exception {
        // Criando um novo objeto CollegeStudent e definindo seus atributos
        student.setFirstname("Rodrigo");
        student.setLastname("Alexandre Alves");
        student.setEmailAddress("rodrigo.alexandre@luv2code_school.com");

        // Simulando uma requisição POST para o endpoint "/" com o objeto CollegeStudent convertido em JSON e verificando se o status da resposta é OK (200) e se o tipo de conteúdo é JSON UTF-8 e se o tamanho do array JSON retornado é 2
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        // Convertendo o objeto CollegeStudent em JSON usando o ObjectMapper e adicionando ao corpo da requisição
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        // Verificando se o estudante foi realmente inserido no banco de dados, buscando pelo endereço de e-mail e garantindo que o objeto retornado não seja nulo
        CollegeStudent verifyStudent = studentDao.findByEmailAddress("rodrigo.alexandre@luv2code_school.com");
        // Asserting that the student is not null, indicating that it was successfully added to the database
        assertNotNull(verifyStudent, "Student should be in the database");
    }

    @Test
    public void deleteStudentHttpRequest() throws Exception {
        int id = 1;

        // Verificando se o estudante com o ID especificado existe no banco de dados antes de realizar a exclusão
        assertTrue(studentDao.findById(id).isPresent());

        // Simulando uma requisição DELETE para o endpoint "/student/{id}" e verificando se o status da resposta é OK (200), se o tipo de conteúdo é JSON UTF-8 e se o tamanho do array JSON retornado é 0
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(APPLICATION_JSON_UTF8)))
                .andExpect(jsonPath("$", hasSize(0)));

        // Verificando se o estudante com o ID especificado não existe mais no banco de dados após a exclusão
        assertFalse(studentDao.findById(id).isPresent());
    }

    // Limpeza do banco de dados após cada teste, removendo os dados inseridos durante a configuração
    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
    }
}
