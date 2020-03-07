/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;
import java.net.*;
import java.io.*;

/**
 *
 * @author ITAUTEC
 */

public class ServidorSocket {

    public static void main(String[] args) throws IOException {

        //Inicializando o servidor na porta 9999
        ServerSocket servidor = null;
        Socket cliente;        

        try {
            
            servidor = new ServerSocket(9999);
            System.out.println("Servidor Conectado");

            while (true) {

                //Aguardando solicitação de um cliente cliente
                cliente = servidor.accept();
                System.out.println("Cliente aceito");
                Thread thread = new Thread(new GerenciadoClientes(cliente));
                thread.start();
                
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            //Fechando a conexão com o servidor
            if (servidor != null) {
                servidor.close();
                System.out.println("Servidor Encerrado");
            }

        }

    }

}
