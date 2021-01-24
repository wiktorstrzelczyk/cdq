package pl.wiktorstrzelczyk.recruitment.task.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CdqRecruitmentTaskApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(CdqRecruitmentTaskApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
