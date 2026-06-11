package com.example.cruddemo;

import com.example.cruddemo.dao.AppDAO;
import com.example.cruddemo.entity.Course;
import com.example.cruddemo.entity.Instructor;
import com.example.cruddemo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLOutput;
import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		/*Instructor instructor = new Instructor("Miserias", "Brown", "test@gmail.com");
		InstructorDetail instructorDetail = new InstructorDetail("Muie", "http>//www.youtube.com");

		Course course1 = new Course("AFSDFSD", instructor);
		Course course2 = new Course("asfafadsfds", instructor);

		instructor.add(course1);
		instructor.add(course2);*/

		Instructor instructor = new Instructor();

		return runner -> {
			// createInstructor(appDAO, instructor, instructorDetail);
			// findInstructor(appDAO, 3);
			// deleteInstructor(appDAO, 2);
			// findInstructorDetail(appDAO, 1);
			// deleteInstructorDetail(appDAO, 4);
			// createInstructorWithCourses(appDAO, instructor, instructorDetail);
			// findInstructorWithCourses(appDAO, 1);
			// findCoursesForInstructor(appDAO, 1);
			// findInstructorWithCoursesJoinFetch(appDAO, 1);
			// updateInstructor(appDAO, 1);
			// updateCourse(appDAO, 5);
			// deleteInstructor(appDAO, 4);
			deleteCourse(appDAO, 6);
		};
	}

	private void deleteCourse(AppDAO appDAO, int id) {
		System.out.println("Deleting Course: " + id);

		appDAO.deleteCourseById(id);

		System.out.println("Done!");
	}

	private void updateCourse(AppDAO appDAO, int id) {
		System.out.println("Updating Course with id: " + id);
		Course course = appDAO.findCourseById(id);
		System.out.println(course);

		// edita o novo campo
		course.setTitle("IA");

		appDAO.update(course);
		System.out.println("Update Course" + course);
	}

	private void updateInstructor(AppDAO appDAO, int id) {
		System.out.println("Updating Instructor id: " + id);
		Instructor instructor = appDAO.findInstructorById(id);
		System.out.println(instructor);

		// edita o novo campo
		instructor.setLastName("Teste");

		appDAO.update(instructor);
		System.out.println("Update Instructor: " + instructor.toString());
	}

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO, int id) {
		System.out.println("Finding Instructor id: " + id);
		Instructor instructor = appDAO.findInstructorByIdJoinFetch(id);

		System.out.println("Instructor: " + instructor.toString());
		System.out.println("Courses associated: " + instructor.getCourses());
		System.out.println("Done!");
	}

	private void findCoursesForInstructor(AppDAO appDAO, int id) {
		System.out.println("Finding Instructor id: " + id);
		Instructor instructor = appDAO.findInstructorById(id);
		System.out.println("Instructor: " + instructor.toString());

		// find courses for instructor
		System.out.println("Finding courses for instructor id: " + id);
		List<Course> courseList = appDAO.findCoursesByInstructorId(id);

		// associate the objects
		instructor.setCourses(courseList);

		System.out.println("Courses: " + instructor.getCourses());
		System.out.println("Done!");
	}

	private void findInstructorWithCourses(AppDAO appDAO, int id) {
		System.out.println("Finding Instructor id: " + id);

		Instructor instructor = appDAO.findInstructorById(id);

		System.out.println("Instructor: " + instructor.toString());
		System.out.println("Associated Courses: " + instructor.getCourses());
		System.out.println("Done!");
	}

	private void createInstructorWithCourses(AppDAO appDAO, Instructor instructor, InstructorDetail instructorDetail) {
		// associate the objects
		instructor.setInstructorDetail(instructorDetail);

		System.out.println("Saving instructor: " + instructor);
		System.out.println("The courses: " + instructor.getCourses());
		appDAO.save(instructor);
		System.out.println("Done!");
	}

	private void deleteInstructorDetail(AppDAO appDAO, int id) {
		System.out.println("Deleting Instructor Detail: " + id);
		appDAO.deleInstructorDetailById(id);
		System.out.println("Done!");
	}

	private void findInstructorDetail(AppDAO appDAO, int id) {
		System.out.println("Finding instructor detail id: " + id);
		InstructorDetail instructorDetail = appDAO.findInstructorDetailById(id);
		System.out.println("Instructor Detail: " + instructorDetail.toString());
		System.out.println("Instructor: " + instructorDetail.getInstructor());
		System.out.println("Done!");
	}

	private void deleteInstructor(AppDAO appDAO, int id) {
		System.out.println("Deleting Instructor: " + id);
		appDAO.deleteInstructorByID(id);
		System.out.println("Done!");
	}

	private void findInstructor(AppDAO appDAO, int id) {
		System.out.println("Finding instructor id: " + id);
		Instructor instructor = appDAO.findInstructorById(id);
		System.out.println("Instructor: " + instructor.toString());
		System.out.println("Instructor Detail: " + instructor.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO, Instructor instructor, InstructorDetail instructorDetail) {
		// create the instructor
		/*Instructor instructor = new Instructor("Rodrigo", "Alexandre", "rodrigo@gmail.com");

		InstructorDetail instructorDetail = new InstructorDetail("Programação", "http>//www.luv2code.com/youtube");*/

		// associate the objects
		instructor.setInstructorDetail(instructorDetail);

		System.out.println("Saving instructor..." + instructor);
		appDAO.save(instructor);

		System.out.println("Done!");
	}

}
