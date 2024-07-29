package simple

import chisel3._
import chisel3.util._

class FetchDecodeIO extends Bundle {
    val instr = Output(UInt(32.W))
    val pc = Output(UInt(32.W))
}

class Fetch extends Module {
    val io = IO(new Bundle {
        val fetchdecodeIO = new FetchDecodeIO()
        val a = Input(UInt(32.W))
        val b = Input(UInt(32.W))
    })
    // fetch阶段的实现省略
    io.fetchdecodeIO.instr := io.a
    io.fetchdecodeIO.pc := io.b
}

class DecodeExecuteIO extends Bundle {
    val aluOp = Output(UInt(5.W))
    val regA = Output(UInt(32.W))
    val regB = Output(UInt(32.W))
}

class Decode extends Module {
    val io = IO(new Bundle {
        val fetchdecodeIO = Flipped(new FetchDecodeIO())
        val decodeexecuteIO = new DecodeExecuteIO()
    })
    // decode阶段的实现省略
    io.decodeexecuteIO.aluOp := io.fetchdecodeIO.instr(5, 0)
    io.decodeexecuteIO.regA := io.fetchdecodeIO.instr
    io.decodeexecuteIO.regB := io.fetchdecodeIO.pc
}

class ExecuteTopIO extends Bundle {
    val result1 = Output(UInt(32.W))
    val result2 = Output(UInt(32.W))
}

class Execute extends Module {
    val io = IO(new Bundle {
        val decodeexecuteIO = Flipped(new DecodeExecuteIO())
        val executetopIO = new ExecuteTopIO()
    })
    // execute阶段的实现省略
    io.executetopIO.result1 := io.decodeexecuteIO.regA
    io.executetopIO.result2 := io.decodeexecuteIO.regB
}

class Top extends Module {
    val io = IO(new Bundle {
        val a = Input(UInt(32.W))
        val b = Input(UInt(32.W))
        val executetopIO = new ExecuteTopIO()
    })

    val fetch = Module(new Fetch())
    val decode = Module(new Decode())
    val execute = Module(new Execute())

    fetch.io.a := io.a
    fetch.io.b := io.b
    fetch.io.fetchdecodeIO <> decode.io.fetchdecodeIO
    decode.io.decodeexecuteIO <> execute.io.decodeexecuteIO
    io.executetopIO <> execute.io.executetopIO
}

