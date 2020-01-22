package com.zero.greenlist.ui.trashcan;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                value.add(new PieEntry(34f, "Leeg")); //arralist waarde voor leeg in %
                Connection conn = null;
                float volF, leegF;

                Statement stmt = null;
                ResultSet rsVol =  null;
                ResultSet rsLeeg =  null;
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/10DkfAUMii" , "1ODkfAUMii", "8E9HiyrzmC");
                    stmt = conn.createStatement();
                    rsVol = stmt.executeQuery("SELECT vol FROM greenlist_sensor");
                    rsLeeg = stmt.executeQuery("SELECT leeg FROM greenlist_sensor");

                    if(stmt.execute("SELECT vol FROM greenlist_sensor")){
                        rsVol = stmt.getResultSet();
                        volF = rsVol.getFloat("vol");
                        value.add(new PieEntry(volF, "Vol")); //arraylist waarde voor vol in %
                    }
                    if(stmt.execute("SELECT leeg FROM greenlist_sensor")){
                        rsLeeg = stmt.getResultSet();
                        leegF = rsLeeg.getFloat("leeg");
                        value.add(new PieEntry(leegF, "Leeg")); //arralist waarde voor leeg in %
                    }                    }catch(SQLException exx){
                    System.out.println("SQLException: " + exx.getMessage());
                    System.out.println("SQLState: " + exx.getSQLState());
                    System.out.println("VendorError: " + exx.getErrorCode());
                }



                PieChart pieChart = getActivity().findViewById(R.id.piechart);
                pieChart.setUsePercentValues(true); //zorgt dat procenten gebruikt kunnen worden
                Description desc = new Description();
                desc.setText("Overzicht prullenbak");
                desc.setTextSize(50f);

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
