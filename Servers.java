import java.io.*;
import java.net.*;
import java.util.Scanner;
class chiz {
    String team;
    int level;
    String wd;
    int status;

    chiz(String steam, int slevel, String sface, int sstatus) {
        team = steam;
        level = slevel;
        wd = sface;
        status = sstatus;
    }

    boolean flipable() {
        return status == 0 ? true : false;
    }

    void flip() {
        status = 1;
    }
}

class board {
    public static final String re = "\u001B[0m";// Text Reset
    public static final String r = "\u001B[31m";// RED
    public static final String b = "\033[0;34m"; // BLUE
    chiz[][] grd;
    int turn;
    int ab, c, ser;
    String p1_team = "null", p2_team = "null", servertest = "", error = "";

    void init() {
        grd = new chiz[4][8];
        turn = 1;
        ab = 0;
        c = 0;
        ser = 1;
        grd[0][0] = new chiz("r", 0, "帥", 0);
        grd[0][1] = new chiz("r", 1, "仕", 0);
        grd[0][2] = new chiz("r", 1, "仕", 0);
        grd[0][3] = new chiz("r", 2, "相", 0);
        grd[0][4] = new chiz("r", 2, "相", 0);
        grd[0][5] = new chiz("r", 3, "居", 0);
        grd[0][6] = new chiz("r", 3, "居", 0);
        grd[0][7] = new chiz("r", 4, "傌", 0);
        grd[1][0] = new chiz("r", 4, "傌", 0);
        grd[1][1] = new chiz("r", 5, "炮", 0);
        grd[1][2] = new chiz("r", 5, "炮", 0);
        grd[1][3] = new chiz("r", 6, "兵", 0);
        grd[1][4] = new chiz("r", 6, "兵", 0);
        grd[1][5] = new chiz("r", 6, "兵", 0);
        grd[1][6] = new chiz("r", 6, "兵", 0);
        grd[1][7] = new chiz("r", 6, "兵", 0);
        grd[2][0] = new chiz("b", 0, "將", 0);
        grd[2][1] = new chiz("b", 1, "士", 0);
        grd[2][2] = new chiz("b", 1, "士", 0);
        grd[2][3] = new chiz("b", 2, "象", 0);
        grd[2][4] = new chiz("b", 2, "象", 0);
        grd[2][5] = new chiz("b", 3, "車", 0);
        grd[2][6] = new chiz("b", 3, "車", 0);
        grd[2][7] = new chiz("b", 4, "馬", 0);
        grd[3][0] = new chiz("b", 4, "馬", 0);
        grd[3][1] = new chiz("b", 5, "包", 0);
        grd[3][2] = new chiz("b", 5, "包", 0);
        grd[3][3] = new chiz("b", 6, "卒", 0);
        grd[3][4] = new chiz("b", 6, "卒", 0);
        grd[3][5] = new chiz("b", 6, "卒", 0);
        grd[3][6] = new chiz("b", 6, "卒", 0);
        grd[3][7] = new chiz("b", 6, "卒", 0);
    }

    void rnd() {
        for (int i = 0; i < 200; i++) {
            int x1 = (int) (Math.random() * 8);
            int y1 = (int) (Math.random() * 4);
            int x2 = (int) (Math.random() * 8);
            int y2 = (int) (Math.random() * 4);
            chiz tmp;
            tmp = grd[y1][x1];
            grd[y1][x1] = grd[y2][x2];
            grd[y2][x2] = tmp;
        }
    }

