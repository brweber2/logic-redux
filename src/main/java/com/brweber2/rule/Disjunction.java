package com.brweber2.rule;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface Disjunction extends RuleBody {
    Goal getLeft();
    Goal getRight();
}
