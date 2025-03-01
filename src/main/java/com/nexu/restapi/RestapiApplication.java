package com.nexu.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class RestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
		createDirectoryIfNotExists("/data");
	}

	private static void createDirectoryIfNotExists(String directoryPath) {
		File directory = new File(directoryPath);
		if (!directory.exists()) {
			if (directory.mkdirs()) {
				System.out.println("Directorio creado: " + directoryPath);
			} else {
				System.out.println("No se pudo crear el directorio: " + directoryPath);
			}
		}
	}
}
