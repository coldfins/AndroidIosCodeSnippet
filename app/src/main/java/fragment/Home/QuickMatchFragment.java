package fragment.Home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.daprlabs.cardstack.SwipeDeck;
import com.dating.coolmatch.R;

import java.util.ArrayList;

import Model.LikeUnlike.GetLikeUnlikeModel;
import Model.QuickMatch.GetQuickMatchModel;
import Model.QuickMatch.QuickMatchedUsersListModel;
import adapter.QuickMatch.SwipeDeckAdapter;
import interfaces.RestInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.CheckInternetisAvailable;
import utils.GetUrl;
import utils.Util;

public class QuickMatchFragment extends Fragment {

    private View rootView;
    private GetUrl url = new GetUrl();
    public static SwipeDeck cardStack;
    private LinearLayout cvNoData;
    private ProgressBar progress;
    private RelativeLayout relative_container;
    private int progressCount = 0;
    ArrayList<QuickMatchedUsersListModel> model = new ArrayList<QuickMatchedUsersListModel>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
                " Quick Match ");
        rootView = inflater.inflate(R.layout.quickmatch_fragment_layout, container,
                false);
        setHasOptionsMenu(true);
        setView();
        return rootView;
    }

    @SuppressWarnings("unchecked")
    public void setView() {

        progress = (ProgressBar) rootView.findViewById(R.id.progress);
        relative_container = (RelativeLayout) rootView.findViewById(R.id.relative_container);

        cardStack = (SwipeDeck) rootView.findViewById(R.id.swipe_deck);
        cardStack.setHardwareAccelerationEnabled(true);

        cvNoData = (LinearLayout) rootView.findViewById(R.id.matchesCardNoData);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                getMatches(66);
            }
        }, 2000);
    }

    private void getMatches(int id) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url.getUrl()).setLogLevel(RestAdapter.LogLevel.FULL).build();

        RestInterface restInterface = adapter
                .create(RestInterface.class);
        restInterface.getQuickMatches(id, new Callback<GetQuickMatchModel>() {
            @Override
            public void success(GetQuickMatchModel getMatchesModel, Response response) {
                if (progress.isShown()) {
                    relative_container.setVisibility(View.GONE);
                }

                if (getMatchesModel.getErrorCode() == 0) {
                    Log.d("TTT", "Progress " + progressCount);
                    model.clear();
                    model.addAll(getMatchesModel.getMatchedUsersList());
                    Log.d("TTT", "Size of array in quick match : " + model.size());
                    final SwipeDeckAdapter adapter = new SwipeDeckAdapter(model, getActivity());
                    cardStack.setAdapter(adapter);

                    cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
                        @Override
                        public void cardSwipedLeft(int position) {
                            Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
                        }

                        @Override
                        public void cardSwipedRight(int position) {
                            Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
                            int Id = model.get(position).getUserId();

                            likeUnlike(Id, 66);
                        }

                        @Override
                        public void cardsDepleted() {
                            Log.i("MainActivity", "no more cards");
                            cvNoData.setVisibility(View.VISIBLE);
                            cardStack.setVisibility(View.GONE);
                        }

                        @Override
                        public void cardActionDown() {

                        }

                        @Override
                        public void cardActionUp() {

                        }
                    });

                    cardStack.setLeftImage(R.id.left_image);
                    cardStack.setRightImage(R.id.right_image);

                } else if (getMatchesModel.getErrorCode() == 2) {
                    cvNoData.setVisibility(View.VISIBLE);
                    cardStack.setVisibility(View.GONE);
                } else {
                    cvNoData.setVisibility(View.VISIBLE);
                    cardStack.setVisibility(View.GONE);
                    Util.showAlert(getActivity(), getMatchesModel.getMsg() + "");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (progress.isShown()) {
                    relative_container.setVisibility(View.GONE);
                }

                try {
                    boolean IsInternetConnected = CheckInternetisAvailable.getInstance(getActivity()).check_internet();
                    String message = error.getMessage();
                    if (IsInternetConnected) {
                        String msg = "";
                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Util.showAlert(getActivity(), "Error: " + msg);
                        }
                    } else {
                        Util.showAlert(getActivity(), Util.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

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
                    boolean IsInternetConnected = CheckInternetisAvailable.getInstance(getActivity()).check_internet();
                    String message = error.getMessage();
                    if (IsInternetConnected) {
                        String msg = "";

                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Util.showAlert(getActivity(), "Error: " + msg);
                        }
                    } else {
                        Util.showAlert(getActivity(), Util.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
