package jn.asm.tree;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.tree.*;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class HttpClientNode extends ClassNode {
    public HttpClientNode(int api, ClassVisitor cv) {
        super(api);
        this.cv = cv;
    }

    @Override
    public void visitEnd() {
        // 首先，处理自己的代码逻辑
        for (MethodNode mn : methods) {
            if ("openServer".equals(mn.name) && "()V".equals(mn.desc)) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new FieldInsnNode(GETFIELD, "sun/net/www/http/HttpClient", "url", "Ljava/net/URL;"));
                list.add(new MethodInsnNode(INVOKESTATIC, "boot/filter/HttpClientFilter", "testURL", "(Ljava/net/URL;)Ljava/net/URL;", false));
                list.add(new InsnNode(POP));

                mn.instructions.insert(list);
            }
        }

        // 其次，调用父类的方法实现（根据实际情况，选择保留，或删除）
        super.visitEnd();

        // 最后，向后续ClassVisitor传递
        if (cv != null) {
            accept(cv);
        }
    }
}
