package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
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

    @Autowired
    private StudentAndGradeService studentAndGradeService;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    @BeforeEach
    // Configurando o banco de dados antes de cada teste
    public void setupDatabase() {
        jdbc.execute("insert into student(firstname, lastname, email_address) " +
                "values ('Eric', 'Roby', 'eric.roby@luv2code_school.com')");

        jdbc.execute("insert into math_grade(student_id, grade) values (1, 100.00)");
        jdbc.execute("insert into science_grade(student_id, grade) values (1, 100.00)");
        jdbc.execute("insert into history_grade(student_id, grade) values (1, 100.00)");
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
    // Teste com para verificar se o método deleteStudent está funcionando corretamente
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

    @Sql("/insertData.sql")
    @Test
    // Teste para verificar se o método getGradebook está funcionando corretamente
    public void getGradebookService() {
        // Recuperando todos os estudantes do banco de dados usando o serviço
        Iterable<CollegeStudent> collegeStudentIterable = studentService.getGradebook();
        // Convertendo o Iterable para uma lista para facilitar a verificação
        List<CollegeStudent> collegeStudents = new ArrayList<>();

        // Adicionando todos os estudantes do Iterable para a lista
        for (CollegeStudent student : collegeStudentIterable) {
            collegeStudents.add(student);
        }

        // Verificando se há apenas um estudante no banco de dados
        assertEquals(6, collegeStudents.size(), "Find one student");
    }

    @Test
    // Teste para criar uma nota e verificar se ela foi salva corretamente no banco de dados
    public void createGradeService() {
        // criar a nota
        assertTrue(studentAndGradeService.createGrade(80.50, 1, "math"));
        assertTrue(studentAndGradeService.createGrade(80.50, 1, "science"));
        assertTrue(studentAndGradeService.createGrade(80.50, 1, "history"));

        // pegar as notas do estudante
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(1);

        // verificar se há notas
        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2, "Should have at least one math grade");
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2, "Should have at least one science grade");
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2, "Should have at least one history grade");
    }

    @Test
    public void createGradeServiceReturnFalse() {
        assertFalse(studentAndGradeService.createGrade(105, 1, "math"));
        assertFalse(studentAndGradeService.createGrade(-5, 1, "science"));
        assertFalse(studentAndGradeService.createGrade(80.50, 9, "history"));
        assertFalse(studentAndGradeService.createGrade(80.50, 1, "literature"));
    }

    @Test
    public void deleteGradeService() {
        // Verificando se o estudante com id 1 está presente no banco de dados antes de deletar as notas
        assertEquals(1, studentService.deleteGrade(1, "math"), "Returns student id after delete");
        assertEquals(1, studentService.deleteGrade(1, "science"), "Returns student id after delete");
        assertEquals(1, studentService.deleteGrade(1, "history"), "Returns student id after delete");
    }

    @AfterEach
    // Limpando o banco de dados após cada teste
    public void setupAfterTransaction() {
        // Limpando as tabelas do banco de dados após cada teste
        jdbc.execute("delete from student");
        jdbc.execute("delete from math_grade");
        jdbc.execute("delete from science_grade");
        jdbc.execute("delete from history_grade");

        // Resetando os IDs das tabelas de notas para 1 após cada teste
        jdbc.execute("alter table student alter column id restart with 1");
        jdbc.execute("alter table math_grade alter column id restart with 1");
        jdbc.execute("alter table science_grade alter column id restart with 1");
        jdbc.execute("alter table history_grade alter column id restart with 1");
    }
}
