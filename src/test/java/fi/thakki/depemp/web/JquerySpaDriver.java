package fi.thakki.depemp.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JquerySpaDriver {

    private WebDriver myWebDriver;
    private String myBaseUrl;
    
    public JquerySpaDriver(WebDriver webDriver, String baseUrl) {
        myWebDriver = webDriver;
        myBaseUrl = baseUrl;
    }
    
    public void navigateTo() {
        myWebDriver.get(myBaseUrl + "/jquery/index.html");
        myWebDriver.findElement(By.id("departmentsTable")).isDisplayed();
    }

    public void addDepartment(
            String name,
            String description) {
        myWebDriver.findElement(By.id("newDepartmentName")).click();        
        myWebDriver.findElement(By.id("newDepartmentName")).sendKeys(name);
        myWebDriver.findElement(By.id("newDepartmentDescription")).click();        
        myWebDriver.findElement(By.id("newDepartmentDescription")).sendKeys(description);
        myWebDriver.findElement(By.id("newDepartmentSubmit")).click();
    }

    public void assertDepartmentExists(
            String name) {
        // TODO Auto-generated method stub
    }
}
