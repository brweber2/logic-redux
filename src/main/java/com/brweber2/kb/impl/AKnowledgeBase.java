package com.brweber2.kb.impl;

import com.brweber2.kb.Fact;
import com.brweber2.kb.Functor;
import com.brweber2.kb.Knowledge;
import com.brweber2.kb.KnowledgeBase;
import com.brweber2.kb.Rule;
import com.brweber2.proofsearch.ProofSearch;
import com.brweber2.rule.Conjunction;
import com.brweber2.rule.Disjunction;
import com.brweber2.rule.Goal;
import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Constant;
import com.brweber2.term.Numeric;
import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.term.impl.AComplexTerm;
import com.brweber2.unify.Binding;
import com.brweber2.unify.Unifier;
import com.brweber2.unify.UnifyResult;
import com.brweber2.unify.impl.ABinding;
import com.brweber2.unify.impl.RuleBinding;
import com.brweber2.unify.impl.Unify;

import java.lang.management.GarbageCollectorMXBean;
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
        if ( goal instanceof Atom)
        {
            return clauses.get(((Atom)goal).getFunctor());
        }
        else if ( goal instanceof Numeric )
        {
            return clauses.get(((Numeric)goal).getFunctor());
        }
        else if ( goal instanceof Variable )
        {
            ArrayList<Knowledge> list = new ArrayList<Knowledge>();
            for (Functor functor : clauses.keySet()) {
                list.addAll(clauses.get(functor));
            }
            return list;
        }
        else if ( goal instanceof ComplexTerm )
        {
            return clauses.get(((ComplexTerm)goal).getFunctor());
        }
        else
        {
            throw new RuntimeException("wtf?");
        }
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
            boolean matchFound = false;
            if ( goal instanceof Conjunction )
            {
                Conjunction conjunction = (Conjunction) goal;
                Deque<Goal> newGoals = new ArrayDeque<Goal>();
                newGoals.add(conjunction.getLeft());
                newGoals.add(conjunction.getRight());
                satisfy(newGoals, parentBinding, true);
            }
            else if ( goal instanceof Disjunction )
            {
                Disjunction disjunction = (Disjunction) goal;
                Deque<Goal> leftGoals = new ArrayDeque<Goal>();
                leftGoals.add(disjunction.getLeft());
                satisfy(leftGoals,parentBinding,true);
                Deque<Goal> rightGoals = new ArrayDeque<Goal>();
                rightGoals.add(disjunction.getRight());
                satisfy(rightGoals,parentBinding,true);
            }
            else
            {
                boolean ruleMatched = false; // only use the first rule that matches
                for ( Knowledge clause : getClauses(goal) )
                {
                    System.out.println("goal is " + goal + " and remaining goals are " + goals + " and checking clause " + clause);
                    if ( clause instanceof Fact )
                    {
                        Fact fact = (Fact) clause;
                        log.finest( "goal " + goal + " checking fact " + fact + " with " + parentBinding );
                        UnifyResult unifyResult = unifier.unify( (Term)goal, fact.getTerm(), new RuleBinding(parentBinding) );
                        System.out.println("    before binding: " + parentBinding);
                        if ( unifyResult.succeeded() )
                        {
                            System.out.println("    binding: " + unifyResult.bindings());
                            matchFound = true;
//                            System.out.println("fact " + fact + " matched goal " + goal);
                            log.info( "bbw when unify succeeded goals size is " + goals.size() + " with " + unifyResult.bindings() );
                            if ( goals.isEmpty() )
                            {   // since this is our last one, we have  match
//                                System.out.println("bottom of tree!");
                                if ( ruleAsked )
                                {
                                    ruleMatched = true;
                                }
                                print( unifyResult.bindings() );
                            }
                            else
                            {
                                System.out.println("let's go look at " + goals);
                                satisfy( goals, new ABinding((RuleBinding)unifyResult.bindings()), ruleAsked );
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
                        UnifyResult unifyResult = new Unify().unify((Term)goal,rule.getHead().getTerm(),new RuleBinding(parentBinding));
                        if ( unifyResult.succeeded() )
                        {
//                            System.out.println("rule " + rule + " matched goal " + goal);
                            log.fine("rule match so far, checking rest of goals");
                            Deque<Goal> newGoals = new ArrayDeque<Goal>();
                            newGoals.add(rule.getBody());
                            satisfy(newGoals, new ABinding((RuleBinding)unifyResult.bindings()), true);
                        }
                    }
                    else
                    {
                        throw new RuntimeException( "Unknown clause type for " + clause.getClass().getName() );
                    }
                }
            }
            System.out.println(goal + "? " + matchFound);
            if ( ruleAsked && !matchFound )
            {
//                System.out.println("no match found for " + goal);
                return;
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
