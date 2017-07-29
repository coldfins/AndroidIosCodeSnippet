package com.medical.doctfin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.Circle;
import com.medical.adapter.SelectSpecialistAdapter;
import com.medical.model.SpecialistCategoryListModel;
import com.medical.model.SpecialistCategoryResponse;
import com.medical.ratrofitinterface.DoctFin;
import com.medical.utils.GetAllUrl;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SelectSpecialistTypeActivity extends ActionBarActivity implements
		RecyclerView.OnItemTouchListener {
	private Toolbar displayToolbar;
	private ArrayList<SpecialistCategoryListModel> restSpecialistCategoryListModel;
	private RecyclerView specialistCatRecyleView;
	private CardView specialistTypeCardView;
	private OnItemClickListener mListener;
	private GestureDetector mGestureDetector;
	private String categoryIdString, categoryNameString, categorySubNameString,
			subCatId;
	private GetAllUrl url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_specialist_type);
		restSpecialistCategoryListModel = new ArrayList<SpecialistCategoryListModel>();
		setToolBar();
		setResources();
		setRestAdapter();
	}

	public SelectSpecialistTypeActivity() {
		super();
	}

	public SelectSpecialistTypeActivity(Context context,
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_specialist_type, menu);
		return true;
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

	public static interface OnItemClickListener {
		public void onItemClick(View view, int position);

		public void onItemLongClick(View view, int position);
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
		try {
			specialistCatRecyleView = (RecyclerView) findViewById(R.id.specialistCatRecyleView);
			specialistTypeCardView = (CardView) findViewById(R.id.specialistTypeCardView);
			specialistCatRecyleView.setHasFixedSize(true);
			url = new GetAllUrl();
			LinearLayoutManager llm = new LinearLayoutManager(this);
			specialistCatRecyleView.setLayoutManager(llm);
			specialistCatRecyleView
					.addOnItemTouchListener(new SelectSpecialistTypeActivity(
							getApplicationContext(),
							specialistCatRecyleView,
							new SelectSpecialistTypeActivity.OnItemClickListener() {

								@Override
								public void onItemLongClick(View view,
										int position) {

								}

								@Override
								public void onItemClick(View view, int position) {
									// TODO Auto-generated method stub
									// CardView specialistCategoryCardView =
									// (CardView) view
									// .findViewById(R.id.specialistCategoryCardView);
									TextView specialistTypeTextView = (TextView) view
											.findViewById(R.id.specialistTypeTextView);
									TextView selectSpecialistIdTextView = (TextView) view
											.findViewById(R.id.specialistTypeIdTextView);
									categoryIdString = selectSpecialistIdTextView
											.getText().toString();
									categoryNameString = specialistTypeTextView
											.getText().toString();

									Intent intent = new Intent(
											getBaseContext(),
											SelectSpecialistSubCatActivity.class);
									intent.putExtra("catId",
											selectSpecialistIdTextView
													.getText().toString());

									startActivityForResult(intent, 11);

								}
							}));

		} catch (Exception ex) {
			Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	private void setRestAdapter() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				url.getUrl()).build();
		DoctFin doctFinInterFace = restAdapter.create(DoctFin.class);
		final ProgressDialog mProgressDialog = ProgressDialog.show(this, "",
				"", true);

		mProgressDialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		mProgressDialog.setContentView(R.layout.dialog);

		ProgressBar progressBar = (ProgressBar) mProgressDialog
				.findViewById(R.id.progressWheel);
		Circle circle = new Circle();

		circle.setColor(getResources().getColor(R.color.white));
		progressBar.setIndeterminateDrawable(circle);
		doctFinInterFace
				.getSpecialistCategoryList(new Callback<SpecialistCategoryResponse>() {

					@Override
					public void success(SpecialistCategoryResponse model,
							Response response) {
						Log.i("TAB1",
								model.getErrorCode() + " " + model.getMsg());
						if (model.getErrorCode() == 0) {
							if (model.getCategoryList().size() != 0) {

								restSpecialistCategoryListModel.addAll(model
										.getCategoryList());
								specialistCatRecyleView
										.setVisibility(View.VISIBLE);
								SelectSpecialistAdapter selectSpecialistAdapter = new SelectSpecialistAdapter(
										restSpecialistCategoryListModel);
								specialistCatRecyleView
										.setAdapter(selectSpecialistAdapter);
							} else {
								specialistTypeCardView
										.setVisibility(View.VISIBLE);
							}
						}
						if (mProgressDialog.isShowing())
							mProgressDialog.dismiss();
					}

					@Override
					public void failure(RetrofitError error) {
						// TODO Auto-generated method stb
						Log.i("error", error.toString());
						// if (mProgressDialog.isShowing())
						// mProgressDialog.dismiss();
					}
				});
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		finish();
		super.onBackPressed();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 11) {
			if (data != null) {
				categorySubNameString = data.getStringExtra("catSubName");
				subCatId = data.getStringExtra("catSubId");

				Intent msgintent = new Intent();
				msgintent.putExtra("catName", categoryNameString);
				msgintent.putExtra("subCatName", categorySubNameString);
				msgintent.putExtra("catId", categoryIdString);
				msgintent.putExtra("subCatId", subCatId);
				setResult(10, msgintent);
				finish();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);

		// if (requestCode == 3) {
		// if (data != null) {
		// categorySubNameString = data.getStringExtra("catSubName");
		//
		// Log.e("data", data + " **");
		// }
		// }
	}

}
