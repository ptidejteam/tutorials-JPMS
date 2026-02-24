package com.ibm.toad.cfparse.instruction;

import java.util.EventListener;

public interface InstructionEventListener extends EventListener {
   void handleInstruction(InstructionEvent var1);
}
