package com.example.make91.randomnames.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private LoadMoreListener loadMoreListener;

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.addOnScrollListener(listener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeOnScrollListener(listener);
    }

    private final RecyclerView.OnScrollListener listener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (getAdapter() != null && listener != null) {

                int itemCount = getAdapter().getItemCount();

                LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();

                int lastVisiblePos = manager.findLastVisibleItemPosition();

                if (lastVisiblePos > itemCount - 2) {
                    loadMoreListener.shouldLoadMore();
                }
            }
        }
    };

    public interface LoadMoreListener {
        void shouldLoadMore();
    }
}
