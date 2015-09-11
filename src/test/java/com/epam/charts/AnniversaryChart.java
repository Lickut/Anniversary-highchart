package com.epam.charts;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AnniversaryChart extends HighChart {

	@FindBy(how = How.CSS, using = "g.highcharts-series-group>g.highcharts-series:not(:empty")
	protected List<WebElement> plotSeries;
	@FindBy(how = How.CSS, using = "g.highcharts-series-group>g.highcharts-tracker>g>path")
	protected List<WebElement> elementToHoverOver;
	@FindBy(how = How.CSS, using = "rect:nth-child(2)")
	protected WebElement rectElement;

	public AnniversaryChart(WebDriver driver, String containerID) {
		super(driver, containerID);
	}

	public int getSeriesNumber(String seriesTitle) {
		List<String> labels = getLegendLabels();
		if (labels.contains(seriesTitle)) {
			for (int i = 0; i < labels.size(); i++) {
				if (seriesTitle.equals(labels.get(i)))
					return i;
			}
		}
		throw new IllegalArgumentException("No such series: " + seriesTitle);
	}

	public int getAmountOfSeries() {
		return plotSeries.size();
	}

	public int getSeriesSize(int seriesNumber) {
		if (driver instanceof JavascriptExecutor) {
			Long seriesSize = (Long) ((JavascriptExecutor) driver)
					.executeScript(
							"var chart = $('#'+arguments[0]).highcharts();"
									+ "return chart.series[arguments[1]].data.length;",
							containerID, seriesNumber);
			return seriesSize.intValue();
		}
		return 0;
	}

	public void hoverOverPointAtSeries(int pointNumber, int seriesNumber) {
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver)
					.executeScript(
							"var chart = $('#'+arguments[0]).highcharts();"
									+ "chart.series[arguments[1]].data[arguments[2]].setState('hover');"
									+ "chart.tooltip.refresh(chart.series[arguments[1]].data[arguments[2]]);",
							containerID, seriesNumber, pointNumber);
		}
	}
}
