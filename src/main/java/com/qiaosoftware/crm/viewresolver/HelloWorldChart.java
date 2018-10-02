package com.qiaosoftware.crm.viewresolver;

import java.awt.Font;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

@Component("helloworld")
public class HelloWorldChart extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		JFreeChart chart = createChart(createDataset(map));
		ChartUtilities.writeChartAsJPEG(response.getOutputStream(), chart, 1000, 550);
	}
	
	private static PieDataset createDataset(Map<String, Object> map) {
		
		DefaultPieDataset localDefaultPieDataset = new DefaultPieDataset();
		
		for(Map.Entry<String, Object> entry: map.entrySet()){
			localDefaultPieDataset.setValue(entry.getKey(), (int)entry.getValue());
		}
		
		return localDefaultPieDataset;
	}

	private static JFreeChart createChart(PieDataset paramPieDataset) {
		
		JFreeChart localJFreeChart = ChartFactory.createPieChart3D(
				"客户贡献分析图表", paramPieDataset, true, true, false);
		
		PiePlot3D localPiePlot3D = (PiePlot3D) localJFreeChart.getPlot();
		localPiePlot3D.setDarkerSides(true);
		localPiePlot3D.setStartAngle(290.0D);
		localPiePlot3D.setDirection(Rotation.CLOCKWISE);
		localPiePlot3D.setForegroundAlpha(0.5F);
		localPiePlot3D.setNoDataMessage("No data to display");
		
		//解决乱码需要置换字体
		Font font = new Font("宋体", Font.BOLD, 35);
		TextTitle txtTitle = null;
		txtTitle = localJFreeChart.getTitle();
		txtTitle.setFont(font);
		PiePlot pieplot = (PiePlot) localJFreeChart.getPlot();
		pieplot.setLabelFont(new Font("宋体", Font.PLAIN, 25));
		
		localJFreeChart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 20));
		
		return localJFreeChart;
	}

}
