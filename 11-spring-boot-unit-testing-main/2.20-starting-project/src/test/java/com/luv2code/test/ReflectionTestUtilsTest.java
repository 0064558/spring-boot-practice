package com.luv2code.test;

import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = com.luv2code.component.MvcTestingExampleApplication.class)
public class ReflectionTestUtilsTest {
    // injeta o contexto da aplicação no teste, permitindo que você acesse os beans do Spring e suas dependências
    @Autowired
    ApplicationContext context;

    // injeta o bean CollegeStudent no teste, permitindo que você acesse e modifique os atributos do estudante durante os testes
    @Autowired
    CollegeStudent student;

    // injeta o bean StudentGrades no teste, permitindo que você acesse e modifique os resultados de notas do estudante durante os testes
    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void studentBeforeEach() {
        // definindo os valores dos atributos do objeto student usando os métodos setters
        student.setFirstname("Eric");
        student.setLastname("Roby");
        student.setEmailAddress("eric.roby@luv2code_school.com");
        student.setStudentGrades(studentGrades);

        // usando ReflectionTestUtils para definir o valor do campo privado "id" do objeto student
        ReflectionTestUtils.setField(student, "id", 1);
        // usando ReflectionTestUtils para definir o valor do campo privado "studentGrades" do objeto student com uma nova instância de StudentGrades contendo uma lista de notas
        ReflectionTestUtils.setField(student, "studentGrades", new StudentGrades(new ArrayList<>(Arrays.asList(
                100.0, 85.0, 76.50, 91.75))));
    }

    @Test
    public void getPrivateField() {
        // usando ReflectionTestUtils para obter o valor do campo privado "id" do objeto student e verificando se é igual a 1
        assertEquals(1, ReflectionTestUtils.getField(student, "id"));
    }

    @Test
    public void invokePrivateMethod() {
        // usando ReflectionTestUtils para invocar o método privado "getFirstNameAndId" do objeto student e verificando se o resultado é igual a "Eric 1"
        assertEquals("Eric 1", ReflectionTestUtils.invokeMethod(student, "getFirstNameAndId"));
    }
}
