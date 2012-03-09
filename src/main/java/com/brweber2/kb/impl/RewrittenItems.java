package com.brweber2.kb.impl;

import com.brweber2.kb.Rule;
import com.brweber2.rule.Conjunction;
import com.brweber2.rule.Disjunction;
import com.brweber2.rule.Goal;
import com.brweber2.term.Term;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class RewrittenItems {
    
    private Goal goal;
    private Term ruleHead;
    private Collection<Deque<Goal>> goals = new ArrayList<Deque<Goal>>();
    
    public RewrittenItems( Goal goal, Rule rule )
    {
        // todo re-write variables in these two...
        this.goal = goal;
        this.ruleHead = rule.getHead().getTerm();

        Deque<Goal> currentGoals = new ArrayDeque<Goal>();
        for ( Goal newGoal : rule.getBody().getGoals() )
        {
            if ( newGoal instanceof Conjunction )
            {
                Conjunction conjunction = (Conjunction) newGoal;
            }
            else if ( newGoal instanceof Disjunction )
            {
                Disjunction conjunction = (Disjunction) newGoal;
                goals.add( conjunction.getLeft() );
            }
            else
            {

            }
        }
        
    }

    public Goal getGoal() {
        return goal;
    }

    public Term getRuleHead() {
        return ruleHead;
    }

    public Collection<Deque<Goal>> getSetsOfNewGoals() {
        return goals;
    }
}
