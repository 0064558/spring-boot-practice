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

		return runner -> {

		};
	}

	private void deleteCourseAndReviews(AppDAO appDAO, int id) {
		System.out.println("Deleting course with id: " + id);
		appDAO.deleteCourseById(id);
		System.out.println("Done!");
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
