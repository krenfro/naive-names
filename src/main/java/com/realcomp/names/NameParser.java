package com.realcomp.names;

import java.util.List;

public interface NameParser{

    /**
     * @param parse not null, nor empty
     * @return The first parsed Name, never null.
     */
    public Name parse(String parse);
    
    /**
     * Convenience method to parse and return all the Names.
     * @param parse not null, nor empty
     * @return All parsed names, never empty nor null.
     */
    public List<Name> parseAll(String parse);
    
    /**
     * @return true if the previous parse has another name
     */
    public boolean hasNext();
    
    /**
     * @return the next parsed name, or null if not available.
     */
    public Name next();
    
}
