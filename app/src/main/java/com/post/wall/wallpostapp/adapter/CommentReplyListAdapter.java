package com.post.wall.wallpostapp.adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.post.wall.wallpostapp.CommentReplyListActivity;
import com.post.wall.wallpostapp.HomeActivity;
import com.post.wall.wallpostapp.R;
import com.post.wall.wallpostapp.UserProfileActivity;
import com.post.wall.wallpostapp.model.Comment;
import com.post.wall.wallpostapp.model.PostComment;
import com.post.wall.wallpostapp.model.PostCommentReplyModel;
import com.post.wall.wallpostapp.model.PostCommentsListModel;
import com.post.wall.wallpostapp.model.PostLikeModel;
import com.post.wall.wallpostapp.model.User;
import com.post.wall.wallpostapp.utility.CircleImageView;
import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.GetUrlClass;
import com.post.wall.wallpostapp.utility.RestInterface;
import com.post.wall.wallpostapp.utility.Utility;
import com.post.wall.wallpostapp.utility.checkInternetisAvailable;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CommentReplyListAdapter extends RecyclerView.Adapter<CommentReplyListAdapter.ViewHolder>{
//
    Comment comment;
    Activity ctx;

    DisplayImageOptions doption = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.img_profile)
            .showImageOnFail(R.drawable.img_profile)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    RestInterface restInterface;
//    GetUrlClass url;
//    RestAdapter adapter;

    public CommentReplyListAdapter(Activity ctx, Comment comment) {
        super();
        this.ctx = ctx;
        this.comment = comment;

        restInterface = Utility.getRetrofitInterface();
//        url = new GetUrlClass();
//        adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
//        restInterface = adapter.create(RestInterface.class);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgUserImage;
        TextView txtUsername, txtTime, txtComment, txtLikeCount;//, txtReplyCount;
        ImageView imgCommentimage, imgLikeUnlike;
        LinearLayout layoutTotalLike;//, layoutTotalReply;
        ProgressBar progress_like;

        public ViewHolder(View itemView) {
            super(itemView);
            imgUserImage = (CircleImageView) itemView.findViewById(R.id.imgUserImage);
            txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtComment = (TextView) itemView.findViewById(R.id.txtComment);
            imgCommentimage = (ImageView) itemView.findViewById(R.id.imgCommentimage);
            txtLikeCount = (TextView) itemView.findViewById(R.id.txtLikeCount);
//            txtReplyCount = (TextView) itemView.findViewById(R.id.txtReplyCount);
            layoutTotalLike = (LinearLayout) itemView.findViewById(R.id.layoutTotalLike);
//            layoutTotalReply = (LinearLayout) itemView.findViewById(R.id.layoutTotalReply);
            progress_like = (ProgressBar) itemView.findViewById(R.id.progress_like);
            imgLikeUnlike = (ImageView) itemView.findViewById(R.id.imgLikeUnlike);
        }
    }

    @Override
    public int getItemCount() {
        return comment.getPostcommentreply().size();
    }

    @Override
    public long getItemId(int position) {
        return comment.getPostcommentreply().indexOf(getItemId(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_reply_list_row, viewGroup, false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        try {
            com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            imageLoader.displayImage(comment.getPostcommentreply().get(position).getProfilePic(), viewHolder.imgUserImage, doption);
            viewHolder.imgUserImage.setTag(position);
            viewHolder.imgUserImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = Integer.parseInt(v.getTag().toString());
                    startUserProfile(itemPosition);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.txtUsername.setText(comment.getPostcommentreply().get(position).getFirstName() + " " + comment.getPostcommentreply().get(position).getLastName());
        viewHolder.txtUsername.setTag(position);
        viewHolder.txtUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = Integer.parseInt(v.getTag().toString());
                startUserProfile(itemPosition);
            }
        });

        viewHolder.txtTime.setText(Utility.getLeftDayTime(comment.getPostcommentreply().get(position).getPostedDate()));
        viewHolder.txtComment.setText(comment.getPostcommentreply().get(position).getReply());

        if(comment.getPostcommentreply().get(position).getReplyImage() != null &&  !comment.getPostcommentreply().get(position).getReplyImage().equalsIgnoreCase("")){
            viewHolder.imgCommentimage.setVisibility(View.VISIBLE);
            try {
                com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
                imageLoader.displayImage(comment.getPostcommentreply().get(position).getReplyImage(), viewHolder.imgCommentimage, doption);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            viewHolder.imgCommentimage.setVisibility(View.GONE);
        }

        viewHolder.imgLikeUnlike.setImageDrawable(comment.getPostcommentreply().get(position).isLike() ? ctx.getResources().getDrawable(R.drawable.like_total) : ctx.getResources().getDrawable(R.drawable.like_disactive));
        viewHolder.txtLikeCount.setTextColor(comment.getPostcommentreply().get(position).isLike() ? ctx.getResources().getColor(R.color.colorPrimary) : ctx.getResources().getColor(R.color.grey_time));
        viewHolder.txtLikeCount.setText(comment.getPostcommentreply().get(position).getLikeCount() + "");

//        viewHolder.txtReplyCount.setText(comment.getPostcommentreply().get(position).getReplyCount() + "");

        viewHolder.layoutTotalLike.setTag(position);
        viewHolder.layoutTotalLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = Integer.parseInt(v.getTag().toString());
                viewHolder.progress_like.setVisibility(View.VISIBLE);
                likePost(comment.getPostcommentreply().get(position).getPostCommentReplyId(), position, viewHolder);
            }
        });

