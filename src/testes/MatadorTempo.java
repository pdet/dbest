/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import benchmark.Benchmark;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro Holanda
 */
public class MatadorTempo extends Thread {
    private long tempo;
    Testador teste = new Testador();
            public void setTempo(int tempoTeste)   {
                tempo = tempoTeste * 60000;
                       
            }

    @Override
    public void run() {
        super.run();
        try {
            this.sleep(tempo);
            teste.setTempo();
            System.out.println("Tempo Esgotou");
        } catch (InterruptedException ex) {
            Logger.getLogger(MatadorTempo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
         
    
}
