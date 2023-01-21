
/*
[x] Test 1: Verifikovati da se u url-u stranice javlja ruta "/login".
[x] Verifikovati da atribut type u polju za unos email ima vrednost "email" i za password da ima atribut type "password.

[x] Test 2: Koristeci Faker uneti nasumicno generisan email i password i verifikovati da se pojavljuje poruka "User does not exist".
[x]Test 3: Verifikovati da kad se unese admin@admin.com (sto je dobar email) i pogresan password (generisan faker-om), da se pojavljuje poruka "Wrong password"


 */


import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Test1 {
        private WebDriver driver;
        public Faker faker;

    @BeforeClass
    public  void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\nenad\\Documents\\Driver\\chromedriver.exe" );
         driver = new ChromeDriver();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get("https://vue-demo.daniel-avellaneda.com/");
        driver.manage().window().maximize();
        driver.navigate().to("https://vue-demo.daniel-avellaneda.com/login");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    @Test
    public void verifyUrlLogin() {

        WebElement longIn = driver.findElement(By.xpath("/html/body/div/div/div/header/div/div[3]/a[3]/span"));
        longIn.click();
        String actualLink = driver.getCurrentUrl();
        Assert.assertTrue(actualLink.contains("/login"));

        WebElement inputEmail = driver.findElement(By.id("email"));
        WebElement inputPassword = driver.findElement(By.id("password"));
        Assert.assertEquals(inputEmail.getAttribute("type"), "email");
        Assert.assertEquals(inputPassword.getAttribute("type"), "password");

    }
    @Test
    public void incorrectUserAndEmail() {

        WebElement loginIncorrect = driver.findElement(By.xpath("/html/body/div/div/div/header/div/div[3]/a[3]/span"));
        loginIncorrect.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement inputEmail = driver.findElement(By.id("email"));
         faker = new Faker();
        String email = faker.internet().emailAddress();
        inputEmail.sendKeys(email);

        WebElement inputPassword = driver.findElement(By.id("password"));
        String password = faker.internet().password();
        inputPassword.sendKeys(password);

        WebElement loginButton = driver.findElement(By.xpath("/html/body/div/div/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button/span"));
        loginButton.click();


        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li")));
        WebElement message = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));
        Assert.assertTrue(message.getText().contains("User does not exist"));

    }
    @Test
    public void incorrectPassword() {

        WebElement loginIncorrect = driver.findElement(By.xpath("/html/body/div/div/div/header/div/div[3]/a[3]/span"));
        loginIncorrect.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement inputEmail = driver.findElement(By.id("email"));
        inputEmail.sendKeys("admin@admin.com");

        faker = new Faker();
        WebElement inputPassword = driver.findElement(By.id("password"));
        String password = faker.internet().password();
        inputPassword.sendKeys(password);

        WebElement loginButton = driver.findElement(By.xpath("/html/body/div/div/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button/span"));
        loginButton.click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li")));
        WebElement message = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));
        Assert.assertTrue(message.getText().contains("Wrong password"));
    }
}
