package jn.asm.tree;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.tree.*;

import java.util.Iterator;

import static jdk.internal.org.objectweb.asm.Opcodes.*;


public class VMManagementImplNode extends ClassNode {
    public VMManagementImplNode(int api, ClassVisitor cv) {
        super(api);
        this.cv = cv;
    }

    @Override
    public void visitEnd() {
        // 首先，处理自己的代码逻辑
        for (MethodNode mn : methods) {
            if ("getVmArguments".equals(mn.name) && "()Ljava/util/List;".equals(mn.desc)) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new FieldInsnNode(GETFIELD, "sun/management/VMManagementImpl", "vmArgs", "Ljava/util/List;"));
                list.add(new MethodInsnNode(INVOKESTATIC, "boot/filter/VMManagementImplFilter", "testArgs", "(Ljava/util/List;)Ljava/util/List;", false));
                list.add(new FieldInsnNode(PUTFIELD, "sun/management/VMManagementImpl", "vmArgs", "Ljava/util/List;"));

                Iterator<AbstractInsnNode> it = mn.instructions.iterator();
                while (it.hasNext()) {
                    AbstractInsnNode in = it.next();

                    if (AbstractInsnNode.INSN == in.getType() && ARETURN == in.getOpcode()) {
                        mn.instructions.insert(in.getPrevious().getPrevious(), list);
                        break;
                    }
                }
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
