package com.ibm.toad.cfparse.attributes;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.utils.D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

public final class ConstantValueAttrInfo extends AttrInfo {
   private int d_idxCP;

   public void set(String var1) {
      this.d_idxCP = super.d_cp.addString(var1);
   }

   public void set(int var1) {
      this.d_idxCP = super.d_cp.addInteger(var1);
   }

   public void set(float var1) {
      this.d_idxCP = super.d_cp.addFloat(var1);
   }

   public void set(long var1) {
      this.d_idxCP = super.d_cp.addLong(var1);
   }

   public String toString() {
      return this.sindent() + "Attribute: " + super.d_cp.getAsString(super.d_idxName) + ": " + super.d_cp.getAsString(this.d_idxCP) + "\n";
   }

   ConstantValueAttrInfo(ConstantPool var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   protected void read(DataInputStream var1) throws IOException {
      super.d_len = var1.readInt();
      D.assert(super.d_len == 2, "d_len != 2 : " + super.d_len);
      this.d_idxCP = var1.readShort();
   }

   public void set(double var1) {
      this.d_idxCP = super.d_cp.addDouble(var1);
   }

   public String get() {
      return super.d_cp.getAsString(this.d_idxCP);
   }

   protected void sort(int[] var1) {
      super.sort(var1);
      this.d_idxCP = var1[this.d_idxCP];
   }

   protected void write(DataOutputStream var1) throws IOException {
      var1.writeShort(super.d_idxName);
      var1.writeInt(2);
      var1.writeShort(this.d_idxCP);
   }

   protected BitSet uses() {
      BitSet var1 = super.uses();
      var1.set(this.d_idxCP);
      return var1;
   }

   public int getType() {
      return super.d_cp.getType(this.d_idxCP);
   }
}
