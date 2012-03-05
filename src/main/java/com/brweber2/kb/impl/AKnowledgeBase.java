package com.brweber2.kb.impl;

import com.brweber2.kb.Fact;
import com.brweber2.kb.Knowledge;
import com.brweber2.kb.KnowledgeBase;
import com.brweber2.kb.Query;
import com.brweber2.kb.QueryResult;
import com.brweber2.kb.Rule;
import com.brweber2.rule.Conjunction;
import com.brweber2.rule.Disjunction;
import com.brweber2.rule.Goal;
import com.brweber2.unify.Binding;
import com.brweber2.unify.UnifyResult;
import com.brweber2.unify.impl.Bindings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class AKnowledgeBase implements KnowledgeBase {
    
    List<Knowledge> clauses = new ArrayList<Knowledge>();
    
    public void assertKnowledge( Knowledge knowledge )
    {
        clauses.add(knowledge);
    }

    public QueryResult pose( Query query )
    {
        return pose( query, new Bindings() );
    }
    
    public QueryResult pose( Query query, Binding binding )
    {
        QueryResult queryResult = new AQueryResult();
        for (Knowledge clause : clauses) {
            if ( clause instanceof Fact )
            {
                Fact fact = (Fact) clause;
                UnifyResult unifyResult = query.getTerm().unify( fact.getTerm(), binding );
                if ( unifyResult.succeeded() )
                {
                    queryResult.add( fact, unifyResult );
                }
            }
            else if ( clause instanceof Rule )
            {
                Rule rule = (Rule) clause;
                UnifyResult unifyResult = query.getTerm().unify( rule.getHead().getTerm() );
                if ( unifyResult.succeeded() )
                {
                    Binding headBinding = unifyResult.bindings();
                    if ( satisfy(rule.getBody().getGoals(), headBinding, queryResult) )
                    {
                        queryResult.add( rule, unifyResult );
                    }
                }
            }
            else
            {
                throw new RuntimeException("Unknown clause type for " + clause);
            }
        }
        return queryResult;
    }
    
    private boolean satisfy( List<Goal> goals, Binding binding, QueryResult queryResult )
    {
        for (Goal goal : goals) {
            if ( !satisfy( goal,  binding, queryResult ) )
            {
                return false;
            }
        }
        return true;
    }
    
    private boolean satisfy( Goal goal, Binding binding, QueryResult queryResult )
    {
        if ( goal instanceof Conjunction )
        {
            Conjunction conjunction = (Conjunction) goal;
            return satisfy(conjunction.getLeft(),binding,queryResult) && satisfy(conjunction.getRight(),binding,queryResult);
        }
        else if ( goal instanceof Disjunction )
        {
            Disjunction disjunction = (Disjunction) goal;
            return satisfy(disjunction.getLeft(),binding,queryResult) || satisfy(disjunction.getRight(),binding,queryResult);
        }
        else
        {
            QueryResult goalResult = pose( goal.asQuery(), binding.newBinding() );

        }
        
    }
}
