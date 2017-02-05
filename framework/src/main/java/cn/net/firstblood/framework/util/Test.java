/**
 * 
 */
package cn.net.firstblood.framework.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gangxiang.chengx
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		//System.out.println(HttpClientUtil.doGet("http://myseller.taobao.com/api/asynSetShortCutApp.do?userId=101429440&callback=callback"));
//		int beginYear = 2016;
//		String day_str = DateUtil.format(DateUtil.getCurrentTime(), "-MM-dd 00:00:00");
//		List<Date> dateList = new ArrayList<Date>();
//		for(int year=beginYear;year<=DateUtil.getYear();year++){
//			System.out.println(DateUtil.parseDate(year+day_str));
//			dateList.add(DateUtil.parseDate(year+day_str));
//		}
//		
//		//System.out.println(DateUtil.getYear());
		
//		List<File> fileList = FileUtil.getFileList("/Users/chengx/Documents/mydoc/pic/2016/0204");
//		for(File file : fileList){
//			System.out.println(file.getAbsolutePath());
//		}
//		
//		Date date = new Date(1486129833444L);
//		System.out.println(DateUtil.format(date));
		String str = "https://webpush.wx.qq.com/cgi-bin/mmwebwx-bin/synccheck?r=1486129833444&skey=%40crypt_adab0c28_659c8d6aa78e3aa93f58703d72e400c7&sid=mLB7rlPQ%2Fozm9vxV&uin=2685885737&deviceid=e759155195163869&synckey=1_658780041%7C2_658780045%7C3_658780038%7C11_658780038%7C13_658780038%7C201_1486129546%7C1000_1486116362%7C1001_1486116392%7C1002_1485528723%7C1004_1484915380&_=1486129553775";
		int index = str.indexOf("r=")+2;
		String str1= str.substring(0,index);
		String str2 = str.substring(str.indexOf("&", index));
		System.out.println(str1);
		System.out.println(str2);
	}

}
