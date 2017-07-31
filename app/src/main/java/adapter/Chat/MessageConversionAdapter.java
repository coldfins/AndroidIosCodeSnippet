package adapter.Chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.dating.coolmatch.R;

import java.util.List;

import Model.Chat.SendRevMessageModel;
import utils.Util;

public class MessageConversionAdapter extends RecyclerView.Adapter<MessageConversionAdapter.MyViewHolder> {
    Context context;
    String img;
    private List<SendRevMessageModel> commonMessageModelList;

    public MessageConversionAdapter(List<SendRevMessageModel> commonMessageModelList, Context context,String img) {
        this.commonMessageModelList = commonMessageModelList;
        this.context = context;
        this.img=img;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_chat, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (commonMessageModelList.get(position).getSendRcvFlag().equalsIgnoreCase("Receive")) {
            holder.linearToUser.setVisibility(View.VISIBLE);
            holder.linearFromUser.setVisibility(View.GONE);
            holder.tvConversionToUser.setText(commonMessageModelList.get(position).getMessage());
            holder.tvToUserTime.setText(Util.getLeftTimeMessage(commonMessageModelList.get(position).getPostedDate()));

            if(!img.equals("")) {
                Picasso.with(context).load(img).placeholder(R.mipmap.default_user)
                        .error(R.mipmap.default_user).fit().centerCrop().into(holder.ivUserPic);
            }
        } else if (commonMessageModelList.get(position).getSendRcvFlag().equalsIgnoreCase("Send")) {
            holder.linearToUser.setVisibility(View.GONE);
            holder.linearFromUser.setVisibility(View.VISIBLE);
            holder.tvConversionFromUser.setText(commonMessageModelList.get(position).getMessage());
            holder.tvFromUserTime.setText(Util.getLeftTimeMessage(commonMessageModelList.get(position).getPostedDate()));
        }
    }
    @Override
    public int getItemCount() {
        return commonMessageModelList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvConversionFromUser, tvConversionToUser,tvFromUserTime,tvToUserTime;
        ImageView ivUserPic;
        LinearLayout linearToUser,linearFromUser;

        public MyViewHolder(View view) {
            super(view);
            ivUserPic=(ImageView)view.findViewById(R.id.ivUserPic);
            tvConversionFromUser = (TextView) view.findViewById(R.id.tvFromUserConversion);
            tvConversionToUser = (TextView) view.findViewById(R.id.tvToUserConversion);
            tvFromUserTime= (TextView) view.findViewById(R.id.tvFromUserConversionTime);
            tvToUserTime= (TextView) view.findViewById(R.id.tvToConversionTime);
            linearToUser= (LinearLayout) view.findViewById(R.id.linearToUser);
            linearFromUser= (LinearLayout) view.findViewById(R.id.linearFromUser);

        }
    }
}