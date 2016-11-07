package com.github.krenfro.names;

import java.util.Objects;

class ParsedToken {
    
    private String value;
    private TokenType type;
    
    public ParsedToken(String value, TokenType type){
        Objects.requireNonNull(value);
        Objects.requireNonNull(type);
        this.value = value;
        this.type = type;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        Objects.requireNonNull(value);
        this.value = value;
    }

    public TokenType getType(){
        return type;
    }
    
    public void setType(TokenType type){
        Objects.requireNonNull(type);
        this.type = type;
    }

    @Override
    public String toString(){
        return value;
    }
    
    @Override
    public int hashCode(){
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.value);
        hash = 67 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        final ParsedToken other = (ParsedToken) obj;
        if (!Objects.equals(this.value, other.value)){
            return false;
        }
        if (this.type != other.type){
            return false;
        }
        return true;
    }
    
    

}
