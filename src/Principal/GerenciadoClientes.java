/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Conversa.Mensagem;
import Conversa.MensagemEnum;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ITAUTEC
 */
public class GerenciadoClientes implements Runnable {

    private Socket cliente = null;
    public static final HashMap<String, GerenciadoClientes> LISTA_CLIENTES = new HashMap<>();
    private ObjectOutputStream escritor;
    private ObjectInputStream leitor;
    private String nomeUsuario;

    public GerenciadoClientes(Socket cliente) {
        this.cliente = cliente;
    }

    void DesconectarCliente() {
        try {
            this.cliente.close();
            System.out.println("Cliente desconectado");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public ObjectOutputStream getEscritor() {
        return this.escritor;
    }

    public void addLista_Clientes(String nome) {

        GerenciadoClientes.LISTA_CLIENTES.put(nome, this);
    }

    @Override
    public void run() {

        try {

            //Envia informações para o servidor
            escritor = new ObjectOutputStream(cliente.getOutputStream());

            //Recebe informações do servidor
            leitor = new ObjectInputStream(cliente.getInputStream());

            while (true) {

                Mensagem recebido = (Mensagem) leitor.readObject();

                if (recebido.getMensagemEnum() == MensagemEnum.ADD) {

                    String mensagemRecebida = (String) recebido.getMensagem();

                    if (!mensagemRecebida.isEmpty()) {

                        addLista_Clientes(mensagemRecebida);

                        ArrayList<String> lista = new ArrayList<>();

                        LISTA_CLIENTES.keySet().forEach(c -> lista.add(c));

                        Mensagem m = new Mensagem(MensagemEnum.ADD_SUCCESS, "Usuário criado com sucesso", lista);

                        lista.forEach(c -> {

                            try {
                                GerenciadoClientes gerenciador = (GerenciadoClientes) GerenciadoClientes.LISTA_CLIENTES.get(c);

                                gerenciador.escritor.writeObject(m);
                                gerenciador.escritor.flush();
                                
                            } catch (IOException e) {
                            }
                        });
                        escritor.writeObject(new Mensagem(MensagemEnum.ADD_SUCCESS, "Usuário criado com sucesso: " + GerenciadoClientes.LISTA_CLIENTES.keySet()));

                        escritor.flush();
                    }
                }
                //Desconecta o cliente do chat (Socket.close())
                if (recebido.getMensagemEnum() == MensagemEnum.SAIR) {
                    DesconectarCliente();
                }
                if (recebido.getMensagemEnum() == MensagemEnum.PRIVADA) {

                    String mensagem = (String) recebido.getMensagem();

                    if (!recebido.getPara().isEmpty() && !mensagem.isEmpty()) {

                        escritor.writeObject(new Mensagem(MensagemEnum.TEXTO, "Eu: " + mensagem));
                        escritor.flush();

                        GerenciadoClientes destinatario = (GerenciadoClientes) LISTA_CLIENTES.get(recebido.getPara());
                        destinatario.escritor.writeObject(new Mensagem(MensagemEnum.TEXTO, this.nomeUsuario + ": " + mensagem));
                        destinatario.escritor.flush();
                    }
                }
                if (recebido.getMensagemEnum() == MensagemEnum.TEXTO) {

                    String mensagemRecebida = (String) recebido.getMensagem();
                    escritor.writeObject(new Mensagem(MensagemEnum.SUCCESS, "Eu: " + mensagemRecebida));
                    escritor.flush();
                }

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }

    }

}
