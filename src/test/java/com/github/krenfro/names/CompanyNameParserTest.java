package com.github.krenfro.names;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompanyNameParserTest{

    private final MultiNameParser parser;

    public CompanyNameParserTest(){
        parser = new NameParserBuilder().lastNameFirst().omitPunctuation().buildMultiName();
    }


    @Test
    public void testCompanyNameParsing(){
        Name name = parser.parse("ADVANTAGE SALES & MKTG LLC").get(0);
        assertTrue(name.isCompany());
        assertEquals("ADVANTAGE SALES & MKTG LLC", name.toString());

        name = parser.parse("MKTG LLC/CMN").get(0);
        assertTrue(name.isCompany());
                
        name = parser.parse("ADVANTAGE SALES & MKTG LLC/CMN").get(0);
        assertTrue(name.isCompany());
        assertEquals("ADVANTAGE SALES & MKTG LLC/CMN", name.toString());
        
        name = parser.parse("DEUTSCHE BK NATL TRUST CO TRUSTEE").get(0);
        assertTrue(name.isCompany());
        assertEquals("DEUTSCHE BK NATL TRUST", name.toString());
        
        //this is one of the few time a period will be preserved
        name = parser.parse("DCMLA@LIVE.COM").get(0);
        assertTrue(name.isCompany());
        assertEquals("DCMLA@LIVE.COM", name.toString());
        
        name = parser.parse("B & G CHEMICAL & EQPT CO INC").get(0);
        assertTrue(name.isCompany());
        assertEquals("B & G CHEMICAL & EQPT CO INC", name.toString());
        
        
        name = parser.parse("STANFORD M KAUFMAN &ASSOC").get(0);
        assertTrue(name.isCompany());
        assertEquals("STANFORD M KAUFMAN &ASSOC", name.toString());
        
        
        List<Name> names = parser.parse("LIN & SONS ENTERPRISES INC % MS LEE-LEE LIN");
        assertEquals(2, names.size());
        assertEquals("LIN & SONS ENTERPRISES INC", names.get(0).toString());
        assertEquals("MS LEE-LEE LIN", names.get(1).toString());
        
         
        names = parser.parse("WEAVER TR  & ASSOCIATES PC");
        assertTrue(names.get(0).isCompany());
        assertEquals("WEAVER TR  & ASSOCIATES PC", names.get(0).toString());   
        
        
        

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
            "Tyrell Corp",
            "AARS & WELLS"
        };

        for (String name : companyNames){
            List<Name> result = parser.parse(name);
            assertTrue("[" + name + "] not recognized as company name.", result.get(0).isCompany());
            assertEquals(1, result.size());
        }
    }

}
