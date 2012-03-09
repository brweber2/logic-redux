/**
 * @author brweber2
 *         Copyright: 2012
 */
package com.brweber2.rule.impl;

import com.brweber2.rule.Disjunction;
import com.brweber2.rule.Goal;

public class ADisconjunction implements Disjunction
{
    private Goal left;
    private Goal right;

    public ADisconjunction( Goal left, Goal right )
    {
        this.left = left;
        this.right = right;
    }

    public Goal getLeft()
    {
        return left;
    }

    public Goal getRight()
    {
        return right;
    }

    @Override
    public String toString()
    {
        return left + "; " + right;
    }
}
