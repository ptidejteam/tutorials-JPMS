package com.ibm.toad.cfparse.instruction;

import com.ibm.toad.cfparse.ConstantPool;

public interface InstructionFactory {
   BaseInstruction create(TagFactory var1, ConstantPool var2, byte[] var3, int var4, int var5);

   BaseInstruction create(TagFactory var1, ConstantPool var2, String var3) throws InstructionFormatException;
}
