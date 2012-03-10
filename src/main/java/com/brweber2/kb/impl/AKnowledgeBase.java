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
import com.brweber2.unify.impl.Bindings;
import com.brweber2.unify.impl.Unify;
import com.brweber2.unify.impl.WrappedBinding;

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
        log.fine("asking " + query);
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
        satisfy( goals, new Bindings(), false );
    }

    public void satisfy( Deque<Goal> goals, Binding parentBinding, boolean ruleAsked )
    {
        Binding bindingToUse1 = new WrappedBinding( parentBinding );
        log.finer("there are " + goals.size() + " goals");
        Goal goal = goals.pollFirst();
        while ( goal != null )
        {
            log.finest("goal before rewrite is " + goal);
            goal = (Goal) rewriteGoal( goal, bindingToUse1 );
            Binding bindingToUse = bindingToUse1.getCopy();
            log.finest("rewritten goal is " + goal);
            boolean ruleMatched = false; // only use the first rule that matches
            for ( Knowledge clause : getClauses(goal) )
            {
                if ( clause instanceof Fact )
                {
                    Fact fact = (Fact) clause;
                    log.finest("goal " + goal + " checking fact " + fact + " with " + bindingToUse);
                    UnifyResult unifyResult = unifier.unify( goal, (Term) fact, new WrappedBinding(bindingToUse) );
                    if ( unifyResult.succeeded() )
                    {
                        log.finer("bbw when unify succeeded goals size is " + goals.size());
                        if ( goals.isEmpty() )
                        {   // since this is our last one, we have  match
                            if ( ruleAsked )
                            {
                                ruleMatched = true;
                            }
                            print( unifyResult );
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
                    log.finest("goal " + goal + " checking rule " + rule + " with " + bindingToUse);
                    RewrittenItems rewrittenItems = new RewrittenItems( goal, rule, bindingToUse );
                    UnifyResult unifyResult = rewrittenItems.getUnifyResult();
                    if ( unifyResult.succeeded() )
                    {
                        log.fine("rule match so far, checking rest of goals");
                        for ( Deque<Goal> newGoals : rewrittenItems.getSetsOfNewGoals() )
                        {
                            log.finer("trying to satisfy " + newGoals + " with " + unifyResult.bindings() );
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
    
    public Term rewriteGoal( Term goal, Binding binding )
    {
        if ( goal instanceof Constant )
        {
            return goal;
        }
        else if ( goal instanceof ComplexTerm )
        {
            ComplexTerm original = (ComplexTerm) goal;
            List<Term> newTerms = new ArrayList<Term>(  );
            for ( Term term : original.getTerms() )
            {
                newTerms.add( rewriteGoal( term, binding ) );
            }
            return new AComplexTerm( original.getFunctor().getFunctorString(), newTerms.toArray( new Term[newTerms.size()] ) );
        }
        else if ( goal instanceof Variable )
        {
            Variable var = (Variable) goal;
            if ( binding.isBound( var ) )
            {
                binding.markToUnbind( var );
                return binding.resolve( var );
            }
            return var;
        }
        else
        {
            throw new RuntimeException( "What have you given me???" );
        }
    }
    
    public void print( UnifyResult unifyResult )
    {
        try
        {
            log.info("yes");
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
