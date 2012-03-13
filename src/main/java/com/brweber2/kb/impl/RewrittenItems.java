package com.brweber2.kb.impl;

import com.brweber2.kb.Fact;
import com.brweber2.kb.Rule;
import com.brweber2.rule.Conjunction;
import com.brweber2.rule.Disjunction;
import com.brweber2.rule.Goal;
import com.brweber2.rule.RuleBody;
import com.brweber2.term.Term;
import com.brweber2.unify.Binding;
import com.brweber2.unify.Unifier;
import com.brweber2.unify.UnifyResult;
import com.brweber2.unify.impl.ABinding;
import com.brweber2.unify.impl.RuleBinding;
import com.brweber2.unify.impl.Unify;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class RewrittenItems {
    
    private Goal goal;
    private Rule rule;
    private List<Deque<Goal>> goals = new ArrayList<Deque<Goal>>();
    
    public RewrittenItems( Goal goal, Rule rule )
    {
        this.goal = goal;
        this.rule = rule;
        rewrite();
    }

    private void rewrite()
    {
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
    
    public UnifyResult getUnifyResult(Binding binding)
    {
        return new Unify().unify( goal, (Term) rule.getHead(), binding );
    }

    public Goal getGoal() {
        return goal;
    }

    public Fact getRuleHead() {
        return rule.getHead();
    }

    public List<Deque<Goal>> getSetsOfNewGoals() {
        return goals;
    }
}
