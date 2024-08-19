package CA4

import chisel3._
import chisel3.util._

class RegisterFile extends Module {
  val width: Int = 16
  val numRegisters: Int = 6
  val io = IO(new Bundle {
    val write = Input(Bool())
    val addr = Input(UInt(8.W))
    val dataIn = Input(UInt(width.W))
    val dataOut = Output(UInt(width.W))
  })

  // Create a SyncReadMem for register file
  val registerFile = SyncReadMem(numRegisters, UInt(width.W))

  io.dataOut := DontCare

  // Read and write 
  val readData = registerFile.read(io.addr)
  when(io.write) {
    registerFile.write(io.addr, io.dataIn)
  }

  .otherwise {
    io.dataOut := readData
  }
  
  // Testing
  when (registerFile(5) =/= 0.U) {
    for (i <- 0 until numRegisters) {
      printf(p"Regfile($i): ${registerFile(i.U)}\n")
    }
  printf(p"\n")
  }

}
