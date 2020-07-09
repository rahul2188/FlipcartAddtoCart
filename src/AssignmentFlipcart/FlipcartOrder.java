package AssignmentFlipcart;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlipcartOrder {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		//setting the system property and initiating the webdriver
		System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		
		//launching the website
		driver.get("http://flipcart.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		
		//closing the login frame
		driver.findElement(By.xpath("//button[@class='_2AkmmA _29YdH8']")).click();
		
		//Go to TVs & Appliances -> Shop by screen size -> 48-55
		Actions a=new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//ul[@class='_114Zhd']/li[2]"))).perform();
		driver.findElement(By.xpath("//a[@title='48 - 55']")).click();
		
		//selecting Price range - 20,000 to 60,000 and  Resolution Ultra HD(4K).
		Thread.sleep(4000);
		Select minValue=new Select(driver.findElement(By.xpath("//div[@class='_1qKb_B']/select")));
		minValue.selectByValue("20000");
		
		Thread.sleep(4000);
		Select maxValue=new Select(driver.findElement(By.xpath("//div[@class='_1YoBfV']/select")));
		maxValue.selectByValue("60000");
		
		Thread.sleep(4000);
		driver.findElement(By.xpath("//div[text()='Resolution']")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Ultra HD (4K)')]/preceding-sibling::div[@class='_1p7h2j']")).click();
		
		Thread.sleep(4000);
		//printing number of total products
		String productsShown=driver.findElement(By.xpath("//span[@class='eGD5BM']")).getText();
		System.out.println(productsShown);
		
		//Add 3 televisions to compare
		
		int numberOfProducts=driver.findElements(By.xpath("//div[@class='_1UoZlX']//div[@class='_1p7h2j']")).size();
		System.out.println(numberOfProducts);
		
		for(int i=1;i<=3;i++) {
			driver.findElements(By.xpath("//div[@class='_1UoZlX']//div[@class='_1p7h2j']")).get(i).click();
		}
		
		driver.findElement(By.xpath("//span[text()='COMPARE']")).click();
		
		//taking screenshot of the comparison
		Thread.sleep(4000);
		TakesScreenshot screenshot=(TakesScreenshot)driver;
		File src=screenshot.getScreenshotAs(OutputType.FILE);
		File dest=new File("./screenshot/screenshot.png");
		FileUtils.copyFile(src, dest);
		

		List<WebElement> prices=driver.findElements(By.xpath("//div[@class='_37Mj_H']//div[@class='_1vC4OE']"));
		ArrayList<Integer> list=new ArrayList<>();
		for(int i=0;i<prices.size();i++) {
			String priceProd=prices.get(i).getText().substring(1);
			 priceProd=priceProd.replaceAll("[^0-9]", "");
			 int priceInt=Integer.parseInt(priceProd);
			 list.add(priceInt);
		}
		Collections.sort(list);
		int lowestPrice=list.get(0);
		System.out.println(lowestPrice);
		System.out.println("===============");
		NumberFormat format1=NumberFormat.getNumberInstance(Locale.US);
		String lowestPrice1=format1.format(lowestPrice);
		
		System.out.println(lowestPrice1);
		driver.findElement(By.xpath("//div[@class='_2lK_YI']//*[contains(text(),'"+lowestPrice1+"')]/../../../following-sibling::div[1]/child::div/button[@class='_2AkmmA _2Npkh4 _2MWPVK e1kKGU']")).click();
		//driver.findElement(By.xpath(xpathExpression))
		
		Thread.sleep(4000);
		//clicking on Help Centre
		System.out.println(driver.getTitle());
		driver.findElement(By.linkText("Help Center")).click();
		
		Set<String> set=driver.getWindowHandles();
		Iterator<String> id=set.iterator();
		
		for(String s:set) {
			driver.switchTo().window(s);
			if(driver.getTitle().contains("Flipkart Customer Care & Help Center")) {
				driver.findElement(By.xpath("//div[@class='_3aS5mM']//p[contains(text(),'Preorder')]")).click();
				System.out.println(driver.getTitle());
				driver.findElement(By.xpath("//button[@class='_2AkmmA Gl6Vsq' and text()='Yes']")).click();
				driver.findElement(By.xpath("//span[text()='Cart']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//span[text()='Place Order']")).click();
			}
		}

		
		
		
	}

}
