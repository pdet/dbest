/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.util;

import benchmark.Benchmark;
import fachada.FachadaDeRecuperacaoDeDados;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgbd.SGBD;
import testes.Testador;
import ui.ConexaoBanco;
import ui.AcompanhamentoTestes;

/**
 *
 * @author pedroholanda
 */
public class PesquisaConsultas extends Thread {

    private static SGBD sgbd;
    private static String nomeBancoResultados;
    private static String ip;
    AcompanhamentoTestes teste;
    ThreadConfiguracaoTestes tempoConcluido = new ThreadConfiguracaoTestes();

    public void setBanco(SGBD sgbd) {
        this.sgbd = sgbd;
    }

    public void setTeste(AcompanhamentoTestes testeExecucao) {
        this.teste = testeExecucao;
    }

    public void setNomeBancoResultados(String nomeBanco) {
        this.nomeBancoResultados = nomeBanco;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public void run() {
        String duracaoConsulta = null;
        int i = 0;
        String idConsulta = null;
        ArrayList consultasExecutadas;
        String interacao;
        FachadaDeRecuperacaoDeDados fachada = new FachadaDeRecuperacaoDeDados();
        ArrayList consultasTabela = new ArrayList();
        ConexaoBanco banco = new ConexaoBanco();
        Benchmark bench = banco.getBenchmark();
        ArrayList consultas = bench.getConsultas();
        try {
            this.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PesquisaConsultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (!tempoConcluido.testeConcluiu()) {

            try {
                consultasExecutadas = fachada.getDadosDasConsultasDoTeste(fachada.getIdTesteAtual(sgbd, nomeBancoResultados, ip), sgbd, nomeBancoResultados, ip);
                while (i < consultasExecutadas.size()) {
                    idConsulta = consultasExecutadas.get(i).toString();
                    if (consultasTabela.contains(idConsulta) && consultasTabela.size() == consultas.size()) {
                        consultasTabela.clear();
                        teste.atualizarJIteracoes();
                        teste.preencherJConsultasfaltando();
                    }
                    if(!consultasTabela.contains(idConsulta)){
                    duracaoConsulta = consultasExecutadas.get(i + 3).toString();
                    interacao = consultasExecutadas.get(i + 1).toString();
                    i = i + 4;
                    System.out.println(i/4);
                    teste.atualizarJConsultasExecutando(idConsulta, duracaoConsulta, interacao);
                    consultasTabela.add(idConsulta);
                    }
                }
                if (consultasTabela.contains(idConsulta) && consultasTabela.size() == consultas.size()) {
                    consultasTabela.clear();
                    teste.atualizarJIteracoes();
                    teste.preencherJConsultasfaltando();
                }
               // else if(consultasTabela.size() == consultas.size() && tempoConcluido.testeConcluiu()){
             //       consultasTabela.clear();
             //       teste.atualizarJIteracoes();
             //   }
            } catch (SQLException ex) {
                Logger.getLogger(PesquisaConsultas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PesquisaConsultas.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                this.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PesquisaConsultas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            consultasExecutadas = fachada.getDadosDasConsultasDoTeste(fachada.getIdTesteAtual(sgbd, nomeBancoResultados, ip), sgbd, nomeBancoResultados, ip);
            idConsulta = consultasExecutadas.get(i).toString();
            duracaoConsulta = consultasExecutadas.get(i + 3).toString();
            consultasTabela.add(idConsulta);
        } catch (SQLException ex) {
            Logger.getLogger(PesquisaConsultas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PesquisaConsultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    if (consultasTabela.size() == consultas.size()) {
        teste.testeTerminou(duracaoConsulta);
    }
    else{
        
    }
       // consultasTabela.clear();
        tempoConcluido.setTesteConcluido(false);
    }
}
