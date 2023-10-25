package com.hcis.testcases;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.Logger;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import java.util.*;


public class ExcelDataWebTableData {

	@Test (priority=1)
	public static HSSFSheet openExcelFile(String filePath, String sheetName) {
		try {
			File fileLocation = new File(filePath);
			FileInputStream fis = new FileInputStream(fileLocation);
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			return wb.getSheet(sheetName);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	 @Test (priority=2)
		    public static void checkFileExistence(String filePath) {
		        File downloadedFile = new File(filePath);
		        if (downloadedFile.exists()) {
		        	System.out.println("File has been downloaded successfully.");
		        } else {
		        	System.out.println("File download failed.");

		        }
		    }

		   @Test (priority=3)
		    public static void checkColumnsInSheet(HSSFSheet sheet, String[] columnsToCheck) {
		        if (sheet != null) {
		            for (String columnName : columnsToCheck) {
		                boolean columnFound = false;
		                Row firstRow = sheet.getRow(9);
		                if (firstRow != null) {
		                    for (int i = 0; i < firstRow.getLastCellNum(); i++) {
		                        Cell cell = firstRow.getCell(i);
		                        if (cell != null && cell.getStringCellValue().equalsIgnoreCase(columnName)) {
		                            columnFound = true;
		                            break;
		                        }
		                    }
		                }

		                if (columnFound) {
		                	System.out.println("Column '" + columnName + "' is present in the sheet.");
		                } else {		               
		                    System.out.println("Column '" + columnName + "' is not present in the sheet.");
		                }
		            }
		        } else {
		        	System.out.println("Sheet not found.");          
		        }
		    }  

	@Test (priority=4)
	public static MultiValuedMap<String, String> compareRows(HSSFSheet sheet, int keyRowIndex, int valueRowIndex) {

		MultiValuedMap<String, String> rowComparison = new ArrayListValuedHashMap<>();

		Row keyRow = sheet.getRow(keyRowIndex);      
		Row valueRow = sheet.getRow(valueRowIndex);    

		if (keyRow != null && valueRow != null) {
			int maxColumns = Math.max(keyRow.getLastCellNum(), valueRow.getLastCellNum());

			for (int columnIndex = 0; columnIndex < maxColumns; columnIndex++) {
				Cell keyCell = keyRow.getCell(columnIndex);
				Cell valueCell = valueRow.getCell(columnIndex);

				String keyCellValue = (keyCell != null) ? keyCell.toString() : "";
				String valueCellValue = (valueCell != null && !valueCell.toString().trim().isEmpty()) ? valueCell.toString() : "Cell is empty";

				if(keyCellValue.isEmpty())
				{
					continue;
				}
				rowComparison.put(keyCellValue, valueCellValue);

			}

			for (Map.Entry<String, String> entry : rowComparison.entries()) {
				System.out.println("Column: " + entry.getKey() + ", Value: " + entry.getValue());
			} 

		}
		else {
			System.out.println("One or both of the specified rows not found.");
		}
		return rowComparison;
	} 

	@Test(priority=5)
	public static MultiValuedMap<String, String> getTableData(WebDriver driver, WebElement cols, WebElement rows) {


		MultiValuedMap<String, String> tableData = new ArrayListValuedHashMap<>();

		List<WebElement> headerCells = cols.findElements(By.tagName("th"));
		List<WebElement> dataCells = rows.findElements(By.tagName("td"));

		int cellCount = Math.min(headerCells.size(), dataCells.size());

		for (int i = 0; i < cellCount; i++) {
			WebElement headerCell = headerCells.get(i);
			WebElement dataCell = dataCells.get(i);

			String headerText = headerCell.getText();
			String dataText = dataCell.getText();
			if(headerText.isEmpty())
			{
				continue;
			}
			tableData.put(headerText, dataText);

		}

		for (Map.Entry<String, String> entry : tableData.entries()) {
			System.out.println("Column: " + entry.getKey() + ", Value: " + entry.getValue());
		}
		return tableData;
	}  
	
	
	

		@Test (priority=6)
		    public static void compareMultipleKeyValues(MultiValuedMap<String, String> map1, MultiValuedMap<String, String> map2, List<String> keysToCompare) {
	            for (String key : keysToCompare) {
	                Collection<String> value1 = map1.get(key);
	                Collection<String> value2 = map2.get(key);

	                if (value1 != null && value2 != null) {
	                    if (value1.equals(value2)) {
	                        System.out.println("Values for key '" + key + "' are equal.");
	                    } else {
	                        System.out.println("Values for key '" + key + "' are not equal.");
	                    }
	                } else {
	                    System.out.println("Key '" + key + "' not found in one of the maps.");
	                }
	            }
	        }
		
		@Test(priority=7)
		public static MultiValuedMap<String, String> getTableData(WebDriver driver, WebElement cols, WebElement rows, List<String> columnsToCheck1) {
		    MultiValuedMap<String, String> tableData = new ArrayListValuedHashMap<>();
		    
		    List<WebElement> headerCells = cols.findElements(By.tagName("th"));
		    List<WebElement> dataCells = rows.findElements(By.tagName("td"));
		    
		    for (int i = 0; i < headerCells.size(); i++) {
		        WebElement headerCell = headerCells.get(i);
		        String headerText = headerCell.getText();
		        
		        if (columnsToCheck1.contains(headerText)) {
		        	System.out.println( headerText  +  " is present" );
		            WebElement dataCell = dataCells.get(i);
		            String dataText = dataCell.getText();
		            if (!headerText.isEmpty()) {
		                tableData.put(headerText, dataText);
		            }
		        }
		    }
		    
		    for (Map.Entry<String, String> entry : tableData.entries()) {
		        System.out.println("Column: " + entry.getKey() + ", Value: " + entry.getValue());
		    }
		    
		    return tableData;
		}

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

		driver.findElement(By.xpath("//input[@id='nombre']")).sendKeys("demotest3");
		driver.findElement(By.xpath("//button[@id='imgBuscar']")).click();
		Thread.sleep(100);
		WebElement table = driver.findElement(By.xpath("//table[@class='ui-jqgrid-htable']"));
		driver.findElement(By.xpath("//img[@title='Export To Excel']")).click();

		String filePath = "C:\\Users\\v.vinothini\\Downloads\\impresionPanel.xls";
		String sheetName = "Report";
		String[] columnsToCheck = {"Registration Date", "Race", "Ethnicity"};
		int firstRowIndex = 9;
		int secondRowIndex = 10;
		int keyRowIndex = 9;
		int valueRowIndex = 10;

		WebElement cols = driver.findElement(By.xpath("//table[@class='ui-jqgrid-htable']/thead/tr"));
		WebElement rows = driver.findElement(By.xpath("//table[@id='panelLocalizacionPacientes']/tbody/tr[2]"));

		System.out.println("Test Case1-->Open the Excel File");
		HSSFSheet sheet = openExcelFile(filePath, sheetName);
		System.out.println("Test Case2-->To Check if Excel file is downloaded in local machine");
		checkFileExistence(filePath);
		System.out.println("Test Case2-->To check if Registration Date,Ethnicity,Race fields are present in Excel Sheet ");
		checkColumnsInSheet(sheet, columnsToCheck);
		System.out.println("Test Case4-->To read the values from Excel Sheet");
		System.out.println("****Excel Data***");
		MultiValuedMap<String, String> compareRowsMap = compareRows(sheet,keyRowIndex,valueRowIndex);
		System.out.println("Test Case5-->To Read the Values from WebTable in Patient Search screen");
		System.out.println("****WebTable Data***");
		MultiValuedMap<String, String> getTableDataMap = getTableData(driver, cols, rows);
		System.out.println("Test Case6-->To Check if the fields are present in the WebTable");
		ArrayList<String> columnsToCheck1 = new ArrayList<String>(Arrays.asList("Registration Date", "Race", "Ethnicity"));
	    getTableData(driver, cols, rows, columnsToCheck1);
		System.out.println("Test Case7-->To compare if values in Hcis screen and Excel sheet are matching");
		ArrayList<String> keysToCompare = new ArrayList<String>(Arrays.asList("Gender", "Name", "IUP"));
		compareMultipleKeyValues(compareRowsMap, getTableDataMap, keysToCompare);	
	
		
		
	}	        
};








