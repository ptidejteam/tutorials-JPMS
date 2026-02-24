package com.ibm.toad.cfparse.instruction;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;

public interface CodeViewer {
   void setInstructionType(InstructionFactory var1);

   String view(ConstantPool var1, byte[] var2, CodeAttrInfo.ExceptionInfo[] var3, int var4, String var5);
}
