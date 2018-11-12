package eu.stamp_project.mutationtest.evosuite;

import org.pitest.testapi.TestSuiteFinder;

import java.util.Collections;
import java.util.List;

public class EvosuiteTestSuiteFinder implements TestSuiteFinder {

    @Override
    public List<Class<?>> apply(Class<?> aClass) {
        return Collections.emptyList(); //Do not handle test suites
    }

}
