package com.github.prisonershats;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrisonersHatsRunner<T> {
	private final HatsGenerator<T> generator;
	private final PrisonersHatsStrategy<T> solver;
	private final HatsChecker<T> checker;
	private Function<T, String> toStringFn;

	public PrisonersHatsRunner(HatsGenerator<T> generator, PrisonersHatsStrategy<T> solver,
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
			if (test % 100 == 0) {
				System.out.println("Iteration " + test);
			}
			// initialise hats randomly
			List<T> hats = generator.generate(numberOfPrisoners);
			System.out.print("real hats: ");
			printList(hats);

			// ask hat number of each prisoner
			List<T> saidHats = new ArrayList<>();
			for (int prisoner = 0; prisoner < numberOfPrisoners; prisoner++) {
				List<T> visibleHats = hats.subList(prisoner + 1, numberOfPrisoners);
				T hatValueAnounced = solver.guessHat(saidHats, visibleHats);
				saidHats.add(hatValueAnounced);
			}
			System.out.print("said hats: ");
			printList(saidHats);

			// check hats == heardBefore
			int deaths = checker.deathCount(hats, saidHats);
			System.out.println("deaths: " + deaths);
			deathCount += deaths;
			System.out.println("----");
			maxDeaths = Math.max(maxDeaths, deaths);
		}
		System.out.println("mean deaths: " + deathCount / testsCount + ", max deaths: " + maxDeaths);
	}

	private void printList(List<T> list) {
		System.out.println(list.stream()
				.map(toStringFn)
				.collect(Collectors.joining(",")));
	}

}
