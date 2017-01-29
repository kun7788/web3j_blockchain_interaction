//pragma solidity ^0.4.0; 
contract MyContract {
    int i = 2;
    function counter() constant returns(int c) {return i;}
    function inc() {i = i+1;}
    function multiply(int a) returns(int d) { return a * 7; }
}
    
