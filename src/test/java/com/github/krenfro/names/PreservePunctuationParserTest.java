package com.github.krenfro.names;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PreservePunctuationParserTest{

    private final MultiNameParser parser;

    public PreservePunctuationParserTest(){
        parser = new NameParserBuilder().lastNameFirst().preservePunctuation().buildMultiName();
    }

    @Test
    public void testSimpleParsing(){
        List<Name> names = parser.parse("Jones, Dr. Henry Walton Jr.");
        assertEquals("Jr.", names.get(0).getSuffix());
        assertEquals("Dr.", names.get(0).getPrefix());
    }

}
