package com.github.prisonershats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

public class GenericPrisonersHatsRunner<T extends Comparable<T>> {
	private static final Logger LOG = LoggerFactory.getLogger(GenericPrisonersHatsRunner.class);

	private final GenericHatsGenerator<T> generator;
	private final GenericPrisonersHatsStrategy<T> solver;
	private final HatsChecker<T> checker;
	private Function<T, String> toStringFn;

	public GenericPrisonersHatsRunner(GenericHatsGenerator<T> generator, GenericPrisonersHatsStrategy<T> solver,
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
			generator.generate(numberOfPrisoners);
			List<T> hats = generator.getHats();
			LOG.debug("real hats: {}", toString(hats));
			Set<T> allHats = generator.getAllHats();

			// ask hat number of each prisoner
			List<T> saidHats = new ArrayList<>();
			for (int prisoner = 0; prisoner < numberOfPrisoners; prisoner++) {
				List<T> visibleHats = hats.subList(prisoner + 1, numberOfPrisoners);
				T hatValueAnounced = solver.guessHat(saidHats, visibleHats, allHats);
				saidHats.add(hatValueAnounced);
			}
			LOG.debug("said hats: {}", toString(hats));

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