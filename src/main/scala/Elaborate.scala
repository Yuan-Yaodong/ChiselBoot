import chisel3._

object Elaborate extends App {
  val firtoolOptions = Array("--lowering-options=" + List(
    // make yosys happy
    // see https://github.com/llvm/circt/blob/main/docs/VerilogGeneration.md
    "disallowLocalVariables",
    "disallowPackedArrays",
    "locationInfoStyle=wrapInAtSquareBracket"
  ).reduce(_ + "," + _))
  emitVerilog(new gcd.GCD(), Array("--target-dir", "verilog"))
  emitVerilog(new alu.Alu(), Array("--target-dir", "verilog"))
}
