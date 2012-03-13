package com.brweber2.unify;

import com.brweber2.term.Term;
import com.brweber2.term.impl.AComplexTerm;
import com.brweber2.term.impl.AVariable;
import com.brweber2.term.impl.AnAtom;
import com.brweber2.term.impl.AnNumeric;
import com.brweber2.unify.impl.ABinding;
import com.brweber2.unify.impl.RuleBinding;
import com.brweber2.unify.impl.Unify;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class UnifyTest {
    @Test
    public void testAtomUnify()
    {
        shouldAtomUnify( new AnAtom("foo"), new AnAtom("foo") );
        shouldNotAtomUnify(new AnAtom("foo"), new AnAtom("bar"));
        shouldNotAtomUnify(new AnAtom("foo"), new AnNumeric("123.4"));
        shouldNotAtomUnify(new AnAtom("foo"), new AComplexTerm("quux"));
        shouldNotAtomUnify(new AnAtom("foo"), new AComplexTerm("quux",new AnAtom("foo")));
    }
    
    @Test
    public void testComplexTermUnify()
    {
        shouldComplexTermUnify( new AComplexTerm("foo"), new AComplexTerm("foo") );
        shouldComplexTermUnify( new AComplexTerm("foo", new AnAtom("bar")), new AComplexTerm("foo", new AnAtom("bar")) );
        shouldComplexTermUnify( new AComplexTerm("foo", new AComplexTerm("quux",new AnAtom("bing"))), new AComplexTerm("foo",new AComplexTerm("quux",new AnAtom("bing"))) );

        shouldComplexTermNotUnify(new AComplexTerm("foo"), new AnAtom("foo"));
        shouldComplexTermNotUnify(new AComplexTerm("foo"), new AComplexTerm("bar"));
        shouldComplexTermNotUnify( new AComplexTerm("foo", new AnAtom("bar")), new AComplexTerm("foo") );
        shouldComplexTermNotUnify( new AComplexTerm("foo", new AComplexTerm("quux",new AnAtom("bing"))), new AComplexTerm("foo",new AComplexTerm("quux",new AnAtom("meh"))) );
    }
    
    @Test
    public void testVariableAtomUnify()
    {
        shouldVariableAtomUnify(new AnAtom("foo"), new AVariable("bar"));
        shouldVariableAtomUnify(new AVariable("bar"), new AnAtom("foo"));
        ABinding binding = new ABinding();
        binding.instantiate(new AVariable("bar"),new AnAtom("foo"));
        shouldVariableAtomUnify(new AVariable("bar"), new AnAtom("foo"), binding);
    }

    @Test
    public void testVariableAtomNotUnify()
    {
        shouldVariableAtomUnify( new AnAtom("foo"), new AVariable("bar") );
        shouldVariableAtomUnify( new AVariable("bar"), new AnAtom("foo") );
        Binding binding = new ABinding();
        binding.instantiate(new AnAtom("quux"),new AVariable("bar"));
        RuleBinding ruleBinding = new RuleBinding( binding );
        shouldVariableAtomNotUnify(new AVariable("bar"), new AnAtom("foo"), ruleBinding);
    }

    private void shouldComplexTermUnify( Term a, Term b )
    {
        UnifyResult result = new Unify().unify( a, b );
        Assert.assertTrue(result.succeeded());
        Assert.assertTrue(result.bindings().getVariables().isEmpty() );
    }

    private void shouldComplexTermNotUnify( Term a, Term b )
    {
        UnifyResult result = new Unify().unify( a, b );
        Assert.assertFalse(result.succeeded());
        Assert.assertTrue(result.bindings().getVariables().isEmpty() );
    }
    
    private void shouldVariableAtomUnify( Term a, Term b )
    {
        shouldVariableAtomUnify(a, b, new ABinding());
    }

    private void shouldVariableAtomUnify( Term a, Term b, RuleBinding binding )
    {
        UnifyResult resultOne = new Unify().unify( a, b, binding);
        Assert.assertTrue(resultOne.succeeded());
        Assert.assertEquals(resultOne.bindings().getVariables().size(), 0);
    }

    private void shouldVariableAtomUnify( Term a, Term b, ABinding binding )
    {
        UnifyResult resultOne = new Unify().unify( a, b, binding );
        Assert.assertTrue(resultOne.succeeded());
        Assert.assertEquals(resultOne.bindings().getVariables().size(), 1);
        Assert.assertTrue(resultOne.bindings().getVariables().contains(new AVariable("bar")));
        Assert.assertEquals(resultOne.bindings().resolve(new AVariable("bar")), new AnAtom("foo") );
    }

    private void shouldVariableAtomNotUnify( Term a, Term b, Binding binding )
    {
        UnifyResult resultOne = new Unify().unify( a, b, binding );
        Assert.assertFalse(resultOne.succeeded());
        Assert.assertEquals(resultOne.bindings().getVariables().size(), 0);
        Assert.assertFalse(resultOne.bindings().getVariables().contains(new AVariable("bar")));
        Assert.assertEquals(resultOne.bindings().resolve(new AVariable("bar")), null );
    }
    
    private void shouldAtomUnify( Term a, Term b )
    {
        UnifyResult resultOne = new Unify().unify( a, b );
        Assert.assertTrue(resultOne.succeeded());
        Assert.assertTrue(resultOne.bindings().getVariables().isEmpty() );

        UnifyResult resultTwo = new Unify().unify( b, a );
        Assert.assertTrue(resultTwo.succeeded());
        Assert.assertTrue(resultTwo.bindings().getVariables().isEmpty() );

        Unifier unify = new Unify();
        UnifyResult resultThree = unify.unify(a,b);
        Assert.assertTrue(resultThree.succeeded());
        Assert.assertTrue(resultThree.bindings().getVariables().isEmpty() );

        UnifyResult resultFour = unify.unify(b,a);
        Assert.assertTrue(resultFour.succeeded());
        Assert.assertTrue(resultFour.bindings().getVariables().isEmpty() );
    }

    private void shouldNotAtomUnify( Term a, Term b )
    {
        UnifyResult resultOne = new Unify().unify( a, b );
        Assert.assertFalse(resultOne.succeeded());
        Assert.assertTrue(resultOne.bindings().getVariables().isEmpty() );

        UnifyResult resultTwo = new Unify().unify( b, a );
        Assert.assertFalse(resultTwo.succeeded());
        Assert.assertTrue(resultTwo.bindings().getVariables().isEmpty() );

        Unifier unify = new Unify();
        UnifyResult resultThree = unify.unify(a,b);
        Assert.assertFalse(resultThree.succeeded());
        Assert.assertTrue(resultThree.bindings().getVariables().isEmpty() );

        UnifyResult resultFour = unify.unify(b,a);
        Assert.assertFalse(resultFour.succeeded());
        Assert.assertTrue(resultFour.bindings().getVariables().isEmpty() );
    }
}
