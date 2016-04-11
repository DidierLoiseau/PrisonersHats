package com.github.prisonershats.generators;

import com.github.prisonershats.SetGenerator;

import java.util.*;

public class StringSetGenerator implements SetGenerator<String> {

	private final Random random;

	public StringSetGenerator(Random random) {
		this.random = random;
	}

	public Set<String> generate(int size) {
		Set<String> hats = new HashSet<>();
		while (hats.size() < size) {
			String generatedString = generateRandomString();
			if (!hats.contains(generatedString)) {
				hats.add(generatedString);
			}
		}
		return hats;
	}

	private String generateRandomString() {
		return randomString(random.nextInt(10) + 1);
	}

	// adapted from http://stackoverflow.com/a/157202
	private static final String AB = "abcdefghijklmnopqrstuvwxyz";
	private String randomString(int len){
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ )
			sb.append( AB.charAt( random.nextInt(AB.length()) ) );
		return sb.toString();
	}

}