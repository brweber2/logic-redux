/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;

@ProcessRule( rule={"<RuleBody> ::= <RuleAnd>"
        ,"<RuleBody> ::= <RuleOr>"
        ,"<RuleBody> ::= <Term>"} )

public class RuleBodyRuleHandler extends Reduction
{
    private Reduction body;

    public RuleBodyRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 1) {
                body = reduction.get( 0 ).asReduction();
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
        body.execute();
        setValue(body.getValue());
    }
}
