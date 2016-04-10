package com.github.prisonershats;

import java.util.List;
import java.util.Set;

/**
 * A generator of prisoners hats of a given type.
 *
 * @param <T> the type of hats to generate
 * @author Didier Loiseau
 */
public interface GenericHatsGenerator<T> {

	public void generate(int prisonersCount);

	public List<T> getHats();

	public Set<T> getAllHats();

}