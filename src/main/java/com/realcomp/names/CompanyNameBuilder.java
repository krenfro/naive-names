package com.realcomp.names;

import java.util.List;

class CompanyNameBuilder{

    public static String build(List<ParsedToken> tokens){        
        StringBuilder s = new StringBuilder();
        boolean needDelimiter = false;
        for (ParsedToken token: tokens){
            if (needDelimiter){
                s.append(" ");
            }
            s.append(token.getValue());
            needDelimiter = true;
        }
        return s.toString();
    }
}
