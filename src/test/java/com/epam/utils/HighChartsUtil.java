package com.epam.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.epam.charts.AnniversaryChart;

public class HighChartsUtil {

	private WebDriver driver;
	private AnniversaryChart chart;
	private String containerID = "container";

	public HighChartsUtil() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public List<List<String>> getSeriesTooltips(String seriesTitle) {
		List<List<String>> seriesTooltips = new ArrayList<List<String>>();
		int seriesNumber = chart.getSeriesNumber(seriesTitle);
		int seriesSize = chart.getSeriesSize(seriesNumber);
		for (int i = 0; i < seriesSize; i++) {
			seriesTooltips.add(getTooltip(seriesNumber, i));
		}
		return seriesTooltips;
	}

	public List<String> getTooltip(int seriesNumber, int pointNumber) {
		chart.hoverOverPointAtSeries(pointNumber, seriesNumber);
		return chart.getCurrentToolTip();
	}

	public void openURL(String url) {
		driver.get(url);
	}

	public void closeBrowser() {
		driver.close();
	}

	public void switchToFrame(String frameLocation) {
		driver.switchTo().frame(driver.findElement(By.xpath(frameLocation)));
	}

	public void setAnniversaryChart() {
		chart = new AnniversaryChart(driver, containerID);
	}
}
