package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

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
    // Configurando o banco de dados antes de cada teste
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

    @Test
    // Teste para verificar se o método deleteStudent está funcionando corretamente
    public void deleteStudentService() {
        // Verificando se o estudante com id 1 está presente no banco de dados antes de deletar
        Optional<CollegeStudent> deletedStudent = studentDao.findById(1);
        assertTrue(deletedStudent.isPresent(), "Return true if student is present");

        // Deletando o estudante com id 1 usando o serviço
        studentService.deleteStudent(1);

        // Verificando se o estudante com id 1 não está mais presente no banco de dados após a deleção
        deletedStudent = studentDao.findById(1);

        // Verificando se o estudante foi deletado corretamente do banco de dados
        assertFalse(deletedStudent.isPresent(), "Return false if student is not present");
    }

    @AfterEach
    // Limpando o banco de dados após cada teste
    public void setupAfterTransaction() {
        jdbc.execute("delete from student");
        jdbc.execute("alter table student alter column id restart with 1");
    }
}
