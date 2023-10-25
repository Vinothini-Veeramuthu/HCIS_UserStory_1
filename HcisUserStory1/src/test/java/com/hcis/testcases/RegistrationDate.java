package com.hcis.testcases;

import org.openqa.selenium.chrome.ChromeOptions;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
public class RegistrationDate {

	public static void main(String[] args) throws AWTException, InterruptedException {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\v.vinothini\\eclipse-workspace\\HcisUserStory1\\src\\test\\resources\\Drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
		WebDriver driver = new ChromeDriver(options);
		driver.get("http://4.188.249.255/hphis/edoctor/principal.jsp");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().window().maximize();
		
		 //Enter UserName
		driver.findElement(By.name("J_USERNAME")).sendKeys("admin");
				
		//Enter Password
		driver.findElement(By.id("J_PASSWORD")).sendKeys("hcis4demo");
		Thread.sleep(2000);
				
		//Select Centre
		WebElement centre = driver.findElement(By.xpath("//select[@name='Conexion']"));
		Select selectCentre = new Select(centre);
		selectCentre.selectByVisibleText("General Hospital");
				
		//Select Unit
		WebElement unit = driver.findElement(By.xpath("//select[@id='unidad']"));
		Select selectUnit = new Select(unit);
		selectUnit.selectByVisibleText("Emergency Department");
		
		//Click Login Button
		driver.findElement(By.id("entrarButton")).click();
		
		//ZoomIn to 80%
		Robot robot = new Robot();
		for (int i = 0; i < 2; i++) 
		{
			
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_CONTROL);	
		}
		
		driver.switchTo().frame("menu");
		driver.findElement(By.xpath("//span[normalize-space()='Patients search']")).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("contenido");
		driver.findElement(By.xpath("//img[@title='New Patient']")).click();
		List<String> listHandles2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(listHandles2.get(1));
		Thread.sleep(1000);
		
		//To Verify if Registration Date field is displayed in the screen
		WebElement RegDateLabel = driver.findElement(By.xpath("//label[normalize-space()='Registration Date']"));
		boolean displayedRegDate = RegDateLabel.isDisplayed();
		if(displayedRegDate)
		{
			System.out.println("RegDate is Displayed in Patient Reg Form");
		}
		else
		{
			System.out.println("RegDate is Not Displayed in Patient Reg Form");
		} 
		
		//Verifying if Registration date field is pre-populated with current date
        WebElement field = driver.findElement(By.id("patientRegDate"));
        
        // Retrieve the default value of the field
        String defaultValue = field.getAttribute("value");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        String formattedDate =  dateFormat.format(currentDate);
        System.out.println("Current Date: " + formattedDate);
        System.out.println("Default Date: " + defaultValue);
        if(defaultValue.contentEquals(formattedDate))
        {
        	System.out.println("Default value is currentDate");
        }
        else
        {
        	System.err.println("Default value is not currentDate");
        }
        
        //To Verify if future date is allowed
        driver.findElement(By.xpath("//img[@id='imgborrar_patientRegDate']")).click();
        driver.findElement(By.xpath("//input[@id='patientRegDate']")).sendKeys("28/09/2023",Keys.ENTER);
        String alertText = driver.switchTo().alert().getText();
        System.out.println(alertText);
        driver.switchTo().alert().accept();
        
        
        //To Verify if pre-date is allowed
        driver.findElement(By.xpath("//input[@id='patientRegDate']")).sendKeys("10/09/2023",Keys.ENTER);
        String preDateAlert = driver.switchTo().alert().getText();
        System.out.println(preDateAlert);
        driver.switchTo().alert().accept();  
        
        
        //To Verify DOB is same as Registration Date
        WebElement dob = driver.findElement(By.xpath("//input[@id='fechaNacimiento']"));
        dob.sendKeys(formattedDate);
        String dobValue= dob.getAttribute("value");
        if(dobValue.equals(defaultValue))
        {
        	System.out.println("Dob can be same as reg date");
        }
        else
        {
        	System.out.println("Dob should be changed ");
        } 
        
        
        //To Verify if future is allowed for DOB
        WebElement dob2 = driver.findElement(By.xpath("//input[@id='fechaNacimiento']"));
        dob2.sendKeys("10/10/2023",Keys.ENTER);
        String futureDateAlert = driver.switchTo().alert().getText();
        System.out.println(futureDateAlert);
        driver.switchTo().alert().accept(); 
        
        
        //To Verify if calendar picker is clickable
        WebElement findElement = driver.findElement(By.xpath("//img[@id='imgpatientRegDate']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", findElement);
        
        //To Verify if user is allowed to select pre-date from calendar-picker      
        driver.switchTo().frame("iframeDatos");
        driver.findElement(By.xpath("//body[1]/form[1]/header[1]/a[1]/img[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("a[id='txtMenos'] img[class='size-12']")).click();
        driver.findElement(By.xpath("//a[normalize-space()='15']")).click(); 
        
        //To Verify if user is allowed to select future-date from calendar-picker  
        WebElement findElement1 = driver.findElement(By.xpath("//img[@id='imgpatientRegDate']"));
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", findElement1);
        driver.switchTo().frame("iframeDatos");
        driver.findElement(By.xpath("//body[1]/form[1]/header[1]/a[2]/img[1]")).click();
        Thread.sleep(100);
        driver.findElement(By.xpath("(//img[@class='size-12'])[4]")).click();
        driver.findElement(By.xpath("//a[normalize-space()='25']")).click();
        String text = driver.switchTo().alert().getText();
        System.out.println(text); 
        
        
        //To Verify if appropriate error message is displayed when invalid date format is given
        driver.findElement(By.xpath("//input[@id='patientRegDate']")).clear();
        driver.findElement(By.xpath("//input[@id='patientRegDate']")).sendKeys("30/02/2023",Keys.ENTER);
        String DateAlertText = driver.switchTo().alert().getText();
        System.out.println(DateAlertText); 
        
        //To verify if reg date field is accepting characters and appropriate msg is displayed
        driver.findElement(By.xpath("//input[@id='patientRegDate']")).clear();
        driver.findElement(By.xpath("//input[@id='patientRegDate']")).sendKeys("u",Keys.ENTER);
        String charAlertText = driver.switchTo().alert().getText();
        System.out.println(charAlertText);
       

}      

};
