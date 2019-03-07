/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import benchmark.Benchmark;
import java.sql.SQLException;
import sgbd.SGBD;
import testes.MatadorTempo;
import testes.Testador;

/**
 *
 * @author Lucas
 */
public class FachadaDeExecucaoDeTestes {

    public void testarPorIteracao(SGBD sgbd, Benchmark benchmark, String ip, int numeroDeIteracoes, int ordemDoTeste) throws SQLException, ClassNotFoundException {
        int idDoSgbd = sgbd.getId();
        String nomeDoBancoDeDados = benchmark.getNomesDosBancosDeDados().get(idDoSgbd);
        String nomeDoBancoParaArmazenarResultados = benchmark.getNomesDosBancosDeDadosParaArmazenarResultados().get(idDoSgbd);

        Testador testador = new Testador(sgbd, nomeDoBancoDeDados, nomeDoBancoParaArmazenarResultados, ip);
        System.out.println("entrou no fachada - testar por iteracao");
        testador.testarPorIteracao(benchmark, numeroDeIteracoes, ordemDoTeste);

        testador.fecharConexao();

    }

    public void testarPorTempo(SGBD sgbd, Benchmark benchmark, String ip, int tempo, int ordemDoTeste) throws SQLException, ClassNotFoundException {
        int idDoSgbd = sgbd.getId();
        String nomeDoBancoDeDados = benchmark.getNomesDosBancosDeDados().get(idDoSgbd);
        String nomeDoBancoParaArmazenarResultados = benchmark.getNomesDosBancosDeDadosParaArmazenarResultados().get(idDoSgbd);

        Testador testador = new Testador(sgbd, nomeDoBancoDeDados, nomeDoBancoParaArmazenarResultados, ip);
        MatadorTempo acabarTempo = new MatadorTempo();
        System.out.println("entrou no fachada - testar por tempo");
        acabarTempo.setTempo(tempo);
        acabarTempo.start();
        testador.testarTempo(benchmark, ordemDoTeste);
        
        testador.fecharConexao();    
    }
}
