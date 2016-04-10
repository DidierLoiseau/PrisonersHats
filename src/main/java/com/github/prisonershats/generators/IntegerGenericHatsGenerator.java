package com.github.prisonershats.generators;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class IntegerGenericHatsGenerator extends BaseGenericHatsGenerator<Integer> {

    public IntegerGenericHatsGenerator(Random random) {
        super(random);
    }

    protected Set<Integer> generateAllHats(int prisonersNumber) {
        return new HashSet<>(IntStream.range(0, prisonersNumber + 1).boxed().collect(toList()));
    }

}