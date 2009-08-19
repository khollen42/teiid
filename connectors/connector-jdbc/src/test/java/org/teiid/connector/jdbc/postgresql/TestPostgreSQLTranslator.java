/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */

package org.teiid.connector.jdbc.postgresql;

import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;
import org.teiid.connector.api.ConnectorException;
import org.teiid.connector.jdbc.MetadataFactory;

import com.metamatrix.cdk.api.EnvironmentUtility;

public class TestPostgreSQLTranslator {

    private static PostgreSQLTranslator TRANSLATOR; 

    @BeforeClass public static void setupOnce() throws Exception {
        TRANSLATOR = new PostgreSQLTranslator();        
        TRANSLATOR.initialize(EnvironmentUtility.createEnvironment(new Properties(), false));
    }
    
    public String getTestVDB() {
        return MetadataFactory.PARTS_VDB;
    }
    
    private String getTestBQTVDB() {
        return MetadataFactory.BQT_VDB;
    }
        
    public void helpTestVisitor(String vdb, String input, String expectedOutput) throws ConnectorException {
        MetadataFactory.helpTestVisitor(vdb, input, expectedOutput, TRANSLATOR);
    }

    @Test public void testConversion1() throws Exception {
        String input = "SELECT char(convert(PART_WEIGHT, integer) + 100) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT chr((cast(PARTS.PART_WEIGHT AS integer) + 100)) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
          
    @Test public void testConversion2() throws Exception {
        String input = "SELECT convert(PART_WEIGHT, long) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT cast(PARTS.PART_WEIGHT AS bigint) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
          
    @Test public void testConversion3() throws Exception {
        String input = "SELECT convert(PART_WEIGHT, short) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT cast(PARTS.PART_WEIGHT AS smallint) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
          
    @Test public void testConversion4() throws Exception {
        String input = "SELECT convert(PART_WEIGHT, float) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT cast(PARTS.PART_WEIGHT AS real) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion5() throws Exception {
        String input = "SELECT convert(PART_WEIGHT, double) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT cast(PARTS.PART_WEIGHT AS float8) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion6() throws Exception {
        String input = "SELECT convert(PART_WEIGHT, biginteger) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT cast(PARTS.PART_WEIGHT AS numeric) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion7() throws Exception {
        String input = "SELECT convert(PART_WEIGHT, bigdecimal) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT cast(PARTS.PART_WEIGHT AS decimal) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion8() throws Exception {
        String input = "SELECT convert(PART_WEIGHT, boolean) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT cast(PARTS.PART_WEIGHT AS boolean) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion9() throws Exception {
        String input = "SELECT convert(PART_WEIGHT, date) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT to_date(PARTS.PART_WEIGHT, 'YYYY-MM-DD') FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion10() throws Exception {
        String input = "SELECT convert(PART_WEIGHT, time) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT to_timestamp(('1970-01-01 ' || PARTS.PART_WEIGHT), 'YYYY-MM-DD HH24:MI:SS') FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion11() throws Exception {
        String input = "SELECT convert(PART_WEIGHT, timestamp) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF') FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion12() throws Exception {
        String input = "SELECT convert(convert(PART_WEIGHT, time), string) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT to_char(to_timestamp(('1970-01-01 ' || PARTS.PART_WEIGHT), 'YYYY-MM-DD HH24:MI:SS'), 'HH24:MI:SS') FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion13() throws Exception {
        String input = "SELECT convert(convert(PART_WEIGHT, timestamp), string) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT to_char(to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF'), 'YYYY-MM-DD HH24:MI:SS.US') FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion14() throws Exception {
        String input = "SELECT convert(convert(PART_WEIGHT, date), string) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT to_char(to_date(PARTS.PART_WEIGHT, 'YYYY-MM-DD'), 'YYYY-MM-DD') FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion15() throws Exception {
        String input = "SELECT convert(convert(PART_WEIGHT, timestamp), date) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT cast(to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF') AS date) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion16() throws Exception {
        String input = "SELECT convert(convert(PART_WEIGHT, timestamp), time) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT cast(to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF') AS time) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion17() throws Exception {
        String input = "SELECT convert(convert(PART_WEIGHT, time), timestamp) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT to_timestamp(to_char(to_timestamp(('1970-01-01 ' || PARTS.PART_WEIGHT), 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS') FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion18() throws Exception {
        String input = "SELECT convert(convert(PART_WEIGHT, date), timestamp) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT to_timestamp(to_char(to_date(PARTS.PART_WEIGHT, 'YYYY-MM-DD'), 'YYYY-MM-DD HH24:MI:SS'), 'YYYY-MM-DD HH24:MI:SS') FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testConversion19() throws Exception {
        String input = "SELECT convert(convert(PART_WEIGHT, boolean), string) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT CASE WHEN cast(PARTS.PART_WEIGHT AS boolean) = TRUE THEN '1' ELSE '0' END FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    
    @Test public void testLog() throws Exception {
        String input = "SELECT log(convert(PART_WEIGHT, double)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT ln(cast(PARTS.PART_WEIGHT AS float8)) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
        input = "SELECT log10(convert(PART_WEIGHT, double)) FROM PARTS"; //$NON-NLS-1$
        output = "SELECT log(cast(PARTS.PART_WEIGHT AS float8)) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    
    @Test public void testLeft() throws Exception {
        String input = "SELECT left(PART_WEIGHT, 2) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT SUBSTR(PARTS.PART_WEIGHT, 1, 2) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testRight() throws Exception {
        String input = "SELECT right(PART_WEIGHT, 2) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT SUBSTR(PARTS.PART_WEIGHT, (-1 * 2)) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    
    @Test public void testDayOfWeek() throws Exception {
        String input = "SELECT dayofweek(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT (EXTRACT(DOW FROM to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF')) + 1) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testDayOfMonth() throws Exception {
        String input = "SELECT dayofmonth(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT EXTRACT(DAY FROM to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF')) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testDayOfYear() throws Exception {
        String input = "SELECT dayofyear(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT EXTRACT(DOY FROM to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF')) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testHour() throws Exception {
        String input = "SELECT hour(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT EXTRACT(HOUR FROM to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF')) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testMinute() throws Exception {
        String input = "SELECT minute(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT EXTRACT(MINUTE FROM to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF')) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testMonth() throws Exception {
        String input = "SELECT month(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT EXTRACT(MONTH FROM to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF')) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testQuarter() throws Exception {
        String input = "SELECT quarter(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT EXTRACT(QUARTER FROM to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF')) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testSecond() throws Exception {
        String input = "SELECT second(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT EXTRACT(SECOND FROM to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF')) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testWeek() throws Exception {
        String input = "SELECT week(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT EXTRACT(WEEK FROM to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF')) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testYear() throws Exception {
        String input = "SELECT year(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT EXTRACT(YEAR FROM to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF')) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testDayName() throws Exception {
        String input = "SELECT dayname(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT RTRIM(TO_CHAR(to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF'), 'Day')) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testMonthName() throws Exception {
        String input = "SELECT monthname(convert(PART_WEIGHT, timestamp)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT RTRIM(TO_CHAR(to_timestamp(PARTS.PART_WEIGHT, 'YYYY-MM-DD HH24:MI:SS.UF'), 'Month')) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testIfnull() throws Exception {
        String input = "SELECT ifnull(PART_WEIGHT, 'otherString') FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT coalesce(PARTS.PART_WEIGHT, 'otherString') FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testSubstring1() throws Exception {
        String input = "SELECT substring(PART_WEIGHT, 1) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT substr(PARTS.PART_WEIGHT, 1) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testSubstring2() throws Exception {
        String input = "SELECT substring(PART_WEIGHT, 1, 5) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT substr(PARTS.PART_WEIGHT, 1, 5) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testBooleanAggregate() throws Exception {
        String input = "SELECT MIN(convert(PART_WEIGHT, boolean)) FROM PARTS"; //$NON-NLS-1$
        String output = "SELECT bool_and(cast(PARTS.PART_WEIGHT AS boolean)) FROM PARTS";  //$NON-NLS-1$

        helpTestVisitor(getTestVDB(),
            input, 
            output);
    }
    @Test public void testRowLimit2() throws Exception {
        String input = "select intkey from bqt1.smalla limit 100"; //$NON-NLS-1$
        String output = "SELECT SmallA.IntKey FROM SmallA LIMIT 100"; //$NON-NLS-1$
               
        helpTestVisitor(getTestBQTVDB(),
            input, 
            output);        
    }
    @Test public void testRowLimit3() throws Exception {
        String input = "select intkey from bqt1.smalla limit 50, 100"; //$NON-NLS-1$
        String output = "SELECT SmallA.IntKey FROM SmallA LIMIT 100 OFFSET 50"; //$NON-NLS-1$
               
        helpTestVisitor(getTestBQTVDB(),
            input, 
            output);        
    }    
    
    @Test public void testBitFunctions() throws Exception {
        String input = "select bitand(intkey, intnum), bitnot(intkey), bitor(intnum, intkey), bitxor(intnum, intkey) from bqt1.smalla"; //$NON-NLS-1$
        String output = "SELECT (SmallA.IntKey & SmallA.IntNum), ~(SmallA.IntKey), (SmallA.IntNum | SmallA.IntKey), (SmallA.IntNum # SmallA.IntKey) FROM SmallA"; //$NON-NLS-1$
               
        helpTestVisitor(getTestBQTVDB(),
            input, 
            output);        
    }
    
    /**
     * Test the translator's ability to rewrite the LOCATE() function in a form 
     * suitable for the data source.
     * <p>
     * <code>SELECT locate(INTNUM, 'chimp', 1) FROM BQT1.SMALLA</code>
     *  
     * @throws Exception
     */
    @Test public void testLocate() throws Exception {
        String input = "SELECT locate(INTNUM, 'chimp', 1) FROM BQT1.SMALLA"; //$NON-NLS-1$
        String output = "SELECT position(cast(SmallA.IntNum AS varchar) in substr('chimp', 1)) FROM SmallA";  //$NON-NLS-1$

        MetadataFactory.helpTestVisitor(MetadataFactory.BQT_VDB,
                input, output, 
                TRANSLATOR);
    }

    /**
     * Test the translator's ability to rewrite the LOCATE() function in a form 
     * suitable for the data source.
     * <p>
     * <code>SELECT locate(STRINGNUM, 'chimp') FROM BQT1.SMALLA</code>
     *  
     * @throws Exception
     */
    @Test public void testLocate2() throws Exception {
        String input = "SELECT locate(STRINGNUM, 'chimp') FROM BQT1.SMALLA"; //$NON-NLS-1$
        String output = "SELECT position(SmallA.StringNum in 'chimp') FROM SmallA";  //$NON-NLS-1$

        MetadataFactory.helpTestVisitor(MetadataFactory.BQT_VDB,
                input, output, 
                TRANSLATOR);
    }

    /**
     * Test the translator's ability to rewrite the LOCATE() function in a form 
     * suitable for the data source.
     * <p>
     * <code>SELECT locate(INTNUM, '234567890', 1) FROM BQT1.SMALLA WHERE INTKEY = 26</code>
     *  
     * @throws Exception
     */
    @Test public void testLocate3() throws Exception {
        String input = "SELECT locate(INTNUM, '234567890', 1) FROM BQT1.SMALLA WHERE INTKEY = 26"; //$NON-NLS-1$
        String output = "SELECT position(cast(SmallA.IntNum AS varchar) in substr('234567890', 1)) FROM SmallA WHERE SmallA.IntKey = 26";  //$NON-NLS-1$

        MetadataFactory.helpTestVisitor(MetadataFactory.BQT_VDB,
                input, output, 
                TRANSLATOR);
    }

    /**
     * Test the translator's ability to rewrite the LOCATE() function in a form 
     * suitable for the data source.
     * <p>
     * <code>SELECT locate('c', 'chimp', 1) FROM BQT1.SMALLA</code>
     *  
     * @throws Exception
     */
    @Test public void testLocate4() throws Exception {
        String input = "SELECT locate('c', 'chimp', 1) FROM BQT1.SMALLA"; //$NON-NLS-1$
        String output = "SELECT 1 FROM SmallA";  //$NON-NLS-1$

        MetadataFactory.helpTestVisitor(MetadataFactory.BQT_VDB,
                input, output, 
                TRANSLATOR);
    }

    /**
     * Test the translator's ability to rewrite the LOCATE() function in a form 
     * suitable for the data source.
     * <p>
     * <code>SELECT locate(STRINGNUM, 'chimp', -5) FROM BQT1.SMALLA</code>
     *  
     * @throws Exception
     */
    @Test public void testLocate5() throws Exception {
        String input = "SELECT locate(STRINGNUM, 'chimp', -5) FROM BQT1.SMALLA"; //$NON-NLS-1$
        String output = "SELECT position(SmallA.StringNum in substr('chimp', 1)) FROM SmallA";  //$NON-NLS-1$

        MetadataFactory.helpTestVisitor(MetadataFactory.BQT_VDB,
                input, output, 
                TRANSLATOR);
    }

    /**
     * Test the translator's ability to rewrite the LOCATE() function in a form 
     * suitable for the data source.
     * <p>
     * <code>SELECT locate(STRINGNUM, 'chimp', INTNUM) FROM BQT1.SMALLA</code>
     *  
     * @throws Exception
     */
    @Test public void testLocate6() throws Exception {
        String input = "SELECT locate(STRINGNUM, 'chimp', INTNUM) FROM BQT1.SMALLA"; //$NON-NLS-1$
        String output = "SELECT position(SmallA.StringNum in substr('chimp', CASE WHEN SmallA.IntNum < 1 THEN 1 ELSE SmallA.IntNum END)) FROM SmallA";  //$NON-NLS-1$

        MetadataFactory.helpTestVisitor(MetadataFactory.BQT_VDB,
                input, output, 
                TRANSLATOR);
    }

    /**
     * Test the translator's ability to rewrite the LOCATE() function in a form 
     * suitable for the data source.
     * <p>
     * <code>SELECT locate(STRINGNUM, 'chimp', LOCATE(STRINGNUM, 'chimp') + 1) FROM BQT1.SMALLA</code>
     *  
     * @throws Exception
     */
    @Test public void testLocate7() throws Exception {
        String input = "SELECT locate(STRINGNUM, 'chimp', LOCATE(STRINGNUM, 'chimp') + 1) FROM BQT1.SMALLA"; //$NON-NLS-1$
        String output = "SELECT position(SmallA.StringNum in substr('chimp', CASE WHEN (position(SmallA.StringNum in 'chimp') + 1) < 1 THEN 1 ELSE (position(SmallA.StringNum in 'chimp') + 1) END)) FROM SmallA";  //$NON-NLS-1$

        MetadataFactory.helpTestVisitor(MetadataFactory.BQT_VDB,
                input, output, 
                TRANSLATOR);
    }
    
}
