package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class PaymentFeatureFunctionalTest {

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
    void paymentDetailFormPage_isAccessible(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/detail");

        assertEquals("Payment Detail", driver.getTitle());
        assertTrue(driver.findElement(By.id("paymentIdInput")).isDisplayed());
    }

    @Test
    void paymentDetailByIdPage_isAccessible(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/detail/pay-001");

        assertEquals("Payment Detail", driver.getTitle());
        assertTrue(driver.getPageSource().contains("Payment ID"));
    }

    @Test
    void paymentAdminListPage_isAccessible(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/list");

        assertEquals("Payment Admin List", driver.getTitle());
        assertTrue(driver.findElement(By.id("paymentTable")).isDisplayed());
    }

    @Test
    void paymentAdminDetailPage_isAccessible(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/pay-001");

        assertEquals("Payment Admin Detail", driver.getTitle());
        assertTrue(driver.findElement(By.id("statusSelect")).isDisplayed());
    }

    @Test
    void paymentAdminSetStatusAction_isAvailable(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/pay-001");

        WebElement statusSelect = driver.findElement(By.id("statusSelect"));
        statusSelect.findElements(By.tagName("option")).stream()
                .filter(option -> option.getAttribute("value").equals("SUCCESS"))
                .findFirst()
                .ifPresent(WebElement::click);

        driver.findElement(By.id("setStatusButton")).click();

        assertEquals("Payment Admin Detail", driver.getTitle());
        List<WebElement> statusLabels = driver.findElements(By.id("paymentStatusLabel"));
        assertTrue(!statusLabels.isEmpty());
    }
}
