using System;
using Newtonsoft.Json;

namespace Robocode.TankRoyale.BotApi
{
  /// <summary>
  /// Represents the current bot state. 
  /// </summary>
  public sealed class BotState
  {
    /// <summary>Energy level.</summary>
    /// <value>The energy level.</value>
    public double Energy { get; }

    /// <summary>X coordinate.</summary>
    /// <value>The X coordinate.</value>
    public double X { get; }

    /// <summary>Y coordinate.</summary>
    /// <value>The Y coordinate.</value>
    public double Y { get; }

    /// <summary>Driving direction in degrees.</summary>
    /// <value>The driving direction in degrees.</value>
    public double Direction { get; }

    /// <summary>Gun direction in degrees.</summary>
    /// <value>The gun direction in degrees.</value>
    public double GunDirection { get; }

    /// <summary>Radar direction in degrees.</summary>
    /// <value>The radar direction in degrees.</value>
    public double RadarDirection { get; }

    /// <summary>Radar sweep angle in degrees.</summary>
    /// <value>The radar sweep angle in degrees.</value>
    public double RadarSweep { get; }

    /// <summary>Speed measured in pixels per turn.</summary>
    /// <value>The speed measured in pixels per turn.</value>
    public double Speed { get; }

    /// <summary>Turn rate of the body in degrees per turn (can be positive and negative).</summary>
    /// <value>The turn rate of the body in degrees per turn (can be positive and negative).</value>
    public double TurnRate { get; }

    /// <summary>Turn rate of the gun in degrees per turn (can be positive and negative).</summary>
    /// <value>The turn rate of the gun in degrees per turn (can be positive and negative).</value>
    public double GunTurnRate { get; }

    /// <summary>Turn rate of the radar in degrees per turn (can be positive and negative).</summary>
    /// <value>The turn rate of the radar in degrees per turn (can be positive and negative).</value>
    public double RadarTurnRate { get; }

    /// <summary>Gun heat.</summary>
    /// <value>The gun heat.</value>
    public double GunHeat { get; }

    /// <summary>
    /// Body color code.
    /// </summary>
    /// <value>The body color code.</value>
    public int? BodyColor { get; }

    /// <summary>
    /// Gun turret color code.
    /// </summary>
    /// <value>The gun turret color code.</value>
    public int? TurretColor { get; }

    /// <summary>
    /// Radar color code.
    /// </summary>
    /// <value>The radar color code.</value>
    public int? RadarColor { get; }

    /// <summary>
    /// Bullet color code.
    /// </summary>
    /// <value>The bullet color code.</value>
    public int? BulletColor { get; }

    /// <summary>
    /// Scan arc color code.
    /// </summary>
    /// <value>The scan arc color code.</value>
    public int? ScanColor { get; }

    /// <summary>
    /// Tracks color code.
    /// </summary>
    /// <value>The tracks color code.</value>
    public int? TracksColor { get; }

    /// <summary>
    /// Gun color code.
    /// </summary>
    /// <value>The gun color code.</value>
    public int? GunColor { get; }

    /// <summary>
    /// Initializes a new instance of the BotState class.
    /// </summary>
    /// <param name="energy">Energy level.</param>
    /// <param name="x">X coordinate.</param>
    /// <param name="y">Y coordinate.</param>
    /// <param name="direction">Driving direction in degrees.</param>
    /// <param name="gunDirection">Gun direction in degrees.</param>
    /// <param name="radarDirection">Radar direction in degrees.</param>
    /// <param name="radarSweep">Radar sweep angle in degrees.</param>
    /// <param name="speed">Speed measured in pixels per turn.</param>
    /// <param name="turnRate">Turn rate of the body in degrees per turn.</param>
    /// <param name="gunTurnRate">Turn rate of the gun in degrees per turn.</param>
    /// <param name="radarTurnRate">Turn rate of the radar in degrees per turn.</param>
    /// <param name="gunHeat">Gun heat.</param>
    /// <param name="bodyColor">Body color code.</param>
    /// <param name="turretColor">Gun turret color code.</param>
    /// <param name="radarColor">Radar color code.</param>
    /// <param name="bulletColor">Bullet color code.</param>
    /// <param name="scanColor">Scan arc color code.</param>
    /// <param name="tracksColor">Tracks color code.</param>
    /// <param name="gunColor">Gun color code.</param>
    [JsonConstructor]
    public BotState(double energy, double x, double y, double direction, double gunDirection,
      double radarDirection, double radarSweep,
      double speed, double turnRate, double gunTurnRate, double radarTurnRate, double gunHeat,
      int? bodyColor, int? turretColor, int? radarColor, int? bulletColor, int? scanColor, int? tracksColor,
      int? gunColor)
    {
      Energy = energy;
      X = x;
      Y = y;
      Direction = direction;
      GunDirection = gunDirection;
      RadarDirection = radarDirection;
      RadarSweep = radarSweep;
      Speed = speed;
      TurnRate = turnRate;
      GunTurnRate = gunTurnRate;
      RadarTurnRate = radarTurnRate;
      GunHeat = gunHeat;
      BodyColor = bodyColor;
      TurretColor = turretColor;
      RadarColor = radarColor;
      BulletColor = bulletColor;
      ScanColor = scanColor;
      TracksColor = tracksColor;
      GunColor = gunColor;
    }
  }
}