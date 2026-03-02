package com.ibm.toad.cfparse.instruction;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.attributes.LineNumberAttrInfo;
import com.ibm.toad.cfparse.utils.ByteArray;
import com.ibm.toad.utils.D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.Map.Entry;

public class MutableCodeSegment implements TagFactory {
   private static final String baseTag = "TAG_";
   private int d_curTagNum;
   private HashMap d_off2tagNum;
   private HashMap d_off2iNum;
   private HashMap d_tagNum2tagStr;
   private HashMap d_tagStr2tagNum;
   private HashMap d_tagStr2iNum;
   private Vector d_instructions;
   int d_numExceptions;
   CodeAttrInfo.ExceptionInfo[] d_exceptions;
   ConstantPool d_cp;
   private InstructionFactory d_if;
   
   public CodeAttrInfo.ExceptionInfo getException(int var1) {
      return var1 >= 0 && var1 <= this.d_numExceptions ? this.d_exceptions[var1] : null;
   }

   private void fixTags() {
      Iterator var1 = this.d_off2tagNum.entrySet().iterator();

      while(var1.hasNext()) {
         Entry var2 = (Entry)var1.next();
         Integer var3 = (Integer)this.d_off2iNum.get((Integer)var2.getKey());
         String var4 = (String)this.d_tagNum2tagStr.get((Integer)var2.getValue());
         ((BaseInstruction)this.d_instructions.elementAt(var3)).setTag(var4);
         this.d_tagStr2iNum.put(var4, var3);
      }

   }

   public int getNumInstructions() {
      return this.d_instructions.size();
   }

   public int tag2Inum(String var1) {
      Integer var2 = (Integer)this.d_tagStr2iNum.get(var1);
      return var2 == null ? -1 : var2;
   }

   public String toString() {
      return this.toString(0);
   }

   private MutableCodeSegment(ConstantPool var1) {
      this.d_cp = var1;
      this.d_curTagNum = 0;
      this.d_off2tagNum = new HashMap();
      this.d_off2iNum = new HashMap();
      this.d_tagNum2tagStr = new HashMap();
      this.d_tagStr2tagNum = new HashMap();
      this.d_tagStr2iNum = new HashMap();
      this.d_instructions = new Vector();
   }

   public MutableCodeSegment(ConstantPool var1, byte[] var2, CodeAttrInfo.ExceptionInfo[] var3, int var4) {
      this(var1);
      this.d_if = new ByteInstructionFactory();
      this.init(var2, var3, var4);
   }
  
   
   public MutableCodeSegment(ConstantPool var1, CodeAttrInfo var2, boolean var3) {
      this(var1);
      this.d_if = new ByteInstructionFactory();
      this.init(var2.getCode(), var2.getExceptions(), var2.getNumExceptions());
      if (var3) {
         this.addLines(var2);
      }

   }

   
   public MutableCodeSegment(ConstantPool var1, byte[] var2, CodeAttrInfo.ExceptionInfo[] var3, int var4, InstructionFactory var5) {
      this(var1);
      this.d_if = var5;
      this.init(var2, var3, var4);
   }
   
   
   public String getTag(int var1) {
      return (String)this.d_tagNum2tagStr.get(new Integer(var1));
   }

   public MutableCodeSegment(ConstantPool var1, CodeAttrInfo var2, boolean var3, InstructionFactory var4) {
      this(var1);
      this.d_if = var4;
      this.init(var2.getCode(), var2.getExceptions(), var2.getNumExceptions());
      if (var3) {
         this.addLines(var2);
      }

   }

