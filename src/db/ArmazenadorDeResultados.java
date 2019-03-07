/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import benchmark.Benchmark;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import consulta.Consulta;

/**
 *
 * @author Lucas
 */
public class ArmazenadorDeResultados {

    private Connection connection = null;

    public ArmazenadorDeResultados(String url, String usuario, String senha) throws SQLException {
        conectarAoBanco(url, usuario, senha);
    }

    public void conectarAoBanco(String url, String usuario, String senha) throws SQLException {
        connection = DriverManager.getConnection(url, usuario, senha);
    }
    
    public void fecharConexao() throws SQLException {
        this.connection.close();
    }

    /*Método utilizado para inserir apenas o id e a data de início
    do novo teste na tabela 'teste' e retorna o id do teste inserido*/
    public int inserirNovoTeste(String tipoDoTeste, int ordemDoTeste, int idDoBenchmark) throws SQLException {
        int idDoTeste = getNextId();
        String dataInicio = getDataHora();
        String clausulaSqlParaInserirNovoTeste = "insert into teste(id,benchmark,tipo,ordem,dataInicio) values (?,?,?,?,?)";
        PreparedStatement preparedStatement = this.connection.prepareStatement(clausulaSqlParaInserirNovoTeste);

        preparedStatement.setInt(1, idDoTeste);
        preparedStatement.setInt(2, idDoBenchmark);
        preparedStatement.setString(3, tipoDoTeste);
        preparedStatement.setString(4, getOrdemDoTeste(ordemDoTeste));
        preparedStatement.setString(5, dataInicio);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return idDoTeste;
    }

    public void inserirConsulta(Consulta consulta, int idDoTeste, int iteracao) throws SQLException {
        String clausulaSqlParaInserirConsulta = "insert into consulta(idteste,id,iteracao,ordem,duracao) values(?,?,?,?,?)";
        PreparedStatement preparedStatement = this.connection.prepareStatement(clausulaSqlParaInserirConsulta);

        preparedStatement.setInt(1, idDoTeste);
        preparedStatement.setInt(2, consulta.getId());
        preparedStatement.setInt(3, iteracao);
        preparedStatement.setInt(4, consulta.getOrdem());
        preparedStatement.setLong(5, consulta.getTempoDeExecucao());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void atualizarDadosDoTesteConcluido(int idDoTeste, long duracaoDoTeste, int numeroDeIteracoes) throws SQLException {
        String dataFim = getDataHora();
        String clausulaSqlParaAtualizarDadosDoTeste = "update teste set dataFim = ?, duracao = ? , numeroDeIteracoes = ? where id = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(clausulaSqlParaAtualizarDadosDoTeste);

        preparedStatement.setString(1, dataFim);
        preparedStatement.setLong(2, duracaoDoTeste);
        preparedStatement.setInt(3, numeroDeIteracoes);
        preparedStatement.setInt(4, idDoTeste);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public String getOrdemDoTeste(int ordem) {
        String ordemDoTeste = null;
        switch (ordem) {
            case 1:
                ordemDoTeste = "Sequencial";
                break;
            case 2:
                ordemDoTeste = "Aleatória";
                break;
            case 3:
                ordemDoTeste = "Aleatória Pré-Definida";
        }
        return ordemDoTeste;
    }

    public static String getDataHora() {
        String formatoDataHora = "dd-MM-yyyy HH:mm:ss";
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatoDataHora);
        return simpleDateFormat.format(calendario.getTime());
    }

    private int getNextId() throws SQLException {
        int nextId = 0;
        Statement statement = this.connection.createStatement();
        String sqlClauseToGetNextId = "SELECT MAX(id) as maxId FROM teste";
        ResultSet resultset = statement.executeQuery(sqlClauseToGetNextId);

        if (resultset.next()) {
            nextId = resultset.getInt("maxId");
        }
        nextId++;
        statement.close();
        return nextId;
    }

    public void configurarBenchmarkNoBanco(Benchmark benchmark) throws SQLException {
        if(!isBenchmarkAlreadyOnDB(benchmark.getId())) {

            String clausulaSQLParaInserirBenchmarkNoBanco = "insert into benchmark(id,nome,numeroDeConsultas) values(?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(clausulaSQLParaInserirBenchmarkNoBanco);
            
            preparedStatement.setInt(1,benchmark.getId());
            preparedStatement.setString(2,benchmark.getNome());
            preparedStatement.setInt(3,benchmark.getConsultas().size());
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
            inserirConsultasBenchmark(benchmark);
        }
    }
    
    public void inserirConsultasBenchmark(Benchmark benchmark) throws SQLException {
        String clausulaSQLParaInserirConsultaPorBenchmark = "insert into consultasBenchmark(idBenchmark,idConsulta,sqlConsulta) values(?,?,?)";
        PreparedStatement preparedStatement = this.connection.prepareStatement(clausulaSQLParaInserirConsultaPorBenchmark);
        int numeroDeConsultas = benchmark.getConsultas().size();
        for(int i = 0; i< numeroDeConsultas ; i++) {
            preparedStatement.setInt(1,benchmark.getId());
            preparedStatement.setInt(2,benchmark.getConsultas().get(i).getId());
            preparedStatement.setString(3,benchmark.getConsultas().get(i).getTextoConsulta());
            
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
    }
    
    public boolean isBenchmarkAlreadyOnDB(int idDoBenchmark) throws SQLException {
        Statement statement = this.connection.createStatement();
        String clausulaSQLParaVerificarSeOBenchmarkEstaNoBanco = "SELECT * FROM benchmark WHERE id =" + idDoBenchmark;
        ResultSet resultset = statement.executeQuery(clausulaSQLParaVerificarSeOBenchmarkEstaNoBanco);

        if (resultset.next()) {
            return true;
        }
        return false;
    }
}
