package com.brweber2.unify.impl;

import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Numeric;
import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.UnificationResult;
import com.brweber2.unify.Unifier;

import java.util.List;

/**
 * @deprecated
 * @author brweber2
 *         Copyright: 2012
 */
public class Unify implements Unifier {

    @Override
    public UnifyResult unify(Term a, Term b) {
        UnifyResult result = new UnifyResult();
        unify( result, a, b );
        return result;
    }

    public void unify(UnificationResult unificationResult, Term a, Term b) {

        if ( a instanceof Atom && b instanceof Atom )
        {
            unificationResult.set(a.equals(b),(Atom)a,(Atom)b);
        }
        else if ( a instanceof Numeric && b instanceof Numeric )
        {
            unificationResult.set(a.equals(b),(Numeric)a,(Numeric)b);
        }
        else if ( a instanceof Variable && b instanceof Variable )
        {
            unificationResult.shareValues((Variable)a,(Variable)b);
        }
        else if ( a instanceof Variable )
        {
            unificationResult.instantiate((Variable)a,b);
        }
        else if ( b instanceof Variable )
        {
            unificationResult.instantiate((Variable)b,a);
        }
        else if ( a instanceof ComplexTerm && b instanceof ComplexTerm )
        {
            unificationResult.set( unify(unificationResult,(ComplexTerm) a,(ComplexTerm) b), (ComplexTerm) a, (ComplexTerm) b);
        }
        else
        {
            unificationResult.fail(a,b);
        }
    }

    private boolean unify( UnificationResult unificationResult, ComplexTerm a, ComplexTerm b )
    {
        if ( a.getFunctor().equals(b.getFunctor()) )
        {
            if ( a.getArity() == b.getArity() )
            {
                return argsUnify(unificationResult, a, b);
            }
        }
        return false;
    }

    private boolean argsUnify(UnificationResult unificationResult, ComplexTerm a, ComplexTerm b) {
        List<Term> aTerms = a.getTerms();
        List<Term> bTerms = b.getTerms();
        for ( int i = 0; i < a.getArity(); i++ )
        {
            unify(unificationResult,aTerms.get(i),bTerms.get(i));
            if ( !unificationResult.succeeded() )
            {
                return false;
            }
        }
        return true;
    }
}
