package com.medical.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.github.ybq.android.spinkit.style.Circle;
import com.medical.doctfin.AddReminderActivity;
import com.medical.doctfin.PatientAppointMentDetail;
import com.medical.doctfin.R;
import com.medical.model.DeleteAppointmentResponse;
import com.medical.model.PatientAppointmentModel;
import com.medical.ratrofitinterface.DoctFin;
import com.medical.utils.GetAllUrl;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

@SuppressLint("NewApi")
public class AppointmentAdapter extends RecyclerSwipeAdapter<AppointmentAdapter.AppointmentViewHolder> {
	private ArrayList<PatientAppointmentModel> patientAppointmentArrayList;
	private static Context context;

	private String docNameString, doctAddressString, doctAppDateTimeString;
	private Typeface font;

	private int pos, appointmentIdInt;
	private GetAllUrl url = new GetAllUrl();

	public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
		private CardView appointmentCardView;
		private TextView doctNameTextView, doctAddressTextView,
				appointmentTextView, cancelAppointmentTextView, remindTextView,
				clockImage, appointmentIdTextView;
		private ImageView doctImageView, regphotoImage, deleteImageView;
		private SwipeLayout swipeLayout;
		private LinearLayout rejectLinearLayout;

		public AppointmentViewHolder(View itemView) {
			super(itemView);
			swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
			rejectLinearLayout = (LinearLayout) itemView.findViewById(R.id.rejectLinearLayout);
			appointmentCardView = (CardView) itemView.findViewById(R.id.appointmentCardView);
			doctImageView = (ImageView) itemView.findViewById(R.id.doctImageView);
			doctNameTextView = (TextView) itemView.findViewById(R.id.doctNameTextView);
			doctAddressTextView = (TextView) itemView.findViewById(R.id.doctAddressTextView);
			appointmentTextView = (TextView) itemView.findViewById(R.id.appointmentTextView);
			cancelAppointmentTextView = (TextView) itemView.findViewById(R.id.cancelAppointmentTextView);
			remindTextView = (TextView) itemView.findViewById(R.id.remindTextView);
			appointmentIdTextView = (TextView) itemView.findViewById(R.id.appointmentIdTextView);
			clockImage = (TextView) itemView.findViewById(R.id.clockTextView);
			regphotoImage = (ImageView) itemView.findViewById(R.id.regadduserphotoimage);
			deleteImageView = (ImageView) itemView.findViewById(R.id.deleteImageView);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Log.d(getClass().getSimpleName(), "onItemSelected: ");
				}
			});
		}
	}

	public AppointmentAdapter(ArrayList<PatientAppointmentModel> patientAppointmentArrayList, Context context, int pos) {
		super();
		this.patientAppointmentArrayList = patientAppointmentArrayList;
		this.context = context;
		this.pos = pos;
	}

	@Override
	public int getItemCount() {
		return patientAppointmentArrayList.size();
	}

	@Override
	public void onBindViewHolder(final AppointmentViewHolder appointmentViewHolder, final int i) {
		Picasso.with(context)
				.load(patientAppointmentArrayList.get(i).getUserImage())
				.placeholder(R.drawable.reguserimage)
				.error(R.drawable.reguserimage)
				.into(appointmentViewHolder.doctImageView);

		appointmentViewHolder.doctNameTextView.setText("Dr. " + patientAppointmentArrayList.get(i).getUserName());
		appointmentViewHolder.doctAddressTextView.setText(patientAppointmentArrayList.get(i).getClinicAddress());
		String dtStart = patientAppointmentArrayList.get(i).getStartTime();
		appointmentViewHolder.appointmentIdTextView.setText(patientAppointmentArrayList.get(i).getAppointmentId().toString());

		font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
		appointmentViewHolder.clockImage.setText("\uf017");
		appointmentViewHolder.clockImage.setTypeface(font);
		if (patientAppointmentArrayList.get(i).getAttend() == 2) {
			appointmentViewHolder.regphotoImage.setImageDrawable(context.getResources().getDrawable(R.drawable.rejected));
			appointmentViewHolder.cancelAppointmentTextView.setVisibility(View.GONE);
			appointmentViewHolder.remindTextView.setVisibility(View.GONE);
			appointmentViewHolder.swipeLayout.setSwipeEnabled(false);
		}
		if (patientAppointmentArrayList.get(i).getAttend() == 1) {
			appointmentViewHolder.regphotoImage.setImageDrawable(context.getResources().getDrawable(R.drawable.accepted));
			appointmentViewHolder.cancelAppointmentTextView.setVisibility(View.VISIBLE);
			appointmentViewHolder.remindTextView.setVisibility(View.VISIBLE);
			appointmentViewHolder.swipeLayout.setSwipeEnabled(false);
		}
		if (patientAppointmentArrayList.get(i).getAttend() == 0) {
			appointmentViewHolder.regphotoImage.setImageDrawable(context.getResources().getDrawable(R.drawable.panding));
			appointmentViewHolder.cancelAppointmentTextView.setVisibility(View.GONE);
			appointmentViewHolder.remindTextView.setVisibility(View.GONE);
			appointmentViewHolder.swipeLayout.setSwipeEnabled(false);
		}
		if (pos == 2) {
			appointmentViewHolder.cancelAppointmentTextView.setVisibility(View.GONE);
			appointmentViewHolder.remindTextView.setVisibility(View.GONE);
			appointmentViewHolder.swipeLayout.setSwipeEnabled(true);
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
		try {
			Date date = format.parse(dtStart);
			SimpleDateFormat dtFormats = new SimpleDateFormat("EEE, MMM dd,yyyy hh:mm a", Locale.US);
			appointmentViewHolder.appointmentTextView.setText(dtFormats.format(date).toString());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		appointmentViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
		appointmentViewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
			@Override
			public void onOpen(SwipeLayout layout) {
				// YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
			}
		});

		appointmentViewHolder.rejectLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
				alertbox.setTitle(Html.fromHtml("<font color='#000000'> Delete? </font>"));
				alertbox.setMessage("Are You Sure You Want To Delete Appointment ?");
				alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						appointmentIdInt = patientAppointmentArrayList.get(i).getAppointmentId();
						deletePatientAppointment(i, appointmentIdInt);
						appointmentViewHolder.swipeLayout.close();
					}
				});
				alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						appointmentViewHolder.swipeLayout.close();
					}
				});
				alertbox.show();
			}
		});

		appointmentViewHolder.cancelAppointmentTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
				alertbox.setTitle(Html.fromHtml("<font color='#000000'> Cancel Appointment? </font>"));
				alertbox.setMessage("Are You Sure You Want To Cancel Appointment?");
				alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						appointmentIdInt = patientAppointmentArrayList.get(i).getAppointmentId();
						deletePatientAppointment(pos, appointmentIdInt);
					}
				});
				alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
				alertbox.show();
			}
		});

		appointmentViewHolder.deleteImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
				alertbox.setTitle(Html.fromHtml("<font color='#000000'> Delete? </font>"));
				alertbox.setMessage("Are You Sure You Want To Delete Appointment ?");
				alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						appointmentIdInt = patientAppointmentArrayList.get(i).getAppointmentId();
						deletePatientAppointment(i, patientAppointmentArrayList.get(i).getAppointmentId());
					}
				});
				alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

				}
			});

			alertbox.show();
			appointmentViewHolder.swipeLayout.close();
			}
		});

		appointmentViewHolder.appointmentCardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, PatientAppointMentDetail.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("patientAppointmetDetail", patientAppointmentArrayList.get(i));
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.exit_to_left);
			}
		});

		appointmentViewHolder.remindTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					docNameString = appointmentViewHolder.doctNameTextView.getText().toString();
					doctAddressString = appointmentViewHolder.doctAddressTextView.getText().toString();
					doctAppDateTimeString = appointmentViewHolder.appointmentTextView.getText().toString();

					Intent intent = new Intent(context, AddReminderActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("patientAppointmetDetail", patientAppointmentArrayList.get(i));
					intent.putExtra("docNameString", docNameString);
					intent.putExtra("doctAddressString", doctAddressString);
					intent.putExtra("doctAppDateTimeString", doctAppDateTimeString);
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.exit_to_left);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mItemManger.bind(appointmentViewHolder.itemView, i);

	}

	@Override
	public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int i) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_list_data, parent, false);
		AppointmentViewHolder viewHolder = new AppointmentViewHolder(v);
		return viewHolder;
	}

	private void deletePatientAppointment(final int adappos, int id) {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url.getUrl()).build();
		DoctFin doctFinInterFace = restAdapter.create(DoctFin.class);
		final ProgressDialog mProgressDialog = ProgressDialog.show(context, "", "", true);

		mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		mProgressDialog.setContentView(R.layout.dialog);
		ProgressBar progressBar = (ProgressBar) mProgressDialog.findViewById(R.id.progressWheel);
		Circle circle = new Circle();

		circle.setColor(context.getResources().getColor(R.color.white));
		progressBar.setIndeterminateDrawable(circle);
		doctFinInterFace.deletePatientAppointment(id, new Callback<DeleteAppointmentResponse>() {
			@Override
			public void failure(RetrofitError error) {
				try {
					Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
					if (mProgressDialog.isShowing())
						mProgressDialog.dismiss();
				} catch (Exception ex) {
					if (mProgressDialog.isShowing())
						mProgressDialog.dismiss();
				}
			}

			@Override
			public void success(DeleteAppointmentResponse model, Response response) {
				if (model.getErrorCode() == 0) {
					Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

					patientAppointmentArrayList.remove(adappos);
					notifyDataSetChanged();
					if (mProgressDialog.isShowing())
						mProgressDialog.dismiss();
				}
				if (model.getErrorCode() == 1) {
					if (mProgressDialog.isShowing())
						mProgressDialog.dismiss();
				}
			}
		});
	}

	@Override
	public int getSwipeLayoutResourceId(int position) {
		return R.id.swipe;
	}

}
