package monnef.playground.javassist.target;

public class WorldServerTarget implements IWorldServerTarget {
    private int foo;

    private static void printDebug(String msg) {
        System.out.println("[TargetClass] " + msg);
    }

    static {
        printDebug("WorldServerTarget - init");
    }

    @Override
    public void process() {
        printDebug("process start");
        foo++;
        int pos = 4;
        printDebug("after pos init");
        if (canStrikeAt(pos)) {
            strike(pos); // <-- we want to wrap this method call
        }
        printDebug("after canStrike if");
        foo--;
        printDebug("process end");
    }

    public void strike(int pos) {
        printDebug("☇☇☇ striking at " + pos);
    }

    public boolean canStrikeAt(int pos) {
        return pos > 0;
    }
}
