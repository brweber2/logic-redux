package com.brweber2.rule;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface Disjunction extends Goal {
    Goal getLeft();
    Goal getRight();
}
