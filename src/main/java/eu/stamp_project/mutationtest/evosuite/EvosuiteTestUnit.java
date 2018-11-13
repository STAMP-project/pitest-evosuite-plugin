package eu.stamp_project.mutationtest.evosuite;

import org.evosuite.runtime.EvoRunner;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.pitest.testapi.Description;
import org.pitest.testapi.ResultCollector;
import org.pitest.testapi.TestUnit;

import java.lang.reflect.Method;

import static eu.stamp_project.mutationtest.evosuite.EvosuiteTestUnitFinder.isTestMethod;

public class EvosuiteTestUnit implements TestUnit {

    private Method method;

    public EvosuiteTestUnit(Method method) {
        if(method == null) {
            throw new IllegalArgumentException("Method can not be null");
        }
        if(!isTestMethod(method)) {
            throw new IllegalArgumentException("A test method should be public, with no arguments and annotated with Test or EvosuiteTest");
        }

        this.method = method;
    }

    @Override
    public void execute(ResultCollector rc) {
        Description description = getDescription();
        try {
            RunNotifier notifier = new RunNotifier();
            notifier.addFirstListener(new CollectorListener(description, rc));
            // Overriding these two parameters from the test classes
            EvoRunner.useClassLoader = false;
            EvoRunner.useAgent = false;
            EvoRunner runner = new EvoRunner(method.getDeclaringClass());
            runner.filter(new MethodTestFilter(method));
            runner.run(notifier);
        }
        catch (InitializationError error) {
            rc.notifyEnd(description, error);
        }
        catch (NoTestsRemainException exc) {
            rc.notifySkipped(description);
        }
    }

    @Override
    public Description getDescription() {
        return new Description(method.getName(), method.getDeclaringClass());
    }
}
