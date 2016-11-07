package com.realcomp.names;

import org.junit.Test;
import static org.junit.Assert.*;


public class NameParsingContextTest {
    
    @Test
    public void testLastNameFirst() {
        
        SingleNameParsingContext ctx = new SingleNameParsingContext(true);
        ctx.add(new ParsedToken("LAST", TokenType.LAST)); 
        ctx.add(new ParsedToken("FIRST", TokenType.FIRST)); 
        ctx.add(new ParsedToken("MIDDLE", TokenType.MIDDLE)); 
        
        Name name = ctx.build();
        assertEquals("LAST", name.getLast());
        assertEquals("MIDDLE", name.getMiddle());
        assertEquals("FIRST", name.getFirst());
    }
    
    @Test
    public void testLastNameLast() {
        
        SingleNameParsingContext ctx = new SingleNameParsingContext(false);
        ctx.add(new ParsedToken("FIRST", TokenType.FIRST)); 
        ctx.add(new ParsedToken("MIDDLE", TokenType.MIDDLE)); 
        ctx.add(new ParsedToken("LAST", TokenType.LAST)); 
        
        Name name = ctx.build();
        assertEquals("LAST", name.getLast());
        assertEquals("MIDDLE", name.getMiddle());
        assertEquals("FIRST", name.getFirst());
    }
    
    @Test
    public void testLastNameLastWithComma() {        
        SingleNameParsingContext ctx = new SingleNameParsingContext(false);
        ctx.add(new ParsedToken("LAST", TokenType.LAST)); 
        ctx.comma();
        ctx.add(new ParsedToken("FIRST", TokenType.FIRST)); 
        ctx.add(new ParsedToken("MIDDLE", TokenType.MIDDLE)); 
        
        Name name = ctx.build();
        assertEquals("LAST", name.getLast());
        assertEquals("MIDDLE", name.getMiddle());
        assertEquals("FIRST", name.getFirst());
    }
}