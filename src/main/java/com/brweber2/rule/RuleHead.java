package com.brweber2.rule;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;

import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface RuleHead {
    Term getTerm();
    List<Variable> getVariables();
}
