package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @BeforeEach
    public void setupDatabase() {
        jdbc.execute("insert into student(firstname, lastname, email_address) " +
                "values ('Eric', 'Roby', 'eric.roby@luv2code_school.com')");
    }

    @Test
    // Teste para criar um estudante e verificar se ele foi salvo corretamente no banco de dados
    public void createStudentServiceTest() {
        // Criando um estudante usando o serviço
        studentService.createStudent("Chad", "Darby", "chad.darby@luv2code_school.com");

        // Recuperando o estudante do banco de dados usando o StudentDao
        CollegeStudent students = studentDao.findByEmailAddress("chad.darby@luv2code_school.com");

        // Verificando se o estudante foi salvo corretamente no banco de dados
        assertEquals("chad.darby@luv2code_school.com", students.getEmailAddress(), "find by email");
    }

    @Test
    // Teste para verificar se o método checkIfStudentIsNull está funcionando corretamente
    public void isStudentNullCheck() {
        assertTrue(studentService.checkIfStudentIsNull(1));

        assertFalse((studentService.checkIfStudentIsNull(0)));
    }
}
