package adapter.QuickMatch;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dating.coolmatch.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import Model.LikeUnlike.GetLikeUnlikeModel;
import Model.QuickMatch.QuickMatchedUsersListModel;
import fragment.Home.QuickMatchFragment;
import interfaces.RestInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.CheckInternetisAvailable;
import utils.GetUrl;
import utils.Util;

public class SwipeDeckAdapter extends BaseAdapter {
    private ArrayList<QuickMatchedUsersListModel> data;
    private Context context;
    private int matchId;
    private GetUrl url = new GetUrl();

    public SwipeDeckAdapter(ArrayList<QuickMatchedUsersListModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.quich_match_user_layout, parent, false);
        }
        ((TextView) v.findViewById(R.id.tvUserName)).setText(data.get(position).getUserName());
        ((TextView) v.findViewById(R.id.tvUserAge)).setText(data.get(position).getAge() + " year");
        ImageView ivUserPic = (ImageView) v.findViewById(R.id.ivUserPic);
        ImageView ivLike = (ImageView) v.findViewById(R.id.ivClose);
        ImageView ivClose = (ImageView) v.findViewById(R.id.ivLike);

        matchId=data.get(position).getUserId();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.male_dafault)
                .showImageForEmptyUri(R.mipmap.male_dafault)
                .showImageOnLoading(R.mipmap.male_dafault)
                .showImageOnFail(R.mipmap.male_dafault).cacheOnDisc()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader.displayImage(data.get(position).getProfilePic(), ivUserPic,
                options);

        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickMatchFragment.cardStack.swipeTopCardLeft(200);
                likeUnlike(matchId,66);
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickMatchFragment.cardStack.swipeTopCardRight(200);
            }
        });

        return v;
    }

    private void likeUnlike(int id, int myid) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url.getUrl()).setLogLevel(RestAdapter.LogLevel.FULL).build();

        RestInterface restInterface = adapter
                .create(RestInterface.class);
        restInterface.addLikeForQuickMatch(id, myid, new Callback<GetLikeUnlikeModel>() {
            @Override
            public void success(GetLikeUnlikeModel getMatchesModel, Response response) {
                Log.d("TTT", getMatchesModel.getMsg() + " " + getMatchesModel.getErrorCode());
            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    boolean IsInternetConnected = CheckInternetisAvailable.getInstance(context).check_internet();
                    String message = error.getMessage();
                    if (IsInternetConnected) {
                        String msg = "";
                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Util.showAlert(context, "Error: " + msg);
                        }
                    } else {
                        Util.showAlert(context, Util.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}