/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;

@ProcessRule(rule={"<Statements> ::= <Statement> <Statements>",
"<Statements> ::= <Statement>"})

public class StatementsRuleHandler extends Reduction
{
    private Reduction statement;
    private Reduction statements = null;

    public StatementsRuleHandler()
    {
        statement = null;
    }

    public StatementsRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 1 || reduction.size() == 2) {
                statement = reduction.get( 0 ).asReduction();
                if ( reduction.size() == 2)
                {
                    statements = reduction.get( 1 ).asReduction();
                }
            }else {
                parser.raiseParserException("wrong number of args");
            }
        } else {
            parser.raiseParserException("no reduction");
        }
    }

    @Override
    public void execute() throws ParserException
    {
        statement.execute();
        setValue( statement.getValue() );
        if ( statements != null )
        {
            statements.execute();
            setValue( statements.getValue() );
        }
    }
}
