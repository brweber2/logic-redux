package com.brweber2.term.impl;

import com.brweber2.term.Numeric;
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
public class AnNumeric implements Numeric {
    private final String number;

    public AnNumeric(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public UnifyResult unify(Term other) {
        return unify(other, new Bindings());
    }

    public UnifyResult unify(Term other, Binding binding) {
        UnificationResult result = new com.brweber2.unify.impl.UnifyResult(binding);
        if ( other instanceof Numeric)
        {
            result.set(this.equals(other),this,(Numeric)other);
        }
        else if ( other instanceof Variable)
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
}
