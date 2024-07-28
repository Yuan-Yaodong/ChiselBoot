package alu

import chisel3._
import chisel3.util._

/**
 * ALU Operation codes:
    00000: NOP
    00001: ADD
    00010: SUB
    00011: MUL
    00100: MOD
    00101: AND
    00110: OR
    00111: XOR
  * 
  */


class Alu extends Module {
    val io = IO(new Bundle {
        val alu_op   = Input(UInt(5.W))
        val alu_in1  = Input(SInt(16.W))
        val alu_in2  = Input(SInt(16.W))
        val alu_out  = Output(SInt())
    })

    io.alu_out := 0.S

    switch(io.alu_op) {
        is("b00000".U) { }
        is("b00001".U) { io.alu_out := io.alu_in1 +& io.alu_in2 }
        is("b00010".U) { io.alu_out := io.alu_in1 -& io.alu_in2 }
        is("b00011".U) { io.alu_out := io.alu_in1 * io.alu_in2 }
        is("b00100".U) { io.alu_out := io.alu_in1 % io.alu_in2 }
        is("b00101".U) { io.alu_out := io.alu_in1 & io.alu_in2 }
        is("b00110".U) { io.alu_out := io.alu_in1 | io.alu_in2 }
        is("b00111".U) { io.alu_out := io.alu_in1 ^ io.alu_in2 }
    }

}