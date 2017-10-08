package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//import org.openqa.selenium.htmlunit.BrowserVersion; 
import com.gargoylesoftware.htmlunit.BrowserVersion; 
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Keys;
import org.apache.log4j.*;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.PropertyConfigurator;
//import org.slf4j.log4j.Logger;
//import org.slf4j.log4j.LoggerFactory;

public class UITest {
  private WebDriver driver;
  private String baseUrl;
  private String actionUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  //Object initialization for log
  private static Logger Log = Logger.getLogger(UITest.class.getName());

  @Before                                              
  public void setUp() throws Exception {
    PropertyConfigurator.configure("log4j.properties");
    //Log.setLevel(Level.WARN);
    //Log.setLevel(Level.ALL);
    Log.setLevel(Level.DEBUG);
    ////loading log4j.xml file 
    //DOMConfigurator.configure("log4j.xml"); 
    //log4j properties file

    Log.debug("setUp()+");
    //System.setProperty("webdriver.gecko.driver", "C:\\geckodriver\\geckodriver-64.exe"); //глюки в нажатии ссылки
    //System.setProperty("webdriver.gecko.driver", "C:\\geckodriver\\geckodriver-32.exe"); //глюки в диалоговом окне Выбор файла
    if(System.getProperty("webdriver.gecko.driver")==null){
     ////System.setProperty("webdriver.ie.driver", "C:\\IEDriver\\IEDriverServer-64.exe");
     if(System.getProperty("webdriver.ie.driver")==null){
      //System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
      if(System.getProperty("webdriver.chrome.driver")==null){
       //HtmlUnitDriver 
       //driver = new HtmlUnitDriver(true); //true - ena JS
       //driver = new HtmlUnitDriver(DesiredCapabilities.internetExplorer());
       //driver = new HtmlUnitDriver(BrowserVersion.CHROME);
       driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER);
       //driver.setJavascriptEnabled(true);
       Log.debug("setUp()-HtmlUnit");
      }else{
       driver = new ChromeDriver();
       Log.debug("setUp()-Chrome");
      }
     }else{
      driver = new InternetExplorerDriver();
      Log.debug("setUp()-InternetExplorer");
     }
    }else{
     driver = new FirefoxDriver();
     Log.debug("setUp()-Firefox");
    }

    if(driver==null){
     Log.error("setUp()-null");
     System.exit(1);
    }

