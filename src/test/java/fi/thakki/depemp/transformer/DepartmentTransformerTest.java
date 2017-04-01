package fi.thakki.depemp.transformer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fi.thakki.depemp.dto.DepartmentListDto;
import fi.thakki.depemp.model.Department;
import fi.thakki.depemp.model.builder.DepartmentBuilder;

public class DepartmentTransformerTest {

	private DepartmentTransformer myTransformerUnderTest = new DepartmentTransformer();

	@Test
	public void toListDtoHappyCase() {
		Department dep = new DepartmentBuilder().id(Long.valueOf(1)).name("Foobar")
				.description("A little bit longer description").get();

		DepartmentListDto result = myTransformerUnderTest.toListDto(dep);

		assertEquals(dep.getId(), result.id);
		assertEquals(dep.getName(), result.name);
	}
}
