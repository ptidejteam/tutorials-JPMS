package com.ibm.toad.cfparse.utils;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.instruction.BaseInstruction;
import com.ibm.toad.cfparse.instruction.ImmutableCodeSegment;
import com.ibm.toad.cfparse.instruction.JVMInstruction;
import com.ibm.toad.cfparse.instruction.MutableCodeSegment;
import com.ibm.toad.utils.D;
import java.util.Stack;
import java.util.Vector;

public class InstrUtils {
   private static final boolean debug = false;
   public static final int DEFAULT = 0;
   public static final int JUMP = 1;
   public static final int CJUMP = 2;
   public static final int TJUMP = 3;
   public static final int RETURN = 4;
   public static final int THROW = 5;
   public static final int VARIABLE = 6;
   public static final int JSR = 7;
   public static final int JSR_RET = 8;
   private static final int SPECIAL = 195936448;
   private static int[] stackChanges = new int[]{0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, -1, 0, -1, 0, -1, -1, -1, -1, -1, -2, -1, -2, -1, -1, -1, -1, -1, -2, -2, -2, -2, -1, -1, -1, -1, -2, -2, -2, -2, -1, -1, -1, -1, -3, -4, -3, -4, -3, -3, -3, -3, -1, -2, 1, 1, 1, 2, 2, 2, 0, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -2, -1, -2, -1, -2, 0, 1, 0, 1, -1, -1, 0, 0, 1, 1, -1, 0, -1, 0, 0, 0, -3, -1, -1, -3, -3, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -2, -2, -2, 0, 1, 0, -1, -1, 195936448, 195936448, 195936448, 195936448, 195936448, 195936448, 195936448, 195936448, 195936448, 195936448, 195936448, 195936448, 195936448, 195936448, 0, 1, 0, 0, 0, 195936448, 0, 0, -1, -1, 0, 195936448, -1, -1, 0, 1, 0, 1, 1, 2, 0, -2, 1, -3, 1, -1, 2, -2, 195936448, 195936448, 195936448, 195936448, 195936448, 195936448, 0, 1, 0, 195936448, 0, 0, 195936448, 195936448, 195936448, 0, 0, 0};

   private static int vStackEffect(ConstantPool var0, byte[] var1, int var2) {
      int var3;
      int var4;
      switch(var1[var2] & 255) {
      case 178:
         var3 = ByteArray.getShortAtOffset(var1, 1);
         var4 = -CPUtils.countWords(var0, var3);
         return var4;
      case 179:
      case 184:
         var3 = ByteArray.getShortAtOffset(var1, 1);
         var4 = CPUtils.countWords(var0, var3);
         return var4;
      case 180:
         var3 = ByteArray.getShortAtOffset(var1, 1);
         var4 = -CPUtils.countWords(var0, var3) - 1;
         return var4;
      case 181:
      case 182:
      case 183:
         var3 = ByteArray.getShortAtOffset(var1, 1);
         var4 = CPUtils.countWords(var0, var3) - 1;
         return var4;
      case 185:
         var3 = ByteArray.getShortAtOffset(var1, 1);
         ByteArray.getByteAtOffset(var1, 3);
         var4 = CPUtils.countWords(var0, var3) - 1;
         return var4;
      case 197:
         var3 = ByteArray.getByteAtOffset(var1, 3);
         return -(var3 - 1);
      default:
         throw new RuntimeException("Unknown Special Stack Effect");
      }
   }