   private void readCode(byte[] var1) {
      int var2 = 0;

      while(var2 < var1.length) {
         int var3;
         BaseInstruction var8;
         label55: {
            var3 = var2;
            int var4 = ByteArray.getByteAtOffset(var1, var2);
            boolean var5 = false;
            boolean var6 = false;
            boolean var7 = false;
            var8 = null;
            boolean var9 = false;
            boolean var10 = false;
            ++var2;
            JVMInstruction var12 = JVMInstruction.d_instrTable[var4];
            byte[] var11;
            int var15;
            int var16;
            int var18;
            int var19;
            int var20;
            int var21;
            int var22;
            int var23;
            int var24;
            String var25;
            switch(var4) {
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
            case 167:
            case 168:
            case 198:
            case 199:
               var21 = ByteArray.getSignedShortAtOffset(var1, var2);
               var24 = this.addTag(var3 + var21);
               var11 = new byte[]{(byte)var4, (byte)(var24 >>> 8 & 255), (byte)(var24 & 255)};
               var2 += JVMInstruction.argSkip('a');
               var8 = this.d_if.create(this, this.d_cp, var11, 0, 3);
               break label55;
            case 170:
               var20 = (4 - var2 % 4) % 4;
               var2 += var20;
               var21 = ByteArray.getIntAtOffset(var1, var2);
               var23 = this.addTag(var3 + var21);
               var2 += 4;
               int var13 = ByteArray.getIntAtOffset(var1, var2);
               var2 += 4;
               int var14 = ByteArray.getIntAtOffset(var1, var2);
               var2 += 4;
               var11 = new byte[13 + (var14 - var13 + 1) * 4];
               var11[0] = (byte)var4;
               ByteArray.writeInt(var11, 1, var23);
               ByteArray.writeInt(var11, 5, var13);
               ByteArray.writeInt(var11, 9, var14);
               var22 = 13;

               for(var15 = var13; var15 <= var14; ++var15) {
                  var21 = ByteArray.getIntAtOffset(var1, var2);
                  var24 = this.addTag(var3 + var21);
                  ByteArray.writeInt(var11, var22, var24);
                  var22 += 4;
                  var2 += 4;
               }

               var8 = this.d_if.create(this, this.d_cp, var11, 0, var11.length);
               break label55;
            case 171:
               var20 = (4 - var2 % 4) % 4;
               var2 += var20;
               var21 = ByteArray.getIntAtOffset(var1, var2);
               var23 = this.addTag(var3 + var21);
               var2 += 4;
               var15 = ByteArray.getIntAtOffset(var1, var2);
               var2 += 4;
               var11 = new byte[9 + var15 * 8];
               var11[0] = (byte)var4;
               ByteArray.writeInt(var11, 1, var23);
               ByteArray.writeInt(var11, 5, var15);
               var22 = 9;

               for(var16 = 0; var16 < var15; ++var16) {
                  int var17 = ByteArray.getIntAtOffset(var1, var2);
                  ByteArray.writeInt(var11, var22, var17);
                  var22 += 4;
                  var2 += 4;
                  var21 = ByteArray.getIntAtOffset(var1, var2);
                  var24 = this.addTag(var3 + var21);
                  ByteArray.writeInt(var11, var22, var24);
                  var22 += 4;
                  var2 += 4;
               }

               var8 = this.d_if.create(this, this.d_cp, var11, 0, var11.length);
               break label55;
            case 196:
               var16 = ByteArray.getByteAtOffset(var1, var2);
               var2 += 3;
               if (var16 == 132) {
                  var2 += 2;
               }

               var8 = this.d_if.create(this, this.d_cp, var1, var3, var2 - var3);
               break label55;
            case 200:
            case 201:
               var21 = ByteArray.getIntAtOffset(var1, var2);
               var24 = this.addTag(var3 + var21);
               var11 = new byte[]{(byte)var4, (byte)(var24 >>> 24 & 255), (byte)(var24 >>> 16 & 255), (byte)(var24 >>> 8 & 255), (byte)(var24 & 255)};
               var2 += JVMInstruction.argSkip('A');
               var8 = this.d_if.create(this, this.d_cp, var11, 0, 5);
               break label55;
            default:
               var25 = var12.operandTypes();
               var18 = var25.length();
               var19 = 0;
            }

            while(var19 < var18) {
               var2 += JVMInstruction.argSkip(var25.charAt(var19));
               ++var19;
            }

            var8 = this.d_if.create(this, this.d_cp, var1, var3, var2 - var3);
         }

         this.d_off2iNum.put(new Integer(var3), new Integer(this.d_instructions.size()));
         if (var8 != null) {
            this.d_instructions.addElement(var8);
         }
      }

   }

   public void fixLineNumberTable(LineNumberAttrInfo var1) {
      int var2 = 0;
      boolean var3 = true;
      var1.clear();

      for(int var4 = 0; var4 < this.d_instructions.size(); ++var4) {
         BaseInstruction var5 = (BaseInstruction)this.d_instructions.elementAt(var4);
         if (var5.getOpCode() == 254) {
            if (var3) {
               var1.clear();
               var3 = false;
            }

            int var6 = ByteArray.getShortAtOffset(var5.getCode((int[])null, 0), 1);
            var1.add(var6, var2);
         } else {
            var2 += var5.getLength(var2);
         }
      }

   }

   public BaseInstruction create(String var1) throws InstructionFormatException {
      BaseInstruction var2 = this.d_if.create(this, this.d_cp, var1);
      String var3 = var2.getTag();
      if (var3 != null) {
         this.tagFor(var3, true);
      }

      return var2;
   }

