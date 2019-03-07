package ui.util;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import ui.TestesConcluidos;
import ui.AcompanhamentoTestes;



public class GraficoBarras extends javax.swing.JFrame {

    private static List dados;
    private JFreeChart chart;
  

    
    public void setGraficoBarras() throws ClassNotFoundException, IOException{
        CategoryDataset dataset = GraficoBarras.createDataset();
        chart = GraficoBarras.createBarChart(dataset);
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(400, 300));
        setContentPane(panel); 
    }
    
    public void setDados (List dadosE){
        this.dados = dadosE;
    }

    private static CategoryDataset createDataset() throws ClassNotFoundException {

        TestesConcluidos teste = new TestesConcluidos();
        int cons = 0,inter = 1, dur = 3;
        int i = 0;
        String idConsulta;
        String duracaoConsultaString;
        double duracaoConsulta;
        String iteracao;
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            cons = 0;
            dur = 1;
            inter = 2;
            while (i < dados.size()/3){
                idConsulta = dados.get(cons).toString();
                System.out.print(idConsulta);
                duracaoConsultaString = dados.get(dur).toString();
                duracaoConsulta = Double.parseDouble(duracaoConsultaString);
                iteracao = dados.get(inter).toString();
                cons = cons + 3;
                dur = dur + 3;
                inter = inter + 3;
                i++;
                dataset.addValue(duracaoConsulta, iteracao, idConsulta);                       
            }        
        return dataset;
    }
    
        public void gerarImagem() throws IOException{
            ChartUtilities.saveChartAsJPEG(new java.io.File("grafbarra.jpg"), chart, 700, 600);      
    }
    
 

    private static JFreeChart createBarChart(CategoryDataset dataset) throws IOException {
        JFreeChart chart = ChartFactory.createBarChart(
                "Tempo das Consultas por Iteração", //Titulo
                "Iteração", // Eixo X
                "Tempo(ms)", //Eixo Y
                dataset, // Dados para o grafico
                PlotOrientation.VERTICAL, //Orientacao do grafico
                true, false, false); // exibir: legendas, tooltips, url
                   // ChartUtilities.saveChartAsJPEG(new java.io.File("c:/barra.jpg"), chart, 700, 600);  

        return chart;
    }

 

}
