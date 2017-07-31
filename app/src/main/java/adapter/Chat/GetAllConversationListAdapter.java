package adapter.Chat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.dating.coolmatch.ChatDetailsActivity;
import com.dating.coolmatch.R;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import Model.Chat.MessageModel;
import Model.LikeUnlike.GetLikeUnlikeModel;
import interfaces.RestInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.CheckInternetisAvailable;
import utils.GetUrl;
import utils.Util;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class GetAllConversationListAdapter extends
        RecyclerView.Adapter<GetAllConversationListAdapter.ViewHolder> {

    ArrayList<MessageModel> newsItem;
    Activity ctx;
    private GetUrl url = new GetUrl();
    View view;
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    private boolean like;
    private ProgressDialog mProgressDialog;
    private MessageModel mathces;

    public GetAllConversationListAdapter(Activity ctx, ArrayList<MessageModel> newsItem) {
        super();
        this.ctx = ctx;
        this.newsItem = newsItem;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lvChat;
        TextView txtMsg, txtName, txtId, txtTime, txtTotalmsg;
        ImageView imgUser, imgCall, imgFav, imgChat;
        private SwipeLayout swipeLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            lvChat = (LinearLayout) itemView.findViewById(R.id.lvChat);
            txtMsg = (TextView) itemView.findViewById(R.id.txtUserMsg);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtName = (TextView) itemView.findViewById(R.id.txtUsername);
            txtId = (TextView) itemView.findViewById(R.id.txtId);
            imgUser = (ImageView) itemView.findViewById(R.id.ivUserPic);
            txtTotalmsg = (TextView) itemView.findViewById(R.id.txtTotalmsg);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            imgCall = (ImageView) itemView.findViewById(R.id.imgCall);
            imgFav = (ImageView) itemView.findViewById(R.id.imgFav);
            imgChat = (ImageView) itemView.findViewById(R.id.imgChat);
        }
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return newsItem.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return newsItem.indexOf(getItemId(position));

    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        // TODO Auto-generated method stub
        mathces = newsItem.get(i);
        viewHolder.txtId.setText(String.valueOf(mathces.getUserId()));
        viewHolder.txtName.setText(mathces.getUserName());
        viewHolder.txtMsg.setText(mathces.getMessageContent());
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
            }
        });


        if (mathces.getCounter().equals("0")) {
            viewHolder.txtTotalmsg.setVisibility(View.GONE);
        } else {
            viewHolder.txtTotalmsg.setVisibility(View.VISIBLE);
            viewHolder.txtTotalmsg.setText(mathces.getCounter());
        }
        viewHolder.txtTime.setText(Util.getLeftTime(mathces.getPostedDate()));
        viewHolder.lvChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i = new Intent(ctx, ChatDetailsActivity.class);
                i.putExtra("chatUserId", viewHolder.txtId.getText().toString());
                i.putExtra("chatUserName", viewHolder.txtName.getText().toString());
                i.putExtra("chatUserImg", mathces.getImage() + "");
                ctx.startActivityForResult(i, 123);
                ctx.overridePendingTransition(
                        R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(ctx));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.blank_matches)
                .showImageForEmptyUri(R.mipmap.blank_matches)
                .showImageOnLoading(R.mipmap.blank_matches)
                .showImageOnFail(R.mipmap.blank_matches).cacheOnDisc()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader.displayImage(mathces.getImage(), viewHolder.imgUser,
                options);

        viewHolder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.swipeLayout.close();
                String phnum = mathces.getContactNo() + "";
                if (phnum.equals("")) {

                    Toast.makeText(v.getContext(), "Sorry,This user is not provide the contact no.", Toast.LENGTH_SHORT).show();

                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phnum));

                    if (ContextCompat.checkSelfPermission(ctx,
                            Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(ctx, new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
                    } else {
                        v.getContext().startActivity(callIntent);
                    }
                }
            }
        });

        viewHolder.imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.swipeLayout.close();
                Intent i = new Intent(ctx, ChatDetailsActivity.class);
                i.putExtra("chatUserId", viewHolder.txtId.getText().toString());
                i.putExtra("chatUserName", viewHolder.txtName.getText().toString());
                i.putExtra("chatUserImg", mathces.getImage() + "");
                ctx.startActivityForResult(i, 123);
                ctx.overridePendingTransition(
                        R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });

        like = mathces.getLikeOrNot();
        Log.d("TTT", "Like: " + mathces.getLikeOrNot());
        if (like) {
            viewHolder.imgFav.setImageResource(R.mipmap.star_yellow);
            viewHolder.imgFav.setTag(R.mipmap.star_yellow);
        } else {
            viewHolder.imgFav.setImageResource(R.mipmap.star);
            viewHolder.imgFav.setTag(R.mipmap.star);
        }

        viewHolder.imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.swipeLayout.close();
                if (((Integer) viewHolder.imgFav.getTag()).intValue() == R.mipmap.star_yellow) {
                    viewHolder.imgFav.setImageResource(R.mipmap.star);
                    viewHolder.imgFav.setTag(R.mipmap.star);
                    likeUnlike(66, Integer.parseInt(viewHolder.txtId.getText().toString()));
                    newsItem.get(i).setLikeOrNot(false);
                    notifyItemChanged(i);

                } else if (((Integer) viewHolder.imgFav.getTag()).intValue() == R.mipmap.star) {
                    viewHolder.imgFav.setImageResource(R.mipmap.star_yellow);
                    viewHolder.imgFav.setTag(R.mipmap.star_yellow);
                    likeUnlike(66, Integer.parseInt(viewHolder.txtId.getText().toString()));
                    newsItem.get(i).setLikeOrNot(true);
                    notifyItemChanged(i);

                }
                Log.d("TTT", "Likeeeee " + 66 + " // " + mathces.getUserId() + "//" + viewHolder.txtId.getText().toString() + "//" + i);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        // TODO Auto-generated method stub
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.list_coversations_users, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        view = v;
        return viewHolder;
    }


    private void likeUnlike(int id, int myid) {
        mProgressDialog = ProgressDialog.show(ctx, "", "", true);
        mProgressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setContentView(R.layout.dialog);
        ProgressBar progressBar = (ProgressBar) mProgressDialog
                .findViewById(R.id.progressWheel);
        ThreeBounce circle = new ThreeBounce();
        circle.setColor(ctx.getResources().getColor(R.color.white));
        progressBar.setIndeterminateDrawable(circle);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url.getUrl()).setLogLevel(RestAdapter.LogLevel.FULL).build();

        RestInterface restInterface = adapter
                .create(RestInterface.class);
        restInterface.likeUnlike(id, myid, new Callback<GetLikeUnlikeModel>() {
            @Override
            public void success(GetLikeUnlikeModel getMatchesModel, Response response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.d("TTT", getMatchesModel.getMsg() + " " + getMatchesModel.getErrorCode());
            }

            @Override
            public void failure(RetrofitError error) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                try {
                    boolean IsInternetConnected = CheckInternetisAvailable.getInstance(ctx).check_internet();
                    String message = error.getMessage();
                    if (IsInternetConnected) {
                        String msg = "";
                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Util.showAlert(ctx, "Error: " + msg);
                        }
                    } else {
                        Util.showAlert(ctx, Util.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
