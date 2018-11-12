package eu.stamp_project.mutationtest.evosuite;

import eu.stamp_project.mutationtest.evosuite.test.ClassUnderTest;
import eu.stamp_project.mutationtest.evosuite.test.NoValidTestCase;
import eu.stamp_project.mutationtest.evosuite.test.TestWithOneSuccess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;


import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@RunWith(Parameterized.class)
public class EvosuiteTestUnitFinderTest {

    @Parameter
    public Class<?> testClass;

    @Parameter(1)
    public int expectedTestCases;


    @Parameters(name="{index} Class {0} expected to have {1} test cases")
    public static Collection<Object[]> data() {
        return Arrays.asList( new Object[][] {
                {ClassUnderTest.class    , 0},
                {TestWithOneSuccess.class, 1},
                {NoValidTestCase.class   , 0}
        });
    }

    @Test()
    public void testTestWithOneSuccess() {
        EvosuiteTestUnitFinder finder = new EvosuiteTestUnitFinder();
        assertThat(finder.findTestUnits(testClass), hasSize(expectedTestCases));
    }
}