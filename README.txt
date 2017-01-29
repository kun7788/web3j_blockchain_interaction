example for using web3j to access the blockchain and communicate with a
contract

* shell script to start a fresh node with custom genesis block and private
  testnetwork
* bin and abi are created from solc-file
* java wrapper class is generated from bin and abi
--> 'gradle generateCode' -> goes into src/main/java/web3_test/MyContract.java
* 'NewClass.deployContract(...)' uses the generated wrapper to store a contract
  on the blockchain and to interact with it

TODO
* dummy code that shows how to use whisper
* dummy code for doing the above with a jason agent
* get rid of '/../genesis.json'



structure:
src/main/resources/MyContract.sol <- the contract as solidity-code
                  /MyContract.bin
                  /MyContract.abi <- both are generated from the .sol

src/main/java/web3j_test/MyContract.java <- the wrapper that represents the contract; bin and abi are required to generate it


Before running the code with 'gradle run', execute the shell-script
'start_chain.sh' and enter 'miner.start()' on the geth command-line it 
brings up. Don't forget to keep your handwritten and generated code in sync!

The chain can be monitored with the mist wallet like this:

'mist --rpc /tmp/smartcontract_chaindata/geth.ipc'

or with ge-ethereum's (geth) console
* ??

