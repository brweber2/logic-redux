package com.brweber2.term;

import java.util.List;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface ComplexTerm extends Term {
    String getFunctor();
    int getArity();
    List<Term> getTerms();
}
