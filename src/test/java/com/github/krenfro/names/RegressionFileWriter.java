package com.github.krenfro.names;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Utility class to read the current regression-test.gz file and create a new regression-test.json file.
 * 
 * This class is only useful when the parsing logic has significantly changed, and a new regression test file
 * needs to be created.
 */
class RegressionFileWriter{

    private final ObjectMapper jackson;
    private final MultiNameParser parser;

    public RegressionFileWriter(){
        jackson = new ObjectMapper();
        jackson.setSerializationInclusion(Include.NON_EMPTY);
        jackson.setSerializationInclusion(Include.NON_DEFAULT);
        parser = new NameParserBuilder().lastNameFirst().omitPunctuation().buildMultiName();
    }

    public void write(InputStream in, OutputStream out) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        String s = reader.readLine();
        while (s != null){
            RegressionRecord record = jackson.readValue(s, RegressionRecord.class);
            List<Name> names = parser.parse(record.getRaw());
            String json = jackson.writeValueAsString(new RegressionRecord(record.getRaw(), names));
            writer.write(json);
            writer.newLine();
            s = reader.readLine();
        }
        writer.close();
        reader.close();
    }

    public static void main(String[] args){
        try{
            RegressionFileWriter writer = new RegressionFileWriter();
            InputStream in = new GZIPInputStream(RegressionFileWriter.class.getResourceAsStream("regression-test.gz"));
            OutputStream out = new FileOutputStream("/tmp/regression-test.json");
            writer.write(in, out);
            in.close();
            out.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
