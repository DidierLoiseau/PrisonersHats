package com.github.prisonershats;

import java.util.List;
import java.util.Set;

public interface GenericStrategy<T> {

	T guessHat(List<T> heardHats, List<T> visibleHats, Set<T> allHats);

}
