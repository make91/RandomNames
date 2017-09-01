package com.example.make91.randomnames;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.make91.randomnames.beans.Person;
import com.example.make91.randomnames.network.APIService;
import com.example.make91.randomnames.view.MyRecyclerView;
import com.example.make91.randomnames.view.PeopleAdapter;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity implements MyRecyclerView.LoadMoreListener {

    public static final String TAG = "MainActivity";
    private final int peopleAmount = 5; // must be at least 2
    private Realm realm;
    private List<Person> personList;
    APIService apiService;
    MyRecyclerView recycler;
    PeopleAdapter adapter;
    private SwipeRefreshLayout swiper;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = RandomNamesApplication.getInstance().getApiService();

        adapter = new PeopleAdapter();
        recycler = (MyRecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
        recycler.setLoadMoreListener(this);

        swiper = (SwipeRefreshLayout) findViewById(R.id.swiper);
        swiper.setColorSchemeColors(Color.GRAY);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (personList != null && personList.size() != 0 && realm != null) {
                    realm.beginTransaction();
                    realm.delete(Person.class);
                    Log.d(TAG, "SwipeRefreshLayout: realm deleted");
                    realm.commitTransaction();
                }
                loadPeople();
                swiper.setRefreshing(false);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        if (!isLoading) {
                            isLoading = true;
                            int adapterPosition = viewHolder.getAdapterPosition();
                            Log.d(TAG, "deleting person from adapterPosition " + adapterPosition);
                            adapter.delete(adapterPosition);
                            isLoading = false;
                        }
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recycler);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        realm = Realm.getDefaultInstance();
        personList = realm.where(Person.class).findAllSorted("id");

        if (personList == null || personList.size() == 0) {
            Log.d(TAG, "realm personList empty, loading");
            loadPeople();
        } else {
            Log.d(TAG, "realm personList not empty");
            Log.d(TAG, "list size: " + personList.size() +
                    ", max id: " + realm.where(Person.class).max("id").intValue());
        }

        for (Person p : personList) {
            Log.d(TAG, "from realm: " + p.toString());
        }

        adapter.initialize(realm.where(Person.class).findAllSorted("id"));
    }

    @Override
    protected void onPause() {
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
        super.onPause();
    }

    private void loadPeople() {
        isLoading = true;
        Log.d(TAG, "loading more people");
        adapter.setIsLoading(true);
        apiService.getPeople(peopleAmount, true).enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                adapter.setIsLoading(false);
                isLoading = false;
                Number number = realm.where(Person.class).max("id");
                int maxId = number != null ? number.intValue() : -1;
                personList = response.body();
                if (personList != null && personList.size() > 0) {
                    for (Person p : personList) {
                        p.setId(++maxId);
                        Log.d(TAG, "Loading new person: " + p.toString());
                    }

                    realm.beginTransaction();
                    realm.copyToRealm(personList);
                    Log.d(TAG, "Copying to realm");
                    realm.commitTransaction();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: NO SUCCESS");
                adapter.setIsLoading(false);
                isLoading = false;
            }
        });
    }

    @Override
    public void shouldLoadMore() {
        if (!isLoading) {
            loadPeople();
        }
    }
}