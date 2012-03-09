package com.brweber2.rule;

import com.brweber2.kb.Functor;
import com.brweber2.term.Term;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface Goal extends RuleBody, Term
{
    Functor getFunctor();
}
