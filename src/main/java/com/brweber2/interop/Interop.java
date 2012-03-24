/*
 * Copyright (C) 2012 brweber2
 */
package com.brweber2.interop;

import com.brweber2.term.Atom;
import com.brweber2.term.Numeric;
import com.brweber2.term.impl.AnAtom;
import com.brweber2.term.impl.AnNumeric;

import java.math.BigDecimal;

public class Interop
{
    public static Atom toAtom( String s )
    {
        return new AnAtom( s );
    }
    
    public static String fromAtom( Atom a )
    {
        return a.getFunctor().getFunctorString();
    }
    
    public static Numeric toNumeric( Number n )
    {
        return new AnNumeric( "" + n );
    }
    
    public static Number fromNumeric( Numeric n )
    {
        return new BigDecimal( n.getFunctor().getFunctorString() );
    }

    public static void main( String[] args )
    {
        System.out.println( toNumeric( 234.2346D ) );
        System.out.println( fromNumeric( toNumeric( 234.2346D ) ));

        System.out.println( toNumeric( new BigDecimal( "" +234.2346D ) ) );
        System.out.println( fromNumeric( toNumeric( new BigDecimal( "" + 234.2346D ) )) );
    }
}
