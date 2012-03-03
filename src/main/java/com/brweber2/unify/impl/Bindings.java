package com.brweber2.unify.impl;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class Bindings implements Binding {
    
    private Map<Variable,String> lookups = new HashMap<Variable,String>();
    private Map<String,Term> values = new HashMap<String,Term>();
    
    public boolean isBound( Variable a )
    {
        return lookups.containsKey(a) && values.containsKey(lookups.get(a));
    }

    public void shareValues(Variable a, Variable b) {
        String uuid = UUID.randomUUID().toString();
        lookups.put(a,uuid);
        lookups.put(b,uuid);
    }

    public void instantiate(Variable a, Term b) {
        String uuid = UUID.randomUUID().toString();
        lookups.put(a,uuid);
        values.put(uuid,b);
    }
}
