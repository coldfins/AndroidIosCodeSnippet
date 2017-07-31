package fragment.Home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dating.coolmatch.R;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import Model.Chat.GetAllConveration;
import Model.Chat.MessageModel;
import adapter.Chat.GetAllConversationListAdapter;
import interfaces.RestInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.CheckInternetisAvailable;
import utils.GetUrl;
import utils.Util;

public class CommunicationFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener, RecyclerView.OnItemTouchListener, SearchView.OnQueryTextListener {

    private View rootView;
    private SwipyRefreshLayout swipyRefreshLayoutChatConversion;
    public static RecyclerView rvChat;
    private int limit = 10, offset = 0;
    private ProgressDialog mProgressDialog;
    private GetUrl url = new GetUrl();
    public static ArrayList<MessageModel> allMsgList = new ArrayList<MessageModel>();
    ;
    public static RecyclerView.Adapter chatAdapter;
    public static LinearLayoutManager chatLayoutManager;
    int flag = 0;
    private LinearLayout matchesCardNoData;
    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
                "Conversations");
        rootView = inflater.inflate(R.layout.converation_fragment_layout, container,
                false);
        setHasOptionsMenu(true);
        setView(rootView);
        return rootView;
    }

    public void setView(View rootView) {
        swipyRefreshLayoutChatConversion = (SwipyRefreshLayout) rootView.findViewById(R.id.swipyRefreshLayoutChatConverstion);
        swipyRefreshLayoutChatConversion.setOnRefreshListener(this);
        rvChat = (RecyclerView) rootView.findViewById(R.id.rvChatConversion);
        matchesCardNoData = (LinearLayout) rootView.findViewById(R.id.matchesCardNoData);
        Log.d("TTT", "offsettttt: " + offset + " " + limit);
        if (allMsgList.size() == 0) {
            getConversation(limit);
        } else {
            matchesCardNoData.setVisibility(View.GONE);
            rvChat.setVisibility(View.VISIBLE);
            Log.d("TTT", allMsgList.size() + " " + offset);
            chatAdapter = new GetAllConversationListAdapter(getActivity(), allMsgList);
            chatLayoutManager = new LinearLayoutManager(getActivity());
            rvChat.setLayoutManager(chatLayoutManager);
            rvChat.setAdapter(chatAdapter);
            Log.d("TTT", "retrofit if 0: " + flag);
        }

        rvChat.addOnItemTouchListener(new CommunicationFragment(
                getActivity(), rvChat,
                new CommunicationFragment.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                    }

                }));
    }

    private void getConversation(int limit) {
        mProgressDialog = ProgressDialog.show(getActivity(), "", "", true);
        mProgressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setContentView(R.layout.dialog);
        ProgressBar progressBar = (ProgressBar) mProgressDialog
                .findViewById(R.id.progressWheel);
        ThreeBounce circle = new ThreeBounce();
        circle.setColor(getResources().getColor(R.color.white));
        progressBar.setIndeterminateDrawable(circle);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url.getUrl()).setLogLevel(RestAdapter.LogLevel.FULL).build();

        RestInterface restInterface = adapter
                .create(RestInterface.class);
        restInterface.getAllConversation(66, limit, offset, new Callback<GetAllConveration>() {
            @Override
            public void success(GetAllConveration getMatchesModel, Response response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (getMatchesModel.getErrorCode() == 0) {

                    if (flag == 0) {
                        allMsgList.addAll(getMatchesModel.getMessages());
                        if (allMsgList.size() > 0) {
                            matchesCardNoData.setVisibility(View.GONE);
                            rvChat.setVisibility(View.VISIBLE);
                            offset = allMsgList.size();

                            Log.d("TTT", allMsgList.size() + " " + offset);
                            chatAdapter = new GetAllConversationListAdapter(getActivity(), allMsgList);
                            chatLayoutManager = new LinearLayoutManager(getActivity());
                            rvChat.setLayoutManager(chatLayoutManager);
                            rvChat.setAdapter(chatAdapter);
                            Log.d("TTT", "retrofit if 0: " + flag);
                        } else {

                            matchesCardNoData.setVisibility(View.VISIBLE);
                            rvChat.setVisibility(View.GONE);
                            swipyRefreshLayoutChatConversion.setRefreshing(false);
                        }
                    } else if (flag == 1) {
                        Log.d("TTT", "retrofit else 1: " + flag);
                        allMsgList.addAll(getMatchesModel.getMessages());
                        offset = allMsgList.size();
                        Log.d("TTT", "Else part " + allMsgList.size() + " " + offset);
                        chatAdapter.notifyDataSetChanged();

                    }
                    swipyRefreshLayoutChatConversion.setRefreshing(false);
                } else {
                    swipyRefreshLayoutChatConversion.setRefreshing(false);
                    Util.showAlert(getActivity(), getMatchesModel.getMsg() + "");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                swipyRefreshLayoutChatConversion.setRefreshing(false);
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

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            if (allMsgList.size() > 0 && allMsgList != null) {
                flag = 1;
                Log.d("TTT", "Swipe: " + flag);
                getConversation(limit);
            }

        } else {
            flag = 0;
            Log.d("TTT", "Swipe: " + flag);
            getConversation(limit);
        }
    }

    @SuppressLint("DefaultLocale")
    private ArrayList<MessageModel> filter(List<MessageModel> models,
                                           String query) {
        String textUser = null;
        query = query.toLowerCase();
        ArrayList<MessageModel> filteredModelList = new ArrayList<MessageModel>();
        if (query != "" && !query.equals("")) {
            for (MessageModel model : models) {
                textUser = model.getUserName().toLowerCase();
                if (textUser.contains(query)) {
                    filteredModelList.add(model);
                } else {
                    textUser = model.getUserName().toLowerCase();
                    if (textUser.contains(query)) {
                        filteredModelList.add(model);
                    }
                }
            }
        } else {
            filteredModelList = allMsgList;
        }
        return filteredModelList;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {

        // TODO Auto-generated method stub
        ArrayList<MessageModel> filteredModelList = filter(allMsgList,
                query);
        if (!query.trim().equals("") && query != null) {

            matchesCardNoData.setVisibility(View.GONE);
            chatAdapter = new GetAllConversationListAdapter(getActivity(), filteredModelList);
            chatLayoutManager = new LinearLayoutManager(getActivity());
            rvChat.setHasFixedSize(true);
            rvChat.setLayoutManager(chatLayoutManager);
            rvChat.setAdapter(chatAdapter);
            rvChat.scrollToPosition(0);

        } else {
            matchesCardNoData.setVisibility(View.GONE);
            chatAdapter = new GetAllConversationListAdapter(getActivity(),
                    filteredModelList);
            chatLayoutManager = new LinearLayoutManager(getActivity());
            rvChat.setHasFixedSize(true);
            rvChat.setLayoutManager(chatLayoutManager);
            rvChat.setAdapter(chatAdapter);
            rvChat.scrollToPosition(0);

        }
        if (filteredModelList.isEmpty()) {
            matchesCardNoData.setVisibility(View.VISIBLE);
        }
        return true;
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);
    }

    public CommunicationFragment() {
        super();
    }

    public CommunicationFragment(Context context, final RecyclerView recyclerView,
                                 OnItemClickListener listener) {
        mListener = listener;

        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(
                                e.getX(), e.getY());

                        if (childView != null && mListener != null) {
                            mListener.onItemLongClick(childView,
                                    recyclerView.getChildPosition(childView));
                        }
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.communication_fragment, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(this);
        EditText searchEdit = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));

        searchEdit.setHint("Enter Keyword");
        searchEdit.setHintTextColor(Color.WHITE);
        searchEdit.setTextSize(18);

        ((EditText) searchView
                .findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setHintTextColor(Color.WHITE);
        ((ImageView) searchView
                .findViewById(android.support.v7.appcompat.R.id.search_close_btn))
                .setImageResource(R.mipmap.close1);
        ((ImageView) searchView
                .findViewById(android.support.v7.appcompat.R.id.search_button))
                .setImageResource(R.mipmap.search);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if (childView != null && mListener != null
                && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
