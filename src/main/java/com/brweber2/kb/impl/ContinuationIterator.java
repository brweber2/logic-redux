/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.kb.impl;

import java.util.Iterator;
import java.util.List;

public class ContinuationIterator<T> implements Iterator<T>
{
    List<T> goals;
    private int index = 0;
    Iterator<T> iterator;

    public ContinuationIterator( List<T> goals )
    {
        this.goals = goals;
        iterator = goals.iterator();
    }

    public boolean hasNext()
    {
        return iterator.hasNext();
    }

    public T next()
    {
        index++;
        return iterator.next();
    }

    public void remove()
    {
        iterator.remove();
    }
    
    public ContinuationIterator<T> continuation()
    {
        return new ContinuationIterator<T>( goals.subList( index, goals.size() ) );
    }
}
