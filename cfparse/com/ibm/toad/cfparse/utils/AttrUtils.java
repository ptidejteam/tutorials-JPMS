package com.ibm.toad.cfparse.utils;

import com.ibm.toad.cfparse.attributes.AttrInfo;
import com.ibm.toad.cfparse.attributes.AttrInfoList;

public final class AttrUtils {
   public static void deleteAttrs(AttrInfoList var0, String[] var1) {
      for(int var2 = 0; var2 < var0.length(); ++var2) {
         String var3 = var0.getName(var2);
         if (!var3.equals("Code") && !var3.equals("Exceptions") && !var3.equals("ConstantValue")) {
            var0.remove(var2);
            --var2;
         }
      }

   }

   public static AttrInfo getAttrByName(AttrInfoList var0, String var1) {
      return var0.get(var1);
   }
}