    void show() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++)
                if (grd[i][j] == null)
                    System.out.print("   ");
                else if (grd[i][j].status == 1 && grd[i][j].team.equals("r"))
                    System.out.print(r + grd[i][j].wd + re + " ");
                else if (grd[i][j].status == 1 && grd[i][j].team.equals("b"))
                    System.out.print(b + grd[i][j].wd + re + " ");
                else if (grd[i][j].status == 0)
                    System.out.print("口 ");

            System.out.println();

        }
        System.out.println(ab);
    }

    void move(int y1, int x1, int y2, int x2) {
        if (grd[y2][x2] != null)
            grd[y2][x2].status = 2;// 問老師
        grd[y2][x2] = grd[y1][x1];
        grd[y1][x1] = null;

    }

    boolean movable(int y1, int x1, int y2, int x2) {
        boolean movable = false;
        if (grd[y1][x1] != null && grd[y1][x1].status == 1)
            if (grd[y2][x2] == null)
                movable = true;
            else if (grd[y2][x2].status == 1 && !grd[y1][x1].team.equals(grd[y2][x2].team))
                if (grd[y1][x1].level == 5) {
                    int cnt = 0, f = 0, t = 0;
                    if (x1 == x2) {
                        if (y1 > y2) {
                            f = y2;
                            t = y1;
                        } else {
                            f = y1;
                            t = y2;
                        }
                        for (int i = f + 1; i < t; i++)
                            if (grd[i][x1] != null)
                                cnt++;
                    } else if (y1 == y2) {
                        if (x1 > x2) {
                            f = x2;
                            t = x1;
                        } else {
                            f = x1;
                            t = x2;
                        }
                        for (int i = f + 1; i < t; i++)
                            if (grd[y1][i] != null)
                                cnt++;
                    }
                    if (cnt == 1)
                        movable = true;
                } else if (!(grd[y1][x1].level == 0 && grd[y2][x2].level == 6)
                        && grd[y1][x1].level <= grd[y2][x2].level)
                    movable = true;
                else if (grd[y1][x1].level == 6 && grd[y2][x2].level == 0)
                    movable = true;
        return movable;
    }

    public void save() throws IOException {
        int aaa = 123;
        FileWriter fw = new FileWriter("c:/java/a.txt");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                chiz ch = grd[i][j];
                if (ch != null) {
                    fw.write(ch.team + ":" + ch.level + ":" + ch.wd + ":" + ch.status + ",");
                } else {
                    fw.write("X,");
                }
            }
        }
        if (turn == 1) {
            fw.write("P1,");

        } else {
            fw.write("P2,");
        }
        fw.write(c + ",");
        fw.write(ab + ",");

        fw.close();
    }

    public void load() throws IOException {
        FileReader fr = new FileReader("c:/java/c.txt");
        char[] data = new char[500];
        int len = fr.read(data);
        String savedata = new String(data, 0, len);
        String[] token = savedata.split(",");
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 8; j++) {
                grd[i][j] = new chiz("", 0, "", 0);
                if (token[i * 8 + j].equals("X")) {
                    grd[i][j] = null;

                } else {
                    if (token[i * 8 + j + 1].equals("P1"))
                        turn = 1;
                    else
                        turn = -1;

                    if (token[i * 8 + j + 2].equals("1"))
                        c = 1;
                    else if (token[i * 8 + j + 2].equals("0"))
                        c = 0;
                    else if (token[i * 8 + j + 2].equals("-1"))
                        c = -1;
                    if (token[i * 8 + j + 3].equals("1"))
                        ab = 1;
                    else if (token[i * 8 + j + 3].equals("0"))
                        ab = 0;

                    String[] onechiz = token[i * 8 + j].split(":");
                    grd[i][j].team = onechiz[0];
                    grd[i][j].level = Integer.parseInt(onechiz[1]);
                    grd[i][j].wd = onechiz[2];
                    grd[i][j].status = Integer.parseInt(onechiz[3]);
                }

            }
        fr.close();
    }

    int game_end() { // 遊戲結束
        int rcount = 0, bcount = 0;
        int end = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 8; j++) {
                if (grd[i][j] != null) {
                    if (grd[i][j].team.equals("r"))
                        rcount++;
                    else if (grd[i][j].team.equals("b"))
                        bcount++;
                }
            }
        if (rcount == 0)
            end = 1;
        if (bcount == 0)
            end = -1;
        if (rcount == 1 && bcount == 1) {
            end = 0;
        }

        return end;
    }

    int test(String cmd) {// 輸入除了數字
        String token[] = cmd.split(",");
        int b = 0;
        String c = cmd;
        if (c.equals("load") || c.equals("save")) {// 除了load save

        } else {
            for (int i = 0; i < token.length; i++) {
                try {
                    int a = Integer.valueOf(token[i]);
                } catch (Exception e) {
                    b = 1;
                }
            }
        }
        return b;
    }

    public void token_length_4(int y1, int x1, int y2, int x2) {

        if (grd[y1][x1].level == 5
                || (Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 0)
                || (Math.abs(x1 - x2) == 0 && Math.abs(y1 - y2) == 1)) {// 只能吃相鄰的包除外

            if (movable(y1, x1, y2, x2)) {
                move(y1, x1, y2, x2);
                turn *= -1;
                servertest = "OK";
            }
        } else if (Math.abs(x1 - x2) == 0 && Math.abs(y1 - y2) == 0) {
            System.out.println("棋子不能在原地不動");
            error = "棋子不能在原地不動";
        } else if (grd[y2][x2] != null) {
            System.out.println("棋子不能跳著吃");
            error = "棋子不能跳著吃";
        } else {
            System.out.println("棋子不能跳著走");
            error = "棋子不能跳著走";
        }

    }
}

