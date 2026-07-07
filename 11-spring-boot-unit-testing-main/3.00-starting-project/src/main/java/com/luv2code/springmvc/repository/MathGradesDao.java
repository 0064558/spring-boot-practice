package com.luv2code.springmvc.repository;

import com.luv2code.springmvc.models.MathGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MathGradesDao extends JpaRepository<MathGrade, Integer> {
    public Iterable<MathGrade> findGradeByStudentId(int studentId);
}
