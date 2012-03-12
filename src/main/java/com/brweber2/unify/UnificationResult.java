package com.brweber2.unify;

import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Numeric;
import com.brweber2.term.Term;
import com.brweber2.term.Variable;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface UnificationResult extends UnifyResult {
    void set(boolean succeeded, Atom a, Atom b);
    void set(boolean succeeded, Numeric a, Numeric b);
    void set(boolean succeeded, Variable a, Term b);
    void set(boolean succeeded, ComplexTerm a, ComplexTerm b);

    void set( boolean succeeded);
    void fail(Term a, Term b);
}
