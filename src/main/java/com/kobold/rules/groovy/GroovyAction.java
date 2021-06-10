package com.kobold.rules.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

import java.util.Objects;

public class GroovyAction implements Action {

    private final Script expression;

    public GroovyAction(String expression) {
        this.expression = new GroovyShell().parse(expression);
    }

    public GroovyAction(String expression, CompilerConfiguration compilerConfiguration) {
        this.expression = new GroovyShell(compilerConfiguration).parse(expression);
    }

    @Override
    public void execute(Facts facts) throws Exception {
        Objects.requireNonNull(facts, "facts cannot be null");
        Binding binding = new Binding();
        facts.asMap().forEach(binding::setVariable);
        InvokerHelper.createScript(expression.getClass(), binding).run();
    }
}
