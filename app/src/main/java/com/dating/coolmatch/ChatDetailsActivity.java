package com.dating.coolmatch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.Collections;

import Model.Chat.GetSendRevMsg;
import Model.Chat.SendMsgModel;
import Model.Chat.SendRevMessageModel;
import adapter.Chat.MessageConversionAdapter;
import interfaces.RestInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.CheckInternetisAvailable;
import utils.GetUrl;
import utils.Util;

public class ChatDetailsActivity extends AppCompatActivity implements SwipyRefreshLayout.OnRefreshListener{

    private Toolbar toolBar;
    private String Name = "null",Image="",nextFlag="";
    private int  Id, offset = 0, limit = 0, flag = 0,recordId=0,animationFlag=0;
    private RecyclerView rvChatConversion;
    private RecyclerView.Adapter msgAdapter;
    private SwipyRefreshLayout swipyRefreshLayoutChatConversion;
    private ArrayList<SendRevMessageModel>  chatModel = new ArrayList<>();
    private GetUrl url = new GetUrl();
    private ProgressDialog mProgressDialog;
    private EditText edtMessageContent;
    private ImageView imgSendMessage;
    private SendRevMessageModel setMesageValue;
    private LinearLayout matchesCardNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        setView();
    }

    private void setView() {

        //send message
        imgSendMessage=(ImageView)findViewById(R.id.imgSendMessage);
        imgSendMessage.setOnClickListener(sendMsg);
        edtMessageContent=(EditText)findViewById(R.id.edtMessageContent);

        //all control
        rvChatConversion = (RecyclerView) findViewById(R.id.rvChatConversion);
        swipyRefreshLayoutChatConversion=(SwipyRefreshLayout)findViewById(R.id.swipyRefreshLayoutChatConversion);
        swipyRefreshLayoutChatConversion.setOnRefreshListener(this);
        Id = Integer.parseInt(getIntent().getStringExtra("chatUserId"));
        Name = getIntent().getStringExtra("chatUserName");
        Image=getIntent().getStringExtra("chatUserImg");
        Log.d("TTT", Id + " " + Name);
        setupToolbar();
        getConversation(offset, limit, nextFlag, recordId);

        //no data linear
        matchesCardNoData=(LinearLayout)findViewById(R.id.matchesCardNoData);

        //msg text
        edtMessageContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (animationFlag == 0) {
                        Animation myFadeInAnimation = AnimationUtils.loadAnimation(ChatDetailsActivity.this, R.anim.fadeout);
                        imgSendMessage.startAnimation(myFadeInAnimation);
                        animationFlag = 1;
                    }
                    imgSendMessage.setImageResource(R.mipmap.sendpink_big);
                } else {
                    animationFlag = 0;
                    if (animationFlag == 0) {
                        Animation myFadeInAnimation = AnimationUtils.loadAnimation(ChatDetailsActivity.this, R.anim.fadein);
                        imgSendMessage.startAnimation(myFadeInAnimation);
                        animationFlag = 1;
                    }
                    imgSendMessage.setImageResource(R.mipmap.send_big);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getConversation(final int offsett,int limit,String flagg,int lastId) {
        mProgressDialog = ProgressDialog.show(ChatDetailsActivity.this, "", "", true);
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
        restInterface.receiveMessages(66, Id, limit, offsett, flagg, lastId, new Callback<GetSendRevMsg>() {
            @Override
            public void success(GetSendRevMsg getMatchesModel, Response response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (getMatchesModel.getErrorCode() == 0) {

                    if (flag == 0) {
                        Collections.reverse(getMatchesModel.getMessages());
                        chatModel.addAll(getMatchesModel.getMessages());
                        Log.d("TTT", chatModel.size() + " //flag=0 //" + chatModel.get(chatModel.size() - 1).getMessage());
                        if (chatModel.size() > 0) {
                            matchesCardNoData.setVisibility(View.GONE);
                            rvChatConversion.setVisibility(View.VISIBLE);
                            Log.d("TTT", chatModel.size() + "");
                            msgAdapter = new MessageConversionAdapter(chatModel, getApplicationContext(), Image);
                            LinearLayoutManager chatLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvChatConversion.setLayoutManager(chatLayoutManager);
                            rvChatConversion.setAdapter(msgAdapter);
                            rvChatConversion.scrollToPosition(chatModel.size() - 1);
                            Log.d("TTT", "retrofit if 0: " + flag);
                        } else {
                            matchesCardNoData.setVisibility(View.VISIBLE);
                            rvChatConversion.setVisibility(View.GONE);
                            swipyRefreshLayoutChatConversion.setRefreshing(false);
                        }
                    } else if (flag == 1) {
                        Collections.reverse(getMatchesModel.getMessages());
                        chatModel.addAll(getMatchesModel.getMessages());
                        Log.d("TTT", chatModel.size() + " //flag=1 //" + chatModel.get(chatModel.size() - 1).getMessage());
                        rvChatConversion.scrollToPosition(chatModel.size() - 1);
                        msgAdapter.notifyDataSetChanged();
                    }
                    swipyRefreshLayoutChatConversion.setRefreshing(false);
                } else {
                    swipyRefreshLayoutChatConversion.setRefreshing(false);
                    Util.showAlert(ChatDetailsActivity.this, getMatchesModel.getMsg() + "");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                swipyRefreshLayoutChatConversion.setRefreshing(false);
                try {
                    boolean IsInternetConnected = CheckInternetisAvailable.getInstance(ChatDetailsActivity.this).check_internet();
                    String message = error.getMessage();
                    if (IsInternetConnected) {
                        String msg = "";
                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Util.showAlert(ChatDetailsActivity.this, "Error: " + msg);
                        }
                    } else {
                        Util.showAlert(ChatDetailsActivity.this, Util.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private View.OnClickListener sendMsg=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(edtMessageContent.getText().toString().equals(""))
            {
                Toast.makeText(getApplicationContext(),"Please write something",Toast.LENGTH_SHORT).show();
            }
            else
            {
                sendMessage(edtMessageContent.getText().toString());
            }
        }
    };

    private void sendMessage(final String msg) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url.getUrl()).setLogLevel(RestAdapter.LogLevel.FULL).build();

        RestInterface restInterface = adapter
                .create(RestInterface.class);
        restInterface.sendMessage(66, Id, msg, new Callback<SendMsgModel>() {
            @Override
            public void success(SendMsgModel getMatchesModel, Response response) {

                if (getMatchesModel.getErrorCode() == 0) {
                    edtMessageContent.setText("");
                    setMesageValue = new SendRevMessageModel();
                    setMesageValue.setMessageId(getMatchesModel.getMessage().getMessageId());
                    setMesageValue.setMessage(msg);
                    setMesageValue.setReceiverId(getMatchesModel.getMessage().getReceiverId());
                    setMesageValue.setSenderId(getMatchesModel.getMessage().getSenderId());
                    setMesageValue.setSendRcvFlag("Send");
                    setMesageValue.setViewFlag(getMatchesModel.getMessage().getViewFlag());
                    setMesageValue.setPostedDate(getMatchesModel.getMessage().getPostedDate());
                    chatModel.add(setMesageValue);

                    Log.d("TTT","Chat model size: "+chatModel.size());
                    if(chatModel.size()==1)
                    {
                        msgAdapter = new MessageConversionAdapter(chatModel, getApplicationContext(), Image);
                        LinearLayoutManager chatLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvChatConversion.setLayoutManager(chatLayoutManager);
                        rvChatConversion.setAdapter(msgAdapter);
                        rvChatConversion.scrollToPosition(chatModel.size() - 1);
                    }
                    else {
                        if (msgAdapter != null) {
                            rvChatConversion.scrollToPosition(chatModel.size() - 1);
                            msgAdapter.notifyDataSetChanged();

                        }
                    }

                } else {
                    Util.showAlert(ChatDetailsActivity.this, getMatchesModel.getMsg() + "");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    boolean IsInternetConnected = CheckInternetisAvailable.getInstance(ChatDetailsActivity.this).check_internet();
                    String message = error.getMessage();
                    if (IsInternetConnected) {
                        String msg = "";
                        if (message != null) {
                            msg = message;
                        } else {
                            msg = "Server Problem.";
                        }

                        if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                            Util.showAlert(ChatDetailsActivity.this, "Error: " + msg);
                        }
                    } else {
                        Util.showAlert(ChatDetailsActivity.this, Util.NO_INTERNET_MESSAGE);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }


    public void setupToolbar() {
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        toolBar.setTitleTextColor(Color.WHITE);
        toolBar.setTitle(Name + "");
        Util.setWhiteToolBar(toolBar, getApplicationContext(), getResources().getColor(R.color.white));
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            intent.putExtra("MESSAGE", "CallApi");
            setResult(123, intent);
            finish();
            overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent();
        intent.putExtra("MESSAGE", "CallApi");
        setResult(123, intent);
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }



    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            if(chatModel.size()>0 && chatModel!=null )
            {
                flag=1;
                nextFlag="next";
                recordId = chatModel.get(chatModel.size()-1).getMessageId();
                Log.d("TTT", "Swipe: "+flag);
                limit=0;
                offset=0;
                getConversation(offset,limit,nextFlag,recordId);
            }
        }
        else
        {
            flag=0;
            Log.d("TTT", "Swipe: "+flag);
            limit=0;
            nextFlag="";
            recordId=0;
            swipyRefreshLayoutChatConversion.setRefreshing(false);
        }
    }
}