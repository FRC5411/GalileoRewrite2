package frc.robot.Commands;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Drive;

public class CartDrive extends CommandBase {
  private Drive m_drive;
  DoubleSupplier m_x;
  DoubleSupplier m_y;
  DoubleSupplier m_z;
  BooleanSupplier m_l;

  public CartDrive(DoubleSupplier x, DoubleSupplier y, DoubleSupplier z, BooleanSupplier l, Drive drive) {
    m_x = x;
    m_y = y;
    m_z = z;
    m_l = l;
    m_drive = drive;
    addRequirements(drive);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_drive.cartesianDrive(m_x.getAsDouble(), m_y.getAsDouble(), m_z.getAsDouble());
    m_drive.arcadeDrive(-m_y.getAsDouble(), m_x.getAsDouble(), m_l.getAsBoolean());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}