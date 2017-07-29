package com.housing.housecheap.utility;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.housing.housecheap.R;

public class CustomAlertBox {

	public void MyAlertBox(Context context) {
		final Dialog dialog=new Dialog(context);
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.internetconnection_dialog);

		TextView textMsg = (TextView) dialog.findViewById(R.id.txtDialogMessage);
		textMsg.setText("Please, Check Internet connection");
		TextView dialogButton = (TextView) dialog.findViewById(R.id.txtdialogButtonOK);
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog.show();
		dialog.getWindow().setAttributes(lp);

	}
	
}
