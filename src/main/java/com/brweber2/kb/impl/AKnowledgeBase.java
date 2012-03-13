package com.brweber2.kb.impl;

import com.brweber2.kb.Fact;
import com.brweber2.kb.Functor;
import com.brweber2.kb.Knowledge;
import com.brweber2.kb.KnowledgeBase;
import com.brweber2.kb.Rule;
import com.brweber2.proofsearch.ProofSearch;
import com.brweber2.rule.Goal;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Constant;
import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.term.impl.AComplexTerm;
import com.brweber2.unify.Binding;
import com.brweber2.unify.Unifier;
import com.brweber2.unify.UnifyResult;
import com.brweber2.unify.impl.ABinding;
import com.brweber2.unify.impl.RuleBinding;
import com.brweber2.unify.impl.Unify;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class AKnowledgeBase implements KnowledgeBase, ProofSearch {
    
    Map<Functor,Collection<Knowledge>> clauses = new HashMap<Functor,Collection<Knowledge>>();
    Unifier unifier = new Unify();
    Logger log = Logger.getLogger( AKnowledgeBase.class.getName() );
    
    public void assertKnowledge( Knowledge knowledge )
    {
        Functor functor = knowledge.getFunctor();
        if ( !clauses.containsKey( functor ) )
        {
            clauses.put( functor, new ArrayList<Knowledge>() );
        }
        clauses.get( functor ).add( knowledge );
    }
    
    public void pose( Goal query )
    {
        log.fine( "asking " + query );
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

    private Collection<Knowledge> getClauses( Goal goal )
    {
        return clauses.get( goal.getFunctor() );
    }

    public void satisfy( Deque<Goal> goals )
    {
        satisfy( goals, new ABinding(), false );
    }

    public void satisfy( Deque<Goal> goals, Binding parentBinding, boolean ruleAsked )
    {
        log.finer( "there are " + goals.size() + " goals" );
        Goal goal = goals.pollFirst();
        while ( goal != null )
        {
            boolean ruleMatched = false; // only use the first rule that matches
            for ( Knowledge clause : getClauses(goal) )
            {
                if ( clause instanceof Fact )
                {
                    Fact fact = (Fact) clause;
                    log.finest( "goal " + goal + " checking fact " + fact + " with " + parentBinding );
                    UnifyResult unifyResult = unifier.unify( goal, (Term) fact, new RuleBinding(parentBinding) );
                    if ( unifyResult.succeeded() )
                    {
                        log.info( "bbw when unify succeeded goals size is " + goals.size() + " with " + unifyResult.bindings() );
                        if ( goals.isEmpty() )
                        {   // since this is our last one, we have  match
                            if ( ruleAsked )
                            {
                                ruleMatched = true;
                            }
                            print( unifyResult.bindings() );
                        }
                        else
                        {
                            satisfy( goals, unifyResult.bindings(), ruleAsked );
                            return;
                        }
                    }
                }
                else if ( ruleMatched & clause instanceof Rule )
                {
                    // safely ignore me
                }
                else if ( clause instanceof Rule )
                {
                    Rule rule = (Rule) clause;
                    log.finest( "goal " + goal + " checking rule " + rule + " with " + parentBinding );
                    RewrittenItems rewrittenItems = new RewrittenItems( goal, rule );
                    UnifyResult unifyResult = rewrittenItems.getUnifyResult(new RuleBinding(parentBinding));
                    if ( unifyResult.succeeded() )
                    {
                        log.fine( "rule match so far, checking rest of goals" );
                        for ( Deque<Goal> newGoals : rewrittenItems.getSetsOfNewGoals() )
                        {
                            log.finer( "trying to satisfy " + newGoals + " with " + unifyResult.bindings() );
                            satisfy( newGoals, unifyResult.bindings(), true );
                        }
                    }
                }
                else
                {
                    throw new RuntimeException( "Unknown clause type for " + clause.getClass().getName() );
                }
            }
            goal = goals.pollFirst();
        }
    }
    
    public void print( Binding binding )
    {
        try
        {
            log.info("yes");
            binding.dumpVariables();
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
