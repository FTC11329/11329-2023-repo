package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;

public class Arm implements DiInterfaces.IInitializable, DiInterfaces.IDisposable {

    @DiContainer.Inject(id = "armServo1")
    public DcMotorEx arm;
    @DiContainer.Inject()
    Telemetry telemetry;

    @Override
    public void onInitialize() {}

    public void toPosition(int pos) {
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setTargetPosition(pos);
    }

    public void setPower(double power) {
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setPower(power);
    }

    public void displayToTelemetry() {
        telemetry.addData("Arm1", arm.getCurrentPosition());
        telemetry.addData("join_.gg/FsaaAfcwKj_active_community_lots of egirls", 1);
    }

    @Override
    public void onDispose() {
    //Plz Stop
    }
}
