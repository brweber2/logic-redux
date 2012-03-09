package com.brweber2.term.impl;

import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;
import com.brweber2.unify.UnificationResult;
import com.brweber2.unify.UnifyResult;
import com.brweber2.unify.impl.Bindings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class AComplexTerm implements ComplexTerm {
    private final String functor;
    private final List<Term> terms = new ArrayList<Term>();

    public AComplexTerm(String functor, Term ... terms ) {
        this.functor = functor;
        if ( terms != null )
        {
            Collections.addAll(this.terms,terms);
        }
    }

    public String getFunctor() {
        return functor;
    }
    
    public int getArity()
    {
        return terms.size();
    }

    public List<Term> getTerms() {
        return terms;
    }

    public UnifyResult unify(Term other) {
        return unify(other, new Bindings());
    }

    public UnifyResult unify(Term other, Binding binding) {
        UnificationResult result = new com.brweber2.unify.impl.UnifyResult(binding);
        if ( other instanceof ComplexTerm )
        {
            result.set(this.equals(other),this,(ComplexTerm)other);
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

        AComplexTerm that = (AComplexTerm) o;

        if (!functor.equals(that.functor)) return false;
        if (!terms.equals(that.terms)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = functor.hashCode();
        result = 31 * result + terms.hashCode();
        return result;
    }

    public Term getTerm()
    {
        return this;
    }
}
