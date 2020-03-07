/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversa;

import java.io.Serializable;
import java.util.ArrayList;



public class Mensagem implements Serializable{

    /**
     * @return the clientes
     */
    public ArrayList<String> getClientes() {
        return clientes;
    }

    /**
     * @param clientes the clientes to set
     */
    public void setClientes(ArrayList<String> clientes) {
        this.clientes = clientes;
    }

    private MensagemEnum mensagemEnum;
    private Object mensagem;
    private String para;
    private ArrayList<String> clientes = new ArrayList<>();
    //Construtor
    public Mensagem(MensagemEnum mensagemEnum, Object mensagem) {
        
        this.mensagemEnum = mensagemEnum;
        this.mensagem = mensagem;
    }
    
    //Construtor
    public Mensagem(MensagemEnum mensagemEnum, Object mensagem, ArrayList<String> clientes) {
        
        this.mensagemEnum = mensagemEnum;
        this.mensagem = mensagem;
        this.clientes = clientes;
    }
    
    //construtor
    public Mensagem(MensagemEnum mensagemEnum, Object mensagem, String para) {
        
        this.mensagemEnum = mensagemEnum;
        this.mensagem = mensagem;
        this.para = para;
    }

    public MensagemEnum getMensagemEnum() {
        return mensagemEnum;
    }

    public void setMensagemEnum(MensagemEnum mensagemEnum) {
        this.mensagemEnum = mensagemEnum;
    }

    public Object getMensagem() {
        return mensagem;
    }

    /**
     * @param mensagem the mensagem to set
     */
    public void setMensagem(Object mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * @return the para
     */
    public String getPara() {
        return para;
    }

    /**
     * @param para the para to set
     */
    public void setPara(String para) {
        this.para = para;
    }
}
