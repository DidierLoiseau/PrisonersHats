package com.github.prisonershats.generators;

import com.github.prisonershats.GenericHatsGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class StringGenericHatsGenerator extends BaseGenericHatsGenerator<String> {

	public StringGenericHatsGenerator(Random random) {
		super(random);
	}

	protected Set<String> generateAllHats(int prisonersNumber) {
		Set<String> hats = new HashSet<>();
		while (hats.size() < prisonersNumber + 1) {
			String generatedString = generateRandomString();
			if (!hats.contains(generatedString)) {
				hats.add(generatedString);
			}
		}
		return hats;
	}

	private String generateRandomString() {
		return randomString(random.nextInt(10));
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