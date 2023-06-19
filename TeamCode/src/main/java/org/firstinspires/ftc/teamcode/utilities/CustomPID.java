package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.util.ElapsedTime;

public class CustomPID {
    /*
     * Proportional Integral Derivative Controller w/ Low pass filter and anti-windup
     */
    private double Kp = 1;
    private double Ki = 0;
    private double Kd = 0;
    private double Kf = 0;

    private double reference = 0;
    private double lastReference = reference;

    private double integral = 0;

    private double lastError = 0;

    private double a = 0.8; // a can be anything from 0 < a < 1
    private double lastFilterEstimate = 0;
    private double maxIntegral = 0.25;
    private ElapsedTime timer;



    //'fancy' toggles some other features that may aid in performance
    //private boolean fancy = false;
    // Elapsed timer class from SDK, please use it, it's epic

    public CustomPID(double Kp, double Ki,double  Kd){
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.timer = new ElapsedTime();
    }
    public CustomPID(double Kp, double Ki,double  Kd, double Kf){
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.Kf = Kf;
        this.timer = new ElapsedTime();
    }
    public void setTargetPosition(double desiredPosition){
        //Sets the postion the motor should go to
        this.lastReference = reference;
        this.integral = 0;
        this.reference = desiredPosition;
    }
    public void setMaxIntegral(double maxIntegral){
        this.maxIntegral = maxIntegral;
    }

    public double  getPIDoutput(double currentPosition){
        //Must be called every tick this function finds the estimated output given P, I, and D values
        double error = this.reference - currentPosition;
        //Find the Porportioanl, Integral, and derivative based on the PID values
        double derivative = (error-lastError)/timer.seconds();
        this.integral  =  this.integral + (error * timer.seconds());
        timer.reset();
        lastError = error;
        return this.Kp * error + Ki*integral + Kd* derivative;
    }
    //adds Feedforward
    public double getPIDfOutputNoAngle(double currentPosition){
        //Similar to getPIDoutput but adds feedforward based on cos() of currentAngle
        double error = this.reference - currentPosition;
        //Find the Porportioanl, Integral, and derivative based on the PID values
        double derivative = (error-lastError)/timer.seconds();
        this.integral  =  this.integral + (error * timer.seconds());
        timer.reset();
        lastError = error;
        return this.Kp * error + Ki*integral + Kd* derivative + this.Kf;
    }
    public double getPIDfOutput(double currentPosition, double currentAngle){
        //Similar to getPIDoutput but adds feedforward based on cos() of currentAngle
        double error = this.reference - currentPosition;
        //Find the Porportioanl, Integral, and derivative based on the PID values
        double derivative = (error-lastError)/timer.seconds();
        this.integral  =  this.integral + (error * timer.seconds());
        timer.reset();
        lastError = error;
        return this.Kp * error + Ki*integral + Kd* derivative + Math.cos(Math.toRadians(currentAngle))*this.Kf;
    }
    public double getPIDfOutputFancy(double currentPosition, double currentAngle){
        /*adds:
        Integral Sum cap *
        integral reset *
        integral stop
        filtered derivative *
         */

        //Similar to getPIDoutput but adds feedforward based on cos() of currentAngle
        double error = this.reference - currentPosition;

        double deltaError = (error - lastError);
        //Add lowpass filter to derivative to (hopefully) filter out error
        double currentFilterEstimate = (this.a * this.lastFilterEstimate) + (1+this.a) * deltaError;
        this.lastFilterEstimate = currentFilterEstimate;
        //Find the Porportioanl, Integral, and derivative based on the PID values
        double derivative = currentFilterEstimate/timer.seconds();
        this.integral  =  this.integral + (error * timer.seconds());
        if(integral*this.Ki> this.maxIntegral){
            integral = this.maxIntegral/this.Ki;
        }
        else if(integral * this.Ki< this.maxIntegral){
            integral = -this.maxIntegral/this.Ki;
        }
        if(reference != lastReference){
            integral = 0;
        }

        timer.reset();
        lastError = error;
        lastReference = this.reference;
        return this.Kp * error + Ki*integral + Kd* derivative + Math.cos(currentAngle)*this.Kf;

    }
}

