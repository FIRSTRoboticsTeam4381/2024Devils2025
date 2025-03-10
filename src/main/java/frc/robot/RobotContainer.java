// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.Supplier;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Autos;
import frc.robot.commands.CommandsAsWell;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.Hang;
import frc.robot.subsystems.Indexter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PhotonCam;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;

@Logged
public class RobotContainer {
  
  // Controllers
  //public final CommandXboxController driver = new CommandXboxController(0);
  //public final CommandXboxController specialist = new CommandXboxController(1);
  public final CommandPS4Controller driver = new CommandPS4Controller(0);
  public final CommandPS4Controller specialist = new CommandPS4Controller(1);

  //Auto Chooser
  SendableChooser<Autos.PreviewAuto> autoChooser = new SendableChooser<>();

  // Subsystems
  public final Swerve swerve = new Swerve();
  public final Indexter indexter =  new Indexter();
  public final Pivot pivot = new Pivot();
  public final Shooter shooter = new Shooter();
  public final Intake intake = new Intake();
  public final Hang hang = new Hang();
  
  public final CommandsAsWell commandsAsWell = new CommandsAsWell(specialist, indexter, pivot, intake, shooter, hang);


  //public final PhotonCam camA = new PhotonCam("Camera A", new Transform3d(new Translation3d(Units.inchesToMeters(-10.375), Units.inchesToMeters(-7.3125),  Units.inchesToMeters(8.5)), new Rotation3d(0,Math.PI/-6,Math.PI/-4-Math.PI)) );
  //public final PhotonCam camB = new PhotonCam("Camera B", new Transform3d(new Translation3d(Units.inchesToMeters(-10.375), Units.inchesToMeters(7.3125),  Units.inchesToMeters(8.5)), new Rotation3d(0,Math.PI/-6,Math.PI/4-Math.PI)) );
  //public final PhotonCam camC = new PhotonCam("Camera C", new Transform3d(new Translation3d(Units.inchesToMeters(-10.375), Units.inchesToMeters(7.3125),  Units.inchesToMeters(8.5)), new Rotation3d(0,Math.PI/-6,Math.PI/4-Math.PI)) );
  //public final PhotonCam camD = new PhotonCam("Camera D", new Transform3d(new Translation3d(Units.inchesToMeters(-10.375), Units.inchesToMeters(7.3125),  Units.inchesToMeters(8.5)), new Rotation3d(0,Math.PI/-6,Math.PI/4-Math.PI)) );


  // Constructor: set up the robot! 
  public RobotContainer() {
    robotReference = this;

    // Set default commands here




    // Set up autonomous picker
    // Add any autos you want to be able to select below
    autoChooser.setDefaultOption("None", Autos.none());
    autoChooser.addOption("Test", Autos.testAuto());
    autoChooser.addOption("Barge & Reef Auto", Autos.BargeandReefAuto());
    autoChooser.addOption("Autocool", Autos.AutoCool());
    autoChooser.addOption("Delete This Later", Autos.DeleteThisLater());
    // Add auto controls to the dashboard
    SmartDashboard.putData("Choose Auto:", autoChooser);
    SmartDashboard.putData(CommandScheduler.getInstance());
    autoChooser.onChange((listener) -> listener.showPreview());
    SmartDashboard.putNumber("Start Delay",0);

    NamedCommands.registerCommand(null, getAutonomousCommand());





    // Configure button bindings
    configureBindings();
  }

  private void configureBindings() {
    swerve.setDefaultCommand(new TeleopSwerve(swerve, 
            driver::getLeftY,
            driver::getLeftX,
          //interpolateJoystick(driver::getLeftY,0.05),
          //interpolateJoystick(driver::getLeftX,0.05), 
          interpolateJoystick (driver::getRightX,0.05),
             true, driver.L1()::getAsBoolean));

          pivot.setDefaultCommand(pivot.joystickControl(specialist::getLeftY));
          hang.setDefaultCommand(hang.joystickControl(specialist::getRightY));


          specialist.R3().toggleOnTrue(shooter.shooter(3000));

            specialist.cross().whileTrue(shooter.Shoot());
            //specialist.a().onFalse(shooter.doNotSpit());
            specialist.triangle().whileTrue(indexter.indexNote());
            specialist.square().toggleOnTrue(commandsAsWell.IntakeAndIndexNote());

           // specialist.povUp().onTrue(pivot.level4());
            specialist.povDown().onTrue(pivot.level1());
            specialist.povLeft().onTrue(pivot.level2());
           // specialist.povRight().onTrue(pivot.level3());

             specialist.touchpad().or(driver.touchpad()).onTrue
             (new InstantCommand(() -> CommandScheduler.getInstance().cancelAll()));

    driver.options()
            .onTrue(new InstantCommand(() -> swerve.zeroGyro())
            .alongWith(new InstantCommand(() -> swerve.resetOdometry(new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0))))));
  }

  public Command getAutonomousCommand() {
    double startDelay=SmartDashboard.getNumber("Start Delay", 0);
    return new SequentialCommandGroup( 
    new WaitCommand(startDelay), 
    new ScheduleCommand(autoChooser.getSelected().auto)); 
  }


  /**
   * Smooths joystic input for easier precice control without sacrificing full power.
   * @param in Input from joystic
   * @param deadzone Joystick deadzone
   * @return Transformed output
   */
  /*public static Supplier<Double> interpolateJoystick(Supplier<Double> in, double deadzone)
  {
      return () -> interpolateNow(in.get(), deadzone);
  }

  public static double interpolateNow(double in, double deadzone)
  {
      if(Math.abs(in) < deadzone)
          return 0.0;
      else if (in>0)
          return Math.pow((in - deadzone)*(1.0/(1.0-deadzone)), 3);
      else 
          return -Math.pow((-in - deadzone)*(1.0/(1.0-deadzone)), 3);
  }*/

  public static Supplier<Double> interpolateJoystick(Supplier<Double> in, double deadzone)
  {
      return () -> in.get();
  }

  public static double interpolateNow(double in, double deadzone)
  {
    if(Math.abs(in) < deadzone)
      return 0.0;
    else
      return in;
  }

    
  // Static reference to the robot class
  // Previously we used static subsystems, but this appears to break things in 2025
  // Use getRobot() to get the robot object
  private static RobotContainer robotReference;

  /**
   * Get a reference to the RobotContainer object in use
   * @return the active RobotContainer object
   */
  public static RobotContainer getRobot()
  {
    return robotReference;
  }

}
