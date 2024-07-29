import chisel3._
import _root_.circt.stage.ChiselStage

object Elaborate extends App {
  val firtoolOptions = Array(
    "-disable-all-randomization",
    "-strip-debug-info",
    "--lowering-options=" + List(
      // make yosys happy
      // see https://github.com/llvm/circt/blob/main/docs/VerilogGeneration.md
      "disallowLocalVariables",
      "disallowPackedArrays",
      "locationInfoStyle=wrapInAtSquareBracket"
    ).reduce(_ + "," + _)
  )

  // ChiselStage.emitSystemVerilogFile(new gcd.GCD(), args, firtoolOptions)
  // ChiselStage.emitSystemVerilogFile(new alu.Alu(), args, firtoolOptions)
  // ChiselStage.emitSystemVerilogFile(new simple.led(2,0.2f), args, firtoolOptions)
  ChiselStage.emitSystemVerilogFile(new simple.Top(), args, firtoolOptions)

}
