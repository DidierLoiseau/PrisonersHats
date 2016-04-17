package com.github.prisonershats;

import static java.util.Arrays.asList;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.prisonershats.checkers.SimpleHatsChecker;
import com.github.prisonershats.generators.IntegerHatsGenerator;
import com.github.prisonershats.strategies.MeanBasedStrategy;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class PrisonersHats {
	private static final Logger LOG = LoggerFactory.getLogger(PrisonersHats.class);

	private static final OptionParser PARSER = new OptionParser();
	private static final OptionSpec<Integer> PRISONERS = PARSER
			.acceptsAll(asList("p", "prisoners"), "number of prisoners")
			.withRequiredArg().ofType(Integer.class)
			.defaultsTo(100);
	private static final OptionSpec<Integer> TESTS = PARSER
			.acceptsAll(asList("t", "tests"), "number of tests")
			.withRequiredArg().ofType(Integer.class)
			.defaultsTo(1000);
	private static final OptionSpec<String> STRATEGY = PARSER
			.acceptsAll(asList("s", "strategy"), "the strategy to use")
			.withRequiredArg();
	private static final OptionSpec<String> STRATEGY_PARAMETER = PARSER
			.acceptsAll(asList("sp", "strategyparameter"), "the parameter for the strategy to use")
			.withRequiredArg();
	private static final OptionSpec<Long> SEED = PARSER
			.acceptsAll(asList("r", "seed"), "random seed")
			.withRequiredArg().ofType(Long.class);
	static {
		PARSER.acceptsAll(asList("?", "h", "help"), "shows this help").forHelp();
	}

	public static void main(String[] args) throws Exception {
		OptionSet options = PARSER.parse(args);
		if (options.has("help")) {
			PARSER.printHelpOn(System.out);
			return;
		}

		int n = options.valueOf(PRISONERS);
		int testsCount = options.valueOf(TESTS);
		// TODO add some type checking
		PrisonersHatsStrategy<Integer> solver;
		if (options.has(STRATEGY)) {
			Class<?> strategyClass = Class
					.forName(MeanBasedStrategy.class.getPackage().getName() + "." + options.valueOf(STRATEGY));
			if (options.has(STRATEGY_PARAMETER)) {
				Class[] constructorArgs = {String.class};
				solver = (PrisonersHatsStrategy<Integer>) strategyClass.getDeclaredConstructor(constructorArgs).newInstance(options.valueOf(STRATEGY_PARAMETER));
			}
			else {
				solver = strategyClass.asSubclass(PrisonersHatsStrategy.class).newInstance();
			}
		} else {
			solver = new MeanBasedStrategy();
		}
		LOG.info("Using strategy: {}", solver.getClass().getSimpleName());
		Random random;
		if (options.has(SEED)) {
			random = new Random(options.valueOf(SEED));
		} else {
			random = new Random(/* 42 */);
		}

		HatsGenerator<Integer> generator = new IntegerHatsGenerator(random);
		HatsChecker<Integer> checker = new SimpleHatsChecker<>();

		PrisonersHatsRunner<Integer> prisonersHatsRunner
			= new PrisonersHatsRunner<>(generator, solver, checker, i -> String.format("%3d", i));
		prisonersHatsRunner.runWith(n, testsCount);
	}

}