package jn.asm.tree;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.tree.*;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class InetAddressNode extends ClassNode {
    public InetAddressNode(int api, ClassVisitor cv) {
        super(api);
        this.cv = cv;
    }

    @Override
    public void visitEnd() {
        // 首先，处理自己的代码逻辑
        for (MethodNode m : methods) {
            if ("getAllByName".equals(m.name) && "(Ljava/lang/String;Ljava/net/InetAddress;)[Ljava/net/InetAddress;".equals(m.desc)) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new MethodInsnNode(INVOKESTATIC, "boot/filter/InetAddressFilter", "testQuery", "(Ljava/lang/String;)Ljava/lang/String;", false));
                list.add(new InsnNode(POP));

                m.instructions.insert(list);
                continue;
            }

            if ("isReachable".equals(m.name) && "(Ljava/net/NetworkInterface;II)Z".equals(m.desc)) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new MethodInsnNode(INVOKESTATIC, "boot/filter/InetAddressFilter", "testReachable", "(Ljava/net/InetAddress;)Ljava/lang/Object;", false));
                list.add(new VarInsnNode(ASTORE, 4));
                list.add(new InsnNode(ACONST_NULL));
                list.add(new VarInsnNode(ALOAD, 4));

                LabelNode label1 = new LabelNode();
                list.add(new JumpInsnNode(IF_ACMPEQ, label1));
                list.add(new InsnNode(ICONST_0));
                list.add(new InsnNode(IRETURN));
                list.add(label1);

                m.instructions.insert(list);
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