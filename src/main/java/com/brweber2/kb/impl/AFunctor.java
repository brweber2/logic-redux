/**
 * @author brweber2
 *         Copyright: 2012
 */
package com.brweber2.kb.impl;

import com.brweber2.kb.Functor;

public class AFunctor implements Functor
{
    private String functor;
    private int arity;

    public AFunctor( String functor, int arity )
    {
        this.functor = functor;
        this.arity = arity;
    }

    public String getFunctorString()
    {
        return functor;
    }

    public int getArity()
    {
        return arity;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        AFunctor aFunctor = (AFunctor) o;

        if ( arity != aFunctor.arity )
        {
            return false;
        }
        if ( !functor.equals( aFunctor.functor ) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = functor.hashCode();
        result = 31 * result + arity;
        return result;
    }

    @Override
    public String toString()
    {
        return "AFunctor{" +
                "functor='" + functor + '\'' +
                ", arity=" + arity +
                '}';
    }
}
