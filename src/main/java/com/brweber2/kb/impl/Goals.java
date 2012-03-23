/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.kb.impl;

import com.brweber2.kb.KnowledgeBase;
import com.brweber2.rule.Conjunction;
import com.brweber2.rule.Disjunction;
import com.brweber2.rule.Goal;

import java.util.ArrayList;
import java.util.Collection;

public class Goals
{
    Collection<GoalList> listOfGoalLists = new ArrayList<GoalList>();
    KnowledgeBase kb;
    private GoalList currentGoals;

    public Goals( KnowledgeBase kb )
    {
        this.kb = kb;
    }

    public Goals( KnowledgeBase kb, Goal astGoal )
    {
        this.kb = kb;
        currentGoals = new GoalList( kb );
        processGoal( astGoal );
        if ( !currentGoals.isEmpty() )
        {
            listOfGoalLists.add( currentGoals );
        }
    }

    public Collection<GoalList> getListOfGoalLists()
    {
        return listOfGoalLists;
    }

    private void processGoal( Goal astGoal )
    {
        if ( astGoal instanceof Disjunction )
        {
            Disjunction disjunction = (Disjunction) astGoal;
            processGoal( disjunction.getLeft() );
            listOfGoalLists.add( currentGoals );
            currentGoals = new GoalList( kb );
            processGoal( disjunction.getRight() );
        }
        else if ( astGoal instanceof Conjunction )
        {
            Conjunction conjunction = (Conjunction) astGoal;
            processGoal( conjunction.getLeft() );
            processGoal( conjunction.getRight() );
        }
        else
        {
            currentGoals.add( astGoal );
        }
    }

    public boolean isEmpty()
    {
        return listOfGoalLists.isEmpty();
    }
}
