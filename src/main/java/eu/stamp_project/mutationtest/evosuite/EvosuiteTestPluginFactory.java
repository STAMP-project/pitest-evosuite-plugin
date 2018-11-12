package eu.stamp_project.mutationtest.evosuite;

import org.pitest.classinfo.ClassByteArraySource;
import org.pitest.testapi.Configuration;
import org.pitest.testapi.TestGroupConfig;
import org.pitest.testapi.TestPluginFactory;

import java.util.Collection;

public class EvosuiteTestPluginFactory implements TestPluginFactory {

    @Override
    public Configuration createTestFrameworkConfiguration(
            TestGroupConfig config,
            ClassByteArraySource source,
            Collection<String> excludedRunners,
            Collection<String> includedTestMethods
    ) {
        //TODO: Handle the configuration exclusions
        return new EvosuiteTestConfiguration();
    }

    @Override
    public String name() {
        return "evosuite";
    }

    @Override
    public String description() {
        return "Support for EvoSuite generated tests";
    }

}
