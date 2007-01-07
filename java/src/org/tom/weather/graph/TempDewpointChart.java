package org.tom.weather.graph;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.jdbc.JDBCXYDataset;
import org.jfree.data.xy.XYDataset;

public class TempDewpointChart extends BaseChart {

  private static final Logger LOGGER = Logger
      .getLogger(TempDewpointChart.class);

  public TempDewpointChart(DataSource source, String filename, Timestamp start, Timestamp end) {
    buildChart(source, filename, start, end);
  }

  private void buildChart(DataSource source, String filename, Timestamp start, Timestamp end) {
    XYDataset data = readData(source, start, end);

    // create the chart...
    JFreeChart chart = ChartFactory.createTimeSeriesChart(
        "Temperature / Dewpoint", "Date", "Degrees F", data, // data
        true, // include legend
        true, false);

    writeChart(chart, filename);
  }
  
  private XYDataset readData(DataSource source, Timestamp start, Timestamp end) {
    JDBCXYDataset data = null;


    try {
      Connection con = source.getConnection();
      data = new JDBCXYDataset(con);
      String sql = "SELECT date - INTERVAL  " + Grapher.OFFSET / 1000 + " second , outside_temp, average_dewpoint FROM archive_records"
          + " where date >= '" + start + "' and date < '" + end
          + "' order by date desc;";
      data.executeQuery(sql);
      con.close();
    }

    catch (SQLException e) {
      LOGGER.error(e);
    }

    catch (Exception e) {
      LOGGER.error(e);
    }
    return data;
  }
}
