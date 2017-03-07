package com.sabel.bilderrahmen.client.utils.WebService;

import javax.xml.bind.annotation.XmlRootElement;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by you shall not pass on 10.02.2017.
 */
@XmlRootElement
public class MyAuthenticator extends Authenticator {
    private static String username = "";
    private static String password = "";

    @Override
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication (MyAuthenticator.username,MyAuthenticator.password.toCharArray());
    }

    public static void setPasswordAuthentication(String username, String password) {
        MyAuthenticator.username = username;
        MyAuthenticator.password = password;
    }
}
