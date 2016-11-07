package com.github.krenfro.names;

import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;
import static org.junit.Assert.*;

public class LastNameFirstParserTest{

    private static final Logger logger = Logger.getLogger(LastNameFirstParserTest.class.getName());

    private final MultiNameParser parser;

    public LastNameFirstParserTest(){
        parser = new NameParserBuilder().lastNameFirst().omitPunctuation().buildMultiName();
    }

    @Test
    public void testSimpleParsing(){
        Name name = parser.parse("VanDamme, Jean Claude").get(0);
        assertEquals("Jean Claude VanDamme", name.toString());
        
        name = parser.parse("Willis, Mr Walter Bruce").get(0);
        assertEquals("Mr", name.getPrefix());
        
        name = parser.parse("Schwarzenegger, Gov Arnold Alois").get(0);
        assertEquals("Gov", name.getPrefix());
        
        name = parser.parse("Jones, Dr Henry Walton Jr").get(0);
        assertEquals("Jr", name.getSuffix());
        assertEquals("Dr", name.getPrefix());
    }


    @Test
    public void testCareOf(){
        List<Name> names = parser.parse("AHMED MAHBUB S & MINARA B % STERLING BANK");
        assertEquals("MAHBUB S AHMED", names.get(0).toString());
        assertFalse(names.get(0).isCompany());
        assertEquals("MINARA B AHMED", names.get(1).toString());
        assertFalse(names.get(1).isCompany());
        assertEquals("STERLING BANK", names.get(2).toString());
        assertTrue(names.get(2).isCareOf());
        assertTrue(names.get(2).isCompany());
    }

    @Test
    public void testMultiNames(){
        List<Name> names = parser.parse("AICHLMAYR RICKY W & PENNY");
        assertFalse(names.get(0).isCompany());
        assertEquals("RICKY W AICHLMAYR", names.get(0).toString());
        assertFalse(names.get(1).isCompany());
        assertEquals("PENNY AICHLMAYR", names.get(1).toString());

        names = parser.parse("AICHLMAYR RICKY W & PENNY R");
        assertFalse(names.get(0).isCompany());
        assertEquals("RICKY W AICHLMAYR", names.get(0).toString());
        assertFalse(names.get(1).isCompany());
        assertEquals("PENNY R AICHLMAYR", names.get(1).toString());
        
        //handle this common form in last name first parser
        names = parser.parse("JAMES & PATRICIA CASE");
        assertFalse(names.get(0).isCompany());
        assertEquals("JAMES CASE", names.get(0).toString());
        assertFalse(names.get(1).isCompany());
        assertEquals("PATRICIA CASE", names.get(1).toString());
        
        names = parser.parse("TENNELL BOBBIE B & VAN A");
        assertEquals("BOBBIE B TENNELL", names.get(0).toString());
        //This is one that could be improved.
        assertEquals("A VAN", names.get(1).toString());
        
        names = parser.parse("WHEELER CRAIG & NICOLE & BRENT");
        assertEquals("CRAIG WHEELER", names.get(0).toString());
        assertEquals("NICOLE WHEELER", names.get(1).toString());
        assertEquals("BRENT WHEELER", names.get(2).toString());
        
        names = parser.parse("COOKSEY TROY & PAMELA S");
        assertEquals("TROY COOKSEY", names.get(0).toString());
        assertEquals("PAMELA S COOKSEY", names.get(1).toString());
                
        names = parser.parse("HOBBS KEVIN B & GARY J & JEAN");
        assertEquals("KEVIN B HOBBS", names.get(0).toString());
        assertEquals("GARY J HOBBS", names.get(1).toString());
        assertEquals("JEAN HOBBS", names.get(2).toString());
        
                
    }
    
    

    
    @Test
    public void testSuffix(){
        List<Name> names = parser.parse("STAFFORD ALLAN M ATTY");
        assertFalse(names.get(0).isCompany());
        assertEquals("ATTY ALLAN M STAFFORD", names.get(0).toString());
        assertEquals("ATTY", names.get(0).getPrefix());
    }
    
