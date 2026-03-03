package com.ibm.toad.cfparse.instruction;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.utils.ByteArray;
import com.ibm.toad.cfparse.utils.CPUtils;
import com.ibm.toad.cfparse.utils.InstrUtils;

final class MipsInstruction implements BaseInstruction {
   private int d_opCode;
   private byte[] d_code;
   private String d_tag;
   private ConstantPool d_cp;
   private TagFactory d_fac;
   private Minstr d_instr;
   private static char d_type = 'a';
   private static int d_top = 0;

   public Minstr parseInstr(byte[] var1) {
      int var2 = var1[0] & 255;
      Minstr var5 = null;
      JVMInstruction var13 = JVMInstruction.d_instrTable[var2];
      int var14 = InstrUtils.stackEffect(this.d_cp, var1, 0);
      int var3;
      int var4;
      String var6;
      String var7;
      int var9;
      int var10;
      int var11;
      int var12;
      switch(var2) {
      case 0:
         var5 = new Minstr("nop");
         break;
      case 1:
         var5 = new Minstr("li %as" + (d_top + 1) + ", null");
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
         var4 = var2 - 3;
         var5 = new Minstr("li %is" + (d_top + 1) + ", " + var4);
         break;
      case 9:
      case 10:
         var4 = var2 - 9;
         var5 = new Minstr("li %ls" + (d_top + 1) + ", " + var4);
         break;
      case 11:
      case 12:
      case 13:
         var4 = var2 - 11;
         var5 = new Minstr("li %fs" + (d_top + 1) + ", " + var4);
         break;
      case 14:
      case 15:
         var4 = var2 - 14;
         var5 = new Minstr("li %ds" + (d_top + 1) + ", " + var4);
         break;
      case 16:
         var4 = this.getOperand(var1, 1, 'i');
         var5 = new Minstr("li %is" + (d_top + 1) + ", " + var4);
         break;
      case 17:
         var4 = this.getOperand(var1, 1, 'I');
         var5 = new Minstr("li %is" + (d_top + 1) + ", " + var4);
         break;
      case 18:
         var4 = this.getOperand(var1, 1, 'c');
         switch(this.d_cp.getType(var4)) {
         case 3:
            var6 = "i";
            break;
         case 4:
            var6 = "f";
            break;
         case 5:
         case 6:
         case 7:
         default:
            throw new RuntimeException("Incorrect LDC bytecode");
         case 8:
            var6 = "a";
         }

         var5 = new Minstr("li %" + var6 + "s" + (d_top + 1) + ", " + this.d_cp.getAsJava(var4));
         break;
      case 19:
         var4 = this.getOperand(var1, 1, 'C');
         switch(this.d_cp.getType(var4)) {
         case 3:
            var6 = "i";
            break;
         case 4:
            var6 = "f";
            break;
         case 5:
         case 6:
         case 7:
         default:
            throw new RuntimeException("Incorrect LDC bytecode");
         case 8:
            var6 = "a";
         }

         var5 = new Minstr("li %" + var6 + "s" + (d_top + 1) + ", " + this.d_cp.getAsJava(var4));
         break;
      case 20:
         var4 = this.getOperand(var1, 1, 'C');
         switch(this.d_cp.getType(var4)) {
         case 5:
            var6 = "l";
            break;
         case 6:
            var6 = "d";
            break;
         default:
            throw new RuntimeException("Incorrect LDC2_W bytecode");
         }

         var5 = new Minstr("li %" + var6 + "s" + (d_top + 1) + ", " + this.d_cp.getAsJava(var4));
         break;
      case 21:
         var4 = this.getOperand(var1, 1, 'v');
         var5 = new Minstr("mov %is" + (d_top + 1) + ", %il" + var4);
         break;
      case 22:
         var4 = this.getOperand(var1, 1, 'v');
         var5 = new Minstr("mov %ls" + (d_top + 1) + ", %ll" + var4);
         break;
      case 23:
         var4 = this.getOperand(var1, 1, 'v');
         var5 = new Minstr("mov %fs" + (d_top + 1) + ", %fl" + var4);
         break;
      case 24:
         var4 = this.getOperand(var1, 1, 'v');
         var5 = new Minstr("mov %ds" + (d_top + 1) + ", %dl" + var4);
         break;
      case 25:
         var4 = this.getOperand(var1, 1, 'v');
         var5 = new Minstr("mov %as" + (d_top + 1) + ", %al" + var4);
         break;
      case 26:
      case 27:
      case 28:
      case 29:
         var4 = var2 - 26;
         var5 = new Minstr("mov %is" + (d_top + 1) + ", %il" + var4);
         break;
      case 30:
      case 31:
      case 32:
      case 33:
         var4 = var2 - 30;
         var5 = new Minstr("mov %ls" + (d_top + 1) + ", %ll" + var4);
         break;
      case 34:
      case 35:
      case 36:
      case 37:
         var4 = var2 - 34;
         var5 = new Minstr("mov %fs" + (d_top + 1) + ", %fl" + var4);
         break;
      case 38:
      case 39:
      case 40:
      case 41:
         var4 = var2 - 38;
         var5 = new Minstr("mov %ds" + (d_top + 1) + ", %dl" + var4);
         break;
      case 42:
      case 43:
      case 44:
      case 45:
         var4 = var2 - 42;
         var5 = new Minstr("mov %as" + (d_top + 1) + ", %al" + var4);
         break;
      case 46:
         var5 = new Minstr("lw  %is" + (d_top + 1) + ",%as" + d_top + "(%is" + (d_top - 1) + ")");
         break;
      case 47:
         var5 = new Minstr("lw  %ls" + (d_top + 1) + ",%as" + d_top + "(%is" + (d_top - 1) + ")");
         break;
      case 48:
         var5 = new Minstr("lw  %fs" + (d_top + 1) + ",%as" + d_top + "(%is" + (d_top - 1) + ")");
         break;
      case 49:
         var5 = new Minstr("lw  %ds" + (d_top + 1) + ",%as" + d_top + "(%is" + (d_top - 1) + ")");
         break;
      case 50:
         var5 = new Minstr("lw  %as" + (d_top + 1) + ",%as" + d_top + "(%is" + (d_top - 1) + ")");
         break;
      case 51:
         var5 = new Minstr("lw  %is" + (d_top + 1) + ",%as" + d_top + "(%is" + (d_top - 1) + ")");
         break;
      case 52:
         var5 = new Minstr("lw  %is" + (d_top + 1) + ",%as" + d_top + "(%is" + (d_top - 1) + ")");
         break;
      case 53:
         var5 = new Minstr("lw  %is" + (d_top + 1) + ",%as" + d_top + "(%is" + (d_top - 1) + ")");
         break;
      case 54:
         var4 = this.getOperand(var1, 1, 'v');
         var5 = new Minstr("mov %il" + var4 + ", %is" + d_top);
         break;
      case 55:
         var4 = this.getOperand(var1, 1, 'v');
         var5 = new Minstr("mov %ll" + var4 + ", %ls" + d_top);
         break;
      case 56:
         var4 = this.getOperand(var1, 1, 'v');
         var5 = new Minstr("mov %fl" + var4 + ", %fs" + d_top);
         break;
      case 57:
         var4 = this.getOperand(var1, 1, 'v');
         var5 = new Minstr("mov %dl" + var4 + ", %ds" + d_top);
         break;
      case 58:
         var4 = this.getOperand(var1, 1, 'v');
         var5 = new Minstr("mov %al" + var4 + ", %as" + d_top);
         break;
      case 59:
      case 60:
      case 61:
      case 62:
         var4 = var2 - 59;
         var5 = new Minstr("mov %il" + d_top + ", %is" + var4);
         break;
      case 63:
      case 64:
      case 65:
      case 66:
         var4 = var2 - 63;
         var5 = new Minstr("mov %ll" + d_top + ", %ls" + var4);
         break;
      case 67:
      case 68:
      case 69:
      case 70:
         var4 = var2 - 67;
         var5 = new Minstr("mov %fl" + d_top + ", %fs" + var4);
         break;
      case 71:
      case 72:
      case 73:
      case 74:
         var4 = var2 - 71;
         var5 = new Minstr("mov %dl" + d_top + ", %ds" + var4);
         break;
      case 75:
      case 76:
      case 77:
      case 78:
         var4 = var2 - 75;
         var5 = new Minstr("mov %al" + d_top + ", %as" + var4);
         break;
      case 79:
         var5 = new Minstr("sw  %is" + d_top + ",%as" + (d_top - 2) + "(%is" + (d_top - 1) + ")");
         break;
      case 80:
         var5 = new Minstr("sw  %ls" + d_top + ",%as" + (d_top - 2) + "(%is" + (d_top - 1) + ")");
         break;
      case 81:
         var5 = new Minstr("sw  %fs" + d_top + ",%as" + (d_top - 2) + "(%is" + (d_top - 1) + ")");
         break;
      case 82:
         var5 = new Minstr("sw  %ds" + d_top + ",%as" + (d_top - 2) + "(%is" + (d_top - 1) + ")");
         break;
      case 83:
         var5 = new Minstr("sw  %as" + d_top + ",%as" + (d_top - 2) + "(%is" + (d_top - 1) + ")");
         break;
      case 84:
         var5 = new Minstr("sw  %is" + d_top + ",%as" + (d_top - 2) + "(%is" + (d_top - 1) + ")");
         break;
      case 85:
         var5 = new Minstr("sw  %is" + d_top + ",%as" + (d_top - 2) + "(%is" + (d_top - 1) + ")");
         break;
      case 86:
         var5 = new Minstr("sw  %is" + d_top + ",%as" + (d_top - 2) + "(%is" + (d_top - 1) + ")");
         break;
      case 87:
      case 88:
         var5 = new Minstr("");
         break;
      case 89:
         var5 = new Minstr("mov %is" + d_top + ",%is" + (d_top + 1));
         break;
      case 90:
         var5 = new Minstr("mov %is" + d_top + ",%is" + (d_top + 1), new Minstr("mov %is" + (d_top - 1) + ",%is" + d_top, new Minstr("mov %is" + (d_top + 1) + ",%is" + (d_top - 1))));
         break;
      case 91:
         var5 = new Minstr("mov %is" + d_top + ",%is" + (d_top + 1), new Minstr("mov %is" + (d_top - 1) + ",%is" + d_top, new Minstr("mov %is" + (d_top + 1) + ",%is" + (d_top - 1))));
         break;
      case 92:
         var5 = new Minstr("mov %is" + (d_top - 1) + ",%is" + (d_top + 1), new Minstr("mov %is" + d_top + ",%is" + (d_top + 2)));
      case 93:
      case 94:
      case 95:
      case 105:
      case 106:
      case 107:
      case 108:
      case 109:
      case 110:
      case 111:
      case 112:
      case 113:
      case 114:
      case 115:
      case 116:
      case 117:
      case 118:
      case 119:
      case 120:
      case 121:
      case 122:
      case 123:
      case 124:
      case 125:
      case 126:
      case 127:
      case 128:
      case 129:
      case 130:
      case 131:
      case 132:
      case 133:
      case 134:
      case 135:
      case 136:
      case 137:
      case 138:
      case 139:
      case 140:
      case 141:
      case 142:
      case 143:
      case 144:
      case 145:
      case 146:
      case 147:
      case 148:
      case 149:
      case 150:
      case 151:
      case 152:
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 168:
      case 169:
      case 170:
      case 171:
      case 172:
      case 173:
      case 174:
      case 175:
      case 176:
      case 177:
      case 178:
      case 179:
      case 184:
      case 185:
      case 186:
      case 187:
      case 188:
      case 189:
      case 190:
      case 191:
      case 192:
      case 193:
      case 194:
      case 195:
      case 196:
      case 197:
      case 198:
      case 199:
      case 200:
      case 201:
      default:
         break;
      case 96:
         var5 = new Minstr("add %is" + (d_top - 1) + ", %is" + d_top + ",%is" + (d_top - 1));
         break;
      case 97:
         var5 = new Minstr("add %ls" + (d_top - 1) + ", %ls" + d_top + ",%ls" + (d_top - 1));
         break;
      case 98:
         var5 = new Minstr("add %fs" + (d_top - 1) + ", %fs" + d_top + ",%fs" + (d_top - 1));
         break;
      case 99:
         var5 = new Minstr("add %ds" + (d_top - 1) + ", %ds" + d_top + ",%ds" + (d_top - 1));
         break;
      case 100:
         var5 = new Minstr("sub %is" + (d_top - 1) + ", %is" + d_top + ",%is" + (d_top - 1));
         break;
      case 101:
         var5 = new Minstr("sub %ls" + (d_top - 1) + ", %ls" + d_top + ",%ls" + (d_top - 1));
         break;
      case 102:
         var5 = new Minstr("sub %fs" + (d_top - 1) + ", %fs" + d_top + ",%fs" + (d_top - 1));
         break;
      case 103:
         var5 = new Minstr("sub %ds" + (d_top - 1) + ", %ds" + d_top + ",%ds" + (d_top - 1));
         break;
      case 104:
         var5 = new Minstr("smul %is" + (d_top - 1) + ", %is" + d_top + ",%is" + (d_top - 1));
         break;
      case 153:
         var12 = this.getOperand(var1, 1, 'a');
         var5 = new Minstr("cmp is" + d_top + ", 0", new Minstr("be " + this.d_fac.getTag(var12)));
         break;
      case 154:
         var12 = this.getOperand(var1, 1, 'a');
         var5 = new Minstr("cmp is" + d_top + ", 0", new Minstr("bn " + this.d_fac.getTag(var12)));
         break;
      case 155:
         var12 = this.getOperand(var1, 1, 'a');
         var5 = new Minstr("cmp is" + d_top + ", 0", new Minstr("bl " + this.d_fac.getTag(var12)));
         break;
      case 156:
         var12 = this.getOperand(var1, 1, 'a');
         var5 = new Minstr("cmp is" + d_top + ", 0", new Minstr("bge " + this.d_fac.getTag(var12)));
         break;
      case 157:
         var12 = this.getOperand(var1, 1, 'a');
         var5 = new Minstr("cmp is" + d_top + ", 0", new Minstr("bg " + this.d_fac.getTag(var12)));
         break;
      case 158:
         var12 = this.getOperand(var1, 1, 'a');
         var5 = new Minstr("cmp is" + d_top + ", 0", new Minstr("ble " + this.d_fac.getTag(var12)));
         break;
      case 167:
         var12 = this.getOperand(var1, 1, 'a');
         var5 = new Minstr("jmp " + this.d_fac.getTag(var12));
         break;
      case 180:
         var3 = this.getOperand(var1, 1, 'C');
         var6 = this.d_cp.get(var3).getAsString();
         var9 = var6.indexOf(32);
         var10 = var6.indexOf(32, var9 + 1);
         var7 = var6.substring(var9 + 1, var10);
         var6 = var6.substring(var10 + 1);
         switch(var6.charAt(0)) {
         case 'B':
         case 'C':
         case 'I':
         case 'S':
         case 'Z':
            var6 = "i";
            break;
         case 'D':
            var6 = "d";
            break;
         case 'F':
            var6 = "f";
            break;
         case 'J':
            var6 = "l";
            break;
         case 'L':
         case 'V':
         case '[':
            var6 = "a";
         }

         var5 = new Minstr("ld [%as" + d_top + "+delta(" + var7 + ")], %" + var6 + "s" + d_top + "");
         break;
      case 181:
         var3 = this.getOperand(var1, 1, 'C');
         var6 = this.d_cp.get(var3).getAsString();
         var9 = var6.indexOf(32);
         var10 = var6.indexOf(32, var9 + 1);
         var7 = var6.substring(var9 + 1, var10);
         var6 = var6.substring(var10 + 1);
         switch(var6.charAt(0)) {
         case 'B':
         case 'C':
         case 'I':
         case 'S':
         case 'Z':
            var6 = "i";
            break;
         case 'D':
            var6 = "d";
            break;
         case 'F':
            var6 = "f";
            break;
         case 'J':
            var6 = "l";
            break;
         case 'L':
         case 'V':
         case '[':
            var6 = "a";
         }

         var5 = new Minstr("st  %" + var6 + "s" + d_top + ", [%as" + (d_top - 1) + "+delta(" + var7 + ")]");
         break;
      case 182:
         var3 = this.getOperand(var1, 1, 'C');
         var11 = -CPUtils.countArgs(this.d_cp, var3);
         var6 = this.d_cp.get(var3).getAsString();
         var9 = var6.indexOf(32);
         var10 = var6.indexOf(32, var9 + 1);
         var7 = var6.substring(var9 + 1, var10);
         var5 = new Minstr("ld  [%as" + (d_top + var11) + "], %at0", new Minstr("ld  [%at0 + delta(" + var7 + ")], %at1", new Minstr("call %at1")));
         break;
      case 183:
         var3 = this.getOperand(var1, 1, 'C');
         var11 = -CPUtils.countArgs(this.d_cp, var3);
         var6 = this.d_cp.get(var3).getAsString();
         var9 = var6.indexOf(32);
         var10 = var6.indexOf(32, var9 + 1);
         var7 = var6.substring(var9 + 1, var10);
         if (var11 == 0) {
            String var10000 = "";
         } else if (var11 > 0) {
            (new StringBuffer()).append("+").append(var11).toString();
         } else {
            (new StringBuffer()).append("").append(var11).toString();
         }

         var5 = new Minstr("ld  [%as" + (d_top + var11) + "], %at0", new Minstr("ld  [%at0 + delta(" + var7 + ")], %at1", new Minstr("call %at1")));
      }

      d_top += var14;
      if (var5 == null) {
         var5 = new Minstr("untranslated opCode " + var13.name());
      }

      return var5;
   }

