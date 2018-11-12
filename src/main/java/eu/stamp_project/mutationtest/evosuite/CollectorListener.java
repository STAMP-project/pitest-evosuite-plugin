package eu.stamp_project.mutationtest.evosuite;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.StoppedByUserException;
import org.pitest.testapi.ResultCollector;

// This class is a copy of https://github.com/hcoles/pitest/blob/57af81af9e4914c4f89c34af485c2342ec6e6a9e/pitest/src/main/java/org/pitest/junit/adapter/AdaptingRunListener.java
// which is private

public class CollectorListener extends RunListener {
    private final org.pitest.testapi.Description description;
    private final ResultCollector rc;
    private boolean                              failed = false;

    CollectorListener(final org.pitest.testapi.Description description,
                        final ResultCollector rc) {
        this.description = description;
        this.rc = rc;
    }

    @Override
    public void testStarted(final Description description) throws Exception {
        //TODO: Since we are running only one test method at a time this is maybe not needed
        if (this.failed) {
            // If the JUnit test has been annotated with @BeforeClass or @AfterClass
            // need to force the exit after the first failure as tests will be run as
            // a block
            // rather than individually.
            // This is apparently the junit way.
            throw new StoppedByUserException();
        }
        this.rc.notifyStart(this.description);
    }

    @Override
    public void testFailure(final Failure failure) throws Exception {
        this.rc.notifyEnd(this.description, failure.getException());
        this.failed = true;
    }

    @Override
    public void testAssumptionFailure(final Failure failure) {
        // do nothing so treated as success
        // see http://junit.sourceforge.net/doc/ReleaseNotes4.4.html#assumptions
    }

    @Override
    public void testIgnored(final Description description) throws Exception {
        this.rc.notifySkipped(this.description);
    }

    @Override
    public void testFinished(final Description description) throws Exception {
        if (!this.failed) {
            this.rc.notifyEnd(this.description);
        }
    }

}
