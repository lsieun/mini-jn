package jn.utils;

import jn.asm.tree.*;
import jn.cst.Const;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class TransformerUtils {
    public static byte[] transformBigInteger(ClassLoader loader,
                                              String className,
                                              Class<?> classBeingRedefined,
                                              ProtectionDomain protectionDomain,
                                              byte[] classfileBuffer)
            throws IllegalClassFormatException {
        if ("java/math/BigInteger".equals(className)) {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            ClassVisitor cv = new BigIntegerNode(Const.ASM_VERSION, cw);

            int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
            cr.accept(cv, parsingOptions);
            return cw.toByteArray();
        }
        return null;
    }

    public static byte[] transformInetAddress(ClassLoader loader,
                                               String className,
                                               Class<?> classBeingRedefined,
                                               ProtectionDomain protectionDomain,
                                               byte[] classfileBuffer)
            throws IllegalClassFormatException {
        if ("java/net/InetAddress".equals(className)) {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            ClassVisitor cv = new InetAddressNode(Const.ASM_VERSION, cw);

            int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
            cr.accept(cv, parsingOptions);
            return cw.toByteArray();
        }
        return null;
    }

    public static byte[] transformVMManagement(ClassLoader loader,
                                             String className,
                                             Class<?> classBeingRedefined,
                                             ProtectionDomain protectionDomain,
                                             byte[] classfileBuffer)
            throws IllegalClassFormatException {
        if ("sun/management/VMManagementImpl".equals(className)) {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            ClassVisitor cv = new VMManagementImplNode(Const.ASM_VERSION, cw);

            int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
            cr.accept(cv, parsingOptions);
            return cw.toByteArray();
        }
        return null;
    }

    public static byte[] transformHttpClient(ClassLoader loader,
                                                String className,
                                                Class<?> classBeingRedefined,
                                                ProtectionDomain protectionDomain,
                                                byte[] classfileBuffer)
            throws IllegalClassFormatException {
        if ("sun/net/www/http/HttpClient".equals(className)) {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            ClassVisitor cv = new HttpClientNode(Const.ASM_VERSION, cw);

            int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
            cr.accept(cv, parsingOptions);
            return cw.toByteArray();
        }
        return null;
    }

    public static byte[] transformLinkedTreeMap(ClassLoader loader,
                                                String className,
                                                Class<?> classBeingRedefined,
                                                ProtectionDomain protectionDomain,
                                                byte[] classfileBuffer)
            throws IllegalClassFormatException {
        if ("com/google/gson/internal/LinkedTreeMap".equals(className)) {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            ClassVisitor cv = new LinkedTreeMapNode(Const.ASM_VERSION, cw);

            int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
            cr.accept(cv, parsingOptions);
            return cw.toByteArray();
        }
        return null;
    }
}
