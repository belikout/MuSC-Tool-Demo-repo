var thrift = require("thrift");
var GenAST= require('./GenAST.js');
var parser=require("solidity-parser-antlr")

//RPC接口的实现
var genASTImpl = {
  genAST: function(sol) {
    console.log(JSON.stringify(parser.parse(sol)))
    console.log("receive: "+JSON.stringify(parser.parse(sol)));
    return JSON.stringify(parser.parse(sol));
  }
}
//启动服务器，默认只支持TBufferedTransport和TBinaryProtocol
var server = thrift.createServer(GenAST, genASTImpl);
server.listen(9898);
