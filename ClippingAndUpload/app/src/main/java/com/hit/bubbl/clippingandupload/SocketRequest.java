package com.hit.bubbl.clippingandupload;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * @author Bubbles
 * @create 2018/7/23
 * @Describe
 */
public class SocketRequest {
    private String serverIp;

    public SocketRequest(String serverIp) {
        super();
        this.serverIp = serverIp;
    }

    public boolean upload(byte[] data, String filename, Map parameters) throws IOException {

//        Socket mapSocket=new Socket(serverIp, 2019);
//        DataOutputStream mapOut = new DataOutputStream(mapSocket.getOutputStream());
//        if(parameters!=null){
//            for (Iterator iter = parameters.keySet().iterator(); iter.hasNext();){
//                String element = (String) iter.next();
//                String value=parameters.get(element).toString();
//                mapOut.writeUTF(value);
//            }
//        }else {
//            mapOut.writeUTF("");
//        }
//        mapSocket.close();
//
//        Socket filenameSocket;
//        filenameSocket=new Socket(serverIp, 2018);
//        // 发送给服务器的数据
//        DataOutputStream filenameOut = new DataOutputStream(filenameSocket.getOutputStream());
//        filenameOut.writeUTF(filename);
//        filenameSocket.close();
//
//        Socket socket;  //套接字
//        FileOutputStream out;   //传送文件
//        socket = new Socket(serverIp, 2017);
//        out = (FileOutputStream)socket.getOutputStream();
//        int start=0;
//        int end=data.length;
//        while (start<end){
//            byte [] newData;
//            if(start+64<=end){
//                newData= Arrays.copyOfRange(data, start, start+64);
//            } else {
//                newData= Arrays.copyOfRange(data, start, end);
//            }
////            newData= Arrays.copyOfRange(data, start, 7);
//            System.out.println(data.length);
//            System.out.println(newData.length);
//            out.write(newData);
//            start=start+64;
//        }
////        out.write(data);
//        out.close();
//        socket.close();

        return true;
    }

    public boolean download(String fileSavePath, String filename) throws IOException{
        File target;    //接收到的文件保存的位置
        FileOutputStream save;  //将接收到的文件写入电脑
        FileInputStream in;     //读取穿送过来的数据文件

        //接受前文件的准备
        target = new File(fileSavePath);
        save = new FileOutputStream(target);

        Socket socket=new Socket(serverIp, 2020);

        //传送文件名
        DataOutputStream mapout = new DataOutputStream(socket.getOutputStream());
        mapout.writeUTF(filename);
//        mapout.close();

        //接受文件流
        in = (FileInputStream) socket.getInputStream();
        try {

            //接收数据
            byte[] b = new byte[64];
            int n = in.read(b);
//            System.out.println(b.toString());
            int start = (int) System.currentTimeMillis();
            while (n != -1) {
                save.write(b, 0, n);    //写入指定地方
                n = in.read(b);
            }
            int end = (int) System.currentTimeMillis();
            System.out.println("接收花费的时间：" + (end - start));
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            socket.close();
            save.close();
        }
        return true;

    }
}
