package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    StudentDao studentDao;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;

    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;

    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

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
                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradesDao.save(mathGrade);
                return true;
            }
            if (gradeType.equals("science")) {
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradesDao.save(scienceGrade);
                return true;
            }
            if (gradeType.equals("history")) {
                historyGrade.setId(0);
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

        return studentId;
    }
}
