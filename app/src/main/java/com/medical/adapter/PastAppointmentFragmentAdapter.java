package com.medical.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.medical.fragment.FutureAppointmentFragment;
import com.medical.fragment.PastAppointmentFragment;
import com.medical.fragment.TodayAppointmentFragment;
import com.medical.model.PatientAppointmentModel;

import java.util.ArrayList;

public class PastAppointmentFragmentAdapter extends FragmentPagerAdapter {

	final int PAGE_COUNT = 3;
	private String tabTitles[] = new String[] { "Today", "Future", "Past" };
	private ArrayList<PatientAppointmentModel> patientAppointmentArrayList = new ArrayList<PatientAppointmentModel>();

	public PastAppointmentFragmentAdapter(FragmentManager fm, ArrayList<PatientAppointmentModel> patientModel) {
		super(fm);
		this.patientAppointmentArrayList = patientModel;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment frag = null;
		switch (position) {

		case 0:
			frag = new TodayAppointmentFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("todayPatientArraylist", patientAppointmentArrayList);
			frag.setArguments(bundle);
			break;
		case 1:
			frag = new FutureAppointmentFragment();
			Bundle futureBundle = new Bundle();
			futureBundle.putSerializable("futurePatientArraylist", patientAppointmentArrayList);
			frag.setArguments(futureBundle);
			break;

		case 2:
			frag = new PastAppointmentFragment();
			Bundle pastBundle = new Bundle();
			pastBundle.putSerializable("pastPatientArraylist", patientAppointmentArrayList);
			frag.setArguments(pastBundle);
			break;

		default:
			break;
		}
		return frag;

	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return tabTitles[position];
	}

}
