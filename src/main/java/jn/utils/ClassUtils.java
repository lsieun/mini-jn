package jn.utils;

import sun.management.VMManagement;
import sun.net.www.http.HttpClient;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;

public class ClassUtils {
    public static Class<?> getVMManagementImpl() {
        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            Field jvm = runtime.getClass().getDeclaredField("jvm");
            jvm.setAccessible(true);
            VMManagement mgmt = (sun.management.VMManagement) jvm.get(runtime);
            return mgmt.getClass();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Class<?> getLinkedTreeMap() {
        try {
            return Class.forName("com.google.gson.internal.LinkedTreeMap");
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
