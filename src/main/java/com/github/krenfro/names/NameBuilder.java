package com.github.krenfro.names;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class NameBuilder{
    
    private final List<ParsedToken> tokens;
    private boolean lastNameFirst;
    private boolean foundLastName;
    private boolean foundFirstName;
    private int numInitials;
    private boolean estate;
    private boolean careOf;
    private boolean company;
    private boolean trust;

    public NameBuilder(boolean lastNameFirst){
        this.lastNameFirst = lastNameFirst;
        tokens = new LinkedList<>();
        foundLastName = false;
        foundFirstName = false;
        numInitials = 0;
        estate = false;
        careOf = false;
        company = false;
        trust = false;
    }
        
    public void reset(boolean lastNameFirst){
        this.lastNameFirst = lastNameFirst;
        tokens.clear();
        foundLastName = false;
        foundFirstName = false;
        numInitials = 0;
        estate = false;
        careOf = false;
        company = false;
        trust = false;
    }
    
    public void appendToPreviousToken(String value){
        if (tokens.isEmpty()){
            append(new ParsedToken(value, TokenType.UNKNOWN));
        }
        else{
            ParsedToken last = tokens.get(tokens.size() -1 );
            last.setValue(last.getValue().concat(value));
        }
    }
    
    public void append(ParsedToken token){

        if (token.getType() == TokenType.UNKNOWN){
            if (company){
                token.setType(TokenType.COMPANY);
            }
            else if (estate && lastNameFirst){
                token.setType(TokenType.ESTATE);
            }
        }

        tokens.add(token);
        if (token.getValue().length() == 1){
            numInitials++;
        }

        switch(token.getType()){
            case COMPANY:
                company = true;
                break;
            case TRUST:
                trust = true;
                break;
            case ESTATE:
                estate = true;
                break;
            case FIRST:
                foundFirstName = true;
                break;
            case LAST:
                foundLastName = true;
        }
    }
    
    public void prepend(ParsedToken token){
        if (token.getType() == TokenType.UNKNOWN){
            if (company){
                token.setType(TokenType.COMPANY);
            }
            else if (estate && lastNameFirst){
                token.setType(TokenType.ESTATE);
            }
        }

        tokens.add(0, token);
        if (token.getValue().length() == 1){
            numInitials++;
        }

        switch(token.getType()){
            case COMPANY:
                company = true;
                break;
            case TRUST:
                trust = true;
                break;
            case ESTATE:
                estate = true;
                break;
            case FIRST:
                foundFirstName = true;
                break;
            case LAST:
                foundLastName = true;
        }
    }
    

    public boolean isCompany(){
        return company;
    }
    
    public boolean isTrust(){
        return trust;
    }
    
    public void setCompany(boolean company){
        this.company = company;
    }

    public boolean hasContent(){
        return !tokens.isEmpty();
    }

    public int getNumTokens(){
        return tokens.size();
    }
    
    public int getNumInitials(){
        return numInitials;
    }
    
    public void comma(){
        lastNameFirst = true;
        assignLastName();
    }
    
    public boolean hasLastName(){
        int count = 0;
        for (ParsedToken t: tokens){
            switch(t.getType()){
                case LAST:
                    return true;
                case FIRST:
                case MIDDLE:
                case UNKNOWN:
                    count++;
            }
        }
        return count - numInitials > 1;
    }

    public boolean isCareOf(){
        return careOf;
    }

    public void setCareOf(boolean careOf){
        this.careOf = careOf;
    }

    public void setLastNameFirst(boolean lastNameFirst){
        this.lastNameFirst = lastNameFirst;
    }
    
    public boolean isLastNameFirst(){
        return lastNameFirst;
    }

    public Name build(){        
        Name name;
        if (tokens.size() == numInitials){
            company = true;
        }
        
        if (company){
            name = new Name(CompanyNameBuilder.build(tokens));
        }
        else{
            assignLastName();
            assignRemainingTokens();        
            if (foundLastName && !foundFirstName && trust){
                company = true;
                List<ParsedToken> reordered = new ArrayList<>();
                for (ParsedToken token: tokens){
                    if (token.getType() != TokenType.TRUST){
                        reordered.add(token);
                    }
                }
                for (ParsedToken token: tokens){
                    if (token.getType() == TokenType.TRUST){
                        reordered.add(token);
                    }
                }
                
                name = new Name(CompanyNameBuilder.build(reordered));
            }
            else{                
                ProperNameBuilder builder = new ProperNameBuilder(tokens);
                name = new Name(builder.build());
                name.setPrefix(builder.getPrefix());
                name.setFirst(builder.getFirst());
                name.setMiddle(builder.getMiddle());
                name.setLast(builder.getLast());
                name.setSuffix(builder.getSuffix());
            }
        }
        
        name.setCareOf(careOf);
        name.setCompany(company);
        if (estate){
            name.setEstate(combine(TokenType.ESTATE));
        }
        if (trust){
            name.setTrust(combine(TokenType.TRUST));
        }
        return name;
    }
    
    
    private String combine(TokenType type){
        StringBuilder s = new StringBuilder();
        boolean needDelimiter = false;
        for (ParsedToken token: tokens){
            if (token.getType() == type){
                if (needDelimiter){
                    s.append(" ");
                }
                needDelimiter = true;
                s.append(token.getValue());
            }
        }
        return s.toString();
    }
    
    private void assignLastName(){
        if (!company && !tokens.isEmpty() && !foundLastName){
            if (lastNameFirst){
                for (ParsedToken token: tokens){
                    if (token.getType() == TokenType.UNKNOWN){
                        token.setType(TokenType.LAST);
                        foundLastName = true;
                        break;
                    }
                }
            }
            else{
                ParsedToken token;
                for (int x = tokens.size() - 1; x >= 0; x--){
                    token = tokens.get(x);
                    if (token.getType() == TokenType.UNKNOWN){
                        token.setType(TokenType.LAST);
                        foundLastName = true;
                        break;
                    }
                }
            }
        }
    }
    
    private void assignRemainingTokens(){
        if (!company && !tokens.isEmpty()){
            for (ParsedToken token : tokens){
                if (token.getType() == TokenType.UNKNOWN){
                    if (foundFirstName){
                        token.setType(TokenType.MIDDLE);
                    }
                    else{
                        token.setType(TokenType.FIRST);
                        foundFirstName = true;
                    }
                }
            }
        }
    }
}
