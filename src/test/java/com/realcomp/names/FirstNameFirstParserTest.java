package com.realcomp.names;

import org.junit.Test;
import static org.junit.Assert.*;

public class FirstNameFirstParserTest{

    private final NameParser parser;

    public FirstNameFirstParserTest(){
        parser = new DefaultNameParser(false);
    }

    @Test
    public void testSimpleParsing(){
        Name name = parser.parse("Jean Claude VanDamme");
        assertEquals("Jean Claude VanDamme", name.toString());
        
        name = parser.parse("ANNA T O'CONNOR");
        assertEquals("ANNA T O'CONNOR", name.toString());
        assertFalse(name.isCompany());
    }
    
    
    @Test
    public void testMultiNames(){
        Name name = parser.parse("JAMES & PATRICIA CASE");
        assertFalse(name.isCompany());
        assertEquals("JAMES CASE", name.toString());
        name = parser.next();
        assertFalse(name.isCompany());
        assertEquals("PATRICIA CASE", name.toString());
    }

}
