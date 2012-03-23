/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.brweber2.term.Term;
import com.brweber2.term.impl.AComplexTerm;
import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

import java.util.ArrayList;
import java.util.List;

@ProcessRule( rule={"<ComplexTerm> ::= Id '(' ')'"
        ,"<ComplexTerm> ::= Id '(' <TermList> ')'"} )

public class ComplexTermRuleHandler extends Reduction
{
    String id;
    Reduction termList;

    public ComplexTermRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 3) {
                id = reduction.get( 0 ).asString();
            } else if (reduction.size() > 3 ) {
                id = reduction.get( 0 ).asString();
                termList = reduction.get( 2 ).asReduction();
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
        List<Term> terms;
        if ( termList != null )
        {
            termList.execute();
            terms = (List<Term>) termList.getValue().asObject();
        }
        else
        {
            terms = new ArrayList<Term>();
        }
        setValue( new Variable( new AComplexTerm( id, (Term[]) terms.toArray( new Term[terms.size()] ) ) ) );
    }
}
