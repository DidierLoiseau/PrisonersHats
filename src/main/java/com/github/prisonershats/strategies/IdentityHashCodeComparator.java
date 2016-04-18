package com.github.prisonershats.strategies;

import java.util.Comparator;

public class IdentityHashCodeComparator implements Comparator<Object> {

    @Override
    public int compare(Object a, Object b) {
        int uniqueHashCodeA = System.identityHashCode(a);
        int uniqueHashCodeB = System.identityHashCode(b);
        return uniqueHashCodeA > uniqueHashCodeB ? 1 : uniqueHashCodeA < uniqueHashCodeB ? - 1 : 0;
    }

}
