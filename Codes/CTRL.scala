package CA4
  
import chisel3._
import chisel3.util._

class Controller extends Module {
  val io = IO(Flipped(new DatapathIO))

  io.sel_PC := true.B
  io.sel_RegW := true.B
  io.sel_PCAddr := true.B
  io.SrcBSel := true.B
  io.mem_WE := false.B
  io.AReg_en := false.B
  io.ALUOp := 0.U 
  io.RegWe := false.B

  val inst = io.Rom_inst_out
  val opcode = inst(7, 2)
  val imm = inst(0)

  switch(inst) {
    is("b00001000".U) { // Add
      io.ALUOp := Mux(imm === 0.U, 1.U, 1.U)
      io.SrcBSel := Mux(imm === 0.U, 2.U, 1.U)
    }
    is("b00001001".U) { // Addi
      io.ALUOp := Mux(imm === 0.U, 1.U, 1.U)
      io.SrcBSel := Mux(imm === 0.U, 1.U, 0.U) // Adjust SrcBSel for Addi
    }
    is("b00001100".U) { // Sub
      io.ALUOp := Mux(imm === 0.U, 2.U, 2.U)
      io.SrcBSel := Mux(imm === 0.U, 2.U, 1.U)
    }
    is("b00001101".U) { // Subi
      io.ALUOp := Mux(imm === 0.U, 2.U, 2.U)
      io.SrcBSel := Mux(imm === 0.U, 1.U, 0.U) // Adjust SrcBSel for Subi
    }
    is("b00011000".U) { // Shr
      io.ALUOp := 7.U
      io.SrcBSel := 1.U
    }
    is("b00100010".U) { // And
      io.ALUOp := 3.U
      io.SrcBSel := 2.U
    }
    is("b00100011".U) { // Andi
      io.ALUOp := 3.U
      io.SrcBSel := 1.U
    }
    is("b00100100".U) { // Or
      io.ALUOp := 4.U
      io.SrcBSel := 2.U
    }
    is("b00100101".U) { // Ori
      io.ALUOp := 4.U
      io.SrcBSel := 1.U
    }
    is("b00100110".U) { // Xor
      io.ALUOp := 5.U
      io.SrcBSel := 2.U
    }
    is("b00100111".U) { // Xori
      io.ALUOp := 5.U
      io.SrcBSel := 1.U
    }
    is("b00100000".U) { // Load
      io.ALUOp := 6.U
      io.SrcBSel := 2.U
    }
    is("b00100001".U) { // Loadi
      io.ALUOp := 6.U
      io.SrcBSel := 1.U
    }
    is("b00110000".U) { // Store
      io.ALUOp := 0.U
      io.SrcBSel := true.B
      io.RegWe := true.B
      io.sel_RegW := true.B
    }
    is("b01000000".U) { // Jal
      io.ALUOp := 0.U
      io.SrcBSel := true.B
      io.sel_PC := false.B
      io.sel_RegW := false.B
    }
    is("b01010000".U) { // LdAddr
      io.ALUOp := 0.U
      io.AReg_en := true.B
      io.SrcBSel := 0.U
    }
    is("b01100000".U) { // Loadind
      io.ALUOp := 6.U
      io.SrcBSel := 0.U
    }
    is("b01110000".U) { // Storeind
      io.ALUOp := 0.U
      io.SrcBSel := true.B
      io.mem_WE := true.B
    }
    is("b10000000".U) { // Br
      io.ALUOp := 0.U
      io.SrcBSel := true.B
      io.sel_PC := true.B
      io.sel_PCAddr := false.B
    }
    is("b10010000".U) { // BrZ
      io.ALUOp := 0.U
      io.SrcBSel := true.B
      io.sel_PC := true.B
      io.sel_PCAddr := false.B
    }
  }
}
