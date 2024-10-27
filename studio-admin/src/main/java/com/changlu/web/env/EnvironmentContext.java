package com.changlu.web.env;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Data
public class EnvironmentContext {

    @Autowired
    private Environment environment;


    // sm2公钥
    public String getSM2PublicKey(){
        return environment.getProperty("sm2.publicKey","");
    }

    // sm2密钥
    public String getSM2PrivateKey(){
        return environment.getProperty("sm2.privateKey","");
    }

    // sm2公钥Q：以Q值做为js端的加密公钥
    public String getSM2PublicKeyQ(){
        return environment.getProperty("sm2.publicKeyQ","");
    }

}
