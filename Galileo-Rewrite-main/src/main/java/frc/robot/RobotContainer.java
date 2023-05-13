package frc.robot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.CartDrive;
import frc.robot.Subsystems.Drive;
import frc.robot.Subsystems.Intake;
import frc.robot.Subsystems.Lift;

public class RobotContainer {
  private Drive mDrive;
  private Intake mIntake;
  private Lift mLift;
  private CommandXboxController mController;
  private POVButton IN;
  private POVButton OUT;
  private POVButton LOW;
  private POVButton HIGH;

  public RobotContainer() {
    mDrive = new Drive();
    mIntake = new Intake();
    mLift = new Lift();

    mController = new CommandXboxController(0);
    LOW = new POVButton(mController.getHID(), 180);
    HIGH = new POVButton(mController.getHID(), 0);
    IN = new POVButton(mController.getHID(), 90);
    OUT = new POVButton(mController.getHID(), 270);

    mLift.startCompressor();

    mDrive.setDefaultCommand(new CartDrive(
      () -> mController.getLeftX(),
      () -> mController.getLeftY(),
      () -> mController.getRightX(),
      () -> mLift.getLiftState(),
      mDrive
    ));
//    mDrive.setDefaultCommand(mDrive.runLift());


    configureBindings();
  }

  private void configureBindings() {
    //Score commands
    //Score low
    click(mController.rightTrigger(), mIntake.lowScoreCommand(), mIntake.stopCommand());

    //Score mid
    click(mController.leftTrigger(), mIntake.midScoreCommand(), mIntake.stopCommand());

    //Score plate
    click(mController.rightBumper(), mLift.pushOutCommand(), mLift.sideStopCommand());

    //Bring in
    click(mController.leftBumper(), mLift.pushInCommand(), mLift.sideStopCommand());

    //Toggle intake
    click(mController.a(), mLift.toggleIntakeCommand(), mLift.stopCommand());

    //Wheel intake(extra)
    click(mController.b(), mIntake.spinInCommand(), mIntake.stopCommand());

    //Lift Commands
    //Lift Both
    click(HIGH, mLift.liftUpCommand(), mLift.stopCommand());

    //Bring down both
    click(LOW, mLift.bringDownCommand(), mLift.stopCommand());

    //Toggle front
    click(IN, mLift.toggleFrontCommand(), mLift.stopCommand());

    //Toggle back
    click(OUT, mLift.toggleBackCommand(), mLift.stopCommand());
  }

  public void click(Trigger button, Command command, Command command2) {
    button.onTrue(command);
    button.onFalse(command2);
  }

  public void click(Trigger button, Command command) {
    button.onTrue(command);
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}