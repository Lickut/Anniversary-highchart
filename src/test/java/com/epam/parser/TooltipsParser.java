package com.epam.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TooltipsParser {
	public static List<List<String>> parseTooltips(String csvFile) throws IOException {
		String line = "";
		String cvsSplitBy = ";";
		List<List<String>> result = new ArrayList<List<String>>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "UTF8"))) {
			while ((line = br.readLine()) != null) {
				ArrayList<String> listLine = new ArrayList<>();
				String[] tooltip = line.split(cvsSplitBy);
				listLine.addAll(Arrays.asList(tooltip));
				result.add(listLine);
			}
		}
		return result;
	}
}
