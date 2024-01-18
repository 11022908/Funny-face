package com.thinkdiffai.futurelove.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabItem;
import com.thinkdiffai.futurelove.R;
import com.thinkdiffai.futurelove.databinding.ItemRcvEvent1Binding;
import com.thinkdiffai.futurelove.databinding.TabItemBinding;
import com.thinkdiffai.futurelove.model.Comon;
import com.thinkdiffai.futurelove.model.DetailEvent;
import com.squareup.picasso.Picasso;
import com.thinkdiffai.futurelove.model.DetailEventList;
import com.thinkdiffai.futurelove.view.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;
public class EventHomeAdapter extends RecyclerView.Adapter<EventHomeAdapter.EventHomeViewHolder>  implements Filterable {

    private List<DetailEventList> eventList;
    private List<DetailEventList> eventListOld;
    public IOnClickItemListener iOnClickItem;
    public EventonClick EventonClick;
    public EventonClick EventonClickDetail;
    public clickEventDetail onClickDetail;
    private String urlImgMale;
    private String urlImgFemale;
    public interface EventonClick {
        void onClickItem();
    }

    public void NavEventDetail(EventonClick eventonClick){
        this.EventonClickDetail = eventonClick;
    }
    public void setOnClickDetail(clickEventDetail clickDetail){
        this.onClickDetail = clickDetail;
    }
    private Context context;

    public EventHomeAdapter(List<DetailEventList> eventList, IOnClickItemListener iOnClickItem, Context context) {
        this.eventList = eventList;
        this.iOnClickItem = iOnClickItem;
        this.context = context;
    }

    public void setData(List<DetailEventList> detailEventLists) {
        eventList = detailEventLists;
        eventListOld = detailEventLists;
    }

    public interface IOnClickItemListener {
        void onClickItem(long idToanBoSuKien);
    }

    public interface clickEventDetail{
        void onClick(int position);
    }

    @NonNull
    @Override
    public EventHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemRcvEvent1Binding itemBinding = ItemRcvEvent1Binding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        TabItemBinding tabItemBinding = TabItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EventHomeViewHolder(itemBinding, tabItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHomeViewHolder holder, int position) {
        DetailEventList detailList = eventList.get(position);
       List<DetailEvent> events = detailList.getSukien();
       DetailEvent detailEvent = events.get(position);

           holder.itemBinding.tvTenSuKien.setText(detailEvent.getTenSuKien());
           holder.itemBinding.tvUserName.setText(detailEvent.getTenNam());
           holder.itemBinding.tvCommentNumber.setText(String.valueOf(detailEvent.getCountComment()));
           holder.itemBinding.tvEventDetail.setText(detailEvent.getNoiDungSuKien());
           holder.itemBinding.tvViewNumber.setText(String.valueOf(detailEvent.getCountView()));
           holder.itemBinding.tvDate.setText(detailEvent.getRealTime());
        if (detailEvent.getLinkNamGoc() != null && !detailEvent.getLinkNamGoc().isEmpty() && detailEvent.getLinkNuGoc() != null && !detailEvent.getLinkNuGoc().isEmpty()) {
            Glide.with(holder.itemView.getContext()).load(detailEvent.getLinkNamGoc()).error(R.drawable.baseline_account_circle_24).into(holder.itemBinding.avatarImageView);
//            Glide.with(holder.itemView.getContext()).load(comment.getLinkNuGoc()).error(R.drawable.baseline_account_circle_24).into(holder.itemCommentBinding.imageAvatar2);
        }
        else if (urlImgFemale != null && !urlImgFemale.isEmpty() && urlImgMale != null && !urlImgMale.isEmpty()) {
            Glide.with(holder.itemView.getContext()).load(urlImgMale).error(R.drawable.baseline_account_circle_24).into(holder.itemBinding.avatarImageView);
//            Glide.with(holder.itemView.getContext()).load(urlImgFemale).error(R.drawable.baseline_account_circle_24).into(holder.itemCommentBinding.imageAvatar2);
        }
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(EventonClickDetail != null){
                       EventonClickDetail.onClickItem();
                       Log.d("position", "onClick: ");
                   }
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

        private final ItemRcvEvent1Binding itemBinding;

        public EventHomeViewHolder(ItemRcvEvent1Binding itemBinding, TabItemBinding tabItem) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }

}
