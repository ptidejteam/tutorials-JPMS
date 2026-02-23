package com.ibm.toad.cfparse.instruction;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.utils.ByteArray;
import com.ibm.toad.cfparse.utils.CPUtils;
import com.ibm.toad.utils.D;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.StringTokenizer;

public final class Instruction extends JVMInstruction implements BaseInstruction {
   private byte[] d_code;
   private BitSet d_uses;
   private String d_tag;
   private ConstantPool d_cp;
   private TagFactory d_fac;
   private String[] d_varTable;
   private boolean marked = false;

   private Object[] parseString(String var1) throws ParseException {
      Object[] var2;
      int var4;
      int var6;
      int var7;
      int var9;
      int var11;
      int var12;
      int var13;
      String var32;
      int var34;
      int var37;
      byte var39;
      if (super.d_opCode == 170) {
         var39 = 0;
         var4 = var1.indexOf(" ");
         var32 = var1.substring(0, var4);
         var6 = var1.indexOf("#", var4);
         var7 = var1.indexOf("-", var6);
         var34 = var1.indexOf("#", var7);
         var9 = var1.indexOf(" ", var34);
         boolean var35 = false;
         boolean var38 = false;

         int var36;
         try {
            var36 = Integer.parseInt(var1.substring(var6 + 1, var7).trim());
            var11 = Integer.parseInt(var1.substring(var34 + 1, var9).trim());
         } catch (NumberFormatException var15) {
            throw new ParseException("Cannot parse " + var1);
         }

         var2 = new Object[3 + (var11 - var36) + 1];
         var12 = this.d_fac.tagFor(var32, true);
         var37 = var39 + 1;
         var2[var39] = new Integer(var12);
         var2[var37++] = new Integer(var36);
         var2[var37++] = new Integer(var11);
         var1 = var1.substring(var9).trim();

         for(var13 = 0; var13 <= var11 - var36; ++var13) {
            var4 = var1.indexOf(" ");
            if (var4 == -1) {
               var4 = var1.length();
            }

            var32 = var1.substring(0, var4);
            var1 = var1.substring(var4).trim();
            var12 = this.d_fac.tagFor(var32, true);
            var2[var37++] = new Integer(var12);
         }

         return var2;
      } else if (super.d_opCode == 171) {
         var39 = 0;
         var4 = var1.indexOf(" ");
         var32 = var1.substring(0, var4);
         var6 = var1.indexOf("#", var4);
         var7 = var1.indexOf(" ", var6);
         boolean var8 = false;

         try {
            var34 = Integer.parseInt(var1.substring(var6 + 1, var7).trim());
         } catch (NumberFormatException var17) {
            throw new ParseException("Cannot parse " + var1.substring(var6 + 1, var7).trim());
         }

         var2 = new Object[2 + var34 * 2];
         var9 = this.d_fac.tagFor(var32, true);
         var37 = var39 + 1;
         var2[var39] = new Integer(var9);
         var2[var37++] = new Integer(var34);
         var1 = var1.substring(var7).trim();

         class pair {
            Integer match;
            Integer tgt;
            // $FF: synthetic field
            final Instruction this$0;

            public pair(Instruction var1, Integer var2, Integer var3) {
               (this.this$0 = var1).getClass();
               this.match = var2;
               this.tgt = var3;
            }
         }

         pair[] var10 = new pair[var34];

         for(var11 = 0; var11 < var34; ++var11) {
            var4 = var1.indexOf("(");
            var6 = var1.indexOf("#", var4);
            var12 = var1.indexOf(",", var6);
            var13 = var1.indexOf(")", var12);
            boolean var14 = false;

            int var40;
            try {
               var40 = Integer.parseInt(var1.substring(var6 + 1, var12));
            } catch (NumberFormatException var16) {
               throw new ParseException("Cannot parse " + var1.substring(var6 + 1, var12));
            }

            var32 = var1.substring(var12 + 1, var13).trim();
            var1 = var1.substring(var13 + 1).trim();
            var9 = this.d_fac.tagFor(var32, true);
            var10[var11] = new pair(this, new Integer(var40), new Integer(var9));
         }

         Arrays.sort(var10, new Comparator(this) {
            // $FF: synthetic field
            final Instruction this$0;

            {
               (this.this$0 = var1).getClass();
            }

            public boolean equals(Object var1) {
               return this.equals(var1);
            }

            public int compare(Object var1, Object var2) {
               return ((pair)var1).match.compareTo(((pair)var2).match);
            }
         });

         for(var11 = 0; var11 < var10.length; ++var11) {
            var2[var37++] = var10[var11].match;
            var2[var37++] = var10[var11].tgt;
         }

         return var2;
      } else if (super.d_opCode == 196) {
         var37 = var1.indexOf(" ");
         String var31 = var1.substring(0, var37).trim();
         var32 = var1.substring(var37).trim();
         var37 = var32.indexOf(" ");
         if (var37 != -1) {
            var1 = var32.substring(var37).trim();
            var32 = var32.substring(0, var37).trim();
            var2 = new Object[3];
         } else {
            var2 = new Object[2];
            var1 = null;
         }

         if (!var31.equals("iinc") && !var31.substring(1).equals("load") && !var31.substring(1).equals("store") && !var31.equals("ret")) {
            throw new ParseException("illegal opCode for widening  " + var31);
         } else {
            var2[0] = var31;
            if (!var32.startsWith("v")) {
               throw new ParseException("local variable must begin with 'V' " + var32);
            } else {
               try {
                  var2[1] = Short.valueOf(var32.substring(1));
               } catch (NumberFormatException var19) {
                  throw new ParseException("Cannot parse " + var32.substring(1));
               }

               if (var1 != null) {
                  if (!var1.startsWith("#")) {
                     throw new ParseException("integer operand must begin with '#' " + var1);
                  }

                  try {
                     var2[2] = Short.valueOf(var1.substring(1));
                  } catch (NumberFormatException var18) {
                     throw new ParseException("Cannot parse " + var1.substring(1));
                  }
               }

               return var2;
            }
         }
      } else {
         var2 = new Object[super.d_operandTypes.length()];

         for(var4 = 0; var4 < super.d_operandTypes.length(); ++var4) {
            if (var1.length() == 0) {
               if (super.d_operandTypes.charAt(var4) != '0') {
                  throw new ParseException("no line, " + var4 + "/" + super.d_operandTypes.length());
               }

               var2[var4] = new Byte((byte)0);
            } else {
               String var3;
               int var5;
               if (var1.charAt(0) == '"') {
                  var3 = var1.substring(0, var1.lastIndexOf(34));
                  var1 = var1.substring(var1.lastIndexOf(34) + 1).trim();
               } else {
                  var5 = var1.indexOf(" ");
                  if (var5 == -1) {
                     var3 = var1.trim();
                     var1 = "";
                  } else {
                     var3 = var1.substring(0, var5).trim();
                     var1 = var1.substring(var5 + 1).trim();
                  }
               }

               switch(super.d_operandTypes.charAt(var4)) {
               case '0':
               case 'i':
                  if (!var3.startsWith("#")) {
                     throw new ParseException("integer operand must begin with '#' " + var3);
                  }

                  try {
                     var2[var4] = Byte.valueOf(var3.substring(1));
                     break;
                  } catch (NumberFormatException var28) {
                     throw new ParseException("Cannot parse " + var3.substring(1));
                  }
               case 'A':
                  var5 = this.d_fac.tagFor(var3, true);
                  var2[var4] = new Integer(var5);
                  break;
               case 'C':
                  if (super.d_opCode == 19) {
                     switch(var3.charAt(0)) {
                     case '"':
                        var2[var4] = var3.substring(1);
                        continue;
                     case '#':
                        try {
                           var2[var4] = Integer.valueOf(var3.substring(1));
                           continue;
                        } catch (NumberFormatException var23) {
                           throw new ParseException("Cannot parse " + var3.substring(1));
                        }
                     case 'F':
                        try {
                           var2[var4] = Float.valueOf(var3.substring(1));
                           continue;
                        } catch (NumberFormatException var22) {
                           throw new ParseException("Cannot parse " + var3.substring(1));
                        }
                     default:
                        throw new ParseException("Cannot parse " + var3);
                     }
                  } else if (super.d_opCode == 20) {
                     switch(var3.charAt(0)) {
                     case 'D':
                        try {
                           var2[var4] = Double.valueOf(var3.substring(1));
                           continue;
                        } catch (NumberFormatException var20) {
                           throw new ParseException("Cannot parse " + var3.substring(1));
                        }
                     case 'L':
                        try {
                           var2[var4] = Long.valueOf(var3.substring(1));
                           continue;
                        } catch (NumberFormatException var21) {
                           throw new ParseException("Cannot parse " + var3.substring(1));
                        }
                     default:
                        throw new ParseException("Cannot parse " + var3);
                     }
                  } else if (super.d_opCode != 178 && super.d_opCode != 179 && super.d_opCode != 180 && super.d_opCode != 181) {
                     if (super.d_opCode != 182 && super.d_opCode != 183 && super.d_opCode != 184 && super.d_opCode != 185) {
                        if (super.d_opCode == 187 || super.d_opCode == 189 || super.d_opCode == 192 || super.d_opCode == 193 || super.d_opCode == 197) {
                           if (super.d_opCode == 197) {
                              var7 = var1.indexOf("#");
                              if (var7 == -1) {
                                 throw new ParseException("multianewarray without integer operand " + var3 + " " + var1);
                              }

                              var3 = var3 + " " + var1.substring(0, var7).trim();
                              var1 = var1.substring(var7).trim();
                           } else {
                              var3 = var3 + " " + var1;
                              var1 = "";
                           }

                           if (var3.indexOf("[") != -1) {
                              StringBuffer var33 = new StringBuffer();
                              CPUtils.java2descriptor(var3, var33);
                              var2[var4] = var33.toString().trim();
                           } else {
                              var2[var4] = CPUtils.dots2slashes(var3).trim();
                           }
                        }
                     } else {
                        if (super.d_opCode == 185) {
                           var7 = var1.indexOf("#");
                           if (var7 == -1) {
                              throw new ParseException("invokeinterface without integer operand " + var3 + " " + var1);
                           }

                           var3 = var3 + " " + var1.substring(0, var7).trim();
                           var1 = var1.substring(var7).trim();
                        } else {
                           var3 = var3 + " " + var1;
                           var1 = "";
                        }

                        var2[var4] = CPUtils.java2internal(var3);
                     }
                     break;
                  } else {
                     var7 = var1.lastIndexOf(".");
                     if (var7 != -1 && var7 != var1.length()) {
                        var2[var4] = CPUtils.dots2slashes(var1.substring(0, var7)) + " " + var1.substring(var7 + 1, var1.length()) + " " + CPUtils.java2internal(var3);
                        var1 = "";
                        break;
                     }

                     throw new ParseException("Cannot parse " + var1);
                  }
               case 'I':
                  if (!var3.startsWith("#")) {
                     throw new ParseException("integer operand must begin with '#' " + var3);
                  }

                  try {
                     var2[var4] = Short.valueOf(var3.substring(1));
                     break;
                  } catch (NumberFormatException var27) {
                     throw new ParseException("Cannot parse " + var3.substring(1));
                  }
               case 'V':
                  if (!var3.startsWith("v")) {
                     throw new ParseException("local variable must begin with 'V' " + var3);
                  }

                  try {
                     var2[var4] = Short.valueOf(var3.substring(1));
                     break;
                  } catch (NumberFormatException var29) {
                     throw new ParseException("Cannot parse " + var3.substring(1));
                  }
               case 'a':
                  var5 = this.d_fac.tagFor(var3, true);
                  var2[var4] = new Short((short)var5);
                  break;
               case 'c':
                  if (super.d_opCode == 18) {
                     switch(var3.charAt(0)) {
                     case '"':
                        var2[var4] = var3.substring(1);
                        break;
                     case '#':
                        try {
                           var2[var4] = Integer.valueOf(var3.substring(1));
                           break;
                        } catch (NumberFormatException var25) {
                           throw new ParseException("Cannot parse " + var3.substring(1));
                        }
                     case 'F':
                        try {
                           var2[var4] = Float.valueOf(var3.substring(1));
                           break;
                        } catch (NumberFormatException var24) {
                           throw new ParseException("Cannot parse " + var3.substring(1));
                        }
                     default:
                        throw new ParseException("Cannot parse " + var3);
                     }
                  }
                  break;
               case 'l':
                  if (!var3.startsWith("#")) {
                     throw new ParseException("integer operand must begin with '#' " + var3);
                  }

                  try {
                     var2[var4] = Integer.valueOf(var3.substring(1));
                     break;
                  } catch (NumberFormatException var26) {
                     throw new ParseException("Cannot parse " + var3.substring(1));
                  }
               case 't':
                  var6 = JVMInstruction.String2arrayTypeCode(var3);
                  if (var6 == -1) {
                     throw new ParseException("Incorrect typecode" + var3);
                  }

                  var2[var4] = new Byte((byte)var6);
                  break;
               case 'v':
                  if (!var3.startsWith("v")) {
                     throw new ParseException("local variable must begin with 'v' " + var3);
                  }

                  try {
                     var2[var4] = Byte.valueOf(var3.substring(1));
                     break;
                  } catch (NumberFormatException var30) {
                     throw new ParseException("Cannot parse " + var3.substring(1));
                  }
               default:
                  throw new ParseException("Unknown operandType " + super.d_operandTypes.charAt(var4));
               }
            }
         }

         return var2;
      }
   }

