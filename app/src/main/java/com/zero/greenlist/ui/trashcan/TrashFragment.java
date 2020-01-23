package com.zero.greenlist.ui.trashcan;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zero.greenlist.R;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import javax.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrashFragment extends Fragment {

    private TrashViewModel trashViewModel;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {

        trashViewModel =
                ViewModelProviders.of(this).get(TrashViewModel.class);
        View root = inflater.inflate(R.layout.fragment_trashcan, container, false);
        final TextView textView = root.findViewById(R.id.navigation_trashcan);
        trashViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                List<PieEntry> value = new ArrayList<>(); //arraylist omdat geen vaste grote nodig heeft
                Connection conn = null;
                Statement stmt = null;

                try{
                    String url = "jdbc:mysql://remotemysql.com:3306/10DkfAUMii";
                    String user = "1ODkfAUMii";
                    String pass = "8E9HiyrzmC";
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection( url, user, pass);
                    stmt = conn.createStatement();
                    String sqlString = "SELECT vol, leeg FROM greenlist_sensor";
                    ResultSet rs = stmt.executeQuery(sqlString);
                    while(rs.next()){
                        float volF = rs.getFloat("vol");
                        float leegF = rs.getFloat("leeg");
                        value.add(new PieEntry(leegF, "Leeg")); //arralist waarde voor leeg in %
                        value.add(new PieEntry(volF, "Vol")); //arraylist waarde voor vol in %
                    }
                    stmt.close();
                    rs.close();
                    conn.close();
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());

                }
                catch(ClassNotFoundException ex) {
                    System.out.println("Error: unable to load driver class!");
                    System.exit(1);}
                catch(InstantiationException ex) {
                    System.out.println("Error: unable to instantiate driver!");
                    System.exit(3);
                        }

                PieChart pieChart = getActivity().findViewById(R.id.piechart);
                pieChart.setUsePercentValues(true); //zorgt dat procenten gebruikt kunnen worden
                Description desc = new Description();
                desc.setText("Overzicht prullenbak");
                desc.setTextSize(50f);
                value.add(new PieEntry(34f, "Leeg")); //arralist waarde voor leeg in %
                pieChart.setDescription(desc);
                pieChart.setHoleRadius(60f);
                pieChart.setTransparentCircleRadius(60f);


                PieDataSet pieDataSet = new PieDataSet(value, "Prullenbakinhoud");
                PieData pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
                pieChart.animateXY(1400, 1400);
                pieData.setValueTextSize(15f);
                pieData.setValueTextColor(Color.YELLOW);

            }
        });
        return root;
    }

}
