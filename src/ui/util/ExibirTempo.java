/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import ui.AcompanhamentoTestes;
import ui.AcompanhamentoTestes;

/**
 *
 * @author pedroholanda
 */
public class ExibirTempo extends Thread{
    int min = 0;
    int seg = 0;
    int hora = 00;
    AcompanhamentoTestes tela;
    
    public void setTela(AcompanhamentoTestes tela){
        this.tela = tela;
        
    }
    public void run(){
        while(true){
        try {
            this.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExibirTempo.class.getName()).log(Level.SEVERE, null, ex);
        }
        seg++;
        if (seg == 60){
            min++;
            seg=00;
            if(min ==60){
                hora++;
                min = 00;
                       
            }
        }
        tela.setTempoDecorrido(hora, min, seg);
    }
    }
    
}
