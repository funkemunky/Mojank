package cc.funkemunky.fixer.api.utils;

import org.bukkit.Location;

public class MathUtil {

    public static double getHorizontalDistance(Location from, Location to) {
        Location cFrom = from.clone();
        Location cTo = to.clone();

        cFrom.setY(0);
        cTo.setZ(0);

        return cFrom.distance(cTo);
    }

    public static double getVerticalDistance(Location from, Location to) {
        return to.getY() - from.getY();
    }

    public static int floor(double var0) {
        int var2 = (int) var0;
        return var0 < var2 ? var2 - 1 : var2;
    }

    public static boolean elapsed(long time, long needed) {
        return Math.abs(System.currentTimeMillis() - time) >= needed;
    }

    public static float getDelta(float one, float two) {
        return Math.abs(one - two);
    }

    public static double getDelta(double one, double two) {
        return Math.abs(one - two);
    }

    public static long elapsed(long time) {
        return Math.abs(System.currentTimeMillis() - time);
    }
}
