// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import static edu.wpi.first.units.Units.Seconds;

import java.io.ObjectInputFilter.Config;
import java.nio.file.Watchable;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

@Logged



public class Shooter extends SubsystemBase {

  private SparkMax motor10;
  private SparkMax motor20;
  /** Creates a new Intake. */
  public Shooter() {
  motor10 = new SparkMax(48, MotorType.kBrushless);
  motor20 = new SparkMax(49, MotorType.kBrushless);
     
  SparkMaxConfig motor10Config = new SparkMaxConfig();
  SparkMaxConfig motor20Config = new SparkMaxConfig();

  motor10Config
     .smartCurrentLimit(30)
     .idleMode(IdleMode.kCoast);
    
    motor10.configure (motor10Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      motor20Config.apply(motor10Config);
       

    motor20.configure(motor20Config , ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

     this.setDefaultCommand(
      new FunctionalCommand(()-> {motor10.set(0);motor20.set(0);},
      () -> {},
       (killed) -> {},
        () -> {return false;},
          this)
    );


  }
    

public Command Spit()

{
    return new ParallelCommandGroup(new InstantCommand(() -> motor10.set(-0.55)) , new InstantCommand(() -> motor20.set(-0.55), this)).withName("Shooter_shoot_Frooty_Loopy_Thingy").repeatedly();
    
}   
public Command stopspit()

{
    return new ParallelCommandGroup(new InstantCommand(() -> motor10.set(0)) , new InstantCommand(() -> motor20.set(0), this)).withName("stop_shoot_frooty_loopy_think");
    
}   

public Command doNotSpit()
{
 return new InstantCommand(() -> motor10.set(0), this);
}

public Command doSpit()
{
 return new InstantCommand(() -> motor10.set(1), this);
}
  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
    SmartDashboard.putData(this);
  }
  
}


