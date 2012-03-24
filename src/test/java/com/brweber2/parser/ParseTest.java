/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser;

import com.brweber2.kb.Knowledge;
import com.brweber2.term.impl.AComplexTerm;
import com.brweber2.term.impl.AVariable;
import com.brweber2.term.impl.AnAtom;
import com.brweber2.term.impl.AnNumeric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.StringReader;
import java.util.List;

public class ParseTest
{
    private void parseIt( String source, Object ... expecteds )
    {
        List<Knowledge> results = CompileGrammar.parse( new StringReader( source ) );
        Assert.assertEquals( results.size(), expecteds.length );
        for ( int i = 0; i < results.size(); i++ )
        {
            Knowledge result = results.get( i );
            Object expected = expecteds[i];
            Assert.assertTrue( expected.getClass().isAssignableFrom( result.getClass() ) );
            Assert.assertEquals( result, expected );
        }
    }
    
    @Test
    public void parseAtom()
    {
        parseIt( "foo.", new AnAtom( "foo" ) );
        parseIt( "foo. bar.", new AnAtom( "foo" ), new AnAtom( "bar" ) );
    }
    
    @Test
    public void parseNumeric()
    {
        parseIt( "123.", new AnNumeric( "123" ) );
        parseIt( "123.45.", new AnNumeric( "123.45" ) );
    }
    
    @Test
    public void parseVariable()
    {
        parseIt( "foo(@X,bar).", new AComplexTerm( "foo", new AVariable("X"),new AnAtom( "bar" ) ) );
    }

    @Test
    public void parseComplexTerm()
    {
        parseIt( "foo(bar,baz).", new AComplexTerm( "foo", new AnAtom("bar"),new AnAtom( "baz" ) ) );
        parseIt( "foo(bar,quux(baz)).", new AComplexTerm( "foo", new AnAtom("bar"),new AComplexTerm( "quux", new AnAtom( "baz" ) ) ) );
    }

    @Test
    public void parseRule()
    {
//        parseIt( "related(@X,@Y,Jim) :- family(@Z,@Y,@X,Jim,Dave).",
//                new AComplexTerm( "foo", new AnAtom("bar"),new AnAtom( "baz" ) ) );
    }
}
