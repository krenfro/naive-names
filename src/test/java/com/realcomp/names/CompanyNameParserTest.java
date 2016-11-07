package com.realcomp.names;

import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class CompanyNameParserTest{

    private static final Logger logger = Logger.getLogger(CompanyNameParserTest.class.getName());

    private final NameParser parser;

    public CompanyNameParserTest(){
        parser = new DefaultNameParser(true);
    }


    @Test
    public void testCompanyNameParsing(){
        Name name = parser.parse("ADVANTAGE SALES & MKTG LLC");
        assertTrue(name.isCompany());
        assertEquals("ADVANTAGE SALES & MKTG LLC", name.toString());

        name = parser.parse("MKTG LLC/CMN");
        assertTrue(name.isCompany());
                
        name = parser.parse("ADVANTAGE SALES & MKTG LLC/CMN");
        assertTrue(name.isCompany());
        assertEquals("ADVANTAGE SALES & MKTG LLC/CMN", name.toString());
        
        name = parser.parse("DEUTSCHE BK NATL TRUST CO TRUSTEE");
        assertTrue(name.isCompany());
        assertEquals("DEUTSCHE BK NATL TRUST", name.toString());
        
        //this is one of the few time a period will be preserved
        name = parser.parse("DCMLA@LIVE.COM");
        assertTrue(name.isCompany());
        assertEquals("DCMLA@LIVE.COM", name.toString());
        
        name = parser.parse("B & G CHEMICAL & EQPT CO INC");
        assertTrue(name.isCompany());
        assertEquals("B & G CHEMICAL & EQPT CO INC", name.toString());
        
        
        name = parser.parse("STANFORD M KAUFMAN &ASSOC");
        assertTrue(name.isCompany());
        assertEquals("STANFORD M KAUFMAN &ASSOC", name.toString());
        

    }
    

    @Test
    public void testCompanyNames(){

        String[] companyNames = new String[]{
            "My Company, Inc.",
            "My Company, Inc",
            "123Anything",
            "Plumbing 123",
            "Jeremy's Venture",
            "Nakatomi, LLC",
            "Nakatomi, L L C",
            "Nakatomi L L C",
            "AISD",
            "AARS & WELLS"
        };

        for (String name : companyNames){
            assertTrue("[" + name + "] not recognized as company name.", parser.parse(name).isCompany());
            assertFalse(parser.hasNext());
        }
    }

}
