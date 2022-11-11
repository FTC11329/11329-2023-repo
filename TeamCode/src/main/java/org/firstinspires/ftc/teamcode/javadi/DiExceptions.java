package org.firstinspires.ftc.teamcode.javadi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;

public class DiExceptions {
    public static class InstanceNotFoundException extends RuntimeException {
        Class<?> searchClass;
        DiContext context;
        List<DiRule> rules;
        WeakHashMap<UUID, Object> objectPool;

        public String toString() {
            String finalString = "Instance not found while resolving " + searchClass.getName();

            finalString += "\nInstance not found while resolving " + searchClass.getName();

            finalString += "\n- You might have forgotten to bind() the class before resolving it.";

            List<Class<?>> values = new ArrayList<>();
            context.memberClass = searchClass;
            
            for (DiRule rule : rules) {
                switch (rule.retrievalMode) {
                    case Resolve:
                    case Create:
                        if (rule.instanceClass != null && rule.instanceClass.getSimpleName().contains(searchClass.getName())) values.add(rule.instanceClass);
                        break;
                    case Return:
                        if (rule.targetObject != null && rule.targetObject.getClass().getSimpleName().contains(searchClass.getName())) values.add(rule.targetObject.getClass());
                        break;
                    default:
                        break;
                }
            }

            if (values.size() > 0) {
                finalString += "\n- Found " + ((values.size() > 1) ? "classes with similar names: " : "a class with a similar name: ");

                for (Class<?> similarClass : values) {
                    finalString += "\n| - " + similarClass.getName();
                }

                finalString += "\n| You might have imported the wrong class.";
            }

            return finalString;
        }

        public InstanceNotFoundException(Class<?> searchClass, DiContext context, List<DiRule> rules, WeakHashMap<UUID, Object> objectPool) {
            this.searchClass = searchClass;
            this.context = context;
            this.rules = rules;
            this.objectPool = objectPool;
            
            System.out.println(this.toString());
        }
    }
    public static class MultipleInstancesFoundException extends RuntimeException {
        Class<?> searchClass;
        DiContext context;
        List<DiRule> rules;
        WeakHashMap<UUID, Object> objectPool;

        public String toString() {
            String finalString = "Multiple instances found while resolving " + searchClass.getName();

            finalString += "\n- You might have accidentally duplicated a bind() statement";

            if (context.id == null || context.id.isEmpty()) {
                finalString += "\n- ID is blank, you might have forgotten to bind() with and ID";
            }

            return finalString;
        }

        public MultipleInstancesFoundException(Class<?> searchClass, DiContext context, List<DiRule> rules, WeakHashMap<UUID, Object> objectPool) {   
            this.searchClass = searchClass;
            this.context = context;
            this.rules = rules;
            this.objectPool = objectPool;
            
            System.out.println(this.toString());
        }
    }
    public static class IncompleteBindingException extends RuntimeException {
        DiRule rule; 
        boolean foundRecursion;

        public String toString() {
            String finalString = "";

            switch (rule.retrievalMode) {
                case Resolve:
                case Create:
                    if (rule.instanceClass == null) {
                        finalString = "An incomplete binding was found";
                    } else {
                        finalString = "An incomplete binding was found for " + rule.instanceClass.getName();
                    }
                    break;
                case Return:
                    if (rule.targetObject == null) {
                        finalString = "An incomplete binding was found";
                    } else {
                        finalString = "An incomplete binding was found for " + rule.targetObject.getClass().getName();

                        Object instance = rule.container.objectPool.get(rule.targetObject);

                        if (instance != null) finalString += "\n- No instance is set for resolution";
                    }

                    break;
                default:
                    break;
            }

            if (foundRecursion) finalString += "\n- Found potential recursion in resolution, you may have bound the class to itself";

            return finalString;
        }

        public IncompleteBindingException(DiRule rule, boolean foundRecursion) {        
            this.rule = rule;
            this.foundRecursion = foundRecursion;

            System.out.println(this.toString());
        }
    }
    public static class RuleBuilderException extends RuntimeException {
        String reason;

        public String toString() {
            return "A Rule Builder exception was thrown: \n" + reason;
        }

        public RuleBuilderException(String reason) {        
            this.reason = reason;
            
            System.out.println(this.toString());
        }
    }
}
