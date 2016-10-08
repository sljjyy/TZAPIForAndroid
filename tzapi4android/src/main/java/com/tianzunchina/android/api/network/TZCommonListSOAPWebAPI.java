package com.tianzunchina.android.api.network;

import android.support.v4.util.ArrayMap;

import com.tianzunchina.android.api.view.InstalList;

public class TZCommonListSOAPWebAPI {
	private final InstalList instalList;
	private WebCallBackListener listenner;
	private SOAPWebAPI webAPI;

	public TZCommonListSOAPWebAPI(InstalList instalList, WebCallBackListener listenner){
		this.instalList = instalList;
		this.listenner = listenner;
		webAPI = new SOAPWebAPI();
	}
	
	public void getList(int skip, int task, ArrayMap<String, Object> map) {
		TZRequest request = new TZRequest(instalList.getWebServiceUrl(), instalList.getName());
		if(instalList.getSkipNumber() != null){
			request.addParam(instalList.getSkipNumber(), skip);
		}
		if(instalList.getTakeNumber() != null){
			request.addParam(instalList.getTakeNumber(), task);
		}
		if(map != null){
			for(int i = 0; i < map.size(); i++){
				request.addParam(map.keyAt(i), map.valueAt(i));
			}
		}
		webAPI.call(request, listenner);
	}
}