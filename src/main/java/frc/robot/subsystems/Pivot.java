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
import frc.robot.commands.SparkPosition;
import edu.wpi.first.math.Matrix;


@Logged
public class Pivot extends SubsystemBase {
  private SparkMax motor1;
  private SparkMax motor3;

  public Command joystickControl(Supplier<Double> input)
  {
    return new RepeatCommand
      (
        new InstantCommand(() -> motor1.set((Math.abs(input.get()) < Constants.stickDeadband) ? 0 : input.get()), this)
      );
  }

  //Creates a new Pivot.
  public Pivot() {

    motor1 = new SparkMax(52, MotorType.kBrushless);
    motor3 = new SparkMax(51, MotorType.kBrushless);
    


    SparkMaxConfig motor1Config = new SparkMaxConfig();
    SparkMaxConfig motor3Config = new SparkMaxConfig();

    motor1Config
    .smartCurrentLimit(30)
    .idleMode(IdleMode.kBrake);

    motor1.configure(motor1Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    motor3Config
    .smartCurrentLimit(30)
    .idleMode(IdleMode.kBrake)
    .follow(motor1.getDeviceId(), true);
    
    motor3.configure(motor3Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    

this.setDefaultCommand(
  
new FunctionalCommand(
() -> {motor1.set(0);}, 
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



public Command postitionToIndex()
{
  return new SparkPosition(motor1, 47, 5, this)
    .withName("Postition to shoot Frooty Loopy Thingy");
    
}
public Command level1(){return new SparkPosition(motor1, 1, 0.5, this);}
public Command level2(){return new SparkPosition(motor1, 2, 0.5, this);}
public Command level3(){return new SparkPosition(motor1, 3, 0.5, this);}
public Command level4(){return new SparkPosition(motor1, 4, 0.5, this);}
}