    //baseUrl = "http://www.tender.pro";
    baseUrl = System.getProperty("baseUrl");
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    Log.debug("setUp().");
  }

  @Test
  public void testUI() throws Exception {

   Log.info("0. Подготовка и проверка наличия в меню пункта 'Прайс-лист'");

    Log.info("0.1 Зайти на сайт (по горячим следам, без авторизации)");
    //actionUrl = "/cabinet/info?sid=1234567&companyid=654321";
    actionUrl = System.getProperty("actionUrl");
    driver.get(baseUrl + actionUrl);
    Log.debug(".перейти на вкладку 'Прайс-лист'");
    driver.findElement(By.linkText("Прайс-лист")).click();
    try {
     assertEquals("name", driver.findElement(By.xpath(".//*[@id='showPriceByState']/div[2]/div[1]/div[1]")).getAttribute("class"));
     Log.debug("0. OK (открылась вкладка, продолжаем тест)");
    } catch (Error e) {
     verificationErrors.append(e.toString());
     Log.error("0. Не открылась вкладка, завершаем тест");
     return;
    }

    Log.info("0.2 Отобразить старый прайс-лист");
    new Select(driver.findElement(By.name("pricetype"))).selectByVisibleText("все");
    new Select(driver.findElement(By.name("faceid"))).selectByVisibleText("все");
    driver.findElement(By.cssSelector("div.btnHolder > button[type=\"submit\"]")).click();
    Log.debug(".определить наличие таблицы");
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS); //MILLISECONDS);
    boolean exists = driver.findElements( By.xpath(".//*[@id='Table']/thead/tr/th[4]/a/img") ).size() != 0;
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    Log.info("0.3 Очистить прайс-лист, если обнаружена таблица");
    if(exists){
     Log.debug(".(таблица найдена)");
     Log.debug(".нажать [X] в первой строке таблицы для удаления старого прайс-листа");
     driver.findElement(By.xpath(".//*[@id='Table']/thead/tr/th[4]/a/img")).click();
     Log.debug(".[если еще не отключено флажком] дополнительно всплывает модальное окно 'Вы собираетесь удалить прайс-лист целиком. Продолжить?'");
     Thread.sleep(1000); //Wait to load model pop-up
     //driver.switchTo().activeElement();
     //System.out.println(driver.findElements(By.xpath("//button[contains(text(),'OK')]")).size());
     //driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
     try {
      // Check the presence of alert
      Alert alert1=driver.switchTo().alert();
      // if present consume the alert
      alert1.accept();
     } catch (NoAlertPresentException ex) {
      // Alert not present
      //ex.printStackTrace();
     }
     Log.debug(".[если еще не отключено флажком] дополнительно всплывает модальное окно 'Внимание ...! Продолжить?'");
     Thread.sleep(1000); //Wait to load model pop-up
     //driver.switchTo().activeElement();
     //driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
     try {
      // Check the presence of alert
      Alert alert2=driver.switchTo().alert();
      // if present consume the alert
      alert2.accept();
     } catch (NoAlertPresentException ex) {
      // Alert not present
      //ex.printStackTrace();
     }
     Log.debug(".[если еще не отключено флажком] дополнительно всплывает модальное окно 'Вы уверены?'");
     Thread.sleep(1000); //Wait to load model pop-up
     //driver.switchTo().activeElement();
     //driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
     try {
      // Check the presence of alert
      Alert alert3=driver.switchTo().alert();
      // if present consume the alert
      alert3.accept();
     } catch (NoAlertPresentException ex) {
      // Alert not present
      //ex.printStackTrace();
     }
     Log.debug(".(вернулись)");
    }else{
     Log.debug(".(таблицы нет)");
    }

    Log.info("0.4 Проверить, что теперь таблица не показывается");
    Thread.sleep(1000); //Wait to load model pop-up
    Log.debug("0.4 "+driver.getCurrentUrl());
    new Select(driver.findElement(By.name("pricetype"))).selectByVisibleText("все");
    new Select(driver.findElement(By.name("faceid"))).selectByVisibleText("все");
    driver.findElement(By.cssSelector("div.btnHolder > button[type=\"submit\"]")).click();
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS); //MILLISECONDS);
    exists = driver.findElements( By.xpath(".//*[@id='Table']/thead/tr/th[4]/a/img") ).size() != 0;
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    if(exists){
     Log.error("0.4 Показывается таблица - не удалось удалить старый прайс-лист, завершаем тест");
     return;
    }else{
     Log.debug("0.4 OK (удалось удалить старый прайс-лист)");
    }

    Log.info("0.5 Заполнить прайс-лист...");
    //driver.findElement(By.linkText("Импорт прайс-листа из xls файла")).click();
    if(driver.findElements( By.xpath(".//*[@id='showPriceByState']/div[2]/div[2]/a[3]") ).size() == 0){
     Log.error("0.5 Нет линка 'Импорт из xls файла', завершаем тест");
     return;
    }
    driver.findElement(By.xpath(".//*[@id='showPriceByState']/div[2]/div[2]/a[3]")).click();
     //Импорт прайс-листа из xls:
     //crash HtmlUnit: driver.findElement(By.id("file")).clear();
     //driver.findElement(By.id("file")).sendKeys("C:\\Users\\shiv\\Downloads\\price206383.xls");
     Thread.sleep(1000); //Wait to load page
     Log.debug(driver.getCurrentUrl());
     if(driver.findElements( By.xpath(".//*[@id='file']") ).size() == 0){
      Log.error("0.5 Не открылось окно 'Загрузка файла позиций в прайс-лист', завершаем тест");
      return;
     }
