import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

public class ImprovingCuckooHashing {
	public static void main(String[] args) throws IOException {

		ImprovingCuckooHashing im = new ImprovingCuckooHashing();

		/* A HashMap is created to store Key and Frequency */
		LinkedHashMap<String, Integer> hm = PerfectHashFrequencies.getKeyFreqUsingPerfectHash();

		System.out.println();
		System.out.println("*Final list of Elements with Key and Frequency Value*");
		System.out.println(hm);

		/* getMemoryModule() contains frequency and its respective memory */
		HashMap<Integer, Character> indexTable = MemoryMapping.getMemoryModule();

		// ArrayList<String> memoryA = new ArrayList<String>();
		// ArrayList<String> memoryB = new ArrayList<String>();
		String[] memoryA = new String[hm.size()];
		String[] memoryB = new String[hm.size()];

		/*
		 * Iterate through set of keys in hashmap to index the f(x) value to get
		 * Memory Module
		 */
		for (String key : hm.keySet()) {

			int value = hm.get(key);
			char memModule = 0;

			/*
			 * Check if the index table contains the frequency, if it presents
			 * then the memory module is returned
			 */
			if (indexTable.containsKey(value)) {
				memModule = indexTable.get(value);
			} else if (value > 4) {
				memModule = 'a';
				indexTable.put(value, 'a');
			}

			int position = MemoryMapping.toAscii(key);

			if (memModule == 'a') {
				position = position % memoryA.length;
				if (memoryA[position] != null && memoryA[position] != key) {
					position = (MemoryMapping.toAscii(key)) % memoryB.length;
					memoryB[position] = key;
				}
				else {
					memoryA[position] = key;
				}
			} else if (memModule == 'b') {
				position = position % memoryB.length;
				if (memoryB[position] != null && memoryB[position] != key) {
					position = (MemoryMapping.toAscii(key)) % memoryB.length;
					memoryB[position] = key;
				} else {
					memoryB[position] = key;
				}
			}

			/*System.out.println(
					"Key:" + key + " Frequency:" + value + " Memory Module:" + memModule + " Position:" + position);*/

		}

		System.out.println();
		System.out.println("*Memory-A");
		for (int i = 0; i < memoryA.length; i++) {
			System.out.println("Key[" + i + "]: " + memoryA[i]);
		}

		System.out.println();
		System.out.println("*Memory-B");
		for (int i = 0; i < memoryB.length; i++) {
			System.out.println("Key[" + i + "]: " + memoryB[i]);
		}
	}

}