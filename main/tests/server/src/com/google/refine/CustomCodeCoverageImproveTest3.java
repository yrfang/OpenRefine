package com.google.refine;

import com.google.refine.sorting.BooleanCriterion;
import com.google.refine.sorting.Criterion;
import com.google.refine.util.ParsingUtilities;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomCodeCoverageImproveTest3 extends RefineTest {

    @Test
    public void testCreateKeyMaker() {
        BooleanCriterion bc = new BooleanCriterion();
        Assert.assertEquals(bc.columnName, null);
    }

    @Test
    public void testGetValueType() {
        BooleanCriterion bc = new BooleanCriterion();
        Assert.assertEquals(bc.getValueType(), "boolean");
    }
}
