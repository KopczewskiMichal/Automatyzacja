package org.app;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
    SpringApplication.run(AppApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("MYSQL_USER: " + System.getenv("MYSQL_USER"));
        System.out.println("MYSQL_PASSWORD: " + System.getenv("MYSQL_PASSWORD"));
        System.out.println("MYSQL_URL: " + System.getenv("MYSQL_URL"));
    }

}
