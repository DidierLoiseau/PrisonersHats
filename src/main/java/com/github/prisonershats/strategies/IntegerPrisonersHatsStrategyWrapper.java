package com.github.prisonershats.strategies;

import com.github.prisonershats.GenericPrisonersHatsStrategy;
import com.github.prisonershats.PrisonersHatsStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IntegerPrisonersHatsStrategyWrapper<T extends Comparable<T>> implements GenericPrisonersHatsStrategy<T> {

    private PrisonersHatsStrategy<Integer> integerPrisonersHatsStrategy;

    public IntegerPrisonersHatsStrategyWrapper(PrisonersHatsStrategy<Integer> integerPrisonersHatsStrategy) {
        this.integerPrisonersHatsStrategy = integerPrisonersHatsStrategy;
    }

    @Override
    public T guessHat(List<T> heardHats, List<T> visibleHats, Set<T> allHats) {

        List<T> allHatsSorted = new ArrayList<>(allHats);
        allHatsSorted.sort(null); // 'null' to sort using Comparable interface of hats

        List<Integer> heardHatsIntegers = new ArrayList<>();
        for (T hat : heardHats) {
            heardHatsIntegers.add(allHatsSorted.indexOf(hat));
        }

        List<Integer> visibleHatsIntegers = new ArrayList<>();
        for (T hat : visibleHats) {
            visibleHatsIntegers.add(allHatsSorted.indexOf(hat));
        }

        int guessHatInteger = integerPrisonersHatsStrategy.guessHat(heardHatsIntegers, visibleHatsIntegers);

        return allHatsSorted.get(guessHatInteger);
    }

}
