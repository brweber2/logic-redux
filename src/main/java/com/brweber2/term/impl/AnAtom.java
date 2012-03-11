package com.brweber2.term.impl;

import com.brweber2.kb.Functor;
import com.brweber2.kb.impl.AFunctor;
import com.brweber2.term.Atom;
import com.brweber2.term.Term;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class AnAtom implements Atom {
    private final String atom;

    public AnAtom(String atom) {
        this.atom = atom;
    }

    public String getAtom() {
        return atom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnAtom anAtom = (AnAtom) o;

        if (!atom.equals(anAtom.atom)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return atom.hashCode();
    }

    public Term getTerm()
    {
        return this;
    }

    @Override
    public String toString()
    {
        return atom;
    }

    public Functor getFunctor()
    {
        return new AFunctor(atom,0);
    }
}
