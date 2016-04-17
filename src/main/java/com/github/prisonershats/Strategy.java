package com.github.prisonershats;

import java.util.List;

public interface Strategy<T> {
	T guessHat(List<T> heardHats, List<T> visibleHats);
}
