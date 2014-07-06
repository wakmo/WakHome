package com.wakkir.util;

/**
 * Created with IntelliJ IDEA.
 * User: wakkir
 * Date: 30/12/12
 * Time: 03:13
 * To change this template use File | Settings | File Templates.
 */
/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import java.util.*;
import java.text.*;

public class DecimalFormatter
{

    static public String getValue(String pattern, String value )
    {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(Double.valueOf(value));
        //System.out.println(value + "  " + pattern + "  " + output);
        return output;
    }

    static public String getValue(String pattern, String value, Locale loc)
    {
        NumberFormat nf = NumberFormat.getNumberInstance(loc);
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern(pattern);
        String output = df.format(Double.valueOf(value));
        //System.out.println(pattern + "  " + output + "  " + loc.toString());
        return output;
    }

    static public void customFormat(String pattern, double value )
    {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        System.out.println(value + "  " + pattern + "  " + output);
    }

    static public void localizedFormat(String pattern, double value, Locale loc )
    {
        NumberFormat nf = NumberFormat.getNumberInstance(loc);
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern(pattern);
        String output = df.format(value);
        System.out.println(pattern + "  " + output + "  " + loc.toString());
    }

    static public void main(String[] args)
    {

        customFormat("###,###.###", 123456.789);
        customFormat("###.##", 123456.789);
        customFormat("000000.000", 123.78);
        customFormat("$###,###.###", 12345.67);
        customFormat("\u00A3###,###.###", 12345.67);

        Locale currentLocale = new Locale("en", "GB");

        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(currentLocale);
        unusualSymbols.setDecimalSeparator('|');
        unusualSymbols.setGroupingSeparator('^');
        String strange = "#,##0.###";
        DecimalFormat weirdFormatter = new DecimalFormat(strange, unusualSymbols);
        weirdFormatter.setGroupingSize(4);
        String bizarre = weirdFormatter.format(12345.678);
        System.out.println(bizarre);

        Locale[] locales =
               {
                new Locale("en", "GB"),
                new Locale("de", "DE"),
                new Locale("fr", "FR")
                };

        for (int i = 0; i < locales.length; i++)
        {
            localizedFormat("###,###.###", 123456.789, locales[i]);
        }

    }
}