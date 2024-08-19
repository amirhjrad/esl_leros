package CA4

import chisel3._
import chisel3.util._

class Register(width: Int, name: String = "") extends Module {
  val io = IO(new Bundle {
    val enable = Input(Bool())
    val dataIn = Input(UInt(width.W))
    val dataOut = Output(UInt(width.W))
  })

  // Init
  val reg = RegInit(0.U(width.W))

  when(io.enable) {
    reg := io.dataIn
  }

  // Output
  io.dataOut := reg
}
