package server;

import model.ListeDesVentes;
import model.Vente;

import java.io.*;
import java.net.Socket;

public class HandleServer extends Thread {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private ListeDesVentes enchere;

    public HandleServer(Socket socket, ListeDesVentes enchere) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        this.enchere = enchere;
    }

    public void run() {
        try {
            while (true) {
                String command = in.readLine();

                if (command.split(" ")[0].equals("new")) {
                    enchere.newVente(new Vente(
                            Float.parseFloat(command.split(" ")[1]),
                            command.split(" ")[2],
                            command.split(" ")[3]));
                }

                if (command.split(" ")[0].equals("affiche")) {
                    out.println(enchere.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
