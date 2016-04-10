package com.github.prisonershats.checkers;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * This checker extends the simple hats checker by making sure no 2 prisoners
 * say the same hat.
 *
 * @param <T>
 *            the type of hats to check
 * @author Didier Loiseau
 */
public class NonRepeatingHatsChecker<T> extends SimpleHatsChecker<T> {
	@Override
	public int deathCount(List<T> realHats, List<T> saidHats) {
		Set<T> alreadySaid = new HashSet<>();
		return deathCount(realHats, saidHats, (r, s) -> alreadySaid.add(s) && Objects.equals(r, s));
	}
}
