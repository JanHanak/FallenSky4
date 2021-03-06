package cz.nasa.fallensky;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.nasa.fallensky.data.Meteorit;
import cz.nasa.fallensky.utils.SpaceItemDecoration;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.exceptions.RealmException;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListFragment extends Fragment {
    RecyclerView recyclerView;
    TextView chip;

    RealmAdapter adapter;
    RealmResults results;
    RecyclerView.ItemDecoration dividerItemDecoration;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.recyclerView);
        chip = getView().findViewById(R.id.chip);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if (dividerItemDecoration == null) {
            dividerItemDecoration = new SpaceItemDecoration(getActivity(),getResources().getDimensionPixelSize(R.dimen.space_items));
            recyclerView.addItemDecoration(dividerItemDecoration);
        }

        try {
            Realm realm = Realm.getDefaultInstance();
            String []fieldNames={"mass"};
            Sort sort[]={Sort.DESCENDING};

            //ugly part
            results = realm.where(Meteorit.class).beginsWith("year","201").not().beginsWith("year","2010").findAllSorted(fieldNames,sort);
            chip.setText(results.size()+" "+getString(R.string.meteors));

            results.addChangeListener(new RealmChangeListener<RealmResults>() {
                @Override
                public void onChange(RealmResults realmResults) {
                    chip.setText(realmResults.size()+" "+getString(R.string.meteors));
                }
            });
        } catch (RealmException e) {
            e.printStackTrace();
        }

        adapter = new RealmAdapter(results,getActivity());
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        recyclerView.setAdapter(adapter);
    }
}
