package com.brweber2.term.impl;

import com.brweber2.term.Variable;

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

    @Override
    public String toString()
    {
        return "@" + variable;
    }
}
