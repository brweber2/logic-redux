package com.brweber2.unify;

import com.brweber2.term.Term;
import com.brweber2.term.impl.AComplexTerm;
import com.brweber2.term.impl.AnAtom;
import com.brweber2.term.impl.AnNumeric;
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
        shouldUnify( new AnAtom("foo"), new AnAtom("foo") );
        shouldNotUnify(new AnAtom("foo"), new AnAtom("bar"));
        shouldNotUnify(new AnAtom("foo"), new AnNumeric("123.4"));
        shouldNotUnify(new AnAtom("foo"), new AComplexTerm("quux"));
        shouldNotUnify(new AnAtom("foo"), new AComplexTerm("quux",new AnAtom("foo")));
    }
    
    private void shouldUnify( Term a, Term b )
    {
        UnifyResult resultOne = a.unify(b);
        Assert.assertTrue(resultOne.succeeded());
        Assert.assertTrue(resultOne.bindings().getVariables().isEmpty() );

        UnifyResult resultTwo = b.unify(a);
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

    private void shouldNotUnify( Term a, Term b )
    {
        UnifyResult resultOne = a.unify(b);
        Assert.assertFalse(resultOne.succeeded());
        Assert.assertTrue(resultOne.bindings().getVariables().isEmpty() );

        UnifyResult resultTwo = b.unify(a);
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
