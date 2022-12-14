package org.firstinspires.ftc.teamcode.subsystems;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

public class PipelineTest extends OpenCvPipeline {
    public double x = 0;
    // Notice this is declared as an instance variable (and re-used), not a local variable


    @Override
    public void init(Mat firstFrame) {

    }

    @Override
    public Mat processFrame(Mat input) {
        // Because a submat is a persistent reference to a region of the parent buffer,
        // (which in this case is `input`) any changes to `input` will be reflected in
        // the submat (and vice versa).
        x = 1;
        return input;
    }
}
