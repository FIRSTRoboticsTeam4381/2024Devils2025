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




public class Pivot extends SubsystemBase {
  private SparkMax motor1;
  private SparkMax motor3;
  public Command joystickControl(Supplier<Double> input)
{
return new RepeatCommand
(
  new InstantCommand(() -> motor1.set(input.get()), this)
);
}
  //Creates a new Pivot.
  public Pivot() {

    motor1 = new SparkMax(50, MotorType.kBrushless);
    motor3 = new SparkMax(51, MotorType.kBrushless);
    


    SparkMaxConfig motor1Config = new SparkMaxConfig();

    motor1Config
    .smartCurrentLimit(30)
    .idleMode(IdleMode.kCoast);

    motor1.configure(motor1Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    

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
  return new SequentialCommandGroup
    ( new InstantCommand(() -> motor1.set(0.4)),
      new WaitCommand(1),
      new InstantCommand(() -> motor1.set(0))
    ).withName("Postition to shoot Frooty Loopy Thingy");
    
}


}
