package fi.thakki.depemp.web;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import fi.thakki.depemp.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-webTest.properties")
public abstract class WebTestBase {

    @Value("${webdriver.chrome.driver}")
    private String myChromeDriverLocation;

    @Value("${webtest.baseurl}")
    private String myBaseUrl;

    private WebDriver myWebDriver;
    
    @Before
    public void onSetUp() {
        System.setProperty("webdriver.chrome.driver", myChromeDriverLocation);
        myWebDriver = new ChromeDriver();
        onSetUpComplete();
    }
    
    @After
    public void onTearDown() {
        myWebDriver.quit();
    }

    protected WebDriver webDriver() {
        return myWebDriver;
    }
    
    protected String baseUrl() {
        return myBaseUrl;
    }
    
    protected abstract void onSetUpComplete();
}
