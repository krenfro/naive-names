package com.github.krenfro.names;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import static org.junit.Assert.*;

import org.junit.Test;

public class RegressionTest {

    private static final Logger logger = Logger.getLogger(RegressionTest.class.getName());
            
    @Test
    public void testForRegressions() throws IOException{
        logger.info("Starting long regression test...");
        //regression-test contains gzipped json-encoded RegressionRecords, one per line.
        InputStream gzipStream = new GZIPInputStream(this.getClass().getResourceAsStream("regression-test.gz"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(gzipStream, "UTF-8"));
        ObjectMapper jackson = new ObjectMapper();
        MultiNameParser parser = new NameParserBuilder().lastNameFirst().omitPunctuation().buildMultiName();
        String s = reader.readLine();
        int problemCount = 0;
        int recordCount = 0;
        while (s != null){
            recordCount++;            
            RegressionRecord record = jackson.readValue(s, RegressionRecord.class);
            List<Name> names = parser.parse(record.getRaw());
            if (!names.equals(record.getNames())){
                StringBuilder msg = new StringBuilder();
                msg.append("Regression error [").append(record.getRaw()).append("]\n");
                msg.append("Old:\n");
                for (Name name: record.getNames()){
                    msg.append(name).append("\n");
                }
                msg.append("New:\n");
                for (Name name: names){
                    msg.append(name).append("\n");
                }                        
                logger.log(Level.WARNING, msg.toString());
                
                problemCount++;
            }
            s = reader.readLine();
        }
        
        reader.close();
        if (problemCount > 0){
            String message = String.format("Encountered [%s] regression errors", problemCount);
            fail(message);
        }
        
        if (recordCount < 100000){
            fail("Only read [ " + recordCount + "] regression records");
        }
    }
}
