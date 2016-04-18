package com.github.prisonershats;

import java.util.Random;

import com.github.prisonershats.checkers.SimpleHatsChecker;
import com.github.prisonershats.generators.StringSetGenerator;
import com.github.prisonershats.strategies.IntegerStrategyWrapper;
import com.github.prisonershats.strategies.PermutationsStrategy;
import com.github.prisonershats.strategies.PrisonersChainStrategy;

public class Main {

	// non-generic

//	public static void main(String[] args) throws Exception {
//		int n = 100;
//		int testsCount = 1000;
//
//		Strategy<Integer> solver = new PermutationsStrategy<>();
//
//		Random random = new Random(/* 42 */);
//		HatsGenerator<Integer> generator = new IntegerHatsGenerator(random);
//		HatsChecker<Integer> checker = new SimpleHatsChecker<>();
//
//		Runner<Integer> prisonersHatsRunner
//			= new Runner<>(generator, solver, checker, i -> String.format("%3d", i));
//		prisonersHatsRunner.runWith(n, testsCount);
//	}

	// generic applied to String

	public static void main(String[] args) throws Exception {
		int n = 100;
		int testsCount = 1000;

		//GenericStrategy<String> solver = new PermutationsStrategy<>();
		GenericStrategy<String> solver = new IntegerStrategyWrapper<>(new PrisonersChainStrategy());
		SetGenerator<String> generator = new StringSetGenerator(new Random(/* 42 */));
		HatsChecker<String> checker = new SimpleHatsChecker<>();

		GenericRunner<String> prisonersHatsRunner
				= new GenericRunner<>(generator, solver, checker, i -> i.toString());
		prisonersHatsRunner.runWith(n, testsCount);
	}

	// generic applied to Integer

//	public static void main(String[] args) throws Exception {
//		int n = 100;
//		int testsCount = 1000;
//
//		GenericStrategy<Integer> solver = new PermutationsStrategy<>();
//		SetGenerator<Integer> generator = new IntegerSetGenerator();
//		HatsChecker<Integer> checker = new SimpleHatsChecker<>();
//
//		GenericRunner<Integer> prisonersHatsRunner
//				= new GenericRunner<>(generator, solver, checker, Object::toString);
//		prisonersHatsRunner.runWith(n, testsCount);
//	}

}