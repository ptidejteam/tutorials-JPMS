package com.ibm.toad.cfparse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

public final class FieldInfoList {
   private ConstantPool d_cp;
   private int d_numFields;
   private FieldInfo[] d_fields;

   public FieldInfo get(int var1) {
      return var1 >= 0 && var1 <= this.d_numFields ? this.d_fields[var1] : null;
   }

   public FieldInfo addStatic(ClassFile var1, String var2) {
      if (this.d_fields == null || this.d_numFields == this.d_fields.length) {
         this.resize();
      }

      this.d_fields[this.d_numFields] = new FieldInfo(var1, this.d_cp, var2);
      ++this.d_numFields;
      return this.d_fields[this.d_numFields - 1];
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("FIELDS:\n");
      if (this.d_numFields == 0) {
         var1.append("  <none>\n");
      } else {
         for(int var2 = 0; var2 < this.d_numFields; ++var2) {
            var1.append("  " + this.d_fields[var2]);
         }
      }

      return var1.toString();
   }

   FieldInfoList(ConstantPool var1) {
      this.d_cp = var1;
      this.d_numFields = 0;
      this.d_fields = null;
   }

   void read(DataInputStream var1) throws IOException {
      this.d_numFields = var1.readShort();
      this.d_fields = new FieldInfo[this.d_numFields];

      for(int var2 = 0; var2 < this.d_numFields; ++var2) {
         this.d_fields[var2] = new FieldInfo(this.d_cp);
         this.d_fields[var2].read(var1);
      }

   }

   public FieldInfo add(String var1) {
      if (this.d_fields == null || this.d_numFields == this.d_fields.length) {
         this.resize();
      }

      this.d_fields[this.d_numFields] = new FieldInfo(this.d_cp, var1);
      ++this.d_numFields;
      return this.d_fields[this.d_numFields - 1];
   }

   public void sort(int[] var1) {
      for(int var2 = 0; var2 < this.d_numFields; ++var2) {
         this.d_fields[var2].sort(var1);
      }

   }

   private void resize() {
      FieldInfo[] var1 = new FieldInfo[this.d_numFields + 10];
      if (this.d_fields != null) {
         System.arraycopy(this.d_fields, 0, var1, 0, this.d_numFields);
      }

      this.d_fields = var1;
   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.d_numFields);

      for(int var2 = 0; var2 < this.d_numFields; ++var2) {
         this.d_fields[var2].write(var1);
      }

   }

   public BitSet uses() {
      BitSet var1 = new BitSet(this.d_cp.length());

      for(int var2 = 0; var2 < this.d_numFields; ++var2) {
         var1.or(this.d_fields[var2].uses());
      }

      return var1;
   }

   public int length() {
      return this.d_numFields;
   }

   public void remove(int var1) {
      if (var1 >= 0 && var1 <= this.d_numFields - 1) {
         for(int var2 = var1; var2 < this.d_numFields - 1; ++var2) {
            this.d_fields[var2] = this.d_fields[var2 + 1];
         }

         this.d_numFields += -1;
      }
   }

   public String getFieldName(int var1) {
      return var1 >= 0 && var1 <= this.d_numFields ? this.d_fields[var1].getName() : null;
   }
}
