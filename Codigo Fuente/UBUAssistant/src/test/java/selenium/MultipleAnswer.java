package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class MultipleAnswer extends AbstractClassTest{

  private static final String BUTTON = "(//input[@id='but'])[2]";

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testMultipleAnswer() throws InterruptedException{
    driver.get(baseUrl + "/UBUassistant/index.jsp");
    driver.findElement(By.id("pinguino")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    
    /***/
    driver.switchTo().frame(driver.findElement(By.id("ubuassistantFrame")));
    /***/
    
    driver.findElement(By.id("user-input")).clear();
    driver.findElement(By.id("user-input")).sendKeys("becas");
    driver.findElement(By.id("enviar")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Vaya, parece que tengo demasiadas respuestas.\nIntenta ser más concreto o selecciona alguna sugerencia.", driver.findElement(By.xpath("//div[@id='chat-output']/div[3]/div")).getText());
    assertEquals("Becas", driver.findElement(By.id("but")).getAttribute("value"));
    assertEquals("Becas internacionales", driver.findElement(By.xpath(BUTTON)).getAttribute("value"));
    driver.findElement(By.xpath(BUTTON)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(isElementPresent(By.linkText("http://www.ubu.es/becas-de-cooperacion")));
    driver.findElement(By.id("but")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.cssSelector("label[title=\"text\"]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(isElementPresent(By.id("but")));
    driver.findElement(By.id("but")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(isElementPresent(By.linkText("http://www.ubu.es/ayudas-y-becas")));
    driver.findElement(By.xpath(BUTTON)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
  }

  @After
  @Override
  public void tearDown() {
    super.tearDown();
  }
}
