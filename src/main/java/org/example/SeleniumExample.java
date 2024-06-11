//package org.example;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Cookie;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//public class SeleniumExample {
//    public static void main(String[] args) {
//        // Setup WebDriver
//        WebDriverManager.chromedriver().browserVersion("125.0.6422.113").setup();
//        WebDriverManager.chromedriver().driverVersion("125.0.6422.113").setup();
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // If you want to run in headless mode
//        options.addArguments("--disable-gpu");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--disable-web-security");
//        WebDriver driver = new ChromeDriver(options);
//
//        try {
//            // 로그인
//            Map<String, String> cookies = login(driver);
//            if (cookies.isEmpty()) {
//                System.out.println("Login failed.");
//                return;
//            }
//
//            // 데이터 요청
//            String response = requestData(driver, cookies);
//
//            // 응답 처리
//            System.out.println("Data Response: " + response);
//
//            // 에러 메시지 확인
//            if (response.contains("ErrorMsg:int=1")) {
//                System.out.println("An error occurred while fetching data.");
//            } else {
//                System.out.println("Data fetched successfully.");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            driver.quit();
//        }
//    }
//
//    private static Map<String, String> login(WebDriver driver) throws InterruptedException {
//        String loginUrl = "https://pp.korea7.co.kr/co/main/retrieveSessionInfoList";
//
//        // Navigate to login page
//        driver.get(loginUrl);
//
//        // Find and fill login form elements
//        WebElement userIdElement = driver.findElement(By.name("userId"));
//        WebElement userPwElement = driver.findElement(By.name("userPw"));
//        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
//
//        userIdElement.sendKeys("dkfood");
//        userPwElement.sendKeys("dkfood0801@");
//        submitButton.click();
//
//        // Wait for login to complete and cookies to be set
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("some_element_id_after_login")));
//
//        // Extract cookies
//        Set<Cookie> seleniumCookies = driver.manage().getCookies();
//        Map<String, String> cookies = new HashMap<>();
//        for (Cookie cookie : seleniumCookies) {
//            if (cookie.getName().equals("WMONID") || cookie.getName().equals("JSESSIONID")) {
//                cookies.put(cookie.getName(), cookie.getValue());
//            }
//        }
//
//        return cookies;
//    }
//
//    private static String requestData(WebDriver driver, Map<String, String> cookies) {
//        String dataUrl = "https://pp.korea7.co.kr/pp/pp/od/PPOD0010/retrieveMasterList";
//
//        // Navigate to data page
//        driver.get(dataUrl);
//
//        // Set cookies for the request
//        for (Map.Entry<String, String> entry : cookies.entrySet()) {
//            driver.manage().addCookie(new Cookie(entry.getKey(), entry.getValue()));
//        }
//
//        // Refresh to apply cookies
//        driver.navigate().refresh();
//
//        // Fill and submit the data request form
//        WebElement dayTypeElement = driver.findElement(By.name("dayType"));
//        WebElement evnStaYmdElement = driver.findElement(By.name("evnStaYmd"));
//        WebElement evnEndYmdElement = driver.findElement(By.name("evnEndYmd"));
//        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
//
//        dayTypeElement.sendKeys("N");
//        evnStaYmdElement.sendKeys("20240513");
//        evnEndYmdElement.sendKeys("20240520");
//        submitButton.click();
//
//        // Wait for data to be returned
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement responseElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("response_element_id")));
//
//        // Get the response text
//        return responseElement.getText();
//    }
//}
