package com.realcomp.names;

public class NameParserFactory {

    /**
     * @param lastNameFirst
     * @return a new NameParser
     */
    public static NameParser build(boolean lastNameFirst){
        return new DefaultNameParser(lastNameFirst);
    }
}
