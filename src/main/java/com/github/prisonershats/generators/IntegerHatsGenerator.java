package com.github.prisonershats.generators;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.prisonershats.HatsGenerator;

public class IntegerHatsGenerator implements HatsGenerator<Integer> {
	private static final Logger LOG = LoggerFactory.getLogger(IntegerHatsGenerator.class);
	private final Random random;

	public IntegerHatsGenerator(Random random) {
		this.random = random;
	}

	@Override
	public List<Integer> generate(int prisonersNumber) {
		List<Integer> hats = IntStream.range(0, prisonersNumber + 1).boxed().collect(toList());
		Collections.shuffle(hats, random);
		LOG.debug("removed hat: " + hats.get(prisonersNumber));
		hats.remove(prisonersNumber);
		return hats;
	}

}
