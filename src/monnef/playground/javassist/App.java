package monnef.playground.javassist;

import monnef.playground.javassist.target.IWorldServerTarget;

public class App {
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        ClassLoader loader = new CustomClassLoader();
        Object obj;
        try {
            System.out.println("loading class");
            obj = loader.loadClass(Transformer.TARGET_CLASS).newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        if (!Transformer.hookInserted) {
            throw new RuntimeException("hook not inserted!");
        }
        IWorldServerTarget server = (IWorldServerTarget) obj;
        server.process();
        StrikeHook.allow = false;
        System.out.println("disabling strikes, should not produce any output");
        server.process();
    }
}