package com.medical.doctfin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.medical.adapter.SelectSubSpecialistAdapter;
import com.medical.model.SpecialistSubCategoryModel;
import com.medical.model.SpecialistSubcatResponse;
import com.medical.ratrofitinterface.DoctFin;
import com.medical.utils.GetAllUrl;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SelectSpecialistSubCatActivity extends ActionBarActivity implements RecyclerView.OnItemTouchListener {
	private Toolbar displayToolbar;
	private RecyclerView specialistSubCatRecyleView;
	private ArrayList<SpecialistSubCategoryModel> restSpecialistSubCatArrayList;
	private CardView specialistSubTypeCardView;
	private OnItemClickListener mListener;
	private GestureDetector mGestureDetector;
	private String categoryId, categorySubCatNameString, catName, catSubId;
	private GetAllUrl url;

	public static interface OnItemClickListener {
		public void onItemClick(View view, int position);

		public void onItemLongClick(View view, int position);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_specialist_sub_cat);
		getIntentIfAvailable();
		setResources();
		setToolBar();
		setRestAdapter();
		Log.i("TAB21", "subcreate");
	}

	public SelectSpecialistSubCatActivity() {
		super();
	}

	public SelectSpecialistSubCatActivity(Context context,
			final RecyclerView recyclerView, OnItemClickListener listener) {

		super();
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

	private void setToolBar() {
		displayToolbar = (Toolbar) findViewById(R.id.displayToolBar);
		displayToolbar.setTitleTextColor(Color.WHITE);
		displayToolbar.setTitle("SelectSpecialist");
		displayToolbar.setNavigationIcon(R.drawable.back);
		displayToolbar.setTitleTextColor(Color.WHITE);
		setSupportActionBar(displayToolbar);
	}

	private void setResources() {
		specialistSubCatRecyleView = (RecyclerView) findViewById(R.id.specialistSubCatRecyleView);
		specialistSubTypeCardView = (CardView) findViewById(R.id.specialistSubTypeCardView);
		specialistSubCatRecyleView.setHasFixedSize(true);
		url = new GetAllUrl();
		LinearLayoutManager llm = new LinearLayoutManager(this);
		specialistSubCatRecyleView.setLayoutManager(llm);
		specialistSubCatRecyleView
				.addOnItemTouchListener(new SelectSpecialistSubCatActivity(
						getApplicationContext(),
						specialistSubCatRecyleView,
						new SelectSpecialistSubCatActivity.OnItemClickListener() {

							@Override
							public void onItemLongClick(View view, int position) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onItemClick(View view, int position) {
								// TODO Auto-generated method stub
								CardView selectSubSpecialistCardView = (CardView) view
										.findViewById(R.id.specialistSubTypeCardView);
								TextView selectSubSpecialistTextView = (TextView) view
										.findViewById(R.id.specialistSubTypeTextView);
								TextView selectSubSpecialistIdTextView = (TextView) view
										.findViewById(R.id.specialistSubTypeIdTextView);
								categorySubCatNameString = selectSubSpecialistTextView
										.getText().toString();
								catSubId = selectSubSpecialistIdTextView
										.getText().toString();

								Intent intent = new Intent();
								intent.putExtra("catSubName",
										categorySubCatNameString);
								intent.putExtra("catSubId", catSubId);
								setResult(11, intent);
								finish();
							}
						}));
		restSpecialistSubCatArrayList = new ArrayList<SpecialistSubCategoryModel>();

	}

	private void setRestAdapter() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				url.getUrl()).build();
		DoctFin doctFinInterFace = restAdapter.create(DoctFin.class);
		Log.i("TAB21", "RestAdapter");
		doctFinInterFace.getSubSpecialistSubCategoryList(
				Integer.parseInt(categoryId),
				new Callback<SpecialistSubcatResponse>() {

					@Override
					public void success(SpecialistSubcatResponse model,
							Response response) {
						// TODO Auto-generated method stub
						Log.i("TAB11", "Success" + model.getErrorCode() + " "
								+ model.getMsg());
						if (model.getErrorCode() == 0) {
							if (model.getSubCategoryList().size() != 0) {
								restSpecialistSubCatArrayList.addAll(model
										.getSubCategoryList());
								specialistSubCatRecyleView
										.setVisibility(View.VISIBLE);
								SelectSubSpecialistAdapter selectSubSpecialistAdapter = new SelectSubSpecialistAdapter(
										restSpecialistSubCatArrayList);
								specialistSubCatRecyleView
										.setAdapter(selectSubSpecialistAdapter);
							
							} else {
								specialistSubTypeCardView
										.setVisibility(View.VISIBLE);
							}
						}
					}

					@Override
					public void failure(RetrofitError error) {
						// TODO Auto-generated method stub
						Log.i("TAG4", "Error" + error.toString());
					}
				});
	}

	private void getIntentIfAvailable() {
		Intent intent = getIntent();

		if (intent.getExtras() != null) {
			categoryId = intent.getStringExtra("catId");
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		super.onBackPressed();
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
	public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

	}

}
