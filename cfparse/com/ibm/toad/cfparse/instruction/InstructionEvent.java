package com.ibm.toad.cfparse.instruction;

public class InstructionEvent {
   BaseInstruction d_instr;
   String d_op;

   public InstructionEvent(String var1, BaseInstruction var2) {
      this.d_op = var1;
      this.d_instr = var2;
   }

   int getOpCode() {
      return this.d_instr.getOpCode();
   }

   BaseInstruction getInstruction() {
      return this.d_instr;
   }
}
