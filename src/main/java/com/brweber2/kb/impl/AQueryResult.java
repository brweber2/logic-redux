package com.brweber2.kb.impl;

import com.brweber2.kb.Fact;
import com.brweber2.kb.Knowledge;
import com.brweber2.kb.QueryResult;
import com.brweber2.kb.Rule;
import com.brweber2.unify.Binding;
import com.brweber2.unify.UnifyResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class AQueryResult implements QueryResult {

    private List<UnifyResult> results = new ArrayList<UnifyResult>();
    
    @Override
    public void add(Knowledge knowledge, UnifyResult unifyResult) {
        // todo prompt for more here if we have a match?
        // print result
        // read for ';' if found carry on until next result...
        // else throw exception to abruptly end this business...
    }

    @Override
    public void add(Fact fact, UnifyResult unifyResult) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void add(Rule rule, QueryResult queryResult) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public QueryResult add(QueryResult queryResult) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void add(Binding binding) {
        results.add( new com.brweber2.unify.impl.UnifyResult(binding) );
    }

    @Override
    public boolean successful()
    {
        return !getUnifyResults().isEmpty();
    }

    @Override
    public List<UnifyResult> getUnifyResults() {
        return results;
    }
}
