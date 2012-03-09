/**
 * @author brweber2
 *         Copyright: 2012
 */
package com.brweber2.unify.impl;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;

import java.util.Set;

public class RuleBinding implements Binding
{
    public boolean isBound( Variable a )
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<Variable> getVariables()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void shareValues( Variable a, Variable b )
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void instantiate( Variable a, Term b )
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Term resolve( Variable a )
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void shareBoundValues( Variable a, Variable b )
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Binding getCopy()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void dumpVariables()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
