package com.positiverse.govhack.saas.client;

import com.positiverse.govhack.saas.client.dto.GiveConsentEvent;
import com.positiverse.govhack.saas.client.dto.WithdrawConsentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;
import org.web3j.protocol.parity.methods.response.PersonalUnlockAccount;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import static org.web3j.abi.Contract.GAS_LIMIT;
import static org.web3j.abi.ManagedTransaction.GAS_PRICE;

/**
 * Created by gha on 30.12.16.
 */
public class EthereumClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final BigInteger ACCOUNT_UNLOCK_DURATION = BigInteger.valueOf(60);

    private static Parity parity;

    private String ethereumNodeUrl;
    private String smartContractAddress;

    public EthereumClient(String ethereumNodeUrl, String smartContractAddress) {
        this.ethereumNodeUrl = ethereumNodeUrl;
        this.smartContractAddress = smartContractAddress;
    }

    public List<GiveConsentEvent> getGiveConsentLog() throws IOException {
        List<GiveConsentEvent> giveConsentEvents = new ArrayList();
        if (!StringUtils.isEmpty(smartContractAddress)) {
            Web3j web3j = Web3j.build(new HttpService(ethereumNodeUrl));

            /** LogGiveConsent
             * { "indexed": true, "name": "_personalDataSubject", "type": "address" },
             * { "indexed": true, "name": "_dataController", "type": "address" },
             * { "indexed": true, "name": "_consentId", "type": "bytes32" },
             * { "indexed": false, "name": "_personalDataSetId", "type": "bytes32" },
             * { "indexed": false, "name": "_personalDataHash", "type": "bytes32" } ],
             **/
            Event event = new Event("LogGiveConsent",
                    Arrays.asList(
                            new TypeReference<Address>() {
                            },
                            new TypeReference<Address>() {
                            },
                            new TypeReference<Bytes32>() {
                            }),
                    Arrays.asList(
                            new TypeReference<Bytes32>() {
                            },
                            new TypeReference<Bytes32>() {
                            }));

            String eventSignature = EventEncoder.encode(event);

            EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, smartContractAddress);
            filter.addSingleTopic(eventSignature);
            EthLog ethLog = web3j.ethGetLogs(filter).send();
            List<EthLog.LogResult> logs = ethLog.getLogs();
            if (!logs.isEmpty()) {
                logger.info("Event size: " + logs.size());
                for (EthLog.LogResult log : ethLog.getResult()) {
                    GiveConsentEvent giveConsentEvent = new GiveConsentEvent();
                    giveConsentEvent.setGiveConsentTxHash(((EthLog.LogObject) log).getTransactionHash().get());
                    logger.info("Event tx hash: " + giveConsentEvent.getGiveConsentTxHash());
                    EthBlock.Block blockByHash = web3j.ethGetBlockByHash(((EthLog.LogObject) log).getBlockHash().get(), true).send().getBlock().get();
                    giveConsentEvent.setBlockTs(blockByHash.getTimestamp());
                    logger.info("Event block timestamp: " + giveConsentEvent.getBlockTs());

                    Address personalDataSubjectAddress = (Address) FunctionReturnDecoder.decodeIndexedValue(((EthLog.LogObject) log).getTopics().get(1), event.getIndexedParameters().get(0));
                    giveConsentEvent.setPersonalDataSubject(String.valueOf(personalDataSubjectAddress));

                    Address dataControllerAddress = (Address) FunctionReturnDecoder.decodeIndexedValue(((EthLog.LogObject) log).getTopics().get(2), event.getIndexedParameters().get(1));
                    giveConsentEvent.setDataController(String.valueOf(dataControllerAddress));

                    Bytes32 consentId = (Bytes32) FunctionReturnDecoder.decodeIndexedValue(((EthLog.LogObject) log).getTopics().get(3), event.getIndexedParameters().get(2));
                    giveConsentEvent.setConsentId(consentId.getValue());

                    List<Type> results = FunctionReturnDecoder.decode(((EthLog.LogObject) log).getData(), event.getNonIndexedParameters());
                    if (!results.isEmpty()) {
                        giveConsentEvent.setPersonalDataSetId((byte[]) results.get(0).getValue());
                        giveConsentEvent.setPersonalDataHash((byte[]) results.get(1).getValue());
                    }

                    giveConsentEvents.add(giveConsentEvent);
                }
            }
        }
        return giveConsentEvents;
    }

    public List<WithdrawConsentEvent> getWithdrawConsentLog() throws IOException {
        List<WithdrawConsentEvent> withdrawConsentEvents = new ArrayList();
        if (!StringUtils.isEmpty(smartContractAddress)) {
            Web3j web3j = Web3j.build(new HttpService(ethereumNodeUrl));

            /** LogWithdrawConsent
             * { "indexed": true, "name": "_personalDataSubject", "type": "address" },
             * { "indexed": true, "name": "_giveConsentTxHash", "type": "bytes32" } ],
             **/
            Event event = new Event("LogWithdrawConsent",
                    Arrays.asList(
                            new TypeReference<Address>() {
                            },
                            new TypeReference<Bytes32>() {
                            }),
                    Arrays.asList());

            String eventSignature = EventEncoder.encode(event);

            EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, smartContractAddress);
            filter.addSingleTopic(eventSignature);
            EthLog ethLog = web3j.ethGetLogs(filter).send();
            List<EthLog.LogResult> logs = ethLog.getLogs();
            if (!logs.isEmpty()) {
                logger.info("Event size: " + logs.size());
                for (EthLog.LogResult log : ethLog.getResult()) {
                    WithdrawConsentEvent withdrawConsentEvent = new WithdrawConsentEvent();
                    withdrawConsentEvent.setWithdrawConsentTxHash(((EthLog.LogObject) log).getTransactionHash().get());
                    logger.info("Event tx hash: " + withdrawConsentEvent.getGiveConsentTxHash());
                    EthBlock.Block blockByHash = web3j.ethGetBlockByHash(((EthLog.LogObject) log).getBlockHash().get(), true).send().getBlock().get();
                    withdrawConsentEvent.setBlockTs(blockByHash.getTimestamp());
                    logger.info("Event block timestamp: " + withdrawConsentEvent.getBlockTs());

                    Address personalDataSubjectAddress = (Address) FunctionReturnDecoder.decodeIndexedValue(((EthLog.LogObject) log).getTopics().get(1), event.getIndexedParameters().get(0));
                    withdrawConsentEvent.setPersonalDataSubject(String.valueOf(personalDataSubjectAddress));

                    Bytes32 giveConsentTxHash = (Bytes32) FunctionReturnDecoder.decodeIndexedValue(((EthLog.LogObject) log).getTopics().get(2), event.getIndexedParameters().get(1));
                    withdrawConsentEvent.setGiveConsentTxHash(giveConsentTxHash.getValue());

                    withdrawConsentEvents.add(withdrawConsentEvent);
                }
            }
        }
        return withdrawConsentEvents;
    }

    public String giveConsent(String coinbaseAccountAddress, String coinbaseAccountPassword,
                                           String dataController, byte[] consentId, byte[] personalDataSetId,
                                           byte[] personalDataHash) throws Exception {
        Objects.requireNonNull(dataController, "dataControler must not be not null");
        requireProperByteArray(consentId, 32, "consentId parameter must be of length 32");
        requireProperByteArray(personalDataSetId, 32, "personalDataSetId parameter must be of length 32");
        requireProperByteArray(personalDataHash, 32, "personalDataHash parameter must be of length 32");
        String transactionHash = null;

        this.parity = Parity.build(new HttpService(ethereumNodeUrl));
        unlockAccount(coinbaseAccountAddress, coinbaseAccountPassword);

        Function function = new Function("giveConsent",
                Arrays.asList(
                        new Address(dataController), // _dataController
                        new Bytes32(consentId), // _consentId
                        new Bytes32(personalDataSetId), // _personalDataSetId
                        new Bytes32(personalDataHash)), // _personalDataHash
                Arrays.asList(new TypeReference<Bool>() {
                }));
        String encodedFunction = FunctionEncoder.encode(function);
        /**
         * It is not possible to return values from transactional functional calls,
         * regardless of the return type of the message signature. However,
         * it is possible to capture values returned by functions using filters.
         */
        Transaction transaction = Transaction.createFunctionCallTransaction(
                coinbaseAccountAddress, //from
                getNonce(coinbaseAccountAddress), //nonce
                GAS_PRICE,
                GAS_LIMIT,
                smartContractAddress, //to
                new BigInteger(String.valueOf(0)), //value
                encodedFunction); //data

        EthSendTransaction transactionResponse = parity.ethSendTransaction(transaction).sendAsync().get();
        transactionHash = transactionResponse.getTransactionHash();
        EthGetTransactionReceipt transactionReceipt =
                parity.ethGetTransactionReceipt(transactionHash).sendAsync().get();
        if (transactionReceipt.hasError()) {
            String message = String.format("BC function giveConsent() returned error: %s", transactionReceipt.getError().getMessage());
            logger.error(message);
            throw new Exception(message);
        }

        return transactionHash;
    }

    public String withdrawConsent(String coinbaseAccountAddress, String coinbaseAccountPassword,
                              byte[] giveConsentTxHash) throws Exception {
        requireProperByteArray(giveConsentTxHash, 32, "giveConsentTxHash parameter must be of length 32");
        String transactionHash = null;

        this.parity = Parity.build(new HttpService(ethereumNodeUrl));
        unlockAccount(coinbaseAccountAddress, coinbaseAccountPassword);

        Function function = new Function("withdrawConsent",
                Arrays.asList(
                        new Bytes32(giveConsentTxHash)), // _giveConsentTxHash
                Arrays.asList(new TypeReference<Bool>() {
                }));
        String encodedFunction = FunctionEncoder.encode(function);
        /**
         * It is not possible to return values from transactional functional calls,
         * regardless of the return type of the message signature. However,
         * it is possible to capture values returned by functions using filters.
         */
        Transaction transaction = Transaction.createFunctionCallTransaction(
                coinbaseAccountAddress, //from
                getNonce(coinbaseAccountAddress), //nonce
                GAS_PRICE,
                GAS_LIMIT,
                smartContractAddress, //to
                new BigInteger(String.valueOf(0)), //value
                encodedFunction); //data

        EthSendTransaction transactionResponse = parity.ethSendTransaction(transaction).sendAsync().get();
        transactionHash = transactionResponse.getTransactionHash();
        EthGetTransactionReceipt transactionReceipt =
                parity.ethGetTransactionReceipt(transactionHash).sendAsync().get();
        if (transactionReceipt.hasError()) {
            String message = String.format("BC function withdrawConsent() returned error: %s", transactionReceipt.getError().getMessage());
            logger.error(message);
            throw new Exception(message);
        }

        return transactionHash;
    }

    private boolean unlockAccount(String coinbaseAccountAddress, String coinbaseAccountPassword) throws Exception {
        PersonalUnlockAccount personalUnlockAccount =
                parity.personalUnlockAccount(coinbaseAccountAddress, coinbaseAccountPassword, ACCOUNT_UNLOCK_DURATION).sendAsync().get();

        return personalUnlockAccount.accountUnlocked();
    }

    private BigInteger getNonce(String address) throws Exception {
        EthGetTransactionCount ethGetTransactionCount = parity.ethGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();

        return ethGetTransactionCount.getTransactionCount();
    }

    private void requireProperByteArray(byte[] array, int length, String message) {
        if (array == null) {
            throw new NullPointerException(message);
        }
        if (array.length != length) {
            throw new IllegalArgumentException(message);
        }
    }

}
