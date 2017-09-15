/*
 * Copyright 2017 ImpactDevelopment
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package clientapi.load;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author Brady
 * @since 9/13/2017 10:24 PM
 */
public final class ValueAccessorTransformer implements IClassTransformer {

    private final Map<String, FieldNode> fieldCache = new LinkedHashMap<>();

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        ClassNode cn = new ClassNode();
        ClassReader cr = new ClassReader(basicClass);
        cr.accept(cn, 0);

        // Clear the cache for the new class
        fieldCache.clear();

        // Assume that any values annotated with @Label should be given accessors
        for (FieldNode fn : cn.fields) {
            if (fn.visibleAnnotations != null) {
                for (AnnotationNode an : fn.visibleAnnotations) {
                    if (an.desc.equals("Lclientapi/util/annotation/Label;")) {
                        for (int i = 0; i < an.values.size(); i++) {
                            if (i % 2 == 0 && an.values.get(i).equals("id"))
                                fieldCache.put(an.values.get(i + 1).toString(), fn);
                        }
                    }
                }
            }
        }

        if (fieldCache.isEmpty() || cn.interfaces.contains("clientapi/value/holder/ValueAccessor"))
            return basicClass;

        cn.interfaces.add("clientapi/value/holder/ValueAccessor");

        createGetMethod(cn);
        createSetMethod(cn);
//        createFieldGetter(cn);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cn.accept(cw);
        return cw.toByteArray();
    }

    private void createGetMethod(ClassNode cn) {
        MethodNode mn = new MethodNode(ACC_PUBLIC, "getFieldValue", "(Ljava/lang/String;)Ljava/lang/Object;", null, null);

        fieldCache.forEach((id, fn) -> {
            Label skip = new Label();

            mn.visitVarInsn(ALOAD, 1);
            mn.visitLdcInsn(id);
            mn.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
            mn.visitJumpInsn(IFEQ, skip);

            mn.visitVarInsn(ALOAD, 0);
            mn.visitFieldInsn(GETFIELD, cn.name, fn.name, fn.desc);

            String object = getObject(fn.desc);
            if (!object.equals(fn.desc))
                mn.visitMethodInsn(INVOKESTATIC, object, "valueOf", "(" + fn.desc + ")L" + object + ";", false);

            mn.visitInsn(ARETURN);
            mn.visitLabel(skip);

        });
        mn.instructions.add(new InsnNode(ACONST_NULL));
        mn.instructions.add(new InsnNode(ARETURN));

        cn.methods.add(mn);
    }

    private void createSetMethod(ClassNode cn) {
        MethodNode mn = new MethodNode(ACC_PUBLIC, "setFieldValue", "(Ljava/lang/String;Ljava/lang/Object;)V", null, null);

        fieldCache.forEach((id, fn) -> {
            Label skip = new Label();

            mn.visitVarInsn(ALOAD, 1);
            mn.visitLdcInsn(id);
            mn.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
            mn.visitJumpInsn(IFEQ, skip);

            mn.visitVarInsn(ALOAD, 0);
            mn.visitVarInsn(ALOAD, 2);
            mn.visitTypeInsn(CHECKCAST, format(getObject(fn.desc)));

            String object = getObject(fn.desc);
            if (!object.equals(fn.desc))
                mn.visitMethodInsn(INVOKEVIRTUAL, object, getName(fn.desc) + "Value", "()" + fn.desc, false);

            mn.visitFieldInsn(PUTFIELD, cn.name, fn.name, fn.desc);
            mn.visitLabel(skip);
        });
        mn.visitInsn(RETURN);

        cn.methods.add(mn);
    }

    private String getObject(String desc) {
        switch (desc) {
            case "B":
                return "java/lang/Byte";
            case "C":
                return "java/lang/Char";
            case "D":
                return "java/lang/Double";
            case "F":
                return "java/lang/Float";
            case "I":
                return "java/lang/Integer";
            case "J":
                return "java/lang/Long";
            case "S":
                return "java/lang/Short";
            case "Z":
                return "java/lang/Boolean";
        }
        return format(desc);
    }

    private String getName(String desc) {
        switch (desc) {
            case "B":
                return "byte";
            case "C":
                return "char";
            case "D":
                return "double";
            case "F":
                return "float";
            case "I":
                return "int";
            case "J":
                return "long";
            case "S":
                return "short";
            case "Z":
                return "boolean";
        }
        return null;
    }

    private String format(String desc) {
        if (desc.startsWith("L") && desc.endsWith(";"))
            // removes the "L" and ";" from the descriptor
            return desc.substring(1, desc.length() - 1);
        else
            return desc;
    }
}
