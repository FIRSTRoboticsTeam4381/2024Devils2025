// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import java.util.function.Supplier;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;


@Logged
public class Hang extends SubsystemBase {
  private SparkMax motorHang1;
  private SparkMax motorHang2;
  public Command joystickControl(Supplier<Double> input)
  {
    return new RepeatCommand
      (
        new InstantCommand(() -> {
          motorHang1.set((Math.abs(-input.get()) < Constants.stickDeadband) ? 0 : -input.get()*0.5);
          if (motorHang2.getAbsoluteEncoder().getPosition() > 0.75 && -input.get() > 0){motorHang1.set(0);} 
          if (motorHang2.getAbsoluteEncoder().getPosition() < 0.1 && -input.get() < 0){motorHang1.set(0);} 

        }, this)
      );
  }

  //Creates a new Pivot.
  public Hang() {

    motorHang1 = new SparkMax(53, MotorType.kBrushless);
    motorHang2 = new SparkMax(54, MotorType.kBrushless);
    


    SparkMaxConfig motorHang1Config = new SparkMaxConfig();
    SparkMaxConfig motorHang2Config = new SparkMaxConfig();


    motorHang1Config
    .smartCurrentLimit(30)
    .idleMode(IdleMode.kCoast);
    motorHang1Config.absoluteEncoder.inverted(true).zeroOffset(0);

    motorHang1.configure(motorHang1Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    motorHang2Config.apply(motorHang1Config);
    motorHang2Config.follow(motorHang1, true);

    motorHang2.configure(motorHang2Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    motorHang2.getAbsoluteEncoder().getPosition();
    

this.setDefaultCommand(
  
new FunctionalCommand(
() -> {motorHang1.set(0);}, 
() -> {}, 
(killed) -> {}, 
() -> {return false;}, 
this)
);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putData(this);
  }





public Command moveMotors()
{
  return new SequentialCommandGroup
    ( new InstantCommand(() -> motorHang1.set(1), this),
      new InstantCommand(() -> motorHang2.set(1), this),
      new WaitCommand(1),
      new InstantCommand(() -> motorHang1.set(-1)),
      new InstantCommand(() -> motorHang2.set(-1))
    ).withName("Moving Motors To Imagine Hanging");

}


}
