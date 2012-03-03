package com.brweber2.unify;

import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Numeric;
import com.brweber2.term.Term;

import java.util.Map;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface UnificationResult extends UnifyResult, Binding {
    void set(boolean succeeded, Atom a, Atom b);
    void set(boolean succeeded, Numeric a, Numeric b);
    void set(boolean succeeded, ComplexTerm a, ComplexTerm b);

    void fail(Term a, Term b);
}