   public String toString(int var1) {
      StringBuffer var2 = new StringBuffer();

      int var3;
      for(var3 = 0; var3 < this.d_instructions.size(); ++var3) {
         BaseInstruction var4 = (BaseInstruction)this.d_instructions.elementAt(var3);
         String var5 = var4.getTag() != null ? var4.getTag() + ":" : "\t";
         var2.append(var3 + ":" + var5 + this.sindent(var1) + " " + var4 + "\n");
      }

      var2.append(this.sindent(var1) + "Exception Table:\n");
      if (this.d_numExceptions == 0) {
         var2.append(this.sindent(var1) + "  <none>\n");
      } else {
         for(var3 = 0; var3 < this.d_numExceptions; ++var3) {
            var2.append(this.sindent(var1) + this.d_exceptions[var3] + "\n");
         }
      }

      return var2.toString();
   }

   public Vector getInstructions() {
      return this.d_instructions;
   }

   public void setInstructions(Vector var1) {
      this.d_instructions = var1;
   }

   private String sindent(int var1) {
      String var2 = "";

      for(int var3 = 0; var3 < var1; ++var3) {
         var2 = var2 + "  ";
      }

      return var2;
   }

   
   public static CodeViewer getViewer() {
      return new CodeViewer() {
         InstructionFactory d_if;

         public void setInstructionType(InstructionFactory var1) {
            this.d_if = var1;
         }

         public String view(ConstantPool var1, byte[] var2, CodeAttrInfo.ExceptionInfo[] var3, int var4, String var5) {
            MutableCodeSegment var6;
            if (this.d_if == null) {
               var6 = new MutableCodeSegment(var1, var2, var3, var4);
            } else {
               var6 = new MutableCodeSegment(var1, var2, var3, var4, this.d_if);
            }

            StringBuffer var7 = new StringBuffer();
            Vector var8 = var6.getInstructions();

            int var9;
            for(var9 = 0; var9 < var8.size(); ++var9) {
               BaseInstruction var10 = (BaseInstruction)var8.elementAt(var9);
               String var11 = var10.getTag() != null ? var10.getTag() + ":" : "\t";
               var7.append(var9 + ":" + var11 + var5 + " " + var10 + "\n");
            }

            var9 = var6.getNumExceptions();
            var7.append(var5 + "Exception Table:\n");
            if (var9 == 0) {
               var7.append(var5 + "  <none>\n");
            } else {
               for(int var12 = 0; var12 < var9; ++var12) {
                  CodeAttrInfo.ExceptionInfo var13 = var6.getException(var12);
                  var7.append(var5 + "start_tag = " + var6.getTag(var13.getStart()));
                  var7.append(", end_tag = " + var6.getTag(var13.getEnd()));
                  var7.append(", handler_tag = " + var6.getTag(var13.getHandler()));
                  var7.append(", catch_type = " + var13.getCatchType() + "\n");
               }
            }

            return var7.toString();
         }
      };
   }

   private void resize() {
      CodeAttrInfo.ExceptionInfo[] var1 = new CodeAttrInfo.ExceptionInfo[this.d_numExceptions + 10];
      if (this.d_exceptions != null) {
         System.arraycopy(this.d_exceptions, 0, var1, 0, this.d_numExceptions);
      }

      this.d_exceptions = var1;
   }

   public int tagFor(String var1, boolean var2) {
      Integer var3 = (Integer)this.d_tagStr2tagNum.get(var1);
      if (var3 != null) {
         return var3;
      } else if (!var2) {
         return -1;
      } else {
         int var4 = this.d_curTagNum++;
         Integer var5 = new Integer(var4);
         this.d_tagNum2tagStr.put(var5, var1);
         this.d_tagStr2tagNum.put(var1, var5);
         return var4;
      }
   }

   public void updateCodeAttrInfo(CodeAttrInfo var1) {
      var1.setCode(this.getCode());
      var1.setExceptions(this.getExcTable());
      AttrInfoList var2 = var1.getAttrs();
      if (var2 != null) {
         LineNumberAttrInfo var3 = (LineNumberAttrInfo)var2.get("LineNumberTable");
         if (var3 != null) {
            this.fixLineNumberTable(var3);
         }
      }
   }

   public int getNumExceptions() {
      return this.d_numExceptions;
   }

   private void addLines(CodeAttrInfo var1) {
      AttrInfoList var2 = var1.getAttrs();
      if (var2 != null) {
         LineNumberAttrInfo var3 = (LineNumberAttrInfo)var2.get("LineNumberTable");
         if (var3 != null) {
            byte[] var4 = new byte[]{-2, 0, 0};

            for(int var5 = 0; var5 < var3.length(); ++var5) {
               ByteArray.writeShort(var4, 1, var3.getLineNumber(var5));
               ByteInstruction var6 = new ByteInstruction(this, this.d_cp, var4, 0, 3);
               Integer var7 = (Integer)this.d_off2iNum.get(new Integer(var3.getStartPC(var5)));
               if (var7 == null) {
                  System.out.println("Panic, no iNum found for index " + var5 + " {" + var3.getLineNumber(var5) + ", " + var3.getStartPC(var5) + "}");
               } else {
                  this.d_instructions.insertElementAt(var6, var7 + var5);
               }
            }

         }
      }
   }

