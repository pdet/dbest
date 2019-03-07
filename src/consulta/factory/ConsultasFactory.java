/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package consulta.factory;

import benchmark.Benchmark;
import consulta.Consulta;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Lucas
 */

/* Classe usada para implementação do padrão Factory para retornar as Consultas
 * de acordo com o SGBD
 */
public class ConsultasFactory {

    /** Constante utilizada para representar o SGBD Oracle **/
    public static final int ORACLE = 1;
    /** Constante utilizada para representar o SGBD postgres **/
    public static final int POSTGRES = 2;
    /** Constante utilizada para representar o SGBD SQL Server **/
    public static final int SQLSERVER = 3;

    public Properties getArquivoDePropriedades(String localizacaoDoArquivo) throws IOException {
        Properties arquivoDePropriedades = new Properties();
        FileInputStream arquivo = new FileInputStream(localizacaoDoArquivo);
        arquivoDePropriedades.load(arquivo);
        return arquivoDePropriedades;
    }

    public void instanciarConsultas(Benchmark benchmark, int idDoSgbd) throws IOException {
        String localizacaoArquivoDeConsultas = benchmark.getLocalizacaoDosArquivosDeConsultas().get(idDoSgbd);
        Properties arquivoDeConsultas = getArquivoDePropriedades(localizacaoArquivoDeConsultas);
        int numeroDeConsultas = Integer.parseInt(arquivoDeConsultas.getProperty("numeroTotalDeConsultas"));

        for (int i = 1; i <= numeroDeConsultas; i++) {
            String textoDaConsulta = arquivoDeConsultas.getProperty(String.valueOf(i));
            Consulta consulta = new Consulta(textoDaConsulta);
            consulta.setId(i);
            if (isTheQueryAnUpdate(textoDaConsulta)) {
                consulta.setTipo('U');
            } else {
                consulta.setTipo('S');
            }
            benchmark.addConsulta(consulta);
        }
    }

    public boolean isBenchmarkAlreadyConfigurated(Benchmark benchmark) {
        if (benchmark.getConsultas().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isTheQueryAnUpdate(String textoDaConsulta) {
        if (textoDaConsulta.toLowerCase().contains("update") || textoDaConsulta.toLowerCase().contains("create")
                || textoDaConsulta.toLowerCase().contains("drop") || textoDaConsulta.contains("delete")) {
            return true;
        }
        return false;
    }
}
