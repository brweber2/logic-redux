package com.brweber2.parser.rulehandler;

import com.brweber2.term.impl.AnNumeric;
import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

@ProcessRule(rule="<Number> ::= NumberLiteral")

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class NumberRuleHandler extends Reduction {
    public NumberRuleHandler(GOLDParser parser) {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 1) {
                setValue( new Variable( new AnNumeric(reduction.get( 0 ).asString() ) ) );
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
