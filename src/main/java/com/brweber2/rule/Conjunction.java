package com.brweber2.rule;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface Conjunction extends Goal {
    Goal getLeft();
    Goal getRight();
}