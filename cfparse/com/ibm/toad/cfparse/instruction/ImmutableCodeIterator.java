package com.ibm.toad.cfparse.instruction;

import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import java.util.Enumeration;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Vector;

public class ImmutableCodeIterator implements Enumeration, ListIterator {
   private int d_curInst;
   private Vector d_listeners;
   private ImmutableCodeSegment d_mc;

   public void addInstructionListener(InstructionEventListener var1) {
      this.d_listeners.add(var1);
   }

   public void step(int var1) {
      int var2 = this.d_curInst + var1;
      if (var2 >= 0 && var2 < this.d_mc.getNumInstructions()) {
         this.d_curInst = var2;
      } else {
         throw new NoSuchElementException("No instruction number " + var2);
      }
   }

   public Object nextElement() throws NoSuchElementException {
      return this.next();
   }

   public void set(Object var1) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("");
   }

   private void fireInstructionEvent(String var1, BaseInstruction var2) {
      InstructionEvent var3 = new InstructionEvent(var1, var2);

      for(int var4 = 0; var4 < this.d_listeners.size(); ++var4) {
         ((InstructionEventListener)this.d_listeners.get(var4)).handleInstruction(var3);
      }

   }

   public ImmutableCodeIterator(CodeAttrInfo var1) {
      this(var1.getCode());
      this.d_curInst = 0;
   }

   public void add(Object var1) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("");
   }

   public ImmutableCodeIterator(byte[] var1) {
      this(new ImmutableCodeSegment(var1));
   }

   public ImmutableCodeIterator(ImmutableCodeSegment var1) {
      this.d_mc = var1;
      this.d_curInst = 0;
   }

   public void rewind() {
      this.d_curInst = 0;
   }

   public boolean hasMoreElements() {
      return this.hasNext();
   }

   public int nextIndex() {
      return this.d_curInst + 1;
   }

   public int previousIndex() {
      return this.d_curInst - 1;
   }

   public boolean hasNext() {
      return this.d_curInst != this.d_mc.getNumInstructions() - 1;
   }

   public Object next() throws NoSuchElementException {
      if (this.d_curInst == this.d_mc.getNumInstructions() - 1) {
         throw new NoSuchElementException("No instruction number " + (this.d_curInst + 1));
      } else {
         ++this.d_curInst;
         ImmutableCodeIterator.RawByteInstr var1 = new ImmutableCodeIterator.RawByteInstr(this, this.d_mc.getInstruction(this.d_curInst));
         this.fireInstructionEvent("next", var1);
         return var1;
      }
   }

   public boolean hasPrevious() {
      return this.d_curInst != 0;
   }

   public Object previous() throws NoSuchElementException {
      if (this.d_curInst == 0) {
         throw new NoSuchElementException("No instruction number " + (this.d_curInst + 1));
      } else {
         this.d_curInst += -1;
         ImmutableCodeIterator.RawByteInstr var1 = new ImmutableCodeIterator.RawByteInstr(this, this.d_mc.getInstruction(this.d_curInst));
         this.fireInstructionEvent("previous", var1);
         return var1;
      }
   }

   public int length() {
      return this.d_mc.getNumInstructions();
   }

   public void remove() throws UnsupportedOperationException {
      throw new UnsupportedOperationException("");
   }

   class RawByteInstr implements BaseInstruction {
      private byte[] d_code;
      // $FF: synthetic field
      final ImmutableCodeIterator this$0;

      RawByteInstr(ImmutableCodeIterator var1, byte[] var2) {
         (this.this$0 = var1).getClass();
         this.d_code = var2;
      }

      public int getOpCode() {
         return this.d_code[0] & 255;
      }

      public String getTag() {
         return null;
      }

      public void setTag(String var1) {
      }

      public int getLength(int var1) {
         return this.d_code.length;
      }

      public byte[] getCode(int[] var1, int var2) {
         return this.d_code;
      }
   }
}