   public static int[] getJumpTargets(ImmutableCodeSegment var0, int var1) {
      int[] var2 = null;
      if (var1 >= 0 && var1 < var0.getNumInstructions()) {
         byte[] var3 = var0.getInstruction(var1);
         int var4 = var0.getOffset(var1);
         int var5 = instrType(var3[0]);
         int var12;
         if (var5 != 1 && var5 != 2 && var5 != 7) {
            if (var5 == 3) {
               byte var8 = 0;
               int var7;
               int var9;
               int var10;
               int var13;
               if (var3[0] == 171) {
                  var12 = ByteArray.getIntAtOffset(var3, 1);
                  var9 = ByteArray.getIntAtOffset(var3, 5);
                  var2 = new int[var9 + 1];
                  var13 = var8 + 1;
                  var2[var8] = var0.getInum(var4 + var12);
                  var7 = 9;

                  for(var10 = 0; var10 < var9; ++var10) {
                     var7 += 4;
                     var12 = ByteArray.getIntAtOffset(var3, var7);
                     var2[var13++] = var0.getInum(var12 + var4);
                     var7 += 4;
                  }
               } else {
                  var12 = ByteArray.getIntAtOffset(var3, 1);
                  var9 = ByteArray.getIntAtOffset(var3, 5);
                  var10 = ByteArray.getIntAtOffset(var3, 9);
                  var2 = new int[var10 - var9 + 1 + 1];
                  var13 = var8 + 1;
                  var2[var8] = var0.getInum(var12 + var4);
                  var7 = 13;

                  for(int var11 = var9; var11 <= var10; ++var11) {
                     var12 = ByteArray.getIntAtOffset(var3, var7);
                     var2[var13++] = var0.getInum(var12 + var4);
                     var7 += 4;
                  }
               }
            }
         } else {
            var2 = new int[1];
            boolean var6 = false;
            if (var3[0] != 200 && var3[0] != 200) {
               var12 = ByteArray.getSignedShortAtOffset(var3, 1);
            } else {
               var12 = ByteArray.getIntAtOffset(var3, 1);
            }

            var2[0] = var0.getInum(var4 + var12);
            if (var2[0] == -1) {
               return null;
            }
         }

         return var2;
      } else {
         return null;
      }
   }

   public static int[] getJumpTargets(MutableCodeSegment var0, BaseInstruction var1) {
      var0.getInstructions();
      int[] var2 = null;
      int var3 = instrType(var1.getOpCode());
      int var12;
      if (var3 != 1 && var3 != 2 && var3 != 7) {
         if (var3 == 3) {
            byte var13 = 0;
            byte[] var15 = var1.getCode((int[])null, 0);
            int var8;
            int var9;
            int var11;
            int var14;
            if (var1.getOpCode() == 171) {
               var11 = ByteArray.getIntAtOffset(var15, 1);
               var8 = ByteArray.getIntAtOffset(var15, 5);
               var2 = new int[var8 + 1];
               var14 = var13 + 1;
               var2[var13] = getBlockNum(var0, var0.getTag(var11));
               var12 = 9;

               for(var9 = 0; var9 < var8; ++var9) {
                  var12 += 4;
                  var11 = ByteArray.getIntAtOffset(var15, var12);
                  var2[var14++] = getBlockNum(var0, var0.getTag(var11));
                  var12 += 4;
               }
            } else {
               var11 = ByteArray.getIntAtOffset(var15, 1);
               var8 = ByteArray.getIntAtOffset(var15, 5);
               var9 = ByteArray.getIntAtOffset(var15, 9);
               var2 = new int[var9 - var8 + 1 + 1];
               var14 = var13 + 1;
               var2[var13] = getBlockNum(var0, var0.getTag(var11));
               var12 = 13;

               for(int var10 = var8; var10 <= var9; ++var10) {
                  var11 = ByteArray.getIntAtOffset(var15, var12);
                  var2[var14++] = getBlockNum(var0, var0.getTag(var11));
                  var12 += 4;
               }
            }
         }
      } else {
         var2 = new int[1];
         byte[] var4 = var1.getCode((int[])null, 0);
         boolean var5 = false;
         String var6 = JVMInstruction.d_instrTable[var1.getOpCode()].operandTypes();
         if (var6.charAt(0) == 'a') {
            var12 = ByteArray.getSignedShortAtOffset(var4, 1);
         } else {
            var12 = ByteArray.getIntAtOffset(var4, 1);
         }

         String var7 = var0.getTag(var12);
         if (var7 == null) {
            return null;
         }

         var2[0] = getBlockNum(var0, var7);
         if (var2[0] == -1) {
            return null;
         }
      }

      return var2;
   }

   public static int instrType(int var0) {
      switch(var0) {
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 198:
      case 199:
         return 2;
      case 167:
      case 200:
         return 1;
      case 168:
      case 201:
         return 7;
      case 169:
         return 8;
      case 170:
      case 171:
         return 3;
      case 172:
      case 173:
      case 174:
      case 175:
      case 176:
      case 177:
         return 4;
      case 178:
      case 179:
      case 180:
      case 181:
      case 182:
      case 183:
      case 184:
      case 185:
      case 197:
         return 6;
      case 191:
         return 5;
      default:
         return 0;
      }
   }

