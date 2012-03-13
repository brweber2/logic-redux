/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.unify.impl;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;
import com.brweber2.unify.Unifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class RuleBinding implements Binding
{
    private static Logger log = Logger.getLogger( RuleBinding.class.getName() );

    Binding parent = null;
    Map<Variable,Variable> parentVariableMappings = new HashMap<Variable, Variable>();
    Map<Variable,String> vars = new HashMap<Variable, String>(  );
    Map<String,Term> values = new HashMap<String, Term>(  );

    public RuleBinding( Binding parent )
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
        parentVariableMappings.put(b,a);
        Term value = parent.resolve( a );
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
        boolean unifies = true;
        if ( parent.isBound( a ) )
        {
            unifies = new Unify().unify( parent.resolve( a ), b ).succeeded();
        }
        if ( ! unifies )
        {
            throw new FailedToUnifyException( a + ":" + b );
        }
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
            Variable varName = getVariableName(variable);
            Term value = resolve(variable);
            log.info( varName + ": " + value );
        }
        if ( parent != null )
        {
            log.info("****** DUMPING PARENT *********");
            parent.dumpVariables();
        }
    }

    // todo may have to expose this on the interface to enable walking up the binding hierarchy
    private Variable getVariableName(Variable variable) {
        if ( parentVariableMappings.containsKey(variable) )
        {
            return parentVariableMappings.get(variable);
        }
        return variable;
    }
}
