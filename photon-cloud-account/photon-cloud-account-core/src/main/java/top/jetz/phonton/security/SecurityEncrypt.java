package top.jetz.phonton.security;

import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class SecurityEncrypt {
    private static String password = "coooooool";
    
    private static String generateSalt() {
//        byte[] key = KeyGenerators.secureRandom(16).generateKey();
//        return new String(Hex.encode(key));
        return KeyGenerators.string().generateKey();
    }
    
    public void encrypt(String salt) {
        String password = "hello";
        BytesEncryptor encrypt = Encryptors.stronger(password, salt);
        byte[] newp = encrypt.encrypt(password.getBytes());
        
    }
    
//    public static void main(String[] args) throws UnsupportedEncodingException {
////        String salt = KeyGenerators.string().generateKey();
////        byte[] key = KeyGenerators.secureRandom(16).generateKey();
////        String salt2 = new String(Hex.encode(key));
////        System.out.println(salt);
////        System.out.println(key);
////        System.out.println(salt2);
//        
////        StandardPasswordEncoder encoder = new StandardPasswordEncoder("secret");
////        String result = encoder.encode("myPassword");
////        assertTrue(encoder.matches("myPassword", result));
//        
//        
//        
//        String salt = generateSalt();
//        String password = "hello";
//        System.out.println(salt);
//        BytesEncryptor encrypt = Encryptors.stronger(password, salt);
//        byte[] enc = encrypt.encrypt(password.getBytes());
//        Encoder encoder = Base64.getEncoder();
//        String encryptStr = encoder.encodeToString(enc);
//        
//        
//        Decoder decoder = Base64.getDecoder();
//        byte[] dec = decoder.decode(encryptStr);
//        byte[] result = encrypt.decrypt(dec);
//        
//        System.out.println(encryptStr);
//        System.out.println(new String(result));
//        
//    }
}
