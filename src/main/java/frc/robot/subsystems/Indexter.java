// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.pathplanner.lib.pathfinding.LocalADStar.GridPosition;
import com.revrobotics.spark.SparkMax;
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
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

@Logged
public class Indexter extends SubsystemBase {
  private SparkMax motor1;

  private DigitalInput brakeBeam1;
  private DigitalInput brakeBeam2;
  /** Creates a new Indexter. */
  public Indexter() {

    motor1 = new SparkMax(47, MotorType.kBrushless);
    brakeBeam2 = new DigitalInput(8);
    brakeBeam1 = new DigitalInput(9);
    
    


    SparkMaxConfig motor1Config = new SparkMaxConfig();

    motor1Config
    .smartCurrentLimit(40)
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
    SmartDashboard.putBoolean("Beam 1", brakeBeam1.get());
    SmartDashboard.putBoolean("Beam 2", !brakeBeam2.get());
  }


public Command indexNote()
{return new SequentialCommandGroup
  ( new InstantCommand(() -> motor1.set(0.4), this),
    new WaitUntilCommand(() -> brakeBeam1.get()),
    new InstantCommand(() -> motor1.set(0), this)
  );


}


public Command Take()

{
    return new InstantCommand(() -> motor1.set(0.52), this).repeatedly();
}
public Command notTake()

{
    return new InstantCommand(() -> motor1.set(0), this).repeatedly();
}
public Command breakBeam(int brakeBeam)
{
return new FunctionalCommand(
  () -> motor1.set(0.5),
  () -> {},
  (interupted) -> motor1.set(0),
   () -> {if(brakeBeam == 1)
   {return brakeBeam1.get();}
   else{return !brakeBeam2.get();}},
   this);
   
}






}



