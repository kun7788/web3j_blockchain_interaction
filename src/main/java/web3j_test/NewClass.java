
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web3j_test;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.ethereum.solidity.compiler.CompilationResult;
import org.ethereum.solidity.compiler.SolidityCompiler;
import static org.ethereum.solidity.compiler.SolidityCompiler.Options.*;

import org.web3j.abi.Transfer;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetCompilers;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.exceptions.TransactionTimeoutException;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;
import org.web3j.protocol.parity.methods.response.PersonalUnlockAccount;
import org.web3j.utils.Convert;

/*dummy class to see if web3j works*/
public class NewClass {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TransactionTimeoutException, IOException, CipherException, CipherException, Exception {

        //attempt to do it with ethereumj
        //StandaloneBlockchain bc = new StandaloneBlockchain().withAutoblock(true);
        //Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        //Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
        //PersonalUnlockAccount personalUnlockAccount = web3.
        //parity.personalUnlockAccount("0xd6c1d261262c310cac8b6637c06d79322f2e60c0", "").sendAsync().get();    
        Parity parity = Parity.build(new HttpService());  // defaults to http://localhost:8545/ 
        
        
        
        
        //Parity.build(web3jService)
        
        //Web3j parity = Web3j.build(new IpcService("/path/to/socketfile"));
        
        Web3ClientVersion web3ClientVersion = parity.web3ClientVersion().sendAsync().get();

        //Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        //Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
        //parity.ethGetBalance(arg0, arg1)
        // put 'miner.start()' on the geth-console or the transaction will fail
        
        //transferEther(parity);
        //compileContract(parity);
        deployContract(parity);

        //web3.getB
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();

        //does the node provide a compiler? (solidity-compiler from ethereumj can also be used)
        Request<?, EthGetCompilers> ethGetCompilers = parity.ethGetCompilers();

        System.out.println("clientversion: " + clientVersion + "; get compilers: " + ethGetCompilers.send().getCompilers());

    }

    public static void transferEther(Parity parity) throws InterruptedException, ExecutionException, TransactionTimeoutException {

        try {

            //read a walletfile and use it as sender(offline signed transaction??)
            Credentials credentials = WalletUtils.loadCredentials("", "keystore/UTC--2016-12-21T16-10-50.355820673Z--e50e1397536628c8eb864d32ae4f63e5fd12aa02");

            //print balance of sender's account
            Request<?, EthGetBalance> ethGetBalance = parity.ethGetBalance("0xe50e1397536628c8eb864d32ae4f63e5fd12aa02", DefaultBlockParameterName.LATEST);
            System.out.println(">>balance before transaction: " + ethGetBalance.send().getBalance().toString());

            //send ether to 0x135...c23
            TransactionReceipt transactionReceipt = Transfer.sendFunds(parity, credentials, "0x13525ddcb1e309cbf81e9afa8658bb28d0156c23", BigDecimal.valueOf(1111), Convert.Unit.WEI);

            //transactionReceipt.get
            //print sender's balance again to impact of the transaction
            System.out.println(">>balance after transaction: " + ethGetBalance.send().getBalance().toString());

        } catch (IOException ex) {

            //Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error!");

        } catch (CipherException ex) {

            //Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error!");
        }
    }
    
    
    public static void deployContract(Parity parity) throws IOException, CipherException, InterruptedException, ExecutionException {
        
        /*code for the class 'MyContract' is generated via the gradle task
        'generateCode' — see 'build.gradle'*/
        
        
        
        Credentials credentials = WalletUtils.loadCredentials("", "keystore/UTC--2016-12-21T16-10-50.355820673Z--e50e1397536628c8eb864d32ae4f63e5fd12aa02");
        //use generated wrapper to deploy the contract to the chain
        Future<MyContract> myContract = MyContract.deploy(parity, credentials, new BigInteger("99999999999"), new BigInteger("999999"), new BigInteger("999999999"));
        //send blocking request
        MyContract contract = myContract.get();

        
        Int256 initialValue = contract.counter().get();
        contract.inc().get();
        contract.inc().get();
        Int256 result  = contract.counter().get();
        System.out.println("current value of counter of two increments of '"+initialValue.getValue()+"': " + result.getValue());
    }
    

