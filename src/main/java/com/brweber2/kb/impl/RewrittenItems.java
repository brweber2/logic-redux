package com.brweber2.kb.impl;

import com.brweber2.kb.Fact;
import com.brweber2.kb.Rule;
import com.brweber2.rule.Conjunction;
import com.brweber2.rule.Disjunction;
import com.brweber2.rule.Goal;
import com.brweber2.rule.RuleBody;

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
    private Fact ruleHead;
    private Collection<Deque<Goal>> goals = new ArrayList<Deque<Goal>>();
    
    public RewrittenItems( Goal goal, Rule rule )
    {
        // todo re-write variables in these two...
        this.goal = goal;
        this.ruleHead = rule.getHead();

        Deque<Goal> currentGoals = new ArrayDeque<Goal>();
        RuleBody newGoal = rule.getBody();
        while ( newGoal instanceof Conjunction || newGoal instanceof Disjunction )
        {
            if ( newGoal instanceof Conjunction )
            {
                Conjunction conjunction = (Conjunction) newGoal;
                currentGoals.add( conjunction.getLeft() );
                newGoal = conjunction.getRight();
            }
            else
            {
                Disjunction disjunction = (Disjunction) newGoal;
                currentGoals.add( disjunction.getLeft() );
                goals.add( currentGoals );
                currentGoals = new ArrayDeque<Goal>();
                newGoal = disjunction.getRight();
            }
        }
        currentGoals.add( (Goal) newGoal );
        goals.add( currentGoals );
    }

    public Goal getGoal() {
        return goal;
    }

    public Fact getRuleHead() {
        return ruleHead;
    }

    public Collection<Deque<Goal>> getSetsOfNewGoals() {
        return goals;
    }
}
