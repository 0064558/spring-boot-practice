package com.example.cruddemo;

import com.example.cruddemo.dao.AppDAO;
import com.example.cruddemo.entity.Instructor;
import com.example.cruddemo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLOutput;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		// Instructor instructor = new Instructor("Maria", "Brown", "maria@gmail.com");
		// InstructorDetail instructorDetail = new InstructorDetail("Tráfico", "http>//www.luv2code.com/youtube");
		return runner -> {
			// createInstructor(appDAO, instructor, instructorDetail);
			// findInstructor(appDAO, 3);
			// deleteInstructor(appDAO, 2);
			findInstructorDetail(appDAO, 1);
		};
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
