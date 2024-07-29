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

  // emitVerilog(new gcd.GCD(), Array("--target-dir", "verilog"))
  // emitVerilog(new alu.Alu(), Array("--target-dir", "verilog"))
  //  chisel3.emitVerilog(new gcd.GCD(), firtoolOptions)
  ChiselStage.emitSystemVerilogFile(new gcd.GCD(), args, firtoolOptions)
  ChiselStage.emitSystemVerilogFile(new alu.Alu(), args, firtoolOptions)

}
