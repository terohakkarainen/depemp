package fi.thakki.depemp.web.jquery;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fi.thakki.depemp.web.PageDriver;

public class JquerySpaDriver implements PageDriver {

    private static final String CLASS_DEPARTMENT = "department";
    private static final String CLASS_ERROR_MESSAGE = "errorMessage";
    private static final String CLASS_ERROR_DETAIL = "errorDetail";

    public static class DepartmentDoesNotExistException extends RuntimeException {

        public DepartmentDoesNotExistException(
                String message) {
            super(message);
        }
    }

    public static class UnexpectedContentException extends RuntimeException {

        public UnexpectedContentException(
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

    @Override
    public void navigateTo() {
        myWebDriver.get(myBaseUrl + "/jquery/index.html");
        if (!findById("departmentsTable").isDisplayed()) {
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

    private void typeToField(
            String fieldId,
            String value) {
        WebElement field = findById(fieldId);
        Actions actions = new Actions(myWebDriver);
        actions.moveToElement(field).click(field).perform();
        actions.moveToElement(field).sendKeys(value).perform();
    }

    private void clickLink(
            String linkId) {
        WebElement link = findById(linkId);
        Actions actions = new Actions(myWebDriver);
        actions.moveToElement(link).click(link).perform();
    }

    private WebElement findById(
            String id) {
        return myWebDriver.findElement(By.id(id));
    }

    public void waitUntilErrorMessageIsDisplayed() {
        waitSilently();
        new WebDriverWait(myWebDriver, 5).until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.className(CLASS_ERROR_MESSAGE)));
    }

    public String getErrorMessage() {
        return myWebDriver.findElement(By.className(CLASS_ERROR_MESSAGE)).getText();
    }

    public List<String> getErrorDetails() {
        List<WebElement> elements = myWebDriver.findElements(By.className(CLASS_ERROR_DETAIL));
        return elements.stream().map(e -> e.getText()).collect(Collectors.toList());
    }

    private void waitSilently() {
        try {
            Thread.sleep(WebDriverWait.DEFAULT_SLEEP_TIMEOUT);
        } catch (Exception e) {
            // Just catch.
        }
    }

    public void waitUntilDepartmentListIsRefreshed() {
        waitSilently();
        new WebDriverWait(myWebDriver, 5).until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.className(CLASS_DEPARTMENT)));
    }

    public List<String> getDepartmentNames() {
        List<WebElement> departments = myWebDriver.findElements(By.className(CLASS_DEPARTMENT));
        return departments.stream().map(e -> e.findElement(By.tagName("td")).getText())
                .collect(Collectors.toList());
    }
}
