package fi.thakki.depemp.web.jquery;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fi.thakki.depemp.web.PageDriver;

public class JquerySpaDriver implements PageDriver {

    public static class DepartmentDoesNotExistException extends RuntimeException {

        public DepartmentDoesNotExistException(
                String message) {
            super(message);
        }
    }

    public static class UnexpectedContentException extends RuntimeException {
        
        public UnexpectedContentException(String message) {
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

    @Override
    public void navigateTo() {
        myWebDriver.get(myBaseUrl + "/jquery/index.html");
        if(!findById("departmentsTable").isDisplayed()) {
            throw new UnexpectedContentException("Element #departmentsTable not visible");
        }
    }

    public void typeNewDepartmentName(
            String name) {
        typeToField("newDepartmentName", name);
    }

    public void typeNewDepartmentDescription(
            String description) {
        typeToField("newDepartmentDescription", description);
    }

    public void submitNewDepartment() {
        clickLink("newDepartmentSubmit");
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

    private void typeToField(
            String fieldId,
            String value) {
        WebElement field = findById(fieldId);
        Actions actions = new Actions(myWebDriver);
        actions.moveToElement(field).click().perform();
        actions.moveToElement(field).sendKeys(value).perform();
    }

    private void clickLink(String linkId) {
        WebElement link = findById(linkId);
        Actions actions = new Actions(myWebDriver);
        actions.moveToElement(link).click().perform();
    }
    
    private WebElement findById(
            String id) {
        return myWebDriver.findElement(By.id(id));
    }
}
