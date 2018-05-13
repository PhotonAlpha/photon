package top.jetz.photon.security;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class EncypterTest {
    private final static String password = "Pass";
    public static void main(String[] args) {
//        StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
//        System.out.println(result);
        new EncypterTest().getString();

    }
    
    
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        new StandardPBEStringEncryptor();
        config.setPasswordCharArray(password.toCharArray());
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
    
    public String getString() {
        StringEncryptor encryptor = stringEncryptor();
        String encryptorResult = encryptor.encrypt("");
        System.out.println(encryptorResult);
        System.out.println(encryptor.decrypt("xx9HnbviT1Xwcr1Wwc6rrqoPij+Ocu/lG/5BbCwAV88="));
        return encryptorResult;
    }
}
