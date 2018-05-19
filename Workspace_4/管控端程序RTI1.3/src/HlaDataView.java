import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.omg.Dynamic.Parameter;


public class HlaDataView implements Runnable{
	ArrayList<objectClass> parameter=CTReadFOM.objParameters;
	public static ArrayList<XYSeries> series =new ArrayList<XYSeries>();
	public static int Num=0;
	public static JFreeChart Jfreechart=null;
	public static boolean jfreechartflag=false;
	public static String nodeName=null;

public HlaDataView(){
	System.out.println("执行到hlaDataView");

	Jfreechart=createLineChart();
}

	public static XYSeriesCollection createDataSet() throws InterruptedException {
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
		standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));
		standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15)); 
		standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15)); 
		 ChartFactory.setChartTheme(standardChartTheme); 
		XYSeriesCollection seriesCollection = new XYSeriesCollection();

		for(int i=0;i<series.size();i++)
		{
			seriesCollection.addSeries(series.get(i));
		}
		return seriesCollection;
	}

	public static  JFreeChart createLineChart() {
		JFreeChart chart=null;
//		ChartFrame frame=null;
//		JFrame f1=new JFrame();
		try {
			chart= ChartFactory.createXYLineChart(nodeName+"仿真实时图", "仿真步长",
					"实时数据", createDataSet(), PlotOrientation.VERTICAL, true,
					true, false);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
//		System.out.println("生成frame");
//		frame=new ChartFrame("Test Line Chart",chart);
//		frame.pack();
//		frame.setVisible(true);
//		return frame;
		return chart;
		}
//	public static ChartFrame createFrame(){
//		ChartFrame cf=null;
//		try {
//			cf=new ChartFrame("Test Line Chart",createLineChart());
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return cf;
//	}

	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<100;i++)
		{
			System.out.println(i);
			for(int j=0;j<series.size();j++)
			{
				if(i%2==0)
				{
					series.get(j).add(i,5+0.02*i+j);
					System.out.println(series.get(j).getDescription());
				}
				else
				{
					series.get(j).add(i,10-0.02*(i+j));
//					series.get(j).add
					
				}
//				chart
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}
//		
	

	
}
