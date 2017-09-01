package com.example.make91.randomnames.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.make91.randomnames.R;
import com.example.make91.randomnames.beans.Person;
import com.example.make91.randomnames.databinding.ListPersonBinding;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class PeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements RealmChangeListener<RealmResults<Person>> {

    private static final int KEY_POSITION_LOADING = 0;
    private static final int KEY_POSITION_ITEM = 1;

    private RealmResults<Person> items;
    private boolean isLoading = false;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == KEY_POSITION_ITEM) {
            ListPersonBinding binding = ListPersonBinding.inflate(inflater, parent, false);
            return new PersonViewHolder(binding);
        } else if (viewType == KEY_POSITION_LOADING) {
            View view = inflater.inflate(R.layout.list_loading, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PersonViewHolder) {
            if (position < items.size()) {
                Person person = items.get(position);
                ((PersonViewHolder) holder).binding.setItem(person);
            }
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (isLoading) {
            count++;
        }

        if (items != null && items.size() != 0) {
            count += items.size();
        }

        return count;
    }

    public void setIsLoading(boolean currentlyLoading) {
        isLoading = currentlyLoading;
        notifyDataSetChanged();
    }

    public void initialize(RealmResults<Person> persons) {
        this.items = persons;
        notifyDataSetChanged();
        items.addChangeListener(this);
    }

    @Override
    public void onChange(RealmResults<Person> persons) {
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if (isLoading) {
            if (items != null && items.size() != 0 && items.size() == position) {
                return KEY_POSITION_LOADING;
            }
        }

        return KEY_POSITION_ITEM;
    }

    public void delete(final int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                items.deleteFromRealm(position);
            }
        });
    }
}
