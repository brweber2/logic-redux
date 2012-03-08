package com.brweber2.kb.impl;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;

import java.util.Set;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class RewriteBinding implements Binding {
    @Override
    public boolean isBound(Variable a) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<Variable> getVariables() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void shareValues(Variable a, Variable b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void instantiate(Variable a, Term b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Term resolve(Variable a) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void shareBoundValues(Variable a, Variable b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
