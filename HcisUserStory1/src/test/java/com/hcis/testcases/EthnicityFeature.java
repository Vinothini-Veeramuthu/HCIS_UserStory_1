package com.hcis.testcases;

import org.openqa.selenium.chrome.ChromeOptions;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebDriver;

public class EthnicityFeature {

	public static void main(String[] args) throws InterruptedException, AWTException {
		
		System.setProperty("webdriver.chrome.driver","C:\\Users\\v.vinothini\\eclipse-workspace\\HcisUserStory1\\src\\test\\resources\\Drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("http://4.188.249.255/hphis/edoctor/principal.jsp");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
				
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
				
                //To Verify if Ethnicity field is displayed in Patient Registration screen
				WebElement findElementEthnicity = driver.findElement(By.xpath("//label[normalize-space()='Ethnicity']"));
				boolean Ethnicitydisplayed = findElementEthnicity.isDisplayed();
				if(Ethnicitydisplayed)
				{
					System.out.println("Ethnicity Label is Displayed In Patient Registration Form");
				}
				else
				{
					System.out.println("Ethicity Label is Not Dipslayed In Patient Registration Form");
				}
				
				// Check if the Ethnicity is empty initially
		        WebElement EthnicityField = driver.findElement(By.xpath("//input[@id='patientEthnicity']"));
				
				// Retrieve the value of the text field
		        String fieldValue = EthnicityField.getAttribute("value");

		        if (fieldValue.isEmpty()) {
		            System.out.println("Ethnicity field is empty.");
		        } else {
		            System.out.println("Ethnicity Field is not empty. Value: " + fieldValue);
		        }
				
				
				//To Verify if Race Element is Enabled
				boolean enabled = EthnicityField.isEnabled();
				if(enabled)
				{
					System.out.println("Ethnicity Field is Enabled In Patient Registration Form");
				}
				else
				{
					System.out.println("Ethnicity Field is Not Enabled In Patient Registration Form");
				}
				
				//To Verify if Race Element is selected when registering a new patient
				boolean selected = EthnicityField.isSelected();
				if(selected)
				{
					System.out.println("Ethnicity is Selected In Patient Registration Form");
				}
				else
				{
					System.out.println("Race Label is Not Selected In Patient Registration Form");
				} 
				
				//To Verify if Selecting Ethnicity without selecting race is throwing appropriate error message
				driver.findElement(By.xpath("//img[@title='Ethnicity']")).click();
				String EthnicityAlert = driver.switchTo().alert().getText();
				System.out.println(EthnicityAlert);
				driver.switchTo().alert().accept();
				driver.findElement(By.xpath("//img[@title='Race']")).click();
				
				List<String> listHandles3 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(listHandles3.get(2));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//input[@id='inputBusqueda']")).sendKeys("1",Keys.ENTER);
				driver.switchTo().window(listHandles2.get(1));
				driver.findElement(By.xpath("//img[@title='Ethnicity']")).click();
				List<String> listHandles4 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(listHandles4.get(2));
				Thread.sleep(1000);
				driver.findElement(By.xpath("//input[@id='inputBusqueda']")).sendKeys("2",Keys.ENTER);
				
				
				

	}

};
