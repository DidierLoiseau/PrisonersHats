package com.github.prisonershats.strategies;

import com.github.prisonershats.GenericStrategy;
import com.github.prisonershats.Strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class IntegerStrategyWrapper<T> implements GenericStrategy<T> {

    private Strategy<Integer> integerStrategy;

    public IntegerStrategyWrapper(Strategy<Integer> integerStrategy) {
        this.integerStrategy = integerStrategy;
    }

    @Override
    public T guessHat(List<T> heardHats, List<T> visibleHats, Set<T> allHats) {

        List<T> allHatsSorted = new ArrayList<>(allHats);
        Collections.sort(allHatsSorted, new IdentityHashCodeComparator());

        List<Integer> heardHatsIntegers = new ArrayList<>();
        for (T hat : heardHats) {
            heardHatsIntegers.add(allHatsSorted.indexOf(hat));
        }

        List<Integer> visibleHatsIntegers = new ArrayList<>();
        for (T hat : visibleHats) {
            visibleHatsIntegers.add(allHatsSorted.indexOf(hat));
        }

        int guessHatInteger = integerStrategy.guessHat(heardHatsIntegers, visibleHatsIntegers);

        return allHatsSorted.get(guessHatInteger);
    }

}
