package com.ibm.toad.cfparse.instruction;

import java.util.Enumeration;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;

public class MutableCodeIterator implements Enumeration, ListIterator {
   private int d_curInst;
   private int d_lastInst;
   private Vector d_listeners;
   private MutableCodeSegment d_mc;
   private Vector d_instructions;

   public void addInstructionListener(InstructionEventListener var1) {
      this.d_listeners.add(var1);
   }

   public void step(int var1) {
      int var2 = this.d_curInst + var1;
      if (var2 >= 0 && var2 < this.d_instructions.size()) {
         this.d_curInst = var2;
      } else {
         throw new NoSuchElementException("No instruction number " + var2);
      }
   }

   public Object nextElement() throws NoSuchElementException {
      return this.next();
   }

   public void set(Object var1) throws UnsupportedOperationException {
      this.d_lastInst = -1;
      if (!(var1 instanceof BaseInstruction)) {
         throw new ClassCastException("argument is not a BaseInstruction");
      } else {
         this.fireInstructionEvent("set", (BaseInstruction)var1);
         this.d_instructions.setElementAt(var1, this.d_curInst);
      }
   }

   private void fireInstructionEvent(String var1, BaseInstruction var2) {
      InstructionEvent var3 = new InstructionEvent(var1, var2);

      for(int var4 = 0; var4 < this.d_listeners.size(); ++var4) {
         ((InstructionEventListener)this.d_listeners.get(var4)).handleInstruction(var3);
      }

   }

   public MutableCodeIterator(ConstantPool var1, CodeAttrInfo var2, boolean var3) {
      this(new MutableCodeSegment(var1, var2, var3));
   }

   public void add(Object var1) throws UnsupportedOperationException {
      this.d_lastInst = -1;
      if (!(var1 instanceof BaseInstruction)) {
         throw new ClassCastException("argument is not a BaseInstruction");
      } else {
         this.fireInstructionEvent("add", (BaseInstruction)var1);
         this.d_instructions.insertElementAt(var1, this.d_curInst);
         ++this.d_curInst;
      }
   }

   public MutableCodeIterator(MutableCodeSegment var1) {
      this.d_mc = var1;
      this.d_instructions = this.d_mc.getInstructions();
      this.d_curInst = 0;
      this.d_lastInst = -1;
      this.d_listeners = new Vector();
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
      return this.d_curInst != this.d_instructions.size();
   }

   public Object next() throws NoSuchElementException {
      if (this.d_curInst == this.d_instructions.size()) {
         throw new NoSuchElementException("No instruction number " + this.d_curInst);
      } else {
         BaseInstruction var1 = (BaseInstruction)this.d_instructions.elementAt(this.d_curInst);
         this.fireInstructionEvent("next", var1);
         this.d_lastInst = this.d_curInst++;
         return var1;
      }
   }

   public boolean hasPrevious() {
      return this.d_curInst != 0;
   }

   public Object previous() throws NoSuchElementException {
      if (this.d_curInst == 0) {
         throw new NoSuchElementException("No instruction number -1");
      } else {
         BaseInstruction var1 = (BaseInstruction)this.d_instructions.elementAt(this.d_curInst);
         this.fireInstructionEvent("previous", var1);
         this.d_lastInst = this.d_curInst;
         this.d_curInst += -1;
         return var1;
      }
   }

   public int length() {
      return this.d_instructions.size();
   }

   public void remove() throws UnsupportedOperationException, IllegalStateException {
      if (this.d_lastInst == -1) {
         throw new IllegalStateException("Cannot call remove now");
      } else {
         this.fireInstructionEvent("remove", (BaseInstruction)this.d_instructions.elementAt(this.d_lastInst));
         this.d_instructions.removeElementAt(this.d_lastInst);
         this.d_lastInst = -1;
      }
   }
}
