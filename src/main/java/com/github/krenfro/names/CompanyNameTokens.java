package com.github.krenfro.names;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Collection of tokens that, when encountered, usually indicate the name is that of a company.
 */
public class CompanyNameTokens{
        
    private static final Set<String> tokens;    
    static{
        tokens = new HashSet<>();
        try{
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    CompanyNameTokens.class.getResourceAsStream("companyName.tokens")))) {
                String s = in.readLine();
                while (s != null){
                    tokens.add(s);
                    s = in.readLine();
                }
            }
        }
        catch (IOException ex){
            throw new IllegalStateException(ex);
        }
    }
    
    
    /**
     * @param token Not null.
     * @return true if the provided token matches a known company name token.
     */
    public static boolean contains(String token){
        return tokens.contains(token.toUpperCase());
    }
    
    /**
     * @param token Not null.
     */
    public static void add(String token){
        Objects.requireNonNull(token);
        tokens.add(token.toUpperCase());
    }
}
