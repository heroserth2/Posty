import com.mongodb.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class MainPosty  extends JFrame{
        private JButton postsButton;
        private JButton homeButton;
        private JButton loginRegisterButton;
    private JLabel imagelogo;
    private JLabel Lb1;
    private JLabel Lb2;
    private JLabel Lb3;
    private JLabel Lb4;
    private JLabel Lb5;
    private JPanel room;
    private JButton btnroom;
    private JTextField tfSe;
    private JPanel panelroom;
    private JTable table;
    private JButton selectButton;
    private JLabel lprocess;
    private JProgressBar progressBar;
    private JLabel status;
    private JLabel about;
    private JPanel mainPanel;
    private JLabel Nofound;
    private JPanel Process;
    static int countroom=0;
    static Thread thread = new Thread();
    static ArrayList<String> tempID = new ArrayList<String>() ;

    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    public  MainPosty(){
        add(mainPanel);
        setSize(1000,800);
        tfSe.setText("ค้นหา");
        about.setText("เกี่ยวกับ");
        loginRegisterButton.setText("สมัครสมาชิก/เข้าสู่ระบบ");
        postsButton.setText("ตั้งกระทู้");
        btnroom.setText("ห้อง");
        homeButton.setText("หน้าหลัก");
        ImageIcon icon = new ImageIcon("./image/logo.png");
        imagelogo.setBounds(200,200,40,40);
        int offset = imagelogo.getInsets().left;
        imagelogo.setIcon(resizeIcon(icon, imagelogo.getWidth() - offset, imagelogo.getHeight() - offset));
        icon = new ImageIcon("./image/love.png");
        Lb1.setBounds(200,200,40,40);
        offset = Lb1.getInsets().left;
        Lb1.setIcon(resizeIcon(icon, Lb1.getWidth() - offset, Lb1.getHeight() - offset));
        icon = new ImageIcon("./image/Game.png");
        Lb2.setBounds(200,200,40,40);
        offset = Lb2.getInsets().left;
        Lb2.setIcon(resizeIcon(icon, Lb2.getWidth() - offset, Lb2.getHeight() - offset));
        icon = new ImageIcon("./image/Food.png");
        Lb3.setBounds(200,200,40,40);
        offset = Lb3.getInsets().left;
        Lb3.setIcon(resizeIcon(icon, Lb3.getWidth() - offset, Lb3.getHeight() - offset));
        icon = new ImageIcon("./image/Cat.png");
        Lb4.setBounds(200,200,40,40);
        offset = Lb4.getInsets().left;
        Lb4.setIcon(resizeIcon(icon, Lb4.getWidth() - offset, Lb4.getHeight() - offset));
        icon = new ImageIcon("./image/catoon.png");
        Lb5.setBounds(200,200,40,40);
        offset = Lb5.getInsets().left;
        Lb5.setIcon(resizeIcon(icon, Lb5.getWidth() - offset, Lb5.getHeight() - offset));
            /* Start*/

        try {
            MongoClientURI uri = new MongoClientURI("mongodb://admin:admin1234@ds046677.mlab.com:46677/posty");
            MongoClient client = new MongoClient(uri);
            DB db = client.getDB("posty");
            try {
                db.getCollection("post");
            } catch (Exception exp) {
                db.createCollection("username", null);
            }
            DBCollection collection = db.getCollection("post");

            //
            JTableHeader header = table.getTableHeader();
            header.setFont(new Font("Tahoma",Font.BOLD,15));
            table.setRowHeight(40);
            DBCursor cursor = null;
            cursor = collection.find();
            String[] columnNames = {"ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            model.addRow(new Object[] { "ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"});
            int run = 1 ;
            while(cursor.hasNext()) {
                try{
                    DBObject obj = cursor.next();
                    Object id = obj.get("_id");
                    tempID.add(id+"");
                    String topic = (String)obj.get("topic");
                    String name = (String)obj.get("roomowner");
                    int topview = (int)obj.get("topview");
                    int countcomment = (int)obj.get("countcomment");
                    model.addRow(new Object[] { run,topic,name,topview,countcomment});
                    run++;
                }
                catch(Exception e){
                    System.out.println("E "+e);
                }
            }

            table.setModel(model);

            cursor.close();





            //
            client.close();
        }catch (Exception exp){
            System.out.println(exp);
        }
        /* Stop */






        btnroom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Process.setVisible(true);
                if(countroom==0){
                    room.setVisible(true);
                    countroom++;
                }else{
                    room.setVisible(false);
                    countroom--;
                }
                Process.setVisible(false);
            }
        });


        tfSe.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    Process.setVisible(true);
                    table.setVisible(true);
                    Nofound.setVisible(false);
                    int r = 0;
                    try {
                        MongoClientURI uri = new MongoClientURI("mongodb://admin:admin1234@ds046677.mlab.com:46677/posty");
                        MongoClient client = new MongoClient(uri);
                        DB db = client.getDB("posty");
                        try {
                            db.getCollection("post");
                        } catch (Exception exp) {
                            db.createCollection("username", null);
                        }
                        DBCollection collection = db.getCollection("post");

                        //

                        DBCursor cursor = null;
                        cursor = collection.find(new BasicDBObject("topic",tfSe.getText()));
                        String[] columnNames = {"ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"};
                        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                        model.addRow(new Object[] { "ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"});
                        int run = 1 ;
                        while(cursor.hasNext()) {
                            r++;
                            try{
                                DBObject obj = cursor.next();
                                Object id = obj.get("_id");
                                tempID.add(id+"");
                                System.out.println("Pass");
                                String topic = (String)obj.get("topic");
                                String name = (String)obj.get("roomowner");
                                int topview = (int)obj.get("topview");
                                int countcomment = (int)obj.get("countcomment");
                                model.addRow(new Object[] { run,topic,name,topview,countcomment});
                                run++;
                                //Pubg สนุกไหมครับ
                            }
                            catch(Exception exp){
                                System.out.println("E "+exp);
                            }
                        }
                        table.setModel(model);
                      
                        cursor.close();
                        client.close();

                    }catch (Exception exp){
                        System.out.println(exp);
                    }
                    Process.setVisible(false);
                    if(r==0){
                        table.setVisible(false);
                        Nofound.setVisible(true);
                        ImageIcon icon = new ImageIcon("./image/nosearch.png");
                        Nofound.setBounds(200,200,300,200);
                        Nofound.setIcon(icon);

                    }
                }
            }
        });

        tfSe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfSe.setText("");
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Process.setVisible(true);
                table.setVisible(true);
                Nofound.setVisible(false);
                try {

                    MongoClientURI uri = new MongoClientURI("mongodb://admin:admin1234@ds046677.mlab.com:46677/posty");
                    MongoClient client = new MongoClient(uri);
                    DB db = client.getDB("posty");
                    try {
                        db.getCollection("post");
                    } catch (Exception exp) {
                        db.createCollection("username", null);
                    }
                    DBCollection collection = db.getCollection("post");

                    //
                    DBCursor cursor = null;
                    cursor = collection.find();
                    String[] columnNames = {"ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"};
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                    model.addRow(new Object[] { "ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"});
                    removeArray();
                    int run =1;
                    while(cursor.hasNext()) {
                        try{
                            DBObject obj = cursor.next();
                            Object id = obj.get("_id");
                            tempID.add(id+"");
                            String topic = (String)obj.get("topic");
                            String name = (String)obj.get("roomowner");
                            int topview = (int)obj.get("topview");
                            int countcomment = (int)obj.get("countcomment");
                            model.addRow(new Object[] { run,topic,name,topview,countcomment});
                            run ++;
                        }
                        catch(Exception exp){
                            System.out.println("E "+exp);
                        }
                    }
                    table.setModel(model);
                    cursor.close();

                    client.close();



                }catch (Exception exp){
                    System.out.println(exp);
                }
                Process.setVisible(false);
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row != 0)
                        status.setText("./Posty/post/" + tempID.get(row - 1));
                }catch(Exception exp){

                }
            }
        });
        about.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Process.setVisible(true);
               try {
                   about about = new about();
                   Toolkit kit = Toolkit.getDefaultToolkit();
                   Image icon = kit.createImage("./image/logo.png");
                   about.setTitle("Posty - About");
                   about.setIconImage(icon);
                   about.setVisible(true);
               }catch (Exception exp){

               }
                Process.setVisible(false);
            }
        });
        Lb1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Process.setVisible(true);
                table.setVisible(true);
                Nofound.setVisible(false);
                try {

                    MongoClientURI uri = new MongoClientURI("mongodb://admin:admin1234@ds046677.mlab.com:46677/posty");
                    MongoClient client = new MongoClient(uri);
                    DB db = client.getDB("posty");
                    try {
                        db.getCollection("post");
                    } catch (Exception exp) {
                        db.createCollection("username", null);
                    }
                    DBCollection collection = db.getCollection("post");
                    DBCursor cursor = null;
                    cursor = collection.find(new BasicDBObject("typeroom","Love"));
                    String[] columnNames = {"ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"};
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                    model.addRow(new Object[] { "ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"});
                    removeArray();
                    int run =1;
                    while(cursor.hasNext()) {
                        try{
                            DBObject obj = cursor.next();
                            Object id = obj.get("_id");
                            tempID.add(id+"");
                            String topic = (String)obj.get("topic");
                            String name = (String)obj.get("roomowner");
                            int topview = (int)obj.get("topview");
                            int countcomment = (int)obj.get("countcomment");
                            model.addRow(new Object[] { run,topic,name,topview,countcomment});
                            run ++;
                        }
                        catch(Exception exp){
                            System.out.println("E "+exp);
                        }
                    }
                    table.setModel(model);
                    cursor.close();

                    client.close();



                }catch (Exception exp){
                    System.out.println(exp);
                }
                Process.setVisible(false);

            }
        });
        Lb2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Process.setVisible(true);
                table.setVisible(true);
                Nofound.setVisible(false);
                try {

                    MongoClientURI uri = new MongoClientURI("mongodb://admin:admin1234@ds046677.mlab.com:46677/posty");
                    MongoClient client = new MongoClient(uri);
                    DB db = client.getDB("posty");
                    try {
                        db.getCollection("post");
                    } catch (Exception exp) {
                        db.createCollection("username", null);
                    }
                    DBCollection collection = db.getCollection("post");
                    DBCursor cursor = null;
                    cursor = collection.find(new BasicDBObject("typeroom","Game"));
                    String[] columnNames = {"ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"};
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                    model.addRow(new Object[] { "ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"});
                    removeArray();
                    int run =1;
                    while(cursor.hasNext()) {
                        try{
                            DBObject obj = cursor.next();
                            Object id = obj.get("_id");
                            tempID.add(id+"");
                            String topic = (String)obj.get("topic");
                            String name = (String)obj.get("roomowner");
                            int topview = (int)obj.get("topview");
                            int countcomment = (int)obj.get("countcomment");
                            model.addRow(new Object[] { run,topic,name,topview,countcomment});
                            run ++;
                        }
                        catch(Exception exp){
                            System.out.println("E "+exp);
                        }
                    }
                    table.setModel(model);
                    cursor.close();

                    client.close();



                }catch (Exception exp){
                    System.out.println(exp);
                }
                Process.setVisible(false);
            }
        });
        Lb3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Process.setVisible(true);
                table.setVisible(true);
                Nofound.setVisible(false);

                try {

                    MongoClientURI uri = new MongoClientURI("mongodb://admin:admin1234@ds046677.mlab.com:46677/posty");
                    MongoClient client = new MongoClient(uri);
                    DB db = client.getDB("posty");
                    try {
                        db.getCollection("post");
                    } catch (Exception exp) {
                        db.createCollection("username", null);
                    }
                    DBCollection collection = db.getCollection("post");
                    DBCursor cursor = null;
                    cursor = collection.find(new BasicDBObject("typeroom","Food"));
                    String[] columnNames = {"ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"};
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                    model.addRow(new Object[] { "ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"});
                    removeArray();
                    int run =1;
                    while(cursor.hasNext()) {
                        try{
                            DBObject obj = cursor.next();
                            Object id = obj.get("_id");
                            tempID.add(id+"");
                            String topic = (String)obj.get("topic");
                            String name = (String)obj.get("roomowner");
                            int topview = (int)obj.get("topview");
                            int countcomment = (int)obj.get("countcomment");
                            model.addRow(new Object[] { run,topic,name,topview,countcomment});
                            run ++;
                        }
                        catch(Exception exp){
                            System.out.println("E "+exp);
                        }
                    }
                    table.setModel(model);
                    cursor.close();

                    client.close();



                }catch (Exception exp){
                    System.out.println(exp);
                }
                Process.setVisible(false);
            }
        });
        Lb4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Process.setVisible(true);
                table.setVisible(true);
                Nofound.setVisible(false);
                try {

                    MongoClientURI uri = new MongoClientURI("mongodb://admin:admin1234@ds046677.mlab.com:46677/posty");
                    MongoClient client = new MongoClient(uri);
                    DB db = client.getDB("posty");
                    try {
                        db.getCollection("post");
                    } catch (Exception exp) {
                        db.createCollection("username", null);
                    }
                    DBCollection collection = db.getCollection("post");
                    DBCursor cursor = null;
                    cursor = collection.find(new BasicDBObject("typeroom","Animal"));
                    String[] columnNames = {"ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"};
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                    model.addRow(new Object[] { "ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"});
                    removeArray();
                    int run =1;
                    while(cursor.hasNext()) {
                        try{
                            DBObject obj = cursor.next();
                            Object id = obj.get("_id");
                            tempID.add(id+"");
                            String topic = (String)obj.get("topic");
                            String name = (String)obj.get("roomowner");
                            int topview = (int)obj.get("topview");
                            int countcomment = (int)obj.get("countcomment");
                            model.addRow(new Object[] { run,topic,name,topview,countcomment});
                            run ++;
                        }
                        catch(Exception exp){
                            System.out.println("E "+exp);
                        }
                    }
                    table.setModel(model);
                    cursor.close();

                    client.close();



                }catch (Exception exp){
                    System.out.println(exp);
                }
                Process.setVisible(false);
            }
        });
        Lb5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Process.setVisible(true);
                table.setVisible(true);
                Nofound.setVisible(false);

                try {

                    MongoClientURI uri = new MongoClientURI("mongodb://admin:admin1234@ds046677.mlab.com:46677/posty");
                    MongoClient client = new MongoClient(uri);
                    DB db = client.getDB("posty");
                    try {
                        db.getCollection("post");
                    } catch (Exception exp) {
                        db.createCollection("username", null);
                    }
                    DBCollection collection = db.getCollection("post");
                    DBCursor cursor = null;
                    cursor = collection.find(new BasicDBObject("typeroom","Catoon"));
                    String[] columnNames = {"ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"};
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                    model.addRow(new Object[] { "ห้องที่","ชื่อเรื่อง","ห้องของ", "จำนวนคอมเม้น","จำนวนยอดวิว"});
                    removeArray();
                    int run =1;
                    while(cursor.hasNext()) {
                        try{
                            DBObject obj = cursor.next();
                            Object id = obj.get("_id");
                            tempID.add(id+"");
                            String topic = (String)obj.get("topic");
                            String name = (String)obj.get("roomowner");
                            int topview = (int)obj.get("topview");
                            int countcomment = (int)obj.get("countcomment");
                            model.addRow(new Object[] { run,topic,name,topview,countcomment});
                            run ++;
                        }
                        catch(Exception exp){
                            System.out.println("E "+exp);
                        }
                    }
                    table.setModel(model);
                    cursor.close();

                    client.close();



                }catch (Exception exp){
                    System.out.println(exp);
                }

                Process.setVisible(false);
            }
        });
        postsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String massage = "กรุณาสมัครสมาชิก";

                JOptionPane.showMessageDialog(null,massage,"กรุณาสมัครสมาชิก",JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    public static void removeArray(){

    tempID.clear();


    }





}

