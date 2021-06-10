package com.kobold.rules.groovy;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.core.BasicRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroovyRule extends BasicRule {
    private Condition condition = Condition.FALSE;
    private final List<Action> actions = new ArrayList<>();

    private final CompilerConfiguration compilerConfiguration;

    public GroovyRule() {
        this(CompilerConfiguration.DEFAULT);
    }

    public GroovyRule(CompilerConfiguration compilerConfiguration) {
        this.compilerConfiguration = compilerConfiguration;
    }


    /**
     * Set rule name.
     *
     * @param name of the rule
     * @return this rule
     */
    public GroovyRule name(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        return this;
    }

    /**
     * Set rule description.
     *
     * @param description of the rule
     * @return this rule
     */
    public GroovyRule description(String description) {
        this.description = Objects.requireNonNull(description, "description cannot be null");
        return this;
    }

    /**
     * Set rule priority.
     *
     * @param priority of the rule
     * @return this rule
     */
    public GroovyRule priority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Specify the rule's condition as groovy expression.
     *
     * @param condition of the rule
     * @return this rule
     */
    public GroovyRule when(String condition) {
        this.condition = new GroovyCondition(condition, this.compilerConfiguration);
        return this;
    }

    /**
     * Add an action specified as an groovy expression to the rule.
     *
     * @param action to add to the rule
     * @return this rule
     */
    public GroovyRule then(String action) {
        this.actions.add(new GroovyAction(action, this.compilerConfiguration));
        return this;
    }

    @Override
    public boolean evaluate(Facts facts) {
        return condition.evaluate(facts);
    }

    @Override
    public void execute(Facts facts) throws Exception {
        for (Action action : actions) {
            action.execute(facts);
        }
    }

}
