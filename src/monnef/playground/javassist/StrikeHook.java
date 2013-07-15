package monnef.playground.javassist;

public class StrikeHook {
    public static boolean allow = true;

    public static boolean invoke(int position) {
        System.out.println("StrikeHook.invoke(" + position + "), returning: " + allow);
        return allow;
    }
}
