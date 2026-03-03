package com.ibm.toad.cfparse.instruction;

import java.util.BitSet;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.utils.ByteArray;

final class ByteInstruction implements BaseInstruction {
   private int d_opCode;
   private byte[] d_code;
   private BitSet d_uses;
   private String d_tag;
   private ConstantPool d_cp;
   private TagFactory d_fac;
   private boolean marked = false;

   public String toString() {
      JVMInstruction var2 = JVMInstruction.d_instrTable[this.d_opCode];
      StringBuffer var3 = new StringBuffer(var2.name() + " ");
      byte var1;
      int var4;
      int var5;
      int var7;
      if (this.d_opCode == 170) {
         var1 = 1;
         this.sprintOperand(var3, var1, 'A');
         var7 = var1 + 4;
         var3.append(" ");
         var4 = ByteArray.getIntAtOffset(this.d_code, var7);
         this.sprintOperand(var3, var7, 'l');
         var7 += 4;
         var3.append("-");
         var5 = ByteArray.getIntAtOffset(this.d_code, var7);
         this.sprintOperand(var3, var7, 'l');
         var7 += 4;
         var3.append(" ");

         for(int var6 = var4; var6 <= var5; ++var6) {
            this.sprintOperand(var3, var7, 'A');
            var7 += 4;
            var3.append(" ");
         }
      } else if (this.d_opCode == 171) {
         var1 = 1;
         this.sprintOperand(var3, var1, 'A');
         var7 = var1 + 4;
         var3.append(" ");
         var4 = ByteArray.getIntAtOffset(this.d_code, var7);
         this.sprintOperand(var3, var7, 'l');
         var7 += 4;
         var3.append(" ");

         for(var5 = 0; var5 < var4; ++var5) {
            var3.append("(");
            this.sprintOperand(var3, var7, 'l');
            var7 += 4;
            var3.append(", ");
            this.sprintOperand(var3, var7, 'A');
            var7 += 4;
            var3.append(") ");
         }
      } else if (this.d_opCode == 196) {
         var4 = ByteArray.getByteAtOffset(this.d_code, 1);
         JVMInstruction var8 = JVMInstruction.d_instrTable[var4];
         var3.append(var8.name() + " ");
         this.sprintOperand(var3, 2, 'V');
         var3.append(" ");
         if (var4 == 132) {
            this.sprintOperand(var3, 4, 'I');
         }
      } else {
         var7 = 1;
         var4 = var2.d_operandTypes.length();

         for(var5 = 0; var5 < var4; ++var5) {
            this.sprintOperand(var3, var7, var2.d_operandTypes.charAt(var5));
            var7 += JVMInstruction.argSkip(var2.d_operandTypes.charAt(var5));
            var3.append(" ");
         }
      }

      return var3.toString();
   }

   ByteInstruction(TagFactory var1, ConstantPool var2, byte[] var3, int var4, int var5) {
      this.d_cp = var2;
      this.d_code = new byte[var5];
      System.arraycopy(var3, var4, this.d_code, 0, var5);
      this.d_opCode = var3[var4] & 255;
      this.d_fac = var1;
      this.d_tag = null;
   }

   public int getOpCode() {
      return this.d_code[0] & 255;
   }

   ByteInstruction(TagFactory var1, ConstantPool var2, String var3) throws InstructionFormatException {
      throw new InstructionFormatException("Cannot create ByteInstruction from String...");
   }

