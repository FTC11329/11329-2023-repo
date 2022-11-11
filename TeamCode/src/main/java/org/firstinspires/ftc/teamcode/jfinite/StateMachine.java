package org.firstinspires.ftc.teamcode.jfinite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class StateMachine<T extends Enum<T>> {

    protected T currentState;

    protected HashMap<T, List<StateBehaviour>> behaviours = new HashMap<>();
    protected HashMap<T, List<StateBehaviour>> loopedBehaviours = new HashMap<>();
    protected HashMap<T, List<StateBehaviour>> cleanupBehaviours = new HashMap<>();

    protected HashMap<T, HashMap<T, Conditions>> transitions = new HashMap<>();

    /*
     * Allows a subsystem to register a method to be called when the <pre>EventEmitter</pre> transitions to state.
     *
     * @param initialState The initial state for the <pre>EventEmitter</pre> to start in
     */
    public StateMachine(T initialState) {
        currentState = initialState;
    }

    /*
     * Allows a subsystem to register a method to be called when the <pre>EventEmitter</pre> transitions to state.
     *
     * @param  state The target state to wait for
     * @param callee The method to call on transition
     */
    public void addBehaviour(T state, StateBehaviour callee) {
        behaviours.computeIfAbsent(state, k -> new ArrayList<>());

        behaviours.get(state).add(callee);
    }

    public void addLoopedBehaviour(T state, StateBehaviour loopedBehaviour) {
        loopedBehaviours.computeIfAbsent(state, k -> new ArrayList<>());

        loopedBehaviours.get(state).add(loopedBehaviour);
    }

    public void addLoopedBehaviour(T state, StateBehaviour loopedBehaviour, StateBehaviour cleanupBehaviour) {
        loopedBehaviours.computeIfAbsent(state, k -> new ArrayList<>());
        cleanupBehaviours.computeIfAbsent(state, k -> new ArrayList<>());

        loopedBehaviours.get(state).add(loopedBehaviour);
        cleanupBehaviours.get(state).add(cleanupBehaviour);
    }

    /*
     * Allows the code to add a condition to describe a transition between two states.
     *
     * @param activeState The state to wait for before checking <pre>condition</pre>
     * @param targetState The state to transition to when <pre>condition</pre> goes true
     * @param   condition The condition to check before transitioning to <pre>targetState</pre>
     */
    public void setTransitionCondition(T activeState, T targetState, Conditions condition) {
        transitions.computeIfAbsent(activeState, k -> new HashMap<>());

        transitions.get(activeState).put(targetState, condition);
    }

    /*
     * The function to periodically call in order to allow the <pre>EventEmitter</pre> to make transitions.
     */
    public void update() {
        // Check transitions
        HashMap<T, Conditions> activeTransitions = transitions.get(currentState);

        if (activeTransitions != null) {
            Set<T> conditions = activeTransitions.keySet();

            if (conditions != null) {
                for (T event : conditions) {
                    if (activeTransitions.get(event).check()) {
                        transitionTo(event);
                        return;
                    }
                }
            }
        }

        // Run Looped Behaviours
        if (loopedBehaviours.get(currentState) != null) {
            for (StateBehaviour loopedBehaviour : loopedBehaviours.get(currentState)) {
                loopedBehaviour.call();
            }
        }
    }

    private void transitionTo(T state) {
        if (cleanupBehaviours.get(currentState) != null) {
            for (StateBehaviour cleanup : cleanupBehaviours.get(currentState)) {
                cleanup.call();
            }
        }

        currentState = state;

        if (behaviours.get(state) != null) {
            for (StateBehaviour method : behaviours.get(state)) {
                try {
                    method.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // If you read this your cringe why are you reading this stinky library :)
    /*
     * Gets the current state of the <pre>EventEmitter</pre>
     *
     * @returns The current state of the <pre>EventEmitter</pre>
     */
    public T getState() {
        return currentState;
    }
}