   static void methodInit() {
      d_type = 'a';
      d_top = 0;
   }

   public String toString() {
      if (this.d_instr.d_next == null) {
         return this.d_instr.d_instr;
      } else {
         StringBuffer var1 = new StringBuffer(this.d_instr.d_instr);

         for(Minstr var2 = this.d_instr.d_next; var2 != null; var2 = var2.d_next) {
            var1.append("\n" + var2.d_instr);
         }

         return var1.toString();
      }
   }

   MipsInstruction(TagFactory var1, ConstantPool var2, byte[] var3, int var4, int var5) {
      this.d_cp = var2;
      this.d_code = new byte[var5];
      System.arraycopy(var3, var4, this.d_code, 0, var5);
      this.d_opCode = var3[var4] & 255;
      this.d_fac = var1;
      this.d_tag = null;
      this.d_instr = this.parseInstr(this.d_code);
   }

   public int getOpCode() {
      return this.d_code[0] & 255;
   }

   MipsInstruction(TagFactory var1, ConstantPool var2, String var3) throws InstructionFormatException {
      throw new InstructionFormatException("Cannot create MipsInstruction from String yet...");
   }

   public String toString(int var1) {
      if (this.d_instr.d_next == null) {
         return this.d_instr.d_instr;
      } else {
         StringBuffer var2 = new StringBuffer(this.d_instr.d_instr);

         for(Minstr var3 = this.d_instr.d_next; var3 != null; var3 = var3.d_next) {
            var2.append("\n" + sindent(var1 + 9));
            var2.append(var3.d_instr);
         }

         return var2.toString();
      }
   }

