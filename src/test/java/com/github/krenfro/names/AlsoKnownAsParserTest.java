package com.github.krenfro.names;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AlsoKnownAsParserTest{

    private final MultiNameParser parser;

    public AlsoKnownAsParserTest(){
        parser = new NameParserBuilder().lastNameFirst().omitPunctuation().buildMultiName();
    }

    public void testAKA(){

        List<Name> names = parser.parse("FORCIER AKA WILLIAMS PAMELA J Y ET AL");

        assertEquals("FORCIER AKA WILLIAMS PAMELA J Y ET AL", names.get(0).toString());
    }
}
