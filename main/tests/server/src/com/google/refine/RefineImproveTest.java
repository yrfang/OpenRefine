package com.google.refine;

import static com.google.refine.RefineTest.invoke;
import static org.testng.Assert.assertEquals;

import com.google.refine.model.Cell;
import com.google.refine.model.Row;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

public class RefineImproveTest {
    Properties options;

    // main/src/com/google/refine/model/Row.java
    @Test
    public void getNullField() {
        Row row = new Row(5);
        row.starred = false;
        row.flagged = false;
        Assert.assertEquals(row.getField("non-starred-non-flagged", options), null);
    }

    @Test
    public void getCell() {
        Row row = new Row(1);
        row.setCell(0, new Cell(1, null));
        Assert.assertEquals(row.getCell(0).getValue(), 1);
    }

    @Test
    public void getNullCell() {
        Row row = new Row(1);
        row.setCell(0, new Cell(1, null));
        // the expected result should be null, because the desired cell is larger than cell max size
        Assert.assertEquals(row.getCell(2), null);
    }

    // main/src/com/google/refine/expr/functions/ToString.java
    @Test
    public void testStringObject() {
        assertEquals(invoke("toString", "1"), "1");
    }

    // main/src/com/google/refine/expr/functions/Length.java
    @Test
    public void testLength() {
        assertEquals(invoke("length", "1"), 1);
        assertEquals(invoke("length", Arrays.asList("v1", "v2")), 2);
    }

    // main/src/com/google/refine/expr/functions/date/Now.java
    @Test
    public void testNowNull() {
        Assert.assertEquals(invoke("now", new Object[] { 1 }), null);
    }

    // main/src/com/google/refine/expr/functions/date/Inc.java
    @Test
    public void testIncAddDays() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss.SSSSSSSSSX");
        OffsetDateTime source = OffsetDateTime.parse("20180510-23:55:44.000789000Z",
                formatter);
        Assert.assertTrue(invoke("inc", source, 2, "days") instanceof OffsetDateTime);
        Assert.assertEquals(invoke("inc", source, 2, "days"), source.plus(2, ChronoUnit.DAYS));
    }

    // main/src/com/google/refine/expr/functions/date/DatePart.java
    @Test
    public void testDatePartGetPart() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        // year, years
        Assert.assertEquals(invoke("datePart", c, "year"), c.get(Calendar.YEAR));
        Assert.assertEquals(invoke("datePart", c, "years"), c.get(Calendar.YEAR));

        // month, months (people expect January to be 1 not 0)
        Assert.assertEquals(invoke("datePart", c, "month"), c.get(Calendar.MONTH) + 1);
        Assert.assertEquals(invoke("datePart", c, "months"), c.get(Calendar.MONTH) + 1);

        // days
        Assert.assertEquals(invoke("datePart", c, "days"), c.get(Calendar.DAY_OF_MONTH));

        // hours
        Assert.assertEquals(invoke("datePart", c, "hours"), c.get(Calendar.HOUR_OF_DAY));

        // minutes
        Assert.assertEquals(invoke("datePart", c, "minutes"), c.get(Calendar.MINUTE));

        // seconds
        Assert.assertEquals(invoke("datePart", c, "seconds"), c.get(Calendar.SECOND));

        // milliseconds
        Assert.assertEquals(invoke("datePart", c, "milliseconds"), c.get(Calendar.MILLISECOND));

        // weekday
        String[] s_daysOfWeek = new String[] {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Assert.assertEquals(invoke("datePart", c, "weekday"), s_daysOfWeek[c.get(Calendar.DAY_OF_WEEK)]);
    }
}
