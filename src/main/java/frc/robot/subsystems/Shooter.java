// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkMax;

import static edu.wpi.first.units.Units.Seconds;

import java.io.ObjectInputFilter.Config;
import java.nio.file.Watchable;

import com.revrobotics.spark.SparkBase.ControlType;
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
     .smartCurrentLimit(60)
     .idleMode(IdleMode.kCoast);
    motor10Config.closedLoop.p(0.001).i(0).d(0.001).velocityFF(0.00015);
    motor10.configure (motor10Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      motor20Config.apply(motor10Config);
      motor20Config
      .smartCurrentLimit(60)
      .idleMode(IdleMode.kCoast);
     motor20Config.closedLoop.p(0.0005).i(0).d(0.015).velocityFF(0.00015);

    motor20.configure(motor20Config , ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

     this.setDefaultCommand(
      new FunctionalCommand(()-> {motor10.set(0);motor20.set(0);},
      () -> {},
       (killed) -> {},
        () -> {return false;},
          this)
    );

    motor10.getEncoder().getVelocity();
    motor20.getEncoder().getVelocity();


  }
    

public Command Spit()

{
    return new ParallelCommandGroup(new InstantCommand(() -> motor10.set(-0.65)) , new InstantCommand(() -> motor20.set(-0.50), this)).withName("Actually Shoot Things").repeatedly();
    
}   
public Command stopspit()

{
    return new ParallelCommandGroup(new InstantCommand(() -> motor10.set(0)) , new InstantCommand(() -> motor20.set(0), this)).withName("Stop Shooting Things");
    
}   

public void setVelocity(double RPM)

{
    motor10.getClosedLoopController().setReference(-RPM, ControlType.kVelocity, ClosedLoopSlot.kSlot0);
    motor20.getClosedLoopController().setReference(-RPM*0.3, ControlType.kVelocity, ClosedLoopSlot.kSlot0);
}   
public Command shooter(double RPM)
{
 return new FunctionalCommand(() -> setVelocity(RPM), () -> {}, (interuppted) -> setPresent(0), ()-> {return false;} , this);
}

public void setPresent( double RPM)
{
  motor10.set(RPM);
  motor20.set(RPM);

}
/*public Command doNotSpit()
{
 return new InstantCommand(() -> motor10.set(0), this);
}

public Command doSpit()
{
 return new InstantCommand(() -> motor10.set(1), this);
}*/
  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
    SmartDashboard.putData(this);
  }
  
}


