package eu.stamp_project.mutationtest.evosuite;

import org.pitest.help.PitHelpError;
import org.pitest.testapi.Configuration;
import org.pitest.testapi.TestSuiteFinder;
import org.pitest.testapi.TestUnitFinder;

import java.util.Optional;

public class EvosuiteTestConfiguration implements Configuration {

    @Override
    public TestUnitFinder testUnitFinder() {
        return new EvosuiteTestUnitFinder();
    }

    @Override
    public TestSuiteFinder testSuiteFinder() {
        return new EvosuiteTestSuiteFinder();
    }

    @Override
    public Optional<PitHelpError> verifyEnvironment() {
        return Optional.empty();
    }
}
