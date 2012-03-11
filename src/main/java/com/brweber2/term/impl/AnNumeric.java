package com.brweber2.term.impl;

import com.brweber2.kb.Functor;
import com.brweber2.kb.impl.AFunctor;
import com.brweber2.term.Numeric;
import com.brweber2.term.Term;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class AnNumeric implements Numeric {
    private final String number;

    public AnNumeric(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnNumeric anNumeric = (AnNumeric) o;

        if (!number.equals(anNumeric.number)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    public Term getTerm()
    {
        return this;
    }

    @Override
    public String toString()
    {
        return number;
    }

    public Functor getFunctor()
    {
        return new AFunctor( number, 0 );
    }
}
