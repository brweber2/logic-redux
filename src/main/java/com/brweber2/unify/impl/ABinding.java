/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.unify.impl;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;
import com.brweber2.unify.Unifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class ABinding implements Binding
{
    private static Logger log = Logger.getLogger( ABinding.class.getName() );
    
    private Binding parent = null;
    private Map<Variable,String> vars = new HashMap<Variable, String>(  );
    private Map<String,Term> values = new HashMap<String, Term>(  );
    private Set<Variable> toUnbinds = new HashSet<Variable>(  );


    public ABinding()
    {
    }

    public ABinding( Binding parent )
    {
        this.parent = parent;
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
        if ( parent != null )
        {
            value = parent.resolve( a );
        }
        vars.put( b, uuid );
        values.put( uuid, value );
    }

    public void instantiate( Term a, Variable b )
    {
        String uuid = UUID.randomUUID().toString();
        vars.put( b, uuid );
        values.put( uuid, a );
    }
    
    public void instantiate( Variable a, Term b )
    {
        String uuid = UUID.randomUUID().toString();
        Term value = null;
        if ( parent != null )
        {
            value = parent.resolve( a );
        }
        Unifier unifier = new Unify();
        com.brweber2.unify.UnifyResult result = unifier.unify( value, b );
        if (result.succeeded() )
        {
            vars.put( a, uuid );
            values.put( uuid, b );
        }
        else
        {
            throw new RuntimeException( "wtf" );
        }
    }

    public Term resolve( Variable a )
    {
        if ( isBound( a ) )
        {
            return values.get( vars.get( a ) );
        }
        else if ( parent != null )
        {
            return parent.resolve( a );
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
