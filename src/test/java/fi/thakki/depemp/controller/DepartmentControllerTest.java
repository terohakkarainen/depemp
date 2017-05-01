package fi.thakki.depemp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.Application;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.model.EntityFactory;
import fi.thakki.depemp.model.builder.DepartmentBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-integrationTest.properties")
public class DepartmentControllerTest {

	@Autowired
	private TestRestTemplate myRestTemplate;

	@Autowired
	private EntityFactory myEntityFactory;

	@Test
	public void getAllDepartments() throws Exception {
		String name = randomString(Department.NAME_LENGTH);
		String desc = randomString(Department.DESCRIPTION_LENGTH);
		Department department = new DepartmentBuilder().name(name).description(desc).get();
		myEntityFactory.persist(department);
		
		ResponseEntity<String> result = myRestTemplate.getForEntity("/departments", String.class);

		assertThat(result.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		JSONAssert.assertEquals(String.format("[{\"id\":1,\"name\":\"%s\"}]", name), result.getBody(), true);
	}
	
	@Test
	public void getDepartment() throws Exception {
		String name = randomString(Department.NAME_LENGTH);
		String desc = randomString(Department.DESCRIPTION_LENGTH);
		Department department = new DepartmentBuilder().name(name).description(desc).get();
		myEntityFactory.persist(department);
		
		ResponseEntity<String> result =
				myRestTemplate.getForEntity("/departments/" + department.getId(), String.class);

		assertThat(result.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		JSONAssert.assertEquals(
			String.format("{\"id\":%d,\"name\":\"%s\",\"description\":%s}", department.getId(), name, desc),
			result.getBody(),
			true);
	}
	
	@Test
	public void getNonExistingDepartment() throws Exception {
		ResponseEntity<String> result =
				myRestTemplate.getForEntity("/departments/-100", String.class);
		
		assertThat(result.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	private static final String randomString(int length) {
		return RandomStringUtils.random(length);
	}
}
