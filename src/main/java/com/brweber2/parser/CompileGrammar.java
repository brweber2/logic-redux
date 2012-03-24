/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.parser;

import com.brweber2.kb.Knowledge;
import com.creativewidgetworks.goldparser.engine.Reduction;
import com.creativewidgetworks.goldparser.parser.GOLDParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;

public class CompileGrammar
{
    private static final Logger log = Logger.getLogger(CompileGrammar.class.getName());
    
    public static void main( String[] args ) throws IOException
    {
        File grammarFile = new File("/Users/bweber/code/logic-redux/src/main/resources/naive_java_unification.egt");
        File sourceFile = new File( "/Users/bweber/code/logic-redux/hello.pl" );

        log.finest("" + grammarFile.exists());
        log.finest("" + sourceFile.exists());

        Reader sourceReader = new FileReader( sourceFile );
        GOLDParser parser = parser();
//        GOLDParser parser = new GOLDParser();
//        if ( !parser.setup( grammarFile ) )
//        {
//            throw new RuntimeException( "Unable to parse the grammar file " + grammarFile.getAbsolutePath() );
//        }
//        parser.loadRuleHandlers( "com.brweber2.parser.rulehandler" );
//        parser.setGenerateTree( true );
//        log.warning("errors:" + parser.validateHandlersExist());
        if ( !parser.parseSourceStatements( sourceReader ) )
        {
            throw new RuntimeException( "Unable to parse the source file " + sourceFile.getAbsolutePath() );
        }
        parser.getCurrentReduction().execute();
        Object result = parser.getCurrentReduction().getValue().asObject();
        System.out.println("[class] " + result.getClass().getName());
        System.out.println(result);
//        for ( int i =0; i < srh.size(); i++)
//        {
//            System.out.println(srh.getValue());
//            System.out.println(i + ": " + srh.get( i ));
//            System.out.println(i + ": " + srh.get( i ).asReduction());
//        }
//        System.out.println( srh.getValue() );
//        dumpReduction(parser.getCurrentReduction());
//        parser.getCurrentReduction().execute();
//        System.out.println(parser.getCurrentReduction());
//        System.out.println(parser.getCurrentReduction().getValue());
//        System.out.println(parser.getCurrentReduction().getValue().asObject());


//        System.out.println(parser.getParseTree());
    }

    public static void dumpReduction( Reduction r )
    {
        r.execute();
        System.out.println(r + ":[value] " + r.getValue());
        if ( r.getValue() != null )
        {
            System.out.println(r + ":[object] " + r.getValue().asObject());
        }
        System.out.println(r + ":[parent] " + r.getParent());
        for ( int i = 0; i < r.size(); i++ )
        {
            System.out.println(i + ":[next] " + r.get( i ) );
            if ( "<Atom>".equals( r.get( i ).toString() ) )
            {
                System.out.println("bbw");
                System.out.println(r.get( i ).asReduction());
                System.out.println(r.get( i ).asString());
                System.out.println(r.get( i ).getClass());
                System.out.println(r.get( i ).getData());
                System.out.println(r.get( i ).getPosition());
                System.out.println(r.get( i ).getState());
                System.out.println(r.get( i ).getGroup());
                System.out.println(r.get( i ).getName());
                System.out.println(r.get( i ).getTableIndex());
                System.out.println(r.get( i ).getType());
            }
            System.out.println(i + ":[data] " + r.get( i ).getData() );
            System.out.println(i + ":[class] " + r.get( i ).getData().getClass().getName() );
            Reduction next = r.get( i ).asReduction();
            System.out.println(i + ":[reduction] " + r.get( i ).asReduction() );
            if ( next !=  null )
            {
                System.out.println(i + ":[string] " + r.get( i ).asString());
                dumpReduction( next );
            }
        }
    }
    
    public static List<Knowledge> parse( InputStream is )
    {
        return parse( new InputStreamReader( is ) );
    }

    public static List<Knowledge> parse( Reader r )
    {
        GOLDParser parser = parser();
        if ( !parser.parseSourceStatements( r ) )
        {
            throw new RuntimeException( "Unable to parse the source." );
        }
        parser.getCurrentReduction().execute();
        return (List<Knowledge>) parser.getCurrentReduction().getValue().asObject();
    }

    public static GOLDParser parser()
    {
            InputStream inputStream = CompileGrammar.class.getClassLoader().getResourceAsStream("naive_java_unification.egt");
            if ( inputStream == null )
            {
                throw new RuntimeException( "No such egt file." );
            }
            return parser( inputStream );
    }
    
    public static GOLDParser parser( InputStream grammarFile )
    {
        GOLDParser parser = new GOLDParser(grammarFile,"com.brweber2.parser.rulehandler",false);
        List<String> errors = parser.validateHandlersExist();
        if ( !errors.isEmpty() )
        {
            throw new RuntimeException( "Missing handlers!" + errors );
        }
        return parser;
    }
}