   Instruction(TagFactory var1, ConstantPool var2, byte[] var3, int var4, int var5) {
      super(var3[var4]);
      this.d_cp = var2;
      this.d_code = new byte[var5];
      System.arraycopy(var3, var4, this.d_code, 0, var5);
      this.d_fac = var1;
      this.d_tag = null;
   }

   public int getOpCode() {
      return super.d_opCode;
   }

   Instruction(TagFactory var1, ConstantPool var2, String var3) throws InstructionFormatException {
      this.d_fac = var1;
      this.d_cp = var2;
      this.d_tag = null;
      this.verify(var3);
   }

   private void verify(String var1) throws InstructionFormatException {
      StringTokenizer var3 = new StringTokenizer(var1);
      if (!var3.hasMoreTokens()) {
         throw new InstructionFormatException("null Instruction");
      } else {
         String var4 = var3.nextToken();
         if (var4.endsWith(":")) {
            this.d_tag = var4.substring(0, var4.length() - 1);
         }

         String var5;
         if (this.d_tag == null) {
            var5 = var4;
         } else {
            if (!var3.hasMoreTokens()) {
               throw new InstructionFormatException("malformed tag, no opcode");
            }

            var5 = var3.nextToken();
         }

         int var2 = JVMInstruction.isOpcode(var5);
         super.d_opCode = var2;
         super.d_name = JVMInstruction.d_instrTable[var2].d_name;
         super.d_operandTypes = JVMInstruction.d_instrTable[var2].operandTypes();
         super.d_len = JVMInstruction.d_instrTable[var2].d_len;
         if (super.d_operandTypes.equals("unused")) {
            throw new InstructionFormatException("unused instruction");
         } else {
            int var6 = var1.indexOf(var5);
            D.assert(var6 != -1, "Argh: sArgs == -1");
            String var7 = var1.substring(var6 + var5.length()).trim();

            try {
               Object[] var8 = this.parseString(var7);
               this.d_code = this.writeArgs(var8);
            } catch (ParseException var9) {
               throw new InstructionFormatException(var9.getMessage());
            }
         }
      }
   }

