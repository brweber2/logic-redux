/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.brweber2.rule.RuleBody;
import com.brweber2.rule.impl.ADisconjunction;
import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

@ProcessRule( rule="<RuleOr> ::= <Term> ';' <RuleBody>" )

public class RuleOrRuleHandler extends Reduction
{
    private Reduction left;
    private Reduction right;

    public RuleOrRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 3) {
                left = reduction.get(0).asReduction();
                right = reduction.get(2).asReduction();
            } else {
                parser.raiseParserException("wrong number of args");
            }
        } else {
            parser.raiseParserException("no reduction");
        }
    }

    @Override
    public void execute() throws ParserException
    {
        left.execute();
        right.execute();
        setValue( new Variable( new ADisconjunction( (RuleBody)left.getValue().asObject(), (RuleBody) right.getValue().asObject() ) ) );
    }
}
