package com.github.prisonershats;

import java.util.Random;

import com.github.prisonershats.checkers.SimpleHatsChecker;
import com.github.prisonershats.generators.IntegerHatsGenerator;
import com.github.prisonershats.strategies.MeanBasedStrategy;

public class PrisonersHats {

	public static void main(String[] args) throws Exception {
		int n = 100;
		int testsCount = 1000;
		PrisonersHatsStrategy<Integer> solver = new MeanBasedStrategy();
		Random random = new Random(/* 42 */);
		HatsGenerator<Integer> generator = new IntegerHatsGenerator(random);
		HatsChecker<Integer> checker = new SimpleHatsChecker<>();

		PrisonersHatsRunner<Integer> prisonersHatsRunner
			= new PrisonersHatsRunner<>(generator, solver, checker, i -> String.format("%3d", i));
		prisonersHatsRunner.runWith(n, testsCount);
	}

}