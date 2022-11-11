package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.javadi.DiContainer;
import org.firstinspires.ftc.teamcode.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.jfinite.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.teleop.TeleopBase;
import org.firstinspires.ftc.teamcode.teleop.TeleopState;

public class TeleopDrive implements DiInterfaces.IInitializable, DiInterfaces.ITickable {
    @DiContainer.Inject()
    Telemetry telemetry;
    @DiContainer.Inject()
    Drivetrain drivetrain;
    @DiContainer.Inject()
    Slides slides;
    @DiContainer.Inject(id = "gamepad1")
    public Gamepad gamepad1;
    @DiContainer.Inject(id = "gamepad2")
    public Gamepad gamepad2;
    @DiContainer.Inject()
    public Arm arm;
    @DiContainer.Inject()
    public Claw claw;

    //@DiContainer.Inject()
    //public StateMachine<TeleopState> stateMachine;


    @Override
    public void onInitialize() {
        TeleopBase.stateMachine.setTransitionCondition(TeleopState.ENTRY, TeleopState.GRABBING, () -> true);
        TeleopBase.stateMachine.setTransitionCondition(TeleopState.GRABBING, TeleopState.GRABBED, () -> gamepad2.right_bumper);
        TeleopBase.stateMachine.setTransitionCondition(TeleopState.GRABBED, TeleopState.GRABBING, () -> gamepad2.right_bumper);
        TeleopBase.stateMachine.setTransitionCondition(TeleopState.GRABBED, TeleopState.RAISING, () -> gamepad2.a);
        TeleopBase.stateMachine.setTransitionCondition(TeleopState.RAISING, TeleopState.RAISING2, () -> true);
        TeleopBase.stateMachine.setTransitionCondition(TeleopState.RAISING2, TeleopState.PLACING, () -> gamepad2.b);
        TeleopBase.stateMachine.setTransitionCondition(TeleopState.PLACING, TeleopState.RETRACTING, () -> gamepad2.right_bumper);
        TeleopBase.stateMachine.setTransitionCondition(TeleopState.RETRACTING, TeleopState.RETRACTING2, () -> true);
        TeleopBase.stateMachine.setTransitionCondition(TeleopState.RETRACTING2, TeleopState.GRABBING, () -> gamepad2.right_bumper);

        //---------------------------------------------------
        TeleopBase.stateMachine.addBehaviour(TeleopState.ENTRY, () -> claw.setClawPower(RobotConfig.Claw.handStartingPosition));
        TeleopBase.stateMachine.addBehaviour(TeleopState.RAISING, () -> slides.goToPosition(RobotConfig.Slides.Positions.SafePosition));
        TeleopBase.stateMachine.addLoopedBehaviour(TeleopState.RAISING2, () -> slides.moveSlides(gamepad2.left_stick_y), () -> slides.moveSlides(0));
        TeleopBase.stateMachine.addBehaviour(TeleopState.RETRACTING, () -> slides.goToPosition((RobotConfig.Slides.Positions.SafePosition)));

        TeleopBase.stateMachine.addBehaviour(TeleopState.GRABBING, () -> claw.ungrab());
        TeleopBase.stateMachine.addBehaviour(TeleopState.GRABBED, () -> claw.grab());
        TeleopBase.stateMachine.addBehaviour(TeleopState.RAISING, () -> claw.ungrab());

        TeleopBase.stateMachine.addLoopedBehaviour(TeleopState.PLACING, () -> arm.setArmSpeed(gamepad2.left_stick_x), () -> arm.setArmSpeed(0));
    }

    @Override
    public void onTick() {
        TeleopBase.stateMachine.update();
        double vertical = -gamepad1.left_stick_y;
        double horizontal = -gamepad1.left_stick_x;
        double rotational = gamepad1.right_stick_x;
        telemetry.addData("Current state", TeleopBase.stateMachine.getState());
        telemetry.update();
        /*double upPower = gamepad2.left_trigger;
        double downPower = gamepad2.right_trigger;
        boolean grab = gamepad2.right_bumper;
        double armPower = -gamepad2.left_stick_x;
        double handPower = gamepad2.right_stick_x;
        //doubles reading for probably more accuracy or something because java idk
        slides.moveSlides(upPower - downPower);

        arm.setArmSpeed(armPower);
        claw.setClawPower(handPower);
        if(grab){claw.grab();}
        else {claw.ungrab();}*/
        drivetrain.MecanumDrive(vertical, horizontal, rotational, 0.7);
    }

}
//Teagan was defenetly here. ya totally shure allen
/*
russian jets at N5.32234791 W2.3138492 CHEAP
192.168.4.382.930.171
russian jets for $5? satisfactory!
somebody once told me that the world was gonna roll me and i ain't the charpest tool in the shed and she was looking kinda dumb with a finger and a thumb in the shape of an l on her forehead and the years start coming and they don't stop coming and fed to the rures and you hit the ground ruunning. didnt make sense not to live for fun, your brain gets smart Iff you see this
 */