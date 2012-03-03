package com.brweber2.term.impl;

import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Term;

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
}
