package com.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

public class CounterResponse {

	private JSONArray counts = new JSONArray();

	public CounterResponse() {
		
	}
	
	public CounterResponse(ArrayList<String> searchText) {
		File file = null;
		try {
			file = new ClassPathResource("lorem.txt").getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String text : searchText) {
			this.counts.put(new JSONObject("{'" + text + "':" + countWord(text, file) + "}"));
		}
	}

	private static int countWord(String word, File file) {
		int count = 0;
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String nextToken = scanner.next();
			nextToken = nextToken.replace(",", "");
			nextToken = nextToken.replace(".", "");
			if (nextToken.trim().toLowerCase().equalsIgnoreCase(word.trim().toLowerCase()))
				count++;
		}
		return count;
	}

	private static Map<String, Integer> countWords = new TreeMap<String, Integer>();


	public static List<Entry<String, Integer>> getTop(int topMuch) {
		File file=null;
		try {
			file = new ClassPathResource("lorem.txt").getFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (final Stream<String> lines = Files.lines(Paths.get(file.getPath()))
				.map(line -> line.split("[\\s]+")).flatMap(Arrays::stream).distinct().sorted()) {
			List<String> uniqueWords = new ArrayList<>();
			uniqueWords=lines.collect(Collectors.toList());
			for (int i = 0; i < uniqueWords.size(); i++) {
				countWords.put(uniqueWords.get(i),countWord(uniqueWords.get(i),file));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Set<Entry<String, Integer>> set = countWords.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
		{
			public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 ){
				return (o2.getValue()).compareTo( o1.getValue() );
			};
		});
		return list.subList(0, topMuch);
	}

	public JSONArray getCounts() {
		return counts;
	}

	public void setCounts(JSONArray counts) {
		this.counts = counts;
	}

}
