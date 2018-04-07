package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.JUnitCore;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class AdminLearn extends AbstractClassTest{
  
  private static final String TABLA1 = "//table[@id='tabla']/tbody/tr";
  private static final String TABLA2 = "//table[@id='tabla']/tbody/tr[";
  private static final String TABLA3 = "]/td[2]";
  private static final String BUTTON = "(//input[@id='button'])[";
  
  @Before
  @Override
  public void setUp() {
    super.setUp();
    JUnitCore junit = new JUnitCore();
    junit.run(NoAnswer.class);
  }


  @Test
  public void testAdminLearn() throws InterruptedException {
    driver.get(baseUrl + "/UBUassistant/index.jsp");
    driver.findElement(By.cssSelector("input.adminLink")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys("admin2@ubu.es");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("admin2");
    driver.findElement(By.cssSelector("button")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("learn")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.cssSelector("button.saveas")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(isElementPresent(By.linkText("XLS")));
    
    int rowCount=driver.findElements(By.xpath(TABLA1)).size();
    
    assertEquals("ccc", driver.findElement(By.xpath(TABLA2+rowCount+TABLA3)).getText());
    driver.findElement(By.xpath(BUTTON+(rowCount*2)+"]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    
    rowCount=driver.findElements(By.xpath(TABLA1)).size();
    
    assertEquals("verve", driver.findElement(By.xpath(TABLA2+rowCount+TABLA3)).getText());
    driver.findElement(By.xpath(BUTTON+(rowCount*2-1)+"]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.linkText("Editor de Casos")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("edit")).click();
    
    int rowCount2=driver.findElements(By.xpath(TABLA1)).size();

    assertEquals("verve", driver.findElement(By.xpath(TABLA2+rowCount2+TABLA3)).getText());
    driver.findElement(By.xpath(BUTTON+(rowCount2*2)+"]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.switchTo().alert().accept();
    TimeUnit.MILLISECONDS.sleep(5000);

    
  }

  @After
  @Override
  public void tearDown() {
    super.tearDown();
  }
}
