package com.github.prisonershats;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Runner<T> {
	private static final Logger LOG = LoggerFactory.getLogger(Runner.class);
	
	private final HatsGenerator<T> generator;
	private final Strategy<T> solver;
	private final HatsChecker<T> checker;
	private Function<T, String> toStringFn;

	public Runner(HatsGenerator<T> generator, Strategy<T> solver,
				  HatsChecker<T> checker, Function<T, String> toStringFn) {
		this.generator = generator;
		this.solver = solver;
		this.checker = checker;
		this.toStringFn = toStringFn;
	}

	public void runWith(int numberOfPrisoners, int testsCount) {
		double deathCount = 0;
		int maxDeaths = 0;
		for (int test = 0; test < testsCount; test++) {
			if (!LOG.isDebugEnabled() && test % 100 == 0) {
				LOG.info("Iteration {}", test);
			}
			LOG.debug("--- Iteration {}", test);
			// initialise hats randomly
			List<T> hats = generator.generate(numberOfPrisoners);
			LOG.debug("real hats: {}", toString(hats));

			// ask hat number of each prisoner
			List<T> saidHats = new ArrayList<>();
			for (int prisoner = 0; prisoner < numberOfPrisoners; prisoner++) {
				List<T> visibleHats = hats.subList(prisoner + 1, numberOfPrisoners);
				T hatValueAnounced = solver.guessHat(saidHats, visibleHats);
				saidHats.add(hatValueAnounced);
			}
			LOG.debug("said hats: {}", toString(saidHats));

			// check hats == heardBefore
			int deaths = checker.deathCount(hats, saidHats);
			LOG.debug("deaths: {}", deaths);
			deathCount += deaths;
			maxDeaths = Math.max(maxDeaths, deaths);
		}
		LOG.info("mean deaths: {}, max deaths: {}", deathCount / testsCount, maxDeaths);
	}

	private String toString(List<T> list) {
		return list.stream()
				.map(toStringFn)
				.collect(joining(","));
	}

}
