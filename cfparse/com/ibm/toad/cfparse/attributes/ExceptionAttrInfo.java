package com.ibm.toad.cfparse.attributes;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.utils.D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

public final class ExceptionAttrInfo extends AttrInfo {
   private int d_numExceptions;
   private int[] d_exceptions;

   public String get(int var1) {
      return var1 >= 0 && var1 < this.d_numExceptions && this.d_exceptions[var1] != 0 ? super.d_cp.getAsJava(this.d_exceptions[var1]) : "";
   }

   public void set(int var1, String var2) {
      if (var1 >= 0 && var1 < this.d_numExceptions && this.d_exceptions[var1] != 0) {
         super.d_cp.editClass(this.d_exceptions[var1], var2);
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(this.sindent() + "Attribute: " + super.d_cp.getAsString(super.d_idxName) + ": ");

      for(int var2 = 0; var2 < this.d_numExceptions; ++var2) {
         if (this.d_exceptions[var2] != 0) {
            var1.append(", " + super.d_cp.getAsJava(this.d_exceptions[var2]));
         }
      }

      return var1.append("\n").toString();
   }

   ExceptionAttrInfo(ConstantPool var1, int var2, int var3) {
      super(var1, var2, var3);
      super.d_len = 0;
      this.d_numExceptions = 0;
      this.d_exceptions = null;
   }

   public void add(String var1) {
      if (this.d_exceptions == null || this.d_numExceptions == this.d_exceptions.length) {
         this.resize();
      }

      this.d_exceptions[this.d_numExceptions] = super.d_cp.addClass(var1);
      ++this.d_numExceptions;
   }

   protected void read(DataInputStream var1) throws IOException {
      super.d_len = var1.readInt();
      this.d_numExceptions = var1.readShort();
      D.assert(super.d_len == 2 + this.d_numExceptions * 2, "d_len != 2 + (d_numExceptions * 2)\n" + super.d_len + " != 2 + (" + this.d_numExceptions + "* 2)\n");
      this.d_exceptions = new int[this.d_numExceptions];

      for(int var2 = 0; var2 < this.d_numExceptions; ++var2) {
         this.d_exceptions[var2] = var1.readShort();
      }

   }

   protected void sort(int[] var1) {
      super.sort(var1);

      for(int var2 = 0; var2 < this.d_numExceptions; ++var2) {
         this.d_exceptions[var2] = var1[this.d_exceptions[var2]];
      }

   }

   private void resize() {
      int[] var1 = new int[this.d_numExceptions + 10];
      if (this.d_exceptions != null) {
         System.arraycopy(this.d_exceptions, 0, var1, 0, this.d_numExceptions);
      }

      this.d_exceptions = var1;
   }

   protected void write(DataOutputStream var1) throws IOException {
      var1.writeShort(super.d_idxName);
      var1.writeInt(2 + this.d_numExceptions * 2);
      var1.writeShort(this.d_numExceptions);

      for(int var2 = 0; var2 < this.d_numExceptions; ++var2) {
         var1.writeShort(this.d_exceptions[var2]);
      }

   }

   protected BitSet uses() {
      BitSet var1 = super.uses();

      for(int var2 = 0; var2 < this.d_numExceptions; ++var2) {
         var1.set(this.d_exceptions[var2]);
      }

      return var1;
   }

   public int length() {
      return this.d_numExceptions;
   }

   public void remove(int var1) {
      if (var1 >= 0 && var1 < this.d_numExceptions) {
         for(int var2 = var1; var2 < this.d_numExceptions - 1; ++var2) {
            this.d_exceptions[var2] = this.d_exceptions[var2 + 1];
         }

         this.d_numExceptions += -1;
      }
   }
}
