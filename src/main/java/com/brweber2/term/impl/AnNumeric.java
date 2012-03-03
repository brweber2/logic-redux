package com.brweber2.term.impl;

import com.brweber2.term.Numeric;

/**
 * @author brweber2
 *         Copyright: 2012
 */
public class AnNumeric implements Numeric {
    private final String number;

    public AnNumeric(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
