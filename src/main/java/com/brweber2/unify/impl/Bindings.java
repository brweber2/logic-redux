package com.brweber2.unify.impl;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class Bindings implements Binding {

    private Logger log = Logger.getLogger( Bindings.class.getName() );
    
    private Map<String,Variable> reverseLookups = new HashMap<String, Variable>();
    private Map<Variable,String> lookups = new HashMap<Variable,String>();
    private Map<String,Term> values = new HashMap<String,Term>();
    private Set<Variable> toUnbinds = new HashSet<Variable>(  );
    
    public boolean isBound( Variable a )
    {
        return lookups.containsKey(a) && values.containsKey(lookups.get(a));
    }

    public void unbind( Variable a )
    {
        values.remove( lookups.get( a ) );
        lookups.remove( a );
    }

    public void unbindMarked()
    {
        for ( Variable toUnbind : toUnbinds )
        {
            unbind( toUnbind );
        }
    }

    public void markToUnbind( Variable a )
    {
        toUnbinds.add( a );
    }

    public Set<Variable> getVariables()
    {
        return lookups.keySet();
    }

    public void shareValues(Variable a, Variable b) {
        String uuid = UUID.randomUUID().toString();
        reverseLookups.put(uuid,a);
        lookups.put(b,uuid);
    }

    public void instantiate(Variable a, Term b) {
        String uuid = UUID.randomUUID().toString();
        lookups.put(a,uuid);
        values.put(uuid,b);
    }
    
    public Term resolve(Variable a)
    {
        if ( isBound(a) )
        {
            return values.get(lookups.get(a));
        }
        return null;
    }

    public void shareBoundValues(Variable a, Variable b) {
        String uuid = lookups.get(a);
        lookups.put(b,uuid);
    }

    public Binding getCopy()
    {
        Bindings binding = new Bindings();
        binding.reverseLookups = new HashMap<String, Variable>( this.reverseLookups );
        binding.lookups = new HashMap<Variable, String>( this.lookups );
        binding.values = new HashMap<String, Term>( this.values );
        binding.toUnbinds = new HashSet<Variable>( this.toUnbinds );
        binding.unbindMarked();
        binding.toUnbinds.clear();
        return binding;
    }

    public void dumpVariables()
    {
        for ( Variable variable : lookups.keySet() )
        {
            String uuid = lookups.get( variable );
            // the value always comes from the current scope
            Term value = values.get( lookups.get( variable ) );
            // but the variable name might come from elsewhere....
            if ( reverseLookups.containsKey( uuid ) )
            {
                variable = reverseLookups.get( uuid );
            }
            log.info( variable + ": " + value );
        }
    }

    @Override
    public String toString()
    {
        return "Bindings{" +
                "reverseLookups=" + reverseLookups +
                ", lookups=" + lookups +
                ", values=" + values +
                '}';
    }
}
