/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Conversa.Mensagem;
import Conversa.MensagemEnum;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author ITAUTEC
 */
public class SalaBatePapo extends JFrame {

    public JTextArea areaTextoEnviar;

    public JPanel painelAreaConversa;
    public JPanel painelAreaTexto;
    public JPanel painelListaUsuarios;
    public JTextArea areaTextoConversa;
    public static JList ListaUsuarios;
    public JScrollPane barraRolagem;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    DefaultListModel modelo;
    private String usuario;
    //Criando uma lista de usuários para testar o Jlist    
    String mensagemDestinatario;

    public SalaBatePapo() {

        this.iniciarChat();
        inicializarComponentes();
        this.receberMensagem();

    }

    private void inicializarComponentes() {

        //Criando os painéis
        painelAreaConversa = new JPanel();
        painelAreaTexto = new JPanel();

        painelAreaConversa.setBackground(Color.blue);
        painelAreaTexto.setBackground(Color.gray);

        painelListaUsuarios = new JPanel();

        painelListaUsuarios.setSize(2, 100);
        painelListaUsuarios.setBackground(Color.yellow);

        //Criando os campos que receberão e enviarão os texto
        areaTextoEnviar = new JTextArea();
        areaTextoEnviar.setPreferredSize(new Dimension(500, 30));
        //areaTextoEnviar.setColumns(35);

        areaTextoEnviar.addKeyListener(new areaTextoPressionarEnter());

        areaTextoConversa = new JTextArea(15, 30);

        modelo = new DefaultListModel();
        ListaUsuarios = new JList();
        ListaUsuarios.setModel(modelo);

        ListaUsuarios.addMouseListener(new listaUsuariosEventosMouse());

        painelListaUsuarios.add(BorderLayout.CENTER, ListaUsuarios);

        //Criando a barra de rolagem para colocar a areaTextoConversa dentro dela.
        barraRolagem = new JScrollPane(areaTextoConversa);
        barraRolagem.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        barraRolagem.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        //Adcionando os textArea aos painéis.
        painelAreaConversa.add(BorderLayout.NORTH, painelListaUsuarios);
        painelAreaConversa.add(BorderLayout.EAST, barraRolagem);

        painelAreaTexto.add(BorderLayout.CENTER, areaTextoEnviar);

        super.getContentPane().add(BorderLayout.SOUTH, painelAreaTexto);
        super.getContentPane().add(BorderLayout.CENTER, painelAreaConversa);

        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pack();
        this.setVisible(true);
        this.setTitle("Sala de bate papo");

        usuario = JOptionPane.showInputDialog("Informe seu nome");
        escrever(new Mensagem(MensagemEnum.ADD, usuario));


    }

    //KeyListenes utilizam threads, pois são interfaces ouvintes
    private class areaTextoPressionarEnter implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {

        }

        @Override
        public void keyPressed(KeyEvent ke) {

            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {

                if (!areaTextoEnviar.getText().isEmpty()) {

                    String mensagem = areaTextoEnviar.getText();

                    Mensagem objMensagem = new Mensagem(MensagemEnum.TEXTO, mensagem, mensagemDestinatario);

                    escrever(objMensagem);

                    areaTextoEnviar.setText("");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {

        }

    }

    private class listaUsuariosEventosMouse implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent me) {
            if (me.getClickCount() == 2) {

                mensagemDestinatario = (String) ListaUsuarios.getSelectedValue();
            }
        }

        @Override
        public void mousePressed(MouseEvent me) {

        }

        @Override
        public void mouseReleased(MouseEvent me) {

        }

        @Override
        public void mouseEntered(MouseEvent me) {

        }

        @Override
        public void mouseExited(MouseEvent me) {

        }

    }

    private void escrever(Mensagem mensagem) {
        try {

            output.writeObject(mensagem);
            output.flush();

        } catch (IOException e) {

        }

    }

    //Inicia a conexão com servidor alimentando as variáveis ObjectInputStream e ObjectOutputStream
    //Tornei esta classe final, porque eu não quero que ela seja sobreposta
    public final void iniciarChat() {
        try {

            Socket cliente = new Socket("localhost", 9999);

            input = new ObjectInputStream(cliente.getInputStream());
            output = new ObjectOutputStream(cliente.getOutputStream());

        } catch (IOException e) {

        }

    }

    public final void receberMensagem() {
        try {

            while (true) {

                Mensagem mensagem = (Mensagem) input.readObject();

                if (mensagem.getMensagemEnum() == MensagemEnum.SUCCESS) {

                    String mensagemRecebida = (String) mensagem.getMensagem();
                    if (!mensagemRecebida.isEmpty()) {

                        areaTextoConversa.append(mensagemRecebida + "\n");
                    }
                }
                if (mensagem.getMensagemEnum() == MensagemEnum.ADD_SUCCESS) {

                    String mensagemRecebida = (String) mensagem.getMensagem();
                    areaTextoConversa.append(mensagemRecebida + "\n" + mensagem.getClientes());
                    
                    if (mensagem.getClientes().size()>0) {
                        
                        mensagem.getClientes().forEach(c->{modelo.addElement(c);});
                    }

                }
                //Desconecta o cliente do chat (Socket.close())
                if (mensagem.getMensagemEnum() == MensagemEnum.SAIR) {
                    System.exit(0);
                }
            }

        } catch (ClassNotFoundException | IOException classNotFound) {

        }
    }
}
