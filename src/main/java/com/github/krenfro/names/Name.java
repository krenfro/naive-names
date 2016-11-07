package com.github.krenfro.names;

import java.util.Objects;

/**
 * <p>The name of an individual or legal entity.</p>
 * 
 * <p>Stores at a minimum, the canonical representation of a name.</p>
 * <p>
 * Additional meta-data <i>may</i> also be present, including:
 * <ul>
 *  <li>flag indicating whether the name is that of a company or legal entity.</li>
 *  <li>flag indicating whether the name is a "care-of" name.</li>
 *  <li>parts of an individual's name including:
 *     <ol>
 *      <li>prefix  (e.g., MR, MRS, DR, ...)</li>
 *      <li>first</li>
 *      <li>middle (may contain several space delimited tokens)</li>
 *      <li>last</li>
 *      <li>suffix  (e.g., SR, JR, ...)</li>
 *     </ol>
 *  </li>
 *  <li>flag indicating whether the name is that of a legal trust 
 *      along with the trust description (e.g., "Trust" in "Bill Gates Trust")</li>
 *  <li>flag indicating whether the name is that of an estate
 *      along with the estate description (e.g., "Estate Of" in "Estate of Bill Gates")</li>
 * </ul>
 * </p>
 * 
 * <p>All get methods will never return null.</p>
 * 
 * 
 * 
 */
public class Name {

    private String name = "";
    private String prefix = "";
    private String first = "";
    private String middle = "";
    private String last = "";
    private String suffix = "";
    private boolean company;
    private boolean careOf;
    private String trust = "";
    private String estate = "";
    
    public Name(){
    }
    
    /**
     * @param name Not null.
     */
    public Name(String name){
        Objects.requireNonNull(name);
        this.name = name;
    }

    /**
     * @return The complete canonical name for this individual or legal entity. Never null.
     */
    public String getName(){
        return name;
    }

    /**
     * @param name Not null.
     */
    public void setName(String name){
        Objects.requireNonNull(name);
        this.name = name;
    }    

    /**
     * @return the name prefix. Never null.
     */
    public String getPrefix(){
        return prefix;
    }

    /**
     * @param prefix Not null.
     */
    public void setPrefix(String prefix){
        Objects.requireNonNull(prefix);
        this.prefix = prefix;
    }

    /**
     * @return first name. Never null.
     */ 
    public String getFirst(){
        return first;
    }

    /**
     * @param first Not null.
     */
    public void setFirst(String first){
        Objects.requireNonNull(first);
        this.first = first;
    }

    /**
     * @return Never null.
     */
    public String getMiddle(){
        return middle;
    }

    /**
     * 
     * @param middle Not null.
     */
    public void setMiddle(String middle){
        Objects.requireNonNull(middle);
        this.middle = middle;
    }

    /**
     * @return Never null.
     */
    public String getLast(){
        return last;
    }

    /**
     * @param last  Not null.
     */
    public void setLast(String last){
        Objects.requireNonNull(last);
        this.last = last;
    }

    /**
     * @return Never null.
     */
    public String getSuffix(){
        return suffix;
    }

    /**
     * @param suffix Not null.
     */
    public void setSuffix(String suffix){
        Objects.requireNonNull(suffix);
        this.suffix = suffix;
    }

    /**
     * @return true if the name represents a corporation; else false.
     */
    public boolean isCompany(){
        return company;
    }
    
    public void setCompany(boolean company){
        this.company = company;
    }

    /**
     * @return true if the name was marked as the 'care-of' name.
     */
    public boolean isCareOf(){
        return careOf;
    }

    public void setCareOf(boolean careOf){
        this.careOf = careOf;
    }

    /**
     * @return trust tokens. Never null.
     */
    public String getTrust(){
        return trust;
    }

    /**
     * @param trust the trust tokens. Not null. (e.g., 'Trust Of')
     */
    public void setTrust(String trust){
        this.trust = trust;
    }

    
    /**
     * @return true if the name is that of a legal trust; else false.
     */
    public boolean isTrust(){
        return !trust.isEmpty();
    }
        
    /**
     * 
     * @return Estate tokens. (e.g., "Estate Of". Not null.)
     */
    public String getEstate(){
        return estate;
    }

    /**
     * @param estate the estate tokens. Not null. (e.g., "Estate Of")
     */
    public void setEstate(String estate){
        Objects.requireNonNull(estate);
        this.estate = estate;
    }

    /**
     * @return true if the name is that of an estate; else false.
     */
    public boolean isEstate(){
        return !estate.isEmpty();
    }

    /**
     * @return The complete canonical name for this individual or legal entity. Never null.
     * @see Name#getName()
     */
    @Override
    public String toString(){
        return name;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.prefix);
        hash = 53 * hash + Objects.hashCode(this.first);
        hash = 53 * hash + Objects.hashCode(this.middle);
        hash = 53 * hash + Objects.hashCode(this.last);
        hash = 53 * hash + Objects.hashCode(this.suffix);
        hash = 53 * hash + (this.company ? 1 : 0);
        hash = 53 * hash + (this.careOf ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.trust);
        hash = 53 * hash + Objects.hashCode(this.estate);
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
        final Name other = (Name) obj;
        if (!Objects.equals(this.name, other.name)){
            return false;
        }
        if (!Objects.equals(this.prefix, other.prefix)){
            return false;
        }
        if (!Objects.equals(this.first, other.first)){
            return false;
        }
        if (!Objects.equals(this.middle, other.middle)){
            return false;
        }
        if (!Objects.equals(this.last, other.last)){
            return false;
        }
        if (!Objects.equals(this.suffix, other.suffix)){
            return false;
        }
        if (this.company != other.company){
            return false;
        }
        if (this.careOf != other.careOf){
            return false;
        }
        if (!Objects.equals(this.trust, other.trust)){
            return false;
        }
        if (!Objects.equals(this.estate, other.estate)){
            return false;
        }
        return true;
    }

    
    
}
