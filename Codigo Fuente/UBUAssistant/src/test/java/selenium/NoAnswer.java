package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class NoAnswer extends AbstractClassTest{

  private static final String INPUT = "user-input";
  private static final String SUGBUT = "input.sugBut";

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testNoAnswer() throws InterruptedException{
    driver.get(baseUrl + "/UBUassistant/index.jsp");
    driver.findElement(By.id("pinguino")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    
    /***/
    driver.switchTo().frame(driver.findElement(By.id("ubuassistantFrame")));
    /***/
    
    driver.findElement(By.id(INPUT)).clear();
    driver.findElement(By.id(INPUT)).sendKeys("verve");
    driver.findElement(By.id("enviar")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Lo siento no tengo respuestas a tu pregunta :(\nEcha un vistazo a las sugerencias de búsqueda", driver.findElement(By.xpath("//div[@id='chat-output']/div[3]/div")).getText());
    assertEquals("verano", driver.findElement(By.cssSelector(SUGBUT)).getAttribute("value"));
    assertTrue(isElementPresent(By.xpath("(//input[@value='nivel 0'])[2]")));
    assertTrue(isElementPresent(By.xpath("(//input[@value='matricula cursos verano'])[2]")));
    driver.findElement(By.cssSelector(SUGBUT)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(isElementPresent(By.linkText("http://www.ubu.es/cursos-de-verano-de-la-universidad-de-burgos")));
    assertEquals("Valora esta respuesta", driver.findElement(By.id("buttonPanelContent")).getText());
    driver.findElement(By.cssSelector("label[title=\"text\"]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Voto guardado con éxito. Su voto ha sido 5", driver.findElement(By.id("buttonPanel")).getText());
    driver.findElement(By.id(INPUT)).clear();
    driver.findElement(By.id(INPUT)).sendKeys("ccc");
    driver.findElement(By.id("enviar")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.cssSelector(SUGBUT)).click();
  }

  @After
  @Override
  public void tearDown() {
    super.tearDown();
  }
}
