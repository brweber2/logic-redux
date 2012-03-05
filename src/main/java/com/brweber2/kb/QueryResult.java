package com.brweber2.kb;

import com.brweber2.unify.Binding;
import com.brweber2.unify.UnifyResult;

import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface QueryResult {
    void add( Fact fact, UnifyResult unifyResult );
    void add( Rule rule, QueryResult queryResult );
    QueryResult add( QueryResult queryResult );
    void add( Binding binding );

    boolean successful();
    List<UnifyResult> getUnifyResults();
}
