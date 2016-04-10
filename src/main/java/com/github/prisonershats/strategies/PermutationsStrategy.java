package com.github.prisonershats.strategies;

import com.github.prisonershats.PrisonersHatsStrategy;

import java.util.List;

public class PermutationsStrategy implements PrisonersHatsStrategy<Integer> {

    public Integer guessHat(List<Integer> heardBefore, List<Integer> seenAhead) {
        // -1 = ?
        // -2 = X
        int[] full = new int[heardBefore.size() + seenAhead.size() + 2];
        for (int i = 0; i < heardBefore.size(); i++) {
            full[i] = heardBefore.get(i);
        }
        full[heardBefore.size()] = -1;
        for (int i = 0; i < seenAhead.size(); i++) {
            full[heardBefore.size() + 1 + i] = seenAhead.get(i);
        }
        full[heardBefore.size() + 1 + seenAhead.size()] = -2;

        int permutationCount = 0;
        for (int i = 0; i < full.length; i++) {
            while (full[i] != -1 && full[i] != -2 && full[i] != i) {
                int temp = full[full[i]]; // -2
                full[full[i]] = full[i];
                full[i] = temp;
                permutationCount++;
            }
        }
        if (permutationCount%2 == 0) {
            // find index where -2
            for (int i = 0; i < full.length; i++) {
                if (full[i] == -2) {
                    return i;
                }
            }
        }
        else {
            // find index where -1
            for (int i = 0; i < full.length; i++) {
                if (full[i] == -1) {
                    return i;
                }
            }
        }

        return 1;
    }

}
