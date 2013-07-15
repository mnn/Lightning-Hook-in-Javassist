package monnef.playground.javassist;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader {
    private static final boolean showDebugMessages = true;

    private static void printDebug(String msg) {
        if (showDebugMessages) System.out.println("[CustomClassLoader] " + msg);
    }

    public static byte[] inputStreamToByteArray(InputStream is) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return buffer.toByteArray();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        printDebug("loadClass: processing class - " + name);
        Class cachedResult = findLoadedClass(name);
        if (cachedResult != null) {
            printDebug("returning cached data");
            return cachedResult;
        }
        if (Transformer.TARGET_CLASS.equals(name)) {
            InputStream inputStream = getResourceAsStream(name.replace('.', '/') + ".class");
            byte[] inputByteCode = inputStreamToByteArray(inputStream);
            byte[] outputByteCode = Transformer.transform(inputByteCode);
            printDebug("returning freshly transformed class");
            return defineClass(name, outputByteCode, 0, outputByteCode.length);
        }
        printDebug("calling parent CL");
        return super.loadClass(name);
    }
}
