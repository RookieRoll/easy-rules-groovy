package com.kobold.rules.groovy;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.support.AbstractRuleFactory;
import org.jeasy.rules.support.RuleDefinition;

public class GroovyRuleFactory extends AbstractRuleFactory {
    private final CompilerConfiguration compilerConfiguration;

    public GroovyRuleFactory() {
        this(CompilerConfiguration.DEFAULT);
    }

    public GroovyRuleFactory(CompilerConfiguration compilerConfiguration) {
        this.compilerConfiguration = compilerConfiguration;
    }

    @Override
    protected Rule createSimpleRule(RuleDefinition ruleDefinition) {
        GroovyRule groovyRule = new GroovyRule(compilerConfiguration)
                .name(ruleDefinition.getName())
                .description(ruleDefinition.getDescription())
                .priority(ruleDefinition.getPriority())
                .when(ruleDefinition.getCondition());
        for (String action : ruleDefinition.getActions()) {
            groovyRule.then(action);
        }
        return groovyRule;
    }
}