    public static void compileContract(Parity parity) throws IOException, InterruptedException, ExecutionException, CipherException, Exception {

        
        /*account from which the contract is sent has to be unlocked, 
        or else the transaction will be refused; does not seem to work with 
        parity. Maybe with web3j?; currently, account has to be unlocked
        via go-ethereum console (geth)*/
        Request<?, PersonalUnlockAccount> personalUnlockAccount = parity.personalUnlockAccount("0xe50e1397536628c8eb864d32ae4f63e5fd12aa02", "");
        System.out.println(">>account unlocked??: " + personalUnlockAccount.send().accountUnlocked());
            //^ should yield 'true' or 'false', not 'null'??
        

        /*read a walletfile and use it as sender — does not seem to work with
        parity. Maybe with web3j?; currently, account has to be unlocked
        via go-ethereum console (geth)*/
        Credentials credentials = WalletUtils.loadCredentials("", "keystore/UTC--2016-12-21T16-10-50.355820673Z--e50e1397536628c8eb864d32ae4f63e5fd12aa02");


        //https://github.com/ethereum/ethereumj/blob/develop/ethereumj-core/src/test/java/org/ethereum/solidity/CompilerTest.java
        //String contractSrc = "contract a { function multiply(uint a) returns(uint d) { return a * 7; } }";
        //String contractSrc = 
        //org.ethereum.solidity.SolidityCompiler doesn't seem to like version pragmas
        String contractSrc = "contract myContract {uint i = 0; function counter() returns(uint c) {return i;}  function inc() {i = ++i;}  function multiply(uint a) returns(uint d) { return a * 7; }}";
    

        //solidity compiler written in java and distributed via ethereumj
        SolidityCompiler.Result res;
        res = SolidityCompiler.compile(contractSrc.getBytes(), true, ABI, BIN, INTERFACE);
        //System.out.println("Out: '" + res.output + "'");
        //System.out.println("Err: '" + res.errors + "'");
        CompilationResult result = CompilationResult.parse(res.output);
        

        CompilationResult.ContractMetadata contract = result.contracts.get("myContract");
        
        System.out.println(">>abi: " + contract.abi);
        //Files.write(Paths.get(System.getProperty("user.dir")).resolve("build/contract.abi"), contract.abi.getBytes());
        System.out.println(">>bin: " + contract.bin);
        //Files.write(Paths.get(System.getProperty("user.dir")).resolve("build/contract.bin"), contract.bin.getBytes());
        
        //raw transaction
        //each account has a nonce: this is a counter to prevent double-spend attacks
        EthGetTransactionCount ethGetTransactionCount = parity.ethGetTransactionCount(
             "0xe50e1397536628c8eb864d32ae4f63e5fd12aa02", DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        
        System.out.println(">>nonce: " + nonce);
        
        //RawTransaction rawTransaction = RawTransaction.createContractTransaction(nonce, new BigInteger("207230238600"), new BigInteger("20723023860000"), new BigInteger("999"), contractSrc);
        
        //transaction to be sent; account has to be unlock or the transaction will fail
        Transaction transaction = Transaction.createContractTransaction(
              "0xe50e1397536628c8eb864d32ae4f63e5fd12aa02",
              nonce,
              BigInteger.valueOf(999999),
              contract.bin
        );
        
        //use web3/parity to hand over the transaction to geth in order to include it in the blockchain
        Request<?, EthSendTransaction> ethSendTransaction = parity.ethSendTransaction(transaction);
        
        /* send the transaction obtaining the transaction hash. The txhash and
        the contract's abi can be used to instantiate a contract object that refers
        to the contract already deployed on the blockchain
        */
        String txhash = ethSendTransaction.send().getTransactionHash();
        
        System.out.println(">>txhash: " + txhash);
            
    }
}
