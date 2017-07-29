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
import com.post.wall.wallpostapp.*;
import com.post.wall.wallpostapp.model.PostComment;
import com.post.wall.wallpostapp.model.PostCommentsListModel;
import com.post.wall.wallpostapp.model.PostLikeModel;
import com.post.wall.wallpostapp.model.User;
import com.post.wall.wallpostapp.utility.CircleImageView;
import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.GetUrlClass;
import com.post.wall.wallpostapp.utility.RestInterface;
import com.post.wall.wallpostapp.utility.Utility;
import com.post.wall.wallpostapp.utility.checkInternetisAvailable;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PostCommentListAdapter extends RecyclerView.Adapter<PostCommentListAdapter.ViewHolder>{
//
    PostCommentsListModel postCommentsListModel;
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

    public PostCommentListAdapter(Activity ctx, PostCommentsListModel likePostUserListModel) {
        super();
        this.ctx = ctx;
        this.postCommentsListModel = likePostUserListModel;

        restInterface = Utility.getRetrofitInterface();
//        url = new GetUrlClass();
//        adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
//        restInterface = adapter.create(RestInterface.class);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgUserImage;
        TextView txtUsername, txtTime, txtComment, txtLikeCount, txtReplyCount;
        ImageView imgCommentimage, imgLikeUnlike;
        LinearLayout layoutTotalLike, layoutTotalReply;
        ProgressBar progress_like;

        public ViewHolder(View itemView) {
            super(itemView);
            imgUserImage = (CircleImageView) itemView.findViewById(R.id.imgUserImage);
            txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtComment = (TextView) itemView.findViewById(R.id.txtComment);
            imgCommentimage = (ImageView) itemView.findViewById(R.id.imgCommentimage);
            txtLikeCount = (TextView) itemView.findViewById(R.id.txtLikeCount);
            txtReplyCount = (TextView) itemView.findViewById(R.id.txtReplyCount);
            layoutTotalLike = (LinearLayout) itemView.findViewById(R.id.layoutTotalLike);
            layoutTotalReply = (LinearLayout) itemView.findViewById(R.id.layoutTotalReply);
            progress_like = (ProgressBar) itemView.findViewById(R.id.progress_like);
            imgLikeUnlike = (ImageView) itemView.findViewById(R.id.imgLikeUnlike);
        }
    }

    @Override
    public int getItemCount() {
        return (postCommentsListModel == null || postCommentsListModel.getComments() == null) ? 0 : postCommentsListModel.getComments().size();
    }

    @Override
    public long getItemId(int position) {
        return postCommentsListModel.getComments().indexOf(getItemId(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_comment_list_row, viewGroup, false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        try {
            com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            imageLoader.displayImage(postCommentsListModel.getComments().get(position).getPostcomment().getProfilePic(), viewHolder.imgUserImage, doption);
            viewHolder.imgUserImage.setTag(position);
            viewHolder.imgUserImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = Integer.parseInt(v.getTag().toString());
                    startUserProfile(postCommentsListModel.getComments().get(itemPosition).getPostcomment());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.txtUsername.setText(postCommentsListModel.getComments().get(position).getPostcomment().getFirstName() + " " + postCommentsListModel.getComments().get(position).getPostcomment().getLastName());
        viewHolder.txtTime.setText(Utility.getLeftDayTime(postCommentsListModel.getComments().get(position).getPostcomment().getPostedDate()));
        if(postCommentsListModel.getComments().get(position).getPostcomment().getComment().length() > 0)
            viewHolder.txtComment.setVisibility(View.VISIBLE);
        else
            viewHolder.txtComment.setVisibility(View.GONE);
        viewHolder.txtComment.setText(postCommentsListModel.getComments().get(position).getPostcomment().getComment());

        if(postCommentsListModel.getComments().get(position).getPostcomment().getCommentImage() != null &&  !postCommentsListModel.getComments().get(position).getPostcomment().getCommentImage().equalsIgnoreCase("")){
            viewHolder.imgCommentimage.setVisibility(View.VISIBLE);
            try {
                com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
                imageLoader.displayImage(postCommentsListModel.getComments().get(position).getPostcomment().getCommentImage(), viewHolder.imgCommentimage, doption);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            viewHolder.imgCommentimage.setVisibility(View.GONE);
        }

        viewHolder.imgLikeUnlike.setImageDrawable(postCommentsListModel.getComments().get(position).getPostcomment().isLike() ? ctx.getResources().getDrawable(R.drawable.like_total) : ctx.getResources().getDrawable(R.drawable.like_disactive));
        viewHolder.txtLikeCount.setTextColor(postCommentsListModel.getComments().get(position).getPostcomment().isLike() ? ctx.getResources().getColor(R.color.colorPrimary) : ctx.getResources().getColor(R.color.grey_time));
        viewHolder.txtLikeCount.setText(postCommentsListModel.getComments().get(position).getPostcomment().getLikeCount() + "");

        viewHolder.txtReplyCount.setText(postCommentsListModel.getComments().get(position).getPostcomment().getReplyCount() + "");

        viewHolder.layoutTotalLike.setTag(position);
        viewHolder.layoutTotalLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = Integer.parseInt(v.getTag().toString());
                viewHolder.progress_like.setVisibility(View.VISIBLE);
                likePost(postCommentsListModel.getComments().get(position).getPostcomment().getPostCommentId(), position, viewHolder);
            }
        });

        viewHolder.layoutTotalReply.setTag(position);
        viewHolder.layoutTotalReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, CommentReplyListActivity.class);
//                intent.putExtra("PostComment", new Gson().toJson(postCommentsListModel.getComments().get(position)));
                CommentReplyListActivity.comment = postCommentsListModel.getComments().get(position);
                ctx.startActivityForResult(intent, Constants.REPLY_POST);
                ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });


    }

    public void startUserProfile(PostComment postComment){
        User user = new User(postComment.getUserId(), postComment.getEmail(), postComment.getFirstName(), postComment.getLastName(), postComment.getBirthDate(), postComment.getProfilePic(), postComment.getContact(), postComment.isPublic());
        Intent intent = new Intent(ctx, UserProfileActivity.class);
        intent.putExtra("User", new Gson().toJson(user));
        ctx.startActivity(intent);
        ctx.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void likePost(int postId, final int position, final ViewHolder viewHolder) {
        restInterface.postCommentLikeUnlike(HomeActivity.user.getUser().getUserId(), postId, new Callback<PostLikeModel>() {
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
                    postCommentsListModel.getComments().get(position).getPostcomment().setLike(true);
                    postCommentsListModel.getComments().get(position).getPostcomment().setLikeCount(postCommentsListModel.getComments().get(position).getPostcomment().getLikeCount() + 1);
                    notifyDataSetChanged();
                } if (code == 1) {
                    postCommentsListModel.getComments().get(position).getPostcomment().setLike(false);
                    postCommentsListModel.getComments().get(position).getPostcomment().setLikeCount(postCommentsListModel.getComments().get(position).getPostcomment().getLikeCount() - 1);
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(ctx, postLikeModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



}
