package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class OrderFeatureFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createOrderPage_isAccessible(ChromeDriver driver) {
        driver.get(baseUrl + "/order/create");

        assertEquals("Create Order", driver.getTitle());
        assertEquals("Create New Order", driver.findElement(By.tagName("h3")).getText());
    }

    @Test
    void orderHistoryPage_isAccessible(ChromeDriver driver) {
        driver.get(baseUrl + "/order/history");

        assertEquals("Order History", driver.getTitle());
        assertEquals("Order History", driver.findElement(By.tagName("h3")).getText());
        assertTrue(driver.findElement(By.id("authorInput")).isDisplayed());
    }

    @Test
    void submitOrderHistory_shouldShowResultsArea(ChromeDriver driver) {
        driver.get(baseUrl + "/order/history");

        driver.findElement(By.id("authorInput")).sendKeys("Safira Sudrajat");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertEquals("Order History", driver.getTitle());
        assertTrue(driver.getPageSource().contains("No orders found."));
    }
}
