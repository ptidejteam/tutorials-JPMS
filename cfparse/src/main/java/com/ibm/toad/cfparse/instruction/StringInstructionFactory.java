package com.ibm.toad.cfparse.instruction;

import com.ibm.toad.cfparse.ConstantPool;

public class StringInstructionFactory implements InstructionFactory {
   public BaseInstruction create(TagFactory var1, ConstantPool var2, byte[] var3, int var4, int var5) {
      return new Instruction(var1, var2, var3, var4, var5);
   }

   public BaseInstruction create(TagFactory var1, ConstantPool var2, String var3) throws InstructionFormatException {
      return new Instruction(var1, var2, var3);
   }
}
