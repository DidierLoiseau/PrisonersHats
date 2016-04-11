package com.github.prisonershats.generators;

import com.github.prisonershats.SetGenerator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class IntegerSetGenerator implements SetGenerator<Integer> {

    public Set<Integer> generate(int size) {
        return new HashSet<>(IntStream.range(0, size).boxed().collect(toList()));
    }

}