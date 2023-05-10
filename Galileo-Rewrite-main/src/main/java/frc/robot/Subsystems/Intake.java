package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static com.ctre.phoenix.motorcontrol.NeutralMode.*;

public class Intake extends SubsystemBase {
  private WPI_TalonSRX upperMotor;
  private WPI_TalonSRX lowerMotor;

  public Intake() {
    upperMotor = configWPI_TalonSRX(upperMotor, 7, false);
    lowerMotor = configWPI_TalonSRX(lowerMotor, 6, true);
  }

  public void spinIn() {
    lowerMotor.set(1);
  }

  public void lowScore() {
    upperMotor.set(1);
    lowerMotor.set(1);
  }

  public void midScore() {
    upperMotor.set(-1);
    lowerMotor.set(1);
  }

  public void stop() {
    upperMotor.set(0);
    lowerMotor.set(0);
  }

  public InstantCommand spinInCommand() {
    return new InstantCommand(() -> spinIn());
  }

  public InstantCommand lowScoreCommand() {
    return new InstantCommand(() -> lowScore());
  }

  public InstantCommand midScoreCommand() {
    return new InstantCommand(() -> midScore());
  }

  public InstantCommand stopCommand() {
    return new InstantCommand(() -> stop());
  }


  private WPI_TalonSRX configWPI_TalonSRX(WPI_TalonSRX motor, int id, boolean inverted) {
    motor = new WPI_TalonSRX(id);
    motor.configFactoryDefault();
    motor.setInverted(inverted);
    motor.setNeutralMode(Brake);
    return motor;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Upper motor", upperMotor.get());
    SmartDashboard.putNumber("Lower Motor", lowerMotor.get());
  }
}