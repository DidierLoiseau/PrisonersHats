package com.github.prisonershats;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Tester {

	public static void main(String[] args) throws Exception {

		int n = 100;
		int testsCount = 1000;
		PrisonersHatsStrategy<Integer> solver = Tester::solve;

		double deathCount = 0;
		int maxDeaths = 0;
		Random random = new Random(/* 42 */);
		for (int test = 0; test < testsCount; test++) {
			if (test % 100 == 0) {
				System.out.println("Iteration " + test);
			}
			// initialise hats randomly
			List<Integer> hats = IntStream.range(0, n + 1).boxed().collect(toList());
			Collections.shuffle(hats, random);
			System.out.println("removed hat: " + hats.get(n));
			hats.remove(n);
			System.out.print("real hats: ");
			printList(hats);

			// ask hat number of each prisoner
			List<Integer> saidHats = new ArrayList<>();
			for (int prisoner = 0; prisoner < n; prisoner++) {
				List<Integer> visibleHats = hats.subList(prisoner + 1, n);
				int hatValueAnounced = solver.guessHat(saidHats, visibleHats);
				saidHats.add(hatValueAnounced);
			}
			System.out.print("said hats: ");
			printList(saidHats);

			// check hats == heardBefore
			int deaths = howManyDeaths(hats, saidHats);
			System.out.println("deaths: " + deaths);
			deathCount += deaths;
			System.out.println("----");
			maxDeaths = Math.max(maxDeaths, deaths);
		}
		System.out.println("mean deaths: " + deathCount / testsCount + ", max deaths: " + maxDeaths);

	}

    // Solver pour n = 2 :-)
//    private static int solve(List<Integer> heardBefore, List<Integer> seenAhead) throws Exception {
//        if (seenAhead.size() > 1) {
//            throw new Exception("ne sait pas résoudre pour n > 2");
//        }
//        if (seenAhead.size() == 1) {
//            return seenAhead.get(0);
//        }
//        else return heardBefore.get(0);
//    }

	// Solver de Xavier
	private static int solve(List<Integer> heardBefore, List<Integer> seenAhead) {
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

	private static int howManyDeaths(List<Integer> hats, List<Integer> saidHats) {
		int deaths = 0;
		for (int i = 0; i < hats.size(); i++) {
			if (!hats.get(i).equals(saidHats.get(i))) {
				deaths++;
			}
		}
		return deaths;
	}

	private static void printList(List<Integer> list) {
		System.out.println(list.stream()
				.map(i -> String.format("%3d", i))
				.collect(Collectors.joining(",")));
	}

}