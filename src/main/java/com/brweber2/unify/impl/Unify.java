package com.brweber2.unify.impl;

import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Numeric;
import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.term.impl.AComplexTerm;
import com.brweber2.unify.Binding;
import com.brweber2.unify.UnificationResult;
import com.brweber2.unify.Unifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class Unify implements Unifier {

    public UnifyResult unify(Term a, Term b) {
        UnifyResult result = new UnifyResult(a,b);
        unify( result, a, b );
        return result;
    }

    public com.brweber2.unify.UnifyResult unifyRuleHead( Term a, Term b, Binding binding )
    {
        // replace any variables in b with the value from binding
        Term modifiedB = replaceVariables(b,binding);
        return unify( modifiedB, a, new ABinding() );
    }

    private Term replaceVariables( Term b, Binding binding )
    {
        if ( b instanceof Variable )
        {
            Variable varB = (Variable) b;
            if ( binding.isBound( varB ) && binding.resolve( varB ) != null )
            {
                return binding.resolve( varB );
            }
            return b;
        }
        else if ( b instanceof ComplexTerm )
        {
            ComplexTerm ct = (ComplexTerm) b;
            List<Term> newTerms = new ArrayList<Term>();
            for ( Term term : ( (ComplexTerm) b ).getTerms() )
            {
                newTerms.add( replaceVariables( term, binding ) );
            }
            return new AComplexTerm( ct.getFunctor().getFunctorString(), newTerms.toArray( new Term[newTerms.size()] ) );
        }
        else
        {
            return b;
        }
    }

    public UnifyResult unify(Term a, Term b, Binding binding ) {
        UnifyResult result = new UnifyResult(a,b,binding);
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
            boolean unifies;
            try
            {
                unificationResult.bindings().shareValues((Variable)a,(Variable)b);
                unifies = true;
            }
            catch ( FailedToUnifyException e )
            {
                unifies = false;
            }
            unificationResult.set( unifies, (Variable) a, b );
        }
        else if ( a instanceof Variable )
        {
//            boolean unifies;
//            try
//            {
//                unificationResult.bindings().instantiate( (Variable) a, b );
//                unifies = true;
//            }
//            catch ( FailedToUnifyException e )
//            {
//                unifies = false;
//            }
            unificationResult.set( true, (Variable) a, b );
        }
        else if ( b instanceof Variable )
        {
            boolean unifies;
            try
            {
                unificationResult.bindings().instantiate(a,(Variable)b);
                unifies = true;
            }
            catch ( FailedToUnifyException e )
            {
                unifies = false;
            }
            unificationResult.set( unifies, (Variable) b, a );
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
        if ( a.getFunctor().equals(b.getFunctor()) )  // this checks arity as well
        {
            return argsUnify(unificationResult, a, b);
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
