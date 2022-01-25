package boot.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class VMManagementImplFilter {
    public static List<String> testArgs(List<String> vmArgs) {
        boolean modified = false;
        List<String> list = new ArrayList<>(vmArgs);
        Iterator<String> it = list.iterator();

        while (it.hasNext()) {
            String arg = it.next();

            if (arg.startsWith("-Djanf.debug=")) {
                it.remove();
                modified = true;
            } else if (arg.startsWith("-javaagent:")) {
                String[] sections = arg.substring(11).split("=", 2);
                if (sections.length < 1) {
                    continue;
                }

                if (!sections[0].endsWith("mini-jn.jar")) {    // not me
                    continue;
                }

                it.remove();
                modified = true;
            }
        }

        return modified ? Collections.unmodifiableList(list) : vmArgs;
    }
}