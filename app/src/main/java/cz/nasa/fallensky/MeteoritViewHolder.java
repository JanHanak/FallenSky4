package cz.nasa.fallensky;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import cz.nasa.fallensky.data.Meteorit;
import cz.nasa.fallensky.utils.Utilities;

public class MeteoritViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public TextView name;
    public TextView info;
    public TextView size;

    public Meteorit mItem;

    public MeteoritViewHolder(View view) {
        super(view);
        mView = view;
        name = view.findViewById(R.id.name);
        info = view.findViewById(R.id.info);
        size = view.findViewById(R.id.size);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder recHolder, Object object) {
        MeteoritViewHolder holder = (MeteoritViewHolder) recHolder;
        final Meteorit obj = (Meteorit) object;
        try {
            holder.mItem = obj;
            holder.name.setText(holder.mItem.name);
            name.setTypeface(null, Typeface.BOLD);

            Date date = Utilities.sourceFormatFull.parse(holder.mItem.year);
            holder.info.setText(Utilities.outputFormat.format(date.getTime()));

            holder.size.setText(holder.mItem.recclass + " - " +holder.mItem.mass + "g");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}