/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.brweber2.term.impl.AnAtom;
import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

@ProcessRule(rule={"<Atom> ::= Id",
"<Atom> ::= AtomLiteral"})

public class AtomRuleHandler extends Reduction
{
    
    public AtomRuleHandler(GOLDParser parser) {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 1) {
                String atomString = reduction.get( 0 ).asString();
                if ( atomString.startsWith("'") && atomString.endsWith("'")){
                    atomString = atomString.substring(1,atomString.length()-1);
                }
                setValue( new Variable( new AnAtom( atomString ) ) );
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
    }
}
