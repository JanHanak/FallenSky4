package cz.nasa.fallensky;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.Set;

import cz.nasa.fallensky.DetailActivity;
import cz.nasa.fallensky.MeteoritViewHolder;
import cz.nasa.fallensky.R;
import cz.nasa.fallensky.data.Meteorit;
import cz.nasa.fallensky.utils.MyConstants;
import io.realm.OrderedRealmCollection;
import io.realm.RealmObject;
import io.realm.RealmRecyclerViewAdapter;

public class RealmAdapter extends RealmRecyclerViewAdapter<RealmObject, MeteoritViewHolder> {

    private boolean inDeletionMode = false;
    private Set<Integer> countersToDelete = new HashSet<Integer>();
    private Activity activity;

    public RealmAdapter(OrderedRealmCollection<RealmObject> data,Activity activity) {
        super(data, true);
        setHasStableIds(true);
        this.activity = activity;
    }

    void enableDeletionMode(boolean enabled) {
        inDeletionMode = enabled;
        if (!enabled) {
            countersToDelete.clear();
        }
        notifyDataSetChanged();
    }

    Set<Integer> getCountersToDelete() {
        return countersToDelete;
    }


    @Override
    public MeteoritViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeteoritViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meteorit, parent, false));
    }

    @Override
    public void onBindViewHolder(MeteoritViewHolder holder,final int position) {
        holder.onBindViewHolder(holder,getItem(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meteorit meteorit = (Meteorit) getItem(position);
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra(MyConstants.ID, meteorit.id);
                activity.startActivity(intent);
            }
        });

    }


}