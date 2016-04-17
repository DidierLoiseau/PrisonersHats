package com.github.prisonershats.strategies;

import java.util.ArrayList;
import java.util.List;

import com.github.prisonershats.Strategy;

/**
 * A simple but efficient strategy based on the mean of the known hats.
 * 
 * @author Xavier
 */
public class MeanBasedStrategy implements Strategy<Integer> {

	@Override
	public Integer guessHat(List<Integer> heardBefore, List<Integer> seenAhead) {
		double mean = 0;
		for (Integer element : heardBefore) {
			mean += element;
		}
		for (Integer element : seenAhead) {
			mean += element;
		}
		mean /= heardBefore.size() + seenAhead.size();
		List<Integer> missings = new ArrayList<>();
		for (int i = 1; i <= heardBefore.size() + seenAhead.size() + 2; i++) {
			if (!heardBefore.contains(i) && !seenAhead.contains(i)) {
				missings.add(i);
			}
		}
		if (Math.abs(mean - missings.get(1)) < Math.abs(mean - missings.get(0))) {
			return missings.get(1);
		} else if (Math.abs(mean - missings.get(1)) == Math.abs(mean - missings.get(0))) {
			if (missings.get(1) < missings.get(0)) {
				return missings.get(1);
			} else {
				return missings.get(0);
			}
		} else {
			return missings.get(0);
		}
	}

}
