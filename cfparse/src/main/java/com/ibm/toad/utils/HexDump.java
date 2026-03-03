package com.ibm.toad.utils;

import java.io.File;
import java.io.FileInputStream;

public class HexDump {
   public static void main(String[] var0) {
      try {
         FileInputStream var1 = new FileInputStream(new File(var0[0]));
         byte[] var2 = new byte[var1.available()];
         var1.read(var2);
         System.out.println(toBytes(var2));
         var1.close();
      } catch (Exception var3) {
         System.out.println("Could not open " + var0[0]);
      }
   }

   private static char AlNum(int var0) {
      return var0 >= 32 && var0 <= 126 ? (char)var0 : '.';
   }

   public static String toBytes(byte[] var0, int var1, int var2) {
      int var3 = 0;
      StringBuffer var4 = new StringBuffer(var2 - var1 + " bytes. \n00: ");
      StringBuffer var5 = new StringBuffer();

      for(int var6 = var1; var6 < var2; ++var6) {
         String var7 = Integer.toHexString(var0[var6] & 255) + " ";
         if ((var0[var6] & 255) < 16) {
            var7 = "0" + var7;
         }

         var4.append(var7);
         var5.append(AlNum(var0[var6] & 255));
         if ((var6 - var1 + 1) % 16 == 0) {
            ++var3;
            var4.append("\t " + var5.toString() + "\n");
            if (var3 < 16) {
               var4.append(Integer.toHexString(var3) + "0: ");
            } else {
               var4.append(Integer.toHexString(var3) + ": ");
            }

            var5.setLength(0);
         }
      }

      var4.append("\t " + var5.toString() + "\n\n");
      return var4.toString();
   }

   public static String toBytes(byte[] var0) {
      return toBytes(var0, 0, var0.length);
   }
}
