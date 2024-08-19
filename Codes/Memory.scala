package CA4

import chisel3._
import chisel3.util._

class Memory(memFile: String = "") extends Module {
  val dataWidth: Int = 16
  val io = IO(new Bundle {
    val enableWrite = Input(Bool())
    val memAddress = Input(UInt(10.W))
    val inputData = Input(UInt(dataWidth.W))
    val outputData = Output(UInt(dataWidth.W))
  })

  val alignedAddr = io.memAddress >> 2

  // 5 16bit word
  val memory = Mem(5, UInt(dataWidth.W))

  // Init state
  val initialized = RegInit(false.B)
  val defaultValues = VecInit(Seq(1.U, 2.U, 3.U, 4.U, 5.U))
  
  // Init
  when(!initialized) {
    for (i <- defaultValues.indices) {
      memory(i) := defaultValues(i)
    }
    initialized := true.B
  }

  // Write
  when(io.enableWrite) {
    memory(alignedAddr) := io.inputData
  }

  // Read
  io.outputData := memory(alignedAddr)
}
