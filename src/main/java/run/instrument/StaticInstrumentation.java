package run.instrument;

import jn.cst.Const;
import jn.asm.tree.*;
import jn.utils.FileUtils;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.math.BigInteger;

public class StaticInstrumentation {
    public static void main(String[] args) {
        Class<?> clazz = BigInteger.class;
        String user_dir = System.getProperty("user.dir");
        String filepath = user_dir + File.separator +
                "target" + File.separator +
                "classes" + File.separator +
                "data" + File.separator +
                clazz.getName().replace(".", "/") + ".class";
        filepath = filepath.replace(File.separator, "/");

        byte[] bytes = dump(clazz);
        FileUtils.writeBytes(filepath, bytes);
        System.out.println("file:///" + filepath);
    }

    public static byte[] dump(Class<?> clazz) {
        String className = clazz.getName();
        byte[] bytes = FileUtils.readClassBytes(className);

        ClassReader cr = new ClassReader(bytes);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor cv = new BigIntegerNode(Const.ASM_VERSION, cw);

        int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
        cr.accept(cv, parsingOptions);
        return cw.toByteArray();
    }
}