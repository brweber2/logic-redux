package com.brweber2.kb;

import com.brweber2.rule.RuleBody;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface Rule extends Knowledge {
    Fact getHead();
    RuleBody getBody();
}
