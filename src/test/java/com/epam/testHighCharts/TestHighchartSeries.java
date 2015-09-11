package com.epam.testHighCharts;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.epam.parser.TooltipsParser;
import com.epam.utils.HighChartsUtil;

@RunWith(JUnitParamsRunner.class)
public class TestHighchartSeries {

	private static String baseURL = "http://www.highcharts.com/component/content/article/2-news/146-highcharts-5th-anniversary";
	private static String tooltipsResourcePath = "src\\test\\resources\\tooltips\\";
	private static String frameLocation = "//div[@class='item-page']/p[2]/iframe";
	private static HighChartsUtil utils;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		utils = new HighChartsUtil();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		utils.closeBrowser();
	}

	@Before
	public void setUp() throws Exception {
		utils.openURL(baseURL);
	}

	@Test
	@Parameters({ "tooltipsEmployees.csv|Highsoft employees",
			"tooltipsGoogle.csv|Google search for highcharts",
			"tooltipsRevenue.csv|Revenue" })
	public void testChartTooltips(String tooltipsFileName, String seriesTitle)
			throws IOException {
		utils.switchToFrame(frameLocation);
		utils.setAnniversaryChart();
		List<List<String>> tooltips = utils.getSeriesTooltips(seriesTitle);
		List<List<String>> expectedTooltips = TooltipsParser
				.parseTooltips(tooltipsResourcePath + tooltipsFileName);
		assertThat(tooltips.size(), is(expectedTooltips.size()));
		for (int i = 0; i < expectedTooltips.size(); i++) {
			assertThat(tooltips.get(i), is(expectedTooltips.get(i)));
		}
	}
}
