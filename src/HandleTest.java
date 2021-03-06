//File Describe:Test System Handle Program
//DATE:2014-12-20
//Author Contact: imhexingye@163.com

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
public class HandleTest implements ActionListener {    
   Test  view; 
   String [][]xuanzeRecord;         //全部的选择题
   //String [][]panduanRecord;        //全部的判断题
   int  typeXuanzeAmount;     //需要的两种题目的个数
   //LinkedList<String []> panduanList; //存放判断题的链表，以便随机抽取
   LinkedList<String []> xuanzeList;
   int tihao=1;                   //题号
   int score = 0;                     //得分
   int error =0;                      //错题数目
   String answer ;                   //正确答案
   PreQuery query;                   //见第2天任务2或第3天任务3
   HandleTest() {
      query=new PreQuery();
      //panduanList = new LinkedList<String []>();
      xuanzeList = new LinkedList<String []>();
   }
   public void setAmount(int a) {
      //typePanduanAmount = a[0];
      typeXuanzeAmount = a;
   }
   public void setExamView(Test v) {
      view =v;
   }
   public void actionPerformed(ActionEvent e) {
       
       if(e.getSource()==view.radioA) {
          view.showChoice.setText(view.radioA.getText());  
       }
       else if(e.getSource()==view.radioB) {
          view.showChoice.setText(view.radioB.getText());  
       }
       else if(e.getSource()==view.radioC) {
          view.showChoice.setText(view.radioC.getText());  
       }
       else if(e.getSource()==view.radioD) {
          view.showChoice.setText(view.radioD.getText());  
       }
       if(e.getSource()==view.buttonStart) {
         score = 0;
         error = 0; 
         tihao = 1;
         answer ="";
         view.showErrorAmount.setText("错题数目："+error);
         view.buttonStart.setEnabled(false);
         view.nextQuesion.setEnabled(true);
         query.setDatabaseName("TiKu");        
         query.setSQL("SELECT * FROM xuanzeT ");
         query.startQuery();
         String [][]xuanzeRecord =query.getRecord();
         for(int i=0;i<xuanzeRecord.length;i++) {
            xuanzeList.add(xuanzeRecord[i]);
         }
         getOneOfXuanzeTi(); 
       }
       if(e.getSource()==view.nextQuesion) {
           String userAnwer=view.showChoice.getText();
           if(userAnwer.equalsIgnoreCase(answer)) {
               score++;
           }
           else {
               error++;
               view.showErrorAmount.setText("错题数目："+error+"|");
           }
          if(typeXuanzeAmount>0)
               getOneOfXuanzeTi();
           else if(typeXuanzeAmount==0){
               view.nextQuesion.setEnabled(false);
               JOptionPane.showMessageDialog
               (null,"分数"+score,"分数"+score, JOptionPane.WARNING_MESSAGE);
               view.buttonStart.setEnabled(true);
              
           }
        }
        if(e.getSource()==view.submitButton) { //应该将考生考号、分数写入数据库
           view.nextQuesion.setEnabled(false); //这里仅仅用对话框显示了分数
           JOptionPane.showMessageDialog
           (null,"分数"+score,"分数"+score, JOptionPane.WARNING_MESSAGE);
           view.buttonStart.setEnabled(true);
          //*************************
          //try{}
          //catch(IOException e){}
        
          //**************************
       }
    }

    private  void getOneOfXuanzeTi(){
         view.init();   //让视图呈现回答选择题的外观
         Random  random = new Random();
         int m=random.nextInt(xuanzeList.size()); 
         String [] shiti = xuanzeList.get(m);
         view.showQuesion.setText(tihao+"\n"+shiti[1]);
         view.showQuesion.append("\nA."+shiti[2]);
         view.showQuesion.append("\nB."+shiti[3]);
         view.showQuesion.append("\nC."+shiti[4]);
         view.showQuesion.append("\nD."+shiti[5]);
         view.radioA.setText("A"); 
         view.radioB.setText("B");
         view.radioC.setText("C"); 
         view.radioD.setText("D");
         answer = shiti[6];
         xuanzeList.remove(m);
         typeXuanzeAmount--;
         tihao++;
    }
}