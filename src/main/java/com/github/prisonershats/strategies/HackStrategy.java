package com.github.prisonershats.strategies;

import com.github.prisonershats.PrisonersHatsStrategy;

import java.util.List;

public class HackStrategy implements PrisonersHatsStrategy<Integer> {

    private List<Integer> visibleHatsFromFirstPrisoner;

    public Integer guessHat(List<Integer> heardHats, List<Integer> visibleHats) {
        if (heardHats.isEmpty()) {
            visibleHatsFromFirstPrisoner = visibleHats;
            for (int i = 0; i < heardHats.size() + visibleHats.size() + 1; i++) {
                if (!heardHats.contains(i) && !visibleHats.contains(i)) {
                    return i;
                }
            }
        }
        else {
            if (visibleHatsFromFirstPrisoner != null && heardHats.size() + visibleHats.size() == visibleHatsFromFirstPrisoner.size()) {
                return visibleHatsFromFirstPrisoner.get(heardHats.size() - 1);
            }
        }
        return -1; // hara-kiri
    }

}
