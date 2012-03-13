package com.brweber2.term;

import com.brweber2.kb.Fact;
import com.brweber2.rule.Goal;
import com.brweber2.rule.RuleBody;

import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface ComplexTerm extends Term, Fact
{
    int getArity();
    List<Term> getTerms();
}
