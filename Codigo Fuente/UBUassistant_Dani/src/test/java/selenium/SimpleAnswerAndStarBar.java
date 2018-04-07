package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class SimpleAnswerAndStarBar extends AbstractClassTest{

  private static final String INPUT = "user-input";
  private static final String ENVIAR = "enviar";
  private static final String ESQUI = "esqui";
  private static final String BUTPANEL = "buttonPanel";

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testSimpleAnswerAndStarBar() throws InterruptedException{
	  
	driver.get(baseUrl + "/UBUassistant/");
    driver.findElement(By.id("pinguino")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    
    /***/
    driver.switchTo().frame(driver.findElement(By.id("ubuassistantFrame")));
    /***/
    
    driver.findElement(By.id(INPUT)).clear();
    driver.findElement(By.id(INPUT)).sendKeys(ESQUI);
    driver.findElement(By.id(ENVIAR)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(isElementPresent(By.linkText("http://www.ubu.es/deportes")));
    driver.findElement(By.xpath("//form[@id='starForm']/div/label[5]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Voto guardado con éxito. Su voto ha sido 1", driver.findElement(By.id(BUTPANEL)).getText());
    driver.findElement(By.id(INPUT)).clear();
    driver.findElement(By.id(INPUT)).sendKeys(ESQUI);
    driver.findElement(By.id(ENVIAR)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.xpath("//form[@id='starForm']/div/label[4]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Voto guardado con éxito. Su voto ha sido 2", driver.findElement(By.id(BUTPANEL)).getText());
    driver.findElement(By.id(INPUT)).clear();
    driver.findElement(By.id(INPUT)).sendKeys(ESQUI);
    driver.findElement(By.id(ENVIAR)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.xpath("//form[@id='starForm']/div/label[3]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Voto guardado con éxito. Su voto ha sido 3", driver.findElement(By.id(BUTPANEL)).getText());
    driver.findElement(By.id(INPUT)).clear();
    driver.findElement(By.id(INPUT)).sendKeys(ESQUI);
    driver.findElement(By.id(ENVIAR)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.xpath("//form[@id='starForm']/div/label[2]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Voto guardado con éxito. Su voto ha sido 4", driver.findElement(By.id(BUTPANEL)).getText());
    driver.findElement(By.id(INPUT)).clear();
    driver.findElement(By.id(INPUT)).sendKeys(ESQUI);
    driver.findElement(By.id(ENVIAR)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.cssSelector("label[title=\"text\"]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Voto guardado con éxito. Su voto ha sido 5", driver.findElement(By.id(BUTPANEL)).getText());
  }

  @After
  @Override
  public void tearDown() {
    super.tearDown();
  }
}
