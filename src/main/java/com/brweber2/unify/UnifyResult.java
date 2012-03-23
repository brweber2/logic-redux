package com.brweber2.unify;

import com.brweber2.term.Term;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface UnifyResult {
    boolean succeeded();
    Binding bindings();
    Term getLeft();
    Term getRight();
}
