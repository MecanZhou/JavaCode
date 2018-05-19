package com.example.test;

import android.app.Application;

public class App extends Application{
	private static String textData="default";
	public void setData(String textData){
		this.textData=textData;
	}
	public String geTextData(){
		return textData;
	}
}
