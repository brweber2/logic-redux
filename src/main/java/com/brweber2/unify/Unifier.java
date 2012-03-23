package com.brweber2.unify;

import com.brweber2.term.Term;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface Unifier {
    UnifyResult unifyRuleHead( Term a, Term b, Binding binding );
    UnifyResult unify( Term a, Term b, Binding binding );
    UnifyResult unify( Term a, Term b );
}
