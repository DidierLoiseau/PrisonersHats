package com.github.prisonershats.strategies;

import static java.util.function.Function.identity;

import java.util.List;
import java.util.stream.Stream;

import com.github.prisonershats.PrisonersHatsStrategy;

public class PermutationsStrategy implements PrisonersHatsStrategy<Integer> {

	@Override
	public Integer guessHat(List<Integer> heardBefore, List<Integer> seenAhead) {
		// initially:
		// my position = -1 (unknown hat we need to guess)
		// fake last position = -2 (the other unknown hat)
		int[] full = Stream.of(heardBefore.stream(), Stream.of(-1),
				seenAhead.stream(), Stream.of(-2))
				.flatMap(identity())
				.mapToInt(i -> i)
				.toArray();

		int permutationCount = 0;
		int minusOnePos = heardBefore.size();
		int minusTwoPos = full.length - 1;
		for (int i = 0; i < full.length; i++) {
			while (full[i] != -1 && full[i] != -2 && full[i] != i) {
				int temp = full[full[i]];
				full[full[i]] = full[i];
				full[i] = temp;
				permutationCount++;
			}
			if (full[i] == -1) {
				minusOnePos = i;
			} else if (full[i] == -2) {
				minusTwoPos = i;
			}
		}
		if (permutationCount % 2 == 0) {
			// keep an even parity:
			// replace -1 by the position it ended at, no more swap required.
			return minusOnePos;
		} else {
			// force an even parity:
			// replace -1 by the position at which -2 ended, so they would need
			// one more swap to make the parity even.
			return minusTwoPos;
		}
	}

}
