package com.example.aopdemo;

import com.example.aopdemo.dao.AccountDAO;
import com.example.aopdemo.dao.MembershipDAO;
import com.example.aopdemo.service.TrafficFortuneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AopdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopdemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AccountDAO accountDAO,
											   MembershipDAO membershipDAO,
											   TrafficFortuneService trafficFortuneService) {
		return runner -> {
			//demoTheBeforeAdvice(accountDAO, membershipDAO);
			//demoAfterReturningAdvice(accountDAO);
			//demoTheAfterThrowingAdvice(accountDAO);
			//demoAfterAdvice(accountDAO);
			//demoAroundAdvice(trafficFortuneService);
			//demoAroundAdviceHandleException(trafficFortuneService);

			demoAroundAdviceRethrowException(trafficFortuneService);
		};
	}

	private void demoAroundAdviceRethrowException(TrafficFortuneService trafficFortuneService) {
		System.out.println("\n\nMain Program: demoAroundAdviceRethrowException");

		System.out.println("Calling getFortune()");

		boolean tripWire = true;
		String data = trafficFortuneService.getFortune(tripWire);

		System.out.println("My fortune is: " + data);

		System.out.println("Finished!");
	}

	private void demoAroundAdviceHandleException(TrafficFortuneService trafficFortuneService) {
		System.out.println("\n\nMain Program: demoAroundAdviceHandleExceptio");

		System.out.println("Calling getFortune()");

		boolean tripWire = true;
		String data = trafficFortuneService.getFortune(tripWire);

		System.out.println("My fortune is: " + data);

		System.out.println("Finished!");
	}

	private void demoAroundAdvice(TrafficFortuneService trafficFortuneService) {
		System.out.println("\n\nMain Program: demoAroundAdvice");

		System.out.println("Calling getFortune()");

		String data = trafficFortuneService.getFortune();

		System.out.println("My fortune is: " + data);

		System.out.println("Finished!");
	}

	private void demoAfterAdvice(AccountDAO accountDAO) {
		List<Account> accounts = null;

		try {
			// add a boolean fkag to simulate exceptions
			boolean tripWire = false;

			accounts = accountDAO.findAccounts(tripWire);
		} catch (Exception e) {
			System.out.println("\n\nMain Program: caught exception: " + e);
		}

		System.out.println("\n\nMain Program: demoTheAfterThrowingAdvice");
		System.out.println("------------------------------------------------");
		System.out.println("Accounts: " + accounts);
	}

	private void demoTheAfterThrowingAdvice(AccountDAO accountDAO) {
		List<Account> accounts = null;

		try {
			// add a boolean fkag to simulate exceptions
			boolean tripWire = true;

			accounts = accountDAO.findAccounts(tripWire);
		} catch (Exception e) {
			System.out.println("\n\nMain Program: caught exception: " + e);
		}

		System.out.println("\n\nMain Program: demoTheAfterThrowingAdvice");
		System.out.println("------------------------------------------------");
		System.out.println("Accounts: " + accounts);
	}

	private void demoAfterReturningAdvice(AccountDAO accountDAO) {
		// call the business method
		List<Account> accounts = accountDAO.findAccounts();
		System.out.println("\n\nMain Program: demoAfterReturningAdvice");
		System.out.println("------------------------------------------------");
		System.out.println("\n\n=====>>> Accounts: " + accounts);
	}

	private void demoTheBeforeAdvice(AccountDAO accountDAO, MembershipDAO membershipDAO) {
		// call the business method
		Account account = new Account();
		account.setName("John");
		account.setLevel("Platinum");
		accountDAO.addAccount(account, true);
		accountDAO.doWork();

		// call the aacountDAO getter/setter methods
		accountDAO.setName("foobar");
		accountDAO.setServiceCode("silver");

		String name = accountDAO.getName();
		String code = accountDAO.getServiceCode();

		// call the membership business method
		membershipDAO.addSillyMember();
		membershipDAO.goToSleep();
	}

}
