package com.brweber2.kb.impl;

import com.brweber2.kb.Fact;
import com.brweber2.kb.Knowledge;
import com.brweber2.kb.KnowledgeBase;
import com.brweber2.kb.Query;
import com.brweber2.kb.QueryResult;
import com.brweber2.kb.Rule;
import com.brweber2.proofsearch.ProofSearch;
import com.brweber2.rule.Conjunction;
import com.brweber2.rule.Disjunction;
import com.brweber2.rule.Goal;
import com.brweber2.unify.Binding;
import com.brweber2.unify.UnifyResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class AKnowledgeBase implements KnowledgeBase, ProofSearch {
    
    List<Knowledge> clauses = new ArrayList<Knowledge>();
    
    public void assertKnowledge( Knowledge knowledge )
    {
        clauses.add(knowledge);
    }
    
    public QueryResult pose( Query query )
    {
        QueryResult queryResult = new AQueryResult();
        for (Knowledge clause : clauses) 
        {
            if ( clause instanceof Fact )
            {
                Fact fact = (Fact) clause;
                UnifyResult unifyResult = query.getTerm().unify( fact.getTerm() );
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
                    queryResult.add( rule, satisfy(rule.getBody().getGoals(), unifyResult.bindings()) );
                }
            }
            else
            {
                throw new RuntimeException("Unknown clause type for " + clause);
            }
        }
        return queryResult;
    }
    
    private QueryResult satisfy( List<Goal> goals, Binding binding )
    {
        QueryResult goalResult =  satisfy( goals.get(0), binding );
        if ( !goalResult.successful() )
        {
            return null;
        }
        for (UnifyResult unifyResult : goalResult.getUnifyResults()) {
            if ( more(goals) )
            {
                satisfy(getRestOf(goals), unifyResult.bindings() );
            }
            else
            {
                goalResult.add(binding);
            }
        }
        return goalResult;
    }

    private boolean more(List<Goal> goals) {
        return goals != null && goals.size() > 1;
    }

    private List<Goal> getRestOf(List<Goal> goals) {
        if ( goals == null || goals.isEmpty() )
        {
            return new ArrayList<Goal>();
        }
        return goals.subList(1,goals.size());
    }

    private QueryResult satisfy( Goal goal, Binding binding )
    {
        if ( goal instanceof Conjunction )
        {
            Conjunction conjunction = (Conjunction) goal;
            List<Goal> conjunctionGoals = new ArrayList<Goal>();
            conjunctionGoals.add( conjunction.getLeft() );
            conjunctionGoals.add( conjunction.getRight() );
            return satisfy(conjunctionGoals,binding);
        }
        else if ( goal instanceof Disjunction )
        {
            Disjunction disjunction = (Disjunction) goal;
            return satisfy(disjunction.getLeft(),binding).add(satisfy(disjunction.getRight(),binding));
        }
        else
        {
            return pose(getQueryFor(goal));
        }
    }

    private Query getQueryFor(Goal goal) {
        return goal.getQuery();
    }


}
