package com.sabel.bilderrahmen.Admin.services;

import com.sabel.bilderrahmen.Admin.Client;

/**
 * Created by robin on 11.05.17.
 */
public class Client_Create extends Client {
    public boolean createMe;
    private String filname;

    public Client_Create(String filname) {

        super(filname.substring(0, filname.lastIndexOf("-") - 1),
                filname.substring(filname.lastIndexOf("-") + 1));
        this.filname = filname;
    }

    public String getFilname() {
        return filname;
    }
}
