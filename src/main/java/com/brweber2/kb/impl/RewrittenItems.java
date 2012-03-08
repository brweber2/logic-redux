package com.brweber2.kb.impl;

import com.brweber2.kb.Query;
import com.brweber2.kb.Rule;
import com.brweber2.rule.Goal;
import com.brweber2.term.Term;

import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class RewrittenItems {
    
    private Term query;
    private Term ruleHead;
    private List<Goal> goals;
    
    public RewrittenItems( Query query, Rule rule )
    {
        query.getTerm();
        rule.getHead().getTerm();
    }

    public Term getQuery() {
        return query;
    }

    public Term getRuleHead() {
        return ruleHead;
    }

    public List<Goal> getGoals() {
        return goals;
    }
}
