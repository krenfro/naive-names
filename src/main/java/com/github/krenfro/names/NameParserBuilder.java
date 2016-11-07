package com.github.krenfro.names;

/**
 * <p>Builder for NameParsers.</p>
 * 
 * By default, punctuation is <b>omitted</b> and names to be parsed are provided <b>last name first</b>.
 * 
 * <p>
 * <pre>
 new NameBuilder().lastNameFirst().omitPunctuation().buildMultiName();
 - is equivalent to -  
 new NameBuilder().buildMultiName();</pre>
 * </p>
 */
public class NameParserBuilder {

    private boolean lastNameFirst = true;
    private boolean preservePunct = false;
   
    /**
     * Hint that the names being parsed are <i>last name</i> first. (Default)
     * @return this
     */
    public NameParserBuilder lastNameFirst(){
        lastNameFirst = true;
        return this;
    }
    
    /**
     * Hint that the names being parsed are <i>first name</i> first
     * @return this
     */
    public NameParserBuilder firstNameFirst(){
        lastNameFirst = false;
        return this;
    }
    
    /**
     * Preserve punctuation in the parsed Name.
     * @return this
     */
    public NameParserBuilder preservePunctuation(){
        preservePunct = true;
        return this;
    }
    
    /**
     * Omit punctuation in the parsed name. (Default)
     * @return this
     */
    public NameParserBuilder omitPunctuation(){
        preservePunct = false;
        return this;
    }
    
    public MultiNameParser buildMultiName(){
        NameParsingContext ctx = new NameParsingContext(lastNameFirst);
        ctx.setPreservePunct(preservePunct);
        return new DefaultMultiNameParser(ctx);
    }
    
    public SingleNameParser buildSingleName(){
        return new DelegatingSingleNameParser(buildMultiName());
    }
}
