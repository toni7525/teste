package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static java.lang.Math.abs;
import static java.lang.Math.cosh;
import static java.lang.Math.sinh;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;


@TeleOp()
public class testdrive extends OpMode {
    float dirx = 0;
    float diry = 1;
    float latdirx = 1;
    float latdiry = 0;
    final float rotationspeed=0.5f;
    private DcMotor FRWheel;
    private DcMotor FLWheel;
    private DcMotor BRWheel;
    private DcMotor BLWheel;
    float angle = 0;

    @Override
    public void init() {
        FRWheel = hardwareMap.get(DcMotor.class, "FrontRightWheel");
        FLWheel = hardwareMap.get(DcMotor.class, "FrontLeftWheel");
        BRWheel = hardwareMap.get(DcMotor.class, "BackRightWheel");
        BLWheel = hardwareMap.get(DcMotor.class, "BackLeftWheel");
        FRWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    @Override
    public void loop() {
        telemetry.addData("angle",angle);
        float x ,y ,rot;
        x = gamepad1.left_stick_x;
        y = gamepad1.left_stick_y;
        x = x * x * x;
        y = y * y * y;
        rot = gamepad1.right_stick_x;
        angle +=rot * rotationspeed;
        rotatevec2(rot * rotationspeed);
        move((y) * dirx + (x) * latdirx, (y) * diry + (x) * latdiry, rot);
    }

    //move forwars all 1
    //move right -fr,bl
    //move left -fl,br
    //rotate left -fl.bl
    void move(float moveleft,float moveforward,float rotate){
        float fr,fl,br,bl;
        fr = moveforward - moveleft + rotate;
        fl = moveforward + moveleft - rotate;
        br = moveforward + moveleft + rotate;
        bl = moveforward - moveleft - rotate;

        float biggest = abs(fr);
        if(biggest<fl)biggest=abs(fl);
        if(biggest<bl)biggest=abs(bl);
        if(biggest<br)biggest=abs(br);
        if(biggest >1) {
            fr = fr / biggest;
            fl = fl / biggest;
            bl = bl / biggest;
            br = br / biggest;
        }
        FRWheel.setPower(fr);
        FLWheel.setPower(fl);
        BRWheel.setPower(br);
        BLWheel.setPower(bl);
    }

    void rotatevec2(float amount) {
        float OldDirx = dirx;
        dirx = dirx * (float)cosh(amount) - diry *(float)sinh(amount);
        diry = OldDirx * (float)sinh(amount) + diry * (float)cosh(amount);
        float latOldDirx = latdirx;
        latdirx = latdirx * (float)cosh(amount) - latdiry *(float)sinh(amount);
        latdiry = latOldDirx * (float)sinh(amount) + latdiry * (float)cosh(amount);
    }

    //public void moveto(/*meters*/float x,float y){

    //}
}
