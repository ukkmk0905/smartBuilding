package com.team.smart.blockchain;

import com.team.smart.vo.WalletVO;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wallet {

    private static final Wallet INSTANCE = new Wallet();

    private static Credentials credentials;

    public Credentials getCredentials() {
        return credentials;
    }

    private static List<WalletVO> lists =new ArrayList<>();

    private String currentName = "";
    public String getCurrentName() {
        return currentName;
    }
    private List<String> names = new ArrayList<>();
    private List<String> passwords = new ArrayList<>();
    private List<String> filepaths = new ArrayList<>();
    private List<String> addresses = new ArrayList<>();
    public List getNames(){
        return names;
    }
    public List getPasswords(){
        return passwords;
    }
    public List getFilepaths(){
        return filepaths;
    }
    public List getAddresses(){
        return addresses;
    }

    public List getLists(String filepath) throws Exception {
        File file1 = new File("walletBean.adt");
        File f = new File(filepath+"/"+file1);
        if (f.exists()){
            List<WalletVO> temp_lists =DeserializeWallet(filepath);
            lists.clear();
            lists.addAll(temp_lists);

            for (WalletVO walletBean:lists){
                String tn = walletBean.getName();
                if (!names.contains(tn)) {

                    String tp = walletBean.getPassword();
                    String tf = walletBean.getPath();
                    String ad = walletBean.getAddress();
                    names.add(tn);
                    passwords.add(tp);
                    filepaths.add(tf);
                    addresses.add(ad);
                }
            }
        }
        return lists;
    }

    /**
     * @param name      current name of wallet
     * @param password  password user once set
     * @param filename  including filepath+filename!!!!
     */
    public void useWallet(String name, String password, String filename){

    }

    public void importWallet(String name, String password, String privateKey, String filepath){

        String pk = "";
        WalletVO walletBean = new WalletVO();
        if (privateKey.equals("")){
            pk = "65e080f727d9ddca08bff41f57283fc7d5e032bb5af8de963dade6a6caaa1ec4";
        }else {
            pk = privateKey;
        }
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(pk,16));
        String pri = Numeric.toHexStringWithPrefix(ecKeyPair.getPrivateKey());
        credentials = Credentials.create(ecKeyPair);

        String address = credentials.getAddress();
        String pub = Numeric.toHexStringWithPrefix(ecKeyPair.getPublicKey());

        try {
            String filename = WalletUtils.generateWalletFile(password,ecKeyPair,new File(filepath),false);
            System.out.println("f: "+filepath+"/"+filename);
            String file=filepath+"/"+filename;

            //File file1 = new File("walletBean.adt");
            File f = new File(filepath+"/walletBean.adt");
            if (f.exists()){
                lists=DeserializeWallet(filepath);
            }

            //namelists.add(name);
            walletBean.setName(name);
            walletBean.setPassword(password);
            walletBean.setAddress(address);
            walletBean.setPath(file);
            lists.add(walletBean);
            SerializeWallet(filepath);
            //dataSave.setDataList("namelists",namelists);
            //dataSave.setDataList("lists",lists);

            //System.out.println("namelists: "+namelists);
            System.out.println("lists: "+lists);
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("original private key: "+pk);
        System.out.println("private key: "+pri+"\npublic key: "+pub+"\naddress: "+address);

    }


    public void createWallet(String name, String password, String filepath){
        try {
            WalletVO walletBean = new WalletVO();
            String filename = WalletUtils.generateLightNewWalletFile(password,new File(filepath));
            System.out.println("f: "+filepath+"/"+filename);
            String file=filepath+"/"+filename;
            credentials = WalletUtils.loadCredentials(password,filepath+"/"+filename);
            String address = credentials.getAddress();
            ECKeyPair ecKeyPair = credentials.getEcKeyPair();
            String pri = Numeric.toHexStringWithPrefix(ecKeyPair.getPrivateKey());
            String pub = Numeric.toHexStringWithPrefix(ecKeyPair.getPublicKey());
            //File file1 = new File("walletBean.adt");
            File f = new File(filepath+"/walletBean.adt");
            if (f.exists()){
                lists=DeserializeWallet(filepath);
            }

            walletBean.setName(name);
            walletBean.setPassword(password);
            walletBean.setAddress(address);
            walletBean.setPath(file);
            lists.add(walletBean);
            SerializeWallet(filepath);
            //dataSave.setDataList("namelists",namelists);
            //dataSave.setDataList("lists",lists);


            System.out.println("lists: "+lists);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void SerializeWallet(String filepath) throws IOException {
        //File file = new File("walletBean.adt");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(filepath+"/walletBean.adt")));
        WalletVO[] walletBean = new WalletVO[lists.size()];
        lists.toArray(walletBean);
        out.writeObject(walletBean);
        System.out.println("성공");
        out.close();
    }

    private static List<WalletVO> DeserializeWallet(String filepath) throws Exception, IOException {
        //File file = new File("walletBean.adt");
        ObjectInputStream out = new ObjectInputStream(new FileInputStream(new File(filepath+"/walletBean.adt")));
        //执行反序列化读取
        WalletVO[] walletBean = (WalletVO[]) out.readObject();
        //将数组转换成List
        List<WalletVO> listObject = new ArrayList<WalletVO>(Arrays.asList(walletBean));
        System.out.println("성공");
        return listObject;
    }


    private Wallet() {}

    public static Wallet getInstance() {

        return INSTANCE;

    }

    public static void main(String[] args) {
         Wallet wallet = getInstance();
    }
}
