import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class AluTester extends AnyFlatSpec with ChiselScalatestTester {
  "Alu" should "perform all operations correctly" in {
    test(new alu.Alu) { dut =>
      val operations = Seq(
        ("b00000".U, (a: SInt, b: SInt) => 0.S), //NOP
        ("b00001".U, (a: SInt, b: SInt) => a +& b), //ADD
        ("b00010".U, (a: SInt, b: SInt) => a -& b), //SUB
        ("b00011".U, (a: SInt, b: SInt) => a * b), //MUL
        ("b00100".U, (a: SInt, b: SInt) => if (b != 0.S) a % b else 0.S), //MOD
        ("b00101".U, (a: SInt, b: SInt) => a & b), //AND
        ("b00110".U, (a: SInt, b: SInt) => a | b), //OR
        ("b00111".U, (a: SInt, b: SInt) => a ^ b)  //XOR
      )

      // Generate a range of values to test
      val testValues = for {
        x <- -10 to 20
        y <- -5 to 35
      } yield (x.S, y.S)

      for ((op, func) <- operations) {
        for ((in1, in2) <- testValues) {
          dut.io.alu_op.poke(op)
          dut.io.alu_in1.poke(in1)
          dut.io.alu_in2.poke(in2)

          // Handle division by zero case for MOD operation
          if (op === "b00100".U && in2 === 0.S) {
            dut.io.alu_out.expect(0.S)  // Modulo by zero is undefined; assuming it outputs zero
          } else {
            dut.io.alu_out.expect(func(in1, in2))
          }
        }
      }
    }
  }
}