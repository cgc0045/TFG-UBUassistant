package selenium;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class AdminAddCase extends AbstractClassTest {
  
  private static final String ACEPT = "input.aceptButton";
  private static final String ERROR = "error";
  private static final String MSG = "*Debe rellenar al menos la palabra clave 1, la categoría y la respuesta.";
  private static final String KEYWORD2 = "keyWord2";
  private static final String CASOPRUEBA = "CasoPrueba";
  private static final String CATEGORIA = "categoria";
  private static final String RESPUESTA = "respuesta";

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testAdminAddCase() throws Exception {
    driver.get(baseUrl + "/UBUassistant/index.jsp");
    driver.findElement(By.cssSelector("input.adminLink")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys("admin1@ubu.es");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("admin1");
    driver.findElement(By.cssSelector("button")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.linkText("Editor de Casos")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("add")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals("Formulario para añadir un caso a la base de datos.", driver.findElement(By.id("subtitle")).getText());
    driver.findElement(By.cssSelector(ACEPT)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(driver.findElement(By.id(ERROR)).getText().equals(MSG));
    driver.findElement(By.id(KEYWORD2)).clear();
    driver.findElement(By.id(KEYWORD2)).sendKeys(CASOPRUEBA);
    driver.findElement(By.id(CATEGORIA)).clear();
    driver.findElement(By.id(CATEGORIA)).sendKeys("CategoriaPrueba");
    driver.findElement(By.cssSelector(ACEPT)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(driver.findElement(By.id(ERROR)).getText().equals(MSG));
    driver.findElement(By.id(RESPUESTA)).clear();
    driver.findElement(By.id(RESPUESTA)).sendKeys("RespuestaPrueba");
    driver.findElement(By.cssSelector(ACEPT)).click();
    driver.findElement(By.id("keyWord1")).clear();
    driver.findElement(By.id("keyWord1")).sendKeys(CASOPRUEBA);
    driver.findElement(By.id(KEYWORD2)).clear();
    driver.findElement(By.id(KEYWORD2)).sendKeys("");
    driver.findElement(By.id(RESPUESTA)).clear();
    driver.findElement(By.id(RESPUESTA)).sendKeys("");
    driver.findElement(By.cssSelector(ACEPT)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id(RESPUESTA)).clear();
    driver.findElement(By.id(RESPUESTA)).sendKeys("RespuestaPrueba");
    driver.findElement(By.id(CATEGORIA)).clear();
    driver.findElement(By.id(CATEGORIA)).sendKeys("");
    driver.findElement(By.cssSelector(ACEPT)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(driver.findElement(By.id(ERROR)).getText().equals(MSG));
    driver.findElement(By.id(CATEGORIA)).clear();
    driver.findElement(By.id(CATEGORIA)).sendKeys("CategoriaPrueba");
    driver.findElement(By.cssSelector(ACEPT)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.linkText("Editor de Casos")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("edit")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    int rowCount=driver.findElements(By.xpath("//table[@id='tabla']/tbody/tr")).size();
    assertEquals(CASOPRUEBA, driver.findElement(By.xpath("//table[@id='tabla']/tbody/tr["+rowCount+"]/td[2]")).getText());
    
    TimeUnit.MILLISECONDS.sleep(2000);
    JavascriptExecutor jse = (JavascriptExecutor)driver;
    jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    TimeUnit.MILLISECONDS.sleep(2000);
    
    driver.findElement(By.xpath("(//input[@id='button'])["+(rowCount*2)+"]")).click();
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
