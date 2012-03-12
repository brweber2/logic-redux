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
import com.brweber2.unify.impl.RuleBinding;
import com.brweber2.unify.impl.Unify;

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
    private Rule rule;
    private Collection<Deque<Goal>> goals = new ArrayList<Deque<Goal>>();
    
    public RewrittenItems( Goal goal, Rule rule )
    {
        this.goal = goal;
        this.rule = rule;
    }

    private void rewrite(Binding binding)
    {
        Deque<Goal> currentGoals = new ArrayDeque<Goal>();
        RuleBody newGoal = rule.getBody();
        while ( newGoal instanceof Conjunction || newGoal instanceof Disjunction )
        {
            if ( newGoal instanceof Conjunction )
            {
                Conjunction conjunction = (Conjunction) newGoal;
                Goal leftTerm = (Goal) AKnowledgeBase.rewriteGoal( conjunction.getLeft(), binding );
                currentGoals.add( leftTerm );
                newGoal = conjunction.getRight();
            }
            else
            {
                Disjunction disjunction = (Disjunction) newGoal;
                Goal leftTerm = (Goal) AKnowledgeBase.rewriteGoal( disjunction.getLeft(), binding );
                currentGoals.add( leftTerm );
                goals.add( currentGoals );
                currentGoals = new ArrayDeque<Goal>();
                newGoal = disjunction.getRight();
            }
        }
        Goal lastTerm = (Goal) AKnowledgeBase.rewriteGoal( (Goal) newGoal, binding );
        currentGoals.add( lastTerm );
        goals.add( currentGoals );
    }
    
    public UnifyResult getUnifyResult(Binding binding)
    {
        Unifier unifier = new Unify();
        UnifyResult unifyResult = unifier.unify( goal, (Term) rule.getHead(), new RuleBinding(binding) );
        rewrite(unifyResult.bindings());
        return unifyResult;
    }

    public Goal getGoal() {
        return goal;
    }

    public Fact getRuleHead() {
        return rule.getHead();
    }

    public Collection<Deque<Goal>> getSetsOfNewGoals() {
        return goals;
    }
}
