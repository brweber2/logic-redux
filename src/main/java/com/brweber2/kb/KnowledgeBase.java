package com.brweber2.kb;

import com.brweber2.proofsearch.ProofSearch;
import com.brweber2.rule.Goal;

import java.util.Collection;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface KnowledgeBase extends ProofSearch
{
    void assertKnowledge( Knowledge knowledge );
    Collection<Knowledge> getClauses( Goal goal );
}
