package com.acl.utils.util;

import java.util.Random;
import com.acl.component.SystemConfig;

/**
 * @className:RandomValidateCode
 * @author:lj
 * @createDate:2016年6月4日 上午11:42:06
 * @vsersion:1.0
 * @department:安创乐科技
 * @description:
 */
public class RandomValidateCode {

	private static String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";// 随机产生的字符串
	
	private static String randNumber = "0123456789";// 随机产生的字符串

	/*
	 * 获取随机的字符
	 */
	public static String getRandomString(int num) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			sb.append(randString.charAt(new Random().nextInt(randString.length())));
		}
		
		return sb.toString();
	}
	
	
	/*
	 * 获取随机的字符
	 */
	public static String getRandomNumber(int num) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			sb.append(randNumber.charAt(new Random().nextInt(randNumber.length())));
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(randomNumber(3,3));
	//	System.out.println(getRandomString(6));
	//	System.out.println(RandomValidateCode.strRandChar("0123456789",3,6,4,"4"));
		//System.out.println(strRandChar(randString,3,6,1,"1"));
	}
	
	/*
	 * 1-AAAxxx模式  2-AABBxx模式  3-AABBCC模式   4-xxxAAA模式 
	 */
	public static String randomNumber(int numberType,int groupType){
		String str="";
		String str1="";
		String str2="";
		char[] rands = new char[6];	
		StringBuffer sb = new StringBuffer();
		switch(numberType){
		case 1://1-AAAxxx模式 
			int rand = 0;
			switch (groupType) {
			case 1:
			case 2:				
				if(groupType==1){
					str=SystemConfig.strletter;
				}else {
					str=SystemConfig.strnumber;					
				}
				rand = (int) (Math.random() * str.length());
		        //获取几个重复的
		        for (int i = 0; i < 3; i++) 
		        { 
		        	rands[i] = str.charAt(rand);		    		           
		        }
		        str = str.replaceAll(String.valueOf(str.charAt(rand)), "");
		        //剩下几个随机选择
		        for (int j = 3; j < 6; j++) 
		        {      
		        	int randB = (int) (Math.random() * str.length());
		        	rands[j] = str.charAt(randB);
		        	str = str.replaceAll(String.valueOf(str.charAt(randB)), "");
		        }
		        //输出结果
		        for(int k=0;k<rands.length;k++){
		            sb.append(rands[k]);
		            System.out.println(sb.toString());
		        }	
				break;
			case 3:
				int n=(int) (Math.random() * 10);				
				if(n>5){
					str1=SystemConfig.strletter;
					str2=SystemConfig.strnumber;
				}else {
					str1=SystemConfig.strnumber;	
					str2=SystemConfig.strletter;
				}
				str=SystemConfig.strnumandlet;
				rand = (int) (Math.random() * str1.length());
				//先获取最后一位
				rands[5]=str1.charAt(rand);
				//获取倒数第二位
				int rand2=(int) (Math.random() * str2.length());
				rands[4]=str2.charAt(rand2);
				//去掉前面已经选择的两个
				str=str.replaceAll(String.valueOf(str1.charAt(rand)), "");
				str=str.replaceAll(String.valueOf(str2.charAt(rand2)), "");
				int rand3=(int) (Math.random() * str.length());
		        //获取几个重复的
		        for (int i = 0; i < 3; i++) 
		        { 		        	
		        	rands[i] = str.charAt(rand3);		    		           
		        }
		        //获取第四个字符
		        str=str.replaceAll(String.valueOf(str.charAt(rand3)), "");
		        int rand4=(int) (Math.random() * str.length());
		        rands[3]=str.charAt(rand4);
		        //输出结果
		        for(int k=0;k<rands.length;k++){
		            sb.append(rands[k]);
		            System.out.println(sb.toString());
		        }	
				break;
			}
			break;
		case 2://2-AABBxx模式 
			int rand2=0;
			int rand3=0;
			switch (groupType) {
			case 1:
			case 2:
				if(groupType==1){
					str=SystemConfig.strletter;
				}else {
					str=SystemConfig.strnumber;					
				}
				 rand2 = (int) (Math.random() * str.length());	    	 	
		        //获取几个重复的
		       	rands[0] =rands[1]= str.charAt(rand2);		    		           
		        str=str.replaceAll(String.valueOf(str.charAt(rand2)), "");
		        rand3 = (int) (Math.random() * str.length());
		        rands[2]=rands[3] = str.charAt(rand3);		
		        str=str.replaceAll(String.valueOf(str.charAt(rand3)), "");
		        //剩下几个随机选择
		        for (int j = 4; j < 6; j++) 
		        { 	      		        	
		        	int randB = (int) (Math.random() * str.length());
		        	rands[j] = str.charAt(randB);
		        	str=str.replaceAll(String.valueOf(str.charAt(randB)), "");
		        }		        
		        //输出结果
		        for(int m=0;m<rands.length;m++){
		            sb.append(rands[m]);
		            System.out.println(sb.toString());
		        }	
				break;
			case 3:
				int n=(int) (Math.random() * 10);				
				if(n>5){
					str1=SystemConfig.strletter;
					str2=SystemConfig.strnumber;
				}else {
					str1=SystemConfig.strnumber;	
					str2=SystemConfig.strletter;
				}
				str=SystemConfig.strnumandlet;
				 rand = (int) (Math.random() * str.length());
				 rand2 = (int) (Math.random() * str1.length());
	    	 	 rand3 = (int) (Math.random() * str2.length());
		        //获取几个重复的
		        rands[0]=rands[1] = str1.charAt(rand2);	    		           
		        rands[2]=rands[3] = str2.charAt(rand3);	   		           
		        	      
				//剩下几个随机选择
		        	str=str.replaceAll(String.valueOf(str1.charAt(rand2)), "");
		        	str=str.replaceAll(String.valueOf(str2.charAt(rand3)), "");
		        	int randC=(int) (Math.random() * str.length());
		        	rands[4] = str.charAt(randC);
		        	str=str.replaceAll(String.valueOf(str.charAt(randC)), "");
		        	rands[5] = str.charAt((int) (Math.random() * str.length()));	      
		        
		        //输出结果
		        for(int m=0;m<rands.length;m++){
		            sb.append(rands[m]);
		            System.out.println(sb.toString());
		        }	
				break;			
			}
			break;
		case 3:	// 3-AABBCC模式
			switch(groupType){//1 纯字母组合  2-纯数字组合 3-字母和数字混合
			case 1:	
			case 2:
				if(groupType==1){
					str=SystemConfig.strletter;
				}else {
					str=SystemConfig.strnumber;					
				}
				 rand = (int) (Math.random() * str.length());
				 rands[0] =rands[1]= str.charAt(rand);
				 str=str.replaceAll(String.valueOf(str.charAt(rand)), "");
				 rand2=(int) (Math.random() * str.length());
				 rands[2] =rands[3]= str.charAt(rand2);
				 str=str.replaceAll(String.valueOf(str.charAt(rand2)), "");
				 rand3=(int) (Math.random() * str.length());
				 rands[4] =rands[5]= str.charAt(rand3);
				  //输出结果
			        for(int m=0;m<rands.length;m++){
			            sb.append(rands[m]);
			            System.out.println(sb.toString());
			        }	
				break;
			case 3:
				int n=(int) (Math.random() * 10);	
				str=SystemConfig.strnumandlet;
				if(n>5){
					str1=SystemConfig.strletter;
					str2=SystemConfig.strnumber;
				}else {
					str1=SystemConfig.strnumber;	
					str2=SystemConfig.strletter;
				}
				 rand = (int) (Math.random() * str1.length());
				 rands[0] =rands[1]= str1.charAt(rand);
				 rand2=(int) (Math.random() * str2.length());
				 rands[2] =rands[3]= str2.charAt(rand2);
				 str=str.replaceAll(String.valueOf(str1.charAt(rand)), "");
				 str=str.replaceAll(String.valueOf(str2.charAt(rand2)), "");
				 rand3=(int) (Math.random() * str.length());
				 rands[4] =rands[5]= str.charAt(rand3);
				  //输出结果
			        for(int m=0;m<rands.length;m++){
			            sb.append(rands[m]);
			            System.out.println(sb.toString());
			        }	
				break;		
			}
			
			break;
		case 4:// 3-xxxAAA模式
			switch(groupType){//1 纯字母组合  2-纯数字组合 3-字母和数字混合
			case 1:	
			case 2:
				if(groupType==1){
					str=SystemConfig.strletter;
				}else {
					str=SystemConfig.strnumber;					
				}
				int rand4 = (int) (Math.random() * str.length());
				//获取几个重复的		        
		        for (int i = 3; i < 6; i++) 
		        { 
		        	rands[i] = str.charAt(rand4);		    		           
		        }
				str=str.replaceAll(String.valueOf(str.charAt(rand4)), "");
		        //剩下几个随机选择		        	        	
		        int randB = (int) (Math.random() * str.length());
		        rands[0] = str.charAt(randB);
		        str=str.replaceAll(String.valueOf(str.charAt(randB)), "");
		        int randC = (int) (Math.random() * str.length());
		        rands[1] = str.charAt(randC);
		        str=str.replaceAll(String.valueOf(str.charAt(randC)), "");
		        int randD = (int) (Math.random() * str.length());
		        rands[2] = str.charAt(randD);		        
		        //输出结果
		        for(int k=0;k<rands.length;k++){
		            sb.append(rands[k]);
		            System.out.println(sb.toString());
		        }
				break;
			case 3: 
				int n=(int) (Math.random() * 10);	
				str=SystemConfig.strnumandlet;
				if(n>5){
					str1=SystemConfig.strletter;
					str2=SystemConfig.strnumber;
				}else {
					str1=SystemConfig.strnumber;	
					str2=SystemConfig.strletter;
				}
				 rand = (int) (Math.random() * str1.length());
				 rands[0] = str1.charAt(rand);
				 rand2=(int) (Math.random() * str2.length());
				 rands[1]= str2.charAt(rand2);
				 str=str.replaceAll(String.valueOf(str1.charAt(rand)), "");
				 str=str.replaceAll(String.valueOf(str2.charAt(rand2)), "");
				 rand3=(int) (Math.random() * str.length());
				 rands[2]= str.charAt(rand3);
				 str=str.replaceAll(String.valueOf(str.charAt(rand3)), "");
				 rands[3]=rands[4]=rands[5]=str.charAt((int) (Math.random() * str.length()));
				  //输出结果
			        for(int m=0;m<rands.length;m++){
			            sb.append(rands[m]);
			            System.out.println(sb.toString());
			        }	
				break;		
			}
			
			break;
		}
		
		
		return sb.toString();
	}
	
	/**
	 * 	
	 * @param intA 	几位是重复
	 * @param intB	生成几位号码
	 * @param intC	1-AAAxxx模式  2-AABBxx模式  3-AABBCC模式   4-xxxAAA模式 (AAA是根据intA变换)
	 * @return
	 */
	public static String strRandChar(String strCode,int intA, int intB,int intC,String type)
	{
		//type =1 纯字母组合  2-纯数字组合 3-字母和数字混合
		 String str = strCode;
		 StringBuffer sb = new StringBuffer();			
	     char[] rands = new char[intB];	
	     
	     if(intC==1)
	     {		     
	    	 	int rand = (int) (Math.random() * str.length());
		        //获取几个重复的
		        for (int i = 0; i < intA; i++) 
		        { 
		        	rands[i] = str.charAt(rand);		    		           
		        }
		        //剩下几个随机选择
		        for (int j = intB-intA; j < intB; j++) 
		        {      	
		        	
		        	String strA = str.replaceAll(String.valueOf(str.charAt(rand)), "");
		        	int randB = (int) (Math.random() * strA.length());
		        	rands[j] = str.charAt(randB);
		        }
		        //输出结果
		        for(int k=0;k<rands.length;k++){
		            sb.append(rands[k]);
		            System.out.println(sb.toString());
		        }	
	     }
	     if(intC==2)
	     {
	    	 	int rand2 = (int) (Math.random() * str.length());
	    	 	int rand3 = (int) (Math.random() * str.length());
		        //获取几个重复的
		        for (int i = 0; i < intA; i++) 
		        { 
		        	
		        	rands[i] = str.charAt(rand2);		    		           
		        }
		        for (int k = intA; k < intB-intA; k++) 
		        {
		        	String strA = str.replaceAll(String.valueOf(str.charAt(rand2)), "");
		        	rands[k] = str.charAt(rand3);		    		           
		        }
		        //剩下几个随机选择
		        for (int j = intB-intA; j < intB; j++) 
		        { 	      
		        	String strA = str.replaceAll(String.valueOf(str.charAt(rand2)), "");
		        	String strB = str.replaceAll(String.valueOf(strA.charAt(rand2)), "");
		        	int randB = (int) (Math.random() * strB.length());
		        	rands[j] = str.charAt(randB);
		        }
		        
		        //输出结果
		        for(int m=0;m<rands.length;m++){
		            sb.append(rands[m]);
		            System.out.println(sb.toString());
		        }	
	     }
	     if(intC==3)
	     {
	    	 	int rand2 = (int) (Math.random() * str.length());
	    	 	int rand3 = (int) (Math.random() * str.length());
		        //获取几个重复的
		        for (int i = 0; i < intA; i++) 
		        { 
		        	
		        	rands[i] = str.charAt(rand2);		    		           
		        }
		        for (int k = intA; k < intB-intA; k++) 
		        {
		        	String strA = str.replaceAll(String.valueOf(str.charAt(rand2)), "");
		        	rands[k] = str.charAt(rand3);		    		           
		        }
		        String strA = str.replaceAll(String.valueOf(str.charAt(rand2)), "");
	        	String strB = str.replaceAll(String.valueOf(strA.charAt(rand2)), "");
	        	
	        	if(type.equals("3"))
	        	{
	        		
	        		if(Character.isDigit(str.charAt(rand2)) && Character.isDigit(str.charAt(rand3)))
	        		{
	        			int randB = (int) (Math.random() * SystemConfig.strletter.length());
	        			for (int j = intB-intA; j < intB; j++) 
	     		        { 
	     		        	rands[j] = str.charAt(randB);
	     		        }
	        		}
	        		if(!Character.isDigit(str.charAt(rand2)) && !Character.isDigit(str.charAt(rand3)))
	        		{
	        			int randB = (int) (Math.random() * SystemConfig.strnumber.length());
	        			for (int j = intB-intA; j < intB; j++) 
	     		        { 
	     		        	rands[j] = str.charAt(randB);
	     		        }
	        		}else
	        		{
	        			int randB = (int) (Math.random() * str.length());
	        			for (int j = intB-intA; j < intB; j++) 
	     		        { 
	     		        	rands[j] = str.charAt(randB);
	     		        }
	        		}
	        	}else
	        	{
	        		//剩下几个随机选择
	        		int randB = (int) (Math.random() * str.length());
        			for (int j = intB-intA; j < intB; j++) 
     		        { 
     		        	rands[j] = str.charAt(randB);
     		        }
	        	}		        
		        //输出结果
		        for(int m=0;m<rands.length;m++){
		            sb.append(rands[m]);
		            System.out.println(sb.toString());
		        }	
	     }
	     if(intC==4)
	     {
	    	 int rand4 = (int) (Math.random() * str.length());
		       
		        //剩下几个随机选择
		        for (int j = 0; j < intA; j++) 
		        { 	        	
		        	int randB = (int) (Math.random() * str.length());
		        	rands[j] = str.charAt(randB);
		        }
		        //获取几个重复的
		        for (int i = intA; i < intB; i++) 
		        { 
		        	rands[i] = str.charAt(rand4);		    		           
		        }
		        //输出结果
		        for(int k=0;k<rands.length;k++){
		            sb.append(rands[k]);
		            System.out.println(sb.toString());
		        }
	     }
	     return sb.toString();
	     	       
	}

	
	
	/*
	 * 1-AAAAAA模式  2-AAABBB模式  3-AABBCC模式   4-ABABAB模式  5-ABCABC模式 
	 */
	public static String randNumber(int numberType,int groupType){
		String str="";
		String str1="";
		String str2="";
		char[] rands = new char[6];	
		StringBuffer sb = new StringBuffer();
		switch(numberType){
		case 1://1-AAAAAA模式 
			int rand = 0;
			switch (groupType) {
			case 1:
			case 2:				
				if(groupType==1){
					str=SystemConfig.strletter;
				}else {
					str=SystemConfig.strnumber;					
				}
				rand = (int) (Math.random() * str.length());		       
		        for (int i = 0; i < 6; i++) 
		        {      
		        	rands[i] = str.charAt(rand);
		        }
		        //输出结果
		        for(int k=0;k<rands.length;k++){
		            sb.append(rands[k]);
		            System.out.println(sb.toString());
		        }	
				break;
			case 3:
				//该模式不存在数字字母组合
				break;
			}
			break;
		case 2: //2-AAABBB模式  
			int rand2=0;
			int rand3=0;
			switch (groupType) {
			case 1:
			case 2:
				if(groupType==1){
					str=SystemConfig.strletter;
				}else {
					str=SystemConfig.strnumber;					
				}
				 rand2 = (int) (Math.random() * str.length());	    	 	
		        //获取几个重复的
		       	rands[0] =rands[1]= rands[2]=str.charAt(rand2);		    		           
		        str=str.replaceAll(String.valueOf(str.charAt(rand2)), "");
		        rand3 = (int) (Math.random() * str.length());
		        rands[3]=rands[4]=rands[5] = str.charAt(rand3);
		        //输出结果
		        for(int m=0;m<rands.length;m++){
		            sb.append(rands[m]);
		            System.out.println(sb.toString());
		        }	
				break;
			case 3:
				int n=(int) (Math.random() * 10);				
				if(n>5){
					str1=SystemConfig.strletter;
					str2=SystemConfig.strnumber;
				}else {
					str1=SystemConfig.strnumber;	
					str2=SystemConfig.strletter;
				}
				str=SystemConfig.strnumandlet;
				 rand = (int) (Math.random() * str.length());
				 rand2 = (int) (Math.random() * str1.length());
	    	 	 rand3 = (int) (Math.random() * str2.length());
		        //获取几个重复的
		        rands[0]=rands[1] =rands[2]= str1.charAt(rand2);	    		           
		        rands[3]=rands[4]=rands[5] = str2.charAt(rand3);   		                 	 
		        //输出结果
		        for(int m=0;m<rands.length;m++){
		            sb.append(rands[m]);
		            System.out.println(sb.toString());
		        }	
				break;			
			}
			break;
		case 3:	// 3-AABBCC模式
			switch(groupType){//1 纯字母组合  2-纯数字组合 3-字母和数字混合
			case 1:	
			case 2:
				if(groupType==1){
					str=SystemConfig.strletter;
				}else {
					str=SystemConfig.strnumber;					
				}
				 rand = (int) (Math.random() * str.length());
				 rands[0] =rands[1]= str.charAt(rand);
				 str=str.replaceAll(String.valueOf(str.charAt(rand)), "");
				 rand2=(int) (Math.random() * str.length());
				 rands[2] =rands[3]= str.charAt(rand2);
				 str=str.replaceAll(String.valueOf(str.charAt(rand2)), "");
				 rand3=(int) (Math.random() * str.length());
				 rands[4] =rands[5]= str.charAt(rand3);
				  //输出结果
			        for(int m=0;m<rands.length;m++){
			            sb.append(rands[m]);
			            System.out.println(sb.toString());
			        }	
				break;
			case 3:
				int n=(int) (Math.random() * 10);	
				str=SystemConfig.strnumandlet;
				if(n>5){
					str1=SystemConfig.strletter;
					str2=SystemConfig.strnumber;
				}else {
					str1=SystemConfig.strnumber;	
					str2=SystemConfig.strletter;
				}
				 rand = (int) (Math.random() * str1.length());
				 rands[0] =rands[1]= str1.charAt(rand);
				 rand2=(int) (Math.random() * str2.length());
				 rands[2] =rands[3]= str2.charAt(rand2);
				 str=str.replaceAll(String.valueOf(str1.charAt(rand)), "");
				 str=str.replaceAll(String.valueOf(str2.charAt(rand2)), "");
				 rand3=(int) (Math.random() * str.length());
				 rands[4] =rands[5]= str.charAt(rand3);
				  //输出结果
			        for(int m=0;m<rands.length;m++){
			            sb.append(rands[m]);
			            System.out.println(sb.toString());
			        }	
				break;		
			}
			
			break;
		case 4://  4-ABABAB模式 
			switch(groupType){//1 纯字母组合  2-纯数字组合 3-字母和数字混合
			case 1:	
			case 2:
				if(groupType==1){
					str=SystemConfig.strletter;
				}else {
					str=SystemConfig.strnumber;					
				}
				int rand4 = (int) (Math.random() * str.length());
				rands[0]=rands[2]=rands[4]=str.charAt(rand4);
				str=str.replaceAll(String.valueOf(str.charAt(rand4)), "");	        	        	
		        int randB = (int) (Math.random() * str.length());
		        rands[1]=rands[3]=rands[5] = str.charAt(randB);
		        //输出结果
		        for(int k=0;k<rands.length;k++){
		            sb.append(rands[k]);
		            System.out.println(sb.toString());
		        }
				break;
			case 3: 
				int n=(int) (Math.random() * 10);	
				str=SystemConfig.strnumandlet;
				if(n>5){
					str1=SystemConfig.strletter;
					str2=SystemConfig.strnumber;
				}else {
					str1=SystemConfig.strnumber;	
					str2=SystemConfig.strletter;
				}
				 rand = (int) (Math.random() * str1.length());
				 rands[0]=rands[2]=rands[4] = str1.charAt(rand);
				 rand2=(int) (Math.random() * str2.length());
				 rands[1]=rands[3]=rands[5]= str2.charAt(rand2);
				  //输出结果
			        for(int m=0;m<rands.length;m++){
			            sb.append(rands[m]);
			            System.out.println(sb.toString());
			        }	
				break;		
			}
			
			break;
		case 5://  5-ABCABC模式
			switch(groupType){//1 纯字母组合  2-纯数字组合 3-字母和数字混合
			case 1:	
			case 2:
				if(groupType==1){
					str=SystemConfig.strletter;
				}else {
					str=SystemConfig.strnumber;					
				}
				int rand4 = (int) (Math.random() * str.length());
				rands[0]=rands[3]=str.charAt(rand4);
				str=str.replaceAll(String.valueOf(str.charAt(rand4)), "");	        	        	
		        int randB = (int) (Math.random() * str.length());
		        rands[1]=rands[4] = str.charAt(randB);
		        str=str.replaceAll(String.valueOf(str.charAt(randB)), "");	        	        	
		        int randC = (int) (Math.random() * str.length());
		        rands[2]=rands[5] = str.charAt(randC);
		        //输出结果
		        for(int k=0;k<rands.length;k++){
		            sb.append(rands[k]);
		            System.out.println(sb.toString());
		        }
				break;
			case 3: 
				int n=(int) (Math.random() * 10);	
				str=SystemConfig.strnumandlet;
				if(n>5){
					str1=SystemConfig.strletter;
					str2=SystemConfig.strnumber;
				}else {
					str1=SystemConfig.strnumber;	
					str2=SystemConfig.strletter;
				}
				 rand = (int) (Math.random() * str1.length());
				 rands[0]=rands[3] = str1.charAt(rand);
				 rand2=(int) (Math.random() * str2.length());
				 rands[1]=rands[4]= str2.charAt(rand2);
				 str=str.replaceAll(String.valueOf(str1.charAt(rand)), "");	        	        	
				 str=str.replaceAll(String.valueOf(str2.charAt(rand2)), "");
				 rand3=(int) (Math.random() * str.length());
				 rands[2]=rands[5]= str.charAt(rand3);
				  //输出结果
			        for(int m=0;m<rands.length;m++){
			            sb.append(rands[m]);
			            System.out.println(sb.toString());
			        }	
				break;		
			}
			
			break;
		}
		
		
		return sb.toString();
	}
}
