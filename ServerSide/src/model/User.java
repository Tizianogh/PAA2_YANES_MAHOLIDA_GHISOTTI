package model;

import java.net.Inet4Address;
import java.net.InetAddress;

public class User {

    private InetAddress adressIP;
    private String pseudo;

    public User(InetAddress adressIP, String pseudo) {
        this.adressIP = adressIP;
        this.pseudo = pseudo;
    }

    public InetAddress getAdressIP() {
        return adressIP;
    }

    public String getPseudo() {
        return pseudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return pseudo.equals(user.pseudo);
    }
}
