package com.pheminist.utils;

public class HUtils {
    private static final String[] NOTE_NAMES = {"c","c#","d","d#","e","f","f#","g","g#","a","a#","b"};

    public static String noteName(int tone){
        return NOTE_NAMES[tone%12];
    }

    public static String octave(int tone){
        return Integer.toString(tone/12-1);
    }

    public static String octaveAndNoteName(int tone){
        return (octave(tone)+noteName(tone));
    }

}
