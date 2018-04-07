package selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class AdminLog extends AbstractClassTest{


  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testAdminLog() throws InterruptedException {
    driver.get(baseUrl + "/UBUassistant/index.jsp");
    driver.findElement(By.cssSelector("input.adminLink")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys("admin1@ubu.es");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("admin1");
    driver.findElement(By.cssSelector("button")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Log de uso de la apicación con las busquedas y su valoración por usuario.", driver.findElement(By.id("subtitle")).getText());
    driver.findElement(By.cssSelector("button.saveas")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(isElementPresent(By.linkText("CSV")));
    driver.findElement(By.cssSelector("input.clean")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(closeAlertAndGetItsText().matches("^¿Está seguro de que desea borrar la tabla de logs[\\s\\S]$"));
    assertFalse(isElementPresent(By.xpath("//table[@id='tabla']/tbody/tr/td[9]")));
  }

  @After
  @Override
  public void tearDown() {
    super.tearDown();
  }

  


}
