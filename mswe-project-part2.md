# 261 Group Project - OpenRefine Part2

Here are some useful links.

- [OpenRefine Project GitHub](https://github.com/OpenRefine/OpenRefine)
- [Our OpenRefine Project forked GitHub](https://github.com/yrfang/OpenRefine)
- [OpenRefine User Manual Document](https://docs.openrefine.org/)
- [OpenRefine Developer Document](https://docs.openrefine.org/technical-reference/build-test-run)
- [Open Refine Official Website](https://openrefine.org/)

Our Group members:
- Ying-Ru Fang (Github account: yrfang)
- Yu-Che Su (Github account: yuchesu)
- Ching-Yang Lin (Github account: tomelinn)

# Part II: Functional Testing and Finite State Machines

Feel free to check out our [Part2 Readme](https://github.com/yrfang/OpenRefine/blob/part2/mswe-project-part2.md) on Github.

## Why finite models are useful for testing?

Finite models are logic which deals with the relation between functional specifications and test case specifications. These models can be represented as diagrams (sequence, interation, finite-state-machine... etc), formal logic or statistical descriptions.

Finite models are useful for testing based on two properties.

- They are **compact**.
  - It's simple and easy to manipulate in a reasonable comact form.
  - It's understandable and checkable.
- They are **predictive**.
  - It's well enough to distinguish between good and bad outcomes of analysis.

Because of the two properties, finite models can be constructed prior to or independent of source code, so can serve as a specification of allowed behavior. They can help capture specs and tell us what test cases can and should be written. Also, we can make sure the written tests case have "enough" coverage of specs. Thus, finite models could be helpful in testing.

## A Feature for a finite state machine

The feature we are going to study for finite state machine is "text filter" feature. After doing text facet, users can apply different format of query option to search data by "text filter". This feature operates `string` data type.

For example, let's say we have a text facet for country data set. Here is an image indicates how to select text facet.

![](https://i.imgur.com/GSBJ6bD.png)


After doing text facet, we can see facet result (the image below) for country conlumn.The facet list would be at the left-hand side of the website.


![](https://i.imgur.com/7NOfthP.png)


The we can further select "Text filter" on the column dropdown menu. The image below is where text filter on the website.

![](https://i.imgur.com/APdhlge.png)


After choosing text filter for country column, users can see the image below on the left-hand side of the website.


![](https://i.imgur.com/H7jbZTn.png)


This **text filter feature** allows users to have some options to explore a messy data set. Users can type a query string in a input field and apply three options for the query string.


#### Three options are:
- Check/Uncheck case sensitive option
  - If user check the case sensitive option, results will be case sensitive
- Check/Uncheck regular expression (RegExp) option
  - If user check the regular expression option, results will match the regular expression
- Check/Uncheck invert option
  - If user check the invert option, results will be inverted
  - When user check invert option, the query output would be excluded.


The test cases of original OpenRefine community project for "text filter" feature are under `main/tests/server/src/com/google/refine/browsing/facets/TextSearchFacetTests.java`. We took a reference of this file to see how it works and apply in our own new test cases.

## Create, draw, and describe that functional model

To build a functional model, we will use Finite State Machine (FSM) to represent the state transition.

From the lecture, we have learned that the example of LED. Users can turn on and turn off LED, so there are two different states for Finite State Machine for LED. In our text filter feature for OpenRefine, we would use this similar idea to draw our Finite State Machine. The tool we use to draw the state transition figure for our FSM is [diagrams.net](https://www.diagrams.net/).

To simplify our Finite State Machine, we assume that the query text input from the user remains the same in all states. you can see each state has `user input query text t1` in the state circle.

![](https://i.imgur.com/rJzUSKT.png)

For larger image, please click [here](https://i.imgur.com/rJzUSKT.png).


Because users can filter different results by six options **(We assume that the query text remains the same in all states)**.
- Check/Uncheck case sensitive option
  - If user check the case sensitive option, results will be case sensitive
- Check/Uncheck regular expression (RegExp) option
  - If user check the regular expression option, results will match the regular expression
- Check/Uncheck invert option
  - If user check the invert option, results will be inverted

According to [Wikipedia](https://en.wikipedia.org/wiki/Finite-state_machine), a state is a description of the status of a system waiting to execute a transition. Therefore, we will have **8 combinations of states** and they are circles in the figure.

**The definition of these 8 states:**

* State1: Case sensitive Off, RegExp Off, Invert Off
* State2: Case sensitive On, RegExp Off, Invert Off
* State3: Case sensitive Off, RegExp On, Invert Off
* State4: Case sensitive Off, RegExp Off, Invert On
* State5: Case sensitive On, RegExp On, Invert Off
* State6: Case sensitive Off, RegExp On, Invert On
* State7: Case sensitive On, RegExp Off, Invert On
* State8: Case sensitive On, RegExp On, Invert On


**Each state transition:**
* State1 to State2: Check case sensitive option
* State1 to State3: Check RegExp option
* State1 to State4: Check Invert option
* State2 to State1: Uncheck case sensitive option
* State2 to State5: Check RegExp option
* State2 to State7: Check Invert option
* State3 to State1: Uncheck RegExp option
* State3 to State5: Check case sensitive option
* State3 to State6: Check Invert option
* State4 to State1: Check Invert option
* State4 to State6: Check RegExp option
* State4 to State7: Check case sensitive option
* State5 to State2: Uncheck RegExp option
* State5 to State3: Uncheck case sensitive option
* State5 to State8: Check Invert option
* State6 to State3: Uncheck Invert option
* State6 to State4: Uncheck RegExp option
* State6 to State8: Check case sensitive option
* State7 to State2: Uncheck Invert option
* State7 to State4: Uncheck case sensitive option
* State7 to State8: Check RegExp option
* State8 to State5: Uncheck Invert option
* State8 to State6: Uncheck case sensitive option
* State8 to State7: Uncheck RegExp option


## Test cases that "cover" the functional model in JUnit

We will follow what we have in the Finite Machine Model to write test cases. You can find our new test cases under `main/tests/server/src/com/google/refine/browsing/facets/CustomFiniteStateMachineTest.java`. Or you can check out the [file on Github](https://github.com/yrfang/OpenRefine/blob/part2/main/tests/server/src/com/google/refine/browsing/facets/CustomFiniteStateMachineTest.java).

Here is our testing inputs.

| Value   |
| --------|
| ABCDEF  |


### Test case explanation

We write 24 test cases to verify every state transition between 8 states. We will choose four test cases to explain our state transition in Finite Machine model, but you can find all test cases [here](https://github.com/yrfang/OpenRefine/blob/part2/main/tests/server/src/com/google/refine/browsing/facets/CustomFiniteStateMachineTest.java). Please note that we assume that the query text remains the same from the start in all states.

#### Start to state1

- input: "ABCDEF" as our query string
- Start
- State1: Case sensitive Off, RegExp Off, Invert Off

Our start is assumed to take a fixed input value of `ABCDEF`, so after the transition from start to state1, the state is Case sensitive Off, RegExp Off, Invert Off. Thus, the output result is expected to be string `ABCDEF` as well.

In our test case method `testStartToState1`, OpenRefine "text filter" provides a convenient filter config to control the on/off for Case sensitive, Regex and Invert to filter the query input.

- `mode = "text"`: it's Regex off (otherwise, it would be "regex" for Regex on)
- `isCaseSensitive = false`: Case sensitive off
- `isInvert = false`: Invert off

After applying the filter config, the expected result would be "ABCDEF", which means the inputs don't need to be filtered. Thus, the assertion syntax would be true because we can get the row 0 with "ABCDEF".

```java=
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
```

#### State2 to State1

- input: "ABCDEF" (qeury filter string remains same)
- State2: Case sensitive On, RegExp Off, Invert Off
- State1: Case sensitive Off, RegExp Off, Invert Off

Because State1 is Case sensitive Off, RegExp Off, Invert Off, so we change corresponding value of variables in the filter config respectively.

- `isCaseSensitive = false`: Case sensitive off, becase the state change from on to off.
- `mode="text"`: Regex off
- `isInvert = false`: invert off

The result is expected to be true because our input text "ABCDEF" shouldn't be filtered out.

```java=
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
```

#### State1 to State3

- input: "ABCDEF" (qeury filter string remains same)
- State1: Case sensitive Off, RegExp Off, Invert Off
- State3: Case sensitive Off, RegExp On, Invert Off

Because State3 is Case sensitive Off, RegExp On, Invert Off, so we change corresponding value of variables in the filter config respectively.

- `isCaseSensitive = false`: Case sensitive off
- `mode="regex"`: Regex on because the state change from off to on
- `isInvert = false`: invert off

OpenRefine allows us to use regex in queryStr filter config, so in our test case, regex is `[^0-9^a-z^A-Z^ ]`. That means we don't want numbers and alphabets, so the expected result of row 0 should not get "ABCDE" for our filter input. The assertion syntax should be false.

```java=
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
```

#### State1 to State4

- input: "ABCDEF" (qeury filter string remains same)
- State1: Case sensitive Off, RegExp Off, Invert Off
- State4: Case sensitive Off, RegExp Off, Invert On

Because State4 is Case sensitive Off, RegExp Off, Invert On, so we can change associated value of variables in the filter config respectively.

- `isCaseSensitive = false`: Case sensitive off
- `mode="text"`: Regex off
- `isInvert = true`: invert on because the invert changes from off to on

Because when user check invert option, the results would be opposite. In other words, the filtered results would be excluded. Thus, in our text case, the expected output result of row 0 from assertion should be falase becasue we don't have "ABCDE" in row 0.

```java=
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
```

#### Result of all test cases

The figure below is our all test cases for Finite Machine Model, and they are all passed in IntelliJ. To run our test cases, open the `main/tests/server/src/com/google/refine/browsing/facets/CustomFiniteStateMachineTest.java` file and righ-click "Run CustomFiniteStateMachineTest" in IntelliJ, you would see the passed results.

![](https://i.imgur.com/AD567SO.png)