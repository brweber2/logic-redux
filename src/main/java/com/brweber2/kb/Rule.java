package com.brweber2.kb;

import com.brweber2.rule.RuleBody;
import com.brweber2.rule.RuleHead;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public interface Rule extends Knowledge {
    RuleHead getHead();
    RuleBody getBody();
}
