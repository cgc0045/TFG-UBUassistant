package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class AdminModifyCase extends AbstractClassTest{


  
  private static final String EDITCASOS = "Editor de Casos";
  private static final String KEYWORD1 = "keyWord1";
  private static final String CATEGORIA = "categoria";
  private static final String RESPUESTA = "respuesta";
  private static final String ACEPT = "input.aceptButton";
  private static final String SCROLL = "window.scrollTo(0, document.body.scrollHeight);";
  private static final String BUTTON = "(//input[@id='button'])[";
  private static final String CASOPRUEBAEDIT = "CasoPruebaEdit";
  private static final String CATEDIT = "CategoriaEditada";
  private static final String TABLA2 = "//table[@id='tabla']/tbody/tr[";
  private static final String MSG = "*Debe rellenar al menos la palabra clave 1, la categoría y la respuesta.";
  private static final String ERROR = "error";

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void testAdminModifyCase() throws InterruptedException{
    driver.get(baseUrl + "/UBUassistant/index.jsp");
    driver.findElement(By.cssSelector("input.adminLink")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("user")).clear();
    driver.findElement(By.id("user")).sendKeys("admin1@ubu.es");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("admin1");
    driver.findElement(By.cssSelector("button")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.linkText(EDITCASOS)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("add")).click();
    driver.findElement(By.id(KEYWORD1)).clear();
    driver.findElement(By.id(KEYWORD1)).sendKeys("CasoPrueba");
    driver.findElement(By.id(CATEGORIA)).clear();
    driver.findElement(By.id(CATEGORIA)).sendKeys("CategoriaPrueba");
    driver.findElement(By.id(RESPUESTA)).clear();
    driver.findElement(By.id(RESPUESTA)).sendKeys("RespuestaPrueba");
    driver.findElement(By.cssSelector(ACEPT)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.linkText(EDITCASOS)).click();
    driver.findElement(By.id("edit")).click();
    
    int rowCount=driver.findElements(By.xpath("//table[@id='tabla']/tbody/tr")).size();
    
    TimeUnit.MILLISECONDS.sleep(2000);
    JavascriptExecutor jse = (JavascriptExecutor)driver;
    jse.executeScript(SCROLL);
    TimeUnit.MILLISECONDS.sleep(2000);
    
    driver.findElement(By.xpath(BUTTON+((rowCount*2)-1)+"]")).click();
    driver.findElement(By.id(KEYWORD1)).clear();
    driver.findElement(By.id(KEYWORD1)).sendKeys(CASOPRUEBAEDIT);
    driver.findElement(By.id("keyWord2")).clear();
    driver.findElement(By.id("keyWord2")).sendKeys("Caso2Edit");
    driver.findElement(By.id("keyWord5")).clear();
    driver.findElement(By.id("keyWord5")).sendKeys("Caso5Edit");
    driver.findElement(By.id(CATEGORIA)).clear();
    driver.findElement(By.id(CATEGORIA)).sendKeys(CATEDIT);
    driver.findElement(By.id(RESPUESTA)).clear();
    driver.findElement(By.id(RESPUESTA)).sendKeys("RespuestaEditada");
    driver.findElement(By.cssSelector(ACEPT)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertEquals(CASOPRUEBAEDIT, driver.findElement(By.xpath(TABLA2+rowCount+"]/td[2]")).getText());
    assertEquals("Caso2Edit", driver.findElement(By.xpath(TABLA2+rowCount+"]/td[3]")).getText());
    assertEquals("Caso5Edit", driver.findElement(By.xpath(TABLA2+rowCount+"]/td[6]")).getText());
    assertEquals(CATEDIT, driver.findElement(By.xpath(TABLA2+rowCount+"]/td[7]")).getText());
    assertEquals("RespuestaEditada", driver.findElement(By.xpath(TABLA2+rowCount+"]/td[8]")).getText());
    
    TimeUnit.MILLISECONDS.sleep(2000);
    jse.executeScript(SCROLL);
    TimeUnit.MILLISECONDS.sleep(2000);
    
    driver.findElement(By.xpath(BUTTON+((rowCount*2)-1)+"]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id(KEYWORD1)).clear();
    driver.findElement(By.id(KEYWORD1)).sendKeys("");
    driver.findElement(By.cssSelector(ACEPT)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(driver.findElement(By.id(ERROR)).getText().equals(MSG));
    driver.findElement(By.id(KEYWORD1)).clear();
    driver.findElement(By.id(KEYWORD1)).sendKeys(CASOPRUEBAEDIT);
    driver.findElement(By.id(CATEGORIA)).clear();
    driver.findElement(By.id(CATEGORIA)).sendKeys("");
    driver.findElement(By.cssSelector(ACEPT)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(driver.findElement(By.id(ERROR)).getText().equals(MSG));
    driver.findElement(By.id(CATEGORIA)).clear();
    driver.findElement(By.id(CATEGORIA)).sendKeys(CATEDIT);
    driver.findElement(By.id(RESPUESTA)).clear();
    driver.findElement(By.id(RESPUESTA)).sendKeys("");
    driver.findElement(By.cssSelector(ACEPT)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(driver.findElement(By.id(ERROR)).getText().equals(MSG));
    driver.findElement(By.linkText(EDITCASOS)).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    driver.findElement(By.id("edit")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    
    TimeUnit.MILLISECONDS.sleep(2000);
    jse.executeScript(SCROLL);
    TimeUnit.MILLISECONDS.sleep(2000);
    
    driver.findElement(By.xpath(BUTTON+(rowCount*2)+"]")).click();
    TimeUnit.MILLISECONDS.sleep(2000);
    assertTrue(closeAlertAndGetItsText().matches("^¿Está seguro de que desea borrar el caso[\\s\\S]$"));
    TimeUnit.MILLISECONDS.sleep(5000);
  }

  @After
  @Override
  public void tearDown() {
    super.tearDown();
  }

  
}
