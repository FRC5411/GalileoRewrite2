package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

public class Lift extends SubsystemBase {
  private DoubleSolenoid m_FrontLift;
  private DoubleSolenoid m_BackLift;
  private DoubleSolenoid m_SideLift;
  private boolean frontLift;
  private Compressor m_compressor;

  public Lift() {
    m_FrontLift = new DoubleSolenoid(5, PneumaticsModuleType.CTREPCM, 0, 7);
    m_BackLift = new DoubleSolenoid(5, PneumaticsModuleType.CTREPCM, 1, 6);
    m_SideLift = new DoubleSolenoid(5, PneumaticsModuleType.CTREPCM, 2, 5);

    frontLift = false;

    m_compressor = new Compressor(5, PneumaticsModuleType.CTREPCM);
  }

  public void startCompressor() {
    m_compressor.enableDigital();
  }

  public void stopCompressor() {
    m_compressor.disable();
  }

  public void liftFrontUp() {
    frontLift = false;
    m_FrontLift.set(kForward);
  }

  public void liftBackUp() {
    m_BackLift.set(kForward);
  }

  public void bringFrontDown() {
    frontLift = true;
    m_FrontLift.set(kReverse);
  }

  public void bringBackDown() {
    m_BackLift.set(kReverse);
  }

  public void pushOut() {
    m_SideLift.set(kForward);
  }

  public void pushIn() {
    m_SideLift.set(kReverse);
  }

  public void sideStop() {
    m_SideLift.set(kOff);
  }

  public void stop() {
    m_FrontLift.set(kOff);
    m_BackLift.set(kOff);
  }

  public ParallelCommandGroup liftUpCommand() {
    InstantCommand liftFrontUp = new InstantCommand(() -> liftFrontUp());
    InstantCommand liftBackUp = new InstantCommand(() -> liftBackUp());
    return new ParallelCommandGroup(liftFrontUp, liftBackUp);
  }

  public InstantCommand liftFrontUpCommand() {
    InstantCommand liftFrontUp = new InstantCommand(() -> liftFrontUp());
    return liftFrontUp;
  }

  public InstantCommand liftBackUpCommand() {
    InstantCommand liftFrontUp = new InstantCommand(() -> liftBackUp());
    return liftFrontUp;
  }

  public ParallelCommandGroup bringDownCommand() {
    InstantCommand bringFrontDown = new InstantCommand(() -> bringFrontDown());
    InstantCommand bringBackDown = new InstantCommand(() -> bringBackDown());
    return new ParallelCommandGroup(bringFrontDown, bringBackDown);
  }

  public InstantCommand pushOutCommand() {
    return new InstantCommand(() -> pushOut(), this);
  }

  public InstantCommand pushInCommand() {
    return new InstantCommand(() -> pushIn(), this);
  }

  public InstantCommand toggleSideCommand() {
    return new InstantCommand(()-> m_SideLift.toggle(), this);
  }

  public InstantCommand toggleFrontCommand() {
    return new InstantCommand(()-> m_FrontLift.toggle(), this);
  }

  public InstantCommand toggleBackCommand() {
    return new InstantCommand(()-> m_BackLift.toggle(), this);
  }

  public InstantCommand stopCommand() {
    return new InstantCommand(() -> stop());
  }

  public InstantCommand sideStopCommand() {
    return new InstantCommand(() -> sideStop(), this);
  }

  public double get(DoubleSolenoid.Value value) {
    switch(value) {
      case kForward:
        return 1;
      case kReverse:
        return -1;
      case kOff:
        return 0;
      default:
        return 0;
    }
  }

  public boolean getLiftState() {
    return get(m_FrontLift.get()) == 1;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("lift 1", get(m_FrontLift.get()));
    SmartDashboard.putNumber("lift 2", get(m_BackLift.get()));
    SmartDashboard.putNumber("lift 3", get(m_SideLift.get()));
    SmartDashboard.putBoolean("Front lift", frontLift);
  }
}