package monnef.playground.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.ByteArrayInputStream;

public class Transformer {
    public static final String TARGET_CLASS = "monnef.playground.javassist.target.WorldServerTarget";
    public static final String HOOK_PROCESSOR_CLASS = "monnef.playground.javassist.StrikeHook";
    public static boolean hookInserted = false;

    public static byte[] transform(byte[] code) {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc;
        try {
            cc = pool.makeClass(new ByteArrayInputStream(code));
            CtMethod method = cc.getMethod("process", "()V");
            method.instrument(
                    new ExprEditor() {
                        public void edit(MethodCall m)
                                throws CannotCompileException {
                            //System.out.println(String.format("MethodCall: cname - %s, mname - %s", m.getClassName(), m.getMethodName()));
                            if (m.getClassName().equals(TARGET_CLASS)
                                    && m.getMethodName().equals("strike")) {
                                m.replace(String.format("{ if(%s.invoke($1)){ $_ = $proceed($$);} }", HOOK_PROCESSOR_CLASS));
                                System.out.println("inserting hook");
                                hookInserted = true;
                            }
                        }
                    }
            );

            return cc.toBytecode();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
