package com.brweber2.kb.impl;

import com.brweber2.kb.Knowledge;
import com.brweber2.kb.QueryResult;
import com.brweber2.unify.UnifyResult;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class AQueryResult implements QueryResult {
    @Override
    public void add(Knowledge knowledge, UnifyResult unifyResult) {
        // todo prompt for more here if we have a match?
        // print result
        // read for ';' if found carry on until next result...
        // else throw exception to abruptly end this business...
    }
}