   public void createExceptionBlock(String var1, String var2, String var3, String var4) {
      if (this.d_exceptions == null || this.d_numExceptions == this.d_exceptions.length) {
         this.resize();
      }

      int var5 = this.tagFor(var1, true);
      int var6 = this.tagFor(var2, true);
      int var7 = this.tagFor(var3, true);
      this.d_exceptions[this.d_numExceptions] = new CodeAttrInfo.ExceptionInfo(this.d_cp, var5, var6, var7, var4);
      ++this.d_numExceptions;
   }

   private void init(byte[] var1, CodeAttrInfo.ExceptionInfo[] var2, int var3) {
      if (var1 != null) {
         this.readCode(var1);
      }
      
      this.makeExcTable(var2, var3);
      this.fixTags();
   }

   private int addTag(int var1) {
      Integer var2 = (Integer)this.d_off2tagNum.get(new Integer(var1));
      if (var2 != null) {
         return var2;
      } else {
         int var3 = this.d_curTagNum++;
         Integer var4 = new Integer(var3);
         Integer var5 = new Integer(var1);
         this.d_off2tagNum.put(var5, var4);
         this.d_tagNum2tagStr.put(var4, "TAG_" + var3);
         this.d_tagStr2tagNum.put("TAG_" + var3, var4);
         return var3;
      }
   }

   public byte[] getCode() {
      int[] var1 = new int[this.d_curTagNum];
      int var2 = 0;

      for(int var3 = 0; var3 < this.d_instructions.size(); ++var3) {
         BaseInstruction var4 = (BaseInstruction)this.d_instructions.elementAt(var3);
         if (var4.getOpCode() != 254) {
            if (var4.getTag() != null) {
               var1[this.tagFor(var4.getTag(), false)] = var2;
            }

            var2 += var4.getLength(var2);
         }
      }

      byte[] var7 = new byte[var2];
      var2 = 0;

      for(int var8 = 0; var8 < this.d_instructions.size(); ++var8) {
         BaseInstruction var5 = (BaseInstruction)this.d_instructions.elementAt(var8);
         if (var5.getOpCode() != 254) {
            byte[] var6 = var5.getCode(var1, var2);
            System.arraycopy(var6, 0, var7, var2, var6.length);
            //D.assert(var6.length == var5.getLength(var2), "Bad lengths in getCode");
            var2 += var5.getLength(var2);
         }
      }

      return var7;
   }

   public void setInstructionFactory(InstructionFactory var1) {
      this.d_if = var1;
   }

   public int numTags() {
      return this.d_curTagNum;
   }

   public CodeAttrInfo.ExceptionInfo[] getExcTable() {
      int[] var1 = new int[this.d_curTagNum];
      int var2 = 0;

      for(int var3 = 0; var3 < this.d_instructions.size(); ++var3) {
         BaseInstruction var4 = (BaseInstruction)this.d_instructions.elementAt(var3);
         if (var4.getOpCode() != 254) {
            if (var4.getTag() != null) {
               var1[this.tagFor(var4.getTag(), false)] = var2;
            }

            var2 += var4.getLength(var2);
         }
      }

      CodeAttrInfo.ExceptionInfo[] var8 = new CodeAttrInfo.ExceptionInfo[this.d_numExceptions];

      for(int var9 = 0; var9 < this.d_numExceptions; ++var9) {
         int var5 = this.d_exceptions[var9].getStart();
         int var6 = this.d_exceptions[var9].getEnd();
         int var7 = this.d_exceptions[var9].getHandler();
         var8[var9] = new CodeAttrInfo.ExceptionInfo(this.d_cp, var1[var5], var1[var6], var1[var7], this.d_exceptions[var9].getCatch());
      }

      return var8;
   }

   private void makeExcTable(CodeAttrInfo.ExceptionInfo[] var1, int var2) {
      this.d_numExceptions = var2;
      this.d_exceptions = new CodeAttrInfo.ExceptionInfo[this.d_numExceptions];

      for(int var3 = 0; var3 < this.d_numExceptions; ++var3) {
         int var4 = var1[var3].getStart();
         int var5 = var1[var3].getEnd();
         int var6 = var1[var3].getHandler();
         this.d_exceptions[var3] = new CodeAttrInfo.ExceptionInfo(this.d_cp, this.addTag(var4), this.addTag(var5), this.addTag(var6), var1[var3].getCatch());
      }

   }
}
