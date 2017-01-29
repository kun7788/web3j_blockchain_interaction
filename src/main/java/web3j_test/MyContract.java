package web3j_test;

import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.web3j.abi.Contract;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.</p>
 */
public final class MyContract extends Contract {
    private static final String BINARY = "6060604052600260006000505560da8060186000396000f360606040526000357c0100000000000000000000000000000000000000000000000000000000900480631df4f14414604b578063371303c014607557806361bc221a146082576049565b005b605f600480803590602001909190505060c9565b6040518082815260200191505060405180910390f35b6080600480505060b4565b005b608d600480505060a3565b6040518082815260200191505060405180910390f35b6000600060005054905060b1565b90565b6001600060005054016000600050819055505b565b600060078202905060d5565b91905056";

    private MyContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public Future<TransactionReceipt> multiply(Int256 a) {
        Function function = new Function("multiply", Arrays.<Type>asList(a), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> inc() {
        Function function = new Function("inc", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Int256> counter() {
        Function function = new Function("counter", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<MyContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue) {
        return deployAsync(MyContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialValue);
    }

    public static MyContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MyContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }
}
