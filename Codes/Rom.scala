package CA4

import chisel3._

class Rom(memoryFile: String = "") extends Module {
  val width: Int = 16
  val io = IO(new Bundle {
    val addr = Input(UInt(10.W))
    val dataOut = Output(UInt(width.W))
  })

  val internal_addr = io.addr >> 1

  val rom = Wire(Vec(19, UInt(width.W)))

  // Instructions stored in ROM

  rom(0) := "b00100000_00000000".U
  rom(1) := "b01010000_0000000".U
  rom(2) := "b01100000_00000000".U
  rom(3) := "b00110000_00000000".U
  rom(4) := "b01100000_00000001".U
  rom(5) := "b00110000_00000001".U
  rom(6) := "b01100000_00000010".U
  rom(7) := "b00110000_00000010".U
  rom(8) := "b01100000_00000011".U
  rom(9) := "b00110000_00000011".U
  rom(10) := "b01100000_00000100".U
  rom(11) := "b00110000_00000100".U
  rom(12) := "b00100000_00000000".U
  rom(13) := "b00001000_00000001".U
  rom(14) := "b00001000_00000010".U
  rom(15) := "b00001000_00000011".U
  rom(16) := "b00001000_00000100".U
  rom(17) := "b00001000_00000101".U
  rom(18) := "b00110000_00000101".U   

  io.dataOut := rom(internal_addr)
}
