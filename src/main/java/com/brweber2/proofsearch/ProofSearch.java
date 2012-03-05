package com.brweber2.proofsearch;

import com.brweber2.kb.Query;
import com.brweber2.kb.QueryResult;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface ProofSearch {
    QueryResult pose( Query query );
}
