package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradebookController {

    @Autowired
    private Gradebook gradebook;

    @Autowired
    private StudentAndGradeService studentAndGradeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStudents(Model m) {
        Iterable<CollegeStudent> students = studentAndGradeService.getGradebook();
        m.addAttribute("students", students);
        return "index";
    }

    @PostMapping(value = "/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {
        studentAndGradeService.createStudent(
                student.getFirstname(),
                student.getLastname(),
                student.getEmailAddress()
        );

        Iterable<CollegeStudent> students = studentAndGradeService.getGradebook();
        m.addAttribute("students", students);

        return "index";
    }

    // Endpoint para exibir informações detalhadas de um estudante específico
    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        // Verifica se o estudante existe antes de tentar acessar suas informações
        if (!studentAndGradeService.checkIfStudentIsNull(id)) {
            return "error";
        }

        // Recupera as informações do estudante e adiciona ao modelo para exibição na view
        GradebookCollegeStudent studentEntity = studentAndGradeService.studentInformation(id);
        m.addAttribute("student", studentEntity);
        // Calcula e adiciona a média das notas de matemática ao modelo, se houver notas disponíveis
        if (studentEntity.getStudentGrades().getMathGradeResults().size() > 0) {
            // Calcula a média das notas de matemática usando o método findGradePointAverage e adiciona ao modelo
            m.addAttribute("mathAverage", studentEntity.getStudentGrades().findGradePointAverage(studentEntity.getStudentGrades().getMathGradeResults()));
        } else {
             // Se não houver notas de matemática, adiciona "N/A" ao modelo para indicar que a média não está disponível
            m.addAttribute("mathAverage", "N/A");
        }

        // Calcula e adiciona a média das notas de história ao modelo, se houver notas disponíveis
        if (studentEntity.getStudentGrades().getScienceGradeResults().size() > 0) {
            m.addAttribute("scienceAverage", studentEntity.getStudentGrades().findGradePointAverage(studentEntity.getStudentGrades().getScienceGradeResults()));
        } else {
            // Se não houver notas de ciência, adiciona "N/A" ao modelo para indicar que a média não está disponível
            m.addAttribute("scienceAverage", "N/A");
        }

        // Calcula e adiciona a média das notas de história ao modelo, se houver notas disponíveis
        if (studentEntity.getStudentGrades().getHistoryGradeResults().size() > 0) {
            m.addAttribute("historyAverage", studentEntity.getStudentGrades().findGradePointAverage(studentEntity.getStudentGrades().getHistoryGradeResults()));
        } else {
            // Se não houver notas de história, adiciona "N/A" ao modelo para indicar que a média não está disponível
            m.addAttribute("historyAverage", "N/A");
        }

        return "studentInformation";
    }

    @GetMapping("/delete/student/{id}")
    public String deleteStudent(@PathVariable int id) {

        if (!studentAndGradeService.checkIfStudentIsNull(id)) {
            return "error";
        }

        studentAndGradeService.deleteStudent(id);
        return "redirect:/";
    }

    @PostMapping(value = "/grades")
    public String createGrade(@RequestParam("grade") double grade,
                              @RequestParam("gradeType") String gradeType,
                              @RequestParam("studentId") int studentId,
                              Model m) {

        if (!studentAndGradeService.checkIfStudentIsNull(studentId)) {
            return "error";
        }

        boolean success = studentAndGradeService.createGrade(grade, studentId, gradeType);

        if (!success) {
            return "error";
        }

        GradebookCollegeStudent studentEntity = studentAndGradeService.studentInformation(studentId);

        m.addAttribute("student", studentEntity);
        // Calcula e adiciona a média das notas de matemática ao modelo, se houver notas disponíveis
        if (studentEntity.getStudentGrades().getMathGradeResults().size() > 0) {
            // Calcula a média das notas de matemática usando o método findGradePointAverage e adiciona ao modelo
            m.addAttribute("mathAverage", studentEntity.getStudentGrades().findGradePointAverage(studentEntity.getStudentGrades().getMathGradeResults()));
        } else {
            // Se não houver notas de matemática, adiciona "N/A" ao modelo para indicar que a média não está disponível
            m.addAttribute("mathAverage", "N/A");
        }

        // Calcula e adiciona a média das notas de história ao modelo, se houver notas disponíveis
        if (studentEntity.getStudentGrades().getScienceGradeResults().size() > 0) {
            m.addAttribute("scienceAverage", studentEntity.getStudentGrades().findGradePointAverage(studentEntity.getStudentGrades().getScienceGradeResults()));
        } else {
            // Se não houver notas de ciência, adiciona "N/A" ao modelo para indicar que a média não está disponível
            m.addAttribute("scienceAverage", "N/A");
        }

        // Calcula e adiciona a média das notas de história ao modelo, se houver notas disponíveis
        if (studentEntity.getStudentGrades().getHistoryGradeResults().size() > 0) {
            m.addAttribute("historyAverage", studentEntity.getStudentGrades().findGradePointAverage(studentEntity.getStudentGrades().getHistoryGradeResults()));
        } else {
            // Se não houver notas de história, adiciona "N/A" ao modelo para indicar que a média não está disponível
            m.addAttribute("historyAverage", "N/A");
        }

        return "studentInformation";

    }

}
