package ui.util;


import java.awt.Font;
import java.io.IOException;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class GraficoPizza extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private DefaultPieDataset dataset;
    private JFreeChart chart;

    public GraficoPizza() {
        dataset = new DefaultPieDataset();
    }

    public void setValue(String title, Double numDouble) {
        dataset.setValue(title, numDouble);
    }

    public void setChar(String title) {

        chart = ChartFactory.createPieChart(title, dataset, true, true, false);
        PiePlot pp = (PiePlot) chart.getPlot();
        pp.setSectionOutlinesVisible(false);
        pp.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        pp.setNoDataMessage("Nenhum Dado Inserido");
        pp.setCircular(false);
        pp.setLabelGap(0.02);
    }

    private JPanel createPanel() {
        return new ChartPanel(chart);
    }

    public void Show() {
        setContentPane(createPanel());
        setVisible(true);
    }

    public void gerarImagem() throws IOException{
            ChartUtilities.saveChartAsJPEG(new java.io.File("grafpizza.jpg"), chart, 700, 600);      
    }

}