public class Servers {
    public static void main(String[] args) {
        try {
            ServerSocket svrsockaet = new ServerSocket(3001);
            // ServerSocket svrsockaet2 = new ServerSocket(3002);
            Socket ssoc = svrsockaet.accept();
            Socket ssoc2 = svrsockaet.accept();
            InputStream sis = ssoc.getInputStream();
            InputStream sis2 = ssoc2.getInputStream();
            OutputStream sos = ssoc.getOutputStream();
            OutputStream sos2 = ssoc2.getOutputStream();
            byte[] sbuffer = new byte[1024];
            byte[] sbuffer2 = new byte[1024];
            String sreceive = "";
            String sreceive2 = "";
            board br = new board();
            br.init();
            br.rnd();
            br.show();
            int ii = 0;
            String aa = "", bb = "", test = "";

            do {
                test = "";
                int slen = sis.read(sbuffer, 0, sbuffer.length);
                sreceive = new String(sbuffer, 0, slen);
                int slen2 = sis2.read(sbuffer2, 0, sbuffer2.length);
                sreceive2 = new String(sbuffer2, 0, slen2);
                test = sreceive + sreceive2;
            } while (!test.equals("OKOK"));
            sos.write("youisP1".getBytes());
            sos2.write("youisP2".getBytes());
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 8; j++) {
                    if (br.grd[i][j] == null) {
                        aa += "  ";
                    } else if (br.grd[i][j].status == 1 && br.grd[i][j].team.equals("r")) {
                        aa += br.r + br.grd[i][j].wd + br.re + " ";
                    } else if (br.grd[i][j].status == 1 && br.grd[i][j].team.equals("b")) {
                        aa += br.b + br.grd[i][j].wd + br.re + " ";
                    } else if (br.grd[i][j].status == 0) {
                        aa += "口 ";
                    }
                }
                aa += "\r\n";
            }
            aa += "P1=null P2=null\r\n";

