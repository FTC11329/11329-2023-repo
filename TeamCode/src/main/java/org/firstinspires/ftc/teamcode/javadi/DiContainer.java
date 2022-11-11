package org.firstinspires.ftc.teamcode.javadi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;

public class DiContainer {
    /**
    * An Annotation to mark the Field to be injected by the Container <br><br><br><br>
    *
    * <b>id</b>: The optional Id of the instance <br><br>
    * <b>optional</b>: Whetheher or not injecting the field is mandatory or not <br><br>
    * @see DiContainer
    */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Inject {
        String id() default "";
        boolean optional() default false;
    }

    DiContainer parentContainer = null;

    public List<DiRule> rules = new ArrayList<>();
    public WeakHashMap<UUID, Object> objectPool = new WeakHashMap<>();

    /**
    * Initializes all objects in the object pool
    * Doesn't check if initialization has already happened, please only call this once
    *
    * @see DiContext
    */
    public void onInject() {
        for (Object objectInstance : this.objectPool.values()) {
            if (objectInstance instanceof DiInterfaces.IInitializable) {
                ((DiInterfaces.IInitializable) objectInstance).onInitialize();
            }
        }
    }

    /**
    * Ticks all objects in the object pool
    *
    * @see DiContext
    */
    public void onTick() {
        for (Object objectInstance : this.objectPool.values()) {
            if (objectInstance instanceof DiInterfaces.ITickable) {
                ((DiInterfaces.ITickable) objectInstance).onTick();
            }
        }
    }

    /**
    * Disposes all objects in the object pool
    *
    * @see DiContext
    */
    public void onDispose() {
        for (Object objectInstance : this.objectPool.values()) {
            if (objectInstance instanceof DiInterfaces.IDisposable) {
                ((DiInterfaces.IDisposable) objectInstance).onDispose();
                
            }
        }

        this.objectPool.clear();
        this.objectPool = new WeakHashMap<>();

        System.gc();
    }

    /**
    * Sets this Container to have a parent
    * Containers with parents resolve instances both from themselves and their parent.
    *
    * @param parent The Container to set as a parent
    * @see DiContainer
    */
    public void setParent(DiContainer parent) {
        this.parentContainer = parent;
    }

    /**
    * Resolves all instances that could be valid for a given Context
    *
    * @param searchClass The class to search for
    * @param context The Context to find instances for
    * @return All objects valid for the Context
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @see DiContainer
    */
    protected List<Object> resolveAll(Class<?> searchClass, DiContext context) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Object> values = new ArrayList<>();
        context.memberClass = searchClass;

        for (DiRule rule : this.rules) {
            if (rule.ruleApplies(context)) {
                values.add(rule.getObjectValue(context));
            }
        }

        if (this.parentContainer != null) {
            values.addAll(this.parentContainer.resolveAll(searchClass, context));
        }

