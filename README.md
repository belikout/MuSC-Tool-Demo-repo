# MuSC Tool Demo
A Tool Demo for Ethereum Smart Contract  Mutation Test.
## Development Environment and Configuration
```Java(1.8) ```and ```Node.js``` Environment are necessary to run this project.  
### Configuration
First, if you haven't install [Truffle](https://github.com/trufflesuite/truffle), you need to install it in your device.
```
$ npm install -g truffle
```
Next, run ```$ npm install``` under [node](https://github.com/belikout/MuSC-Tool-Demo-repo/tree/master/node) folder to get dependencies installed.  
### Start up
Follow the steps below to run this tool:  
1. Start nodejs side service.  
Run [GenASTServer.js](https://github.com/belikout/MuSC-Tool-Demo-repo/blob/master/node/GenASTServer.js) under [node](https://github.com/belikout/MuSC-Tool-Demo-repo/tree/master/node) folder
to start node side service.  
(This service generates AST from Solidity file, mainly using tool [solidity-parser-antlr](https://github.com/federicobond/solidity-parser-antlr))
2. Start demo website on localhost.  
This tool's main body ```mutestdemo``` is written by Spring Boot, running  [MutestdemoApplication.java](https://github.com/belikout/MuSC-Tool-Demo-repo/blob/master/mutestdemo/src/main/java/cn/edu/nju/mutestdemo/MutestdemoApplication.java)
to start the local server for tool. Then access tool website via ```localhost:8080/musc```  
## Tool Usage Process
You can get the tool usage introduction in the video available at https://youtu.be/rHRX8NY4X_E
## Mutation Operators Explanation
### Traditional Operators
| Operator | Description |
| ------ | ------ |
| AOR | Arithmetic Operator Replacement |
| AOI | Arithmetic Operator Insertion |
| ROR | Relational Operator Replacement |
| COR | Conditional Operator Replacement |
| LOR | Logical Operator Replacement |
| ASR | Assignment Short-cut Operator Replacement |
| SDL | Statement Deletion |
| RVR | Return Value Replacement |
| CSC | Condition Statement Change |

### ESC Aimed Operators
|Type| Operator | Description |
| ------ | ------ | ------ |
|Keyword| FSC | Function State Keyword Chang |
|Keyword| FVC | Function Visibility Keyword Chang |
|Keyword| DLR | Data Location Keyword Replacement |
|Keyword| VTR | Variable Type Keyword Replacement |
|Keyword| PKD | Payable Keyword Deletion |
|Keyword| DKD | Delete Keyword Deletion |
|Global Variable and Function| GVC | Global Variable Change |
|Global Variable and Function| MFR | Mathematical  Functions  Replacement |
|Global Variable and Function| AVR | Address  Variable  Replacement |
|Variable Unit| EUR | Ether Unit Replacement |
|Variable Unit| TUR | Time Unit Replacement |
|Error Handling| RSD | Require Statement Deletion |
|Error Handling| RSC | Require Statement Change |
|Error Handling| ASD | Assert Statement Deletion |
|Error Handling| ASC | Assert Statement Change |
