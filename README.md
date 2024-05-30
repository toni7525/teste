package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
@TeleOp()
public class testdrive extends OpMode {
    private DcMotor FRWheel;
    private DcMotor FLWheel;
    private DcMotor BRWheel;
    private DcMotor BLWheel;
    @Override
    public void init() {
        FRWheel = hardwareMap.get(DcMotor.class, "FrontRightWheel");
        FLWheel = hardwareMap.get(DcMotor.class, "FrontLeftWheel");
        BRWheel = hardwareMap.get(DcMotor.class, "BackRightWheel");
        BLWheel = hardwareMap.get(DcMotor.class, "BackLeftWheel");
    }
    @Override
    public void loop() {
        float x,y,rot;
        x = gamepad1.left_stick_x;
        y = gamepad1.left_stick_y;
        rot = gamepad1.right_stick_x;
        boolean slow;
        slow = gamepad1.x;
        if(slow) {
            move(x/2, y/2, rot);
        }else{
            move(x, y, rot); 
        }
    }

    //move forwars all 1
    //move right -fr,bl
    //move left -fl,br
    //rotate left -fl.bl
    public void move(float moveleft,float moveforward,float rotate){
        float fr,fl,br,bl;
        fr = moveforward - moveleft + rotate;
        fl = moveforward + moveleft - rotate;
        br = moveforward + moveleft + rotate;
        bl = moveforward - moveleft - rotate;

        float biggest = ABS(fr);
        if(biggest<fl)biggest=ABS(fl);
        if(biggest<bl)biggest=ABS(bl);
        if(biggest<br)biggest=ABS(br);
        if(biggest >1) {
            float divider = 1 / biggest;
            fr = fr * divider;
            fl = fl * divider;
            bl = bl * divider;
            br = br * divider;
        }
        FRWheel.setPower(fr);
        FLWheel.setPower(fl);
        BRWheel.setPower(br);
        BLWheel.setPower(bl);
    }
    float ABS(float nr){
        if(nr<0) {
            return (-nr);
        }else {
            return nr;
        }
    }
}
