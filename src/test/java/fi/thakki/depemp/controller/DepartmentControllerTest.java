package fi.thakki.depemp.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.Application;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.model.builder.DepartmentBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-integrationTest.properties")
public class DepartmentControllerTest {

	@Autowired
	private TestRestTemplate myRestTemplate;

	@Autowired
	private EntityFactory ef;
	
	@Test
	public void test() {
		String name = "foo";
		String desc = "bar";
		Department department = new DepartmentBuilder().name(name).description(desc).get();
		ef.persist(department);
		
		ResponseEntity<String> result = myRestTemplate.getForEntity("/departments", String.class);
		assertEquals(200, result.getStatusCodeValue());
		System.out.println(result.getBody());
	}
}
