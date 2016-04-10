package com.github.prisonershats.generators;

import com.github.prisonershats.GenericHatsGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public abstract class BaseGenericHatsGenerator<T> implements GenericHatsGenerator<T> {

	private static final Logger LOG = LoggerFactory.getLogger(BaseGenericHatsGenerator.class);
	protected final Random random;

	private Set<T> allHats;
	private List<T> hats;

	public BaseGenericHatsGenerator(Random random) {
		this.random = random;
	}

	public void generate(int prisonersNumber) {
		allHats = generateAllHats(prisonersNumber);
		hats = new ArrayList<>(allHats);
		Collections.shuffle(hats, random);
		LOG.debug("removed hat: " + hats.get(prisonersNumber));
		hats.remove(prisonersNumber);
	}

	protected abstract Set<T> generateAllHats(int prisonersNumber);

	@Override
	public List<T> getHats() {
		return hats;
	}

	@Override
	public Set<T> getAllHats() {
		return allHats;
	}

}