package com.brweber2.unify.impl;

import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Numeric;
import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;
import com.brweber2.unify.UnificationResult;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class UnifyResult implements UnificationResult {
    
    private boolean successful = false;
    private Binding bindings;
    private Term left;
    private Term right;

    public UnifyResult() {
        this.bindings = new ABinding();
    }

    public UnifyResult(Binding bindings) {
        this.bindings = bindings;
    }

    public void set(boolean succeeded, Atom a, Atom b) {
        this.successful = succeeded;
        this.left = a;
        this.right = b;
    }

    public void set(boolean succeeded, Numeric a, Numeric b) {
        this.successful = succeeded;
        this.left = a;
        this.right = b;
    }

    public void set( boolean succeeded, Variable a, Term b )
    {
        this.successful = succeeded;
        this.left = a;
        this.right = b;
    }

    public void set(boolean succeeded, ComplexTerm a, ComplexTerm b) {
        this.successful = succeeded;
        this.left = a;
        this.right = b;
    }

    public void fail(Term a, Term b) {
        this.successful = false;
        this.left = a;
        this.right = b;
    }

    public boolean succeeded() {
        return successful;
    }

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
