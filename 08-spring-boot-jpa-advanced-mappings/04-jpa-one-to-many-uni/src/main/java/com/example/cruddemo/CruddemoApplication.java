package com.example.cruddemo;

import com.example.cruddemo.dao.AppDAO;
import com.example.cruddemo.entity.Course;
import com.example.cruddemo.entity.Instructor;
import com.example.cruddemo.entity.InstructorDetail;
import com.example.cruddemo.entity.Review;
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
			// createCourseAndReviews(appDAO);
			retrieveCourseAndReviews(appDAO, 1);
		};
	}

	private void retrieveCourseAndReviews(AppDAO appDAO, int id) {
		// get the course and reviews
		Course course = appDAO.findCourseAndReviwesByCourseId(id);

		// print the course
		System.out.println(course);

		// print the reviews
		System.out.println(course.getReviews());
	}

	private void createCourseAndReviews(AppDAO appDAO) {
		// create a course
		Course course = new Course("Pacman");

		// add some reviews
		course.addReview(new Review("Greatful Course!"));
		course.addReview(new Review("Good Course!"));
		course.addReview(new Review("Bad Course :("));

		// save the courssout
		System.out.println("Saving the course...");
		appDAO.saveCourse(course);

		System.out.println(course);
		System.out.println(course.getReviews());

		System.out.println("Done!");
	}

}
