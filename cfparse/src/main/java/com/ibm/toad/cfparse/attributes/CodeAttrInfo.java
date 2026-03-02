package com.ibm.toad.cfparse.attributes;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.instruction.BaseInstruction;
import com.ibm.toad.cfparse.instruction.CodeViewer;
import com.ibm.toad.cfparse.instruction.ImmutableCodeSegment;
import com.ibm.toad.cfparse.instruction.InstructionFactory;
import com.ibm.toad.cfparse.instruction.InstructionFormatException;
import com.ibm.toad.cfparse.instruction.MutableCodeSegment;
import com.ibm.toad.cfparse.instruction.TagFactory;
import com.ibm.toad.cfparse.utils.ByteArray;
import com.ibm.toad.cfparse.utils.CPUtils;
import com.ibm.toad.utils.D;
import com.ibm.toad.utils.HexDump;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.Vector;

public final class CodeAttrInfo extends AttrInfo {
   private int d_maxStack;
   private int d_maxLocals;
   private static CodeViewer d_viewer;
   private byte[] d_code;
   private int d_numExceptions;
   private CodeAttrInfo.ExceptionInfo[] d_exceptions;
   private AttrInfoList d_attrs;

   public void setException(CodeAttrInfo.ExceptionInfo var1, int var2) {
      if (var2 >= 0 && var2 <= this.d_numExceptions) {
         this.d_exceptions[var2] = var1;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(this.sindent() + "Attribute: " + super.d_cp.getAsString(super.d_idxName) + "\n" + this.sindent() + "// max_stack: " + this.d_maxStack + "\n" + this.sindent() + "// max_locals: " + this.d_maxLocals + "\n");
      if (d_viewer != null) {
         var1.append(d_viewer.view(super.d_cp, this.d_code, this.d_exceptions, this.d_numExceptions, this.sindent()));
      } else {
         var1.append(HexDump.toBytes(this.d_code));
         var1.append(this.sindent() + "Exception Table:\n");
         if (this.d_numExceptions == 0) {
            var1.append(this.sindent() + "  <none>\n");
         } else {
            for(int var2 = 0; var2 < this.d_numExceptions; ++var2) {
               var1.append(this.sindent() + this.d_exceptions[var2] + "\n");
            }
         }
      }

      var1.append(this.d_attrs + "\n");
      return var1.toString();
   }

   CodeAttrInfo(ConstantPool var1, int var2, int var3) {
      super(var1, var2, var3);
      super.d_len = 0;
      this.d_maxStack = 2;
      this.d_maxLocals = 2;
      this.d_numExceptions = 0;
      this.d_code = new byte[0];
      this.d_exceptions = null;
      this.d_attrs = new AttrInfoList(var1, super.d_depth + 1);
   }

   protected void read(DataInputStream var1) throws IOException {
      super.d_len = var1.readInt();
      this.d_maxStack = var1.readShort();
      this.d_maxLocals = var1.readShort();
      int var2 = var1.readInt();
      this.d_code = new byte[var2];
      var1.readFully(this.d_code);
      this.d_numExceptions = var1.readShort();
      this.d_exceptions = new CodeAttrInfo.ExceptionInfo[this.d_numExceptions];

      for(int var3 = 0; var3 < this.d_numExceptions; ++var3) {
         this.d_exceptions[var3] = new CodeAttrInfo.ExceptionInfo(super.d_cp);
         this.d_exceptions[var3].read(var1);
      }

      this.d_attrs.read(var1);
      ////D.assert(super.d_len == 8 + var2 + 2 + this.d_numExceptions * 8 + this.d_attrs.size(), "d_len != 2 + 2 + 4 + codeLen + 2 + d_numExceptions * 8 + d_attrs.size()\n" + super.d_len + " != 2 + 2 + 4 + " + var2 + " + 2 + " + this.d_numExceptions + " * 8 + " + this.d_attrs.size());
   }

   public int getMaxLocals() {
      return this.d_maxLocals;
   }

   public void setMaxLocals(int var1) {
      this.d_maxLocals = var1;
   }

   protected void sort(int[] var1) {
      super.sort(var1);
      if (this.d_exceptions != null) {
         for(int var2 = 0; var2 < this.d_exceptions.length; ++var2) {
            this.d_exceptions[var2].sort(var1);
         }
      }

      MutableCodeSegment var10 = new MutableCodeSegment(super.d_cp, this, false);
      var10.setInstructionFactory(new CodeAttrInfo.RawByteInstructionFactory(this));
      Vector var3 = var10.getInstructions();

      for(int var4 = 0; var4 < var3.size(); ++var4) {
         BaseInstruction var5 = (BaseInstruction)var3.get(var4);
         byte[] var6 = var5.getCode((int[])null, 0);
         int var7 = var6[0] & 255;
         int var8;
         byte[] var9;
         switch(var7) {
         case 18:
            var8 = var1[ByteArray.getByteAtOffset(var6, 1)];
            if (var8 > 255) {
               var9 = new byte[]{19, (byte)(var8 >>> 8 & 255), (byte)(var8 & 255)};
               var6 = var9;
            } else {
               var6[1] = (byte)var8;
            }

            var3.set(var4, new CodeAttrInfo.RawByteInstr(this, var6, var5.getTag()));
            break;
         case 19:
            var8 = var1[ByteArray.getShortAtOffset(var6, 1)];
            if (var8 < 255) {
               var9 = new byte[]{18, (byte)var8};
               var6 = var9;
            } else {
               var6[1] = (byte)(var8 >>> 8 & 255);
               var6[2] = (byte)(var8 & 255);
            }

            var3.set(var4, new CodeAttrInfo.RawByteInstr(this, var6, var5.getTag()));
            break;
         case 20:
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 185:
         case 187:
         case 189:
         case 192:
         case 193:
         case 197:
            var8 = var1[ByteArray.getShortAtOffset(var6, 1)];
            var6[1] = (byte)(var8 >>> 8 & 255);
            var6[2] = (byte)(var8 & 255);
            var3.set(var4, new CodeAttrInfo.RawByteInstr(this, var6, var5.getTag()));
         }
      }

      this.setCode(var10.getCode());
      if (this.d_exceptions != null) {
         this.d_exceptions = var10.getExcTable();
      }

      this.d_attrs.sort(var1);
   }

   public static void setViewer(CodeViewer var0) {
      d_viewer = var0;
   }

   private BitSet codeUses() {
      BitSet var1 = new BitSet(super.d_cp.length());
      ImmutableCodeSegment var2 = new ImmutableCodeSegment(this.d_code);

      for(int var3 = 0; var3 < var2.getNumInstructions(); ++var3) {
         int var4 = var2.getOffset(var3);
         int var5 = this.d_code[var4] & 255;
         switch(var5) {
         case 18:
            var1.set(ByteArray.getByteAtOffset(this.d_code, 1 + var4));
            break;
         case 19:
         case 20:
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 185:
         case 187:
         case 189:
         case 192:
         case 193:
         case 197:
            var1.set(ByteArray.getShortAtOffset(this.d_code, 1 + var4));
         }
      }

      return var1;
   }

   private void resize() {
      CodeAttrInfo.ExceptionInfo[] var1 = new CodeAttrInfo.ExceptionInfo[this.d_numExceptions + 10];
      if (this.d_exceptions != null) {
         System.arraycopy(this.d_exceptions, 0, var1, 0, this.d_numExceptions);
      }

      this.d_exceptions = var1;
   }

   protected int size() {
      return 8 + this.d_code.length + 2 + this.d_numExceptions * 8 + this.d_attrs.size();
   }

   protected void write(DataOutputStream var1) throws IOException {
      var1.writeShort(super.d_idxName);
      var1.writeInt(8 + this.d_code.length + 2 + this.d_numExceptions * 8 + this.d_attrs.size());
      var1.writeShort(this.d_maxStack);
      var1.writeShort(this.d_maxLocals);
      var1.writeInt(this.d_code.length);
      var1.write(this.d_code, 0, this.d_code.length);
      var1.writeShort(this.d_numExceptions);

      for(int var2 = 0; var2 < this.d_numExceptions; ++var2) {
         this.d_exceptions[var2].write(var1);
      }

      this.d_attrs.write(var1);
   }

   public AttrInfoList getAttrs() {
      return this.d_attrs;
   }

   public void setAttrs(AttrInfoList var1) {
      this.d_attrs = var1;
   }

   protected BitSet uses() {
      BitSet var1 = super.uses();
      var1.or(this.codeUses());
      if (this.d_exceptions != null) {
         for(int var2 = 0; var2 < this.d_exceptions.length; ++var2) {
            String var3 = this.d_exceptions[var2].getCatchType();
            if (!var3.equals("all")) {
               int var4 = super.d_cp.find(7, CPUtils.dots2slashes(var3));
               //D.assert(var4 != -1, "Couldn't find " + CPUtils.dots2slashes(var3) + "+in ConstantPool");
               var1.set(var4);
            }
         }
      }

      var1.or(this.d_attrs.uses());
      return var1;
   }

   public int getNumExceptions() {
      return this.d_numExceptions;
   }

   public CodeAttrInfo.ExceptionInfo[] getExceptions() {
      return this.d_exceptions;
   }

   public void setExceptions(CodeAttrInfo.ExceptionInfo[] var1) {
      this.d_exceptions = var1;
      this.d_numExceptions = var1 == null ? 0 : var1.length;
   }

   public int getMaxStack() {
      return this.d_maxStack;
   }

   public void setMaxStack(int var1) {
      this.d_maxStack = var1;
   }

   public void createExceptionBlock(int var1, int var2, int var3, String var4) {
      if (this.d_exceptions == null || this.d_numExceptions == this.d_exceptions.length) {
         this.resize();
      }

      this.d_exceptions[this.d_numExceptions] = new CodeAttrInfo.ExceptionInfo(super.d_cp, var1, var2, var3, var4);
      ++this.d_numExceptions;
   }

   public void setCode(byte[] var1) {
      ////D.assert(this.d_code != null, "cannot set null code segment");
      this.d_code = var1;
   }

   public byte[] getCode() {
      return this.d_code;
   }

   public CodeAttrInfo.ExceptionInfo getException(int var1) {
      return var1 >= 0 && var1 <= this.d_numExceptions ? this.d_exceptions[var1] : null;
   }

   public static class ExceptionInfo {
      private ConstantPool d_cp;
      private int d_startPC;
      private int d_endPC;
      private int d_handlerPC;
      private int d_catchType;

      public int getHandler() {
         return this.d_handlerPC;
      }

      public void setHandler(int var1) {
         this.d_handlerPC = var1;
      }

      public int getEnd() {
         return this.d_endPC;
      }

      public void setEnd(int var1) {
         this.d_endPC = var1;
      }

      public String toString() {
         return "(" + this.d_startPC + "," + this.d_endPC + ") ->" + this.d_handlerPC + " on " + (this.d_catchType == 0 ? "all" : this.d_cp.getAsJava(this.d_catchType));
      }

      ExceptionInfo(ConstantPool var1) {
         this.d_cp = var1;
      }

      public ExceptionInfo(ConstantPool var1, int var2, int var3, int var4, int var5) {
         this.d_cp = var1;
         this.d_startPC = var2;
         this.d_endPC = var3;
         this.d_handlerPC = var4;
         this.d_catchType = var5;
      }

      public ExceptionInfo(ConstantPool var1, int var2, int var3, int var4, String var5) {
         this.d_cp = var1;
         this.d_startPC = var2;
         this.d_endPC = var3;
         this.d_handlerPC = var4;
         if (var5.equals("all")) {
            this.d_catchType = 0;
         } else {
            this.d_catchType = this.d_cp.find(7, CPUtils.dots2slashes(var5));
            if (this.d_catchType == -1) {
               this.d_catchType = this.d_cp.addClass(CPUtils.dots2slashes(var5));
            }
         }

      }

      protected void read(DataInputStream var1) throws IOException {
         this.d_startPC = var1.readShort();
         this.d_endPC = var1.readShort();
         this.d_handlerPC = var1.readShort();
         this.d_catchType = var1.readShort();
      }

      public int getStart() {
         return this.d_startPC;
      }

      public void setStart(int var1) {
         this.d_startPC = var1;
      }

      protected void sort(int[] var1) {
         if (this.d_catchType != 0) {
            this.d_catchType = var1[this.d_catchType];
         }

      }

      public int getCatch() {
         return this.d_catchType;
      }

      public String getCatchType() {
         return this.d_catchType == 0 ? "all" : this.d_cp.getAsJava(this.d_catchType);
      }

      public void setCatchType(String var1) {
         if (var1.equals("all")) {
            this.d_catchType = 0;
         } else {
            this.d_catchType = this.d_cp.find(7, CPUtils.dots2slashes(var1));
            if (this.d_catchType == -1) {
               this.d_catchType = this.d_cp.addClass(CPUtils.dots2slashes(var1));
            }
         }

      }

      protected void write(DataOutputStream var1) throws IOException {
         var1.writeShort(this.d_startPC);
         var1.writeShort(this.d_endPC);
         var1.writeShort(this.d_handlerPC);
         var1.writeShort(this.d_catchType);
      }

      public boolean equals(Object var1) {
         if (!(var1 instanceof CodeAttrInfo.ExceptionInfo)) {
            return false;
         } else {
            CodeAttrInfo.ExceptionInfo var2 = (CodeAttrInfo.ExceptionInfo)var1;
            return var2.getStart() == this.d_startPC && var2.getEnd() == this.d_endPC && var2.getCatchType().equals(this.getCatchType()) && var2.getHandler() == this.d_handlerPC;
         }
      }
   }

   class RawByteInstructionFactory implements InstructionFactory {
      // $FF: synthetic field
      final CodeAttrInfo this$0;

      public BaseInstruction create(TagFactory var1, ConstantPool var2, byte[] var3, int var4, int var5) {
         byte[] var6 = new byte[var5];
         System.arraycopy(var3, var4, var6, 0, var5);
         return this.this$0.new RawByteInstr(this.this$0, var6, (String)null);
      }

      public BaseInstruction create(TagFactory var1, ConstantPool var2, String var3) throws InstructionFormatException {
         throw new InstructionFormatException("Cannot create RawByteInstr from String..");
      }

      RawByteInstructionFactory(CodeAttrInfo var1) {
         (this.this$0 = var1).getClass();
      }
   }

   class RawByteInstr implements BaseInstruction {
      byte[] d_code;
      String d_tag;
      // $FF: synthetic field
      final CodeAttrInfo this$0;

      RawByteInstr(CodeAttrInfo var1, byte[] var2, String var3) {
         (this.this$0 = var1).getClass();
         this.d_code = var2;
         this.d_tag = var3;
      }

      public int getOpCode() {
         return this.d_code[0] & 255;
      }

      public String getTag() {
         return this.d_tag;
      }

      public void setTag(String var1) {
         this.d_tag = var1;
      }

      public int getLength(int var1) {
         return this.d_code.length;
      }

      public byte[] getCode(int[] var1, int var2) {
         return this.d_code;
      }
   }
}
