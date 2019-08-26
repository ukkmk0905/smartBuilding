package com.team.smart.blockchain.config;

import android.util.Log;

import com.team.smart.blockchain.FishContracts_sol_FishCore;
import com.team.smart.blockchain.Wallet;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Web3jAPI {

    private static Web3j web3j;

    private static FishContracts_sol_FishCore fishCore;

    public static Credentials getCredentials() {
        return credentials;
    }

    private static Credentials credentials;

    private static final Web3jAPI INSTANCE = new Web3jAPI();

    public static Web3jAPI getInstance(){
        if (web3j == null){
            initialize();
        }
        return INSTANCE;
    }

    private Web3jAPI(){}

    public String version(){
        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();

            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            return clientVersion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    private static void connect(){
        web3j = Web3jUtil.buildHttpClient();
    }

    static void initCredentials(){
        Wallet wallet = Wallet.getInstance();
        credentials = wallet.getCredentials();
        fishCore = FishContracts_sol_FishCore.load(Configuration.contractAddress,web3j,credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

    }

    static void initialize(){
        connect();
        initCredentials();
    }

    /**
     *
     * @apiNote return the detail info of a fish on sale
     * @param index BigInteger:         array index of fish list
     * @return List<Object>
     *
     *     index    value               type            comment                     Unit
     *     0        seller              String          the address of seller
     *     1        price               BigInteger      on sale price               wei (1 ether = 10^18 wei)
     *     2        startedAt           BigInteger      time of putting it on sale  timestamp
     *
     *     example:
     *     [0x80753a86047d27b51d542ab453c24f5ad1401f79, 100000, 1528446231]
     */

    public List<Object> getOnSaleInfoOfAFish(BigInteger index){
        try {
            Tuple3<String, BigInteger, BigInteger> fish = fishCore.tokenIdToSale(index).sendAsync().get();
            List<Object> details = new ArrayList<>();
            details.add(fish.getValue1());
            details.add(fish.getValue2());
            details.add(fish.getValue3());
            return details;
        }
        catch (Exception e){
            System.err.println(" error in getOnSaleInfoOfAFish ");
            e.printStackTrace();
            return null;
        }
    }



    /**
     *
     * @apiNote return the detail info of a fish on breeding sale (waiting to mate)
     * @param index BigInteger:         array index of fish list
     * @return List<Object>
     *
     *     index    value               type            comment                     Unit
     *     0        seller              String          the address of seller
     *     1        price               BigInteger      on breeding sale price      wei (1 ether = 10^18 wei)
     *     2        startedAt           BigInteger      time of putting it on sale  timestamp
     *
     *     example:
     *     [0x80753a86047d27b51d542ab453c24f5ad1401f79, 100000, 1528446231]
     */

    public List<Object> getOnBreedingSaleInfoOfAFish(BigInteger index){
        try {
            Tuple3<String, BigInteger, BigInteger> fish = fishCore.tokenIdToBreedingSale(index).sendAsync().get();
            List<Object> details = new ArrayList<>();
            details.add(fish.getValue1());
            details.add(fish.getValue2());
            details.add(fish.getValue3());
            return details;
        }
        catch (Exception e){
            System.err.println(" error in getOnBreedingSaleInfoOfAFish ");
            e.printStackTrace();
            return null;
        }
    }



    /**
     *
     * @apiNote get owner of a fish
     * @param index BigInteger:     array index of fishes list
     * @return  String
     */

    public String getOwnerOfAFish(BigInteger index){

        try {

            String owner = fishCore.ownerOf(index).sendAsync().get();
            return owner;
        }
        catch (Exception e){
            System.err.println(" error in getOwnerOfAFish ");
            e.printStackTrace();
            return null;
        }
    }







    /***
     *
     *
     *
     *
     *              S       E       T
     *
     *
     *
     *
     *
     */


    /**
     *
     *
     *
     * @param index fish id
     * @param price sale price          wei
     * @return
     */


    public boolean putAFishOnSale(BigInteger index, BigInteger price){


        TransactionReceipt txReceipt = null;
        try {
            txReceipt = fishCore.createSale(index,price).sendAsync().get();
            // get tx hash and tx fees
            String txHash = txReceipt.getTransactionHash();
            BigInteger txFees = txReceipt
                    .getCumulativeGasUsed()
                    .multiply(Configuration.GAS_PRICE);

            System.out.println("hash: " + txHash);
            System.out.println("fees: " + Web3jUtil.weiToEther(txFees));
            return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     *
     *
     * @param index fish id
     * @return
     */

    public boolean cancelSale(BigInteger index){

        TransactionReceipt txReceipt = null;
        try {
            txReceipt = fishCore.cancelSale(index).sendAsync().get();
            // get tx hash and tx fees
            String txHash = txReceipt.getTransactionHash();
            BigInteger txFees = txReceipt
                    .getCumulativeGasUsed()
                    .multiply(Configuration.GAS_PRICE);

            System.out.println("hash: " + txHash);
            System.out.println("fees: " + Web3jUtil.weiToEther(txFees));
            return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     *
     *
     * @param index fish id
     * @param money payment $      wei
     * @return
     */

    public boolean buyAFish(BigInteger index, BigInteger money){
        TransactionReceipt txReceipt = null;
        try {
            txReceipt = fishCore.buyOwnership(index,money).sendAsync().get();
            // get tx hash and tx fees
            String txHash = txReceipt.getTransactionHash();
            BigInteger txFees = txReceipt
                    .getCumulativeGasUsed()
                    .multiply(Configuration.GAS_PRICE);

            System.out.println("hash: " + txHash);
            System.out.println("fees: " + Web3jUtil.weiToEther(txFees));
            return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     *
     *
     * @param index fish id
     * @param price on breeding sale price      wei
     * @return
     */


    public boolean putAFishOnBreedingSale(BigInteger index, BigInteger price){


        TransactionReceipt txReceipt = null;
        try {
            txReceipt = fishCore.createBreedingSale(index,price).sendAsync().get();
            // get tx hash and tx fees
            String txHash = txReceipt.getTransactionHash();
            BigInteger txFees = txReceipt
                    .getCumulativeGasUsed()
                    .multiply(Configuration.GAS_PRICE);

            System.out.println("hash: " + txHash);
            System.out.println("fees: " + Web3jUtil.weiToEther(txFees));
            return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     *
     *
     * @param index fish id
     * @return
     */


    public boolean cancelBreedingSale(BigInteger index){

        TransactionReceipt txReceipt = null;
        try {
            txReceipt = fishCore.cancelBreedingSale(index).sendAsync().get();
            // get tx hash and tx fees
            String txHash = txReceipt.getTransactionHash();
            BigInteger txFees = txReceipt
                    .getCumulativeGasUsed()
                    .multiply(Configuration.GAS_PRICE);

            System.out.println("hash: " + txHash);
            System.out.println("fees: " + Web3jUtil.weiToEther(txFees));
            return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     *
     *
     * @param index fish id on breeding sale
     * @param mateId fish id you owned
     * @param money payment $                   wei
     * @return
     */


    public boolean buyMatingWithAFish(BigInteger index, BigInteger mateId, BigInteger money){
        TransactionReceipt txReceipt = null;
        try {
            txReceipt = fishCore.buyMating(index,mateId,money).sendAsync().get();
            // get tx hash and tx fees
            String txHash = txReceipt.getTransactionHash();
            BigInteger txFees = txReceipt
                    .getCumulativeGasUsed()
                    .multiply(Configuration.GAS_PRICE);

            System.out.println("hash: " + txHash);
            System.out.println("fees: " + Web3jUtil.weiToEther(txFees));
            return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }



    /***
     *
     *
     *
     *
     *
     *              Wallet    Functions  except import & create & export
     *
     *
     *
     *
     *
     */







    /**
     *
     * @param to       String     receiver address
     * @param ETHValue Double     ETH!!!  not wei!!!
     */

    public boolean sendETH(String to, Double ETHValue) {

        try {
            TransactionReceipt transactionReceipt = Transfer.sendFunds(
                    web3j, credentials, to,
                    BigDecimal.valueOf(ETHValue), Convert.Unit.ETHER)
                    .send();
            // get tx hash and tx fees
            String txHash = transactionReceipt.getTransactionHash();
            BigInteger txFees = transactionReceipt
                    .getCumulativeGasUsed()
                    .multiply(Configuration.GAS_PRICE);

            System.out.println("hash: " + txHash);
            System.out.println("fees: " + Web3jUtil.weiToEther(txFees));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @return your wallet's eth balance
     */

    public BigDecimal getETHBalance(){

        try {
            Log.d("잔액조회 ~~~", "~~~~~~~~~~~~~~~~~~~~~");
            return Web3jUtil.getBalanceEther(web3j,credentials.getAddress());
        } catch (InterruptedException e) {
            Log.d("잔액조회 ~~~", "~~~~~~~~~~catch intere~");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.d("잔액조회 ~~~", "~~~~~~~catch execut~~");
            e.printStackTrace();
        }
        return new BigDecimal("0");

    }



    public static String timeStamp2Date(String seconds) {
        //String format = "yyyy-MM-dd HH:mm:ss";
        String format = "yyyy-MM-dd";
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }


    /**
     *
     * @return your wallet's address
     */
    public String getAddress(){
        return credentials.getAddress();
    }

    /**
     *
     * @return your wallet's private key
     */

    public String exportPrivateKey(){
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        return Numeric.toHexStringWithPrefix(ecKeyPair.getPrivateKey());
    }



    /***
     *
     *
     *              Internet
     *
     *
     */

    private static String rare;


    private static String pageContent;

    public static void getTxs(){

        String url = "https://ropsten.etherscan.io/address/"+"0x80753a86047d27b51d542ab453c24f5ad1401f79";

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(100, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(100, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(100, TimeUnit.SECONDS)//设置写入超时时间
                .build();

        //创建一个请求
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            pageContent = response.body().string();
        }catch (Exception e){
            System.err.println("\n*1*\n");
            e.printStackTrace();
        }
        //System.out.println(pageContent);
        Pattern p = Pattern.compile("<tr>.*?</tr>");
        Matcher m = p.matcher(pageContent);
        //System.out.println("\nusing regex:\n");
        int len = 0;

        List<String> hashes = new ArrayList<>();

        while (m.find()) {
            String match = m.group();
            //System.out.println("\nh: "+match);

            Pattern p1 = Pattern.compile("<a class='address-tag' href='.*</a>");
            Matcher m1 = p1.matcher(match);
            //System.out.println("\nusing regex for "+match);

            while (m1.find()) {

                String match1 = m1.group();
                String hash = match1.split(">")[1].split("<")[0];
                //System.out.println("e: "+addr);
                len++;
                hashes.add(hash);
            }
        }

        System.out.println("len: " + len);

        for (int i = 0; i < len; i++) {
            String tx_url = "https://ropsten.etherscan.io/tx/" + hashes.get(i);
            //创建一个请求
            System.out.println("\n-------------\ntransaction: #"+ i +" hash:"+ hashes.get(i));
            Request request_tx = new Request.Builder().url(tx_url).build();
            Response response_tx = null;
            String pageTx = "";
            try {
                response_tx = okHttpClient.newCall(request_tx).execute();
                pageTx = response_tx.body().string();
            } catch (IOException e) {
                System.err.println("\n*2*\n");
                e.printStackTrace();
            }

            //System.out.println(pageTx);
            //Pattern p2 = Pattern.compile("<span.*?</span>");
            //Matcher m2 = p2.matcher(pageTx);

            //  TxReceipt Status:           regex       #3

            Pattern stauts_pattern = Pattern.compile("<font color=.*</font>");
            Matcher status_matcher = stauts_pattern.matcher(pageTx);

            status_matcher.find();
            status_matcher.find();
            status_matcher.find();
            String status_result = status_matcher.group();
            String status = status_result.split(">")[1].split("<")[0];
            System.out.println("status: " + status);


            //  Input Data:

            Pattern function_pattern = Pattern.compile("id='inputdata'>[\\s\\S]*</textarea>");
            Matcher function_matcher = function_pattern.matcher(pageTx);
            function_matcher.find();
            String function_result = function_matcher.group();
            String func = function_result.split(">")[1].split("<")[0];
            System.out.println("func: " + func);

            //  The amount of ETH to be transferred to the recipient with the transaction:

            Pattern eth_amount_pattern = Pattern.compile("<span title=\"The amount of ETH to be transferred to the recipient with the transaction\">[\\s\\S]*</span>");
            Matcher eth_amount_matcher = eth_amount_pattern.matcher(pageTx);
            eth_amount_matcher.find();
            String eth_amount_result = eth_amount_matcher.group();
//                String eth_amount_temps = eth_amount_result.split(">")[1];
//                String eth_amount_int = eth_amount_temps.split("<")[0];
//                String eth_amount_reminder = eth_amount_result.split("<")[3].split(">")[0];
//                String eth_amount = eth_amount_int + "." + eth_amount_reminder;

            String eth_amount = eth_amount_result.replaceAll("\\<.*?>", "");
            eth_amount = eth_amount.substring(1,eth_amount.indexOf(')')+1);
            System.out.println("eth_amount: " + eth_amount);

            //  Actual Tx Cost/Fee:

            Pattern tx_cost_pattern = Pattern.compile("<span title=\"Gas .*>[\\s\\S]*</span>");
            Matcher tx_cost_matcher = tx_cost_pattern.matcher(pageTx);
            tx_cost_matcher.find();
            String tx_cost_result = tx_cost_matcher.group();
//                String tx_cost_temps = tx_cost_result.split(">")[1];
//                String tx_cost_int = tx_cost_temps.split("<")[0];
//                String tx_cost_reminder = tx_cost_result.split("<")[3].split(">")[0];
            String tx_cost = tx_cost_result.replaceAll("\\<.*?>", "");
            tx_cost = tx_cost.substring(1,tx_cost.indexOf(')')+1);
            //tx_cost_int+ "." +tx_cost_reminder;
            System.out.println("tx_cost: "+tx_cost);

            //  TimeStamp:

            Pattern timestamp_pattern = Pattern.compile("<span id=\"clock\"></span>[\\s\\S]*</span>");
            Matcher timestamp_matcher = timestamp_pattern.matcher(pageTx);
            timestamp_matcher.find();
            String timestamp_result = timestamp_matcher.group();
            String timestamp = timestamp_result.split(">")[2].split("<")[0];
            System.out.println("timestamp " + timestamp);

            //  TxHash:

            Pattern TxHash_pattern = Pattern.compile("style=\"word-wrap: break-word;\"[\\s\\S]*</div>");
            Matcher TxHash_matcher = TxHash_pattern.matcher(pageTx);
            TxHash_matcher.find();
            String TxHash_result = TxHash_matcher.group();
            String TxHash = TxHash_result.split(">")[1].split("<")[0];
            System.out.println("TxHash " + TxHash);

            //  From:

            Pattern from_pattern = Pattern.compile("<div class=\"col-sm-9 cbs\".*word-wrap.*</a>");
            Matcher from_matcher = from_pattern.matcher(pageTx);
            from_matcher.find();
            String from_result = from_matcher.group();
            String from = from_result.split(">")[2].split("<")[0];
            System.out.println("from " + from);

            //  To:

            Pattern to_pattern = Pattern.compile("<div class=\"col-sm-9 cbs\">.* class='wordwrap'.*?</a>");
            Matcher to_matcher = to_pattern.matcher(pageTx);
            to_matcher.find();
            String to_result = to_matcher.group();
            String to = to_result.replaceAll("\\<.*?>", "").replaceAll("\\[","");
            System.out.println("to " + to);

        }

    }
}
