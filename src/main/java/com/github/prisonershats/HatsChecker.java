package com.github.prisonershats;

import java.util.List;

/**
 * A checker to count the number of the deaths among the prisoners.
 *
 * @param <T> the type of the hats to check
 * @author Didier Loiseau
 */
public interface HatsChecker<T> {
	/**
	 * Counts the deaths among prisoners.
	 * 
	 * @param realHats the real hats
	 * @param saidHats the hats said by the prisoners
	 * @return the number of deathts
	 */
	int deathCount(List<T> realHats, List<T> saidHats);
}
