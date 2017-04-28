package fi.thakki.depemp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.IntegrationTestConfiguration;
import fi.thakki.depemp.model.Department;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
public class DepartmentControllerTest {

	@Autowired
	private TestRestTemplate myRestTemplate;

	@Test
	public void test() {
		ResponseEntity<Department> result = myRestTemplate.getForEntity("/departments/0", Department.class);
		//List<?> result = myRestTemplate.getForObject("/departments", List.class);
		System.out.println("Status: " + result.getStatusCodeValue());
	}
}
