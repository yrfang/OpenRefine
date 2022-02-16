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

package com.google.refine;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.refine.model.ModelException;
import com.google.refine.model.Project;
import com.google.refine.RefineTest;
import com.google.refine.browsing.RowFilter;
import com.google.refine.browsing.facets.TextSearchFacet;
import com.google.refine.browsing.facets.TextSearchFacet.TextSearchFacetConfig;
import com.google.refine.util.ParsingUtilities;
import com.google.refine.util.TestUtils;

public class CustomCodeCoverageImproveTest extends RefineTest {

    // dependencies
    private Project project;
    private TextSearchFacetConfig textfilterconfig;
    private TextSearchFacet textfilter;
    private RowFilter rowfilter;
    private String sensitiveConfigJson = "{\"type\":\"text\","
            + "\"name\":\"Value\","
            + "\"columnName\":\"Value\","
            + "\"mode\":\"text\","
            + "\"caseSensitive\":true,"
            + "\"invert\":false,"
            + "\"query\":\"A\"}";

    private String sensitiveFacetJson = "{\"name\":\"Value\","
            + "\"columnName\":\"Value\","
            + "\"query\":\"A\","
            + "\"mode\":\"text\","
            + "\"caseSensitive\":true,"
            + "\"invert\":false}";

    @Override
    @BeforeTest
    public void init() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @BeforeMethod
    public void setUp() throws IOException, ModelException {
        project = createCSVProject("TextSearchFacet",
                "Value\n"
                        + "a\n");
    }

    private void configureFilter(String filter) throws JsonParseException, JsonMappingException, IOException {
        // Add the facet to the project and create a row filter
        textfilterconfig = ParsingUtilities.mapper.readValue(filter, TextSearchFacetConfig.class);
        textfilter = textfilterconfig.apply(project);
        rowfilter = textfilter.getRowFilter(project);
    }

    /**
     * Test for TextSearchFacet.java file
     */

    @Test
    public void testTextFilterWithNullQuery() throws Exception {
        String filter = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Value\","
                + "\"mode\":\"text\","
                + "\"caseSensitive\":false,"
                + "\"invert\":false,"
                + "\"query\":\"null\"}";

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testRegExFilterWithPatternSyntaxException() throws Exception {
        String filter = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Value\","
                + "\"mode\":\"regex\","
                + "\"caseSensitive\":false,"
                + "\"invert\":false,"
                + "\"query\":\"*\"}";

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }

    @Test
    public void testRegExFilterWithNullPattern() throws Exception {
        String filter = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Value\","
                + "\"mode\":\"regex\","
                + "\"caseSensitive\":false,"
                + "\"invert\":false,"
                + "\"query\":\"null\"}";

        configureFilter(filter);
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
    }


}
