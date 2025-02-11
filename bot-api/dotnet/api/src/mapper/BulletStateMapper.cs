using System.Collections.Generic;
using Robocode.TankRoyale.BotApi.Util;

namespace Robocode.TankRoyale.BotApi.Mapper;

internal static class BulletStateMapper
{
    public static BulletState Map(Schema.BulletState source)
    {
        return new BulletState(
            source.BulletId,
            source.OwnerId,
            source.Power,
            source.X,
            source.Y,
            source.Direction,
            ColorUtil.FromString(source.Color)
        );
    }

    public static IEnumerable<BulletState> Map(IEnumerable<Schema.BulletState> source)
    {
        var bulletStates = new HashSet<BulletState>();
        foreach (var bulletState in source)
        {
            bulletStates.Add(Map(bulletState));
        }

        return bulletStates;
    }
}