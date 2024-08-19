package solutions
  
import chisel3._
import chisel3.util._

class ALU(val w: Int) extends Module {
  val io = IO(new Bundle {
    val A_i = Input(UInt(w.W))
    val B_i = Input(UInt(w.W))
    val OpCode_i = Input(UInt(3.W))
    val Result_o = Output(UInt(w.W))
  })

    io.Result_o := 0.U;

    switch(io.OpCode_i) {
        is(0.U) {
            io.Result_o := io.A_i
        }
        is(1.U) {
            io.Result_o := io.A_i + io.B_i
        }
        is(2.U) {
            io.Result_o := io.A_i - io.B_i
        }
        is(3.U) {
            io.Result_o := io.A_i & io.B_i
        }
        is(4.U) {
            io.Result_o := io.A_i | io.B_i
        }
        is(5.U) {
            io.Result_o := io.A_i ^ io.B_i
        }
        is(6.U) {
            io.Result_o := io.B_i
        }
        is(7.U) {
            io.Result_o := io.A_i >> 1
        }
    }

}