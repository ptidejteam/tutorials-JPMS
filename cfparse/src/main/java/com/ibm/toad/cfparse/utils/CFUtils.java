package com.ibm.toad.cfparse.utils;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.MethodInfoList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.BitSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class CFUtils {
   public static final int BYTES = 1;
   public static final int FILE = 2;
   public static final int CLASSFILE = 3;
   public static final int STRING = 4;

   public static String canonicalize(String var0) {
      String var1 = var0.replace('\\', '/').replace('.', '/');
      if (var1.endsWith("/class")) {
         var1 = var1.substring(0, var1.length() - 6) + ".class";
      } else {
         var1 = var1 + ".class";
      }

      return var1;
   }

   public static String[] getClassNamesFromZip(String var0, String var1) {
      String var2 = var1.replace('\\', '/').replace('.', '/');
      if (var2.endsWith("*")) {
         var2 = var2.substring(0, var2.length() - 1);
      }

      if (!var2.endsWith("/")) {
         var2 = var2 + "/";
      }

      Vector var3 = new Vector();

      try {
         ZipFile var4 = new ZipFile(var0);
         Enumeration var5 = var4.entries();

         while(var5.hasMoreElements()) {
            ZipEntry var6 = (ZipEntry)var5.nextElement();
            if (var6.getName().startsWith(var2) && var6.getName().endsWith(".class") && !var6.isDirectory()) {
               String var7 = var6.getName().replace('/', '.').substring(0, var6.getName().length() - 6);
               var3.addElement(var7);
            }
         }
      } catch (Exception var8) {
         return null;
      }

      String[] var9 = new String[var3.size()];
      var3.copyInto(var9);
      return var9;
   }

   public static Vector getFileDeps(String var0, String var1, boolean var2) {
      Hashtable var3 = new Hashtable();
      Hashtable var4 = new Hashtable();
      Vector var5 = new Vector();
      Vector var6 = new Vector();
      var1 = canonicalize(var1);
      ClassFile var7;
      if (var3.contains(var1)) {
         var7 = (ClassFile)var3.get(var1);
      } else {
         try {
            var7 = new ClassFile(var0 + var1);
         } catch (Exception var12) {
            System.out.println("oops ");
            var12.printStackTrace();
            return null;
         }

         var3.put(var1, var7);
         var4.put(var1, var7.getSourceFilename());
      }

      ConstantPool var8 = var7.getCP();

      for(int var9 = 0; var9 < var8.length(); ++var9) {
         if (var8.getType(var9) == 7) {
            String var10 = canonicalize(var8.getAsString(var9));
            if (!var10.equals(var1) && !var10.startsWith("java/") && !var10.startsWith("com/sun/")) {
               if (var2) {
                  if (!var5.contains(var10)) {
                     var5.addElement(var10);
                     var6.addElement(var10);
                  }
               } else if (var4.contains(var10)) {
                  var6.addElement(var4.get(var10));
               } else {
                  try {
                     var7 = new ClassFile(var0 + var10);
                     var3.put(var10, var7);
                     var4.put(var10, var7.getSourceFilename());
                     if (!var6.contains(var7.getSourceFilename())) {
                        var6.addElement(var7.getSourceFilename());
                     }
                  } catch (Exception var11) {
                     System.out.println("Failed to read " + var10);
                  }
               }
            }
         }
      }

      return var6;
   }

   public static int compress(ClassFile var0, String var1) {
      BitSet var2 = var0.uses();
      int[] var3 = new int[var0.getCP().length()];
      int var4 = 0;
      int var5 = 0;

      for(int var6 = 0; var6 < var3.length; ++var6) {
         if (var2.get(var6)) {
            var3[var6] = var4++;
         } else {
            ++var5;
            var3[var6] = -1;
         }
      }

      var0.sort(var3);
      if (var1 != null) {
         try {
            var0.write(var1);
         } catch (Exception var7) {
            return -1;
         }
      }

      return var5;
   }

   public static Object forName(String var0, int var1, boolean var2) {
      return forName((String[])null, var0, var1, var2);
   }

   public static Object forName(String[] var0, String var1, int var2, boolean var3) {
      String var4 = canonicalize(var1);
      if (var0 == null) {
         var0 = getClassPath(var3);
      }

      for(int var5 = 0; var5 < var0.length; ++var5) {
         String var6 = "";
         if (var0[var5].endsWith("/")) {
            var6 = var0[var5] + var4;
         } else {
            if (var0[var5].endsWith(".jar") || var0[var5].endsWith(".zip")) {
               Object var14 = forName(var0[var5], var1, var2);
               if (var14 != null) {
                  return var14;
               }
               continue;
            }

            var6 = var0[var5] + "/" + var4;
         }

         File var7 = new File(var6);
         if (var7.isFile()) {
            switch(var2) {
            case 1:
               try {
                  FileInputStream var15 = new FileInputStream(var7);
                  byte[] var9 = new byte[var15.available()];
                  boolean var10 = false;

                  int var16;
                  for(int var11 = 0; var11 < var9.length; var11 += var16) {
                     var16 = var15.read(var9, var11, var9.length - var11);
                     if (var16 == -1) {
                        break;
                     }
                  }

                  return var9;
               } catch (Exception var12) {
                  return null;
               }
            case 2:
               return var7;
            case 3:
               try {
                  ClassFile var8 = new ClassFile(var6);
                  return var8;
               } catch (Exception var13) {
                  var13.printStackTrace();
                  break;
               }
            case 4:
               return var6;
            }
         }
      }

      return null;
   }

   public static Object forName(String var0, String var1, int var2) {
      String var3 = canonicalize(var1);

      try {
         ZipFile var4 = new ZipFile(var0);
         ZipEntry var5;
         if ((var5 = var4.getEntry(var3)) != null) {
            InputStream var6 = var4.getInputStream(var5);
            if (var2 == 3) {
               ClassFile var11 = new ClassFile(var6);
               return var11;
            }

            if (var2 != 1) {
               if (var2 == 4) {
                  return var0;
               }

               return null;
            }

            byte[] var7 = new byte[(int)var5.getSize()];
            boolean var8 = false;

            int var12;
            for(int var9 = 0; var9 < var7.length; var9 += var12) {
               var12 = var6.read(var7, var9, var7.length - var9);
               if (var12 == -1) {
                  break;
               }
            }

            return var7;
         }
      } catch (Exception var10) {
      }

      return null;
   }

   public static String[] getClassNames(String var0, boolean var1) {
      String var2 = var0.replace('\\', '/').replace('.', '/');
      if (var2.endsWith("*")) {
         var2 = var2.substring(0, var2.length() - 1);
      }

      if (!var2.endsWith("/")) {
         var2 = var2 + "/";
      }

      String[] var3 = getClassPath(var1);
      Vector var4 = new Vector();

      for(int var5 = 0; var5 < var3.length; ++var5) {
         String var6 = "";
         if (var3[var5].endsWith("/")) {
            var6 = var3[var5] + var2;
         } else {
            if (var3[var5].endsWith(".jar") || var3[var5].endsWith(".zip")) {
               String[] var12 = getClassNamesFromZip(var3[var5], var2);
               if (var12 != null) {
                  for(int var13 = 0; var13 < var12.length; ++var13) {
                     var4.addElement(var12[var13]);
                  }
               }
               continue;
            }

            var6 = var3[var5] + "/" + var2;
         }

         File var7 = new File(var6);
         if (var7.isDirectory()) {
            String[] var8 = var7.list(new FilenameFilter() {
               public boolean accept(File var1, String var2) {
                  return var2.endsWith(".class");
               }
            });
            if (var8 != null) {
               for(int var9 = 0; var9 < var8.length; ++var9) {
                  String var10 = (var2 + var8[var9].substring(0, var8[var9].length() - 6)).replace('/', '.');
                  var4.addElement(var10);
               }
            }
         }
      }

      String[] var11 = new String[var4.size()];
      var4.copyInto(var11);
      return var11;
   }

   public static String[] getLocalMethodNames(ClassFile var0) {
      MethodInfoList var1 = var0.getMethods();
      String[] var2 = new String[var1.length()];

      for(int var3 = 0; var3 < var1.length(); ++var3) {
         var2[var3] = var1.getMethodName(var3);
      }

      return var2;
   }

   public static String[] getUsedFieldNames(ClassFile var0, String[] var1) {
      ConstantPool var2 = var0.getCP();
      Vector var3 = new Vector();

      int var4;
      for(var4 = 0; var4 < var2.length(); ++var4) {
         if (var2.getType(var4) == 9) {
            var3.addElement(var2.getAsJava(var4));
         }
      }

      int var5;
      if (var1 != null) {
         for(var4 = 0; var4 < var1.length; ++var4) {
            var5 = var1[var4].indexOf("*");
            String var6 = var5 == -1 ? var1[var4] : var1[var4].substring(0, var5);
            Vector var7 = (Vector)var3.clone();
            var3.setSize(0);

            for(int var8 = 0; var8 < var7.size(); ++var8) {
               String var9 = (String)var7.elementAt(var8);
               String var10 = var9.substring(var9.lastIndexOf(" ") + 1, var9.length());
               if (!var10.startsWith(var6)) {
                  var3.addElement(var9);
               }
            }
         }
      }

      String[] var11 = new String[var3.size()];

      for(var5 = 0; var5 < var3.size(); ++var5) {
         var11[var5] = (String)var3.elementAt(var5);
      }

      return var11;
   }

   public static String[] getUsedMethodNames(ClassFile var0, String[] var1) {
      ConstantPool var2 = var0.getCP();
      Vector var3 = new Vector();

      int var4;
      for(var4 = 0; var4 < var2.length(); ++var4) {
         if (var2.getType(var4) == 10) {
            var3.addElement(var2.getAsJava(var4));
         }
      }

      int var5;
      if (var1 != null) {
         for(var4 = 0; var4 < var1.length; ++var4) {
            var5 = var1[var4].indexOf("*");
            String var6 = var5 == -1 ? var1[var4] : var1[var4].substring(0, var5);
            Vector var7 = (Vector)var3.clone();
            var3.setSize(0);

            for(int var8 = 0; var8 < var7.size(); ++var8) {
               String var9 = (String)var7.elementAt(var8);
               String var10 = var9.substring(var9.lastIndexOf(" ") + 1, var9.length());
               if (!var10.startsWith(var6)) {
                  var3.addElement(var9);
               }
            }
         }
      }

      String[] var11 = new String[var3.size()];

      for(var5 = 0; var5 < var3.size(); ++var5) {
         var11[var5] = (String)var3.elementAt(var5);
      }

      return var11;
   }

   public static String[] getClassPath(boolean var0) {
      StringTokenizer var1 = new StringTokenizer(System.getProperty("java.class.path"), File.pathSeparator);
      String[] var2 = new String[var1.countTokens()];
      int var3 = 0;

      while(true) {
         String var4;
         do {
            if (!var1.hasMoreTokens()) {
               String[] var5 = new String[var3];
               System.arraycopy(var2, 0, var5, 0, var3);
               return var5;
            }

            var4 = var1.nextToken();
         } while(!var0 && (var4.endsWith(".jar") || var4.endsWith(".zip")));

         var2[var3++] = var4;
      }
   }
}
