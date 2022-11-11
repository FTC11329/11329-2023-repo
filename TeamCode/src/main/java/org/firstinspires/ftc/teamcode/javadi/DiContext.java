package org.firstinspires.ftc.teamcode.javadi;

public class DiContext {
    public String id;
    public Class<?> targetClass;
    public Object objectInstance;
    public String memberName;
    public Class<?> memberClass;
    public boolean optional;
    public DiContainer container;

    /**
    * Initializes a new Context
    *
    * @param id The current Id we're using
    * @param targetClass The class we're trying to find
    * @param objectInstance The instance of the object we're trying to fill, might be null
    * @param memberName The name of the Field we're trying to fill
    * @param memberClass The class of the Field we're trying to fill
    * @param optional Whether or not we're in an optional context
    * @param container The Container we're targeting
    * @see DiContainer
    */
    public DiContext(String id, Class<?> targetClass, Object objectInstance, String memberName, Class<?> memberClass, boolean optional, DiContainer container) {
        this.id = id;
        this.targetClass = targetClass;
        this.objectInstance = objectInstance;
        this.memberName = memberName;
        this.memberClass = memberClass;
        this.optional = optional;
        this.container = container;
    }
}
