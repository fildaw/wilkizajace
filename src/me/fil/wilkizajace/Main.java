package me.fil.wilkizajace;

import java.awt.Color;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class Main extends ApplicationFrame {
	
	List<Double> miesiaceZajace = new ArrayList<Double>();
	List<Double> miesiaceWilki = new ArrayList<Double>();
	
	
	XYSeries zajace = new XYSeries("Zające");
	XYSeries wilki = new XYSeries("Wilki");
	
	public Main(String title) {
		super(title);
	}
	
	public void stworzWykres() {
		for (int i = 0; i < 12*20; i++) {
			zajace.add(i, miesiaceZajace.get(i));
			wilki.add(i, miesiaceWilki.get(i));
		}
		XYSeriesCollection data = new XYSeriesCollection(zajace);
	    data.addSeries(wilki);
	    JFreeChart chart = ChartFactory.createXYLineChart(
	        "Populacja wilków i zajęcy",
	        "Miesiące", 
	        "Populacja", 
	        data,
	        PlotOrientation.VERTICAL,
	        true,
	        true,
	        false
	    );
	    chart.setBackgroundPaint(SystemColor.activeCaption);
	    chart.getXYPlot().getRenderer().setSeriesPaint(0, new Color(0x0e805a));
	    ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	    setContentPane(chartPanel);
	}
	
	public void policz() {
		double a = 0.02;
		double b = 0.0005;
		double c = 0.05;
		
		miesiaceZajace.add(100.0);
		miesiaceWilki.add(30.0);
		
		int populacjaZajecySpada = -1;
		int populacjaWilkowSpada = -1;
		
		for (int i = 1; i <= 12*40; i++) {
			double zn = miesiaceZajace.get(i-1);
			double wn = miesiaceWilki.get(i-1);
			
			double zn_1 = zn + a*zn - b*zn*wn;
			double wn_1 = wn + b*zn*wn - c*wn;
			
			if (zn_1 < zn && populacjaZajecySpada == -1) {
				populacjaZajecySpada = i;
			}
			
			if (wn_1 < wn && populacjaWilkowSpada == -1) {
				populacjaWilkowSpada = i;
			}
			
			miesiaceZajace.add(zn_1);
			miesiaceWilki.add(wn_1);
		}
		
		System.out.println("Po upływie 5 lat:");
		System.out.println("  zające: " + String.format("%.2f", miesiaceZajace.get(60)));
		System.out.println("  wilki: " + String.format("%.2f", miesiaceWilki.get(60)));
		
		System.out.println("");
		System.out.println("Populacja zajecy zaczęła spadać po " + populacjaZajecySpada + " miesiacach");
		System.out.println("Populacja wilkow zaczęła spadać po " + populacjaWilkowSpada + " miesiacach");
		System.out.println("");
		
		System.out.println("Najmniejsza populacja (w ciągu 40 lat):");
		System.out.println(String.format("  zające:  %.2f", Collections.min(miesiaceZajace)));
		System.out.println(String.format("  wilki:  %.2f" , Collections.min(miesiaceWilki)));
		System.out.println("");
		
		System.out.println("Największa populacja (w ciągu 40 lat):");
		System.out.println(String.format("  zające: %.2f", Collections.max(miesiaceZajace)));
		System.out.println(String.format("  wilki:  %.2f", Collections.max(miesiaceWilki)));
	}
	
	public static void main(String[] args) {
		Main demo = new Main("Symulacja");
		demo.policz();
		demo.stworzWykres();
		demo.setSize(1000, 500);
	    //demo.pack();
	    RefineryUtilities.centerFrameOnScreen(demo);
	    demo.setVisible(true);
	}

}
