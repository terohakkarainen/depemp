package fi.thakki.depemp.web.jquery;

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
        
        pageDriver.assertDepartmentExists(newDepartmentName);
    }
}
