package com.dfwy;

import com.dfwy.controller.TaxDataTestController;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.Socket;

public class SocketTest {
    @SneakyThrows
    public static void main(String[] args) {
        String str= "000061212";
        try(Socket socket=new Socket("127.0.0.1", 18080);
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
            BufferedWriter  writer =  new BufferedWriter (new OutputStreamWriter(socket.getOutputStream(),"utf-8"));){
            writer.write(str);
            writer.newLine();
            writer.flush();
            socket.shutdownOutput();
            String line="";
            while((line=reader.readLine())!=null){
                System.out.println("[client]服务端响应："+line);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
