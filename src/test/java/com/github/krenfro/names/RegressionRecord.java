package com.github.krenfro.names;

import com.github.krenfro.names.Name;

import java.util.List;

/**
 * Container for a raw name and its parsed Names.
 */
class RegressionRecord{

    private String raw;
    private List<Name> names;

    public RegressionRecord(){
    }
    
    public RegressionRecord(String raw, List<Name> names){
        this.raw = raw;
        this.names = names;
    }

    public String getRaw(){
        return raw;
    }

    public void setRaw(String raw){
        this.raw = raw;
    }

    public List<Name> getNames(){
        return names;
    }

    public void setNames(List<Name> names){
        this.names = names;
    }
}
