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
import com.brweber2.unify.impl.Unify;
import com.brweber2.unify.impl.WrappedBinding;

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
    private Binding binding;
    
    public RewrittenItems( Goal goal, Rule rule, Binding binding )
    {
        this.binding = binding;
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
    
    public UnifyResult getUnifyResult()
    {
        Unifier unifier = new Unify();
        return unifier.unify( goal, (Term) ruleHead, new RuleBinding(binding) );
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
