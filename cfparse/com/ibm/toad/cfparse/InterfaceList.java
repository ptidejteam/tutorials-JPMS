package com.ibm.toad.cfparse;

import com.ibm.toad.cfparse.utils.CPUtils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

public final class InterfaceList {
   private ConstantPool d_cp;
   private int d_numInterfaces;
   private int[] d_interfaces;

   public String get(int var1) {
      return var1 >= 0 && var1 <= this.d_numInterfaces ? this.d_cp.getAsJava(this.d_interfaces[var1]) : null;
   }

   public void set(int var1, String var2) {
      if (var1 >= 0 && var1 <= this.d_numInterfaces - 1) {
         var2 = CPUtils.dots2slashes(var2);
         int var3 = this.d_cp.find(7, var2);
         if (var3 == -1) {
            var3 = this.d_cp.addClass(var2);
         }

         this.d_interfaces[var1] = var3;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.d_numInterfaces > 0) {
         var1.append("implements ");

         for(int var2 = 0; var2 < this.d_numInterfaces; ++var2) {
            if (var2 > 0) {
               var1.append(", ");
            }

            var1.append(this.d_cp.getAsJava(this.d_interfaces[var2]));
         }

         var1.append("\n");
      }

      return var1.toString();
   }

   InterfaceList(ConstantPool var1) {
      this.d_cp = var1;
      this.d_numInterfaces = 0;
      this.d_interfaces = null;
   }

   void read(DataInputStream var1) throws IOException {
      this.d_numInterfaces = var1.readShort();
      this.d_interfaces = new int[this.d_numInterfaces];

      for(int var2 = 0; var2 < this.d_numInterfaces; ++var2) {
         this.d_interfaces[var2] = var1.readShort();
      }

   }

   public void add(String var1) {
      if (this.d_interfaces == null || this.d_numInterfaces + 1 >= this.d_interfaces.length) {
         this.resize();
      }

      var1 = CPUtils.dots2slashes(var1);
      int var2 = this.d_cp.find(7, var1);
      if (var2 == -1) {
         var2 = this.d_cp.addClass(var1);
      }

      this.d_interfaces[this.d_numInterfaces++] = var2;
   }

   public void sort(int[] var1) {
      for(int var2 = 0; var2 < this.d_numInterfaces; ++var2) {
         this.d_interfaces[var2] = var1[this.d_interfaces[var2]];
      }

   }

   private void resize() {
      int[] var1 = new int[this.d_numInterfaces + 10];
      if (this.d_interfaces != null) {
         System.arraycopy(this.d_interfaces, 0, var1, 0, this.d_numInterfaces);
      }

      this.d_interfaces = var1;
   }

   public String getInterfaceName(int var1) {
      return var1 >= 0 && var1 <= this.d_numInterfaces ? this.d_cp.getAsJava(this.d_interfaces[var1]) : null;
   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.d_numInterfaces);

      for(int var2 = 0; var2 < this.d_numInterfaces; ++var2) {
         var1.writeShort(this.d_interfaces[var2]);
      }

   }

   public BitSet uses() {
      BitSet var1 = new BitSet(this.d_cp.length());

      for(int var2 = 0; var2 < this.d_numInterfaces; ++var2) {
         var1.set(this.d_interfaces[var2]);
      }

      return var1;
   }

   public int length() {
      return this.d_numInterfaces;
   }

   public void remove(int var1) {
      if (var1 >= 0 && var1 <= this.d_numInterfaces - 1) {
         for(int var2 = var1; var2 < this.d_numInterfaces - 1; ++var2) {
            this.d_interfaces[var2] = this.d_interfaces[var2 + 1];
         }

         this.d_numInterfaces += -1;
      }
   }
}
