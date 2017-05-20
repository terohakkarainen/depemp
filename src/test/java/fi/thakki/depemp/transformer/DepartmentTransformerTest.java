package fi.thakki.depemp.transformer;

import org.junit.Test;

public class DepartmentTransformerTest {

    private DepartmentTransformer myTransformerUnderTest = new DepartmentTransformer();

    @Test(expected = NullPointerException.class)
    public void toListDtoThrowsExceptionForNullSource() {
        myTransformerUnderTest.toListDto(null);
    }

    @Test(expected = NullPointerException.class)
    public void toDetailsDtoThrowsExceptionForNullSource() {
        myTransformerUnderTest.toDetailsDto(null);
    }

    @Test(expected = NullPointerException.class)
    public void toDepartmentThrowsExceptionForNullSource() {
        myTransformerUnderTest.toDepartment(null);
    }
}
