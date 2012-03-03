package com.brweber2.term.impl;

import com.brweber2.term.Atom;
import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;
import com.brweber2.unify.UnificationResult;
import com.brweber2.unify.UnifyResult;
import com.brweber2.unify.impl.Bindings;

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
    public UnifyResult unify(Term other) {
        return unify(other, new Bindings());
    }

    @Override
    public UnifyResult unify(Term other, Binding binding) {
        UnificationResult result = new com.brweber2.unify.impl.UnifyResult(binding);
        if ( other instanceof Atom )
        {
            result.set(this.equals(other),this,(Atom)other);
        }
        else if ( other instanceof Variable )
        {
            result.instantiate((Variable) other, this);
        }
        else
        {
            result.fail(this,other);
        }
        return result;
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
}
