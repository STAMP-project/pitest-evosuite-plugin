package eu.stamp_project.mutationtest.evosuite;

import org.evosuite.annotations.EvoSuiteTest;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pitest.testapi.TestUnit;
import org.pitest.testapi.TestUnitFinder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EvosuiteTestUnitFinder implements TestUnitFinder {

    @Override
    public List<TestUnit> findTestUnits(Class<?> clazz) {
        if(!isTestClass(clazz))
            return Collections.emptyList();
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(EvosuiteTestUnitFinder::isTestMethod)
                .map(EvosuiteTestUnit::new)
                .collect(Collectors.toList());
    }

    public static boolean isTestMethod(Method method) {
        return Modifier.isPublic(method.getModifiers()) &&
                method.getParameterCount() == 0 &&
                method.getReturnType().equals(void.class) &&

                (method.getAnnotation(Test.class) != null ||
                        method.getAnnotation(EvoSuiteTest.class) != null);
    }

    public static boolean isTestClass(Class<?> clazz) {
        return clazz.getAnnotation(RunWith.class) != null && clazz.getAnnotation(EvoRunnerParameters.class) != null;
    }
}
