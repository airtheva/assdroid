package net.airtheva.assdroid.fragments;

import net.airtheva.assdroid.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;

public abstract class VectorInputDialog extends DialogFragment implements OnClickListener {
	
	String mTitle = null;
	float[] mVector = null;
	String mPositiveButtonText = null;
	
	public VectorInputDialog() {
		// TODO Auto-generated constructor stub
		mTitle = "VectorInputFragment";
		mVector = new float[]{ 0.0f, 0.0f, 0.0f };
		mPositiveButtonText = "Confirm";
	}
	
	@Override
	final public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), R.style.Theme_Sherlock_Light);
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_vector_input, null);
		setField(view.findViewById(R.id.dialog_vector_input_x), mVector[0]);
		setField(view.findViewById(R.id.dialog_vector_input_y), mVector[1]);
		setField(view.findViewById(R.id.dialog_vector_input_z), mVector[2]);
		AlertDialog dialog = (new AlertDialog.Builder(context))
				.setTitle(mTitle)
				.setView(view)
				.setPositiveButton(mPositiveButtonText, this)
				.setNegativeButton("Cancel", this)
				.create();
		return dialog;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch(which)
		{
		case DialogInterface.BUTTON_POSITIVE:
			onPositiveButtonClick();
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			dismiss();
			break;
		}
	}
	
	float getField(View view)
	{
		return Float.parseFloat(((TextView) view).getText().toString());
	}
	
	void setField(View view, float value)
	{
		((TextView) view).setText(Float.toString(value));
	}
	
	void setTitle(String title)
	{
		mTitle = title;
	}
	
	void set(float[] vector)
	{
		mVector = vector;
	}
	
	void setPositiveButtonText(String text)
	{
		mPositiveButtonText = text;
	}
	
	float[] get()
	{
		float[] vector = new float[3];
		vector[0] = getField(getDialog().findViewById(R.id.dialog_vector_input_x));
		vector[1] = getField(getDialog().findViewById(R.id.dialog_vector_input_y));
		vector[2] = getField(getDialog().findViewById(R.id.dialog_vector_input_z));
		return vector;
	}
	
	abstract void onPositiveButtonClick();

}
