
package com.google.refine.browsing.facets;

import java.io.IOException;
import java.util.regex.Pattern;


import com.google.refine.browsing.filters.ExpressionStringComparisonRowFilter;
import com.google.refine.expr.Evaluable;
import com.google.refine.grel.ast.VariableExpr;
import com.google.refine.util.PatternSyntaxExceptionParser;
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
public class CustomTestableDesignTest extends CustomRefineTest {

    // dependencies
    private Project project;
    private TextSearchFacet.TextSearchFacetConfig textfilterconfig;
    private TextSearchFacet textfilter;
    private RowFilter rowfilter;
    Evaluable eval = new VariableExpr("value");
    Pattern  pattern;
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
    @BeforeAll
    public void init() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @BeforeEach
    public void setUp() throws IOException, ModelException {
        project = createCSVProject("TextSearchFacet",
                "Value\n"
                        + "^&*\n"
                        + "ABCDEF\n"
                        + "abcdef\n"
                        + "123456\n"
                        + " \n");
    }

    private void configureFilter(String filter) throws JsonParseException, JsonMappingException, IOException {
        // Add the facet to the project and create a row filter
        textfilterconfig = ParsingUtilities.mapper.readValue(filter, TextSearchFacet.TextSearchFacetConfig.class);
        textfilter = textfilterconfig.apply(project);
    }

    @Test
    public void testTextFilter() throws Exception {
        // Apply text filter "a"
        String filter = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Value\","
                + "\"mode\":\"text\","
                + "\"caseSensitive\":false,"
                + "\"invert\":false,"
                + "\"query\":\"a\"}";

        configureFilter(filter);

        ExpressionStringComparisonRowFilter escrf = new ExpressionStringComparisonRowFilter(eval, false, "Value", 0) {
            @Override
            protected boolean checkValue(String s) {
                return s.toLowerCase().contains("a");
            };
        };

        rowfilter = textfilter.getRowFilter(project, escrf);

        // Check each row in the project against the filter
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 1, project.rows.get(1)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 2, project.rows.get(2)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 3, project.rows.get(3)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 4, project.rows.get(4)), false);
    }


    @Test
    public void testRegExFilter() throws Exception {
        // Apply regular expression filter "[^0-9^a-z^A-Z^ ]"
        String filter = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Value\","
                + "\"mode\":\"regex\","
                + "\"caseSensitive\":false,"
                + "\"invert\":false,"
                + "\"query\":\"[^0-9^a-z^A-Z^ ]+\"}";
        // error when using [\W\D\S]+ (error message: Unrecognized character escape 'W')

        configureFilter(filter);

        try {
            pattern = Pattern.compile(
                    "[^0-9^a-z^A-Z^ ]+",
                    Pattern.CASE_INSENSITIVE);
        } catch (java.util.regex.PatternSyntaxException e) {
            PatternSyntaxExceptionParser err = new PatternSyntaxExceptionParser(e);
            throw new IllegalArgumentException(err.getUserMessage());
        }

        ExpressionStringComparisonRowFilter escrf = new ExpressionStringComparisonRowFilter(eval, false, "Value", 0) {
            @Override
            protected boolean checkValue(String s) {
                 return pattern.matcher(s).find();
            };
        };

        rowfilter = textfilter.getRowFilter(project, escrf);

        // Check each row in the project against the filter
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 1, project.rows.get(1)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 2, project.rows.get(2)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 3, project.rows.get(3)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 4, project.rows.get(4)), false);
    }
}


