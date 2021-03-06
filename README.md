# pitest-evosuite-plugin
A PITest test plugin to run test classes generated by EvoSuite

## What is `pitest-evosuite-plugin`?

[EvoSuite](http://www.evosuite.org) is a tool to automatically generate test suites for Java projects. 
[PITest](http://pitest.org) is a state of the art mutation testing tool, also for Java projects.
By default, EvoSuite runs the generated test cases in a sandbox. Under this configuration the mutations performed by PITest have no effect
and therefore the result is wrong. (See https://github.com/hcoles/pitest/issues/344 and https://github.com/hcoles/pitest/issues/384).
One solution is to disable this configuration. It can be done in a test-class-wise manner, 
by manually changing the parameter `separateClassLoader` from `true` (default value) to `false`.

```Java
@RunWith(EvoRunner.class) 
@EvoRunnerParameters(
  mockJVMNonDeterminism = true, 
  useVFS = true, 
  useVNET = true, 
  resetStaticState = true, 
  separateClassLoader = true, // Change to false 
  useJEE = true) 
public class ESTest extends ESTest_scaffolding {
  ...
}
```
A better alternatove is to execute EvoSuite and add `-Duse_separate_classloader=false` to the command line.

Nevertheless, since the generated test classes use a custom test runner, PITest will not be able to separate the test class in atomic test cases. This has an impact in the performance of the analysis and the information reported at the end of the analysis.

`pitest-evosuite-plugin` provides an alterntive that executes the EvoSuite generated test classes out of the sandbox, 
without considering the parameters given to the EvoSuite test runner and splits an EvoSuite generated test class into test cases,
so the user can know which test cases cover and kill each mutant.

## How does `pitest-evosuite-plugin` works?

The project provides a plugin for PITest that implements the interfaces needed to discover and execute test cases. The [code of the PITest JUnit5 plugin](https://github.com/pitest/pitest-junit5-plugin) has been a great supporting material. 

## Usage

The project is not currently available from Maven Central. 
So to use you must first clone the repository, build the code and install locally:
```
git clone https://github.com/STAMP-project/pitest-evosuite-plugin
cd pitest-evosuite-plugin
mvn clean install
```

To make PITest use the plugin, 
both the plugin and the EvoSuite standalone runtime classes should be in the same classpath as PITest 
and PITest should be instructed to use the plugin.

### From Maven

To use it from Maven, specify the following configuration in the `pom.xml` file:

```xml
<plugin>
  <groupId>org.pitest</groupId>
  <artifactId>pitest-maven</artifactId>
  <version>1.4.3</version>
  <configuration>
    <testPlugin>evosuite</testPlugin>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>eu.stamp-project</groupId>
      <artifactId>pitest-evosuite-plugin</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>
  </dependencies>
</plugin>
```


### From the command line

When executing PITest from the command line, add the path to the `pitest-evosuite-plugin` jar 
(that can be found in the local repository once the code is built) 
and the EvoSuite standalone runtime (available for download in the [EvoSuite web site](https://github.com/EvoSuite/evosuite/releases/download/v1.0.6/evosuite-standalone-runtime-1.0.6.jar))
and then set `--testPlugin evosuite` in the command line. 
For example:

```
java -cp pitest-1.4.3.jar:pitest-command-line-1.4.3.jar:pitest-entry-1.4.3.jar:evosuite-standalone-runtime-1.0.6.jar:pitest-evosuite-plugin-1.0-SNAPSHOT.jar \
org.pitest.mutationtest.commandline.MutationCoverageReport \
--classPath /code/target/bin \
--mutableCodePath /code/target/classes \
--sourceDirs /code/src/main/java,/code/src/test/java \
--testPlugin evosuite \
```
## License
This project is published under LGPL-3.0 (see LICENSE.md for further details).

**Issues and pull request are welcome!**

## Funding
This project is partially funded by research project STAMP (European Commission - H2020) 

![STAMP - European Commission - H2020](https://github.com/STAMP-project/pitest-descartes/raw/master/docs/logo_readme_md.png)
