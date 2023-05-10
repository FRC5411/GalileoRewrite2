package frc.robot.Commands;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Drive;

public class PolarDrive extends CommandBase {
  private Drive m_drive;
  private DoubleSupplier m_x;
  private DoubleSupplier m_yRadians;
  private DoubleSupplier m_z;

  public PolarDrive(DoubleSupplier x, DoubleSupplier yRadians, DoubleSupplier z, Drive drive) {
    m_x = x;
    m_yRadians = yRadians;
    m_z = z;
    m_drive = drive;
    addRequirements(drive);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_drive.polarDrive(m_x.getAsDouble(), m_yRadians.getAsDouble(), m_z.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}