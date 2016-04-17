package com.github.prisonershats.strategies;

import static java.util.stream.Collectors.toCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import com.github.prisonershats.Strategy;

/**
 * Solves the Prisoners' Hats problem by looking at the chain of prisoners, taking hat numbers as prisoners positions.
 * 
 * @see https://www.reddit.com/r/MattParker/comments/49xq2n/puzzle_prisoners_in_hats_puzzle_two_variations/d12il1k
 * @author didier
 *
 */
public class PrisonersChainStrategy implements Strategy<Integer> {

	@Override
	public Integer guessHat(List<Integer> heardHats, List<Integer> visibleHats) {
		int myPosition = heardHats.size();
		int numPrisoners = myPosition + visibleHats.size() + 1;
		int numHats = numPrisoners + 1; // takes the missing hat into account
		// hats go from 0 to largestHat, which is also the number of prisoners
		int largestHat = numPrisoners;

		// compute the 2 unknown hats
		Set<Integer> unknownHatsSet = IntStream.range(0, numHats).boxed().collect(toCollection(HashSet::new));
		unknownHatsSet.removeAll(heardHats);
		unknownHatsSet.removeAll(visibleHats);
		assert unknownHatsSet.size() == 2;
		Integer[] unknownHats = unknownHatsSet.stream().sorted().toArray(Integer[]::new);
		int smallUnknown = unknownHats[0];
		int largeUnknown = unknownHats[1];
		IntUnaryOperator getHat = i -> getHatAt(i, heardHats, visibleHats);

		boolean[] encounteredPrisoners = new boolean[numPrisoners];
		if (myPosition == 0) {
			// first prisoner
			// compute the number of loops that I see
			final int leadsTo0;
			final int leadsToLargest;
			if (smallUnknown == 0 || largeUnknown == largestHat) {
				leadsTo0 = smallUnknown;
				leadsToLargest = largeUnknown;
			} else {
				int target = getChainEnd(smallUnknown, myPosition, numPrisoners, getHat, encounteredPrisoners);
				if (target == myPosition) {
					leadsTo0 = smallUnknown;
					leadsToLargest = largeUnknown;
				} else {
					leadsTo0 = largeUnknown;
					leadsToLargest = smallUnknown;
				}
			}

			int loops = getLoopCount(myPosition, numPrisoners, getHat, encounteredPrisoners);
			return loops % 2 == 0 ? leadsTo0 : leadsToLargest;
		} else {
			// not the first prisoner
			// first check where the chain starting from what the first prisoner
			// said leads
			int firstChainTarget = getChainEnd(heardHats.get(0), myPosition, numPrisoners, getHat, encounteredPrisoners);
			int smallTarget = getChainEnd(smallUnknown, myPosition, numPrisoners, getHat, encounteredPrisoners);
			int loops = getLoopCount(myPosition, numPrisoners, getHat, encounteredPrisoners);
			if (firstChainTarget == myPosition) {
				// I am part of the  chain that leads to one of the missing hats of the first prisoners!
				// I see the same number of loops than the first prisoner.
				if (loops % 2 == 0) {
					// First said a number that lead to 0... through me.
					// If smallUnknown leads to 0, it is my hat.
					return smallTarget == 0 ? smallUnknown : largeUnknown;
				} else {
					// First said a number that lead to numPrisoners... through me.
					// If smallUnknown leads to 0, it is not my hat.
					return smallTarget == 0 ? largeUnknown : smallUnknown;
				}
			} else {
				// I can deduce the parity of the number of loops that 1 was seeing!
				// Also, one of the unknown hats is part of a loop.
				if ((loops % 2 == 0) == (firstChainTarget == 0)) {
					// I see the same number of loops as the first prisoner, so I am not part of one of those loops.
					// One of the unknown hats leads to 0 or numHats, and the other leads to me (so it is not mine).
					return smallTarget == myPosition ? largeUnknown : smallUnknown;
				} else {
					// I don't see the same number of loops as the first prisoner, so I am part of one of those loops.
					// The unknown hats that leads to me is thus mine.
					return smallTarget == myPosition ? smallUnknown : largeUnknown;
				}
			}
		}
	}

	private int getLoopCount(int myPosition, int numPrisoners, IntUnaryOperator getHat, boolean[] encounteredPrisoners) {
		int loops = 0;
		for (int i = 0; i < numPrisoners; i++) {
			if (i != myPosition && !encounteredPrisoners[i]) {
				// note that getChainEnd does not return -1 if it leads to 0
				int target = getChainEnd(i, myPosition, numPrisoners, getHat, encounteredPrisoners);
				if (target == -1) {
					loops++;
				}
			}
		}
		return loops;
	}

	/**
	 * Computes where the chain starting at {@code start} leads:
	 * <ul>
	 * 	<li>if it leads to 0, returns 0;</li>
	 * 	<li>if it leads to {@code myPosition} (unknown hat), returns {@code myPosition};</li>
	 * 	<li>if it leads to {@code numPrisoners} (unknown hat), returns {@code numPrisoners};</li>
	 * 	<li>else, the chain forms a loop: return {@code -1}.</li>
	 * </ul>
	 * @param start the starting point of the chain to compute
	 * @param myPosition the position of the current prisoner
	 * @param numPrisoners the total number of prisoners
	 * @param getHat an operator that computes the hat of a prisoner from his position
	 * @param encounteredPrisoners TODO
	 * @return the end of the chain
	 */
	private int getChainEnd(int start, int myPosition, int numPrisoners, IntUnaryOperator getHat, boolean[] encounteredPrisoners) {
		int cur = start;
		do {
			if (cur == 0 || cur == myPosition || cur == numPrisoners) {
				return cur;
			}
			encounteredPrisoners[cur] = true;
			cur = getHat.applyAsInt(cur);
		} while (cur != start);
		return -1;
	}

	private int getHatAt(int pos, List<Integer> heardHats, List<Integer> visibleHats) {
		if (pos < heardHats.size()) {
			return heardHats.get(pos);
		}
		return visibleHats.get(pos - 1 - heardHats.size());
	}
}
