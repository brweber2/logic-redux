package com.brweber2.unify;

import com.brweber2.term.Term;
import com.brweber2.term.Variable;

import java.util.Set;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface Binding {

    boolean isBound( Variable a );
    Set<Variable> getVariables();
    void shareValues(Variable a, Variable b);
    void instantiate(Variable a, Term b);
    Term resolve(Variable a);
    void shareBoundValues(Variable a, Variable b);
}
