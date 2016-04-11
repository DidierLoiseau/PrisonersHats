package com.github.prisonershats;

import java.util.Set;

public interface SetGenerator<T> {

	Set<T> generate(int size);

}