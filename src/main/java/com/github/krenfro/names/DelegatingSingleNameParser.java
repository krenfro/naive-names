package com.github.krenfro.names;

import java.util.Objects;

class DelegatingSingleNameParser implements SingleNameParser{

    private final MultiNameParser delegate;
    
    public DelegatingSingleNameParser(MultiNameParser parser){
        Objects.requireNonNull(parser);
        this.delegate = parser;
    }
    
    @Override
    public Name parse(String text){
        return delegate.parse(text).get(0);
    }
}
