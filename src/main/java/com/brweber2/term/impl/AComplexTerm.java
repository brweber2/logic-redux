package com.brweber2.term.impl;

import com.brweber2.kb.Functor;
import com.brweber2.kb.impl.AFunctor;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Term;

import java.util.ArrayList;
import java.util.Arrays;
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
            for ( Term term : terms )
            {
                if ( term == null )
                {
                    System.err.println("functor is " + functor);
                    System.err.println("terms are " + Arrays.toString( terms ) );
                    RuntimeException e = new RuntimeException( );
                    e.printStackTrace();
                    System.exit( -1 );
                }
            }
            Collections.addAll(this.terms,terms);
        }
    }

    public Functor getFunctor() {
        return new AFunctor( functor, terms.size() );
    }
    
    public int getArity()
    {
        return terms.size();
    }

    public List<Term> getTerms() {
        return terms;
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

    @Override
    public String toString()
    {
        return functor + "(" + termsToString( terms ) + ")";
    }
    
    private String termsToString( List<Term> terms )
    {
        StringBuilder str = new StringBuilder(  );
        for ( int i = 0; i < terms.size() - 1; i++ )
        {
            str.append(terms.get( i ));
            str.append(",");
        }
        if ( !terms.isEmpty() )
        {
            str.append( terms.get( terms.size() - 1 ) );
        }
        return str.toString();
    }
}
