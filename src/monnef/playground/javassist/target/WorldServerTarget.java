package monnef.playground.javassist.target;

import static java.lang.System.out;

public class WorldServerTarget implements IWorldServerTarget {
    private int foo;

    static {
        System.out.println("WorldServerTarget - init");
    }

    private void println(String msg) {
        out.println(msg);
    }

    @Override
    public void process() {
        println("process start");
        foo++;
        int pos = 4;
        println("after pos init");
        if (canStrikeAt(pos)) {
            strike(pos);
        }
        println("after canStrike if");
        foo--;
        println("process end");
    }

    public void strike(int pos) {
        println("striking at " + pos);
    }

    public boolean canStrikeAt(int pos) {
        return pos > 0;
    }
}
