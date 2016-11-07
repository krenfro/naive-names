package com.github.krenfro.names;

import com.github.krenfro.names.Name;
import com.github.krenfro.names.NameBuilder;
import com.github.krenfro.names.ParsedToken;
import com.github.krenfro.names.TokenType;
import org.junit.Test;
import static org.junit.Assert.*;


public class NameBuilderTest {
    
    @Test
    public void testLastNameFirst() {
        
        NameBuilder ctx = new NameBuilder(true);
        ctx.append(new ParsedToken("LAST", TokenType.LAST));
        ctx.append(new ParsedToken("FIRST", TokenType.FIRST)); 
        ctx.append(new ParsedToken("MIDDLE", TokenType.MIDDLE)); 
        
        Name name = ctx.build();
        assertEquals("LAST", name.getLast());
        assertEquals("MIDDLE", name.getMiddle());
        assertEquals("FIRST", name.getFirst());
    }
    
    @Test
    public void testLastNameLast() {
        
        NameBuilder ctx = new NameBuilder(false);
        ctx.append(new ParsedToken("FIRST", TokenType.FIRST)); 
        ctx.append(new ParsedToken("MIDDLE", TokenType.MIDDLE)); 
        ctx.append(new ParsedToken("LAST", TokenType.LAST)); 
        
        Name name = ctx.build();
        assertEquals("LAST", name.getLast());
        assertEquals("MIDDLE", name.getMiddle());
        assertEquals("FIRST", name.getFirst());
    }
    
    @Test
    public void testLastNameLastWithComma() {        
        NameBuilder ctx = new NameBuilder(false);
        ctx.append(new ParsedToken("LAST", TokenType.LAST)); 
        ctx.comma();
        ctx.append(new ParsedToken("FIRST", TokenType.FIRST)); 
        ctx.append(new ParsedToken("MIDDLE", TokenType.MIDDLE)); 
        
        Name name = ctx.build();
        assertEquals("LAST", name.getLast());
        assertEquals("MIDDLE", name.getMiddle());
        assertEquals("FIRST", name.getFirst());
    }
}