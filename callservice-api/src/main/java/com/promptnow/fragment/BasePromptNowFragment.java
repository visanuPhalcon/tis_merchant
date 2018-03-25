package com.promptnow.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.promptnow.application.PromptNowFragmentState;
import com.promptnow.utility.UtilApplication;

public class BasePromptNowFragment extends Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().fragmentStateChanged(this, PromptNowFragmentState.Fragment_ActivityCreated);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().fragmentStateChanged(this, PromptNowFragmentState.Fragment_Attached);
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().fragmentStateChanged(this, PromptNowFragmentState.Fragment_Created);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().fragmentStateChanged(this, PromptNowFragmentState.Fragment_Destroyed);
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().fragmentStateChanged(this, PromptNowFragmentState.Fragment_ViewDestroyed);
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().fragmentStateChanged(this, PromptNowFragmentState.Fragment_Detached);
		super.onDetach();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().fragmentStateChanged(this, PromptNowFragmentState.Fragment_Paused);
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().fragmentStateChanged(this, PromptNowFragmentState.Fragment_Resumed);
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().fragmentStateChanged(this, PromptNowFragmentState.Fragment_Started);
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().fragmentStateChanged(this, PromptNowFragmentState.Fragment_Stoped);
		super.onStop();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		UtilApplication.getApplication().fragmentStateChanged(this, PromptNowFragmentState.Fragment_ViewCreated);
		super.onViewCreated(view, savedInstanceState);
	}

}
