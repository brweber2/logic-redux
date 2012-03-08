package com.brweber2.kb.impl;

import com.brweber2.kb.Fact;
import com.brweber2.kb.Knowledge;
import com.brweber2.kb.QueryResult;
import com.brweber2.kb.Rule;
import com.brweber2.term.Variable;
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

    // todo prompt for more here (each add method) if we have a match?
    // print result
    // read for ';' if found carry on until next result...
    // else throw exception to abruptly end this business...

    @Override
    public void add(Fact fact, UnifyResult unifyResult) {
        results.add(unifyResult);
    }

    @Override
    public void add(Rule rule, QueryResult queryResult) {
        // for each variable in rule.getHead() be sure to replace the variable with the value from queryResult.bindings()...
        for (Variable variable : rule.getHead().getVariables()) {
            queryResult.get
        }
    }

    @Override
    public QueryResult add(QueryResult queryResult) {
        results.addAll( queryResult.getUnifyResults() );
        return this;
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
