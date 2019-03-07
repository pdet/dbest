
import benchmark.Benchmark;
import benchmark.factory.BenchmarkFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lucas
 */
public class TesteProperties {

    public Properties getArquivoDePropriedades(String localizacaoDoArquivo) throws IOException {
        Properties arquivoDePropriedades = new Properties();
        FileInputStream arquivo = new FileInputStream(localizacaoDoArquivo);
        arquivoDePropriedades.load(arquivo);
        return arquivoDePropriedades;
    }
    
    public static void main(String args[]) throws IOException {
        TesteProperties testeProperties = new TesteProperties();
        Properties arquivo = testeProperties.getArquivoDePropriedades("C:/Projetos Java/TestesBD/lib/tabelas tpc-h/script tabelas tpc-h postgres.properties");
        String aux = arquivo.getProperty("numeroTotalDeTabelas");
        //aux = System.getProperty("line.separator");
        System.out.println(aux);
        
    }
}