   void sprintOperand(StringBuffer var1, int var2, char var3) {
      boolean var4 = false;
      int var5;
      switch(var3) {
      case '0':
      case 'i':
         var5 = ByteArray.getByteAtOffset(this.d_code, var2);
         if (var5 > 127) {
            var5 -= 256;
         }

         var1.append("#" + var5);
         break;
      case 'A':
         var5 = ByteArray.getIntAtOffset(this.d_code, var2);
         var1.append(this.d_fac.getTag(var5));
         break;
      case 'C':
         var5 = ByteArray.getShortAtOffset(this.d_code, var2);
         var1.append(this.d_cp.getAsJava(var5));
         break;
      case 'I':
         var5 = ByteArray.getShortAtOffset(this.d_code, var2);
         if (var5 > 32767) {
            var5 -= 65536;
         }

         var1.append("#" + var5);
         break;
      case 'V':
         var5 = ByteArray.getShortAtOffset(this.d_code, var2);
         var1.append("v" + var5);
         break;
      case 'a':
         var5 = ByteArray.getSignedShortAtOffset(this.d_code, var2);
         var1.append(this.d_fac.getTag(var5));
         break;
      case 'c':
         var5 = ByteArray.getByteAtOffset(this.d_code, var2);
         var1.append(this.d_cp.getAsJava(var5));
         break;
      case 'l':
         var5 = ByteArray.getIntAtOffset(this.d_code, var2);
         var1.append("#" + var5);
         break;
      case 't':
         var5 = ByteArray.getByteAtOffset(this.d_code, var2);
         var1.append(JVMInstruction.arrayTypeCode2String(var5));
         break;
      case 'v':
         var5 = ByteArray.getByteAtOffset(this.d_code, var2);
         var1.append("v" + var5);
         break;
      default:
         var1.append("<unknown operand type: " + var3 + ">");
      }

   }

   public String getTag() {
      return this.d_tag;
   }

   public void setTag(String var1) {
      this.d_tag = var1;
   }

   private BitSet setUses() {
      if (this.marked) {
         return this.d_uses;
      } else {
         this.marked = true;
         if (this.d_opCode != 170 && this.d_opCode != 171 && this.d_opCode != 196) {
            JVMInstruction var1 = JVMInstruction.d_instrTable[this.d_opCode];
            int var2 = var1.d_operandTypes.length();

            for(int var3 = 0; var3 < var2; ++var3) {
               switch(var1.d_operandTypes.charAt(var3)) {
               case 'C':
                  if (this.d_uses == null) {
                     this.d_uses = new BitSet(this.d_cp.length());
                  }

                  this.d_uses.set(ByteArray.getShortAtOffset(this.d_code, 1));
                  break;
               case 'c':
                  if (this.d_uses == null) {
                     this.d_uses = new BitSet(this.d_cp.length());
                  }

                  this.d_uses.set(ByteArray.getByteAtOffset(this.d_code, 1));
               }
            }
         }

         return this.d_uses;
      }
   }

   BitSet uses() {
      return this.setUses();
   }

