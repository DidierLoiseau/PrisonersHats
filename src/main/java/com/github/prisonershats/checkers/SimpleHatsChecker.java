package com.github.prisonershats.checkers;

import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

import com.github.prisonershats.HatsChecker;

/**
 * The simple hats checker only checks whether the said hats are the same as the real hats.
 * No additional checks are performed.
 *
 * @param <T> the type of hats
 * @author Didier Loiseau
 */
public class SimpleHatsChecker<T> implements HatsChecker<T> {

	@Override
	public int deathCount(List<T> hats, List<T> saidHats) {
		return deathCount(hats, saidHats, Objects::equals);
	}

	/**
	 * Checks the number of deaths based on the given survival check, called for each prisoner.
	 * 
	 * @param realHats the real hats
	 * @param saidHats the hats said by the prisoners
	 * @param survivalCheck the survival check, called for each prisoner with the real hat and the said hat.
	 * @return the number of deaths
	 */
	protected int deathCount(List<T> realHats, List<T> saidHats, BiPredicate<T, T> survivalCheck) {
		int deaths = 0;
		for (int i = 0; i < realHats.size(); i++) {
			if (!survivalCheck.test(realHats.get(i), saidHats.get(i))) {
				deaths++;
			}
		}
		return deaths;
	}

}
