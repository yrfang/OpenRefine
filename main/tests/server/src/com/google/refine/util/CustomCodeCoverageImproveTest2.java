package com.google.refine.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.zip.GZIPOutputStream;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.refine.RefineTest;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomCodeCoverageImproveTest2 extends RefineTest {

    @Override
    @BeforeTest
    public void init() {
        logger = LoggerFactory.getLogger(this.getClass());
    }


//    @Test
//    public void testParseUrlParameters() {
//
//
////        MockHttpServletRequest request = new MockHttpServletRequest();
////        request.addParameter("parameterName", "someValue");
////        assertTrue("Batch is Completed :", returnPointsRatingDisputeFrom.checkBatchExecutionSchedule(request));
//
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        //setup the behaviour here (or do it in setup method or something)
////        when(mockRequest.getParameter("parameterName")).thenReturn("someValue");
////        assertTrue("Batch is Completed :", returnPointsRatingDisputeFrom.checkBatchExecutionSchedule(mockRequest));
//
//
//        Properties options  = ParsingUtilities.parseUrlParameters(mockRequest);
//
//        System.out.println(options);
////        Assert.assertEquals(zdtString, historyEntryDate);
//    }

    @Test
    public void testParseParameters2Argument() {

//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        Properties options = new Properties();
        Properties result = new Properties();

        options  = ParsingUtilities.parseParameters(options, "abc=123&def=456");

        result.put("abc", "123");
        result.put("def", "456");

        Assert.assertEquals(options, result);
    }

    @Test
    public void testParseParameters1Argument() {

        Properties options = new Properties();
        options  = ParsingUtilities.parseParameters(null);

        Assert.assertEquals(options, null);
    }

    @Test
    public void testEncode() {
        String encodedResult  = ParsingUtilities.encode("abc");
        Assert.assertEquals(encodedResult, "abc");
    }

}

