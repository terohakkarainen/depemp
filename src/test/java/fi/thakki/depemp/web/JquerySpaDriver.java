package fi.thakki.depemp.web;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JquerySpaDriver {

    public static class DepartmentDoesNotExistException extends RuntimeException {

        public DepartmentDoesNotExistException(
                String message) {
            super(message);
        }
    }

    private WebDriver myWebDriver;
    private String myBaseUrl;

    public JquerySpaDriver(
            WebDriver webDriver,
            String baseUrl) {
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
        final String departmentClass = "department";

        waitSilently();
        new WebDriverWait(myWebDriver, 5).until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(departmentClass)));

        List<WebElement> departments = myWebDriver.findElements(By.className(departmentClass));
        if (departments.stream().map(w -> w.findElement(By.tagName("td")).getText())
                .noneMatch(s -> name.equals(s))) {
            throw new DepartmentDoesNotExistException(
                    "Did not find a department with name \"" + name + "\"");
        }
    }

    private void waitSilently() {
        try {
            Thread.sleep(WebDriverWait.DEFAULT_SLEEP_TIMEOUT);
        } catch (Exception e) {
            // Just catch.
        }
    }
}
