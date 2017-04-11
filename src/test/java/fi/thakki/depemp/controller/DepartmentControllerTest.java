package fi.thakki.depemp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DepartmentControllerTest {

	@Autowired
	private TestRestTemplate myRestTemplate;

	@Test
	public void test() {
		ResponseEntity<String> response = myRestTemplate.getForEntity("/departments", String.class);
	}
}
