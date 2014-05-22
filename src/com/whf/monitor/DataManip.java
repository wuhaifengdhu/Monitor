package com.whf.monitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.content.Context;

import com.whf.date.CurrentCalendar;
import com.whf.network.DataDownloadService;
import com.whf.sqlite.DatabaseHandler;
import com.whf.sqlite.Transaction;


public class DataManip {
	private ArrayList<Transaction> dataStores=new ArrayList<Transaction>();
	private Context context;
	private static int timeZoneType=0; //0 hourly,1 daily，2 Minute
	public final static int DAILY=1;
	public final static int HOURLY=0;
	public final static int MINUTELY=2;
	
	public DataManip(Context ctx){
		context=ctx;
	}
	
	public static int getTimeZoneType() {
		return timeZoneType;
	}

	public static void setTimeZoneType(int timeZoneType) {
		DataManip.timeZoneType = timeZoneType;
	}
	
	public void drawDataFromSqlite(long end,int zoneType){
		long start=end;;
		switch(zoneType){
			case HOURLY:start=end-60*60;break;
			case DAILY:start=end-60*60*24;break;
			case MINUTELY:start=end-60;break;
		}
		dataStores=new DatabaseHandler(context).getTransactionByTime(start, end);
	}

	private void downloadDatas(){
		//clear the dataStores
		dataStores.clear();
		
		//download data according to the frequence
		dataStores=downloadDatasWithoutStore();
	}
	
	private ArrayList<Transaction> downloadDatasWithoutStore(){
		try {
			//draw time from lastTime to now
			long start=DataDownloadService.latestTime+1;
			CurrentCalendar.setCurrentCalendar();
			long end=CurrentCalendar.getCurrentTimeTotalSecond();
			dataStores=new DatabaseHandler(context).getTransactionByTime(start, end);
		} catch(Exception e){
			e.printStackTrace();
		}
		return dataStores;
		
	}

	
	private ArrayList<Transaction> sortData(ArrayList<Transaction> stores,String center,String sub,int orderType){
				
		        //if there was no data, then can reback directly
		        if(stores==null||stores.isEmpty()) return new ArrayList<Transaction>();
				
				//Get data fit the parameter
				ArrayList<Transaction> results=new ArrayList<Transaction>();
				
				for(int i=0;i<stores.size();i++){
					if((center.equals("all") || center.equals(stores.get(i).dataCenter))&& (sub.equals("") || sub.equalsIgnoreCase(stores.get(i).dataSubType))){
						results.add(stores.get(i));
					}
				}
				
				//sorted according to the orderType
				switch(orderType){
						case 0:Collections.sort(results,new Comparator<Transaction>(){
							public int compare(Transaction emp1,Transaction emp2){
								return emp1.dataCenter.compareToIgnoreCase(emp2.dataCenter);
							}
						});break;
						case 1:Collections.sort(results,new Comparator<Transaction>(){
							public int compare(Transaction emp1,Transaction emp2){
								return emp1.dataSubType.compareToIgnoreCase(emp2.dataSubType);
							}
						});break;
						case 2:Collections.sort(results,new Comparator<Transaction>(){
							public int compare(Transaction emp1,Transaction emp2){
								if(Long.valueOf(emp1.getTime())>Long.valueOf(emp2.getTime())) return 1;
								else if(Long.valueOf(emp1.getTime())<Long.valueOf(emp2.getTime())) return -1;
								else return 0;
							}
						});break;
						case 3:Collections.sort(results,new Comparator<Transaction>(){
							public int compare(Transaction emp1,Transaction emp2){
								if(Double.valueOf(emp1.totalCount)>Double.valueOf(emp2.totalCount)) return 1;
								else if(Double.valueOf(emp1.totalCount)<Double.valueOf(emp2.totalCount)) return -1;
								else return 0;
							}
						});break;
						case 4:Collections.sort(results,new Comparator<Transaction>(){
							public int compare(Transaction emp1,Transaction emp2){
								if(Double.valueOf(emp1.failureCount)>Double.valueOf(emp2.failureCount)) return 1;
								else if(Double.valueOf(emp1.failureCount)<Double.valueOf(emp2.failureCount)) return -1;
								else return 0;
							}
						});break;
						case 5:Collections.sort(results,new Comparator<Transaction>(){
							public int compare(Transaction emp1,Transaction emp2){
								if(Double.valueOf(emp1.failureRate)>Double.valueOf(emp2.failureRate)) return 1;
								else if(Double.valueOf(emp1.failureRate)<Double.valueOf(emp2.failureRate)) return -1;
								else return 0;
							}
						});break;
						case 6:Collections.sort(results,new Comparator<Transaction>(){
							public int compare(Transaction emp1,Transaction emp2){
								if(Double.valueOf(emp1.average )> Double.valueOf(emp2.average)) return 1;
								else if(Double.valueOf(emp1.average )< Double.valueOf(emp2.average)) return -1;
								else return 0;
							}
						});break;
						case 7:Collections.sort(results,new Comparator<Transaction>(){
							public int compare(Transaction emp1,Transaction emp2){
								if(Double.valueOf(emp1.tile95)>Double.valueOf(emp2.tile95)) return 1;
								else if(Double.valueOf(emp1.tile95)<Double.valueOf(emp2.tile95)) return -1;
								else return 0;
							}
						});break;
						case 8:Collections.sort(results,new Comparator<Transaction>(){
							public int compare(Transaction emp1,Transaction emp2){
								if(Double.valueOf(emp1.min)>Double.valueOf(emp2.min)) return 1;
								else if(Double.valueOf(emp1.min)<Double.valueOf(emp2.min)) return -1;
								else return 0;
							}
						});break;
						case 9:Collections.sort(results,new Comparator<Transaction>(){
							public int compare(Transaction emp1,Transaction emp2){
								if(Double.valueOf(emp1.max)>Double.valueOf(emp2.max)) return 1;
								else if(Double.valueOf(emp1.max)<Double.valueOf(emp2.max)) return -1;
								else return 0;
							}
						});break;
				}	
				return results;
	}
	
	public ArrayList<Transaction> getSortedData(String center,String sub,int orderType,int type){
		//refresh data from server  type:0 download data type:1 just sort
		if(type==0) downloadDatas();
		return sortData(dataStores,center,sub,orderType);
	}
	
	public String[] getSubNames(String center){
		ArrayList<String> nameList=new ArrayList<String>();
		ArrayList<Transaction> stores=getSortedData(center,"",2,1);
		for(int i=0;i<stores.size();i++){
			nameList.add(stores.get(i).dataSubType);
		}
		Collections.sort(nameList,new Comparator<String>(){
			public int compare(String emp1,String emp2){
				return emp1.compareToIgnoreCase(emp2);
			}
		});
		Set<String> nameSet=new HashSet<String>();
		nameSet.addAll(nameList);
		String[] result=new String[nameSet.size()];
		Iterator<String> it=nameSet.iterator();
		for(int i=0;it.hasNext();i++){
			result[i]=it.next();
		}
		return result;
		
	}

}
