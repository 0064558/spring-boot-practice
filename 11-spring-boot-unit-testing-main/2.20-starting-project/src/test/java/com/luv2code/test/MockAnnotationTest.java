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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {
    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @Mock
    private ApplicationDao applicationDao;

    // injeta dependencias de mock no applicationService
    // @InjectMocks cria uma instância de ApplicationService e injeta o mock applicationDao nele
    // isso permite que você teste o ApplicationService sem depender de uma implementação real do ApplicationDao
    // apenas injeta dependencias anotadas com @Mock ou @Spy no objeto anotado com @InjectMocks
    @InjectMocks
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
}
