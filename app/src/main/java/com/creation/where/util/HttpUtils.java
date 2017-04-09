package com.creation.where.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zbw on 2017/3/18.
 * 接收path服务器端返回的 InputStream, 转换为 json字符串
 */

public class HttpUtils {
    /**
     * 根据URL，编码和参数获取返回的Json字符串
     * @param url_path
     * @param encode
     * @param obj
     * @return
     */
    public static String getJsonContent(String url_path ,String encode,Object... obj){
        String jsonString = "";
        try {
            URL url = new URL(url_path);
            Constants.CONN = (HttpURLConnection)url.openConnection();

            Constants.CONN.setConnectTimeout(10000);
            Constants.CONN.setRequestMethod("POST");
            Constants.CONN.setDoInput(true);  //从服务器获得数据
            Constants.CONN.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            Constants.CONN.connect();   //建立到远程对象的实际连接
            Log.i("connectedServerURL","访问服务器地址为： "+url_path);

            if(obj.length>0){
                Log.i("connectedServerParam","传入服务器参数为： "+obj[0].toString());
                byte[] bypes = obj[0].toString().getBytes(encode);
                OutputStream os=Constants.CONN.getOutputStream();   //返回打开连接读取的输入流
                os.write(bypes);    // 输入参数
                os.flush();
                os.close();
            }

            int responseCode = Constants.CONN.getResponseCode();
            if (200 == responseCode) {
                jsonString = changeInputStream(Constants.CONN.getInputStream(),encode);
                Log.i("ServerResponse","服务器返回内容为： "+jsonString);
            }else{
//                jsonString=responseCode+"";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Constants.CONN != null) {
                Constants.CONN.disconnect();  //中断连接
            }
        }
        return jsonString;
    }

    private static String changeInputStream(InputStream inputStream , String encode) throws IOException {
        String  jsonString = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        while((len=inputStream.read(data))!=-1){
            outputStream.write(data, 0, len);
        }

        jsonString = new String(outputStream.toByteArray(), encode);
        inputStream.close();
        return jsonString;
    }

}