   public static int getMaxStack(ConstantPool var0, CodeAttrInfo var1) {
      int var2 = 0;
      byte[] var4 = var1.getCode();
      ImmutableCodeSegment var5 = new ImmutableCodeSegment(var4);
      Stack var6 = new Stack();
      Stack var7 = new Stack();
      int var8 = var5.getNumInstructions();
      boolean[] var9 = new boolean[var8];
      int[] var10 = new int[var8];

      int var11;
      for(var11 = 0; var11 < var8; ++var11) {
         var9[var11] = false;
         var10[var11] = 0;
      }

      var11 = var1.getNumExceptions();
      var6.push(new Integer(0));

      int var13;
      for(int var12 = 0; var12 < var11; ++var12) {
         var13 = var5.getInum(var1.getException(var12).getHandler());
         var6.push(new Integer(var13));
         var10[var13] = 1;
      }

      while(true) {
         do {
            if (var6.empty()) {
               return var2;
            }

            var13 = (Integer)var6.pop();
         } while(var9[var13]);

         int var3 = var10[var13];
         boolean var14 = false;

         while(!var14 && !var9[var13]) {
            int var15 = var5.getOffset(var13);
            var9[var13] = true;
            var10[var13] = var3;
            System.out.println(var13 + ":" + var15 + ":" + stackEffect(var0, var4, var15));
            var3 += stackEffect(var0, var4, var15);
            if (var2 < var3) {
               var2 = var3;
            }

            int[] var17;
            label73:
            switch(instrType(var4[var15])) {
            case 1:
               var17 = getJumpTargets(var5, var13);
               if (var17 == null) {
                  throw new RuntimeException("Stack Verify Error: Instruction with no computable target");
               }

               var13 = var17[0];
               break;
            case 2:
               var17 = getJumpTargets(var5, var13);
               if (var17 == null) {
            	   throw new RuntimeException("Stack Verify Error: Instruction with no computable target");
               }

               var6.push(new Integer(var17[0]));
               var10[var17[0]] = var3;
               ++var13;
               break;
            case 3:
               var17 = getJumpTargets(var5, var13);
               if (var17 == null) {
                  throw new RuntimeException ("Stack Verify Error: Instruction with no computable target ");
               }

               var13 = var17[0];
               int var16 = 1;

               while(true) {
                  if (var16 >= var17.length) {
                     break label73;
                  }

                  var6.push(new Integer(var17[var16]));
                  var10[var17[var16]] = var3;
                  ++var16;
               }
            case 4:
            case 5:
               var14 = true;
               break;
            case 6:
            default:
               ++var13;
               break;
            case 7:
               var17 = getJumpTargets(var5, var13);
               if (var17 == null) {
                  throw new RuntimeException("Stack Verify Error: Instruction with no computable target ");
               }

               var7.push(new Integer(var13 + 1));
               var13 = var17[0];
               break;
            case 8:
               if (var7.empty()) {
            	   throw new RuntimeException ("Stack Verify Error: jsr return  with no target ");
               }

               var13 = (Integer)var7.pop();
            }

            if (var13 == var8) {
               var14 = true;
            }
         }
      }
   }

