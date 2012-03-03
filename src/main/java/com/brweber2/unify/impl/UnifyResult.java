package com.brweber2.unify.impl;

import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Numeric;
import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;
import com.brweber2.unify.UnificationResult;

import java.util.Set;

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
        this.bindings = new Bindings();
    }

    public UnifyResult(Binding bindings) {
        this.bindings = bindings;
    }

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

    @Override
    public boolean isBound(Variable a) {
        return bindings.isBound(a);
    }

    @Override
    public Set<Variable> getVariables() {
        return bindings.getVariables();
    }

    @Override
    public void shareValues(Variable a, Variable b) {
        if ( isBound(a) && isBound(b) )
        {
            this.successful = bindings.resolve(a).equals(bindings.resolve(b));
        }
        else if ( isBound(a) )
        {
            bindings.shareBoundValues(a,b);
            this.successful = true;
        }
        else if ( isBound(b) )
        {
            bindings.shareBoundValues(b,a);
            this.successful = true;
        }
        else
        {
            bindings.shareValues(a,b);
            this.successful = true;
        }
    }

    @Override
    public void instantiate(Variable a, Term b) {
        if ( isBound(a) )
        {
            if ( bindings.resolve(a).equals(b) )
            {
                this.successful = true;
                bindings.instantiate(a,b);
            }
            else
            {
                this.successful =false;
            }
        }
        else
        {
            bindings.instantiate(a,b);
            this.successful = true;
        }
    }

    @Override
    public Term resolve(Variable a) {
        return bindings.resolve(a);
    }

    @Override
    public void shareBoundValues(Variable a, Variable b) {
        bindings.shareBoundValues(a, b);
    }
}
