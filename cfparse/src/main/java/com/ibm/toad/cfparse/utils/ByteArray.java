package com.ibm.toad.cfparse.utils;

public class ByteArray {
   public static void writeShort(byte[] var0, int var1, int var2) {
      var0[var1] = (byte)(var2 >>> 8 & 255);
      var0[var1 + 1] = (byte)(var2 & 255);
   }

   public static int getShortAtOffset(byte[] var0, int var1) {
      int var2 = var0[var1] & 255;
      int var3 = var0[var1 + 1] & 255;
      int var4 = var2 << 8 | var3;
      return var4;
   }

   public static int getSignedShortAtOffset(byte[] var0, int var1) {
      byte var2 = var0[var1];
      int var3 = var0[var1 + 1] & 255;
      int var4 = var2 << 8 | var3;
      return var4;
   }

   public static int getIntAtOffset(byte[] var0, int var1) {
      int var2 = var0[var1] & 255;
      int var3 = var0[var1 + 1] & 255;
      int var4 = var0[var1 + 2] & 255;
      int var5 = var0[var1 + 3] & 255;
      int var6 = var2 << 24 | var3 << 16 | var4 << 8 | var5;
      return var6;
   }

   public static int getByteAtOffset(byte[] var0, int var1) {
      return var0[var1] & 255;
   }

   public static void writeInt(byte[] var0, int var1, int var2) {
      var0[var1] = (byte)(var2 >>> 24 & 255);
      var0[var1 + 1] = (byte)(var2 >>> 16 & 255);
      var0[var1 + 2] = (byte)(var2 >>> 8 & 255);
      var0[var1 + 3] = (byte)(var2 & 255);
   }
}
