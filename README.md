# naive-names

A Java library for parsing proper names.

Parsing names accurately is not easy and should be avoided if possible.  This library is _naive_, because it does not use statisical models to identify parts of a name and is intended for use on names from Western cultures.  This library attempts to provide a fast, reasonably accurate name parse without relying on any external dependencies.

In general, the parser will identify the following parts of a proper name:

| prefix | first  | middle | last      | suffix |
| ------ | ------ | ------ | --------- | ------ |
| Mr     | Walter | Bruce  | Willis    |        |
|        | Jean-Claude |   | Van Damme |        |
| Gov    | Arnold | Alois  | Schwarzenegger |   |
| Dr.    | Henry  | Walton | Jones     | Jr.    |



# Usage

```java
// Create a new parser.
// Not thread safe, but you can have many.
MultiNameParser parser = new NameParserBuilder()
    .lastNameFirst()
    .omitPunctuation()
    .buildMultiName();

//Parse a name
Name name = parser.parse("Ford Harrison").get(0);
assertEquals("Harrison", name.getFirst());
assertEquals("Ford", name.getLast());

//Parse all names
List<Name> names = parser.parse("Willis, Bruce & Jackson, Samuel L");
assertEquals(2, names.size());
assertEquals("Bruce Willis", names.get(0).toString());
assertEquals("Samuel L Jackson", names.get(1).toString());
```

## Javadoc

http://krenfro.github.io/naive-names/

Important classes are: 
* [Name](http://krenfro.github.io/naive-names/com/github/krenfro/names/Name.html)
* [MultiNameParser](http://krenfro.github.io/naive-names/com/github/krenfro/names/MultiNameParser.html)
* [SingleNameParser](http://krenfro.github.io/naive-names/com/github/krenfro/names/SingleNameParser.html)
* [NameParserBuilder](http://krenfro.github.io/naive-names/com/github/krenfro/names/NameParserBuilder.html)

## Maven
TBD: will push to maven central when first release is made, in the meantime, checkout and
```
mvn install
```



### Company Names

Often, the problem with parsing names is knowing when _not_ to parse them.  Names of companies and other legal entities do not have parts like "first" or "last" and should _not_ be parsed.  The parser will attempt to recogonize and _not_ parse these names. 
For example:

| name      |
| --------- | 
| Cyberdyne Systems, Inc |
| SETEC ASTRONOMY, LLC | 
| Tyrell Corp | 

### Owner names

If you are really unlucky, you may be required to parse names from ownership information.  Often, this information will contain multiple names with varying levels of crappiness. 

| owner name | 
| ---------- |
| Mel Gibson & Danny Glover|
| Willis, Bruce & Jackson, Samuel L | 

These are fields that contain *TWO* names, and are treated as such.

| owner name | 
| ---------- |
| Kurt Russell % Sylvester Stallone |

The "%" ( or C/O ) means "Care Of" and indicates that coorespondence for Kurt should be sent to Sylvester.  This can be important information that the parser will provide.


### Estates and Trusts

Owner names often refer to legal trusts and estates.

| owner name |
| ---------- | 
| Connor, Sarah Estate of |
| Bruce Wayne Trust |

The parser will provide parsed proper names, with additional fields containing the estate or trust tokens.


### ET UX

"ET UX" and other variations of this latin abbreviation are ignored.

### AKA

"Also Known As" (AKA) and related tokens found in the parsed text are ignored.

### Punctuation

By default, punctuation is ignored.

### Letter Case

The letter case of the input is PreSERveD.



--------------------------------------------------------------
#### When a name is not parsed correctly...
Pull requests welcome, or file an issue!
