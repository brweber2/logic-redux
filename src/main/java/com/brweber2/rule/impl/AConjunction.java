/**
 * @author brweber2
 *         Copyright: 2012
 */
package com.brweber2.rule.impl;

import com.brweber2.rule.Conjunction;
import com.brweber2.rule.Goal;

public class AConjunction implements Conjunction
{
    private Goal left;
    private Goal right;

    public AConjunction( Goal left, Goal right )
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

}
