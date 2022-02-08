package com.google.refine.browsing;

//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.refine.CustomRefineTest;
import com.google.refine.RefineTest;
import com.google.refine.browsing.facets.Facet;
import com.google.refine.browsing.facets.ListFacet;
import com.google.refine.browsing.facets.TextSearchFacet;
import com.google.refine.model.ModelException;
import com.google.refine.model.Project;
import com.google.refine.util.ParsingUtilities;
import com.google.refine.util.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomFiniteStateMachineTest extends CustomRefineTest {
    // dependencies
    private Project project;
    private TextSearchFacet.TextSearchFacetConfig textfilterconfig;
    private TextSearchFacet textfilter;
    private RowFilter rowfilter;

    private String sensitiveFacetJson = "{\"name\":\"Value\","
            + "\"columnName\":\"Country\","
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
                "Country\n"
                        + "AUSTRALIA\n"
                        + "ECUADOR\n"
                        + "HT\n"
                        + "INDIA\n"
                        + "UNITED STATES\n"
                        + " \n");
    }

    private void configureFilter(String filter) throws JsonParseException, JsonMappingException, IOException {
        // Add the facet to the project and create a row filter
        textfilterconfig = ParsingUtilities.mapper.readValue(filter, TextSearchFacet.TextSearchFacetConfig.class);
        textfilter = textfilterconfig.apply(project);
        rowfilter = textfilter.getRowFilter(project);
    }

    @Test
    public void testOriginalFacetToSearchedStringFacet() throws Exception {
        // Apply text filter "a"

        // Column: "Country"
        // Filter Query: "a"
        // Mode: "text"
        // Case sensitive: False
        // Invert: False
        String filter = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Country\","
                + "\"mode\":\"text\","
                + "\"caseSensitive\":false,"
                + "\"invert\":false,"
                + "\"query\":\"a\"}";

        configureFilter(filter);

        // Check each row in the project against the filter
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 1, project.rows.get(1)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 2, project.rows.get(2)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 3, project.rows.get(3)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 4, project.rows.get(4)), true);
    }

    @Test
    public void testSearchedFacetToCaseSensitiveFacet() throws Exception {
        // Apply case-sensitive filter "A"

        String sensitiveConfigJson = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Country\","
                + "\"mode\":\"text\","
                + "\"caseSensitive\":true,"
                + "\"invert\":false,"
                + "\"query\":\"A\"}";

        configureFilter(sensitiveConfigJson);

        // Check each row in the project against the filter
        // Expect to retrieve one row containing "Abc"
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 1, project.rows.get(1)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 2, project.rows.get(2)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 3, project.rows.get(3)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 4, project.rows.get(4)), true);
    }

    @Test
    public void testSearchedFacetToInvertedFacet() throws Exception {
        // Apply inverted text filter "a" (that means facet result contains "a" would be excluded)

        // Column: "Value"
        // Filter Query: "a"
        // Mode: "text"
        // Case sensitive: False
        // Invert: True
        String filter = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Country\","
                + "\"mode\":\"text\","
                + "\"caseSensitive\":false,"
                + "\"invert\":true,"
                + "\"query\":\"a\"}";

        configureFilter(filter);

        // Check each row in the project against the filter
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 1, project.rows.get(1)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 2, project.rows.get(2)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 3, project.rows.get(3)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 4, project.rows.get(4)), false);
    }

    @Test
    public void testSearchedFacetToRegExpFacet() throws Exception {
        // Apply regular expression filter "[abc]"

        // Column: "Value"
        // Filter Query: "[bc]"
        // Mode: "regex"
        // Case sensitive: False
        // Invert: False
        String filter = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Country\","
                + "\"mode\":\"regex\","
                + "\"caseSensitive\":false,"
                + "\"invert\":false,"
                + "\"query\":\"[abc]\"}";

        configureFilter(filter);

        // Check each row in the project against the filter
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 1, project.rows.get(1)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 2, project.rows.get(2)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 3, project.rows.get(3)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 4, project.rows.get(4)), true);
    }

    @Test
    public void testCaseSensitiveFacetWithRegExpFilter() throws Exception {
        // Apply regular expression filter "[C]"

        String filter = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Country\","
                + "\"mode\":\"regex\","
                + "\"caseSensitive\":true,"
                + "\"invert\":false,"
                + "\"query\":\"[C]\"}";

        configureFilter(filter);

        // Check each row in the project against the filter
        // Expect to retrieve one row containing "Abc"
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 1, project.rows.get(1)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 2, project.rows.get(2)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 3, project.rows.get(3)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 4, project.rows.get(4)), false);
    }

    @Test
    public void testRegExpFacetWithInvertedFilter() throws Exception {
        // Apply regular expression filter "[C]"

        String filter = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Country\","
                + "\"mode\":\"regex\","
                + "\"caseSensitive\":false,"
                + "\"invert\":true,"
                + "\"query\":\"[C]\"}";

        configureFilter(filter);

        // Check each row in the project against the filter
        // Expect to retrieve one row containing "Abc"
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 1, project.rows.get(1)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 2, project.rows.get(2)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 3, project.rows.get(3)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 4, project.rows.get(4)), true);
    }

    @Test
    public void testCaseSensitiveFacetWithInvertedFilter() throws Exception {
// Apply case-sensitive filter "A"

        String sensitiveConfigJson = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Country\","
                + "\"mode\":\"text\","
                + "\"caseSensitive\":true,"
                + "\"invert\":true,"
                + "\"query\":\"A\"}";

        configureFilter(sensitiveConfigJson);

        // Check each row in the project against the filter
        // Expect to retrieve one row containing "Abc"
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 1, project.rows.get(1)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 2, project.rows.get(2)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 3, project.rows.get(3)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 4, project.rows.get(4)), false);
    }
}
