/**
 * @author brweber2
 *         Copyright: 2012
 */
package com.brweber2.unify;

import com.brweber2.kb.KnowledgeBase;
import com.brweber2.kb.impl.AKnowledgeBase;
import com.brweber2.rule.impl.AConjunction;
import com.brweber2.rule.impl.ARule;
import com.brweber2.term.impl.AComplexTerm;
import com.brweber2.term.impl.AVariable;
import com.brweber2.term.impl.AnAtom;
import org.testng.annotations.Test;

public class ProofSearchTest
{
    @Test
    public void testProofSearch()
    {
        KnowledgeBase kb = new AKnowledgeBase();

        kb.assertKnowledge( new AComplexTerm( "parent", new AnAtom( "Tim" ), new AnAtom( "Gary" ) ) );
        kb.assertKnowledge( new AComplexTerm( "parent", new AnAtom( "Tim" ), new AnAtom( "Melissa" ) ) );
        kb.assertKnowledge( new AComplexTerm( "parent", new AnAtom( "Tim" ), new AnAtom( "Bob" ) ) );
        kb.assertKnowledge( new AComplexTerm( "parent", new AnAtom( "Ralph" ), new AnAtom( "Tim" ) ) );
        kb.assertKnowledge( new AComplexTerm( "parent", new AnAtom( "Mary" ), new AnAtom( "Nancy" ) ) );
        kb.assertKnowledge( new AComplexTerm( "parent", new AnAtom( "Mark" ), new AnAtom( "Gary" ) ) );

        kb.assertKnowledge( new ARule(
                new AComplexTerm( "related", new AVariable( "X" ), new AVariable( "Y" ) ),
                    new AComplexTerm( "parent", new AVariable( "X" ), new AVariable( "Y" ) ) ) );
        kb.assertKnowledge( new ARule(
                new AComplexTerm( "related", new AVariable( "X" ), new AVariable( "Y" ) ),  new AConjunction(
                    new AComplexTerm( "parent", new AVariable( "X" ), new AVariable( "Z" ) ),
                    new AComplexTerm( "related", new AVariable( "Z" ), new AVariable( "Y" ) ) ) ) );

        kb.pose( new AComplexTerm("parent", new AnAtom( "Tim" ), new AVariable( "Y" ) ) );
    }
}
