import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class PerfectHashFrequencies {

	public static LinkedHashMap<String, Integer> getKeyFreqUsingPerfectHash() throws IOException {
		
		// TODO Auto-generated method stub
		LinkedHashMap<String, Integer> frequenciesIndex = new LinkedHashMap<String, Integer>();

		char letter = 0;

		/*
		 * @SuppressWarnings("resource") BufferedReader in = new
		 * BufferedReader(new
		 * FileReader("E:/java/programs/CuckooHashMap/src/frequentKeys.txt"));
		 * String sample;
		 * 
		 * while ((sample = in.readLine()) != null) {
		 */
		
		String sample = "There is no single development in either technology or management technique which by itself promises simplicity";

		String[] data = new String(sample.toUpperCase()).split(" ");

		int dataLen = data.length;
		int frequencyValue = 0;
		String[] anArray = new String[data.length];

		HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
		HashMap<Character, Integer> hm2 = new HashMap<Character, Integer>();

		for (String key : data) {

			Integer freq;

			// Get the first letter and frequency and place in HashMap
			letter = key.charAt(0);
			freq = hm.get(letter);
			if (freq == null)
				freq = 1;
			else
				freq++;

			hm.put(letter, freq);

			// Get Last Letter and Frequency and place in HashMap
			letter = key.charAt(key.length() - 1);
			freq = hm.get(letter);
			if (freq == null)
				freq = 1;
			else
				freq++;

			hm.put(letter, freq);
		}

		// Sort the Map in decreasing Order
		Map<Character, Integer> sortedHM = hm.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		System.out.println();
		System.out.println("*First & Last Character Occurence of Words*");
		System.out.println(sortedHM);

		// For each Key calculate Sum of first and last characters
		HashMap<String, Integer> wordValueMap = new HashMap<String, Integer>();

		for (String key : data) {
			int wordValue = sortedHM.get(key.charAt(0)) + sortedHM.get(key.charAt(key.length() - 1));
			// System.out.println(key+"-"+wordValue);
			wordValueMap.put(key, wordValue);
		}

		// Get the sorted count of word and frequency
		Map<String, Integer> sorted = wordValueMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		System.out.println();
		System.out.println("*Sorting Words Based on Count of First & Last Character*");
		System.out.println(sorted);

		hm2.putAll(sortedHM);

		// Set the gValues to HashMap for Each Character
		for (Character gValues : hm2.keySet()) {
			hm2.put(gValues, 0);
		}

		// Convert HashMap to Array
		String[] strings = sorted.keySet().toArray(new String[sorted.size()]);

		// Iterate through the HashMap to get Frequency of Keys
		for (String sortedKeys : sorted.keySet()) {

			frequencyValue = PerfectHashFrequencies.getFrequencyValue(sortedKeys, hm2, dataLen, anArray, strings);

			anArray[frequencyValue] = sortedKeys;

		}

		System.out.println();
		System.out.println("*Perfect Hashing Final Index Table Arranged Based on Frequency*");
		for (int i = 0; i < anArray.length; i++) {
			System.out.println("Element[" + i + "]: " + anArray[i]);
			frequenciesIndex.put(anArray[i], i + 1);
		}
		return frequenciesIndex;
	}

	// This Method Calculates the Word Frequency based on First and Last Letter
	// and Size of Word
	public static int getFrequencyValue(String sortedKeys, HashMap<Character, Integer> hm2, int dataLen,
			String[] anArray, String[] strings) {

		int length = sortedKeys.length();
		int gFirst = hm2.get(sortedKeys.charAt(0));
		int gSecond = hm2.get(sortedKeys.charAt(sortedKeys.length() - 1));
		int frequencyValue = (length + gFirst + gSecond) % dataLen;

		if (anArray[frequencyValue] != null && gFirst <= 4) {
			// System.out.println("xxxx:" + sortedKeys);
			hm2.put(sortedKeys.charAt(0), gFirst + 1);
			frequencyValue = getFrequencyValue(sortedKeys, hm2, dataLen, anArray, strings);
		}

		else if (anArray[frequencyValue] != null && gFirst > 4) {
			int prevIndex = (Arrays.asList(strings).indexOf(sortedKeys)) - 1;
			try {
				String prevElement = strings[prevIndex];
				hm2.put(prevElement.charAt(0), gFirst + 1);
				frequencyValue = getFrequencyValue(prevElement, hm2, dataLen, anArray, strings);

				if (anArray[frequencyValue] != null && gFirst <= 4) {
					hm2.put(prevElement.charAt(0), gFirst + 1);
					frequencyValue = getFrequencyValue(prevElement, hm2, dataLen, anArray, strings);
				} else {
					anArray[frequencyValue] = prevElement;
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println(e);
			}
		}
		return frequencyValue;
	}

}