   public String toBytes() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < this.d_code.length; ++var2) {
         int var3 = ByteArray.getByteAtOffset(this.d_code, var2);
         if (var3 < 10) {
            var1.append("0x0" + Integer.toHexString(var3) + " ");
         } else {
            var1.append("0x" + Integer.toHexString(var3) + " ");
         }
      }

      return var1.toString();
   }

   public int getLength(int var1) {
      if (this.d_opCode != 170 && this.d_opCode != 171) {
         return this.d_code.length;
      } else {
         int var2 = (4 - (var1 + 1) % 4) % 4;
         return this.d_code.length + var2;
      }
   }

   public byte[] getCode(int[] var1, int var2) {
      byte[] var3 = null;
      JVMInstruction var6 = JVMInstruction.d_instrTable[this.d_opCode];
      if (!var6.d_operandTypes.equals("") && var1 != null) {
         int var4;
         int var5;
         switch(var6.d_operandTypes.charAt(0)) {
         case 'A':
            var4 = ByteArray.getIntAtOffset(this.d_code, 1);
            var5 = var1[var4] - var2;
            var3 = new byte[this.d_code.length];
            var3[0] = (byte)this.d_opCode;
            var3[1] = (byte)(var5 >>> 24 & 255);
            var3[2] = (byte)(var5 >>> 16 & 255);
            var3[3] = (byte)(var5 >>> 8 & 255);
            var3[4] = (byte)(var5 & 255);
            break;
         case 'a':
            var4 = ByteArray.getSignedShortAtOffset(this.d_code, 1);
            var5 = var1[var4] - var2;
            var3 = new byte[this.d_code.length];
            var3[0] = (byte)this.d_opCode;
            var3[1] = (byte)(var5 >>> 8 & 255);
            var3[2] = (byte)(var5 & 255);
            break;
         case 's':
            boolean var7;
            byte var8;
            int var9;
            int var10;
            int var12;
            int var15;
            int var16;
            switch(this.d_opCode) {
            case 170:
               var10 = (4 - (var2 + 1) % 4) % 4;
               var8 = 0;
               var7 = false;
               var3 = new byte[var10 + this.d_code.length];
               var16 = var8 + 1;
               var3[var8] = (byte)this.d_opCode;

               for(var15 = 0; var15 < var10; ++var15) {
                  var3[var16++] = 0;
               }

               var4 = ByteArray.getIntAtOffset(this.d_code, 1);
               var5 = var1[var4] - var2;
               var3[var16++] = (byte)(var5 >>> 24 & 255);
               var3[var16++] = (byte)(var5 >>> 16 & 255);
               var3[var16++] = (byte)(var5 >>> 8 & 255);
               var3[var16++] = (byte)(var5 & 255);
               var15 = 5;

               do {
                  var3[var16++] = this.d_code[var15];
                  ++var15;
               } while(var15 < 13);

               var12 = ByteArray.getIntAtOffset(this.d_code, 5);
               int var13 = ByteArray.getIntAtOffset(this.d_code, 9);
               var9 = 13;

               for(int var14 = var12; var14 <= var13; ++var14) {
                  var4 = ByteArray.getIntAtOffset(this.d_code, var9);
                  var5 = var1[var4] - var2;
                  var3[var16++] = (byte)(var5 >>> 24 & 255);
                  var3[var16++] = (byte)(var5 >>> 16 & 255);
                  var3[var16++] = (byte)(var5 >>> 8 & 255);
                  var3[var16++] = (byte)(var5 & 255);
                  var9 += 4;
               }

               return var3;
            case 171:
               var10 = (4 - (var2 + 1) % 4) % 4;
               var8 = 0;
               var7 = false;
               var3 = new byte[var10 + this.d_code.length];
               var16 = var8 + 1;
               var3[var8] = (byte)this.d_opCode;

               for(var15 = 0; var15 < var10; ++var15) {
                  var3[var16++] = 0;
               }

               var4 = ByteArray.getIntAtOffset(this.d_code, 1);
               var5 = var1[var4] - var2;
               var3[var16++] = (byte)(var5 >>> 24 & 255);
               var3[var16++] = (byte)(var5 >>> 16 & 255);
               var3[var16++] = (byte)(var5 >>> 8 & 255);
               var3[var16++] = (byte)(var5 & 255);
               var15 = 5;

               do {
                  var3[var16++] = this.d_code[var15];
                  ++var15;
               } while(var15 < 9);

               int var11 = ByteArray.getIntAtOffset(this.d_code, 5);
               var9 = 9;

               for(var12 = 0; var12 < var11; ++var12) {
                  var15 = 0;

                  do {
                     var3[var16++] = this.d_code[var9 + var15];
                     ++var15;
                  } while(var15 < 4);

                  var9 += 4;
                  var4 = ByteArray.getIntAtOffset(this.d_code, var9);
                  var5 = var1[var4] - var2;
                  var3[var16++] = (byte)(var5 >>> 24 & 255);
                  var3[var16++] = (byte)(var5 >>> 16 & 255);
                  var3[var16++] = (byte)(var5 >>> 8 & 255);
                  var3[var16++] = (byte)(var5 & 255);
                  var9 += 4;
               }

               return var3;
            case 196:
               var3 = this.d_code;
               return var3;
            default:
               return var3;
            }
         default:
            var3 = this.d_code;
         }

         return var3;
      } else {
         return this.d_code;
      }
   }
}
