package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class ReservedAndSimpleAnswer extends AbstractClassTest{

  private static final String INPUT = "user-input";
  private static final String ENVIAR = "enviar";

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testReservedAndSimpleAnswer() throws InterruptedException{
    driver.get(baseUrl + "/UBUassistant/index.jsp");
    driver.findElement(By.id("pinguino")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    
    /***/
    driver.switchTo().frame(driver.findElement(By.id("ubuassistantFrame")));
    /***/
    
    driver.findElement(By.id(INPUT)).clear();
    driver.findElement(By.id(INPUT)).sendKeys("Hola");
    driver.findElement(By.id(ENVIAR)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Hola, estoy preparada para responder, adelánte", driver.findElement(By.xpath("//div[@id='chat-output']/div[3]/div")).getText());
    driver.findElement(By.id(INPUT)).clear();
    driver.findElement(By.id(INPUT)).sendKeys("Callate");
    driver.findElement(By.id(ENVIAR)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Lo siento, solo intentaba ayudar", driver.findElement(By.xpath("//div[@id='chat-output']/div[5]/div")).getText());
    driver.findElement(By.id(INPUT)).clear();
    driver.findElement(By.id(INPUT)).sendKeys("deporte");
    driver.findElement(By.id(ENVIAR)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(isElementPresent(By.linkText("http://www.ubu.es/deportes")));
  }

  @After
  @Override
  public void tearDown() {
    super.tearDown();
  }
}
