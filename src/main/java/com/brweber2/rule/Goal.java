package com.brweber2.rule;

import com.brweber2.kb.Query;
import com.brweber2.unify.Binding;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface Goal {
    Query getQuery(Binding binding); // any term really
}
