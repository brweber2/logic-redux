/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.kb.impl;

import com.brweber2.kb.KnowledgeBase;
import com.brweber2.rule.Goal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GoalList
{
    KnowledgeBase kb;
    private final List<Goal> goals = new ArrayList<Goal>();
    private final Map<Goal,Clauses> clausesMap = new HashMap<Goal, Clauses>();
    private Iterator<Goal> iterator;

    public GoalList( KnowledgeBase kb )
    {
        this.kb = kb;
        iterator = goals.iterator();
    }

    public boolean isEmpty()
    {
        return goals.isEmpty();
    }

    public void add( Goal astGoal )
    {
        goals.add( astGoal );
        iterator = goals.iterator();
    }

    public boolean haveMore()
    {
        return iterator.hasNext();
    }
    
    public Goal getNext( )
    {
        Goal next = iterator.next();
        clausesMap.put( next, new Clauses( kb.getClauses( next ) ) );
        return next;
    }

    public Clauses getNextClause(Goal goal)
    {
        return clausesMap.get( goal );
    }
}
