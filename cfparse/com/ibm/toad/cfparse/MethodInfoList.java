package com.ibm.toad.cfparse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

public final class MethodInfoList {
   private ConstantPool d_cp;
   private int d_numMethods;
   private MethodInfo[] d_methods;

   public MethodInfo get(int var1) {
      return this.d_methods[var1];
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("METHODS:\n");
      if (this.d_numMethods == 0) {
         var1.append("  <none>\n");
      } else {
         for(int var2 = 0; var2 < this.d_numMethods; ++var2) {
            var1.append("  " + this.d_methods[var2]);
         }
      }

      return var1.toString();
   }

   MethodInfoList(ConstantPool var1) {
      this.d_cp = var1;
      this.d_numMethods = 0;
      this.d_methods = null;
   }

   void read(DataInputStream var1) throws IOException {
      this.d_numMethods = var1.readShort();
      this.d_methods = new MethodInfo[this.d_numMethods];

      for(int var2 = 0; var2 < this.d_numMethods; ++var2) {
         this.d_methods[var2] = new MethodInfo(this.d_cp);
         this.d_methods[var2].read(var1);
      }

   }

   public MethodInfo add(String var1) {
      if (this.d_methods == null || this.d_numMethods == this.d_methods.length) {
         this.resize();
      }

      this.d_methods[this.d_numMethods] = new MethodInfo(this.d_cp, var1);
      ++this.d_numMethods;
      return this.d_methods[this.d_numMethods - 1];
   }

   public void sort(int[] var1) {
      for(int var2 = 0; var2 < this.d_numMethods; ++var2) {
         this.d_methods[var2].sort(var1);
      }

   }

   private void resize() {
      MethodInfo[] var1 = new MethodInfo[this.d_numMethods + 10];
      if (this.d_methods != null) {
         System.arraycopy(this.d_methods, 0, var1, 0, this.d_numMethods);
      }

      this.d_methods = var1;
   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.d_numMethods);

      for(int var2 = 0; var2 < this.d_numMethods; ++var2) {
         this.d_methods[var2].write(var1);
      }

   }

   public BitSet uses() {
      BitSet var1 = new BitSet(this.d_cp.length());

      for(int var2 = 0; var2 < this.d_numMethods; ++var2) {
         var1.or(this.d_methods[var2].uses());
      }

      return var1;
   }

   public int length() {
      return this.d_numMethods;
   }

   public void remove(int var1) {
      if (var1 >= 0 && var1 <= this.d_numMethods - 1) {
         for(int var2 = var1; var2 < this.d_numMethods - 1; ++var2) {
            this.d_methods[var2] = this.d_methods[var2 + 1];
         }

         this.d_numMethods += -1;
      }
   }

   public String getMethodName(int var1) {
      return var1 >= 0 && var1 <= this.d_numMethods ? this.d_methods[var1].getName() : null;
   }
}
