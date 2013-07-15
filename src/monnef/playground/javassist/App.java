package monnef.playground.javassist;

import monnef.playground.javassist.target.IWorldServerTarget;

public class App {
    private static final boolean showDebugMessages = true;

    public static void main(String[] args) {
        test();
        System.out.flush();
    }

    private static void printDebug(String msg) {
        if (showDebugMessages) System.out.println("[App] " + msg);
    }

    private static void test() {
        ClassLoader loader = new CustomClassLoader();
        Object obj, obj2;
        try {
            printDebug("loading class");
            obj = loader.loadClass(Transformer.TARGET_CLASS).newInstance();
            obj2 = loader.loadClass(Transformer.TARGET_CLASS).newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        if (!Transformer.hookInserted) {
            throw new RuntimeException("hook not inserted!");
        }
        IWorldServerTarget server = (IWorldServerTarget) obj;
        IWorldServerTarget server2 = (IWorldServerTarget) obj2;
        server.process();
        StrikeHook.allow = false;
        printDebug("disabling strikes, should not produce any output");
        server.process();
        printDebug("now starting instance #2 with disabled calling in hook, should not produce any output");
        server2.process();
    }
}