package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {
    // injeta o contexto da aplicação no teste, permitindo que você acesse os beans do Spring e suas dependências
    @Autowired
    ApplicationContext context;

    // injeta o bean CollegeStudent no teste, permitindo que você acesse e modifique os atributos do estudante durante os testes
    @Autowired
    CollegeStudent student;

    // injeta o bean StudentGrades no teste, permitindo que você acesse e modifique os resultados de notas do estudante durante os testes
    @Autowired
    StudentGrades studentGrades;

    /* cria um mock do ApplicationDao, permitindo que você simule o comportamento do DAO sem depender de uma implementação real
    @Mock*/
    @MockitoBean // cria um mock do ApplicationDao, e permite usar o @Autowired para injetar o mock no ApplicationService
    private ApplicationDao applicationDao;

    // @InjectMocks cria uma instância de ApplicationService e injeta o mock applicationDao nele
    // isso permite que você teste o ApplicationService sem depender de uma implementação real do ApplicationDao
    // apenas injeta dependencias anotadas com @Mock ou @Spy no objeto anotado com @InjectMocks
    //@InjectMocks
    @Autowired // mockitoBean já injeta o mock no ApplicationService, então não é necessário usar @InjectMocks
    private ApplicationService applicationService;

    // o método beforeEach() é executado antes de cada teste, garantindo que o estado do teste seja limpo e consistente
    @BeforeEach
    public void beforeEach() {
        student.setFirstname("Eric");
        student.setLastname("Roby");
        student.setEmailAddress("eric.roby@luv2code_school.com");
        student.setStudentGrades(studentGrades);
    }

    @DisplayName("when & verify")
    @Test
    // o método assertEqualsTestAddGradeResultsForSingleClass() é um teste unitário que verifica se o método addGradeResultsForSingleClass() da classe ApplicationService está funcionando corretamente
    public void assertEqualsTestAddGradeResultsForSingleClass() {
        // set up -> execute -> assert -> verify

        // set up
        // quando o método addGradeResultsForSingleClass() do mock applicationDao for chamado com os resultados de notas de matemática do estudante, então ele retornará 100.0
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.0);

        // execute and assert
        // chama o método addGradeResultsForSingleClass() do applicationService com os resultados de notas de matemática do estudante, o esperado é que ele retorne 100.0, pois o mock applicationDao foi configurado para retornar esse valor
        assertEquals(100.0, applicationService.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));

        // verify
        // verifica se o método addGradeResultsForSingleClass() do mock applicationDao foi chamado
        verify(applicationDao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @DisplayName("Find GPA")
    @Test
    // verifica se o método findGradePointAverage() da classe ApplicationService está funcionando corretamente
    public void assertEqualsTestFindGpa() {
        // set up -> execute -> assert -> verify

        // set up
        // configura o mock applicationDao para retornar 88.31 quando o método findGradePointAverage() for chamado
        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(88.31);

        // execute and assert
        // chama o método findGradePointAverage() do applicationService com os resultados de notas de Mat do estudante, o esperado é que ele retorne 88.31, pois o mock applicationDao foi configurado para retornar esse valor
        assertEquals(88.31, applicationService.findGradePointAverage(student.getStudentGrades().getMathGradeResults()));

        // verify
        // verifica se o método findGradePointAverage() do mock applicationDao foi chamado
        verify(applicationDao).findGradePointAverage(studentGrades.getMathGradeResults());
    }

    @DisplayName("Not Null")
    @Test
    // verifica se o método checkNull() da classe ApplicationService está funcionando corretamente
    public void testAssertNotNull() {
        // set up -> execute -> assert -> verify

        // set up
        // configura o mock applicationDao para retornar true quando o método checkNull() for chamado com os resultados de notas de matemática do estudante
        when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);

        // execute and assert
        // chama o método checkNull() do applicationService com os resultados de notas de matemática do estudante, o esperado é que ele retorne true, pois o mock applicationDao foi configurado para retornar esse valor
        // verifica se o objeto retornado pelo método checkNull() do applicationService não é nulo
        assertNotNull(applicationService.checkNull(student.getStudentGrades().getMathGradeResults()), "Object should be not null");

        // verify
        // verifica se o método checkNull() do mock applicationDao foi chamado
        verify(applicationDao).checkNull(studentGrades.getMathGradeResults());
    }
}
