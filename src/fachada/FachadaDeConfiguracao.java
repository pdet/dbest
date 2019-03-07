/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import benchmark.Benchmark;
import benchmark.factory.BenchmarkFactory;
import consulta.factory.ConsultasFactory;
import db.CriadorDeBancoDeDados;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import sgbd.SGBD;
import sgbd.factory.SGBDFactory;

/**
 *
 * @author Lucas
 */
public class FachadaDeConfiguracao {
    
    public ArrayList<SGBD> getAllSGBDs() throws IOException {
        SGBDFactory sgbdFactory = new SGBDFactory();
        ArrayList<SGBD> sgbds = sgbdFactory.getSGBDs();
        return sgbds;
    }
    
    public ArrayList<Benchmark> getBenchmarksSGBD(int idDoSgbd) throws IOException {
        BenchmarkFactory benchmarkFactory = new BenchmarkFactory();
        ArrayList<Benchmark> todosBenchmarks = benchmarkFactory.getBenchmarks();
        ArrayList<Benchmark> benchmarksSelecionados = new ArrayList<Benchmark>();
        
        for(int i=0;i<todosBenchmarks.size();i++) {
            Benchmark benchmarkAtual = todosBenchmarks.get(i);
            if(benchmarkAtual.getIdsDosSgbds().contains(idDoSgbd)) {
                benchmarksSelecionados.add(benchmarkAtual);
            } 
        }
        return benchmarksSelecionados;
    }
    
    public void configurarBenchmarkEscolhido(Benchmark benchmark,int idDoSgbd) throws IOException {
        ConsultasFactory consultasFactory = new ConsultasFactory();
        consultasFactory.instanciarConsultas(benchmark, idDoSgbd);
    }
    
    public void atualizarDadosDoSGBDEscolhido(SGBD sgbd, String usuario, String senha) {
        sgbd.setUsuario(usuario);
        sgbd.setSenha(senha);
    }
    
    public void atualizarDadosDoBenchmarkEscolhido(Benchmark benchmark,int idDoSgbd, String nomeDoBanco, String nomeDoBancoParaArmazenarResultados ) {
        benchmark.getNomesDosBancosDeDados().put(idDoSgbd, nomeDoBanco);
        benchmark.getNomesDosBancosDeDadosParaArmazenarResultados().put(idDoSgbd,nomeDoBancoParaArmazenarResultados);
    }
    
    public void criarBancoDeDadosETabelas(SGBD sgbd, String nomeDoBancoASerCriado, String nomeDoBancoJaExistente, String ip, Benchmark benchmark) throws SQLException, ClassNotFoundException, IOException {
        CriadorDeBancoDeDados criadorDeBancoDeDados = new CriadorDeBancoDeDados(sgbd, nomeDoBancoJaExistente, ip);
        criadorDeBancoDeDados.criarNovoBanco(nomeDoBancoASerCriado);
        criadorDeBancoDeDados.criarTabelasDoBanco(sgbd, nomeDoBancoASerCriado, ip, benchmark);
        
        criadorDeBancoDeDados.fecharConexao();
    }
    
    public void povoarDadosTabela(SGBD sgbd,String nomeDoBancoJaExistente, String ip, String localizacaoDoArquivoScript) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        CriadorDeBancoDeDados criadorDeBancoDeDados = new CriadorDeBancoDeDados(sgbd, nomeDoBancoJaExistente, ip);
        criadorDeBancoDeDados.povoarTabelaDoBanco(localizacaoDoArquivoScript);
        
        criadorDeBancoDeDados.fecharConexao();       
    }
}
