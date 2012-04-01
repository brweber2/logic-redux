/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.brweber2.term.impl.Is;
import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

@ProcessRule(rule={"<Is> ::= <Variable> is <Term>"})

public class IsRuleHandler  extends Reduction
{
    private Reduction variable;
    private Reduction value;

    public IsRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 3) {
                variable = reduction.get( 0 ).asReduction();
                value = reduction.get( 2 ).asReduction();
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
        variable.execute();
        value.execute();
        setValue( new Variable( new Is( (com.brweber2.term.Variable) variable.getValue().asObject(), value.getValue().asObject() ) ) );
    }
}
