package com.ibm.toad.cfparse.attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.ibm.toad.cfparse.ConstantPool;

public final class SyntheticAttrInfo extends AttrInfo {
   public String toString() {
      return this.sindent() + "Attribute: " + super.d_cp.getAsString(super.d_idxName) + "\n";
   }

   SyntheticAttrInfo(ConstantPool var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   protected void read(DataInputStream var1) throws IOException {
      super.d_len = var1.readInt();
      //D.assert(super.d_len == 0, "d_len != 0 : " + super.d_len);
   }

   protected void write(DataOutputStream var1) throws IOException {
      var1.writeShort(super.d_idxName);
      var1.writeInt(0);
   }
}
