package com.sscire.ctof;
/**
 * @author Shannon Scire
 * 18 November 2023
 * Dr. C
 * Lab : Android Fahrenheit to Celsius Converter
 *
 * These methods are for converting between celsius and fahrenheit.
 */
public class Util {
    static double cToF(double celsius){
        double fahrenheit = 0.0;
        fahrenheit = (celsius * (9.0/5.0)) + 32.0;
        return fahrenheit;
    }

    static double fToC(double fahrenheit){
        double celsius = 0.0;
        celsius = (fahrenheit - 32.0) * (5.0/9.0) ;
        return celsius;
    }
}
