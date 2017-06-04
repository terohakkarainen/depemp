package fi.thakki.depemp.web;

import org.junit.Test;

public class JquerySpaTest extends WebTestBase {

    private JquerySpaDriver pageDriver;

    @Override
    protected void onSetUpComplete() {
        pageDriver = new JquerySpaDriver(webDriver(), baseUrl());
    }

    @Test
    public void departmentCanBeAdded() {
        pageDriver.navigateTo();
        pageDriver.addDepartment("Foo", "Bar");
        pageDriver.assertDepartmentExists("Foo");
    }
}
