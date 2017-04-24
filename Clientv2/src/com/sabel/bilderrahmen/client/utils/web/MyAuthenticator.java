package com.sabel.bilderrahmen.client.utils.web;

import javax.xml.bind.annotation.XmlRootElement;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by you shall not pass on 10.02.2017.
 */
@XmlRootElement
public class MyAuthenticator extends Authenticator {
    private static char[] username = "".toCharArray();
    private static char[] password = "".toCharArray();

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(new String(MyAuthenticator.username), MyAuthenticator.password);
    }

    public static void setPasswordAuthentication(char[] username, char[] password) {
        MyAuthenticator.username = username;
        MyAuthenticator.password = password;
    }
}
