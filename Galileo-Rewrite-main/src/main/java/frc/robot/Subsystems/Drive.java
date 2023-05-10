package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
  private WPI_TalonSRX leftFrontMotor;
  private WPI_TalonSRX leftBackMotor;
  private WPI_TalonSRX rightFrontMotor;
  private WPI_TalonSRX rightBackMotor;
  private WPI_TalonSRX liftMotorLeft;
  private WPI_TalonSRX liftMotorRight;

  public Drive() {
    //Drive Motors
    leftBackMotor = configWPI_TalonSRX(leftFrontMotor, 14, true);
    leftFrontMotor = configWPI_TalonSRX(leftBackMotor, 15, true);
    rightBackMotor = configWPI_TalonSRX(rightBackMotor, 3, false);
    rightFrontMotor = configWPI_TalonSRX(rightFrontMotor, 2, false);

    //Lift Motor
    liftMotorLeft = configWPI_TalonSRX(liftMotorLeft, 13, false);
    liftMotorRight = configWPI_TalonSRX(leftBackMotor, 8, false);
  }

  public void polarDrive(double x, double y, double z) {
    Rotation2d direction = new Rotation2d(x, y);

    double magnitude = Math.hypot(x, y);
    double xDirection = direction.getSin();
    double yDirection = direction.getCos();

    cartesianDrive(magnitude * xDirection, magnitude * yDirection, z);
  }

  public void cartesianDrive(double x, double y, double z) {
    double wheelSpeeds[] = normalize(calculateMecanumSpeeds(x, y, z));

    wheelSpeeds = deadband(wheelSpeeds, 0.15);

    setMotors(wheelSpeeds);
  }

   public void arcadeDrive(double transalte, double rotate, boolean MoveOrNot) {
    if(MoveOrNot == true) {
        double left = transalte - rotate;
        double right = transalte + rotate;

        double max = Math.max(Math.abs(left), Math.abs(right));

        if(max >= 1) {
          left /= max;
          right /= max;
        }

        liftMotorLeft.set(left);
        liftMotorRight.set(right);
    }
  }

  private WPI_TalonSRX configWPI_TalonSRX(WPI_TalonSRX motor, int id, boolean inverted) {
    motor = new WPI_TalonSRX(id);
    motor.configFactoryDefault();
    motor.setInverted(inverted);
    motor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
    return motor;
  }

  public InstantCommand runLift() {
    return new InstantCommand(()-> {liftMotorLeft.set(1);liftMotorRight.set(1);}, this);
  }


  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left Back", leftBackMotor.get());
    SmartDashboard.putNumber("Right Back", rightBackMotor.get());
    SmartDashboard.putNumber("Right Front", rightFrontMotor.get());
    SmartDashboard.putNumber("Left Front", leftFrontMotor.get());
    SmartDashboard.putNumber("Lift Left Motor", liftMotorLeft.get());
    SmartDashboard.putNumber("Lift Right Motor", liftMotorRight.get());
  }

  private double deadzone(double v, double deadzone) {
    if(Math.abs(v) < deadzone) {
      return 0;
    }
    return v;
  }
  
  private double[] deadband(double[] wheelSpeeds, double deadzone) {
    for(int i = 0; i < wheelSpeeds.length; i++) {
      wheelSpeeds[i] = deadzone(wheelSpeeds[i], deadzone);
    }
    return wheelSpeeds;
  }

  private double[] normalize(double[] wheelSpeeds) {
    double LF = wheelSpeeds[0];
    double LB = wheelSpeeds[1];
    double RF = wheelSpeeds[2];
    double RB = wheelSpeeds[3];

    double max = Math.max(Math.max(Math.abs(LF), Math.abs(LB)), 
                          Math.max(Math.abs(RF), Math.abs(RB)));

    if(max > 1) {
      LF /= max;
      LB /= max;
      RF /= max;
      RB /= max;
    }

    return new double[] {LF, LB, RF, RB};
  }

  private double[] calculateMecanumSpeeds(double x, double y, double z) {
    double LF = y + x + z;
    double LB = y - x + z;
    double RB = y + x - z;
    double RF = y - x - z;
    return new double[] {LF, LB, RF, RB};
  }

  private void setMotors(double[] wheelSpeeds) {
    leftFrontMotor.set(wheelSpeeds[0]);
    leftBackMotor.set(wheelSpeeds[1]);
    rightFrontMotor.set(wheelSpeeds[2]);
    rightBackMotor.set(wheelSpeeds[3]);
  }
}