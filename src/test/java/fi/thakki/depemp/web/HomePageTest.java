package fi.thakki.depemp.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
public class HomePageTest {

    @Value("${webdriver.chrome.driver}")
    private String myChromeDriverLocation;
    
    private WebDriver myWebDriver;
    
    @Before
    public void onSetUp() {
        System.setProperty("webdriver.chrome.driver", myChromeDriverLocation);
        myWebDriver = new ChromeDriver();
    }
    
    @After
    public void onTearDown() {
        myWebDriver.quit();
    }
    
    @Test
    public void testFoo() {
        myWebDriver.get("http://localhost:8080");
        assertThat(myWebDriver.getTitle()).isEqualTo("Depemp exercise project");
    }
}
