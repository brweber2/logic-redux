package com.brweber2.unify.impl;

import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Numeric;
import com.brweber2.term.Term;
import com.brweber2.unify.Binding;
import com.brweber2.unify.UnificationResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class UnifyResult implements UnificationResult {
    
    private boolean successful = false;
    private Binding bindings = new Bindings();
    private Term left;
    private Term right;
    
    @Override
    public void set(boolean succeeded, Atom a, Atom b) {
        this.successful = succeeded;
        this.left = a;
        this.right = b;
    }

    @Override
    public void set(boolean succeeded, Numeric a, Numeric b) {
        this.successful = succeeded;
        this.left = a;
        this.right = b;
    }

    @Override
    public void set(boolean succeeded, ComplexTerm a, ComplexTerm b) {
        this.successful = succeeded;
        this.left = a;
        this.right = b;
    }

    @Override
    public void fail(Term a, Term b) {
        this.successful = false;
        this.left = a;
        this.right = b;
    }

    @Override
    public boolean succeeded() {
        return successful;
    }

    @Override
    public Binding bindings() {
        return bindings;
    }

    public Term getLeft() {
        return left;
    }

    public Term getRight() {
        return right;
    }
}
