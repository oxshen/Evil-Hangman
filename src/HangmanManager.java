// Owen Shen
// 10/19/23
// Create evil hangman lab with sets and maps.

import java.util.*;

public class HangmanManager {
	// Initializing all the needed sets and maps	
	int guessesLeft;
	Set<Character> guesses = new TreeSet<Character>();
	Set<String> words = new TreeSet<String>();
	int len;
	ArrayList<Character> pattern = new ArrayList<Character>();
	Set<String> temp = new TreeSet<String>();

	public HangmanManager(List<String> dictionary, int length, int max) {
		if (length < 1 && max < 0) { // Checks validity of length and max
			throw new IllegalArgumentException();
		}
		len = length;
		guessesLeft = max;
		for (int i = 0; i < len; i++) {
			pattern.add('-');
		}
		for (String word : dictionary) { // Finds word = lenth
			if (word.length() == len) {
				words.add(word);
			}
		}
	}

	public Set<String> words() {
		return words;
	}

	public int guessesLeft() {
		return guessesLeft;
	}

	public Set<Character> guesses() {
		return guesses;
	}

	public String pattern() {
		String patt = "";
		if (words.isEmpty()) { // Makes sure there is always another word
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < len; i++) {
			patt = patt + pattern.get(i);
		}
		return patt;
	}

	public int record(char guess) {
		if (guessesLeft() < 1 || words.isEmpty()) { // Throws throw an IllegalArgumentException if length is less than 1 or if max is less than 0
			throw new IllegalStateException();
		}
		if (!words.isEmpty() && guesses.contains(guess)) {
			throw new IllegalArgumentException();
		}
		Map<String, Set<String>> m = new TreeMap<String, Set<String>>();
		int occurrences = 0;
		guesses.add(guess);

		String p = "";
		for (String word : words) {     // Loop through the words to generate patterns and group words by pattern
			ArrayList<Character> temp = new ArrayList<>(List.copyOf(pattern));
			for (int i = 0; i < len; i++) {
				if (word.charAt(i) == guess) {
					temp.set(i, guess);
				}
			}
			for (int i = 0; i < len; i++) {
				p = p + temp.get(i);
			}
			if (!m.containsKey(p)) {
				m.put(p, new TreeSet<String>());
			}
			m.get(p).add(word);
			p = "";
		}
		int max = 0;
		for (String patt : m.keySet()) {
			if (m.get(patt).size() > max) {
				max = m.get(patt).size();
				words = m.get(patt);
				for (int i = 0; i < len; i++) {
					pattern.set(i, patt.charAt(i));
				}
			}
		}
		for (int i = 0; i < len; i++) {
			if (pattern.get(i) == guess) {
				occurrences++;
			}
		}

		if (!pattern.contains(guess)) { // Guessesleft -1 if not in pattern
			guessesLeft--;
		}

		return occurrences;
	}

}
