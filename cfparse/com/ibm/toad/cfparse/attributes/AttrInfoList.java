package com.ibm.toad.cfparse.attributes;

import com.ibm.toad.cfparse.ConstantPool;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.BitSet;

public final class AttrInfoList {
   private ConstantPool d_cp;
   private int d_numAttrs;
   private AttrInfo[] d_attrs;
   private int d_depth;
   // $FF: synthetic field
   private static Class class$com$ibm$toad$cfparse$ConstantPool;

   public AttrInfo get(int var1) {
      return var1 >= 0 && var1 <= this.d_attrs.length - 1 ? this.d_attrs[var1] : null;
   }

   public AttrInfo get(String var1) {
      for(int var2 = 0; var2 < this.d_numAttrs; ++var2) {
         if (this.d_attrs[var2].getName().equals(var1)) {
            return this.d_attrs[var2];
         }
      }

      return null;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.d_numAttrs > 0) {
         this.d_depth += -1;
         var1.append(this.sindent());
         ++this.d_depth;
         var1.append("ATTRIBUTES:\n");

         for(int var2 = 0; var2 < this.d_numAttrs; ++var2) {
            var1.append(this.d_attrs[var2] + "\n");
         }
      }

      return var1.toString();
   }

   public AttrInfoList(ConstantPool var1, int var2) {
      this.d_cp = var1;
      this.d_numAttrs = 0;
      this.d_attrs = null;
      this.d_depth = var2;
   }

   private String sindent() {
      String var1 = "";

      for(int var2 = 0; var2 < this.d_depth; ++var2) {
         var1 = var1 + "  ";
      }

      return var1;
   }

   public AttrInfo add(String var1) {
      boolean var2 = false;
      if (this.d_attrs == null || this.d_numAttrs == this.d_attrs.length) {
         this.resize();
      }

      int var6;
      if ((var6 = this.d_cp.find(1, var1)) == -1) {
         var6 = this.d_cp.addUtf8(var1);
      }

      if (var1.equals("SourceFile")) {
         this.d_attrs[this.d_numAttrs] = new SourceFileAttrInfo(this.d_cp, var6, this.d_depth);
      } else if (var1.equals("SourceDir")) {
         this.d_attrs[this.d_numAttrs] = new SourceDirAttrInfo(this.d_cp, var6, this.d_depth);
      } else if (var1.equals("ConstantValue")) {
         this.d_attrs[this.d_numAttrs] = new ConstantValueAttrInfo(this.d_cp, var6, this.d_depth);
      } else if (var1.equals("Code")) {
         this.d_attrs[this.d_numAttrs] = new CodeAttrInfo(this.d_cp, var6, this.d_depth);
      } else if (var1.equals("Exceptions")) {
         this.d_attrs[this.d_numAttrs] = new ExceptionAttrInfo(this.d_cp, var6, this.d_depth);
      } else if (var1.equals("LineNumberTable")) {
         this.d_attrs[this.d_numAttrs] = new LineNumberAttrInfo(this.d_cp, var6, this.d_depth);
      } else if (var1.equals("LocalVariableTable")) {
         this.d_attrs[this.d_numAttrs] = new LocalVariableAttrInfo(this.d_cp, var6, this.d_depth);
      } else if (var1.equals("InnerClasses")) {
         this.d_attrs[this.d_numAttrs] = new InnerClassesAttrInfo(this.d_cp, var6, this.d_depth);
      } else if (var1.equals("Synthetic")) {
         this.d_attrs[this.d_numAttrs] = new SyntheticAttrInfo(this.d_cp, var6, this.d_depth);
      } else if (var1.equals("Deprecated")) {
         this.d_attrs[this.d_numAttrs] = new DeprecatedAttrInfo(this.d_cp, var6, this.d_depth);
      } else {
         try {
            Class var3 = Class.forName(var1 + "AttrInfo");
            Constructor var4 = var3.getConstructor(class$com$ibm$toad$cfparse$ConstantPool != null ? class$com$ibm$toad$cfparse$ConstantPool : (class$com$ibm$toad$cfparse$ConstantPool = class$("com.ibm.toad.cfparse.ConstantPool")), Integer.TYPE, Integer.TYPE);
            this.d_attrs[this.d_numAttrs] = (AttrInfo)var4.newInstance(this.d_cp, new Integer(var6), new Integer(this.d_depth));
         } catch (Exception var5) {
            this.d_attrs[this.d_numAttrs] = new UnknownAttrInfo(this.d_cp, var6, this.d_depth);
         }
      }

      ++this.d_numAttrs;
      return this.d_attrs[this.d_numAttrs - 1];
   }

   public void read(DataInputStream var1) throws IOException {
      this.d_numAttrs = var1.readShort();
      this.d_attrs = new AttrInfo[this.d_numAttrs];

      for(int var2 = 0; var2 < this.d_numAttrs; ++var2) {
         short var3 = var1.readShort();
         String var4 = this.d_cp.getAsString(var3);
         if (var4.equals("SourceFile")) {
            this.d_attrs[var2] = new SourceFileAttrInfo(this.d_cp, var3, this.d_depth);
         } else if (var4.equals("ConstantValue")) {
            this.d_attrs[var2] = new ConstantValueAttrInfo(this.d_cp, var3, this.d_depth);
         } else if (var4.equals("Code")) {
            this.d_attrs[var2] = new CodeAttrInfo(this.d_cp, var3, this.d_depth);
         } else if (var4.equals("Exceptions")) {
            this.d_attrs[var2] = new ExceptionAttrInfo(this.d_cp, var3, this.d_depth);
         } else if (var4.equals("LineNumberTable")) {
            this.d_attrs[var2] = new LineNumberAttrInfo(this.d_cp, var3, this.d_depth);
         } else if (var4.equals("LocalVariableTable")) {
            this.d_attrs[var2] = new LocalVariableAttrInfo(this.d_cp, var3, this.d_depth);
         } else if (var4.equals("InnerClasses")) {
            this.d_attrs[var2] = new InnerClassesAttrInfo(this.d_cp, var3, this.d_depth);
         } else if (var4.equals("SourceDir")) {
            this.d_attrs[var2] = new SourceDirAttrInfo(this.d_cp, var3, this.d_depth);
         } else if (var4.equals("Synthetic")) {
            this.d_attrs[var2] = new SyntheticAttrInfo(this.d_cp, var3, this.d_depth);
         } else if (var4.equals("Deprecated")) {
            this.d_attrs[var2] = new DeprecatedAttrInfo(this.d_cp, var3, this.d_depth);
         } else {
            try {
               Class var5 = Class.forName(var4 + "AttrInfo");
               Constructor var6 = var5.getConstructor(class$com$ibm$toad$cfparse$ConstantPool != null ? class$com$ibm$toad$cfparse$ConstantPool : (class$com$ibm$toad$cfparse$ConstantPool = class$("com.ibm.toad.cfparse.ConstantPool")), Integer.TYPE, Integer.TYPE);
               this.d_attrs[var2] = (AttrInfo)var6.newInstance(this.d_cp, new Integer(var3), new Integer(this.d_depth));
            } catch (Exception var7) {
               this.d_attrs[var2] = new UnknownAttrInfo(this.d_cp, var3, this.d_depth);
            }
         }

         this.d_attrs[var2].read(var1);
      }

   }

   public void sort(int[] var1) {
      for(int var2 = 0; var2 < this.d_numAttrs; ++var2) {
         this.d_attrs[var2].sort(var1);
      }

   }

   private void resize() {
      AttrInfo[] var1 = new AttrInfo[this.d_numAttrs + 10];
      if (this.d_attrs != null) {
         System.arraycopy(this.d_attrs, 0, var1, 0, this.d_numAttrs);
      }

      this.d_attrs = var1;
   }

   public int size() {
      int var1 = 2;

      for(int var2 = 0; var2 < this.d_numAttrs; ++var2) {
         var1 += this.d_attrs[var2].size();
      }

      return var1;
   }

   public void write(DataOutputStream var1) throws IOException {
      var1.writeShort(this.d_numAttrs);

      for(int var2 = 0; var2 < this.d_numAttrs; ++var2) {
         this.d_attrs[var2].write(var1);
      }

   }

   // $FF: synthetic method
   private static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public BitSet uses() {
      BitSet var1 = new BitSet(this.d_cp.length());

      for(int var2 = 0; var2 < this.d_numAttrs; ++var2) {
         var1.or(this.d_attrs[var2].uses());
      }

      return var1;
   }

   public String getName(int var1) {
      return var1 >= 0 && var1 <= this.d_attrs.length - 1 ? this.d_attrs[var1].getName() : null;
   }

   int depth() {
      return this.d_depth;
   }

   public int length() {
      return this.d_numAttrs;
   }

   public void remove(String var1) {
      for(int var2 = 0; var2 < this.d_numAttrs; ++var2) {
         if (this.d_attrs[var2].getName().equals(var1)) {
            this.remove(var2);
         }
      }

   }

   public void remove(int var1) {
      if (var1 >= 0 && var1 <= this.d_numAttrs - 1) {
         for(int var2 = var1; var2 < this.d_numAttrs - 1; ++var2) {
            this.d_attrs[var2] = this.d_attrs[var2 + 1];
         }

         this.d_numAttrs += -1;
      }
   }
}
