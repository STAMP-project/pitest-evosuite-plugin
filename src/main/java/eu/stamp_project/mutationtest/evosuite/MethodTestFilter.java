package eu.stamp_project.mutationtest.evosuite;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

import java.lang.reflect.Method;

public class MethodTestFilter extends Filter {

    private Method method;

    public MethodTestFilter(Method method) {
        if(method == null) throw new IllegalArgumentException("Method can not be null");
        this.method = method;
    }

    @Override
    public boolean shouldRun(Description description) {
        return description.getMethodName().equals(getMethodName()) &&
                description.getClassName().equals(getClassName());
    }

    private String getClassName() {
        return method.getDeclaringClass().getName();
    }

    private String getMethodName() {
        return method.getName();
    }

    @Override
    public String describe() {
        return String.format("Run only method %s from class %s", getMethodName(), getClassName());
    }
}
