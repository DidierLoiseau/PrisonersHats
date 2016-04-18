package com.github.prisonershats.strategies;

import com.github.prisonershats.GenericStrategy;

import java.util.*;

public class PermutationsStrategy<T> implements GenericStrategy<T> {

    public T guessHat(List<T> heardHats, List<T> visibleHats, Set<T> allHats) {

        List<T> allHatsSorted = new ArrayList<>(allHats);
        Collections.sort(allHatsSorted, new IdentityHashCodeComparator());

        List<T> missingHats = new ArrayList<>(allHats);
        missingHats.removeAll(heardHats);
        missingHats.removeAll(visibleHats);

        List<T> hats = new ArrayList<>();
        hats.addAll(heardHats);
        hats.add(missingHats.get(0));
        hats.addAll(visibleHats);
        hats.add(missingHats.get(1));

        int permutationCount = 0;
        for (int i = 0; i < hats.size(); i++) {
            while (!missingHats.contains(hats.get(i)) && !hats.get(i).equals(allHatsSorted.get(i))) {
                T temp = hats.get(allHatsSorted.indexOf(hats.get(i)));
                hats.set(allHatsSorted.indexOf(hats.get(i)), hats.get(i));
                hats.set(i, temp);
                permutationCount++;
            }
        }

        return allHatsSorted.get(hats.indexOf(missingHats.get(permutationCount%2)));
    }

}