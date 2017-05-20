package fi.thakki.depemp.web;

import org.junit.Test;
import org.openqa.selenium.By;

public class JquerySpaTest extends WebTestBase {
    
    @Test
    public void departmentCanBeAdded() {
        webDriver().get(baseUrl() + "/jquery/index.html");
        webDriver().findElement(By.id("departmentsTable")).isDisplayed();
    }
}
