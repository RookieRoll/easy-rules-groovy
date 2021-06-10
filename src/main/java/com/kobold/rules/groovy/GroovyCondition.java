package com.kobold.rules.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

import java.util.Objects;

public class GroovyCondition implements Condition {

    private final Script compiledExpression;

    public GroovyCondition(String expression) {
        compiledExpression = new GroovyShell().parse(expression);
    }

    public GroovyCondition(String expression, CompilerConfiguration compilerConfiguration) {
        this.compiledExpression = new GroovyShell(compilerConfiguration).parse(expression);
    }

    @Override
    public boolean evaluate(Facts facts) {
        Objects.requireNonNull(facts, "facts cannot be null");
        Binding binding = new Binding();
        facts.asMap().forEach(binding::setVariable);
        return (boolean) InvokerHelper.createScript(compiledExpression.getClass(), binding).run();
    }
}
