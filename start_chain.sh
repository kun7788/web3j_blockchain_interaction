#!/bin/bash

DATADIR="/tmp/smartcontract_chaindata"
echo ">> removing datadir..."
rm -r $DATADIR

echo ">> creating genesis block..."
geth --dev --datadir $DATADIR init src/main/resources/genesis.json $DATADIR
echo ">> starting client..."
geth --rpc --dev --datadir $DATADIR --keystore keystore --networkid 9898 --identity "bla"  console


