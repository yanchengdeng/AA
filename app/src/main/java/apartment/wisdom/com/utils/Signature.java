package apartment.wisdom.com.utils;

import com.alibaba.fastjson.JSON;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;


public class Signature {
	
	public static String getSign(Map<String,Object> map,String key){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=null && !"".equals(entry.getValue().toString())){
            	if("data".equals(entry.getKey())){
            		//data里面有数据继续排序
            		if(entry.getValue()!=null){
            			Map<String,Object> tempMap= JSON.parseObject(entry.getValue().toString());
            			for(Map.Entry<String,Object> tempentry:tempMap.entrySet()){
            				if(tempentry.getValue()!=null && !"".equals(tempentry.getValue().toString())){
            					list.add(tempentry.getKey() + "=" + tempentry.getValue() + "&");
            				}
            			}
            		}
            	}else{
            		list.add(entry.getKey() + "=" + entry.getValue() + "&");
            	}
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }

    /**
     * 检验数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromMap(Map<String,Object> map,String key)  {
    	boolean isCheck= true;
		try{
			 //Map<String,Object> map = XMLParser.getMapFromXML(responseString);
			//Map<String,Object> map = XMLParser.getMapFromXML(responseString);
			
		    String signFromAPIResponse = map.get("sign").toString();
		    if(signFromAPIResponse=="" || signFromAPIResponse == null){
		    	isCheck = false;
		    }
		    //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		    map.put("sign","");
		    //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		    String signForAPIResponse = Signature.getSign(map,key);
		
		    if(!signForAPIResponse.equals(signFromAPIResponse)){
		        //签名验不过，表示这个API返回的数据有可能已经被篡改了
		    	isCheck = false;
		    }
		    
		}catch(Exception e){
			isCheck = false;
			e.printStackTrace();
		}
		return isCheck;
    }
}