   public static int getMaxStack(ConstantPool var0, MutableCodeSegment var1) {
      int var2 = -65536;
      int var3 = -1160052736;
      int var4 = 0;
      Vector var5 = var1.getInstructions();
      int[] var6 = new int[var1.numTags() + 1];

      class residue {
         int d_r;
         int d_p;
         residue d_next;

         residue(int var1, int var2, residue var3) {
            this.d_r = var1;
            this.d_p = var2;
            this.d_next = var3;
         }
      }

      residue[] var7 = new residue[var1.numTags() + 1];
      int[] var8 = new int[var1.numTags() + 1];

      int var9;
      for(var9 = 0; var9 < var1.numTags() + 1; ++var9) {
         var7[var9] = new residue(0, var3, (residue)null);
         var8[var9] = var3;
         var6[var9] = 0;
      }

      var9 = 0;
      int var10 = 0;
      var7[var9].d_p = 0;
      boolean var11 = true;

      int var12;
      residue var14;
      int var15;
      for(var12 = 0; var12 < var5.size(); ++var12) {
         BaseInstruction var13 = (BaseInstruction)var5.elementAt(var12);
         if (var12 > 0 && var13.getTag() != null) {
            ++var9;
            if (var11) {
               if (var7[var9].d_p == var3) {
                  if (var7[var9 - 1].d_p != var3 && (var7[var9 - 1].d_p & var2) != var2) {
                     var7[var9].d_p = var10 + var7[var9 - 1].d_p;
                  } else {
                     var7[var9].d_p = var2 | var9 - 1;
                     var7[var9].d_r = var10;
                  }
               } else if ((var7[var9].d_p & var2) == var2) {
                  if (var7[var9 - 1].d_p != var3 && (var7[var9 - 1].d_p & var2) != var2) {
                     var7[var9].d_p = var10 + var7[var9 - 1].d_p;
                     var7[var9].d_r = 0;
                     var7[var9].d_next = null;
                  } else {
                     var14 = new residue(var10, var2 | var9 - 1, var7[var9]);
                     var7[var9] = var14;
                  }
               }
            }

            var10 = 0;
            var11 = true;
         }

         byte[] var21 = var13.getCode((int[])null, 0);
         var15 = stackEffect(var0, var21, 0);
         var10 += var15;
         if (var6[var9] < var10) {
            var6[var9] = var10;
         }

         int var16 = instrType(var13.getOpCode());
         if (var16 == 1 || var16 == 2 || var16 == 3 || var16 == 7) {
            int[] var17 = getJumpTargets(var1, var13);
            if (var17 == null) {
               throw new RuntimeException("Stack Verify Error: Instruction with no computable target ");
            }

            for(int var18 = 0; var18 < var17.length; ++var18) {
               if (var7[var17[var18]].d_p == var3) {
                  if (var7[var9].d_p != var3 && (var7[var9].d_p & var2) != var2) {
                     var7[var17[var18]].d_p = var10 + var7[var9].d_p;
                  } else {
                     var7[var17[var18]].d_p = var2 | var9;
                     var7[var17[var18]].d_r = var10;
                  }
               } else if ((var7[var17[var18]].d_p & var2) == var2) {
                  if (var7[var9].d_p != var3 && (var7[var9].d_p & var2) != var2) {
                     var7[var17[var18]].d_p = var10 + var7[var9].d_p;
                     var7[var17[var18]].d_r = 0;
                     var7[var17[var18]].d_next = null;
                  } else {
                     residue var19 = new residue(var10, var2 | var9, var7[var17[var18]]);
                     var7[var17[var18]] = var19;
                  }
               }
            }
         }

         if (var16 == 1 || var16 == 4 || var16 == 5) {
            var11 = false;
         }

         if (var16 == 7) {
            --var10;
         }
      }

      var12 = 0;

      int var20;
      while(var12 < var1.numTags() + 1) {
         for(var20 = 0; var20 < var1.numTags() + 1; ++var20) {
            if (var8[var20] == var3) {
               if (var7[var20].d_p == var3) {
                  var7[var20].d_p = 1;
                  var8[var20] = 1;
                  ++var12;
               } else if ((var7[var20].d_p & var2) == var2) {
                  for(var14 = var7[var20]; var14 != null && (var14.d_p & var2) == var2; var14 = var14.d_next) {
                     var15 = ~var2 & var14.d_p;
                     if (var8[var15] != var3) {
                        var8[var20] = var8[var15] + var14.d_r;
                        ++var12;
                        break;
                     }
                  }
               } else {
                  var8[var20] = var7[var20].d_r + var7[var20].d_p;
                  ++var12;
               }
            }
         }
      }

      for(var20 = 0; var20 < var1.numTags() + 1; ++var20) {
         int var22;
         if ((var7[var20].d_p & var2) == var2) {
            var22 = var7[var20].d_r + var8[~var2 & var7[var20].d_p] + var6[var20];
         } else {
            var22 = var7[var20].d_r + var7[var20].d_p + var6[var20];
         }

         if (var4 < var22) {
            var4 = var22;
         }
      }

      return var4;
   }

   public static int getBlockNum(MutableCodeSegment var0, String var1) {
      Vector var2 = var0.getInstructions();
      int var3 = 0;

      for(int var4 = 0; var4 < var2.size(); ++var4) {
         BaseInstruction var5 = (BaseInstruction)var2.elementAt(var4);
         if (var5.getTag() != null) {
            if (var4 != 0) {
               ++var3;
            }

            if (var5.getTag().equals(var1)) {
               return var3;
            }
         }
      }

      return -1;
   }

   public static int stackEffect(ConstantPool var0, byte[] var1, int var2) {
      int var3 = stackChanges[var1[var2] & 255];
      if (var3 != 195936448) {
         return var3;
      } else {
         switch(var1[var2] & 255) {
         case 169:
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
         case 191:
            return 0;
         default:
            return vStackEffect(var0, var1, var2);
         }
      }
   }
}
