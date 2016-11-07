package com.realcomp.names;

import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class LastNameFirstParserTest{

    private static final Logger logger = Logger.getLogger(LastNameFirstParserTest.class.getName());

    private final NameParser parser;

    public LastNameFirstParserTest(){
        parser = new DefaultNameParser(true);
    }

    @Test
    public void testSimpleParsing(){
        Name name = parser.parse("VanDamme, Jean Claude");
        assertEquals("Jean Claude VanDamme", name.toString());
    }

    @Test
    public void testEstateNames(){
        Name name = parser.parse("AGUIRRE IRENE EST OF");
        assertTrue(name.isEstate());
        assertEquals("IRENE AGUIRRE", name.toString());
        assertEquals("EST OF", name.getEstate());

        name = parser.parse("AGUIRRE IRENE EST OF % JOHNNY A ESCAMILLA");
        assertTrue(name.isEstate());
        assertEquals("IRENE AGUIRRE", name.toString());
        assertEquals("EST OF", name.getEstate());
        name = parser.next();
        assertTrue(name.isCareOf());
        assertFalse(name.isCompany());
        assertEquals("JOHNNY A ESCAMILLA", name.toString());
        assertEquals("ESCAMILLA", name.getLast());

        name = parser.parse("AGUIRRE IRENE EST OF %JOHNNY A ESCAMILLA");
        assertTrue(name.isEstate());
        name = parser.next();
        assertTrue(name.isCareOf());
        assertFalse(name.isCompany());
        
        name = parser.parse("STAPLETON BETTY J & WALTER R EST OF");
        assertFalse(name.isEstate());
        assertEquals("BETTY J STAPLETON", name.toString());
        name = parser.next();
        assertFalse(name.isCareOf());
        assertTrue(name.isEstate());
        assertEquals("WALTER R STAPLETON", name.toString());
        
    }

    @Test
    public void testCareOf(){
        Name name = parser.parse("AHMED MAHBUB S & MINARA B % STERLING BANK");
        assertEquals("MAHBUB S AHMED", name.toString());
        assertFalse(name.isCompany());
        name = parser.next();
        assertEquals("MINARA B AHMED", name.toString());
        assertFalse(name.isCompany());
        name = parser.next();
        assertEquals("STERLING BANK", name.toString());
        assertTrue(name.isCareOf());
        assertTrue(name.isCompany());
    }

    @Test
    public void testMultiNames(){
        Name name = parser.parse("AICHLMAYR RICKY W & PENNY");
        assertFalse(name.isCompany());
        assertEquals("RICKY W AICHLMAYR", name.toString());
        name = parser.next();
        assertFalse(name.isCompany());
        assertEquals("PENNY AICHLMAYR", name.toString());

        name = parser.parse("AICHLMAYR RICKY W & PENNY R");
        assertFalse(name.isCompany());
        assertEquals("RICKY W AICHLMAYR", name.toString());
        name = parser.next();
        assertFalse(name.isCompany());
        assertEquals("PENNY R AICHLMAYR", name.toString());
        
        //handle this common form in last name first parser
        name = parser.parse("JAMES & PATRICIA CASE");
        assertFalse(name.isCompany());
        assertEquals("JAMES CASE", name.toString());
        name = parser.next();
        assertFalse(name.isCompany());
        assertEquals("PATRICIA CASE", name.toString());
        
    }

    
    @Test
    public void testSuffix(){
        Name name = parser.parse("STAFFORD ALLAN M ATTY");
        assertFalse(name.isCompany());
        assertEquals("ATTY ALLAN M STAFFORD", name.toString());
        assertEquals("ATTY", name.getPrefix());
    }
    
    @Test
    public void testCareOfParsing(){
        Name name = parser.parse("1301 GREENGRASS LP % JIM STARK");
        assertTrue(name.isCompany());
        name = parser.next();
        assertFalse(name.isCompany());
        assertTrue(name.isCareOf());
        assertEquals("JIM STARK", name.toString());

        name = parser.parse("AICHLMAYR RICKY W & PENNY");
        assertEquals("RICKY W AICHLMAYR", name.toString());
        assertFalse(name.isCompany());
        assertFalse(name.isCareOf());
        name = parser.next();
        assertEquals("PENNY AICHLMAYR", name.toString());
        assertFalse(name.isCompany());
        assertFalse(name.isCareOf());        
        
        name = parser.parse("AICHLMAYR RICKY W & PENNY % PENNY AICHLMAYR");
        assertEquals("RICKY W AICHLMAYR", name.toString());
        assertFalse(name.isCompany());
        assertFalse(name.isCareOf());
        name = parser.next();
        assertEquals("PENNY AICHLMAYR", name.toString());
        assertFalse(name.isCompany());
        assertFalse(name.isCareOf());
        name = parser.next();
        assertEquals("PENNY AICHLMAYR", name.toString());
        assertFalse(name.isCompany());
        assertTrue(name.isCareOf());
        
        name = parser.parse("ARISTA VIGINIA ESPINOZA % EST OF FABIAN ARISTA");
        assertEquals("VIGINIA ESPINOZA ARISTA", name.toString());
        assertFalse(name.isCompany());
        assertFalse(name.isCareOf());
        name = parser.next();
        assertEquals("FABIAN ARISTA", name.toString());
        assertFalse(name.isCompany());
        assertTrue(name.isEstate());
        assertTrue(name.isCareOf());
        
        name = parser.parse("DILLARD ILEAN STEWART %DILLARD RAYMOND ESTATE OF");
        assertEquals("ILEAN STEWART DILLARD", name.toString());
        assertFalse(name.isCompany());
        assertFalse(name.isCareOf());
        name = parser.next();
        assertEquals("RAYMOND DILLARD", name.toString());
        assertFalse(name.isCompany());
        assertTrue(name.isEstate());
        assertTrue(name.isCareOf());
        
        name = parser.parse("GOODMAN JEFF JR ESTATE OF % BANKERS SAV & LOAN");
        assertEquals("JEFF GOODMAN JR", name.toString());
        assertFalse(name.isCompany());
        assertFalse(name.isCareOf());
        assertTrue(name.isEstate());
        name = parser.next();
        assertEquals("BANKERS SAV & LOAN", name.toString());
        assertTrue(name.isCompany());
        assertTrue(name.isCareOf());        
        
        name = parser.parse("STAFFORD ERMA C/O DENNIS BOYD ATTORNEY AT LAW");
        assertFalse(name.isCompany());
        assertEquals("ERMA STAFFORD", name.toString());
        name = parser.next();
        assertTrue(name.isCompany());
        
    }

    
    @Test
    public void testTrusts(){
        Name name = parser.parse("REVOCABLE TRUST ZWAYNE");
        assertTrue(name.isCompany());
        assertEquals("ZWAYNE REVOCABLE TRUST", name.toString());
        
        name = parser.parse("ZOE LAND TRUST");
        assertTrue(name.isTrust());
        assertEquals("LAND ZOE", name.toString());   
    }
}
