package eu.stamp_project.mutationtest.evosuite.test;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true)
public class NoValidTestCase extends BaseTestScaffolding {

    @Test
    private void empty() {

    }

    @Test
    public int three() {
        return 3;
    }

    @Test
    public void args(String name) {
        name.length();
    }

    public void method() {

    }
}
