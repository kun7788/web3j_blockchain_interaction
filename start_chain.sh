#!/bin/bash

SOLC="/home/simon/quelltexte/solidity/build/solc/solc"
DATADIR="/tmp/smartcontract_chaindata"
echo ">> removing datadir..."
rm -vr $DATADIR
mkdir -v $DATADIR
echo ">> first mining might take some time since DAG has to be generated..."

#geth --datadir $DATADIR --keystore keystore
echo ">> copy keysore..."
cp -rv keystore $DATADIR

echo ">> creating genesis block..."
geth  --networkid 9898 --identity "test1" --datadir $DATADIR init src/main/resources/genesis.json

#echo ">> making dag..."
#geth  --networkid 9898 --identity "test1" --datadir $DATADIR makedag 0 $DATADIR

echo ">> starting client... --nodiscover --maxpeers 0 ..."
#using '--dev': genesisblock from 'genesis.json' is overwritten and the
# accounts from './keystore' have balance 0, but DAG generation is fast
#not using '--dev': accounts from './keystore' have the balance as defined
# in 'genesis.json' but DAG generation takes very much time
geth --rpc --dev --maxpeers 0 --nodiscover --datadir $DATADIR  --networkid 9898  --identity "test1" --solc $SOLC  console