    @Test
    public void testCareOfParsing(){
        List<Name> names = parser.parse("1301 GREENGRASS LP % JIM STARK");
        assertTrue(names.get(0).isCompany());
        assertFalse(names.get(1).isCompany());
        assertTrue(names.get(1).isCareOf());
        assertEquals("JIM STARK", names.get(1).toString());

        names = parser.parse("AICHLMAYR RICKY W & PENNY");
        assertEquals("RICKY W AICHLMAYR", names.get(0).toString());
        assertFalse(names.get(0).isCompany());
        assertFalse(names.get(0).isCareOf());
        assertEquals("PENNY AICHLMAYR", names.get(1).toString());
        assertFalse(names.get(1).isCompany());
        assertFalse(names.get(1).isCareOf());        
        
        names = parser.parse("AICHLMAYR RICKY W & PENNY % PENNY AICHLMAYR");
        assertEquals("RICKY W AICHLMAYR", names.get(0).toString());
        assertFalse(names.get(0).isCompany());
        assertFalse(names.get(0).isCareOf());
        assertEquals("PENNY AICHLMAYR", names.get(1).toString());
        assertFalse(names.get(1).isCompany());
        assertFalse(names.get(1).isCareOf());
        assertEquals("PENNY AICHLMAYR", names.get(2).toString());
        assertFalse(names.get(2).isCompany());
        assertTrue(names.get(2).isCareOf());
        
        names = parser.parse("ARISTA VIGINIA ESPINOZA % EST OF FABIAN ARISTA");
        assertEquals("VIGINIA ESPINOZA ARISTA", names.get(0).toString());
        assertFalse(names.get(0).isCompany());
        assertFalse(names.get(0).isCareOf());
        assertEquals("FABIAN ARISTA", names.get(1).toString());
        assertFalse(names.get(1).isCompany());
        assertTrue(names.get(1).isEstate());
        assertTrue(names.get(1).isCareOf());
        
        names = parser.parse("DILLARD ILEAN STEWART %DILLARD RAYMOND ESTATE OF");
        assertEquals("ILEAN STEWART DILLARD", names.get(0).toString());
        assertFalse(names.get(0).isCompany());
        assertFalse(names.get(0).isCareOf());
        assertEquals("RAYMOND DILLARD", names.get(1).toString());
        assertFalse(names.get(1).isCompany());
        assertTrue(names.get(1).isEstate());
        assertTrue(names.get(1).isCareOf());
        
        names = parser.parse("GOODMAN JEFF JR ESTATE OF % BANKERS SAV & LOAN");
        assertEquals("JEFF GOODMAN JR", names.get(0).toString());
        assertFalse(names.get(0).isCompany());
        assertFalse(names.get(0).isCareOf());
        assertTrue(names.get(0).isEstate());
        assertEquals("BANKERS SAV & LOAN", names.get(1).toString());
        assertTrue(names.get(1).isCompany());
        assertTrue(names.get(1).isCareOf());        
        
        names = parser.parse("STAFFORD ERMA C/O DENNIS BOYD ATTORNEY AT LAW");
        assertFalse(names.get(0).isCompany());
        assertEquals("ERMA STAFFORD", names.get(0).toString());
        assertTrue(names.get(1).isCompany());
        
    }

    
    @Test
    public void testTrusts(){
        List<Name> names = parser.parse("REVOCABLE TRUST ZWAYNE");
        assertTrue(names.get(0).isCompany());
        assertEquals("ZWAYNE REVOCABLE TRUST", names.get(0).toString());
        
        names = parser.parse("ZOE LAND TRUST");
        assertTrue(names.get(0).isTrust());
        assertEquals("LAND ZOE", names.get(0).toString());   
        
        names = parser.parse("THE RUTLEDGE FAMILY IRREV TRUST");
        assertTrue(names.get(0).isTrust());
        assertTrue(names.get(0).isCompany());
        assertEquals("THE RUTLEDGE FAMILY IRREV TRUST", names.get(0).toString());   
        
        names = parser.parse("THE RUTLEDGE FAMILY IRREV TR");
        assertTrue(names.get(0).isTrust());
        assertTrue(names.get(0).isCompany());
        assertEquals("THE RUTLEDGE FAMILY IRREV TR", names.get(0).toString());   
        
        names = parser.parse("WEIBLE FAMILY REVOCABLE LVG TRUST");
        assertTrue(names.get(0).isTrust());
        assertTrue(names.get(0).isCompany());
        assertEquals("WEIBLE FAMILY REVOCABLE LVG TRUST", names.get(0).toString());   
       
        
        names = parser.parse("PANNELL, JEFF & KIMBERLY TR");
        assertTrue(names.get(0).isTrust());
        assertTrue(names.get(1).isTrust());
        
        names = parser.parse("GRADASSI LIVING TR");        
        assertEquals("GRADASSI LIVING TR", names.get(0).toString());   
        
        
        names = parser.parse("GELINAS LEO P & LISE P % GELINAS FAM LIVING TRUST");
        assertFalse(names.get(0).isTrust());
        assertFalse(names.get(1).isTrust());
        assertTrue(names.get(2).isTrust());
        
                
        
    }
}
