package org.firstinspires.ftc.teamcode.auto.kennan;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;

public class April {
    static final double FEET_PER_METER = 3.28084;
    public int marker = 0;
    public CameraSubsystem camera;
    private double fx = 578.272;
    private double fy = 578.272;
    private double cx = 402.145;
    private double cy = 221.506;
    // UNITS ARE METERS
    private double tagsize = 0.166;
    private int ID_TAG_OF_INTEREST = 18; // Tag ID 18 from the 36h11 family
    private CoolerAprilTagDetectionPipeline coolerAprilTagDetectionPipeline;

    public April(HardwareMap hardwareMap) {
        camera = new CameraSubsystem(hardwareMap);
        coolerAprilTagDetectionPipeline =
                new CoolerAprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        camera.setPipeline(coolerAprilTagDetectionPipeline);
    }

    public ParkLocation getAprilTag() {
        ArrayList<AprilTagDetection> currentDetections = coolerAprilTagDetectionPipeline.getLatestDetections();
        if (currentDetections.size() > 0) {
            marker = currentDetections.get(0).id;
        }
 
        if (marker == 0) {
            return ParkLocation.LEFT;
        } else if (marker == 1) {
            return ParkLocation.CENTER;
        } else if (marker == 2) {
            return ParkLocation.RIGHT;
        } else {
            return ParkLocation.UNKNOWN;
        }
    }

    public void close() {
        camera.close();
    }

    public void openDashboardStream() {
        camera.openDashboardStream();
    }

    public void closeDashboardStream() {
        camera.closeDashboardStream();
    }
}
