/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.term.impl;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;

public class Is implements Term
{
    private Variable variable;
    private Object value;

    public Is( Variable variable, Object value )
    {
        this.variable = variable;
        this.value = value;
    }

    public Variable getVariable()
    {
        return variable;
    }

    public Object getValue()
    {
        return value;
    }
}
