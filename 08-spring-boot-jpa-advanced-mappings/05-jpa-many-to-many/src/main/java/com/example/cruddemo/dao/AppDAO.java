package com.example.cruddemo.dao;

import com.example.cruddemo.entity.Course;
import com.example.cruddemo.entity.Instructor;
import com.example.cruddemo.entity.InstructorDetail;
import com.example.cruddemo.entity.Student;

import java.util.List;

public interface AppDAO {
    void save(Instructor instructor);
    Instructor findInstructorById(int id);
    void deleteInstructorByID(int id);
    InstructorDetail findInstructorDetailById(int id);
    void deleInstructorDetailById(int id);
    List<Course> findCoursesByInstructorId(int id);
    Instructor findInstructorByIdJoinFetch(int id);
    void update(Instructor instructor);
    void update(Course course);
    Course findCourseById(int id);
    void deleteCourseById(int id);
    void saveCourse(Course course);
    Course findCourseAndReviwesByCourseId(int id);
    Course findCourseAndStudentByCourseId(int id);
    Student findStudentAndCoursesByStudentId(int id);
}
