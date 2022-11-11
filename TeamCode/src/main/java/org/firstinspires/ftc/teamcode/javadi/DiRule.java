package org.firstinspires.ftc.teamcode.javadi;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class DiRule {
    protected DiContainer container;
    protected RetrievalMode retrievalMode = RetrievalMode.None;
    protected Class<?> targetClass;
    protected Class<?> instanceClass;
    public UUID targetObject;
    protected List<Function<DiContext, Boolean>> conditions = new ArrayList<>();

    public DiRule(DiContainer containerIn, Class<?> targetClassIn) {
        this.container = containerIn;
        this.targetClass = targetClassIn;
    }

    /**
    * Sets up this rule to create new instances when requested
    *
    * @param instanceClassIn The class to create
    * @see DiContainer
    */
    protected void setupCreate(Class<?> instanceClassIn) {
        this.instanceClass = instanceClassIn;
        this.retrievalMode = RetrievalMode.Create;
    }

    /**
    * Sets up this rule to resolve a different class when requested
    *
    * @param instanceClassIn The class to resolve
    * @throws DiExceptions.IncompleteBindingException Caused if the rule tries to resolve itself in order to prevent deadlocks
    * @see DiContainer
    */
    protected void setupResolve(Class<?> instanceClassIn) {
        if (this.targetClass == instanceClassIn) throw new DiExceptions.IncompleteBindingException(this, true);

        this.instanceClass = instanceClassIn;
        this.retrievalMode = RetrievalMode.Resolve;
    }

    /**
    * Sets up this rule to return an instance when requested
    *
    * @param targetObjectIn The UUID of the object in the object pool
    * @see DiContainer
    */
    public void setupReturn(UUID targetObjectIn) {
        this.targetObject = targetObjectIn;
        this.retrievalMode = RetrievalMode.Return;
    }

    /**
    * Checks if this rule applies to a specific Context
    *
    * @param context The given Context to check against
    * @return Whether or not this rule applies to a given Context
    * @see DiContainer
    */
    protected boolean ruleApplies(DiContext context) {
        boolean canAssignContextToTarget = context.memberClass.isAssignableFrom(this.targetClass);
        boolean canAssignTargetToContext = this.targetClass.isAssignableFrom(context.memberClass);

        if (!(canAssignContextToTarget || canAssignTargetToContext)) return false;

        if (this.conditions.size() < 1) return true;

        boolean applies = true;

        for (Function<DiContext, Boolean> condition: this.conditions) {
            if (!condition.apply(context)) {
                applies = false;
                break;
            }
        }

        return applies;
    }

    /**
    * Returns an object instance using which ever method it was configured for earlier
    *
    * @param context The given Context to use during some operations
    * @return The object instance this Rule grabs
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @throws DiExceptions.IncompleteBindingException Caused if the rule was never configured
    * @see DiContainer
    */
    protected Object getObjectValue(DiContext context) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        switch (this.retrievalMode) {
            case Create:
                if (this.instanceClass != null) return this.container.instantiate(this.instanceClass, context);
                break;
            case Return:
                if (this.targetObject != null) {
                    Object instance = this.container.objectPool.get(this.targetObject);

                    if (instance != null) return instance;
                }
                break;
            case Resolve:
                if (this.instanceClass != null) return this.container.resolve(this.instanceClass, context);
            default:
                break;
        }

        throw new DiExceptions.IncompleteBindingException(this, false);
    }

    protected enum RetrievalMode {
        None, Resolve, Return, Create
    }
}