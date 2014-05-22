package com.whf.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.whf.sqlite.Transaction;

public class JsonParse {
    
	private class ResponseBean implements Serializable{
		private static final long serialVersionUID=7781333309155600681L;
		private String miningType;
		private JSONObject metadata;
		private List<Object[]> details;
		
		public JSONObject getMetadata() {
			return metadata;
		}
		public void setMetadata(JSONObject metadata) {
			this.metadata = metadata;
		}	
		public String getMiningType() {
			return miningType;
		}
		public void setMiningType(String miningType) {
			this.miningType = miningType;
		}
		public List<Object[]> getDetails() {
			return details;
		}
		public void setDetails(List<Object[]> details) {
			this.details = details;
		}
    }
     
    public ArrayList<Transaction> parse(String jsonStr){
    	
		ArrayList<Transaction> result=new ArrayList<Transaction>();	
		if(!jsonStr.equals("")){
			Gson gson=new Gson();
			ResponseBean response=gson.fromJson(jsonStr, ResponseBean.class);
			for(int i=0;i<response.details.size();i++){
				Object[] records=response.details.get(i);
				result.add(new Transaction(-1,records[0].toString(),records[1].toString(),records[2].toString(),records[3].toString(),records[4].toString(),records[5].toString(),records[6].toString(),records[7].toString(),records[8].toString(),records[9].toString(),records[10].toString(),records[11].toString()));	
			}
		}
		return result;
	}

}