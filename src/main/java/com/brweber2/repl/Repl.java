package com.brweber2.repl;

import com.brweber2.kb.Knowledge;
import com.brweber2.kb.KnowledgeBase;
import com.brweber2.kb.impl.AKnowledgeBase;
import com.brweber2.parser.CompileGrammar;
import com.brweber2.rule.Goal;
import com.brweber2.term.Atom;
import com.brweber2.term.ComplexTerm;
import jline.ConsoleReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class Repl {
    private static Logger log = Logger.getLogger(Repl.class.getName());

    private static final KnowledgeBase kb = new AKnowledgeBase();
    private static final CompileGrammar compiler = new CompileGrammar();
    private static MODE mode = MODE.ASK;

    private enum MODE { ADD, ASK }

    public static void main( String[] args ) throws IOException
    {
        // todo load any knowledge base files passed in with -f flag
        ConsoleReader in = new ConsoleReader();
        boolean done = false;
        while ( !done )
        {
            try
            {
                String read = read(in);
                if ( done(read) )
                {
                    done = true;
                    continue;
                }
                eval(read);
            }
            catch ( Exception e )
            {
                System.err.println( "Error: " + e.getMessage() );
                e.printStackTrace();
                System.err.flush();
            }
        }
    }

    private static List parseString( String read )
    {
        return CompileGrammar.parse( new StringReader( read ) );
    }

    private static boolean load( Object question )
    {
        if ( question instanceof ComplexTerm)
        {
            ComplexTerm ct = (ComplexTerm) question;
            if ( ct.getFunctor().getFunctorString().equals( "load" ) && ct.getArity() == 1 && ct.getTerms().get( 0 ) instanceof Atom)
            {
                return true;
            }
        }
        return false;
    }

    private static List loadFile( Object question )
    {
        try
        {
            ComplexTerm ct = (ComplexTerm) question;
            Atom fileName = (Atom) ct.getTerms().get( 0 );
            String file = fileName.getFunctor().getFunctorString();
            File f = new File( file );
            if ( !f.exists() )
            {
                throw new RuntimeException( "No such file: " + file );
            }
            FileReader reader = new FileReader( f );
            return CompileGrammar.parse( reader );
        }
        catch ( FileNotFoundException e )
        {
            throw new RuntimeException( "Unable to parse file " + question, e );
        }
    }

    private static boolean switchMode( Object question )
    {
        if ( question instanceof ComplexTerm )
        {
            ComplexTerm ct = (ComplexTerm) question;
            return ct.getFunctor().getFunctorString().equals( "mode" ) && ct.getArity() == 0;
        }
        return false;
    }

    private static void eval( String read )
    {
        // parse
        List s = parseString( read );
        for ( Object o : s )
        {
            evalOne( o );
        }
    }
    
    private static void evalOne( Object s )
    {
        // todo add toggle trace...
        if ( switchMode( s ) )
        {
            switch ( mode )
            {
                case ADD:
                    mode = MODE.ASK;
                    break;
                case ASK:
                    mode = MODE.ADD;
                    break;
            }
        }
        else if ( load( s ) )
        {
            log.finer("time to load a file...");
            List toAdds = loadFile(s);
            for ( Object o : toAdds )
            {
                kb.assertKnowledge((Knowledge) o);
            }
            System.out.println("loaded " + s );
        }
        else
        {
            switch ( mode )
            {

                case ADD:
                    kb.assertKnowledge( (Knowledge) s );
                    break;
                case ASK:
                    kb.pose( (Goal) s );
                    break;
            }
        }
    }

    private static String getPrompt()
    {
        switch ( mode )
        {
            case ADD:
                return "!- ";
            case ASK:
                return "?- ";
        }
        throw new RuntimeException( "Invalid mode " + mode );
    }

    private static String read(ConsoleReader in) throws IOException
    {
        return in.readLine(getPrompt());
    }

    private static boolean done( String read )
    {
        return "exit".equalsIgnoreCase( read ) || "quit".equalsIgnoreCase( read ) || "halt".equalsIgnoreCase( read );
    }

}