   public String getTag() {
      return this.d_tag;
   }

   public void setTag(String var1) {
      this.d_tag = var1;
   }

   private static String sindent(int var0) {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < var0; ++var2) {
         var1.append(" ");
      }

      return var1.toString();
   }

   int getOperand(byte[] var1, int var2, char var3) {
      boolean var4 = false;
      int var5;
      switch(var3) {
      case '0':
      case 'i':
         var5 = ByteArray.getByteAtOffset(var1, var2);
         if (var5 > 127) {
            var5 -= 256;
         }
         break;
      case 'A':
      case 'l':
         var5 = ByteArray.getIntAtOffset(var1, var2);
         break;
      case 'C':
      case 'V':
         var5 = ByteArray.getShortAtOffset(var1, var2);
         break;
      case 'I':
         var5 = ByteArray.getShortAtOffset(var1, var2);
         if (var5 > 32767) {
            var5 -= 65536;
         }
         break;
      case 'a':
         var5 = ByteArray.getSignedShortAtOffset(var1, var2);
         break;
      case 'c':
      case 't':
      case 'v':
         var5 = ByteArray.getByteAtOffset(var1, var2);
         break;
      default:
         throw new RuntimeException("Unknown Operand type..");
      }

      return var5;
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
      throw new RuntimeException("Can't get bytecodes from sparcInstr yet...");
   }
}
