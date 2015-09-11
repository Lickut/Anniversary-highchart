package com.epam.charts;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class HighChart {

	protected WebDriver driver;
	protected WebElement chart;
	protected WebDriverWait wait;
	protected Actions performAction;
	protected String containerID = "container";
	protected String tooltipMessageLocator = "text > tspan";
	protected String legendTextLocator = "text";

	@FindBy(how = How.CSS, using = "g.highcharts-tooltip")
	protected WebElement toolTip;
	@FindBy(how = How.CSS, using = "g.highcharts-legend-item")
	protected List<WebElement> legend;
	@FindBy(how = How.CSS, using = "g.highcharts-axis")
	protected List<WebElement> axisLabels;

	public HighChart(WebDriver driver, String containerID) {
		this.containerID = containerID;
		this.driver = driver;
		chart = driver.findElement(By.id(containerID));
		PageFactory.initElements(new DefaultElementLocatorFactory(chart), this);

		int waitTimeoutInSeconds = 15;
		wait = new WebDriverWait(driver, waitTimeoutInSeconds, 100);
		performAction = new Actions(driver);
	}

	public boolean isChartDisplayed() {
		return wait.until(visibilityOf(this.chart)) != null;
	}

	public boolean isTooltipDisplayed() {
		return wait.until(visibilityOf(toolTip)) != null;
	}

	public List<String> getCurrentToolTip() {
		List<String> lines = new ArrayList<String>();
		List<WebElement> toolTipLines = toolTip.findElements(By
				.cssSelector(tooltipMessageLocator));
		for (WebElement toolTipLine : toolTipLines) {
			lines.add(toolTipLine.getText());
		}
		return lines;
	}

	public List<String> getLegendLabels() {
		List<String> labels = new ArrayList<String>();
		for (WebElement i : legend) {
			if (i.findElements(By.cssSelector(legendTextLocator)).size() != 0)
				;
			labels.add(i.findElement(By.cssSelector(legendTextLocator))
					.getText());
		}
		return labels;
	}
}
