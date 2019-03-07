/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import java.sql.SQLException;
import java.util.ArrayList;
import db.RecuperadorDeResultados;
import java.util.HashMap;
import sgbd.SGBD;

/**
 *
 * @author Lucas
 */
public class FachadaDeRecuperacaoDeDados {

    public ArrayList getDadosDoTeste(int idTeste, SGBD sgbd, String nomeDoBancoParaArmazenadorOsResultados, String ip) throws SQLException, ClassNotFoundException {
        RecuperadorDeResultados recuperadorDeResultados = new RecuperadorDeResultados(sgbd, nomeDoBancoParaArmazenadorOsResultados, ip);
        ArrayList dadosDoTeste = recuperadorDeResultados.getDadosDoTesteCadastrado(idTeste);

        recuperadorDeResultados.fecharConexao();
        return dadosDoTeste;
    }

    public ArrayList<Integer> getIdDeTodosOsTestes(SGBD sgbd, String nomeDoBancoParaArmazenadorOsResultados, String ip) throws SQLException, ClassNotFoundException {
        RecuperadorDeResultados recuperadorDeResultados = new RecuperadorDeResultados(sgbd, nomeDoBancoParaArmazenadorOsResultados, ip);
        ArrayList<Integer> listaDeTestes = recuperadorDeResultados.getIdDeTodosOsTestes();

        recuperadorDeResultados.fecharConexao();
        return listaDeTestes;

    }

    public ArrayList getDadosDasConsultasDoTeste(int idTeste, SGBD sgbd, String nomeDoBancoParaArmazenadorOsResultados, String ip) throws SQLException, ClassNotFoundException {
        RecuperadorDeResultados recuperadorDeResultados = new RecuperadorDeResultados(sgbd, nomeDoBancoParaArmazenadorOsResultados, ip);
        ArrayList dadosDasConsultas = recuperadorDeResultados.getConsultasTeste(idTeste);

        recuperadorDeResultados.fecharConexao();
        return dadosDasConsultas;
    }

    public int getIdTesteAtual(SGBD sgbd, String nomeDoBancoParaArmazenadorOsResultados, String ip) throws SQLException, ClassNotFoundException {
        RecuperadorDeResultados recuperadorDeResultados = new RecuperadorDeResultados(sgbd, nomeDoBancoParaArmazenadorOsResultados, ip);
        int idDoTesteAtual = recuperadorDeResultados.getIdTesteAtual();

        recuperadorDeResultados.fecharConexao();
        return idDoTesteAtual;
    }

    public HashMap<Integer, String> getTextoDasConsultasBenchmark(int idDoBenchmark, SGBD sgbd, String nomeDoBancoParaArmazenadorOsResultados, String ip) throws SQLException, ClassNotFoundException {
        RecuperadorDeResultados recuperadorDeResultados = new RecuperadorDeResultados(sgbd, nomeDoBancoParaArmazenadorOsResultados, ip);
        HashMap textoDasConsultasBenchmark = recuperadorDeResultados.getConsultasBenchmark(idDoBenchmark);

        recuperadorDeResultados.fecharConexao();
        return textoDasConsultasBenchmark;
    }

    public String getNomeDoBenchmark(int idDoBenchmark, SGBD sgbd, String nomeDoBancoParaArmazenadorOsResultados, String ip) throws SQLException, ClassNotFoundException {
        RecuperadorDeResultados recuperadorDeResultados = new RecuperadorDeResultados(sgbd, nomeDoBancoParaArmazenadorOsResultados, ip);
        String nomeBenchmark = recuperadorDeResultados.getNomeDoBenchmark(idDoBenchmark);

        recuperadorDeResultados.fecharConexao();
        return nomeBenchmark;
    }

    public HashMap<Integer, Integer> getDuracaoMediaDasConsultas(int idDoTeste, SGBD sgbd, String nomeDoBancoParaArmazenadorOsResultados, String ip) throws SQLException, ClassNotFoundException {
        RecuperadorDeResultados recuperadorDeResultados = new RecuperadorDeResultados(sgbd, nomeDoBancoParaArmazenadorOsResultados, ip);
        HashMap<Integer,Integer> duracaoMediaDasConsultas = recuperadorDeResultados.getDuracaoMediaDasConsultas(idDoTeste);
    
        recuperadorDeResultados.fecharConexao();
        return duracaoMediaDasConsultas;
    }
}
