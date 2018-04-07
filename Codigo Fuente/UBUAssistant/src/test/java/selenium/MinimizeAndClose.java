package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class MinimizeAndClose extends AbstractClassTest{
  
  private static final String ICON = "pinguino";
  private static final String MIN = "btn-minimize";
  private static final String FRAME = "ubuassistantFrame";

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testMinimizeAndClose() throws InterruptedException{
	  
    driver.get(baseUrl + "/UBUassistant/index.jsp");
    driver.findElement(By.id(ICON)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(isElementPresent(By.id("divchat-window")));
    driver.findElement(By.id(MIN)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id(MIN)).click();
    TimeUnit.MILLISECONDS.sleep(2000);

    /***/
    driver.switchTo().frame(driver.findElement(By.id(FRAME)));
    /***/
    
    driver.findElement(By.id("user-input")).sendKeys("becas internacionales");
    driver.findElement(By.id("enviar")).click();
    TimeUnit.MILLISECONDS.sleep(3000);
    
    /***/
    driver.switchTo().defaultContent();
    /***/
    
    driver.findElement(By.id(MIN)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id(MIN)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    
    /***/
    driver.switchTo().frame(driver.findElement(By.id(FRAME)));
    /***/
    
    assertTrue(isElementPresent(By.linkText("http://www.ubu.es/becas-de-cooperacion")));
    assertTrue(isElementPresent(By.xpath("//div[@id='chat-output']/div[3]/div")));
    
    /***/
    driver.switchTo().defaultContent();
    /***/
    
    driver.findElement(By.cssSelector("input.btn-close")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(isElementPresent(By.id(ICON)));
    driver.findElement(By.id(ICON)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    
    /***/
    driver.switchTo().frame(driver.findElement(By.id(FRAME)));
    /***/
    
    assertFalse(isElementPresent(By.xpath("//div[@id='chat-output']/div[3]/div")));
    
    /***/
    driver.switchTo().defaultContent();
    /***/
    
    driver.findElement(By.cssSelector("input.btn-close")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.cssSelector("input.btn-close-pinguino")).click();

  }

  @After
  @Override
  public void tearDown() {
    super.tearDown();
  }
}
