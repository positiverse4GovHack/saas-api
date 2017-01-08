package com.positiverse.govhack.saas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import rx.Subscription;

import java.io.IOException;

/**
 * Created by gha on 28.12.16.
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${ethereum.node.url}")
    private String ethereumNodeUrl ;

    /**
     * TODO: Ideas for improvement
     * This listener can be used to read logs from Ethereum as blocks arrive and cache them locally in Saas.
     * It would shorten the time needed to assemble a list of consents and lower burden on Ethereum node.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        try {
            Web3j web3j = Web3j.build(new HttpService(ethereumNodeUrl));
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            logger.info(String.format("web3ClientVersion: %s", web3ClientVersion.getWeb3ClientVersion()));

            Subscription subscription = web3j.blockObservable(false).subscribe((EthBlock ethBlock) -> {
                EthBlock.Block block = ethBlock.getBlock().get();
                logger.info(String.format("New block number %s, transactions: %s", block.getNumber(),
                        block.getTransactions().size()));
            });
        } catch (IOException e) {
            logger.error("", e);
        }
    }

} // class