//     //driver.findElement(By.xpath(".//*[@id='file']")).sendKeys("C:\\Users\\shiv\\Downloads\\price206383.xls");
//     Thread.sleep(1000); //Wait to load model pop-up
//     Log.debug("0.3.1 "+driver.getCurrentUrl());
//     driver.findElement(By.xpath(".//*[@id='file']")).click();
//     //driver.findElement(By.xpath(".//*[@id='file']")).sendKeys(Keys.RETURN);
//     Thread.sleep(1000); //Wait to load model pop-up
//     Log.debug("0.3.2 "+driver.getCurrentUrl());
     driver.findElement(By.xpath(".//*[@id='file']")).sendKeys(System.getProperty("pricePath"));
     Thread.sleep(1000); //Wait to load model pop-up
     Log.debug("0.5.2 "+driver.getCurrentUrl());
     Log.debug("0.5.3 "+driver.findElement(By.xpath(".//*[@id='file']")).getText());
     //+Keys.RETURN);
     //driver.findElement(By.id("send_form")).click();
     Thread.sleep(1000); //Wait to load model pop-up
     Log.debug("0.5.4 "+driver.getCurrentUrl());
     if(driver.findElements( By.xpath(".//*[@id='send_form']") ).size() == 0){
      Log.error("0.5 Не вернулись из окна выбора файла xls, завершаем тест");
      return;
     }
     driver.findElement(By.xpath(".//*[@id='send_form']")).click();
     //после удаления прайса, проверка уже неверна if(exists){
     // //если есть старый прайс-лист, будет ждать нажатия (иначе, вернётся автоматически)
     // driver.findElement(By.xpath(".//*[@id='bcn']")).click();
     //}
     //патч для HtmlUnit(FF3):
     Log.debug("0.5.5 "+driver.getCurrentUrl());
     if(driver.getCurrentUrl() == "http://www.tender.pro/files/upload.json"){
      Log.debug("не вернулись, еще пытаемся");
     }
     if(driver.findElements( By.name("pricetype") ).size() == 0){
      //если есть старый прайс-лист, будет ждать нажатия (иначе, вернётся автоматически)
      driver.findElement(By.xpath(".//*[@id='bcn']")).click();
      Log.debug(driver.getCurrentUrl());
      if(driver.findElements( By.name("pricetype") ).size() == 0){
       Log.error("0.5 Не вернулись из окна 'Загрузка файла позиций в прайс-лист', завершаем тест");
       return;
      }
     }

   Log.info("1. Проверить, что в таблице есть несколько строк");

    new Select(driver.findElement(By.name("pricetype"))).selectByVisibleText("все");
    new Select(driver.findElement(By.name("faceid"))).selectByVisibleText("все");
    driver.findElement(By.cssSelector("div.btnHolder > button[type=\"submit\"]")).click();
    try {
     assertEquals("2", driver.findElement(By.cssSelector("tr.even > td")).getText());
     Log.debug("1. OK (как минимум, две строки)");
    } catch (Error e) {
     verificationErrors.append(e.toString());
     Log.error("1. Таблица не все строки показывает");
    }

   Log.info("2. Проверить, что в таблице нет недействительных");

    new Select(driver.findElement(By.name("pricetype"))).selectByVisibleText("недействительные");
    driver.findElement(By.cssSelector("div.btnHolder > button[type=\"submit\"]")).click();
    try {
     assertEquals("Список пуст", driver.findElement(By.xpath("//div[@id='Content']/div[2]/div/div/div/div/div/div/div/div[2]/div/div[2]/div/div/div[2]/div[7]")).getText());
     Log.debug("2. OK (Список пуст)");
    } catch (Error e) {
     verificationErrors.append(e.toString());
     Log.error("2. Таблица неправильно показывает недействительные)");
    }

   Log.info("3. Проверить, что в таблице нет назначеного Иванова");

    new Select(driver.findElement(By.name("pricetype"))).selectByVisibleText("все");
    new Select(driver.findElement(By.name("faceid"))).selectByVisibleText("Иванов Иван Иванович");
    driver.findElement(By.cssSelector("div.btnHolder > button[type=\"submit\"]")).click();
    try {
     assertEquals("Список пуст", driver.findElement(By.xpath("//div[@id='Content']/div[2]/div/div/div/div/div/div/div/div[2]/div/div[2]/div/div/div[2]/div[7]")).getText());
     Log.debug("3. OK (Список пуст)");
    } catch (Error e) {
     verificationErrors.append(e.toString());
     Log.error("3. Таблица показывает лишних ответственных)");
    }

   Log.info("4. Проверить отображение ответственного");

    Log.info("4.1 Показать все строки таблицы");
    new Select(driver.findElement(By.name("pricetype"))).selectByVisibleText("все");
    new Select(driver.findElement(By.name("faceid"))).selectByVisibleText("все");
    driver.findElement(By.cssSelector("div.btnHolder > button[type=\"submit\"]")).click();
    try {
     assertEquals("2", driver.findElement(By.cssSelector("tr.even > td")).getText());
     Log.debug("4.1 OK (две строки)");
    } catch (Error e) {
     verificationErrors.append(e.toString());
     Log.error("4.1 Меньше двух строк)");
    }

    Log.info("4.2 Отредактировать строку Услуги таблицы");
    driver.findElement(By.linkText("Услуги консультативные по информационно-компьютерным средствам, программному и информационному обеспечению, обработке данных")).click();
     Log.debug(".строка просмотра услуги");
     driver.findElement(By.linkText("Услуга по созданию сайта \"Сайт визитная карточка\"")).click();
     Log.debug(".карточка редактирования услуги");
     new Select(driver.findElement(By.name("faceid"))).selectByVisibleText("Иванов Иван Иванович");
     Log.debug(".закрыть карточку");
     driver.findElement(By.name("send_form")).click();
     Log.debug(".закрыть строку просмотра");
    driver.findElement(By.xpath(".//*[@id='Content']/div[2]/div/div/div/div/div/div/div/div[2]/div/div[2]/div/div[1]/div[2]/div[8]/div[1]/div/button")).click();

    Log.info("4.3 Проверить, что таблица показывает Иванова");
    new Select(driver.findElement(By.name("pricetype"))).selectByVisibleText("все");
    new Select(driver.findElement(By.name("faceid"))).selectByVisibleText("Иванов Иван Иванович");
    driver.findElement(By.cssSelector("div.btnHolder > button[type=\"submit\"]")).click();
    try {
     assertEquals("1", driver.findElement(By.cssSelector("tr.odd > td")).getText());
     Log.debug("4.3 OK (есть строка)");
    } catch (Error e) {
     verificationErrors.append(e.toString());
     Log.error("4.3 Таблица не показывает ответственных");
    }

   Log.info("5. Проверить отображение недействительного товара");

    Log.info("5.1 Показать все строки таблицы");
    new Select(driver.findElement(By.name("pricetype"))).selectByVisibleText("все");
    new Select(driver.findElement(By.name("faceid"))).selectByVisibleText("все");
    driver.findElement(By.cssSelector("div.btnHolder > button[type=\"submit\"]")).click();
    try {
     assertEquals("2", driver.findElement(By.cssSelector("tr.even > td")).getText());
     Log.debug("5.1 OK (две строки)");
    } catch (Error e) {
     verificationErrors.append(e.toString());
     Log.error("5.1 Меньше двух строк)");
    }

    Log.info("5.2 Отредактировать строку с товаром");
    driver.findElement(By.linkText("Операционные системы общего назначения для персональных компьютеров")).click();
     Log.debug(".строка просмотра товара");
    driver.findElement(By.linkText("Комплект клиентского программного обеспечения рабочей станции ОС Windows2k Prof, с лицензией многооконного интерфейса Experion")).click();
     Log.debug(".карточка редактирования товара");
     driver.findElement(By.xpath(".//*[@id='myForm']/div[4]/div/div[2]/input")).click();
     Log.debug(".закрыть карточку");
     driver.findElement(By.name("send_form")).click();
     Log.debug(".закрыть строку просмотра");
    driver.findElement(By.xpath(".//*[@id='Content']/div[2]/div/div/div/div/div/div/div/div[2]/div/div[2]/div/div[1]/div[2]/div[8]/div[1]/div/button")).click();

    Log.info("5.3 Проверить, что таблица показывает недействительный товар");
    new Select(driver.findElement(By.name("pricetype"))).selectByVisibleText("недействительные");
    new Select(driver.findElement(By.name("faceid"))).selectByVisibleText("все");
    driver.findElement(By.cssSelector("div.btnHolder > button[type=\"submit\"]")).click();
    try {
     assertEquals("1", driver.findElement(By.cssSelector("tr.odd > td")).getText());
     Log.debug("5.3 OK (есть первая строка)");
    } catch (Error e) {
     verificationErrors.append(e.toString());
     Log.error("5.3 Таблица не показывает недействительных");
    }

   Log.info("6. Проверить появление описания ошибки при выборе кривого Ответственного");
    new Select(driver.findElement(By.name("pricetype"))).selectByVisibleText("все");
    driver.findElement(By.name("faceid")).click();
    driver.findElement(By.xpath("//option[6]")).click(); //последний пункт [----] является неполным!
    driver.findElement(By.cssSelector("div.btnHolder > button[type=\"submit\"]")).click();
    try {
     assertThat(driver.findElement(By.xpath(".//*[@id='Content']/div[2]/div/div/div/div/div/div/div/div[2]/div/div[2]/div/div[1]/div[2]")).getText(), containsString("warning"));
     Log.debug("6. OK (есть сообщение с описанием ошибки)");
    } catch (Error e) {
     verificationErrors.append(e.toString());
     Log.error("6. Не показывается описание ошибки");
    }

   Log.info("окончание теста.");
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