//        viewHolder.layoutTotalReply.setTag(position);
//        viewHolder.layoutTotalReply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int itemPosition = Integer.parseInt(v.getTag().toString());
//                Intent intent = new Intent(ctx, CommentReplyListActivity.class);
//                intent.putExtra("PostComment", new Gson().toJson(postCommentsListModel.getComments().get(position).getPostcomment()));
//                ctx.startActivity(intent);
//                ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//            }
//        });


    }

    public void startUserProfile(int itemPosition) {
        User user = new User(comment.getPostcommentreply().get(itemPosition).getUserId(), comment.getPostcommentreply().get(itemPosition).getEmail(), comment.getPostcommentreply().get(itemPosition).getFirstName(), comment.getPostcommentreply().get(itemPosition).getLastName(), comment.getPostcommentreply().get(itemPosition).getBirthDate(), comment.getPostcommentreply().get(itemPosition).getProfilePic(), comment.getPostcommentreply().get(itemPosition).getContact(), comment.getPostcommentreply().get(itemPosition).isPublic());
        Intent intent = new Intent(ctx, UserProfileActivity.class);
        intent.putExtra("User", new Gson().toJson(user));
        ctx.startActivity(intent);
        ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void likePost(int postId, final int position, final ViewHolder viewHolder) {
        restInterface.postCommentReplyLikeUnlike(HomeActivity.user.getUser().getUserId(), postId, new Callback<PostLikeModel>() {
            @Override
            public void failure(RetrofitError error) {
                viewHolder.progress_like.setVisibility(View.GONE);
                String message = error.getMessage();
                try {
                    boolean IsInternetConnected = checkInternetisAvailable.getInstance(ctx).check_internet();

                    Log.v("TTT", "SET ADAPTER");

                    if (IsInternetConnected) {
                        String msg = "";
                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Utility.showAlert(ctx, "Error: " + msg);
                        }
                    } else {
                        Utility.showAlert(ctx, Constants.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            }

            @Override
            public void success(PostLikeModel postLikeModel, Response arg1) {
                int code = postLikeModel.getError_code();
                viewHolder.progress_like.setVisibility(View.GONE);

                if (code == 0) {
                    comment.getPostcommentreply().get(position).setLike(true);
                    comment.getPostcommentreply().get(position).setLikeCount(comment.getPostcommentreply().get(position).getLikeCount() + 1);
                    notifyDataSetChanged();
                } if (code == 1) {
                    comment.getPostcommentreply().get(position).setLike(false);
                    comment.getPostcommentreply().get(position).setLikeCount(comment.getPostcommentreply().get(position).getLikeCount() - 1);
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(ctx, postLikeModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



}
