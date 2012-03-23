package com.brweber2.kb.impl;

import com.brweber2.kb.Fact;
import com.brweber2.kb.Functor;
import com.brweber2.kb.Knowledge;
import com.brweber2.kb.KnowledgeBase;
import com.brweber2.kb.Rule;
import com.brweber2.proofsearch.ProofSearch;
import com.brweber2.rule.Goal;
import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import com.brweber2.term.Numeric;
import com.brweber2.term.Term;
import com.brweber2.term.Variable;
import com.brweber2.unify.Binding;
import com.brweber2.unify.Unifier;
import com.brweber2.unify.UnifyResult;
import com.brweber2.unify.impl.ABinding;
import com.brweber2.unify.impl.Unify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
        satisfy( new Goals( this, query ) );
    }

    public Collection<Knowledge> getClauses( Goal goal )
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

    public void satisfy( Goals goals )
    {
        satisfyGoals( goals, new ABinding() );
    }

    public void satisfyGoals( Goals goals, Binding parentBinding )
    {
        for ( GoalList goalList : goals.getListOfGoalLists() )
        {
            satisfyGoalList( goalList, parentBinding );
        }
    }

    private void satisfyGoalList( GoalList goalList, Binding parentBinding )
    {
        Binding binding = new ABinding( parentBinding );
        while ( goalList.haveMore() )
        {
            Goal goal = goalList.getNext();
            System.err.println("Goal: " + goal + " with binding: " + binding);
            Clauses clauses = goalList.getNextClause(goal);

            while ( clauses.hasMore() )
            {
                Knowledge clause = clauses.getNext();
                System.err.println("Clause: " + clause + " with binding: " + binding);
                UnifyResultAndGoalList unifyResultAndGoalList = satisfy( goal, clause, binding );
                UnifyResult unifyResult = unifyResultAndGoalList.getUnifyResult();
                Goals nextGoals = unifyResultAndGoalList.getGoals();
                if ( unifyResult.succeeded() )
                {
                    if ( nextGoals.isEmpty() )
                    {
                        // we have a match!!!!
                        print( unifyResult.bindings() );
                    }
                    else
                    {
                        satisfyGoals( nextGoals, unifyResult.bindings() );
                    }
                }
                else
                {
                    System.err.println( "did not unify " + goal + " and clause " + clause + " with " + binding );
                }
            }
        }
    }
    
    static class UnifyResultAndGoalList
    {
        private UnifyResult unifyResult;
        private Goals goals;

        UnifyResultAndGoalList( UnifyResult unifyResult, Goals goals )
        {
            this.unifyResult = unifyResult;
            this.goals = goals;
        }

        public UnifyResult getUnifyResult()
        {
            return unifyResult;
        }

        public Goals getGoals()
        {
            return goals;
        }
    }
    
    private UnifyResultAndGoalList satisfy( Goal goal, Knowledge clause, Binding binding )
    {
        System.err.println("time to satisfy " + goal + " with " + clause + " given " + binding);
        if ( clause instanceof Fact )
        {
            Fact fact = (Fact) clause;
            UnifyResult result = unifier.unify( fact.getTerm(), (Term) goal, binding );
            System.err.println( "FACT RESULT: " + result + " for " + fact + " asked " + goal + " given " + binding );
            return new UnifyResultAndGoalList( result, new Goals( this ) );
        }
        else // if ( clause instanceof Rule )
        {
            Rule rule = (Rule) clause;
            Fact head = rule.getHead();
            System.err.println("time to satisfy rule " + goal + " with " + head + " given " + binding);
            UnifyResult headResult = ask( goal, head, binding );
            System.err.println( "HEAD RESULT: " + headResult + " for " + head + " asked " + goal + " given " + binding );
            if ( headResult.succeeded() )
            {
                System.err.println( "head succeeded, time to check the body" );
                return new UnifyResultAndGoalList( headResult, new Goals( this, rule.getBody() ) );
            }
            System.err.println( "head did not match :(" );
            return new UnifyResultAndGoalList( headResult, new Goals(this) );
        }
    }
    
    private UnifyResult ask( Goal goal, Fact head, Binding binding )
    {
        System.err.println( "goal " + goal + " head " + head + " binding: " + binding );
        Binding ruleBinding = new ABinding( binding );
        System.err.println( "bbw trying to unify: goal " + goal + " head " + head + " ruleBinding: " + ruleBinding );
        UnifyResult result = unifier.unify( (Term) goal, (Term) head, ruleBinding );
        System.err.println( "goal " + goal + " head " + head + " after ruleBinding: " + ruleBinding );
        if ( result.succeeded() )
        {
            Binding finalBinding = new ABinding( ruleBinding );
            System.err.println( "goal " + goal + " head " + head + " final binding: " + finalBinding );
            return new com.brweber2.unify.impl.UnifyResult( result, finalBinding );
        }
        return result;
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
