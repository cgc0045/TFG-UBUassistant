package selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class EnterAdminPage extends AbstractClassTest{


  private static final String BUTTON = "button";
  private static final String ERROR = "error";
  private static final String PSWD = "password";

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }


  @Test
  public void testEnterAdminPage() throws InterruptedException{

    driver.get(baseUrl + "/UBUassistant/index.jsp");
    driver.findElement(By.cssSelector("input.adminLink")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("volver")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Un asistente que te ayudará con cualquier duda sobre la UBU", driver.findElement(By.cssSelector("div.inner > strong")).getText());
    driver.findElement(By.cssSelector("input.adminLink")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.cssSelector(BUTTON)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(driver.findElement(By.id(ERROR)).getText().equals("* Debe rellenar todos los campos"));
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys("admin1@ubu.es");
    driver.findElement(By.cssSelector(BUTTON)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(driver.findElement(By.id(ERROR)).getText().equals("* Debe rellenar todos los campos"));
    driver.findElement(By.id(PSWD)).clear();
    driver.findElement(By.id(PSWD)).sendKeys("aaaaa");
    driver.findElement(By.cssSelector(BUTTON)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Usuario y/o contraseña no válidos", driver.findElement(By.id(ERROR)).getText());
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys("aaaaa");
    driver.findElement(By.id(PSWD)).clear();
    driver.findElement(By.id(PSWD)).sendKeys("admin1");
    driver.findElement(By.cssSelector(BUTTON)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys("admin1@ubu.es");
    driver.findElement(By.id(PSWD)).clear();
    driver.findElement(By.id(PSWD)).sendKeys("admin1");
    driver.findElement(By.cssSelector(BUTTON)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Log de uso", driver.findElement(By.id("title")).getText());
  }

  @After
  @Override
  public void tearDown(){
    super.tearDown();
  }
}
