/**
 * @author brweber2
 *         Copyright: 2012
 */
package com.brweber2.rule.impl;

import com.brweber2.kb.Fact;
import com.brweber2.kb.Rule;
import com.brweber2.rule.RuleBody;

public class ARule implements Rule
{
    private Fact head;
    private RuleBody goal;

    public ARule( Fact head, RuleBody goal )
    {
        this.head = head;
        this.goal = goal;
    }

    public Fact getHead()
    {
        return head;
    }

    public RuleBody getBody()
    {
        return goal;
    }

    @Override
    public String toString()
    {
        return head + " :- " + goal + ".";
    }
}
