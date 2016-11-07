package com.github.krenfro.names;

import java.util.List;

class ProperNameBuilder{

    private String prefix;
    private String first;
    private String middle;
    private String last;
    private String suffix;

    public ProperNameBuilder(){
    }

    public ProperNameBuilder(Name name){
        prefix(name.getPrefix());
        first(name.getFirst());
        middle(name.getMiddle());
        last(name.getLast());
        suffix(name.getSuffix());
    }

    public static Name swapFirstAndLastName(Name name){
        ProperNameBuilder builder = new ProperNameBuilder(name);
        String temp = builder.last;
        builder.last = builder.first;
        builder.first = temp;
        Name swapped = new Name(builder.build());
        swapped.setPrefix(builder.getFirst());
        swapped.setFirst(builder.getFirst());
        swapped.setMiddle(builder.getMiddle());
        swapped.setLast(builder.getLast());
        swapped.setSuffix(builder.getSuffix());
        return swapped;
    }

    public ProperNameBuilder(List<ParsedToken> tokens){
        for (ParsedToken token : tokens){
            switch (token.getType()){
                case PREFIX:
                    prefix(token.getValue());
                    break;
                case FIRST:
                    first(token.getValue());
                    break;
                case MIDDLE:
                    middle(token.getValue());
                    break;
                case LAST:
                    last(token.getValue());
                    break;
                case SUFFIX:
                    suffix(token.getValue());
                    break;
            }
        }
    }

    private void first(String token){
        if (token != null && !token.isEmpty()){
            if (this.first == null){
                this.first = token;
            }
            else{
                this.first = this.first + " " + token;
            }
        }
    }

    private void last(String token){
        if (!token.isEmpty()){
            if (this.last == null){
                this.last = token;
            }
            else{
                this.last = this.last + " " + token;
            }
        }
    }

    private void middle(String token){
        if (token != null && !token.isEmpty()){
            if (this.middle == null){
                this.middle = token;
            }
            else{
                this.middle = this.middle + " " + token;
            }
        }
    }

    private void prefix(String token){
        if (token != null && !token.isEmpty()){
            if (this.prefix == null){
                this.prefix = token;
            }
            else{
                this.prefix = this.prefix + " " + token;
            }
        }
    }

    private void suffix(String token){
        if (token != null && !token.isEmpty()){
            if (this.suffix == null){
                this.suffix = token;
            }
            else{
                this.suffix = this.suffix + " " + token;
            }
        }
    }

    public String getPrefix(){
        return prefix == null ? "" : prefix;
    }

    public String getSuffix(){
        return suffix == null ? "" : suffix;
    }

    public String getFirst(){
        return first == null ? "" : first;
    }

    public String getMiddle(){
        return middle == null ? "" : middle;
    }

    public String getLast(){
        return last == null ? "" : last;
    }

    public String build(){
        StringBuilder s = new StringBuilder();
        if (prefix != null){
            s.append(prefix).append(" ");
        }
        if (first != null){
            s.append(first).append(" ");
        }
        if (middle != null){
            s.append(middle).append(" ");
        }
        if (last != null){
            s.append(last).append(" ");
        }
        if (suffix != null){
            s.append(suffix);
        }
        return s.toString().trim();
    }
}
