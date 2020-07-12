using Robocode.TankRoyale.BotApi;

namespace Robocode.TankRoyale.Sample.Bots
{
  /// <summary>
  /// MyFirstBot - a sample bot.
  ///
  /// Probably the first bot you will learn about.
  ///
  /// Moves in a seesaw motion, and spins the gun around at each end.
  /// </summary>
  public class MyFirstBot : Bot
  {
    static void Main(string[] args)
    {
      new MyFirstBot().Start();
    }

    MyFirstBot() : base(BotInfo.FromJsonFile("bot-settings.json")) { }

    /// This method runs our bot program, where each command is executed one at a time in a loop.
    public override void Run()
    {
      // Repeat while bot is running
      while (IsRunning)
      {
        Forward(100);
        TurnGunRight(360);
        Back(100);
        TurnGunRight(360);
      }
    }

    /// Our bot scanned another bot. Fire when we see another bot!
    public override void OnScannedBot(ScannedBotEvent evt)
    {
      Fire(1);
    }

    /// Our bot has been hit by a bullet. Turn perpendicular to the bullet so our seesaw might avoid a future shot.
    public override void OnHitByBullet(BulletHitBotEvent evt)
    {
      // Calculate the bearing to the direction of the bullet
      double bearing = evt.Bullet.Direction - Direction;

      // Turn 90 degrees to the bullet direction based on the bearing
      TurnLeft(90 - bearing);
    }
  }
}