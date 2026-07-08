package com.luv2code.springmvc.repository;

import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryGradesDao extends JpaRepository<HistoryGrade, Integer> {
    public Iterable<HistoryGrade> findGradeByStudentId(int studentId);
}
