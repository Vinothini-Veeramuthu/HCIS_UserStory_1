package com.hcis.testcases;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;import org.openqa.selenium.Keys;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.mongodb.MapReduceCommand.OutputType;

import org.openqa.selenium.WebDriver;
public class RaceFeature {

	public static void main(String[] args) throws InterruptedException, AWTException {
		
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
		
		//To check if Race Label is Present in Patient Registration Form
		String pageTitle = driver.getTitle();
        System.out.println("Page Title: " + pageTitle);
		WebElement findElementRace = driver.findElement(By.xpath("//label[normalize-space()='Race']"));
		boolean displayed = findElementRace.isDisplayed();
		if(displayed)
		{
			System.out.println("Race Label is Displayed In Patient Registration Form");
		}
		else
		{	
			System.out.println("Race Label is Not Dipslayed In Patient Registration Form");
		}
		
		
		// Check if the RaceField is empty
        WebElement textField = driver.findElement(By.id("patientRace"));
		
		// Retrieve the value of the text field
        String fieldValue = textField.getAttribute("value");

        if (fieldValue.isEmpty()) {
            System.out.println("The text field is empty.");
        } else {
            System.out.println("The text field is not empty. Value: " + fieldValue);
        }
		
		
		//To Verify if Race Element is Enabled
		boolean enabled = findElementRace.isEnabled();
		if(enabled)
		{
			System.out.println("Race Label is Enabled In Patient Registration Form");
		}
		else
		{
			System.out.println("Race Label is Not Enabled In Patient Registration Form");
		}
		
		
		//User should be able to manually enter in the Race Field
		driver.findElement(By.xpath("//input[@id='patientRace']")).sendKeys("Asian",Keys.ENTER);
		
		//Clicking delete button should delete the selected option
		driver.findElement(By.xpath("(//img[@title='Clear'])[1]")).click();
		
		driver.findElement(By.xpath("//img[@title='Race']")).click();
		List<String> listHandles3 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(listHandles3.get(2));
		try {
            // Locate the table element 
            WebElement table = driver.findElement(By.id("tablaInterior"));
            
            // Get all the rows within the table
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            
            int numberOfRowsExcludingHeader = rows.size() - 1;

            // Print the number of rows
            //System.out.println("Number of rows in the table: " + rows.size());
            System.out.println("Number of rows in the table: " + numberOfRowsExcludingHeader);
            
            
            for (WebElement row : rows) {
               List<WebElement> cells = row.findElements(By.tagName("td"));
                for (WebElement cell : cells) {
                    String cellText = cell.getText();
                    System.out.print(cellText + "\t"); // Print cell value with tab separator
                }
                System.out.println(); // Move to the next row
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the WebDriver session
            driver.quit();
            
          //To check if user is able to select race value using code
   		 driver.findElement(By.xpath("//img[@title='Race']")).click();
   		 List<String> listHandles4 = new ArrayList<String>(driver.getWindowHandles());
   		 driver.switchTo().window(listHandles3.get(2));
   		 Thread.sleep(100);
   		 WebElement forCode = driver.findElement(By.xpath("(//input[@id='busCod'])[1]"));
   		 forCode.click();
   		 driver.findElement(By.xpath("(//input[@id='inputBusqueda'])[1]")).sendKeys("2",Keys.ENTER);

        }
    }
};



