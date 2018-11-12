package eu.stamp_project.mutationtest.evosuite;

import eu.stamp_project.mutationtest.evosuite.test.TestWithFailureAndSuccess;
import eu.stamp_project.mutationtest.evosuite.test.TestWithIgnore;
import eu.stamp_project.mutationtest.evosuite.test.TestWithOneFailure;
import eu.stamp_project.mutationtest.evosuite.test.TestWithOneSuccess;
import javassist.runtime.Desc;
import org.junit.Test;
import org.junit.runner.notification.RunNotifier;
import org.mockito.Mockito;
import org.pitest.testapi.Description;
import org.pitest.testapi.ResultCollector;

import java.lang.reflect.Method;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EvosuiteTestUnitTest {

    private ResultCollector run(Class<?> testClass, String methodName) throws NoSuchMethodException {
        Method method =  testClass.getMethod(methodName);
        EvosuiteTestUnit testUnit = new EvosuiteTestUnit(method);

        ResultCollector collector = mock(ResultCollector.class);
        testUnit.execute(collector);
        return collector;
    }

    @Test
    public void testSuccessNotification() throws Exception {
        ResultCollector collector = run(TestWithOneSuccess.class, "testSuccess");
        verify(collector).notifyStart(any(Description.class));
        verify(collector).notifyEnd(any(Description.class));

    }

    @Test
    public void testFailureNotification() throws Exception {
        ResultCollector collector = run(TestWithOneFailure.class, "testFailure");
        verify(collector).notifyStart(any(Description.class));
        verify(collector).notifyEnd(any(Description.class), any(AssertionError.class));
    }

    @Test
    public void testSuccessInMixedClass() throws Exception {
        ResultCollector collector = run(TestWithFailureAndSuccess.class, "testSuccess");
        verify(collector).notifyStart(any(Description.class)); //Only one start is notified
        verify(collector).notifyEnd(any(Description.class)); //Only one successful end is notified
        verify(collector, never()).notifySkipped(any(Description.class)); // There is no skip notification
    }

    @Test
    public void testSkipNotification()  throws Exception {
        ResultCollector collector = run(TestWithIgnore.class, "testIgnore");
        verify(collector).notifySkipped(any(Description.class));
        verify(collector, never()).notifyEnd(any(Description.class));
        verify(collector, never()).notifyStart(any(Description.class));
        verify(collector, never()).notifyEnd(any(Description.class), any(Throwable.class));
    }



}