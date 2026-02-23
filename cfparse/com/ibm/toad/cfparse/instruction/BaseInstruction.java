package com.ibm.toad.cfparse.instruction;

public interface BaseInstruction {
   int getOpCode();

   String getTag();

   void setTag(String var1);

   int getLength(int var1);

   byte[] getCode(int[] var1, int var2);
}
