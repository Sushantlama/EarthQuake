package com.example.earthquake.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.earthquake.R;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter {
    public MyAdapter(Context context, ArrayList<MyClass> myClass) {
        super(context, 0, myClass);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mylistItemview = convertView;
        if (mylistItemview == null) {
            mylistItemview = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        MyClass myClass = (MyClass) getItem(position);
        TextView mag = mylistItemview.findViewById(R.id.magnitude);
        String text = String.valueOf(myClass.getMagnitude());
        mag.setText(text);

        TextView primary = mylistItemview.findViewById(R.id.primary_location);
        primary.setText(myClass.getPrimary());

        TextView offset = mylistItemview.findViewById(R.id.location_offset);
        offset.setText(myClass.getOffset());

        TextView date = mylistItemview.findViewById(R.id.date);
        date.setText(myClass.getDate());

        TextView time = mylistItemview.findViewById(R.id.time);
        time.setText(myClass.getTime());
        //changing the color of magnitude cicle according to the magnitude
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(myClass.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return mylistItemview;
    }


    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);

    }


}
