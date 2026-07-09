package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    @Autowired
    private StudentGrades studentGrades;

    // Injetando o StudentDao no construtor da classe StudentAndGradeService
    @Autowired
    public StudentAndGradeService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }


    public void createStudent(String firstName, String lastName, String emailAddress) {
        CollegeStudent student = new CollegeStudent(firstName, lastName, emailAddress);
        student.setId(0);
        studentDao.save(student);
    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);

        if (student.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteStudent(int id) {
        if (checkIfStudentIsNull(id)) {
            studentDao.deleteById(id);
            mathGradesDao.deleteByStudentId(id);
            scienceGradesDao.deleteByStudentId(id);
            historyGradesDao.deleteByStudentId(id);
        }
    }

    public Iterable<CollegeStudent> getGradebook() {
        return studentDao.findAll();
    }

    // Método para criar uma nota para um estudante específico
    public boolean createGrade(double grade, int studentId, String gradeType) {
        // Verifica se o estudante existe no banco de dados antes de criar a nota
        if (!checkIfStudentIsNull(studentId)) {
            return false;
        }

        // Verifica se a nota está dentro do intervalo válido (0 a 100)
        if (grade >= 0 && grade <= 100) {
            // Verifica o tipo de nota (matemática ou ciência) e cria a nota correspondente
            if (gradeType.equals("math")) {
                MathGrade mathGrade = new MathGrade();
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradesDao.save(mathGrade);
                return true;
            }
            if (gradeType.equals("science")) {
                ScienceGrade scienceGrade = new ScienceGrade();
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradesDao.save(scienceGrade);
                return true;
            }
            if (gradeType.equals("history")) {
                HistoryGrade historyGrade = new HistoryGrade();
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(studentId);
                historyGradesDao.save(historyGrade);
                return true;
            }
        }
        return false;
    }

    // Método para deletar uma nota de um estudante específico
    public int deleteGrade(int id, String gradeType) {
        int studentId = 0;

        // Verifica o tipo de nota (matemática, ciência ou história) e deleta a nota correspondente
        if (gradeType.equals("math")) {
            // Busca a nota de matemática pelo ID e obtém o ID do estudante associado
            Optional<MathGrade> grade = mathGradesDao.findById(id);
            // Se a nota não existir, retorna o ID do estudante como 0
            if (!grade.isPresent()) {
                return studentId;
            }
            // Se a nota existir, obtém o ID do estudante associado e deleta a nota
            studentId = grade.get().getStudentId();
            mathGradesDao.deleteById(id);
        }
        if (gradeType.equals("science")) {
            // Busca a nota de ciencia pelo ID e obtém o ID do estudante associado
            Optional<ScienceGrade> grade = scienceGradesDao.findById(id);
            // Se a nota não existir, retorna o ID do estudante como 0
            if (!grade.isPresent()) {
                return studentId;
            }
            // Se a nota existir, obtém o ID do estudante associado e deleta a nota
            studentId = grade.get().getStudentId();
            scienceGradesDao.deleteById(id);
        }
        if (gradeType.equals("history")) {
            // Busca a nota de ciencia pelo ID e obtém o ID do estudante associado
            Optional<HistoryGrade> grade = historyGradesDao.findById(id);
            // Se a nota não existir, retorna o ID do estudante como 0
            if (!grade.isPresent()) {
                return studentId;
            }
            // Se a nota existir, obtém o ID do estudante associado e deleta a nota
            studentId = grade.get().getStudentId();
            historyGradesDao.deleteById(id);
        }

        return studentId;
    }

    // Método para obter as informações de um estudante específico, incluindo suas notas
    public GradebookCollegeStudent studentInformation(int id) {

        if (!checkIfStudentIsNull(id)) {
            return null;
        }

        // Busca o estudante pelo ID
        Optional<CollegeStudent> student = studentDao.findById(id);

        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(id);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(id);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(id);

        // Converte os iteráveis de notas em listas para facilitar o acesso e manipulação
        List<Grade> mathGradesList = new ArrayList<>();
        mathGrades.forEach(mathGradesList::add);

        List<Grade> scienceGradesList = new ArrayList<>();
        scienceGrades.forEach(scienceGradesList::add);

        List<Grade> historyGradesList = new ArrayList<>();
        historyGrades.forEach(historyGradesList::add);

        studentGrades.setMathGradeResults(mathGradesList);
        studentGrades.setScienceGradeResults(scienceGradesList);
        studentGrades.setHistoryGradeResults(historyGradesList);

        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(student.get().getId(), student.get().getFirstname(), student.get().getLastname(), student.get().getEmailAddress(), studentGrades);

        return gradebookCollegeStudent;
    }

    public void configureStudentInformationModel(int id, Model m) {
        GradebookCollegeStudent studentEntity = studentInformation(id);

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
    }
}
