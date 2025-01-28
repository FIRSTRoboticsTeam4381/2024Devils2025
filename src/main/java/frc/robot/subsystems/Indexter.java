// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class Indexter extends SubsystemBase {
  private SparkMax motor1;
  private SparkMax motor3;
  private DigitalInput brakeBeam1;
  private DigitalInput brakeBeam2;
  /** Creates a new Indexter. */
  public Indexter() {

    motor1 = new SparkMax(50, MotorType.kBrushless);
    brakeBeam1 = new DigitalInput(1);
    brakeBeam2 = new DigitalInput(2);
    
    


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


public Command PositioningFrootLoop()
{
  return new SequentialCommandGroup
    ( new InstantCommand(() -> motor1.set(0.4)),
      new WaitUntilCommand(() -> !brakeBeam1.get()),
      new InstantCommand(() -> motor1.set(0))
    ).withName("Obtaining Froot Loopy Thingy Thingy");
}


}
