/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.term.impl;

import com.brweber2.term.Term;

import java.util.List;

public class Decompose implements Term
{
    private List<Term> left;
    private List<Term> right;

    public Decompose( List<Term> left, List<Term> right )
    {
        this.left = left;
        this.right = right;
    }

    public List<Term> getLeft()
    {
        return left;
    }

    public List<Term> getRight()
    {
        return right;
    }
}
