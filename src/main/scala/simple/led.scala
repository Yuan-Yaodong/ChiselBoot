package simple

import chisel3._
import chisel3.util._

class led(width: Int, interval: Float) extends Module with RequireAsyncReset {
  val io = IO(new Bundle {
    val led = Output(UInt(width.W))
  })

  val TIME_0_1S   = (interval * 1_000_000_000 / 20).toInt // 时钟周期为20ns
  val bitWidthCnt = log2Ceil(TIME_0_1S)             // 计数器位数

  // 计数器生成函数
  def genCounter(n: UInt): UInt = {
    val cntReg = RegInit(0.U(bitWidthCnt.W))
    cntReg := Mux(cntReg === n - 1.U, 0.U, cntReg + 1.U)
    cntReg
  }

  val count       = genCounter(TIME_0_1S.asUInt)
  val LedReg      = RegInit(0.U(width.W))               // 初始化寄存器为0，位宽为width

  // 计数器达到TIME_0_1S时，LedReg置为1
  LedReg := Mux(count === (TIME_0_1S - 1).asUInt, ~LedReg, LedReg)

  io.led := LedReg
}
