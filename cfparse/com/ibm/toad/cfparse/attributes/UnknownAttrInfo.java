package com.ibm.toad.cfparse.attributes;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.utils.ByteArray;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class UnknownAttrInfo extends AttrInfo {
   private byte[] d_info;

   public byte[] get() {
      return this.d_info;
   }

   public void set(byte[] var1) {
      super.d_len = var1.length;
      this.d_info = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(super.toString() + "  bytes (" + super.d_len + "): ");

      for(int var2 = 0; var2 < super.d_len; ++var2) {
         int var3 = ByteArray.getByteAtOffset(this.d_info, var2);
         var1.append(Integer.toHexString(var3) + " ");
      }

      var1.append("\n");
      return var1.toString();
   }

   UnknownAttrInfo(ConstantPool var1, int var2, int var3) {
      super(var1, var2, var3);
      super.d_len = 0;
      this.d_info = null;
   }

   protected void read(DataInputStream var1) throws IOException {
      super.d_len = var1.readInt();
      this.d_info = new byte[super.d_len];
      var1.readFully(this.d_info);
   }

   protected void write(DataOutputStream var1) throws IOException {
      var1.writeShort(super.d_idxName);
      var1.writeInt(super.d_len);
      if (this.d_info != null) {
         var1.write(this.d_info, 0, super.d_len);
      }

   }
}
