package com.github.krenfro.names;

import java.util.List;

import com.github.krenfro.names.MultiNameParser;
import com.github.krenfro.names.Name;
import com.github.krenfro.names.NameParserBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

public class FirstNameFirstParserTest{

    private final MultiNameParser parser;

    public FirstNameFirstParserTest(){
        parser = new NameParserBuilder().firstNameFirst().omitPunctuation().buildMultiName();
    }

    @Test
    public void testSimpleParsing(){
        Name name = parser.parse("Jean Claude VanDamme").get(0);
        assertEquals("Jean Claude VanDamme", name.toString());
        
        name = parser.parse("ANNA T O'CONNOR").get(0);
        assertEquals("ANNA T O'CONNOR", name.toString());
        assertFalse(name.isCompany());
    }
    
    
    @Test
    public void testMultiNames(){
        List<Name> names = parser.parse("JAMES & PATRICIA CASE");
        assertFalse(names.get(0).isCompany());
        assertEquals("JAMES CASE", names.get(0).toString());
        assertFalse(names.get(1).isCompany());
        assertEquals("PATRICIA CASE", names.get(1).toString());
        
   //     names = parser.parse("N C & LELA M MILLS");
   //     assertEquals("N C MILLS", names.get(0).toString());
   //     assertFalse(names.get(1).isCompany());
   //     assertEquals("LELA M MILLS", names.get(1).toString());
        
    }

}
