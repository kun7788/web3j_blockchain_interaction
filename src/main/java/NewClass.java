
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package web3j_test;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ethereum.solidity.compiler.CompilationResult;
import org.ethereum.solidity.compiler.SolidityCompiler;
import static org.ethereum.solidity.compiler.SolidityCompiler.Options.*;

import org.ethereum.util.blockchain.StandaloneBlockchain;
import org.web3j.abi.Transfer;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.exceptions.TransactionTimeoutException;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;
import org.web3j.protocol.parity.methods.response.PersonalUnlockAccount;
import org.web3j.utils.Convert;

/*dummy class to see if web3j works*/
public class NewClass {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TransactionTimeoutException, IOException {

        
        //attempt to do it with ethereumj
        //StandaloneBlockchain bc = new StandaloneBlockchain().withAutoblock(true);
        //Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        //Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
        //PersonalUnlockAccount personalUnlockAccount = web3.
        //parity.personalUnlockAccount("0xd6c1d261262c310cac8b6637c06d79322f2e60c0", "").sendAsync().get();    
        
        Parity parity = Parity.build(new HttpService());  // defaults to http://localhost:8545/
        Web3ClientVersion web3ClientVersion = parity.web3ClientVersion().sendAsync().get();

        
        // put 'miner.start()' on the geth-console or the transaction will fail
        transferEther(parity);

        compileContract();
        
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();

        System.out.println("clientversion: " + clientVersion);

    }

    public static void transferEther(Parity parity) throws InterruptedException, ExecutionException, TransactionTimeoutException {

        try {

            //read a walletfile and use it as sender
            Credentials credentials = WalletUtils.loadCredentials("", "keystore/UTC--2016-12-21T16-10-50.355820673Z--e50e1397536628c8eb864d32ae4f63e5fd12aa02");

            //send one ether to 0x135...c23
            TransactionReceipt transactionReceipt = Transfer.sendFunds(parity, credentials, "0x13525ddcb1e309cbf81e9afa8658bb28d0156c23", BigDecimal.valueOf(1), Convert.Unit.ETHER);

            System.out.println("check!");

            /*if (personalUnlockAccount.accountUnlocked()) {
            System.out.println("check!");
            // send a transaction, or use parity.personalSignAndSendTransaction() to do it all in one
            }*/
        } catch (IOException ex) {
            
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error!");
            
        } catch (CipherException ex) {
            
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error!");
        }
    }

    public static void compileContract() throws IOException {

        //https://github.com/ethereum/ethereumj/blob/develop/ethereumj-core/src/test/java/org/ethereum/solidity/CompilerTest.java
        
        String METADATA = "";
        
        String contract
                = "" //"pragma solidity ^0.4.7;\n"
                + "\n"
                + "contract a {\n"
                + "\n"
                + "        mapping(address => string) private mailbox;\n"
                + "\n"
                + "        event Mailed(address from, string message);\n"
                + "        event Read(address from, string message);\n"
                + "\n"
                + "}";

        SolidityCompiler.Result res = SolidityCompiler.compile(
                contract.getBytes(), true, ABI, BIN, INTERFACE);
        System.out.println("Out: '" + res.output + "'");
        System.out.println("Err: '" + res.errors + "'");
        CompilationResult result = CompilationResult.parse(res.output);
        
        
        
        if (result.contracts.get("a") != null) {
            System.out.println(result.contracts.get("a").bin);
        } else {
            //Assert.fail();
            System.out.println("error");
        }
        

    }
}
