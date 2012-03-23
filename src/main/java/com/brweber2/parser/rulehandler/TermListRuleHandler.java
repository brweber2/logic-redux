/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser.rulehandler;

import com.brweber2.term.Term;
import com.creativewidgetworks.goldparser.engine.ParserException;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;
import com.creativewidgetworks.goldparser.parser.ProcessRule;
import com.creativewidgetworks.goldparser.parser.Variable;

import java.util.ArrayList;
import java.util.List;

@ProcessRule( rule={"<TermList> ::= <Term> ',' <TermList>"
        ,"<TermList> ::= <Term>"} )

public class TermListRuleHandler extends Reduction
{
    private Reduction term;
    private Reduction terms;
    
    public TermListRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 1) {
                term = reduction.get( 0 ).asReduction();
            } else if ( reduction.size() == 3 ) {
                term = reduction.get( 0 ).asReduction();
                terms = reduction.get( 2 ).asReduction();
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
        term.execute();
        List<Term> result = new ArrayList<Term>();
        result.add( (Term) term.getValue().asObject() );
        if ( terms != null )
        {
            terms.execute();
            List<Term> list = (List<Term>) terms.getValue().asObject();
            if ( !result.addAll( list ) )
            {
                throw new RuntimeException( "Unable to add stuff to a list... :(" );
            }
        }
        setValue( new Variable( result ) );
    }
}
