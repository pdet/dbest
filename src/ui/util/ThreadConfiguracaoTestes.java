/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.util;

import benchmark.Benchmark;
import fachada.FachadaDeExecucaoDeTestes;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgbd.SGBD;
import ui.ConexaoBanco;
import ui.ConfiguracaoDeTestes;
import ui.AcompanhamentoTestes;
import ui.excecao.TesteErro;
import ui.excecao.TesteFoiConcluido;

/**
 *
 * @author Pedro Holanda
 */
public class ThreadConfiguracaoTestes extends Thread{
    FachadaDeExecucaoDeTestes fachadaDeExecucao = new FachadaDeExecucaoDeTestes();

    private static int tipo;
    private static int iteracoes;
    private static ConexaoBanco dados;
    private static int ordem;
    private static int tempo;
    private static boolean testeConcluido = false;
    TesteFoiConcluido testeC = new TesteFoiConcluido();
    AcompanhamentoTestes janela;
    
    
    public boolean testeConcluiu(){
        return testeConcluido;
    }
    
    public void setTipo(int jTipo){
    tipo = jTipo;
    }
    
    public void setTempo(int jTempo){
    tempo = jTempo;
    }
    
    public void setIteracao(int jIteracao){
        iteracoes = jIteracao;
    }
    
    public void setDados (ConexaoBanco conexao){
         dados = conexao;
    }
    
    public void setOrdem(int jOrdem){
        ordem = jOrdem;
    }
    
    public void setJanela (AcompanhamentoTestes janela){
        this.janela = janela;
    }

    public void setTesteConcluido (boolean testeC){
        this.testeConcluido = testeC;
    }
    

    
    public void run (){
        SGBD sgbd;
        Benchmark bench;
        if (tipo == 0) {
            
            sgbd = dados.getSGBD();
             bench = dados.getBenchmark();
            try {
                fachadaDeExecucao.testarPorIteracao(sgbd, bench, dados.getIP(), iteracoes,ordem);
                testeConcluido = true;
                testeC.setVisible(true);
                testeC.setJanela(janela);
            } catch (SQLException ex) {
                TesteErro erro = new TesteErro();
                erro.setErros(ex.getMessage(), janela);
                erro.setVisible(true);
               // Logger.getLogger(ThreadConfiguracaoTestes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadConfiguracaoTestes.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            sgbd = dados.getSGBD();
             bench = dados.getBenchmark();
            try {
                fachadaDeExecucao.testarPorTempo(sgbd, bench, dados.getIP(), tempo, ordem);
                testeConcluido = true;
                testeC.setVisible(true);
                testeC.setJanela(janela);
            } catch (SQLException ex) {
                TesteErro erro = new TesteErro();
                erro.setErros(ex.getMessage(), janela);
                erro.setVisible(true); 
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConfiguracaoDeTestes.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
