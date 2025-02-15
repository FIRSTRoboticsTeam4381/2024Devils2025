// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import static edu.wpi.first.units.Units.Seconds;

import java.io.ObjectInputFilter.Config;
import java.lang.ModuleLayer.Controller;
import java.nio.file.Watchable;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

@Logged
public class Intake extends SubsystemBase {

  private SparkMax intakemotor1;
  private SparkMax intakemotor2;
  /** Creates a new Intake. */
  public Intake() {
  intakemotor1 = new SparkMax(45, MotorType.kBrushless);
  intakemotor2 = new SparkMax(46, MotorType.kBrushless);
     
  SparkMaxConfig intakemotor1Config = new SparkMaxConfig();
  SparkMaxConfig intakemotor2Config = new SparkMaxConfig();

  intakemotor1Config
     .smartCurrentLimit(30)
     .idleMode(IdleMode.kCoast);
    
    intakemotor1.configure (intakemotor2Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      intakemotor2Config.apply(intakemotor1Config);
       intakemotor2Config.follow(intakemotor1);

    intakemotor2.configure(intakemotor2Config , ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

     this.setDefaultCommand
     (
      new FunctionalCommand(()-> {intakemotor1.set(0);},
      () -> {},
       (killed) -> {},
        () -> {return false;},
          this)
    );
  }
 

public Command Take()

{

    return new InstantCommand(() -> intakemotor1.set(-0.5), this).repeatedly();

}
  
public Command stopspinningthing()

{
    return new InstantCommand(() -> intakemotor1.set(0), this);
}

  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
    SmartDashboard.putData(this);
  }
  
}
