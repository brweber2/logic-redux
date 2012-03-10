/**
 * @author brweber2
 *         Copyright: 2012
 */
package com.brweber2.unify.impl;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;

import java.util.Set;

public class WrappedBinding implements Binding
{
    private Binding binding;

    public WrappedBinding( Binding binding )
    {
        this.binding = binding.getCopy();
    }

    public boolean isBound( Variable a )
    {
        return binding.isBound( a );
    }

    public void unbind( Variable a )
    {
        binding.unbind( a );
    }

    public void unbindMarked()
    {
        binding.unbindMarked();
    }

    public void markToUnbind( Variable a )
    {
        binding.markToUnbind( a );
    }

    public Set<Variable> getVariables()
    {
        return binding.getVariables();
    }

    public void shareValues( Variable a, Variable b )
    {
        binding.shareValues( a, b );
    }

    public void instantiate( Variable a, Term b )
    {
        binding.instantiate( a, b );
    }

    public Term resolve( Variable a )
    {
        return binding.resolve( a );
    }

    public void shareBoundValues( Variable a, Variable b )
    {
        binding.shareBoundValues( a, b );
    }

    public Binding getCopy()
    {
        return binding.getCopy();
    }

    public void dumpVariables()
    {
        binding.dumpVariables();
    }
    
    @Override
    public String toString()
    {
        return binding.toString();
    }
}
