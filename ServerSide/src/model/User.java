package model;

import java.io.PrintWriter;
import java.net.InetAddress;

public class User {

    private InetAddress adressIP;
    private String pseudo;
    private PrintWriter out;

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public PrintWriter getOut() {
        return out;
    }

    public User(InetAddress adressIP, String pseudo, PrintWriter out) {
        this.adressIP = adressIP;
        this.pseudo = pseudo;
        this.out=out;
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
