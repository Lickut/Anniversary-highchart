package com.epam.testhighcharts;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.epam.parser.TooltipsParser;
import com.epam.utils.HighChartsUtil;

@RunWith(Parameterized.class)
public class TestHighchartSeries {

	private static String baseURL = "http://www.highcharts.com/component/content/article/2-news/146-highcharts-5th-anniversary";
	private static String tooltipsResourcePath = "src\\test\\resources\\tooltips\\";
	private static String frameLocation = "//div[@class='item-page']/p[2]/iframe";
	private static HighChartsUtil utils;
	private static String hubURL = "http://localhost:4444/wd/hub";
	private WebDriver driver;
	private String browserName;
	private String tooltipsFileName;
	private String seriesTitle;
	
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
                 { "chrome","tooltipsEmployees.csv","Highsoft employees"},{"firefox","tooltipsEmployees.csv","Highsoft employees"},
                 {"chrome","tooltipsGoogle.csv","Google search for highcharts"},{"firefox","tooltipsGoogle.csv","Google search for highcharts"},
                 {"chrome","tooltipsRevenue.csv","Revenue"},{"firefox","tooltipsRevenue.csv","Revenue" }  
           });
    }
	
	@After
	public void tearDown() {
		driver.quit();
	}
    
	public TestHighchartSeries(String browserName, String tooltipsFileName, String seriesTitle) {
		this.browserName = browserName;
		this.tooltipsFileName = tooltipsFileName;
		this.seriesTitle = seriesTitle;
	}
	
	@Test
	public void testChartTooltips() throws IOException {
		driver = createRemoteDriver(browserName);
		openHighchart(driver);

		utils = new HighChartsUtil(driver);
		List<List<String>> tooltips = utils.getSeriesTooltips(seriesTitle);
		List<List<String>> expectedTooltips = TooltipsParser.parseTooltips(tooltipsResourcePath + tooltipsFileName);
		assertThat(tooltips, is(expectedTooltips));
	}

	public WebDriver createRemoteDriver(String browserName) throws MalformedURLException {
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		capability.setBrowserName(browserName);
		capability.setPlatform(Platform.WINDOWS);
		WebDriver driver = new RemoteWebDriver(new URL(hubURL), capability);
		return driver;
	}

	public void openHighchart(WebDriver driver) {
		driver.get(baseURL);
		driver.switchTo().frame(driver.findElement(By.xpath(frameLocation)));
	}
}
