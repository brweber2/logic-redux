/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.kb.impl;

import com.brweber2.kb.Knowledge;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class Clauses
{
    Collection<Knowledge> clauses;
    Iterator<Knowledge> iterator;
    Deque<Iterator<Knowledge>> queue = new ArrayDeque<Iterator<Knowledge>>();
    
    public Clauses( Collection<Knowledge> clauses )
    {
        this.clauses = clauses;
        this.iterator = clauses.iterator();
    }

    public boolean hasMore()
    {
        return iterator.hasNext();
    }

    public Knowledge getNext()
    {
        return iterator.next();
    }

    public void markForBacktracking()
    {
        queue.offer(iterator);
        iterator = clauses.iterator();
    }
    
    public void backtrack()
    {
        iterator = queue.removeLast();
    }
}
