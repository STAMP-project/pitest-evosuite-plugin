package eu.stamp_project.mutationtest.evosuite.test;


import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true)
public class TestWithFailureAndSuccess extends BaseTestScaffolding {
    @Test
    public void testSuccess() {
        assertTrue(true);
    }

    @Test
    public void testFailure() {
        fail();
    }
}