   public String getTag() {
      return this.d_tag;
   }

   public void setTag(String var1) {
      this.d_tag = var1;
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
         if (this.d_varTable != null) {
            var1.append(this.d_varTable[var5]);
         } else {
            var1.append("v" + var5);
         }
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
         if (this.d_varTable != null) {
            var1.append(this.d_varTable[var5]);
         } else {
            var1.append("v" + var5);
         }
         break;
      default:
         var1.append("<unknown operand type: " + var3 + ">");
      }

   }

   public String toString() {
      char[] var1 = new char[]{'i', 'l', 'f', 'd', 'a'};
      StringBuffer var3 = new StringBuffer(this.name() + " ");
      int var4;
      int var5;
      int var9;
      if (this.len() == 0) {
         byte var2;
         int var6;
         int var7;
         switch(super.d_opCode) {
         case 170:
            var2 = 1;
            this.sprintOperand(var3, var2, 'A');
            var9 = var2 + 4;
            var3.append(" ");
            var4 = ByteArray.getIntAtOffset(this.d_code, var9);
            this.sprintOperand(var3, var9, 'l');
            var9 += 4;
            var3.append("-");
            var5 = ByteArray.getIntAtOffset(this.d_code, var9);
            this.sprintOperand(var3, var9, 'l');
            var9 += 4;
            var3.append(" ");

            for(var6 = var4; var6 <= var5; ++var6) {
               this.sprintOperand(var3, var9, 'A');
               var9 += 4;
               var3.append(" ");
            }

            return var3.toString();
         case 171:
            var2 = 1;
            this.sprintOperand(var3, var2, 'A');
            var9 = var2 + 4;
            var3.append(" ");
            var6 = ByteArray.getIntAtOffset(this.d_code, var9);
            this.sprintOperand(var3, var9, 'l');
            var9 += 4;
            var3.append(" ");

            for(var7 = 0; var7 < var6; ++var7) {
               var3.append("(");
               this.sprintOperand(var3, var9, 'l');
               var9 += 4;
               var3.append(", ");
               this.sprintOperand(var3, var9, 'A');
               var9 += 4;
               var3.append(") ");
            }

            return var3.toString();
         case 196:
            var7 = ByteArray.getByteAtOffset(this.d_code, 1);
            JVMInstruction var8 = JVMInstruction.d_instrTable[var7];
            var3.append(var8.name() + " ");
            this.sprintOperand(var3, 2, 'V');
            var3.append(" ");
            if (var7 == 132) {
               this.sprintOperand(var3, 4, 'I');
            }
            break;
         default:
            throw "Unknown special op code".new AssertionException();
         }
      } else if (this.d_varTable != null && super.d_opCode > 25 && super.d_opCode < 46) {
         var4 = super.d_opCode - 26;
         var5 = var4 % 4;
         var4 /= 4;
         var3.setLength(0);
         var3.append(var1[var4] + "load " + this.d_varTable[var5]);
      } else if (this.d_varTable != null && super.d_opCode > 58 && super.d_opCode < 79) {
         var4 = super.d_opCode - 59;
         var5 = var4 % 4;
         var4 /= 4;
         var3.setLength(0);
         var3.append(var1[var4] + "store " + this.d_varTable[var5]);
      } else {
         var9 = 1;
         var4 = super.d_operandTypes.length();

         for(var5 = 0; var5 < var4; ++var5) {
            this.sprintOperand(var3, var9, super.d_operandTypes.charAt(var5));
            var9 += JVMInstruction.argSkip(super.d_operandTypes.charAt(var5));
            var3.append(" ");
         }
      }

      return var3.toString();
   }

   private BitSet setUses() {
      if (this.marked) {
         return this.d_uses;
      } else {
         this.marked = true;
         if (super.d_len != 0) {
            int var1 = super.d_operandTypes.length();

            for(int var2 = 0; var2 < var1; ++var2) {
               switch(super.d_operandTypes.charAt(var2)) {
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

   public void setVarTable(String[] var1) {
      this.d_varTable = var1;
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
      if (super.d_len == 0 && super.d_opCode != 196) {
         int var2 = (4 - (var1 + 1) % 4) % 4;
         return this.d_code.length + var2;
      } else {
         return this.d_code.length;
      }
   }

   public byte[] getCode(int[] var1, int var2) {
      byte[] var3 = null;
      if (!super.d_operandTypes.equals("") && var1 != null) {
         int var4;
         int var5;
         switch(super.d_operandTypes.charAt(0)) {
         case 'A':
            var4 = ByteArray.getIntAtOffset(this.d_code, 1);
            var5 = var1[var4] - var2;
            var3 = new byte[this.d_code.length];
            var3[0] = (byte)super.d_opCode;
            var3[1] = (byte)(var5 >>> 24 & 255);
            var3[2] = (byte)(var5 >>> 16 & 255);
            var3[3] = (byte)(var5 >>> 8 & 255);
            var3[4] = (byte)(var5 & 255);
            break;
         case 'a':
            var4 = ByteArray.getSignedShortAtOffset(this.d_code, 1);
            var5 = var1[var4] - var2;
            var3 = new byte[this.d_code.length];
            var3[0] = (byte)super.d_opCode;
            var3[1] = (byte)(var5 >>> 8 & 255);
            var3[2] = (byte)(var5 & 255);
            break;
         case 's':
            boolean var6;
            byte var7;
            int var8;
            int var9;
            int var11;
            int var14;
            int var15;
            switch(super.d_opCode) {
            case 170:
               var9 = (4 - (var2 + 1) % 4) % 4;
               var7 = 0;
               var6 = false;
               var3 = new byte[var9 + this.d_code.length];
               var15 = var7 + 1;
               var3[var7] = (byte)super.d_opCode;

               for(var14 = 0; var14 < var9; ++var14) {
                  var3[var15++] = 0;
               }

               var4 = ByteArray.getIntAtOffset(this.d_code, 1);
               var5 = var1[var4] - var2;
               var3[var15++] = (byte)(var5 >>> 24 & 255);
               var3[var15++] = (byte)(var5 >>> 16 & 255);
               var3[var15++] = (byte)(var5 >>> 8 & 255);
               var3[var15++] = (byte)(var5 & 255);
               var14 = 5;

               do {
                  var3[var15++] = this.d_code[var14];
                  ++var14;
               } while(var14 < 13);

               var11 = ByteArray.getIntAtOffset(this.d_code, 5);
               int var12 = ByteArray.getIntAtOffset(this.d_code, 9);
               var8 = 13;

               for(int var13 = var11; var13 <= var12; ++var13) {
                  var4 = ByteArray.getIntAtOffset(this.d_code, var8);
                  var5 = var1[var4] - var2;
                  var3[var15++] = (byte)(var5 >>> 24 & 255);
                  var3[var15++] = (byte)(var5 >>> 16 & 255);
                  var3[var15++] = (byte)(var5 >>> 8 & 255);
                  var3[var15++] = (byte)(var5 & 255);
                  var8 += 4;
               }

               return var3;
            case 171:
               var9 = (4 - (var2 + 1) % 4) % 4;
               var7 = 0;
               var6 = false;
               var3 = new byte[var9 + this.d_code.length];
               var15 = var7 + 1;
               var3[var7] = (byte)super.d_opCode;

               for(var14 = 0; var14 < var9; ++var14) {
                  var3[var15++] = 0;
               }

               var4 = ByteArray.getIntAtOffset(this.d_code, 1);
               var5 = var1[var4] - var2;
               var3[var15++] = (byte)(var5 >>> 24 & 255);
               var3[var15++] = (byte)(var5 >>> 16 & 255);
               var3[var15++] = (byte)(var5 >>> 8 & 255);
               var3[var15++] = (byte)(var5 & 255);
               var14 = 5;

               do {
                  var3[var15++] = this.d_code[var14];
                  ++var14;
               } while(var14 < 9);

               int var10 = ByteArray.getIntAtOffset(this.d_code, 5);
               var8 = 9;

               for(var11 = 0; var11 < var10; ++var11) {
                  var14 = 0;

                  do {
                     var3[var15++] = this.d_code[var8 + var14];
                     ++var14;
                  } while(var14 < 4);

                  var8 += 4;
                  var4 = ByteArray.getIntAtOffset(this.d_code, var8);
                  var5 = var1[var4] - var2;
                  var3[var15++] = (byte)(var5 >>> 24 & 255);
                  var3[var15++] = (byte)(var5 >>> 16 & 255);
                  var3[var15++] = (byte)(var5 >>> 8 & 255);
                  var3[var15++] = (byte)(var5 & 255);
                  var8 += 4;
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

   private byte[] writeArgs(Object[] var1) throws InstructionFormatException {
      int var2 = -1;
      byte var3 = 0;
      if (super.d_operandTypes.length() != var1.length && !super.d_operandTypes.equals("special")) {
         throw new InstructionFormatException("Bad Operands");
      } else {
         ByteArrayOutputStream var4 = new ByteArrayOutputStream();
         DataOutputStream var5 = new DataOutputStream(var4);

         try {
            var5.writeByte(super.d_opCode);
         } catch (IOException var25) {
            throw new InstructionFormatException("IO Error");
         }

         byte var6 = 0;
         int var7;
         int var8;
         int var30;
         if (super.d_opCode == 170) {
            try {
               var30 = var6 + 1;
               var5.writeInt((Integer)var1[var6]);
               var7 = (Integer)var1[var30++];
               var8 = (Integer)var1[var30++];
               var5.writeInt(var7);
               var5.writeInt(var8);

               for(int var34 = 0; var34 <= var8 - var7; ++var34) {
                  var5.writeInt((Integer)var1[var30++]);
               }
            } catch (ClassCastException var26) {
               throw new InstructionFormatException("Expecting Integer");
            } catch (IOException var27) {
               throw new InstructionFormatException("IO Error");
            }

            return var4.toByteArray();
         } else if (super.d_opCode == 171) {
            try {
               var30 = var6 + 1;
               var5.writeInt((Integer)var1[var6]);
               var7 = (Integer)var1[var30++];
               var5.writeInt(var7);

               for(var8 = 0; var8 < var7; ++var8) {
                  var5.writeInt((Integer)var1[var30++]);
                  var5.writeInt((Integer)var1[var30++]);
               }
            } catch (ClassCastException var28) {
               throw new InstructionFormatException("Expecting Integer");
            } catch (IOException var29) {
               throw new InstructionFormatException("IO Error");
            }

            return var4.toByteArray();
         } else if (super.d_opCode == 196) {
            short var32 = -1;
            var8 = JVMInstruction.isOpcode(((String)var1[0]).trim());
            if (var8 == -1) {
               throw new InstructionFormatException("Expected valid opCode");
            } else if (!(var1[1] instanceof Short)) {
               throw new InstructionFormatException("Expected Short");
            } else {
               short var9 = (Short)var1[1];
               if (var1.length == 3) {
                  if (!(var1[2] instanceof Short)) {
                     throw new InstructionFormatException("Expected Short");
                  }

                  var32 = (Short)var1[2];
               }

               byte[] var10;
               if (var8 != 132) {
                  if (var9 < 255) {
                     try {
                        var5.writeByte(var9);
                     } catch (IOException var11) {
                        throw new InstructionFormatException("IO Error");
                     }

                     super.d_opCode = var8;
                     super.d_name = JVMInstruction.d_instrTable[var8].d_name;
                     super.d_operandTypes = JVMInstruction.d_instrTable[var8].operandTypes();
                     super.d_len = JVMInstruction.d_instrTable[var8].d_len;
                     var10 = var4.toByteArray();
                     var10[0] = (byte)(var8 & 255);
                     return var10;
                  }

                  try {
                     var5.writeByte(var8);
                     var5.writeShort(var9);
                  } catch (IOException var14) {
                     throw new InstructionFormatException("IO Error");
                  }
               } else {
                  if (var9 < 255 && var32 < 127 && var32 > -128) {
                     try {
                        var5.writeByte(var9);
                        var5.writeByte(var32);
                     } catch (IOException var12) {
                        throw new InstructionFormatException("IO Error");
                     }

                     super.d_opCode = var8;
                     super.d_name = JVMInstruction.d_instrTable[var8].d_name;
                     super.d_operandTypes = JVMInstruction.d_instrTable[var8].operandTypes();
                     super.d_len = JVMInstruction.d_instrTable[var8].d_len;
                     var10 = var4.toByteArray();
                     var10[0] = (byte)(var8 & 255);
                     return var10;
                  }

                  try {
                     var5.writeByte(var8);
                     var5.writeShort(var9);
                     var5.writeShort(var32);
                  } catch (IOException var13) {
                     throw new InstructionFormatException("IO Error");
                  }
               }

               return var4.toByteArray();
            }
         } else {
            for(var7 = 0; var7 < super.d_operandTypes.length(); ++var7) {
               float var33;
               switch(super.d_operandTypes.charAt(var7)) {
               case '0':
               case 'i':
               case 't':
               case 'v':
                  if (!(var1[var7] instanceof Byte)) {
                     throw new InstructionFormatException("Expected Byte (" + super.d_operandTypes.charAt(var7) + ")");
                  }

                  try {
                     var5.writeByte((Byte)var1[var7]);
                     break;
                  } catch (IOException var24) {
                     throw new InstructionFormatException("IO Error");
                  }
               case 'A':
               case 'l':
                  if (!(var1[var7] instanceof Integer)) {
                     throw new InstructionFormatException("Expected Integer");
                  }

                  try {
                     var5.writeInt((Integer)var1[var7]);
                     break;
                  } catch (IOException var22) {
                     throw new InstructionFormatException("IO Error");
                  }
               case 'C':
                  if (super.d_opCode == 19) {
                     if (var1[var7] instanceof String) {
                        var2 = this.d_cp.find(8, (String)var1[var7]);
                        if (var2 == -1) {
                           var2 = this.d_cp.addString((String)var1[var7]);
                        }
                     } else if (var1[var7] instanceof Integer) {
                        var8 = (Integer)var1[var7];
                        var2 = this.d_cp.find(var8);
                        if (var2 == -1) {
                           var2 = this.d_cp.addInteger(var8);
                        }
                     } else {
                        if (!(var1[var7] instanceof Float)) {
                           throw new InstructionFormatException("Wrong Type");
                        }

                        var33 = (Float)var1[var7];
                        var2 = this.d_cp.find(var33);
                        if (var2 == -1) {
                           var2 = this.d_cp.addFloat(var33);
                        }
                     }

                     try {
                        if (var2 < 255) {
                           var3 = 18;
                           var5.writeByte(var2);
                        } else {
                           var5.writeShort(var2);
                        }
                     } catch (IOException var20) {
                        throw new InstructionFormatException("IO Error");
                     }
                  } else if (super.d_opCode == 20) {
                     if (var1[var7] instanceof Long) {
                        long var35 = (Long)var1[var7];
                        var2 = this.d_cp.find((float)var35);
                        if (var2 == -1) {
                           var2 = this.d_cp.addLong(var35);
                        }
                     } else {
                        if (!(var1[var7] instanceof Double)) {
                           throw new InstructionFormatException("Wrong Type");
                        }

                        double var36 = (Double)var1[var7];
                        var2 = this.d_cp.find(var36);
                        if (var2 == -1) {
                           var2 = this.d_cp.addDouble(var36);
                        }
                     }

                     try {
                        var5.writeShort(var2);
                     } catch (IOException var19) {
                        throw new InstructionFormatException("IO Error");
                     }
                  } else if (super.d_opCode != 178 && super.d_opCode != 179 && super.d_opCode != 180 && super.d_opCode != 181) {
                     if (super.d_opCode != 182 && super.d_opCode != 183 && super.d_opCode != 184) {
                        if (super.d_opCode == 185) {
                           if (!(var1[var7] instanceof String)) {
                              throw new InstructionFormatException("Wrong Type");
                           }

                           var2 = this.d_cp.find(11, (String)var1[var7]);
                           if (var2 == -1) {
                              var2 = this.d_cp.addInterface((String)var1[var7]);
                           }

                           try {
                              var5.writeShort(var2);
                           } catch (IOException var16) {
                              throw new InstructionFormatException("IO Error");
                           }
                        } else if (super.d_opCode == 187 || super.d_opCode == 189 || super.d_opCode == 192 || super.d_opCode == 193 || super.d_opCode == 197) {
                           if (!(var1[var7] instanceof String)) {
                              throw new InstructionFormatException("Wrong Type");
                           }

                           var2 = this.d_cp.find(7, (String)var1[var7]);
                           if (var2 == -1) {
                              var2 = this.d_cp.addClass((String)var1[var7]);
                           }

                           try {
                              var5.writeShort(var2);
                           } catch (IOException var15) {
                              throw new InstructionFormatException("IO Error");
                           }
                        }
                     } else {
                        if (!(var1[var7] instanceof String)) {
                           throw new InstructionFormatException("Wrong Type");
                        }

                        var2 = this.d_cp.find(10, (String)var1[var7]);
                        if (var2 == -1) {
                           var2 = this.d_cp.addMethod((String)var1[var7]);
                        }

                        try {
                           var5.writeShort(var2);
                        } catch (IOException var17) {
                           throw new InstructionFormatException("IO Error");
                        }
                     }
                  } else {
                     if (!(var1[var7] instanceof String)) {
                        throw new InstructionFormatException("Wrong Type");
                     }

                     var2 = this.d_cp.find(9, (String)var1[var7]);
                     if (var2 == -1) {
                        var2 = this.d_cp.addField((String)var1[var7]);
                     }

                     try {
                        var5.writeShort(var2);
                     } catch (IOException var18) {
                        throw new InstructionFormatException("IO Error");
                     }
                  }
                  break;
               case 'I':
               case 'V':
               case 'a':
                  if (!(var1[var7] instanceof Short)) {
                     throw new InstructionFormatException("Expected Short");
                  }

                  try {
                     var5.writeShort((Short)var1[var7]);
                     break;
                  } catch (IOException var23) {
                     throw new InstructionFormatException("IO Error");
                  }
               case 'c':
                  if (super.d_opCode == 18) {
                     if (var1[var7] instanceof String) {
                        var2 = this.d_cp.find(8, (String)var1[var7]);
                        if (var2 == -1) {
                           var2 = this.d_cp.addString((String)var1[var7]);
                        }
                     } else if (var1[var7] instanceof Integer) {
                        var8 = (Integer)var1[var7];
                        var2 = this.d_cp.find(var8);
                        if (var2 == -1) {
                           var2 = this.d_cp.addInteger(var8);
                        }
                     } else {
                        if (!(var1[var7] instanceof Float)) {
                           throw new InstructionFormatException("Wrong Type");
                        }

                        var33 = (Float)var1[var7];
                        var2 = this.d_cp.find(var33);
                        if (var2 == -1) {
                           var2 = this.d_cp.addFloat(var33);
                        }
                     }
                  }

                  try {
                     if (var2 > 255) {
                        var3 = 19;
                        var5.writeShort(var2);
                     } else {
                        var5.writeByte(var2);
                     }
                     break;
                  } catch (IOException var21) {
                     throw new InstructionFormatException("IO Error");
                  }
               default:
                  throw new InstructionFormatException("Unknown operandType " + super.d_operandTypes.charAt(var7));
               }
            }

            if (var3 != 0) {
               super.d_opCode = var3;
               super.d_name = JVMInstruction.d_instrTable[var3].d_name;
               super.d_operandTypes = JVMInstruction.d_instrTable[var3].operandTypes();
               super.d_len = JVMInstruction.d_instrTable[var3].d_len;
               byte[] var31 = var4.toByteArray();
               var31[0] = (byte)(var3 & 255);
               return var31;
            } else {
               return var4.toByteArray();
            }
         }
      }
   }
}
