# 261 Group Project - OpenRefine

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

# Part I: Introduction, Set-up and Partition

## Introduction to OpenRefine project

### What is OpenRefine?

OpenRefine is an application we can download and install on our own computer. We can do data cleaning and data manipulation through selections it offers and writing codes in expression section. For example, we can import contents from Wikipedia page, do data transformations and then export them back to Wikipedia. Also, it allows us to do data analysis and visualization like we usually do in Excel.

OpenRefine has three key features.

- **Explore Data**:
  OpenRefine can help you explore large data sets with ease.
- **Clean and Transform Data**:
  OpenRefine gives you powerful ways to clean, correct, codify, and extend your data. Without ever needing to type inside a single cell, you can automatically fix typos, convert things to the right format, and add structured categories from trusted sources.
- **Reconcile and Match Data**:
  OpenRefine can be used to link and extend your dataset with various webservices. Some services also allow OpenRefine to upload your cleaned data to a central database, such as Wikidata.

Feel free to take a look at [Official Website and turtorials]([https:/](https://openrefine.org/)/)

### Divide into detailed OpenRefine analysis

OpenRefine is mainly built in Java (about 70%). Because it is a complete web application, it contains 17.9% codes written in JavaScript to support browser User Interface. Technically, OpenRefine is a Java-based application.

To analyze the complexity of OpenRefine project, we count how many Java programs under OpenRefine repository. We can use the command below on a MacOS command-line to count and the result shows `216674` lines in all Java files.

```bash=
# use this command under OpenRefine directory
find . -name '*.java' | xargs wc -l
```

Besides, we count total Java files under OpenRefine. The result is `1630`. We use the command below on a MacOS command-line.

```bash=
# use this command under OpenRefine directory
find . -name '*.java' | wc -l
```

## OpenRefine environment set-up

### How to build?

#### Forked and get OpenRefine source code
Because we may contribute our changes to this open-source project, we first forked from OpenRefine github repo.



Clone the repository (our [forked](https://github.com/yrfang/OpenRefine) one) at the command line.

```bash=
git clone https://github.com/yrfang/OpenRefine.git
```

#### Set up Java JDK

1. Check your current instlled JDK

- We can use the command below to check all installed JAVA JDK versions on a MacOS.

```bash=
/usr/libexec/java_home -V
```

2. Set the environment variable for the current Java version.

- If you are using bash, open your bash_profile at `~/.bashrc`. Add the following below.
- If you are using zsh, open your zsh_profile at `~/.zshrc`. Also add the command below.
- Either way, you have to open your terminal again after writing the JAVA_HOME varialbe.
- You can also take a look at the reference [here](https://mkyong.com/java/how-to-set-java_home-environment-variable-on-mac-os-x/) if your are using a MacOS.

Take Jav 16.x for an example.:

```bash=
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-16.jdk/Contents/Home
```

You can also point to your default Java JDK on your computer.

```bash=
export JAVA_HOME="$(/usr/libexec/java_home)"
```

3. Either way, make sure you already have environment variable in the place.

```bash=
echo $JAVA_HOME
```

When you echo your JAVA_HOME variable, it should point to your JDK you just set up.

#### Set up Maven

If you haved installed Maven, just ensure you set your `MAVEN_HOME`. If you don't have Maven installation, you can use brew to install on the MacOS.

```bash=
brew install maven
```

Once brew completes the Maven installation, you can set the `MAVEn_HOME` in your zsh or bash profile like what we did for JAVA_HOME.

Take maven 3.8.4 for an example:

```bash=
export MAVEN_HOME=/usr/local/apache-maven/apache-maven-3.8.4
```

#### Build the project

Under your OpenRefine directory, you can build the OpenRefine application at your command line.

```bash=
./refine build
```

### How to run?

#### Run from the command-line

```bash=
./refine
```

You are able to open your local OpenRefine service at http://127.0.0.1:3333/

![](https://i.imgur.com/BkhYemd.png)


## Testing

Document the existing test cases (JUnit or otherwise). This should be a study of the existing testing practices and frameworks that are used already in the system. (This section might evolve as we learn more throughout the quarter.) How do you run them?


### Run the existing test cases from IntelliJ

We are using the 2021 version of IntelliJ. You can also take a reference at [here](https://docs.openrefine.org/technical-reference/build-test-run#building-testing-and-running-openrefine-from-intellij-idea).

#### Import your local OpenRefine project from IntelliJ

> `File` -> `Open` -> `Your OpenRefine directory`

IntelliJ is able to automatically tell that it is a maven project. You can see your source code contains a `pom.xml` file. If IntelliJ doesn't do so for you, click the right side of the IDE of maven icon, and click `reload all maven project`.

![](https://i.imgur.com/eSdKFfV.png)

#### Set up project modules

To devleop OpenRefine by using IntelliJ, we have to set up project moduels level. Under your OpenRefine directory, find the `main` folder, and right-click the `main` folder. Select "Open Module Settings", and set-up the source, test, and testing resources like the screenshot below.

![](https://i.imgur.com/fpzV1xN.png)

#### Check if you set up the modules correctly

Go to the main folder, select any test class under `main/tests/server/src/com.google.refine`. You should not see any import errors. If you see any import modules errors, here is a solution to help you set up again.

There may exist cache errors between IntelliJ and the projects. We have to clean system cache.

> Select the menu `File` -> `Invalidate Caches` -> `Invalidate Caches/Restart`

After cleaning and restarting, open OpenRefine project from IntelliJ again.

Now everything should be all set.

#### Run Unit tests in IntelliJ

Select any classes of test cases under `main/src/tests` folders, you are able to click the class and run "Run XXXTest" or "Run XXXTest with code converage".

![](https://i.imgur.com/EjUuY9w.png)

#### Sample of running existing test cases

For example, we run a test file `HistoryEntryTests` under `main/tests/server/src/com/google/refine/history/HistoryEntryTests.java`, and it has three test cases.

When we click "Run HistoryEntryTests", we can see the Pass/Fail result in IntelliJ.

![](https://i.imgur.com/m7BetqH.png)

We can also click "Run HistoryEntryTests with Coverage", and we can obtain the test cases coverage in HistoryEntry codes.

![](https://i.imgur.com/UX3JwDS.png)


### Run test cases on command line

OpenRefine provides us with command line options to run test cases. You can also find information [here](https://docs.openrefine.org/technical-reference/build-test-run#testing).

To run all tests, use:
```bash=
./refine test
```

**this option is not available when using `refine.bat`.**

To run the server side portion of tests (OpenRefine server), we can use the command:

```bash=
./refine server_test
```

The results will be printed at your command line.

![](https://i.imgur.com/Ak2Oj58.png)


### Catogories of existing test cases

The current existing test cases are mainly under `main/tests/server` for OpenRefine project. To have a quick overview its testing roadmap according to the OpenRefine features, we can simplify classify test cases according to three key features.



- Browsing feature:
    - main/tests/server/src/com/google/refine/browsing
    - main/tests/server/src/com/google/refine/sorting
- cluster feature:
    - main/tests/server/src/com/google/refine/clustering
- Reconcile and Match Data:
    - extensions
        * extensions/database/tests
        * extensions/gdata/tests
        * extensions/jython/tests
        * extensions/phonetic/tests
        * extensions/wikidata/tests


### Analysis techniques of testing for OpenRefine

- On the server side, it's powered by `TestNG` and the unit tests are written in Java;
    - [TestNG testing framework](https://testng.org/doc/documentation-main.html)
    - TestNG is a testing framework designed to simplify a broad range of testing needs, from unit testing (testing a class in isolation of the others) to integration testing (testing entire systems made of several classes, several packages and even several external frameworks, such as application servers).
- On the client side, OpenRefine use Cypress and the tests are written in Javascript
    - [Cypress testing framework](https://www.cypress.io/)
    - [Document](https://docs.cypress.io/guides/overview/why-cypress)


## Partition

### Why need functional testing and partition testing?

**Functional testing**, AKA integration test, testing a slice of functionality in a system to ascertain whether its output is consistent with the end user's expectations. It is a kind of black-box testing that is performed to confirm that the functionality of an application or system is behaving as expected. Also, It ensures security and safety and improves the quality of the product.

**Partition testing** is a software testing technique that allows the tester to divide the input domain into different partitions of classes to reduce time of testing and number of test cases. For example, It can uncover the classes of errors by dividing inputs as valid and invalid classes. This technique tries to define test cases that uncover classes of errors, thereby reducing the total number of test cases that must be developed. An advantage of this approach is reduction in the time required for testing software due to lesser number of test cases.

### OpenRefine partition test cases

The step of our testing is divided into four steps.

- Decompose the specification into equivalence partitions
- Select representatives
- Form test specifications
- Produce and execute actual tests

#### Feature Introduction

select a feature that allows for partitioning. Specify your partitions (and boundaries when appropriate) in English — describe them.

**Text facet**

The feature we are going to study is `text facet`, which can be generated on any column with the "text" data type. Text facet is a powerful way to explore messy data at the very beginning.

For example, let's say that we have a survey for people demographics.

![](https://i.imgur.com/MelDtnq.png)

If we take a closer look, we can tell that many expression of *the United States* in the table based on how people answer their question in the survey.

In the other words, you can spot like:

| country                  |
|--------------------------|
| United states of America |
| UNITED STATES            |
| US                       |

User can select the column dropdown menu and choose `Facet` -> `Text facet`. The created facet will be sorted alphabetically, and can also be sorted by count.

![](https://i.imgur.com/a9hMuDR.png)

We can get the information after *Text facet*, and it give a basic filter option to work on and clean data later.

![](https://i.imgur.com/tvTQcbA.png)

Because of the same country which represents the United States, we can edit the country data set easily.

![](https://i.imgur.com/2kBB3ZY.png)

In addition, to explore data, `text` data type can be operated by the `Text Filter` options.

- Filter exclusive facet by "invert" option
- Filter customized facet by "regular expression" option

### Feature for OpenRefine partition test cases

Partition of `Text filter` for text facet.

`main/tests/server/src/com/google/refine/browsing/facets/TextSearchFacetTests.java`

Because `text` data type is "string" operation in Java, we can narrow down partition categories and boundaries for string specific scenarios.


| Partition Catogories | Boundaries |
| -------- | -------- | 
| specific characters     |  [\^0-9\^a-z\^A-Z\^ ]+    |
| alphabetes(capital)  | [A-Z]|
| alphabetes(non-capital) | [a-z] |
| numbers     |  [0-9]     |
| spaces | " " |
| length exceeds maximum value | greater than  2147483647 (characters) |





### Write new test cases in JUnit

We will use our previous parition and boundaries to write some new test cases in JUnit for OpenRefine `text filter` feature.

You can also find our changes under our fored repo in [part1 branch](https://github.com/yrfang/OpenRefine/tree/part1).

#### JUnit test file location:

You can find our new test cases under this folder: `main/tests/server/src/com/google/refine/browsing/facets/CustomJunitTests.java`

#### Execution in Intellij IDEA

Because OpenRefine server side doesn't use JUnit write the unit tests (it uses TestNG), we have to import Junit dependencies from Maven in `pom.xml` first.

```bash=
# Find the pom.xml under Project root folder
# Add the junit dependency
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-api</artifactId>
  <version>5.7.0</version>
</dependency>
```

#### Our new test cases

We use some of previous partition catogories and bounderies as our new testing inputs.

| Partition Catogories | Test Example |
| -------- | -------- | 
| specific characters     |  ^&*    |
| alphabetes(capital)  | ABCDEF|
| alphabetes(non-capital) | abcdef |
| numbers     |  123456     |
| spaces | " " |

- `testTextFilter()`: if input value contains text in the query, the result should be true, otherwise, false.
- `testInvertedTextFilter()`: if input value contains text in the query, the result should be false, otherwise, true.
- `testRegExFilter()`: if input value matches regular expression in the query, the result should be true, otherwise, false.
- `testCaseSensitiveFilter()`: if input value contains capital letter, the result should be true, otherwise, false.

Take one of test case for an example. We query letter of "a" in our test cases and we can use Junit assert syntax to compare the actual result to expected result at the end.

```java=
@Test
    public void testTextFilter() throws Exception {
        // Apply text filter "a"

        // Column: "Value"
        // Filter Query: "a"
        // Mode: "text"
        // Case sensitive: False
        // Invert: False
        String filter = "{\"type\":\"text\","
                + "\"name\":\"Value\","
                + "\"columnName\":\"Value\","
                + "\"mode\":\"text\","
                + "\"caseSensitive\":false,"
                + "\"invert\":false,"
                + "\"query\":\"a\"}";

        configureFilter(filter);

        // Check each row in the project against the filter
        Assert.assertEquals(rowfilter.filterRow(project, 0, project.rows.get(0)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 1, project.rows.get(1)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 2, project.rows.get(2)), true);
        Assert.assertEquals(rowfilter.filterRow(project, 3, project.rows.get(3)), false);
        Assert.assertEquals(rowfilter.filterRow(project, 4, project.rows.get(4)), false);
    }
```


#### How we run them?

We are able to see the result by right clicking on the file and select `Run CustomJunitTests` button. Therefore, we can see the Green Pass results in IntelliJ.

![](https://i.imgur.com/GdXK6E4.png)


