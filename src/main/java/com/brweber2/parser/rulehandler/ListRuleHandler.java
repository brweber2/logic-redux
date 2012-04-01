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

import java.util.List;

@ProcessRule(rule={"<List> ::= '[' ']'"
    ,"<List> ::= '[' <TermList> ']'"
    ,"<List> ::= '[' <TermList> '|' <TermList> ']'"})

public class ListRuleHandler extends Reduction
{
    public static final String LIST_FUNCTOR = ".";

    private Reduction decompose;
    private Reduction complexTerm;

    public ListRuleHandler(GOLDParser parser)
    {
        Reduction reduction = parser.getCurrentReduction();
        if (reduction != null) {
            if (reduction.size() == 2) {
                complexTerm = null;
            }
            else if ( reduction.size() == 3 ) {
                complexTerm = reduction.get( 1 ).asReduction();
            } else if ( reduction.size() == 5 ) {
                decompose = reduction.get( 1 ).asReduction();
                complexTerm = reduction.get( 3 ).asReduction();
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
        if ( complexTerm == null )
        {
            setValue( new Variable( new AComplexTerm( LIST_FUNCTOR ) ) );
        }
        else if ( decompose != null )
        {
            decompose.execute();
            complexTerm.execute();
            setValue( new Variable( new Decompose( (List<Term>) decompose.getValue().asObject(), (List<Term>) complexTerm.getValue().asObject() ) ) );
        }
        else
        {
            complexTerm.execute();
            List<Term> terms = (List<Term>) complexTerm.getValue().asObject();
            AComplexTerm currentTerm = new AComplexTerm( LIST_FUNCTOR );
            AComplexTerm rootTerm = currentTerm;
            for ( Term term : terms )
            {
                currentTerm.getTerms().add( term );
                AComplexTerm lastTerm = currentTerm;
                // todo should the last list contain element and empty list or just element?
                currentTerm = new AComplexTerm( LIST_FUNCTOR );
                lastTerm.getTerms().add( currentTerm );
            }
            setValue( new Variable( rootTerm ) );
        }
    }
}
