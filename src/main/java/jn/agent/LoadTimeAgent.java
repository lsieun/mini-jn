package jn.agent;

import jn.utils.TransformerUtils;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.math.BigInteger;
import java.net.InetAddress;

public class LoadTimeAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Premain-Class: " + LoadTimeAgent.class.getName());
        System.out.println("Can-Redefine-Classes: " + inst.isRedefineClassesSupported());
        System.out.println("Can-Retransform-Classes: " + inst.isRetransformClassesSupported());
        System.out.println("Can-Set-Native-Method-Prefix: " + inst.isNativeMethodPrefixSupported());
        System.out.println("========= ========= =========");

        // 01. java/math/BigInteger
        {
            ClassFileTransformer transformer = TransformerUtils::transformBigInteger;
            inst.addTransformer(transformer, true);
        }

        // 02. java/net/InetAddress
        {
            ClassFileTransformer transformer = TransformerUtils::transformInetAddress;
            inst.addTransformer(transformer, true);
        }

        // 03. sun/management/VMManagementImpl
        {
            ClassFileTransformer transformer = TransformerUtils::transformVMManagement;
            inst.addTransformer(transformer, true);
        }

        // 04. sun/net/www/http/HttpClient
        {
            ClassFileTransformer transformer = TransformerUtils::transformHttpClient;
            inst.addTransformer(transformer, true);
        }

        // 05. com/google/gson/internal/LinkedTreeMap
        {
            ClassFileTransformer transformer = TransformerUtils::transformLinkedTreeMap;
            inst.addTransformer(transformer, true);
        }

        try {
            Class<?> bigIntegerClass = BigInteger.class;
            Class<InetAddress> inetAddressClass = InetAddress.class;
            inst.retransformClasses(bigIntegerClass, inetAddressClass);
        } catch (UnmodifiableClassException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
