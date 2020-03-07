/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import GUI.SalaBatePapo;
import java.net.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ITAUTEC
 */
public class ClienteSocket {

    SalaBatePapo sala;
    
    public ClienteSocket(SalaBatePapo sala)
    {
     this.sala = sala;   
    }
    
    public void conectandoAoServidor() {
        Socket cliente;

        try {
            cliente = new Socket("localhost", 9999);

            //Lendo mensagens do servidor
            new Thread() {

                @Override
                public void run() {

                    try {

                        ObjectInputStream leitor = new ObjectInputStream(cliente.getInputStream());

                        while (true) {

                            String msg = leitor.readUTF();

                            if (sala != null && msg != null && !msg.isEmpty()) {
                                
                                String conversa = sala.areaTextoConversa.getText() + "\n" + sala.areaTextoEnviar.getText();
                                sala.areaTextoConversa.setText(conversa);                                
                            }

                        }

                    } catch (IOException ex) {
                        System.out.println("O cliente fechou a conexão");
                    }

                }
            }.start();

            //Escrevendo mensagens para o servidor
            ObjectOutputStream escritor = new ObjectOutputStream(cliente.getOutputStream());
            BufferedReader leitorTerminal = new BufferedReader(new InputStreamReader(System.in));

        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