        return values;
    }

    /**
    * Gives you a single instance that is valid for a given context
    *
    * @param searchClass The class to search for
    * @param context The Context to find instances for
    * @return A single object valid for the Context
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @throws DiExceptions.MultipleInstancesFoundException Caused if multiple instances are found
    * @throws DiExceptions.InstanceNotFoundException Caused if no instances are found
    * @see DiContainer
    */
    protected Object resolve(Class<?> searchClass, DiContext context) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<Object> values = this.resolveAll(searchClass, context);

        if (values.size() > 1) throw new DiExceptions.MultipleInstancesFoundException(searchClass, context, rules, objectPool);
        if (values.size() < 1) throw new DiExceptions.InstanceNotFoundException(searchClass, context, rules, objectPool);

        return values.get(0);
    }

    /**
    * Instantiates an instance of a class using a given context and container
    *
    * @param inClass The class to instantiate
    * @param context The Context to use during instantiation
    * @return The instantiated and injected object
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @see DiContainer
    */
    protected Object instantiate(Class<?> inClass, DiContext context) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] constructors = inClass.getConstructors();
        Class<?>[] parameterClasses = constructors[0].getParameterTypes();
        List<Object> parameterValues = new ArrayList<>();

        context.targetClass = inClass;

        for (Class<?> parameterClass : parameterClasses) {
            parameterValues.add(this.resolve(parameterClass, context));
        }

        Object instance = constructors[0].newInstance(parameterValues.toArray(new Object[0]));

        return this.inject(instance, context);
    }

    /**
    * Injects any annotations on a class
    *
    * @param instance The object to inject in to
    * @param context The Context to use during injection
    * @return The injected object
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @see DiContainer
    */
    protected Object inject(Object instance, DiContext context) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field field: fields) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Inject.class)) continue;
            Inject inject = field.getAnnotation(Inject.class);

            try {
                context.id = inject.id();

                if (context.id.equals("")) {
                    context.id = null;
                }
            } catch (NullPointerException e) {
                context.id = null;
            }

            context.optional = inject.optional();
            context.memberName = field.getName();
            context.memberClass = field.getType();

            field.set(instance, this.resolve(field.getType(), context));
        }

        if (instance instanceof DiInterfaces.IInjected) ((DiInterfaces.IInjected) instance).onInject();

        return instance;
    }

    /**
    * Injects any annotations on a class
    *
    * @param instance The object to inject in to
    * @return The injected object
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @see DiContainer
    */
    public Object inject(Object instance) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        DiContext context = new DiContext(null, instance.getClass(), instance, "", null, false, this);

        return this.inject(instance, context);
    }

    /**
    * Instantiates an instance of the class
    *
    * @param inClass The class to instantiate 
    * @return The new object instance
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @see DiContainer
    */
    public Object instantiate(Class<?> inClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        DiContext context = new DiContext(null, inClass, null, "", null, false, this);

        return this.instantiate(inClass, context);
    }

    /**
    * Resolves a single instance of a class
    *
    * @param searchClass The class to search for
    * @return The instance of the class
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @see DiContainer
    */
    public Object resolve(Class<?> searchClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        DiContext context = new DiContext(null, searchClass, null, "", null, false, this);

        return this.resolve(searchClass, context);
    }

    /**
    * Resolves a single instance of a class with a specific id
    *
    * @param searchClass The class to search for
    * @param id The id to search for
    * @return The instance of the class
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @see DiContainer
    */
    public Object resolveId(Class<?> searchClass, String id) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        DiContext context = new DiContext(id, searchClass, null, "", null, false, this);

        return this.resolve(searchClass, context);
    }

    /**
    * Resolves all instances of a class
    *
    * @param searchClass The class to search for
    * @return The instance of the class
    * @throws IllegalAccessException Can be caused by the reflection, out of my control
    * @throws InvocationTargetException Can be caused by the reflection, out of my control
    * @throws InstantiationException Can be caused if the Container fails to instantiate a class
    * @see DiContainer
    */
    public List<Object> resolveAll(Class<?> searchClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        DiContext context = new DiContext(null, searchClass, null, "", null, false, this);

        return this.resolveAll(searchClass, context);
    }

    /**
    * Tries to resolve a single instance of a class
    *
    * @param searchClass The class to search for
    * @return The instance of the class or null
    * @see DiContainer
    */
    public Object tryResolve(Class<?> searchClass) {
        DiContext context = new DiContext(null, searchClass, null, "", null, false, this);

        try {
            return this.resolve(searchClass, context);
        } catch (Exception e) {
            return null;
        }
    }

    /**
    * Tries to resolve a single instance of a class with a specific id
    *
    * @param searchClass The class to search for
    * @param id The id to search for
    * @return The instance of the class or null
    * @see DiContainer
    */
    public Object tryResolveId(Class<?> searchClass, String id) {
        DiContext context = new DiContext(id, searchClass, null, "", null, false, this);

        try {
            return this.resolve(searchClass, context);
        } catch (Exception e) {
            return null;
        }
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
        DiRuleBuilder diRuleBuilder = new DiRuleBuilder(this);

        diRuleBuilder.bind(inClasses);

        return diRuleBuilder;
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
        DiRuleBuilder diRuleBuilder = new DiRuleBuilder(this);

        diRuleBuilder.bindInstance(instance);

        return diRuleBuilder;
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
        DiRuleBuilder diRuleBuilder = new DiRuleBuilder(this);

        diRuleBuilder.bindInstances(instances);

        return diRuleBuilder;
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
    public DiRuleBuilder bindInterfacesTo(Class<?> inClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        DiRuleBuilder diRuleBuilder = new DiRuleBuilder(this);

        diRuleBuilder.bindInterfacesTo(inClass);

        return diRuleBuilder;
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
    public DiRuleBuilder bindInterfacesAndSelfTo(Class<?> inClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        DiRuleBuilder diRuleBuilder = new DiRuleBuilder(this);

        diRuleBuilder.bindInterfacesAndSelfTo(inClass);

        return diRuleBuilder;
    }
}