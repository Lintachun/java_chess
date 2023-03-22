import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client1 {
    public static void main(String[] args) {

        try {
            for (int k = 0; k < 1; k++)
                for (int i = 0; i < 999999999; i++)
                    for (int j = 0; j < 999999999; j++)
                        ;
            Socket csoc = new Socket("127.0.0.1", 3001);
            OutputStream cos = csoc.getOutputStream();
            InputStream cis = csoc.getInputStream();
            Scanner scanner = new Scanner(System.in);
            byte[] cbuffer = new byte[1024];
            String msg = "";
            String creceive = "";
            String p1orp2 = "";
            int clen = 0;
            int one = 0;
            do {
                if (one == 0) {
                    cos.write("OK".getBytes());
                    clen = cis.read(cbuffer, 0, cbuffer.length);
                    p1orp2 = new String(cbuffer, 0, clen);                   
                    one += 1;
                }
               
                if (p1orp2.equals("youisP1")) {
                    
                    clen = cis.read(cbuffer, 0, cbuffer.length);
                    creceive = new String(cbuffer, 0, clen);
                    System.out.println(creceive);
                    cos.write("OK".getBytes());
                    if(creceive.equals("和局") ||creceive.equals("遊戲結束 黑棋獲勝") ||creceive.equals("遊戲結束 紅棋獲勝")){
                        clen = cis.read(cbuffer, 0, cbuffer.length);
                        creceive = new String(cbuffer, 0, clen);
                        System.out.println(creceive);
                        break;
                    }
                    clen = cis.read(cbuffer, 0, cbuffer.length);
                    creceive = new String(cbuffer, 0, clen);
                    cos.write("OK".getBytes());
                    System.out.println("你是P1");
                    if (creceive.equals("P2")) {
                        System.out.println("P2操作中");
                        cos.write("OK".getBytes());
                    } else {
                        System.out.println("請操作");
                        msg = scanner.nextLine();
                        cos.write(msg.getBytes());
                        clen = cis.read(cbuffer, 0, cbuffer.length);
                        creceive = new String(cbuffer, 0, clen);
                        System.out.println(creceive);
                        cos.write("OK".getBytes());
                        if(creceive.equals("和局") ||creceive.equals("遊戲結束 黑棋獲勝") ||creceive.equals("遊戲結束 紅棋獲勝")){
                            clen = cis.read(cbuffer, 0, cbuffer.length);
                            creceive = new String(cbuffer, 0, clen);
                            System.out.println(creceive);
                            break;
                        }
                    }
                }
                if (p1orp2.equals("youisP2")) {
                    clen = cis.read(cbuffer, 0, cbuffer.length);
                    creceive = new String(cbuffer, 0, clen);
                    System.out.println(creceive);
                    cos.write("123".getBytes());
                    if(creceive.equals("和局") ||creceive.equals("遊戲結束 黑棋獲勝") ||creceive.equals("遊戲結束 紅棋獲勝")){
                        clen = cis.read(cbuffer, 0, cbuffer.length);
                        creceive = new String(cbuffer, 0, clen);
                        System.out.println(creceive);
                        break;
                    }
                    clen = cis.read(cbuffer, 0, cbuffer.length);
                    creceive = new String(cbuffer, 0, clen);
                    cos.write("123".getBytes());
                    System.out.println("你是P2");
                    if (creceive.equals("P1")) {
                        System.out.println("P1操作中");
                        cos.write("123".getBytes());
                    } else {
                        System.out.println("請操作");
                        msg = scanner.nextLine();
                        cos.write(msg.getBytes());
                        clen = cis.read(cbuffer, 0, cbuffer.length);
                        creceive = new String(cbuffer, 0, clen);
                        System.out.println(creceive);
                        cos.write("123".getBytes());
                        if(creceive.equals("和局") ||creceive.equals("遊戲結束 黑棋獲勝") ||creceive.equals("遊戲結束 紅棋獲勝")){
                            clen = cis.read(cbuffer, 0, cbuffer.length);
                            creceive = new String(cbuffer, 0, clen);
                            System.out.println(creceive);
                            break;
                        }
                    }
                }
                
            } while (!msg.equals("end"));
            scanner.close();
            csoc.close();

        } catch (Exception e) {
        }
    }
}
