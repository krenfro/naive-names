package com.github.krenfro.names;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class NameParsingContext{
    
    private final boolean defaultLastNameFirst;
    private final NameBuilder builder;
    private List<Name> names;
    private String original;    
    private boolean skipRemaining;
    private boolean lastNameFirst;
    private boolean foundCareOf;
    private boolean foundCompany;
    private boolean foundTrust;
    private boolean foundEstate;
    private int andCount;
    private String potentialFirstName;
    private String previousLastName;
    private String previousAnd;
    private boolean preservePunct;
    
    public NameParsingContext(boolean lastNameFirst){
        names = new ArrayList<>();        
        defaultLastNameFirst = lastNameFirst;
        this.lastNameFirst = lastNameFirst;
        builder = new NameBuilder(defaultLastNameFirst);
    }

    public void reset(){        
        builder.reset(defaultLastNameFirst);
        names = new ArrayList<>();
        foundCareOf = false;
        foundCompany = false;
        foundTrust = false;
        foundEstate = false;
        andCount = 0;
        potentialFirstName = null;
        previousLastName = null;
        lastNameFirst = defaultLastNameFirst;
        skipRemaining = false;
        previousAnd = null;
    }

    public List<Name> getNames(){
        return names;
    }

    public void setNames(List<Name> names){
        Objects.requireNonNull(names);
        this.names = names;
    }
    
    public void addName(Name name){
        Objects.requireNonNull(name);
        if (name.isCompany()){
            foundCompany = true;
        }
        if (name.isCareOf()){
            foundCareOf = true;
        }
        if (name.isTrust()){
            foundTrust = true;
        }
        if (name.isEstate()){
            foundEstate = true;
        }
        names.add(name);
    }

    public String getOriginal(){
        return original;
    }

    public void setOriginal(String original){
        Objects.requireNonNull(original);
        this.original = original;
    }
    
    public void done(){                
        if (builder.hasContent()){
            pushName();
        }
        skipRemaining = true;       
        if (useOriginalNameAsCompany()){
            names.clear();
            Name name = new Name(original);
            name.setCompany(true);            
            addName(name);
        }
        else{
            applyTrusts();
        }
    }
    
    private void applyTrusts(){
        //if the last name is a trust, other non-company names are also trusts
        if (foundTrust){
            Name trust = null;
            for (Name name: names){
                if (name.isTrust() && !name.isCareOf()){
                    trust = name;
                    break;
                }
            }
            if (trust != null){
                for (Name name: names){
                    if (!name.isCompany() && !name.isCareOf()){
                        name.setTrust(trust.getTrust());
                    }
                }
            }
        }
    }
    
    /**
     * After all available names are parsed, some additional checks 
     * are needed to determine if the parse should be discarded, and the original
     * name simply returned as a company name.
     * 
     * @return 
     */
    private boolean useOriginalNameAsCompany(){        
        boolean useOriginal = false;
        if (names.isEmpty()){
            useOriginal = true;
        }
        else if (andCount > 1 && foundCompany){
            //"B & G CHEMICAL & EQPT CO INC" vs "JOHN DOE & JANE SMITH & WALTER JONES"            
            useOriginal = true;
        }
        else if (andCount == 1 && foundCompany && !foundCareOf){
            useOriginal = true;
        }  
        return useOriginal;
    }
    
    public void pushName(){
        if (builder.hasContent()){
            //if (names.size() == 1 && !builder.isCareOf() && !builder.isCompany() && !builder.hasLastName()){
            if (!names.isEmpty() && !builder.isCareOf() && !builder.isCompany() && !builder.hasLastName()){
                Name spouse = names.get(0);
                if (!spouse.getLast().isEmpty()){
                    builder.append(new ParsedToken(spouse.getLast(), TokenType.LAST));
                }
            }

            if (names.isEmpty() && builder.getNumTokens() == 1 && !builder.isCompany() && !builder.isTrust()){
                builder.setCompany(true);
            }

            Name name = builder.build();
            if (name.isCompany() && !name.isCareOf() && names.size() == 1 && !names.get(0).isCompany()){
                names.clear();
                name = new Name(original);
                name.setCompany(true);
                skipRemaining = true;
            }
            else if (name.isCompany() && potentialFirstName != null && previousAnd != null && names.isEmpty()){
                //handle "name & name inc"
                builder.prepend(new ParsedToken(previousAnd, TokenType.COMPANY));
                builder.prepend(new ParsedToken(potentialFirstName, TokenType.COMPANY));                
                previousAnd = null;
                potentialFirstName = null;
                name = builder.build();
            }
            
            else if (!name.isCompany()){            
                if (previousLastName == null){
                    previousLastName = name.getLast();
                }
                if (potentialFirstName != null){
                    //handle "john & jane doe"
                    if (builder.isLastNameFirst()){
                        name = ProperNameBuilder.swapFirstAndLastName(name);
                        previousLastName = name.getLast();
                    }
                    
                    String s = potentialFirstName + " " + previousLastName;
                    Name previous = new Name(s.trim());
                    previous.setLast(previousLastName);
                    previous.setFirst(potentialFirstName);
                    addName(previous);
                }   
            }           

            addName(name);
        }

        builder.reset(lastNameFirst);
    }
    
    public void add(ParsedToken token){   
        if (!skipRemaining){
            builder.append(token);
        }
    }
    
    public void appendToPreviousToken(String value){
        if (!skipRemaining){
            builder.appendToPreviousToken(value);
        }
    }
    
    public void comma(){
        if (!skipRemaining){
            builder.comma();
        }
    }
    
    public void and(String token){
        if (!skipRemaining){
            andCount++;
            //need to determine if this is the start of a new name, or continuation of a company name.    
            if (builder.isCompany()){
                builder.append(new ParsedToken(token, TokenType.COMPANY));
            }
            else if (builder.getNumTokens() == 1 && !builder.isCareOf()){
                //a single token encountered before an &
                // this is either some company name, or a name like "john & jane doe"
                potentialFirstName = builder.build().toString();                
                //keep track of the actual "&" token in case this is like "LIN & SONS ENTERPRISES INC"
                previousAnd = token;
                builder.reset(lastNameFirst);
            }
            else if (!lastNameFirst && builder.getNumTokens() - builder.getNumInitials() <= 1){
                //N C & LELA M MILLS
                pushName();
                setLastNameFirst(false);
            }
            else if (token.length() > 1){
                //no delimiter after & (e.g., "Mike &Assoc")
                pushName();
                token = token.substring(1);
                if (CompanyNameTokens.contains(token)){
                    builder.append(new ParsedToken(token, TokenType.COMPANY));
                }
                else{
                    builder.append(new ParsedToken(token, TokenType.UNKNOWN));
                }
            }
            else{
                //ignore the "&"
                pushName();
                setLastNameFirst(false);
            }
        }
    }

    public boolean isLastNameFirst(){
        return lastNameFirst;
    }

    public boolean isFoundCareOf(){
        return foundCareOf;
    }

    public boolean isFoundCompany(){
        return foundCompany;
    }

    public boolean isFoundTrust(){
        return foundTrust;
    }

    public boolean isFoundEstate(){
        return foundEstate;
    }

    public boolean isPreservePunct(){
        return preservePunct;
    }

    public void setLastNameFirst(boolean lastNameFirst){
        this.lastNameFirst = lastNameFirst;
        builder.setLastNameFirst(lastNameFirst);
    }

    public void setPreservePunct(boolean preservePunct){
        this.preservePunct = preservePunct;
    }

    public String getPreviousLastName(){
        return previousLastName;
    }

    public void careOf(){
        foundCareOf = true;
        builder.setCareOf(true);
    }
    
}