            sos.write(aa.getBytes());
            sos2.write(aa.getBytes());
            do {
                test = "";
                int slen = sis.read(sbuffer, 0, sbuffer.length);
                sreceive = new String(sbuffer, 0, slen);
                int slen2 = sis2.read(sbuffer2, 0, sbuffer2.length);
                sreceive2 = new String(sbuffer2, 0, slen2);
                test = sreceive + sreceive2;
            } while (!test.equals("OK123"));
            System.out.println("4");
            Scanner sc = new Scanner(System.in);
            String cmd = "";
            do {
                br.error = "請重新輸入";
                br.servertest = "1";
                bb = "";
                if (br.c == 0) {
                    System.out.println(br.turn == 1 ? "P1:" + "null" : "P2:" + "null");
                } else if (br.c == 1) {
                    System.out.println(br.turn == 1 ? "P1:" + "紅隊" : "P2:" + "黑隊");
                } else {
                    System.out.println(br.turn == 1 ? "P1:" + "黑隊" : "P2:" + "紅隊");
                }
                if (ii != 0) {
                    do {
                        test = "";
                        int slen = sis.read(sbuffer, 0, sbuffer.length);
                        sreceive = new String(sbuffer, 0, slen);
                        int slen2 = sis2.read(sbuffer2, 0, sbuffer2.length);
                        sreceive2 = new String(sbuffer2, 0, slen2);
                        test = sreceive + sreceive2;
                    } while (!test.equals("OK123"));
                }
                ii++;
                if (br.ser == 1) {
                    sos.write("P1".getBytes());
                    sos2.write("P1".getBytes());
                    do {
                        test = "";
                        int slen = sis.read(sbuffer, 0, sbuffer.length);
                        sreceive = new String(sbuffer, 0, slen);
                        int slen2 = sis2.read(sbuffer2, 0, sbuffer2.length);
                        sreceive2 = new String(sbuffer2, 0, slen2);
                        test = sreceive + sreceive2;
                    } while (!test.equals("OK123"));
                    int slen = sis.read(sbuffer, 0, sbuffer.length);
                    sreceive = new String(sbuffer, 0, slen);
                    cmd = sreceive;
                } else {
                    sos.write("P2".getBytes());
                    sos2.write("P2".getBytes());
                    do {
                        test = "";
                        int slen = sis.read(sbuffer, 0, sbuffer.length);
                        sreceive = new String(sbuffer, 0, slen);
                        int slen2 = sis2.read(sbuffer2, 0, sbuffer2.length);
                        sreceive2 = new String(sbuffer2, 0, slen2);
                        test = sreceive + sreceive2;
                    } while (!test.equals("OK123"));
                    int slen2 = sis2.read(sbuffer2, 0, sbuffer2.length);
                    sreceive2 = new String(sbuffer2, 0, slen2);
                    cmd = sreceive2;
                }

                if (cmd.equals("save")) {
                    br.save();
                }
                if (cmd.equals("load")) {
                    br.load();
                }
                String token[] = cmd.split(",");
                if (br.test(cmd) == 1) {
                    System.out.println("請重新輸入");
                    br.error = "請重新輸入";
                } else {

                    if (token.length == 2) {

                        int y1 = Integer.parseInt(token[0]);
                        int x1 = Integer.parseInt(token[1]);
                        if (y1 <= 3 && x1 <= 7 && y1 >= 0 && x1 >= 0) {
                            if (br.ab == 0) {
                                if (br.grd[y1][x1].team.equals("r")) {
                                    br.c = 1;

                                } else {
                                    br.c = -1;
                                }
                                br.ab++;
                            }

                            if (br.grd[y1][x1] == null) {// 翻開是空的除錯
                                System.out.println("你輸入的地方沒有棋子");
                                br.error = "你輸入的地方沒有棋子";
                            } else if (br.grd[y1][x1].status == 1) {
                                br.error = "以翻開";
                            } else {
                                if (br.grd[y1][x1].flipable()) {
                                    br.grd[y1][x1].flip();
                                    br.turn *= -1;
                                    br.servertest = "OK";
                                }
                            }

                        } else {
                            System.out.println("你輸入的值為範圍外");
                            br.error = "你輸入的值為範圍外";
                        }

                    } else if (token.length == 4) {

                        int y1 = Integer.parseInt(token[0]);
                        int x1 = Integer.parseInt(token[1]);
                        int y2 = Integer.parseInt(token[2]);
                        int x2 = Integer.parseInt(token[3]);
                        if (br.c == br.turn) {
                            try {

                                if (br.grd[y1][x1].team.equals("r")) {
                                    br.token_length_4(y1, x1, y2, x2);
                                } else {
                                    System.out.println("你不能動對方的棋子");
                                    br.error = "你不能動對方的棋子";
                                }

                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("你輸入的值為範圍外");
                                br.error = "你輸入的值為範圍外";
                            } catch (NullPointerException e) {
                                System.out.println("你輸入的地方沒有棋子");
                                br.error = "你輸入的地方沒有棋子";
                            }
                        } else {
                            try {
                                if (br.grd[y1][x1].team.equals("b")) {
                                    br.token_length_4(y1, x1, y2, x2);
                                } else {
                                    System.out.println("你不能動對方的棋子");
                                    br.error = "你不能動對方的棋子";
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("你輸入的值為範圍外");
                                br.error = "你輸入的值為範圍外";
                            } catch (NullPointerException e) {
                                System.out.println("你輸入的地方沒有棋子");
                                br.error = "你輸入的地方沒有棋子";
                            }
                        }
                    }
                }

                br.show();
                String server2 = "";
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (br.grd[i][j] == null) {
                            server2 += "  ";
                        } else if (br.grd[i][j].status == 1 && br.grd[i][j].team.equals("r")) {
                            server2 += br.r + br.grd[i][j].wd + br.re + " ";
                        } else if (br.grd[i][j].status == 1 && br.grd[i][j].team.equals("b")) {
                            server2 += br.b + br.grd[i][j].wd + br.re + " ";
                        } else if (br.grd[i][j].status == 0) {
                            server2 += "口 ";
                        }
                    }
                    server2 += "\r\n";
                }
                int server_game_end = 0;
                if (br.game_end() == 1) {
                    System.out.println("遊戲結束 黑棋獲勝");
                    server_game_end = 1;
                } else if (br.game_end() == -1) {
                    System.out.println("遊戲結束 紅棋獲勝");
                    server_game_end = 2;
                } else if (br.game_end() == 0) {
                    int rx = 0, ry = 0, bx = 0, by = 0;
                    for (int i = 0; i < 4; i++)
                        for (int j = 0; j < 8; j++) {
                            if (br.grd[i][j] != null) {
                                if (br.grd[i][j].team.equals("r")) {
                                    ry = i;
                                    rx = j;
                                } else {
                                    by = i;
                                    bx = j;
                                }
                            }
                        }
                    if (Math.abs(ry - by) == 1 && Math.abs(rx - bx) == 1) {
                        System.out.println("和局");
                       
                        server_game_end = 3;
                    }
                }
                if (br.c == 0) {
                    bb += "P1=null ";
                    bb += "P2=null";
                }
                if (br.c == 1) {
                    bb += "P1=紅隊 ";
                    bb += "P2=黑隊";
                }
                if (br.c == -1) {
                    bb += "P1=黑隊 ";
                    bb += "P2=紅隊";
                }
                server2 += bb + "\r\n";
                if (server_game_end == 1) {
                    sos.write("遊戲結束 黑棋獲勝".getBytes());
                    sos2.write("遊戲結束 黑棋獲勝".getBytes());
                } else if (server_game_end == 2) {
                    sos.write("遊戲結束 紅棋獲勝".getBytes());
                    sos2.write("遊戲結束 紅棋獲勝".getBytes());
                } else if (server_game_end == 3) {
                    sos.write("和局".getBytes());
                    sos2.write("和局".getBytes());
                } else if (br.ser == 1 && br.servertest.equals("OK")) {
                    br.ser *= -1;
                    sos.write("\r".getBytes());
                } else if (br.ser == -1 && br.servertest.equals("OK")) {
                    br.ser *= -1;
                    sos2.write("\r".getBytes());

                } else if (br.ser == 1 && (cmd.equals("save") )) {
                    sos.write("\r".getBytes());
                }else if( br.ser == -1 && (cmd.equals("save"))){
                    sos2.write("\r".getBytes());
                }else if ((br.ser == -1 && (cmd.equals("load")) || (br.ser == 1 && (cmd.equals("load"))))) {
                    if (br.turn == 1 && br.ser == -1) {
                        br.ser = 1;
                        sos2.write("\r".getBytes());
                    } else if (br.turn == -1 && br.ser == 1) {
                        br.ser = -1;
                        sos.write("\r".getBytes());
                    } else if (br.turn == 1 && br.ser == 1) {
                        sos.write("\r".getBytes());
                    } else if (br.turn == -1 && br.ser == -1) {
                        sos2.write("\r".getBytes());
                    }
                } else if (br.ser == 1 && br.servertest.equals("1")) {
                    sos.write(br.error.getBytes());
                } else if (br.ser == -1 && br.servertest.equals("1")) {
                    sos2.write(br.error.getBytes());
                }

                if (!sreceive.equals("OK")) {
                    System.out.println("Server Received:" + sreceive);
                } else {
                    System.out.println("Server Received:" + sreceive2);
                }

                sos.write(server2.getBytes());
                sos2.write(server2.getBytes());
                do {
                    test = "";
                    int slen = sis.read(sbuffer, 0, sbuffer.length);
                    sreceive = new String(sbuffer, 0, slen);
                    int slen2 = sis2.read(sbuffer2, 0, sbuffer2.length);
                    sreceive2 = new String(sbuffer2, 0, slen2);
                    test = sreceive + sreceive2;
                } while (!test.equals("OK123"));
                if(server_game_end==1&&server_game_end==2&&server_game_end==3){
                    break;
                }
            } while (!sreceive.equals("end"));
            svrsockaet.close();
        } catch (Exception e) {
        }
    }
}