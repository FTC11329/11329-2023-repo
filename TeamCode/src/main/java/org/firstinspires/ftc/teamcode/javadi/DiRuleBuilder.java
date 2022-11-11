package org.firstinspires.ftc.teamcode.javadi;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class DiRuleBuilder {
    protected final DiContainer container;
    protected final List<DiRule> targetRules = new ArrayList<>();

    protected Boolean bindDone = false;
    protected Boolean resolutionSet = false;

    private Class<?> target = null;

    protected DiRuleBuilder(DiContainer containerIn) {
        this.container = containerIn;
    }

    /**
    * Creates a new set of rules which registers raw classes with the Conteiners
    *
    * @param inClasses The classes to bind
    * @return A new RuleBuilder 
    * @throws DiExceptions.RuleBuilderException Caused by having an improper configuration prior to trying to set resolution
    * @see DiRule
    */
    public DiRuleBuilder bind(Class<?>... inClasses) {
        if (this.bindDone) throw new DiExceptions.RuleBuilderException("Tried to bind classes to a rule which has already been completed.");

        for (Class<?> inClass : inClasses) {
            DiRule rule = new DiRule(this.container, inClass);

            this.targetRules.add(rule);
            this.container.rules.add(rule);
        }

        return this;
    }

    /**
    * Creates a new rule which registers an instance to be resolved by the Container
    *
    * @param instance The instance to add to the Container
    * @return A new RuleBuilder 
    * @throws DiExceptions.RuleBuilderException Caused by having an improper configuration prior to trying to set resolution
    * @see DiRule
    */
    public DiRuleBuilder bindInstance(Object instance) {
        this.bindInstances(instance);

        return this;
    }

    /**
    * Creates a new set of rules which registers multiple instances to be resolved by the Container
    *
    * @param instances The instances to add to the Container
    * @return A new RuleBuilder 
    * @throws DiExceptions.RuleBuilderException Caused by having an improper configuration prior to trying to set resolution
    * @see DiRule
    */
    public DiRuleBuilder bindInstances(Object... instances) {
        if (this.bindDone || this.resolutionSet) throw new DiExceptions.RuleBuilderException("Tried to bind instances to a rule which has already been completed.");

        this.bindDone = true;
        this.resolutionSet = true;

        for (Object instance : instances) {
            UUID uuid = UUID.randomUUID();

            this.container.objectPool.put(uuid, instance);

            DiRule rule = new DiRule(this.container, instance.getClass());

            rule.setupReturn(uuid);

            this.targetRules.add(rule);
            this.container.rules.add(rule);
        }

        return this;
    }

    /**
    * Creates a new rule which registers a class' interfaces to resolve to one single class 
    *
    * @param inClass The class to get interfaces from and bind
    * @return A new RuleBuilder 
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @see DiRule
    */
    public DiRuleBuilder bindInterfacesTo(Class<?> inClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        this.bind(inClass.getInterfaces());

        this.to(inClass);

        this.asSingle();

        return this;
    }

    /**
    * Creates a new rule which registers a class and its interfaces to resolve to one single class 
    *
    * @param inClass The class to get interfaces from and bind
    * @return A new RuleBuilder 
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @see DiRule
    */
    public DiRuleBuilder bindInterfacesAndSelfTo(Class<?> inClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<Class<?>> classes = Arrays.asList(inClass.getInterfaces());
        classes.add(inClass);

        this.bind(classes.toArray(new Class<?>[0]));

        this.to(inClass);

        this.asSingle();

        return this;
    }

    /**
    * Tells the rules to operate in singleton mode
    *
    * @return A new RuleBuilder 
    * @throws DiExceptions.RuleBuilderException Caused by having an improper configuration prior to trying to set resolution
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @see DiRule
    */
    public DiRuleBuilder asSingle() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (this.resolutionSet) throw new DiExceptions.RuleBuilderException("Tried to set the resolution mode of a completed rule to single.");
        

        this.resolutionSet = true;

        if (this.target != null) {
            this.fromInstance(this.container.instantiate(this.target));
        } else {
            for (DiRule rule : this.targetRules) {
                Object instance = this.container.instantiate(rule.targetClass);
                UUID uuid = UUID.randomUUID();

                this.container.objectPool.put(uuid, instance);

                rule.setupReturn(uuid);
            }
        }

        return this;
    }


    /**
    * Tells the rules to operate in instantiation mode
    *
    * @return A new RuleBuilder 
    * @throws DiExceptions.RuleBuilderException Caused by having an improper configuration prior to trying to set resolution
    * @see DiRule
    */
    public DiRuleBuilder asTransient() {
        if (this.resolutionSet) throw new DiExceptions.RuleBuilderException("Tried to set the resolution mode of a completed rule to transient.");

        this.resolutionSet = true;

        if (this.target != null) {
            for (DiRule rule : this.targetRules) {
                rule.setupCreate(this.target);
            }
        } else {
            for (DiRule rule : this.targetRules) {
                rule.setupCreate(rule.targetClass);
            }
        }

        return this;
    }

    /*public DiRuleBuilder AsCached() {
        // Create new Cached Instances
        return this;
    }*/


    /**
    * Sets the rules to target a new class for resolution
    *
    * @param inClass The class to target
    * @return A new RuleBuilder 
    * @throws DiExceptions.RuleBuilderException Caused by having an improper configuration prior to trying to set resolution
    * @see DiRule
    */
    public DiRuleBuilder to(Class<?> inClass) {
        if (this.target != null) throw new DiExceptions.RuleBuilderException("Tried to bind to classes on a rule which has been completed.");

        this.target = inClass;

        return this;
    }

    /**
    * Sets the rules to resolve a specific instance
    *
    * @param instance The instance to resolve
    * @return A new RuleBuilder 
    * @throws DiExceptions.RuleBuilderException Caused by having an improper configuration prior to trying to set resolution
    * @see DiRule
    */
    public DiRuleBuilder fromInstance(Object instance) {
        if (this.resolutionSet) {
            throw new DiExceptions.RuleBuilderException("Tried to set the resolution mode of a completed rule to an instance.");
        }

        this.resolutionSet = true;

        UUID uuid = UUID.randomUUID();

        this.container.objectPool.put(uuid, instance);

        for (DiRule rule : this.targetRules) {
            rule.setupReturn(uuid);
        }

        return this;
    }

    /**
    * Adds a custom resolution condition to the rules 
    *
    * @param condition The condition to add to the rules
    * @return A new RuleBuilder 
    * @see DiRule
    */
    public DiRuleBuilder when(Function<DiContext, Boolean> condition) {
        for (DiRule rule : this.targetRules) {
            rule.conditions.add(condition);
        }

        return this;
    }

    /**
    * Adds a rule to ensure the rules only resolve when injecting in to a specific class
    *
    * @param inClass The class to limit the Rules to
    * @return A new RuleBuilder 
    * @see DiRule
    */
    public DiRuleBuilder whenInjectedInto(Class<?> inClass) {
        for (DiRule rule : this.targetRules) {
            rule.conditions.add(context -> context.targetClass == inClass);
        }

        return this;
    }

    /**
    * Adds a rule to ensure the rules only resolve when injecting in to an annotation with the correct Id
    *
    * @param id The class to limit the Rules to
    * @return A new RuleBuilder 
    * @see DiRule
    */
    public DiRuleBuilder withId(String id) {
        for (DiRule rule : this.targetRules) {
            rule.conditions.add((context) -> context.id.equals(id));
        }

        return this;
    }
}
