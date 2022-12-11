/*
 * Copyright (c) 2021 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Disabled
@TeleOp(name="EncoderTest")
public class TeleOpEncoderTest extends LinearOpMode
{
    DcMotorEx encoderMotorFL;
    DcMotorEx encoderMotorFR;
    DcMotorEx encoderMotorBL;
    DcMotorEx encoderMotorBR;

    @Override
    public void runOpMode() {
        encoderMotorFL = hardwareMap.get(DcMotorEx.class, "frontLeft");
        encoderMotorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        encoderMotorFR = hardwareMap.get(DcMotorEx.class, "frontRight");
        encoderMotorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        encoderMotorBL = hardwareMap.get(DcMotorEx.class, "backLeft");
        encoderMotorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        encoderMotorBR = hardwareMap.get(DcMotorEx.class, "backRight");
        encoderMotorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("EncoderValue 1: ",encoderMotorFL.getCurrentPosition());
            telemetry.addData("EncoderValue 2: ",encoderMotorFR.getCurrentPosition());
            telemetry.addData("EncoderValue 3: ",encoderMotorBL.getCurrentPosition());
            telemetry.update();
        }
    }
}
