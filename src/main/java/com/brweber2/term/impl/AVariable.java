package com.brweber2.term.impl;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.UnificationResult;
import com.brweber2.unify.UnifyResult;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class AVariable implements Variable {
    private final String variable;

    public AVariable(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public UnifyResult unify(Term other) {
        UnificationResult result = new com.brweber2.unify.impl.UnifyResult();
        if ( other instanceof Variable )
        {
            result.shareValues(this, (Variable) other);
        }
        else
        {
            result.instantiate(this,other);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AVariable aVariable = (AVariable) o;

        if (!variable.equals(aVariable.variable)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return variable.hashCode();
    }
}
