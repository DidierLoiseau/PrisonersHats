package com.github.prisonershats;

import java.util.Random;

import com.github.prisonershats.checkers.SimpleHatsChecker;
import com.github.prisonershats.generators.IntegerHatsGenerator;
import com.github.prisonershats.generators.IntegerSetGenerator;
import com.github.prisonershats.generators.StringSetGenerator;
import com.github.prisonershats.strategies.IntegerPrisonersHatsStrategyWrapper;
import com.github.prisonershats.strategies.PermutationsStrategy;
import com.github.prisonershats.strategies.PrisonersChainStrategy;

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

		//GenericPrisonersHatsStrategy<String> solver = new PermutationsStrategy<>();
		GenericPrisonersHatsStrategy<String> solver = new IntegerPrisonersHatsStrategyWrapper<>(new PrisonersChainStrategy());
		SetGenerator<String> generator = new StringSetGenerator(new Random(/* 42 */));
		HatsChecker<String> checker = new SimpleHatsChecker<>();

		GenericPrisonersHatsRunner<String> prisonersHatsRunner
				= new GenericPrisonersHatsRunner<>(generator, solver, checker, i -> i.toString());
		prisonersHatsRunner.runWith(n, testsCount);
	}

	// generic applied to Integer

//	public static void main(String[] args) throws Exception {
//		int n = 100;
//		int testsCount = 1000;
//
//		GenericPrisonersHatsStrategy<Integer> solver = new PermutationsStrategy<>();
//		SetGenerator<Integer> generator = new IntegerSetGenerator();
//		HatsChecker<Integer> checker = new SimpleHatsChecker<>();
//
//		GenericPrisonersHatsRunner<Integer> prisonersHatsRunner
//				= new GenericPrisonersHatsRunner<>(generator, solver, checker, Object::toString);
//		prisonersHatsRunner.runWith(n, testsCount);
//	}

}