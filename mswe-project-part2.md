# 261 Group Project - OpenRefine Part2

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

The feature we will study for finite state machine is "text filter" feature. After doing text facet, users can apply different format of quering condition to search data by "text filter". This feature operates `string` data type.

![](https://i.imgur.com/lDh71hD.png)

For example, let's say we have a text facet for country. After doing facet, we also select "Text filter" on the column dropdown menu. The facet list would be at the left-hand side of the website and there is also a "text filter" section.

![](https://i.imgur.com/VjHckD9.png)

This **text filter feature** allows users to have some options after they query the searched string. Here are the conditions:

- query a specific string by typing in the input field
- query a case sensitive string by checking case sensitive checkbox
- query a regular expression string by checking regular expression checkbox
- exclude a searched string by clicking invert

The associated test cases for "text filter" feature are under `main/tests/server/src/com/google/refine/browsing/facets/TextSearchFacetTests.java` of the OpenRefine repo.

## Create, draw, and describe that functional model

To build a functional model, we will use Finite State Machine (FSM) to represent the state transition.

From the lecture, we have learned that the example of LED. Users can turn on and turn off LED, so there are two different states for Finite State Machine. In our text filter feature for OpenRefine, we would use this similar idea to draw our Finite State Machine. We use [diagrams.net](https://www.diagrams.net/) to draw the state transition figure for our FSM.

To simplify our Finite State Machine, we assume that the query text input from the user remains the same in all states.

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


## Write test cases that "cover" the functional model in JUnit

We will follow what we have in the Finite Machine Model to write test cases. You can find our new test cases under `main/tests/server/src/com/google/refine/browsing/facets/CustomFiniteStateMachineTest.java`. Or you can check out the [file on Github](https://github.com/yrfang/OpenRefine/blob/part2/main/tests/server/src/com/google/refine/browsing/facets/CustomFiniteStateMachineTest.java).

Here is our testing inputs.

| Value   |
| --------|
| ABCDEF  |


### Test case explanation

We write 24 test cases to verify every state transition between 8 states. Here is an example testing the state transition *from State 2 to State 1*. Because State1 is Case sensitive Off, RegExp Off, Invert Off, so we set corresponding value of variables respectively: `isCaseSensitive = false`, `queryStr = "ABCDEF"`, and `isInvert = false`. The result is expected to be true because our input text "ABCDEF" shouldn't be filtered out.

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

The figure below is our all test cases for Finite Machine Model, and they are all passed on IntelliJ. To run our test cases, open the `main/tests/server/src/com/google/refine/browsing/facets/CustomFiniteStateMachineTest.java` file and righ-click "Run CustomFiniteStateMachineTest", you would see the passed results.

![](https://i.imgur.com/AD567SO.png)