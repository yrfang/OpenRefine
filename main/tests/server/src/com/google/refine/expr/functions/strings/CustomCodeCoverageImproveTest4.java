package com.google.refine.expr.functions.strings;

import com.google.refine.sorting.BooleanCriterion;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.refine.util.TestUtils;

import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomCodeCoverageImproveTest4 {


    @Test
    public void testCall() {
        Match m = new Match();
        Properties prop = new Properties();
        prop.put("key1", "val1");

        Object[] args = new Object[] { "aabcd", "(aa)(bcd)"};
        Object result = m.call(prop, args);

        Assert.assertEquals(((String[])result)[0], "aa");
        Assert.assertEquals(((String[])result)[1], "bcd");
    }
}