package com.brweber2.term;

import com.brweber2.kb.Fact;
import com.brweber2.rule.Goal;

import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface ComplexTerm extends Term, Fact, Goal
{
    String getFunctor();
    int getArity();
    List<Term> getTerms();
}
