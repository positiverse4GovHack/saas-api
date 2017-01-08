package com.positiverse.govhack.saas.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Jacek Szczepański on 2016-12-15.
 */
@RestController
@RequestMapping("/ether")
public class BlockchainController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${ethereum.node.url}")
    private String ethereumNodeUrl ;

    @RequestMapping(method = RequestMethod.GET, value = "/clientVersion", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<String> getClientVersion() throws IOException {
        Web3j web3 = Web3j.build(new HttpService(ethereumNodeUrl));

        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();

        return new ResponseEntity<>(clientVersion, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tx/{transactionHash}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Transaction> getTransactionInfo(@PathVariable String transactionHash) throws IOException {
        Web3j web3 = Web3j.build(new HttpService(ethereumNodeUrl));

        EthTransaction txByHash = web3.ethGetTransactionByHash(transactionHash).send();

        return new ResponseEntity<>(txByHash.getTransaction().get(), HttpStatus.OK);
    }

}

