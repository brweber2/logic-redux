package com.brweber2.term.impl;

import com.brweber2.term.Atom;

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
}
