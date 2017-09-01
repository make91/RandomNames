package com.example.make91.randomnames.view;

import android.support.v7.widget.RecyclerView;

import com.example.make91.randomnames.databinding.ListPersonBinding;

public class PersonViewHolder extends RecyclerView.ViewHolder {

    ListPersonBinding binding;

    public PersonViewHolder(ListPersonBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
