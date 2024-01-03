package com.thinkdiffai.futurelove.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thinkdiffai.futurelove.databinding.ItemRcvEvent1Binding;
import com.thinkdiffai.futurelove.model.Comon;
import com.thinkdiffai.futurelove.model.DetailEvent;
import com.squareup.picasso.Picasso;
import com.thinkdiffai.futurelove.model.DetailEventList;

import java.util.ArrayList;
import java.util.List;
public class EventHomeAdapter extends RecyclerView.Adapter<EventHomeAdapter.EventHomeViewHolder>  implements Filterable {

    private List<DetailEventList> eventList;
    private List<DetailEventList> eventListOld;
    public final IOnClickItemListener iOnClickItem;

    public void setData(List<DetailEventList> eventList) {
        this.eventList = eventList;
        this.eventListOld=eventList;
    }

    Context context;

    public EventHomeAdapter(List<DetailEventList> eventList, IOnClickItemListener iOnClickItem, Context context) {
        this.eventList = eventList;
        this.iOnClickItem = iOnClickItem;
        this.context = context;
    }

    public interface IOnClickItemListener {
        void onClickItem(long idToanBoSuKien);
    }
    @NonNull
    @Override
    public EventHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemRcvEvent1Binding itemBinding = ItemRcvEvent1Binding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new EventHomeViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHomeViewHolder holder, int position) {
        DetailEventList eventsList = eventList.get(position);
        if (eventsList.getSukien().size() == 0)
            return;

//      Get the first element of each detail events list
        List<DetailEvent> detailEvents = eventsList.getSukien();
        if (detailEvents.get(0) == null)
            return;


        Glide.with(holder.itemView.getContext())
                .load(detailEvents.get(0).getLinkNamGoc())
                .into(holder.itemBinding.imgPerson1);

        Glide.with(holder.itemView.getContext())
                .load(detailEvents.get(0).getLinkNuGoc())
                .into(holder.itemBinding.imgPerson2);
        Picasso.get().load(detailEvents.get(0).getLinkDaSwap()).into(holder.itemBinding.imgContent);

        int commaIndex = detailEvents.get(0).getRealTime().indexOf(",");
        String date = detailEvents.get(0).getRealTime().substring(0, commaIndex);
        holder.itemBinding.tvContent.setText(detailEvents.get(0).getTenSuKien());
        holder.itemBinding.tvDate.setText(date);
        //Toast.makeText(context,  events.get(number).getTom_Luoc_Text(), Toast.LENGTH_SHORT).show();
        holder.itemBinding.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PHONG", "ID toan bo su kien: " + detailEvents.get(0).getIdToanBoSuKien());
                iOnClickItem.onClickItem(detailEvents.get(0).getIdToanBoSuKien());
                Comon.link_nam_chua_swap = detailEvents.get(0).getLinkNamChuaSwap();
                Comon.link_nam_goc = detailEvents.get(0).getLinkNamGoc();
                Comon.link_nu_chua_swap = detailEvents.get(0).getLinkNuChuaSwap();
                Comon.link_nu_goc = detailEvents.get(0).getLinkNuGoc();
            }
        });

    }

    @Override
    public int getItemCount() {
        return null == eventList ? 0 : eventList.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = constraint.toString();
                if (searchString.isEmpty()) {
                    eventList= new ArrayList<>(eventListOld);
                } else {
                    List<DetailEventList> filteredList = new ArrayList<>();
                    for (DetailEventList item : eventListOld) {
                        List<DetailEvent> detailEvents = item.getSukien();
                        for (DetailEvent detailEvent: detailEvents){
                            if (detailEvent.getTenSuKien().toLowerCase().contains(searchString.toLowerCase())) {
                                filteredList.add(item);
                            }
                        }

                    }
                    eventList= filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = eventList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                eventList=(List<DetailEventList>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class EventHomeViewHolder extends RecyclerView.ViewHolder {
//        private final ItemRcvHistoryEventBinding itemRcvHistoryEventBinding;

        private final ItemRcvEvent1Binding itemBinding;
        public EventHomeViewHolder(ItemRcvEvent1Binding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }

}
