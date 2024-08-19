package CA4

import chisel3._
import chisel3.util._
import solutions.ALU

class DatapathIO extends Bundle {
  val sel_RegW = Input(Bool())
  val mem_WE = Input(Bool())
  val SrcBSel = Input(UInt(2.W))
  val AReg_out = Output(UInt(16.W))
  val sel_PC = Input(Bool())
  val RegWe = Input(Bool())
  val sel_PCAddr = Input(Bool())
  val AReg_en = Input(Bool())
  val ALUOp= Input(UInt(3.W))
  val Rom_inst_out = Output(UInt(8.W))
}

class Datapath extends Module {
  val io = IO(new DatapathIO())

  val PC = Module(new Register(16, "PC"))
  val RF = Module(new RegisterFile)
  val ALU = Module(new ALU(16))
  val AReg = Module(new Register(16, "AReg"))
  val ARReg = Module(new Register(16, "ARReg"))
  val rom = Module(new Rom("Inst.txt"))
  val memory = Module(new Memory("mem.txt"))

  val SignExt_o = Cat(Fill(8, rom.io.dataOut(7)), rom.io.dataOut(7, 0))

  val PCAdr_Mux = Mux(io.sel_PCAddr, 2.U(16.W), SignExt_o)
  val PCPlus = PCAdr_Mux + PC.io.dataOut
  val PCMUX = Mux(io.sel_PC, PCPlus, ARReg.io.dataOut)

  PC.io.enable := true.B
  PC.io.dataIn := PCMUX

  rom.io.addr := PC.io.dataOut
  io.Rom_inst_out := rom.io.dataOut(15, 8)

  val RegW_out = Mux(io.sel_RegW, AReg.io.dataOut, PCPlus)

  RF.io.write := io.RegWe
  RF.io.addr := rom.io.dataOut(7, 0)
  RF.io.dataIn := RegW_out
  val BSrc_Mux = Mux(io.SrcBSel(1), RF.io.dataOut, Mux(io.SrcBSel(0), SignExt_o, memory.io.outputData))

  ALU.io.B_i := BSrc_Mux
  ALU.io.OpCode_i := io.ALUOp

  val Shl_o = SignExt_o << 2.U
  val ALU_o = ALU.io.Result_o  

  ALU.io.A_i := AReg.io.dataOut
  AReg.io.enable := true.B
  AReg.io.dataIn := ALU_o

  io.AReg_out := AReg.io.dataOut

  ARReg.io.enable := io.AReg_en
  ARReg.io.dataIn := AReg.io.dataOut

  val Mem_Addr = ARReg.io.dataOut + Shl_o

  memory.io.enableWrite := io.mem_WE
  memory.io.memAddress := Mem_Addr
  memory.io.inputData := ARReg.io.dataOut
}
