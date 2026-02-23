package com.ibm.toad.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;

public final class D {
   private D() {
   }

   public static void assert(boolean var0) {
      if (!var0) {
         throw "\nAssertion failure".new AssertionException("\nAssertion failure");
      }
   }

   public static void assert(boolean var0, String var1) {
      if (!var0) {
         throw "\nAssertion failure:" + var1.new AssertionException("\nAssertion failure:" + var1);
      }
   }

   public static void post(boolean var0) {
      if (!var0) {
         throw new D.PostconditionException();
      }
   }

   public static void post(boolean var0, String var1) {
      if (!var0) {
         throw new D.PostconditionException("\n" + var1);
      }
   }

   public static String getStackTrace(Throwable var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      var0.printStackTrace(new PrintStream(var1));
      return var1.toString();
   }

   public static void abort() {
      assert(false);
   }

   public static void abort(String var0) {
      assert(false, var0);
   }

   public static void abort(int var0) {
      abort("" + var0);
   }

   public static boolean isHomogenous(Enumeration var0, String var1) {
      Class var2 = null;

      try {
         var2 = Class.forName(var1);
      } catch (Exception var5) {
         abort("Unable to get Class object for class: " + var1);
      }

      while(var0.hasMoreElements()) {
         Object var3 = var0.nextElement();
         Class var4 = var3.getClass();
         if (var2 != var4) {
            return false;
         }
      }

      return true;
   }

   public static boolean isHomogenous(Vector var0, String var1) {
      return isHomogenous(var0.elements(), var1);
   }

   public static void pre(boolean var0) {
      if (!var0) {
         throw new D.PreconditionException();
      }
   }

   public static void pre(boolean var0, String var1) {
      if (!var0) {
         throw new D.PreconditionException("\n" + var1);
      }
   }

   public class AssertionException extends RuntimeException {
      public AssertionException() {
      }

      public AssertionException(String var1) {
         super(var1);
      }
   }

   public static class PostconditionException extends RuntimeException {
      public PostconditionException() {
      }

      public PostconditionException(String var1) {
         super(var1);
      }
   }

   public static class PreconditionException extends RuntimeException {
      public PreconditionException() {
      }

      public PreconditionException(String var1) {
         super(var1);
      }
   }
}
