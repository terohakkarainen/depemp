package fi.thakki.depemp.controller;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.IntegrationTestConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(IntegrationTestConfiguration.class)
public class DepartmentControllerTest {

	@Autowired
	private TestRestTemplate myRestTemplate;

	@Test
	public void test() {
		// FIXME
//		List<?> result = myRestTemplate.getForObject("/departments", List.class);
//		System.out.println("Departments: " + result.size());
	}
}
