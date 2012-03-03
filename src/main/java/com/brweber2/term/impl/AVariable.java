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
}
