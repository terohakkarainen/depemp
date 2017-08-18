package fi.thakki.depemp.web.jquery;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import fi.thakki.depemp.util.StringUtil;
import fi.thakki.depemp.web.WebTestBase;

public class JquerySpaTest extends WebTestBase {

    private JquerySpaDriver pageDriver;

    @Override
    protected void onSetUpComplete() {
        pageDriver = new JquerySpaDriver(webDriver(), baseUrl());
    }

    @Test
    public void departmentCanBeAdded() {
        String newDepartmentName = StringUtil.randomString();
        String newDepartmentDescription = StringUtil.randomString();

        pageDriver.navigateTo();
        pageDriver.typeNewDepartmentName(newDepartmentName);
        pageDriver.typeNewDepartmentDescription(newDepartmentDescription);
        pageDriver.submitNewDepartment();

        pageDriver.waitUntilDepartmentListIsRefreshed();
        assertThat(pageDriver.getDepartmentNames()).contains(newDepartmentName);
    }

    @Test
    public void validationErrorsAreShown() {
        pageDriver.navigateTo();
        pageDriver.submitNewDepartment();

        pageDriver.waitUntilErrorMessageIsDisplayed();
        assertThat(pageDriver.getErrorMessage()).isEqualTo("Validation failed: 1 error(s)");
        assertThat(pageDriver.getErrorDetails()).contains("name: size must be between 1 and 32");
    }
}
