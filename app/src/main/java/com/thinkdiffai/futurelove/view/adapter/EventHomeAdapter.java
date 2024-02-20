package com.thinkdiffai.futurelove.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

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


    long id_toan_bo_su_kien;
    private List<DetailEventList> eventList;
    private List<DetailEventList> eventListOld;

    public void setData(List<DetailEventList> eventList) {
        this.eventList = eventList;
        this.eventListOld=eventList;
    }
    public IOnClickItemListener iOnClickItem;
    Context context;


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public EventHomeAdapter(List<DetailEventList> eventList, IOnClickItemListener iOnClickItem, Context context) {
        this.eventList = eventList;
        this.iOnClickItem = iOnClickItem;
        this.context = context;
    }

//    public EventHomeAdapter(List<DetailEventList> eventList, Context context, OnItemClickListener onItemClickListener) {
//        this.eventList = eventList;
//        this.context = context;
//        this.onItemClickListener = onItemClickListener;
//    }

    public interface IOnClickItemListener {
        void onClickItem(DetailEvent detailEvent);
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
        int position_view = position;
        DetailEventList eventsList = eventList.get(position);
        if (eventsList.getSukien().size() == 0)
            return;
        List<DetailEvent> detailEvents = eventsList.getSukien();
        if (detailEvents.get(0) == null)
            return;
        DetailEvent detailEvent = detailEvents.get(0);
        Glide.with(holder.itemView.getContext()).load(detailEvent.getLinkNamGoc()).into(holder.itemBinding.avatarImageView);
        holder.itemBinding.tvTenSuKien.setText(detailEvent.getTenSuKien());
        id_toan_bo_su_kien = eventsList.getSukien().get(0).getIdToanBoSuKien();
        holder.itemBinding.tvCommentNumber.setText(String.valueOf(detailEvent.getCountComment()));
        holder.itemBinding.tvEventDetail.setText(detailEvent.getNoiDungSuKien());
        holder.itemBinding.tvViewNumber.setText(String.valueOf(detailEvent.getCountView()));
        holder.itemBinding.tvDate.setText(detailEvent.getRealTime());
        Log.d("line88", "onBindViewHolder: " + detailEvents.size());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClickItem.onClickItem(detailEvent);
            }
        });
//        holder.itemView.setOnClickListener(v -> {
//            onItemClickListener.onItemClick(detailEvents.get(position_view));
//        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("on_item_click_86", "onClick: " + onItemClickListener);
//                if(onItemClickListener != null){
//
//                    onItemClickListener.onItemClick(detailEvent);
//                    Toast.makeText(context, "abctrue", Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(context, "abcfail", Toast.LENGTH_SHORT).show();
//            }
//        });

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

    public interface OnItemClickListener {
        void onItemClick(DetailEvent detailEvent);
    }

}
