import java.util.HashMap;

public final class MemoryMapping {

     /*
	 * This function maps the Memory Based on Frequency Dynamically
	 */
	public static HashMap<Integer, Character> getMemoryModule() {
		HashMap<Integer, Character> indexTable = new HashMap<Integer, Character>();

		for(int i=0;i<5;i++){
			indexTable.put(i, 'b');
		}
		for(int i=5;i<15;i++){
			indexTable.put(i, 'a');
		}
		return indexTable;
	}

	/*
	 * This function is used to get the hashCode for given key and frequency
	 * value
	 */
	public static int toAscii(String key) {
		StringBuilder sb = new StringBuilder();
		String ascString = null;
		for (int i = 0; i < key.length(); i++) {
			sb.append((int) key.charAt(i));
		}
		ascString = sb.toString();
		
		return Math.abs((key == null ? 0 : key.hashCode()) ^ (ascString == null ? 0 : ascString.hashCode()));

	}
}
