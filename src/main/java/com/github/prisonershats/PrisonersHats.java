package com.github.prisonershats;

import java.util.Random;

import com.github.prisonershats.checkers.SimpleHatsChecker;
import com.github.prisonershats.generators.IntegerGenericHatsGenerator;
import com.github.prisonershats.generators.StringGenericHatsGenerator;
import com.github.prisonershats.strategies.PermutationsStrategy;

public class PrisonersHats {

	// non-generic

//	public static void main(String[] args) throws Exception {
//		int n = 100;
//		int testsCount = 1000;
//
//		PrisonersHatsStrategy<Integer> solver = new PermutationsStrategy<>();
//
//		Random random = new Random(/* 42 */);
//		HatsGenerator<Integer> generator = new IntegerHatsGenerator(random);
//		HatsChecker<Integer> checker = new SimpleHatsChecker<>();
//
//		PrisonersHatsRunner<Integer> prisonersHatsRunner
//			= new PrisonersHatsRunner<>(generator, solver, checker, i -> String.format("%3d", i));
//		prisonersHatsRunner.runWith(n, testsCount);
//	}

	// generic applied to String

	public static void main(String[] args) throws Exception {
		int n = 100;
		int testsCount = 1000;

		GenericPrisonersHatsStrategy<String> solver = new PermutationsStrategy<>();
		GenericHatsGenerator<String> generator = new StringGenericHatsGenerator(new Random(/* 42 */));
		HatsChecker<String> checker = new SimpleHatsChecker<>();

		GenericPrisonersHatsRunner<String> prisonersHatsRunner
				= new GenericPrisonersHatsRunner<>(generator, solver, checker, i -> i);
		prisonersHatsRunner.runWith(n, testsCount);
	}

	// generic applied to Integer

//	public static void main(String[] args) throws Exception {
//		int n = 100;
//		int testsCount = 1000;
//
//		GenericPrisonersHatsStrategy<Integer> solver = new PermutationsStrategy<>();
//		GenericHatsGenerator<Integer> generator = new IntegerGenericHatsGenerator(new Random(/* 42 */));
//		HatsChecker<Integer> checker = new SimpleHatsChecker<>();
//
//		GenericPrisonersHatsRunner<Integer> prisonersHatsRunner
//				= new GenericPrisonersHatsRunner<>(generator, solver, checker, Object::toString);
//		prisonersHatsRunner.runWith(n, testsCount);
//	}

}