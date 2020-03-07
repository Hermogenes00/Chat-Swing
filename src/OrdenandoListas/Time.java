/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrdenandoListas;

/**
 *
 * @author ITAUTEC
 */
public class Time {
    
    public Time(String nome)
    {
        this.nome = nome;
    }
    private String nome;

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}
