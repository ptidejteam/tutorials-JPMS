package com.ibm.toad.cfparse.instruction;

class Minstr {
   String d_instr;
   Minstr d_next;

   Minstr(String var1) {
      this.d_instr = var1;
      this.d_next = null;
   }

   Minstr(String var1, Minstr var2) {
      this.d_instr = var1;
      this.d_next = var2;
   }
}
