
import benchmark.Benchmark;
import benchmark.factory.BenchmarkFactory;
import consulta.Consulta;
import consulta.factory.ConsultasFactory;
import fachada.FachadaDeConfiguracao;
import fachada.FachadaDeExecucaoDeTestes;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import db.ArmazenadorDeResultados;
import db.CriadorDeBancoDeDados;
import db.RecuperadorDeResultados;
import fachada.FachadaDeRecuperacaoDeDados;
import java.util.HashMap;
import sgbd.SGBD;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lucas
 */
public class Main {

    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException {
        FachadaDeConfiguracao fachadaDeConfiguracao = new FachadaDeConfiguracao();
        FachadaDeExecucaoDeTestes fachadaDeExecucao = new FachadaDeExecucaoDeTestes();
        FachadaDeRecuperacaoDeDados fachadaDeRecuperacao = new FachadaDeRecuperacaoDeDados();
        ArrayList<SGBD> sgbds = fachadaDeConfiguracao.getAllSGBDs();
        ArrayList<Benchmark> benchmarks = fachadaDeConfiguracao.getBenchmarksSGBD(sgbds.get(2).getId());
        System.out.println(sgbds.get(2).getNome());
        
        HashMap<Integer,Integer> duracaoMedia = fachadaDeRecuperacao.getDuracaoMediaDasConsultas(46, sgbds.get(2), "resultados_dbest", "localhost"); 
        System.out.println(duracaoMedia.get(24));
        //System.out.println(benchmarks.size());
        //fachadaDeConfiguracao.criarBancoDeDadosETabelas(sgbds.get(1), "banconovo", "tpc-h", "localhost", benchmarks.get(1));
        //fachadaDeConfiguracao.povoarDadosTabela(sgbds.get(0), "tpch", "localhost", "C:/Lucas/Projeto Pesquisa/TPC-C/Script Povoar Tabelas/warehouse");
        
        //Benchmark benchmark = benchmarks.get(0);
        //fachadaDeConfiguracao.configurarBenchmarkEscolhido(benchmark, sgbds.get(0).getId());
        //fachadaDeConfiguracao.atualizarDadosDoBenchmarkEscolhido(benchmark, sgbds.get(0).getId(), "tpc-h", "resultados_dbest");
        //System.out.println(benchmark.getLocalizacaoDosArquivosDeCriacaoDasTabelas());
        //fachadaDeConfiguracao.criarBancoDeDadosETabelas(sgbds.get(0), "teste", "tpch", "localhost", benchmark);
        //fachadaDeExecucao.testarPorIteracao(sgbds.get(2), benchmark, "localhost", 2, 1); 
        //RecuperadorDeResultados recuperadorDeResultados = new RecuperadorDeResultados(sgbds.get(2), "resultados_tpch", "localhost"); 
        //fachadaDeConfiguracao.criarBancoDeDadosETabelas(sgbds.get(2), "nomenovo","tpc-h" , "localhost", benchmark);
        
        
        //CriadorDeBancoDeDados criador = new CriadorDeBancoDeDados(sgbds.get(2), "tpc-c", "localhost");
        //criador.povoarTabelaDoBanco("C:/Lucas/Projeto Pesquisa/TPC-C/Script Povoar Tabelas/warehouse");
        
    }

    public static String getDataHora() {
        String formatoDataHora = "dd-MM-yyyy HH:mm:ss";
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatoDataHora);
        return simpleDateFormat.format(calendario.getTime());

    }

    public static String gerarURL(SGBD sgbd, String nomeDoBanco, String ip) {
        String url = sgbd.getUrl();

        // substitui todas as ocorrências de "Java" por "C#"
        url = url.replace("ip", ip);
        url = url.replace("nomeDoBanco", nomeDoBanco);
        return url;
    }
}
