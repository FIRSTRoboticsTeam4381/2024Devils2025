// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Indexter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;

/** Add your docs here. */
public class CommandsAsWell 
{ public Pivot pivot;
    public Indexter indexter;
    public Intake intake;
    public Shooter shooter;
    public CommandsAsWell(Indexter indexter, Pivot pivot, Intake intake, Shooter shooter)
    {
        this.pivot = pivot;
        this.indexter = indexter;
        this.intake = intake;
        this.shooter = shooter;

    }
  
 


 public ParallelCommandGroup GrabFrootLoop()
 { 
    return new ParallelCommandGroup(new InstantCommand(() -> pivot.postitionToIndex()),
    new InstantCommand(() -> intake.Take()),
    new InstantCommand(() -> indexter.indexTheFrootLoop()), 
    new InstantCommand(() -> shooter.Spit()));
      
 }
 
}














