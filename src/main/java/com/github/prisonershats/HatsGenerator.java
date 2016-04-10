package com.github.prisonershats;

import java.util.List;

/**
 * A generator of prisoners hats of a given type.
 *
 * @param <T> the type of hats to generate
 * @author Didier Loiseau
 */
public interface HatsGenerator<T> {
	/**
	 * Generates hats for the given number of prisoners.
	 * @param prisonersNumber the number of prisoners
	 * @return the generated hats
	 */
	public List<T> generate(int prisonersNumber);
}
