package com.brweber2.kb;

import com.brweber2.proofsearch.ProofSearch;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface KnowledgeBase extends ProofSearch
{
    void assertKnowledge( Knowledge knowledge );
}
