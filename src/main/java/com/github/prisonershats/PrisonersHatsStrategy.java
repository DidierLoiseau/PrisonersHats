package com.github.prisonershats;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface PrisonersHatsStrategy<T> {
	public T guessHat(List<T> heardHats, List<T> visibleHats) throws IOException;
}
