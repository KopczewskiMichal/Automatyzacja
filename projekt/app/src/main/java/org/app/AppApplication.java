package org.app;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
    Dotenv dotenv = Dotenv.configure().load();
    System.setProperty("MYSQL_USER", dotenv.get("MYSQL_USER"));
    System.setProperty("MYSQL_PASSWORD", dotenv.get("MYSQL_PASSWORD"));
    System.setProperty("MYSQL_URL", dotenv.get("MYSQL_URL"));
    SpringApplication.run(AppApplication.class, args);

    }

}
