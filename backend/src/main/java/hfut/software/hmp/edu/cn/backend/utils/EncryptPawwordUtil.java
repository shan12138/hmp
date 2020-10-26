package hfut.software.hmp.edu.cn.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptPawwordUtil {
    private static PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * 明文密码加密
     * @param cleartext
     * @return
     */
    public static String EncryptPassword(String cleartext){

        return bCryptPasswordEncoder.encode(cleartext.trim());
    }

    public static Boolean matchPassword(String password, String storePassword ){
        return bCryptPasswordEncoder.matches(storePassword,password);
    }
}
