package top.jetz.photon.test;

import org.jasypt.encryption.StringEncryptor;

import top.jetz.photon.config.JasyptEncryptionConfig;

public class JaystpMailTest {
    public static void main(String[] args) {
        JasyptEncryptionConfig config = new JasyptEncryptionConfig();
        StringEncryptor resolver = config.stringEncryptor();
      System.out.println(resolver.decrypt("fgkR1Uzd7ZGOMJK66e5gnQ=="));
      System.out.println(resolver.decrypt("3JcGuJCv5m/YuHLGKrdX6g=="));
//      System.out.println(resolver.encrypt("dbpassword"));
    }
}
