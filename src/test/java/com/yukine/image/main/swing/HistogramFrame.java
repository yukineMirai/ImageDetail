package com.yukine.image.main.swing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

public class HistogramFrame {

    public static ChartPanel histogra(int[] array){

        CategoryDataset dataset = getDataSet(array);
        JFreeChart chart = ChartFactory.createBarChart3D("直方图","RGB",
                "数量",dataset,PlotOrientation.VERTICAL,
                true,false,false);

        CategoryPlot plot=chart.getCategoryPlot();
        CategoryAxis domainAxis=plot.getDomainAxis();
        domainAxis.setLabelFont(new Font("黑体", Font.BOLD,14));
        domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));
        ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
        ChartPanel frame =new ChartPanel(chart,true);

        return frame;
    }

    public static CategoryDataset getDataSet(int[] array){

        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
        for (int i= 0; i< array.length; i++){
            defaultCategoryDataset.addValue(array[i],"数量",i+"");
        }

        return defaultCategoryDataset;
    }
}
