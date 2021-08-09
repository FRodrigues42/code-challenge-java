package pt.frodrigues.challenge.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import pt.frodrigues.challenge.CodeChallengeJavaApp;

@CucumberContextConfiguration
@SpringBootTest(classes = CodeChallengeJavaApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
