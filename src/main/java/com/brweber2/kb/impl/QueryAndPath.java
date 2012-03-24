/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.kb.impl;

import com.brweber2.kb.Knowledge;
import com.brweber2.rule.Goal;

import java.util.ArrayDeque;
import java.util.Deque;

public class QueryAndPath
{
    private Goal query;
    Deque<Knowledge> path = new ArrayDeque<Knowledge>();

    public QueryAndPath( Goal query )
    {
        this.query = query;
    }

    public QueryAndPath( Goal query, Deque<Knowledge> path )
    {
        this.query = query;
        this.path = path;
    }

    public Goal getQuery()
    {
        return query;
    }

    public Deque<Knowledge> getPath()
    {
        return new ArrayDeque<Knowledge>( path );
    }

    public QueryAndPath push( Knowledge clause )
    {
        Deque<Knowledge> q = getPath();
        q.add( clause );
        return new QueryAndPath( query, q );
    }
}
