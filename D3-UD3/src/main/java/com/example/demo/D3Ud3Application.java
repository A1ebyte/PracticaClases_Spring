package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class D3Ud3Application {

	public static void main(String[] args) {
		RepositorioStock repo = new RepositorioStock();
		iniciarRepo(repo);
		SpringApplication.run(D3Ud3Application.class, args);
	}

	private static void iniciarRepo(RepositorioStock repo) {
		if (repo.getAll().isEmpty()) {
			repo.add("Hollow Knight", new producto(12, 20.00));
			repo.add("XCOM2", new producto(30, 15.50));
			repo.add("Hades", new producto(22, 25.00));
			repo.save();
		}
		System.out.println(repo.getAll());
	}
}
