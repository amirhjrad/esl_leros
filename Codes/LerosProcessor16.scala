package CA4
  
import chisel3._
import chisel3.util._
import chisel3.iotesters.{PeekPokeTester, Driver}

class LerosProcessor16 extends Module
{
  val io = IO(new Bundle {})
    val datapath = Module(new Datapath)
    val controller = Module(new Controller)

    controller.io <> datapath.io
}

class LerosTester(c: LerosProcessor16) extends PeekPokeTester(c) {
    step(21)
}
object LerosProcessor16 {
  def main(args: Array[String]): Unit = {
    if (!Driver(() => new LerosProcessor16())(c => new LerosTester(c))) System.exit(1)
  }
}
