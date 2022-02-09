/*

Copyright 2010, Google Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

    * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above
copyright notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
    * Neither the name of Google Inc. nor the names of its
contributors may be used to endorse or promote products derived from
this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.google.refine.browsing.facets;

import java.io.IOException;



import org.slf4j.LoggerFactory;
import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.google.refine.CustomRefineTest;
import org.junit.jupiter.api.TestInstance;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.refine.model.ModelException;
import com.google.refine.model.Project;
//import com.google.refine.RefineTest;
import com.google.refine.browsing.RowFilter;
import com.google.refine.browsing.facets.TextSearchFacet;
import com.google.refine.browsing.facets.TextSearchFacet.TextSearchFacetConfig;
import com.google.refine.util.ParsingUtilities;
import com.google.refine.util.TestUtils;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomFiniteStateMachineTest extends CustomRefineTest {

    // dependencies
    private Project project;
    private TextSearchFacetConfig textfilterconfig;
    private TextSearchFacet textfilter;
    private RowFilter rowfilter;

    private String sensitiveFacetJson = "{\"name\":\"Value\","
            + "\"columnName\":\"Value\","
            + "\"query\":\"A\","
            + "\"mode\":\"text\","
            + "\"caseSensitive\":true,"
            + "\"invert\":false}";

    @Override
    @BeforeAll
    public void init() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @BeforeEach
    public void setUp() throws IOException, ModelException {
        project = createCSVProject("TextSearchFacet",
                "Value\n"
                        + "ABCDEF\n");
    }

    private void configureFilter(String filter) throws JsonParseException, JsonMappingException, IOException {
        // Add the facet to the project and create a row filter
        textfilterconfig = ParsingUtilities.mapper.readValue(filter, TextSearchFacetConfig.class);
        textfilter = textfilterconfig.apply(project);
        rowfilter = textfilter.getRowFilter(project);
    }

    /**
     * Test
     */

    String mode;
    boolean isCaseSensitive;
    boolean isInvert;
    String queryStr;
    String filter;

//    String filter = "{\"type\":\"text\","
//            + "\"name\":\"Value\","
//            + "\"columnName\":\"Value\","
//            + "\"mode\":\"text\","
//            + "\"caseSensitive\":false,"
//            + "\"invert\":false,"
//            + "\"query\":\"ABCDEF\"}";

    String filter_1 = "{\"type\":\"text\","
            + "\"name\":\"Value\","
            + "\"columnName\":\"Value\","
            + "\"mode\":\"";
    String filter_2 = "\",\"caseSensitive\":";
    String filter_3 = ",\"invert\":";
    String filter_4 = ",\"query\":\"";
    String filter_5 = "\"}";

    @Test
    public void testStartToState1() throws Exception {
        mode = "text";
        isCaseSensitive = false;
        isInvert = false;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState2ToState1() throws Exception {
        mode = "text";
        isCaseSensitive = false;
        isInvert = false;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState3ToState1() throws Exception {
        mode = "text";
        isCaseSensitive = false;
        isInvert = false;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState4ToState1() throws Exception {
        mode = "text";
        isCaseSensitive = false;
        isInvert = false;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState1ToState2() throws Exception {
        mode = "text";
        isCaseSensitive = true;
        isInvert = false;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState5ToState2() throws Exception {
        mode = "text";
        isCaseSensitive = true;
        isInvert = false;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState7ToState2() throws Exception {
        mode = "text";
        isCaseSensitive = true;
        isInvert = false;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState1ToState3() throws Exception {
        // Apply regular expression filter "[^0-9^a-z^A-Z^ ]"
        mode = "regex";
        isCaseSensitive = false;
        isInvert = false;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testState5ToState3() throws Exception {
        // Apply regular expression filter "[^0-9^a-z^A-Z^ ]"
        mode = "regex";
        isCaseSensitive = false;
        isInvert = false;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testState6ToState3() throws Exception {
        // Apply regular expression filter "[^0-9^a-z^A-Z^ ]"
        mode = "regex";
        isCaseSensitive = false;
        isInvert = false;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testState1ToState4() throws Exception {
        mode = "text";
        isCaseSensitive = false;
        isInvert = true;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testState6ToState4() throws Exception {
        mode = "text";
        isCaseSensitive = false;
        isInvert = true;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testState7ToState4() throws Exception {
        mode = "text";
        isCaseSensitive = false;
        isInvert = true;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }
    

    @Test
    public void testState2ToState5() throws Exception {
        // Uncheck Invert option
        mode = "regex";
        isCaseSensitive = true;
        isInvert = false;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testState3ToState5() throws Exception {
        // Uncheck Invert option
        mode = "regex";
        isCaseSensitive = true;
        isInvert = false;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testState8ToState5() throws Exception {
        // Uncheck Invert option
        mode = "regex";
        isCaseSensitive = true;
        isInvert = false;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testState3ToState6() throws Exception {
        // Uncheck case sensitive
        mode = "regex";
        isCaseSensitive = false;
        isInvert = true;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState4ToState6() throws Exception {
        // Uncheck case sensitive
        mode = "regex";
        isCaseSensitive = false;
        isInvert = true;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState8ToState6() throws Exception {
        // Uncheck case sensitive
        mode = "regex";
        isCaseSensitive = false;
        isInvert = true;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState2ToState7() throws Exception {
        // Check case-sensitive option
        mode = "text";
        isCaseSensitive = true;
        isInvert = true;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testState4ToState7() throws Exception {
        // Check case-sensitive option
        mode = "text";
        isCaseSensitive = true;
        isInvert = true;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testState8ToState7() throws Exception {
        // Uncheck Regex: apply the original query
        mode = "text";
        isCaseSensitive = true;
        isInvert = true;
        queryStr = "ABCDEF";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testState5ToState8() throws Exception {
        // Check Invert option to state8
        mode = "regex";
        isCaseSensitive = true;
        isInvert = true;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState6ToState8() throws Exception {
        // Check case-sensitive option to state8
        mode = "regex";
        isCaseSensitive = true;
        isInvert = true;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }

    @Test
    public void testState7ToState8() throws Exception {
        // Check RegExp option
        mode = "regex";
        isCaseSensitive = true;
        isInvert = true;
        queryStr = "[^0-9^a-z^A-Z^ ]+";
        filter = filter_1 + mode + filter_2 + isCaseSensitive + filter_3 + isInvert + filter_4 + queryStr + filter_5;

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
    }
}

