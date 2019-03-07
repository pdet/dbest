
import java.awt.Dimension;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarsGraphic extends JFrame {

    public BarsGraphic(String title) {
        super(title);
        CategoryDataset dataset = BarsGraphic.createDataset();
        JFreeChart chart = BarsGraphic.createBarChart(dataset);
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(400, 300));
        setContentPane(panel);
    }

    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(6, "Preto", "Corsa");
        dataset.addValue(4, "Preto", "Fiesta");
        dataset.addValue(3, "Preto", "Gol");
        dataset.addValue(5, "Vermelho", "Corsa");
        dataset.addValue(2, "Vermelho", "Fiesta");
        dataset.addValue(3, "Vermelho", "Gol");
        dataset.addValue(2, "Azul", "Corsa");
        dataset.addValue(8, "Azul", "Fiesta");
        dataset.addValue(1, "Azul", "Gol");
        return dataset;
    }

    private static JFreeChart createBarChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Escolha de cor por veículo", //Titulo
                "Veículo", // Eixo X
                "Quantidade", //Eixo Y
                dataset, // Dados para o grafico
                PlotOrientation.VERTICAL, //Orientacao do grafico
                true, false, false); // exibir: legendas, tooltips, url
        return chart;
    }

    public static void main(String[] args) {
        BarsGraphic chart = new BarsGraphic("Teste Bar Chart");
        chart.pack();
        chart.setVisible(true);
    }
}
