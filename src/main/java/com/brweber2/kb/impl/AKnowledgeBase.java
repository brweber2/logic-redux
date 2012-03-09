package com.brweber2.kb.impl;

import com.brweber2.kb.Fact;
import com.brweber2.kb.Knowledge;
import com.brweber2.kb.KnowledgeBase;
import com.brweber2.kb.Rule;
import com.brweber2.proofsearch.ProofSearch;
import com.brweber2.rule.Goal;
import com.brweber2.term.Term;
import com.brweber2.unify.Binding;
import com.brweber2.unify.Unifier;
import com.brweber2.unify.UnifyResult;
import com.brweber2.unify.impl.Bindings;
import com.brweber2.unify.impl.Unify;
import com.brweber2.unify.impl.WrappedBinding;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class AKnowledgeBase implements KnowledgeBase, ProofSearch {
    
    List<Knowledge> clauses = new ArrayList<Knowledge>();
    Unifier unifier = new Unify();
    
    public void assertKnowledge( Knowledge knowledge )
    {
        clauses.add(knowledge);
    }
    
    public void pose( Goal query )
    {
        System.out.println("asking " + query);
        try
        {
            Deque<Goal> goals = new ArrayDeque<Goal>();
            goals.push( query );
            satisfy( goals );
        }
        catch ( ShortCircuitException e )
        {
            // just fine....
        }
    }

    private List<Knowledge> getClauses( Goal goal )
    {
        return clauses; // todo optimize for performance reasons later...
    }

    public void satisfy( Deque<Goal> goals )
    {
        satisfy( goals, new Bindings() );
    }

    public void satisfy( Deque<Goal> goals, Binding originalBinding )
    {
        for ( final Goal goal : goals )
        {
            for ( Knowledge clause : getClauses(goal) )
            {
                if ( clause instanceof Fact )
                {
                    Fact fact = (Fact) clause;
                    System.out.println("checking fact " + fact);
                    UnifyResult unifyResult = unifier.unify( goal, (Term) fact, new WrappedBinding(originalBinding) );
//                    UnifyResult unifyResult = goal.unify( (Term) fact, originalBinding );
                    if ( unifyResult.succeeded() )
                    {
                        print( unifyResult );
                    }
                }
                else if ( clause instanceof Rule )
                {
                    Rule rule = (Rule) clause;
                    System.out.println("checking rule " + rule);
                    RewrittenItems rewrittenItems = new RewrittenItems( goal, rule );
                    UnifyResult unifyResult = unifier.unify( rewrittenItems.getGoal(), (Term) rewrittenItems.getRuleHead(), new WrappedBinding(originalBinding) );
//                    UnifyResult unifyResult = rewrittenItems.getGoal().unify( rewrittenItems.getRuleHead().getTerm(), originalBinding );
                    if ( unifyResult.succeeded() )
                    {
                        for ( Deque<Goal> newGoals : rewrittenItems.getSetsOfNewGoals() )
                        {
                            satisfy( newGoals, unifyResult.bindings() );
                        }
                    }
                }
                else
                {
                    throw new RuntimeException( "Unknown clause type for " + clause );
                }
            }
        }
    }
    
    public void print( UnifyResult unifyResult )
    {
        try
        {
            System.out.println("yes");
            unifyResult.bindings().dumpVariables();
            // todo add this back in...
//            BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
//            String line = br.readLine();
//            if ( !";".equals( line.trim() ) )
//            {
//                throw new ShortCircuitException();
//            }
//        }
//        catch ( IOException e )
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "Unable to read input from the user.", e );
        }
    }

}
