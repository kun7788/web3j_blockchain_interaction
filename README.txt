example for using web3j to access the blockchain and communicate with a
contract

* shell script to start a fresh node with custom genesis block and private
  testnetwork

todo:

* dummy contract that gets deployed via web3j
* dummy code that shows interaction between java (web3j) and the contract
* dummy code that shows how to use whisper
* dummy code for doing the above with a jason agent


Before running the code with 'gradle run', execute the shell-script
'start_chain.sh' and enter 'miner.start()' on the geth command-line it 
brings up.

The chain can be monitored with the mist wallet like this:

'mist --rpc /tmp/smartcontract_chaindata/geth.ipc'


