package com.github.krenfro.names;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;
import static org.junit.Assert.*;

public class EstateNameParserTest{

    private static final Logger logger = Logger.getLogger(EstateNameParserTest.class.getName());

    private final MultiNameParser parser;

    public EstateNameParserTest(){
        parser = new NameParserBuilder().lastNameFirst().omitPunctuation().buildMultiName();
    }

    @Test
    public void testEstateNames(){
        List<Name> names = parser.parse("AGUIRRE IRENE EST OF");
        assertTrue(names.get(0).isEstate());
        assertEquals("IRENE AGUIRRE", names.get(0).toString());
        assertEquals("EST OF", names.get(0).getEstate());

        names = parser.parse("AGUIRRE IRENE EST OF % JOHNNY A ESCAMILLA");
        assertTrue(names.get(0).isEstate());
        assertEquals("IRENE AGUIRRE", names.get(0).toString());
        assertEquals("EST OF", names.get(0).getEstate());
        assertTrue(names.get(1).isCareOf());
        assertFalse(names.get(1).isCompany());
        assertEquals("JOHNNY A ESCAMILLA", names.get(1).toString());
        assertEquals("ESCAMILLA", names.get(1).getLast());

        names = parser.parse("AGUIRRE IRENE EST OF %JOHNNY A ESCAMILLA");
        assertTrue(names.get(0).isEstate());
        assertTrue(names.get(1).isCareOf());
        assertFalse(names.get(1).isCompany());
        
        names = parser.parse("STAPLETON BETTY J & WALTER R EST OF");
        assertFalse(names.get(0).isEstate());
        assertEquals("BETTY J STAPLETON", names.get(0).toString());
        assertFalse(names.get(1).isCareOf());
        assertTrue(names.get(1).isEstate());
        assertEquals("WALTER R STAPLETON", names.get(1).toString());
        
    }

    
}
