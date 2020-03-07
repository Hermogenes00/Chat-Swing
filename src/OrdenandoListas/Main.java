/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrdenandoListas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author ITAUTEC
 */
public class Main {

    public static void main(String[] args) {

        ArrayList<Time> times = new ArrayList<>();

        times.add(new Time("Bahia"));
        times.add(new Time("Vitoria"));
        times.add(new Time("Atletico de alagoinhas"));
        times.add(new Time("Sergie"));
        times.add(new Time("Bahia de Feira de Santana"));

        System.out.println("Sem ordenação \n");
        //Utilização simples de lambda
        times.forEach(time -> System.out.println(time.getNome()));
        
        /* Caso contrário teríamos que utilizar o foreach desta forma.
        for(Time time: times)
        {
            System.out.println(time.getNome());
        }
        */        
        
        //Método que chama a ordenação.
        times.sort(new comparatorNome());

        System.out.println("\n Com ordenação ");
        //Utilização simples de lambda
        times.forEach(time -> System.out.println(time.getNome()));
    }

    //Classe responsável por ordenar pelo nome.
    public static class comparatorNome implements Comparator {

        @Override
        public int compare(Object t, Object t1) {

            Time time = (Time) t;
            Time time2 = (Time) t1;

            return time.getNome().compareTo(time2.getNome());
        }
    }
}
