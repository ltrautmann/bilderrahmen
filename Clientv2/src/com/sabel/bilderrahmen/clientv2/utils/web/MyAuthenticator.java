package com.sabel.bilderrahmen.clientv2.utils.web;

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
    private static PasswordAuthentication passwordAuthentication;

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        passwordAuthentication = new PasswordAuthentication(new String(MyAuthenticator.username), MyAuthenticator.password);
        return passwordAuthentication;
    }

    public static void setPasswordAuthentication(char[] username, char[] password) {
        MyAuthenticator.username = username;
        MyAuthenticator.password = password;
    }

    public static void setUsername(char[] username) {
        MyAuthenticator.username = username;
        passwordAuthentication = new PasswordAuthentication(new String(MyAuthenticator.username), MyAuthenticator.password);
    }

    public static void setPassword(char[] password) {
        MyAuthenticator.password = password;
        passwordAuthentication = new PasswordAuthentication(new String(MyAuthenticator.username), MyAuthenticator.password);
    }

    public static char[] getUsername() {
        return username;
    }

    public static char[] getPassword() {
        return password;
    }
}
