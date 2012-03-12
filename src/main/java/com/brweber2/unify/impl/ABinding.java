/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.unify.impl;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class ABinding implements Binding
{
    private static Logger log = Logger.getLogger( ABinding.class.getName() );

    private Map<Variable,String> vars = new HashMap<Variable, String>(  );
    private Map<String,Term> values = new HashMap<String, Term>(  );

    public ABinding()
    {
    }

    public ABinding(RuleBinding binding)
    {
        vars.putAll( binding.vars );
        values.putAll( binding.values );
    }

    public boolean isBound( Variable a )
    {
        return vars.containsKey( a );
    }

    public Set<Variable> getVariables()
    {
        return vars.keySet();
    }

    public void shareValues( Variable a, Variable b )
    {
        String uuid = UUID.randomUUID().toString();
        Term value = null;
        if ( isBound( a ) )
        {
            vars.put( b,vars.get( a ) ); // point b at a
        }
        else
        {
            vars.put( a, uuid );
            vars.put( b, uuid );
            values.put( uuid, value );
        }

    }

    public void instantiate( Term a, Variable b )
    {
        String uuid = UUID.randomUUID().toString();
        vars.put( b, uuid );
        values.put( uuid, a );
    }
    
    public void instantiate( Variable a, Term b )
    {
        instantiate( b, a );
    }

    public Term resolve( Variable a )
    {
        if ( isBound( a ) )
        {
            return values.get( vars.get( a ) );
        }
        return null;
    }

    public void dumpVariables()
    {
        for ( Variable variable : vars.keySet() )
        {
            Term value = resolve( variable );
            log.info( variable + ": " + value );
        }
    }
}