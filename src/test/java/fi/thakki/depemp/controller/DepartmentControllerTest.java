package fi.thakki.depemp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.ArraySizeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.Application;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.model.EntityFactory;
import fi.thakki.depemp.model.LengthDomain;
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
	public void test() throws Exception {
		String name = randomString(LengthDomain.SHORT_DESCRIPTION);
		String desc = randomString(LengthDomain.LONG_DESCRIPTION);
		Department department = new DepartmentBuilder().name(name).description(desc).get();
		myEntityFactory.persist(department);
		
		ResponseEntity<String> result = myRestTemplate.getForEntity("/departments", String.class);

		assertThat(result.getStatusCode().value()).isEqualTo(200);
		assertArraySize(1, result.getBody());
		JSONAssert.assertEquals(
			String.format("[{\"id\":1,\"name\":\"%s\"}]", name), result.getBody(), true);
	}
	
	private static void assertArraySize(int expected, String content) throws JSONException {
		JSONAssert.assertEquals(
				  String.format("[%d]", expected), 
				  content,
				  new ArraySizeComparator(JSONCompareMode.LENIENT));		
	}
	
	private static final String randomString(int length) {
		return RandomStringUtils.random(length);
	}
}
