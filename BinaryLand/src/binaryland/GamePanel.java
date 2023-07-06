package binaryland;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements ActionListener, KeyListener{
    
    //Charcter Movements
    private boolean left=false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    
    //Character Position
    private int maleX = 270;
    private int maleY = 90;
    private int femaleX = 270;
    private int femaleY = 90;
    
    //Spider Position
    private int[] spiderX = new int[100];
    private int[] spiderY = new int[100];
    int spiderLength;
    private boolean[][] posSpider = new boolean[400][400];
    private int spidertime=0;
    private boolean[] spUp = new boolean[100];
    private boolean[] spDown = new boolean[100];
    private boolean[] spLeft = new boolean[100];
    private boolean[] spRight = new boolean[100];
    private boolean[][] close = new boolean[40][40]; 
    
    //Destroy nest and spider
    private boolean UpAttack=false;
    private boolean DownAttack=false;
    private boolean LeftAttack=false;
    private boolean RightAttack=false;
    
    //Levels
    private int level = 0;
    private boolean LevelCompleted = false; 
    private int direction = 1;
    private int lives=3;
    private int livescount=0;
    
    //Score
    private int score=0;
    private int highscore=0;
    private int highscore2=0;
    private String strHigh;
    
    
    //Obstacle placement(walls)
    private boolean[][] side = new boolean[200][200]; 
    
    //Obstacle placement(spider web)
    private boolean[][] web = new boolean[400][400];
    private int[] webX = new int[100];
    private int[] webY = new int[100];
    private int webLength=2;
    
    //Attacking direction 
    private int AttackTime=0;
    private boolean CaUp=false;
    private boolean CaDown=false;
    private boolean CaLeft=false;
    private boolean CaRight=false;
    private int att=2;
    
    //Importing necessary images\\
    //Title
    private ImageIcon GameTitle = new ImageIcon(getClass().getResource("Logo.png"));
    
    //User interface
    private ImageIcon GameCompleted = new ImageIcon(getClass().getResource("GameCompleted.png"));
    private ImageIcon GameFinished = new ImageIcon(getClass().getResource("GameFinished.png"));
    private ImageIcon GameOver = new ImageIcon(getClass().getResource("GameOver.png"));
    private ImageIcon NextLevel = new ImageIcon(getClass().getResource("Next.png"));
    private ImageIcon Restart = new ImageIcon(getClass().getResource("Restart.png"));
    private ImageIcon StartGame = new ImageIcon(getClass().getResource("StartGame.png"));
    private ImageIcon Welcome = new ImageIcon(getClass().getResource("Welcome.png"));
    
    //Walls and boxes
    private ImageIcon Sidewall = new ImageIcon(getClass().getResource("sideWall.png"));
    private ImageIcon Box1 = new ImageIcon(getClass().getResource("box1.png"));
    private ImageIcon Box2 = new ImageIcon(getClass().getResource("box2.png"));
    private ImageIcon Box3 = new ImageIcon(getClass().getResource("box3.png"));
    private ImageIcon HeartJail = new ImageIcon(getClass().getResource("HeartinJail.png"));
    private ImageIcon Heart = new ImageIcon(getClass().getResource("Heart.png"));
    private ImageIcon SpiderWeb = new ImageIcon(getClass().getResource("SpiderWeb.png"));
    private ImageIcon Bottomtext = new ImageIcon(getClass().getResource("Start.png"));
    private ImageIcon Toptext = new ImageIcon(getClass().getResource("End.png"));
    
    //Character Female movement icon
    private ImageIcon FemaleFront = new ImageIcon(getClass().getResource("FemaleFront.png"));
    private ImageIcon FemaleLeft = new ImageIcon(getClass().getResource("FemaleFrontLeft.png"));
    private ImageIcon FemaleRight = new ImageIcon(getClass().getResource("FemaleFrontRight.png"));
    private ImageIcon FemaleBack = new ImageIcon(getClass().getResource("FemaleBack.png"));
    
    //Character Female attack icon
    private ImageIcon FemaleALeft = new ImageIcon(getClass().getResource("FemaleAttackLeft.png"));
    private ImageIcon FemaleARight = new ImageIcon(getClass().getResource("FemaleAttackRight.png"));
    private ImageIcon FemaleAUp = new ImageIcon(getClass().getResource("FemaleAttackUp.png"));
    private ImageIcon FemaleADown = new ImageIcon(getClass().getResource("FemaleAttackDown.png"));
    
    //Character Male movement icon
    private ImageIcon MaleFront = new ImageIcon(getClass().getResource("MaleFront.png"));
    private ImageIcon MaleLeft = new ImageIcon(getClass().getResource("MaleFrontLeft.png"));
    private ImageIcon MaleRight = new ImageIcon(getClass().getResource("MaleFrontRight.png"));
    private ImageIcon MaleBack = new ImageIcon(getClass().getResource("MaleBack.png"));
   
    //Character Male attack icon
    private ImageIcon MaleALeft = new ImageIcon(getClass().getResource("MaleAttackLeft.png"));
    private ImageIcon MaleARight = new ImageIcon(getClass().getResource("MaleAttackRight.png"));
    private ImageIcon MaleAUp = new ImageIcon(getClass().getResource("MaleAttackUp.png"));
    private ImageIcon MaleADown = new ImageIcon(getClass().getResource("MaleAttackDown.png"));
    
    //Character Spider icon
    private ImageIcon SpiderDown = new ImageIcon(getClass().getResource("SpiderDown.png"));
    private ImageIcon SpiderUp = new ImageIcon(getClass().getResource("SpiderUp.png"));
    
    //Time
    private Timer timer;
    private int delay=100;
    
    GamePanel(){
        timer = new Timer(delay,this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0,0,1000,1000);
        g.setColor(Color.GRAY);
        g.fillRect(30,60,510,360);
        
        if(score>highscore)
            highscore=score;
        
        //Storing Highscore
        File myfile = new File("C:/Users/Tameem Ahamed/Desktop/BinaryLand/src/binaryland/saved.txt");
        try {
            myfile.createNewFile();
        } catch (IOException ex) {
            //Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scanner sss;
        try {
            sss = new Scanner(myfile);
            strHigh=sss.next();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            highscore2 = Integer.parseInt(strHigh);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        
        try {
            FileWriter filewriter = new FileWriter("C:/Users/Tameem Ahamed/Desktop/BinaryLand/src/binaryland/saved.txt");
            if(highscore2>highscore)
                highscore=highscore2;
            filewriter.write(""+highscore);
            
            filewriter.close();
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    if(lives==0){  
        level=-1;
        
    }
    if(level>0&&level<6){
        g.setColor(Color.PINK);
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.drawString("Level : "+level, 30, 20);
        g.setFont(new Font("Arial",Font.BOLD,18));
        g.drawString("Lives : "+lives, 30, 43);
        g.drawString("High score : "+strHigh,395, 20);
        g.drawString("Score : "+score, 395, 43);
        for(int i=2;i<17;i++){
            for(int j=3;j<13;j++){
                web[i][j]=false;                  
            }
        }
        GameTitle.paintIcon(this, g, 120, 3);
        for(int i=0;i<17;i++){
            for(int j=0;j<12;j++){
                if(i==0||i==16||j==0||j==11){
                    side[i+1][2+j]=true;
                    Sidewall.paintIcon(this, g,(i+1)*30,30+(j+1)*30);
                }
                else side[i+1][j+2]=false;
            }
        }
        for(int i=0;i<17;i++){
            for(int j=0;j<12;j++){
                if(i==8&&j!=1&&j!=0&&j!=11){
                    side[1+i][2+j]=true;
                }
            }
        }
        HeartJail.paintIcon(this, g, 30*9, 30*3);
        switch(direction){
            case 1:
                MaleFront.paintIcon(this, g, maleX, maleY);
                FemaleFront.paintIcon(this, g, femaleX, femaleY);
                break;
            case 2:
                MaleLeft.paintIcon(this, g, maleX, maleY);
                FemaleRight.paintIcon(this, g, femaleX, femaleY);
                break;
            case 3:
                MaleRight.paintIcon(this, g, maleX, maleY);
                FemaleLeft.paintIcon(this, g, femaleX, femaleY);
                break;
            case 4:
                MaleBack.paintIcon(this, g, maleX, maleY);
                FemaleBack.paintIcon(this, g, femaleX, femaleY);
                break;
        }
        if(AttackTime>0)
        {
            AttackTime--;
            if(CaUp){
                MaleAUp.paintIcon(this, g, maleX, maleY-30);
                FemaleAUp.paintIcon(this,g, femaleX, femaleY-30);
            }
            if(CaDown){
                MaleADown.paintIcon(this, g, maleX, maleY);
                FemaleADown.paintIcon(this,g, femaleX, femaleY);
            }
            if(CaLeft){
                MaleALeft.paintIcon(this, g, maleX-30, maleY);
                FemaleARight.paintIcon(this,g, femaleX, femaleY);
            }
            if(CaRight){
                MaleARight.paintIcon(this, g, maleX, maleY);
                FemaleALeft.paintIcon(this,g, femaleX-30, femaleY);
            }
        }
        else{
            CaUp=false;
            CaDown=false;
            CaRight=false;
            CaLeft=false;
        }
    }
        //Calling levels
        //creating levels and maps
        switch(level){
            case -1:
                GameOver.paintIcon(this, g, 100, 170);
                Restart.paintIcon(this, g, 100, 230);
                break;
            case 0:
                GameTitle.paintIcon(this, g, 150, 150);
                Welcome.paintIcon(this, g, 170,200);
                StartGame.paintIcon(this, g, 135,250);
                
                break;
            case 1:
               int i,j=11;
               for(i=3;i<16;i++){
                   side[i][j]=true;
               }
               j=9;
               i=2;
               side[i][j]=true;
               i=3;
               side[i][j]=true;
               i=5;
               side[i][j]=true;
               i=7;
               side[i][j]=true;
               i=11;
               side[i][j]=true;
               i=13;
               side[i][j]=true;
               i=16;
               side[i][j]=true;
               i=15;
               side[i][j]=true;
               j-=2;
               for(i=3;i<=7;i++){
                   side[i][j]=true;
               }
               for(i=11;i<16;i++){
                   side[i][j]=true;
               }
               j-=2;
               i=2;
               side[i][j]=true;
               i=3;
               side[i][j]=true;
               i=5;
               side[i][j]=true;
               i=7;
               side[i][j]=true;
               i=10;
               side[i][j]=true;
               i=11;
               side[i][j]=true;
               i=13;
               side[i][j]=true;
               i=15;
               side[i][j]=true;
               j--;
               for(i=3;i<6;i++){
                   side[i][j]=true;
               }
               i=7;
               side[i][j]=true;
               i=8;
               side[i][j]=true;
               for(i=11;i<=13;i++){
                   side[i][j]=true;
               }
               i=15;
               side[i][j]=true;
               i=16;
               side[i][j]=true;
               for(i=0;i<20;i++){
                   for(j=0;j<20;j++){
                       web[i][j]=false;
                   }
               }
               for(i=0;i<webLength;i++){
                   web[webX[i]][webY[i]]=true;
               }
               for(i=2;i<17;i++){
                   for(j=3;j<13;j++){
                       if(side[i][j]==true)
                           Box1.paintIcon(this, g, i*30, j*30);
                       if(web[i][j]==true){
                           SpiderWeb.paintIcon(this, g, i*30, j*30);
                       }
                   }
               }
               
               for(i=0;i<20;i++){
                   for(j=0;j<20;j++){
                       posSpider[i][j]=false;
                   }
               }
               
               for(i=0;i<webLength;i++){
                   posSpider[spiderX[i]][spiderY[i]]=true;
               }
               for(i=2;i<17;i++){
                   for(j=3;j<13;j++){
                       if(posSpider[i][j]==true)
                           SpiderDown.paintIcon(this,g, i*30, j*30);
                    }
               }
               spiderAutomove();
               
               break;
            case 2:
               
                side[7][12]=true;
                j=11;
                side[3][j]=true;
                side[5][j]=true;
                side[7][j]=true;
                side[10][j]=true;
                side[11][j]=true;
                side[13][j]=true;
                side[15][j]=true;
                side[16][j]=true;
                j--;
                side[5][j]=true;
                j--;
                for(i=2;i<16;i++){
                    if(i==4||i==8||i==10) continue;
                    side[i][j]=true;
                }
                j-=2;
                for(i=3;i<17;i++){
                    if(i==6||i==12||i==14) continue;
                    side[i][j]=true;
                }
                j--;
                side[5][j]=true;
                side[13][j]=true;
                j--;
                for(i=3;i<16;i++){
                    if(i==4||i==6||i==8||i==10) continue;
                    side[i][j]=true;
                }
                j--;
                for(i=3;i<16;i++){
                    if(i==4||i==6||i==12||i==14) continue;
                    side[i][j]=true;
                }
                for(i=0;i<20;i++){
                   for(j=0;j<20;j++){
                       web[i][j]=false;
                   }
               }
               for(i=0;i<webLength;i++){
                   web[webX[i]][webY[i]]=true;
               }
               for(i=2;i<17;i++){
                   for(j=3;j<13;j++){
                       if(side[i][j]==true)
                           Box3.paintIcon(this, g, i*30, j*30);
                       if(web[i][j]==true){
                           SpiderWeb.paintIcon(this, g, i*30, j*30);
                       }
                   }
               }
               for(i=0;i<20;i++){
                   for(j=0;j<20;j++){
                       posSpider[i][j]=false;
                   }
               }
               
               for(i=0;i<spiderLength;i++){
                   posSpider[spiderX[i]][spiderY[i]]=true;
               }
               for(i=2;i<17;i++){
                   for(j=3;j<13;j++){
                       if(posSpider[i][j]==true)
                           SpiderDown.paintIcon(this,g, i*30, j*30);
                    }
               }
               spiderAutomove();
               break;
            case 3:
                j=3;
                for(i=4;i<=12;i++){
                    if(i==8) continue;
                    side[j][i]=true;
                }
                for(i=3;i<=8;i++){
                    side[i][7]=true;
                }
                i=5;
                for(j=3;j<12;j++){
                    if(j==6) continue;
                    side[i][j]=true;
                }
                i=7;
                for(j=4;j<13;j++){
                    if(j==8) continue;
                    side[i][j]=true;
                }
                side[10][9]=true;
                side[11][4]=true;
                for(i=5;i<=11;i+=2){
                    side[11][i]=true;
                }
                side[12][7]=true;
                for(j=4;j<=11;j++){
                    if(j==8) continue;
                    side[i][j]=true;
                }
                side[14][9]=true;
                side[15][4]=true;
                i=15;
                for(j=5;j<=11;j+=2){
                    side[i][j]=true;
                }
                side[15][12]=true;
                side[16][4]=true;
                side[16][7]=true;
                for(i=0;i<20;i++){
                   for(j=0;j<20;j++){
                       web[i][j]=false;
                   }
               }
               for(i=0;i<webLength;i++){
                   web[webX[i]][webY[i]]=true;
               }
               for(i=2;i<17;i++){
                   for(j=3;j<13;j++){
                       if(side[i][j]==true)
                           Box1.paintIcon(this, g, i*30, j*30);
                       if(web[i][j]==true){
                           SpiderWeb.paintIcon(this, g, i*30, j*30);
                       }
                   }
               }
               for(i=0;i<20;i++){
                   for(j=0;j<20;j++){
                       posSpider[i][j]=false;
                   }
               }
               
               for(i=0;i<spiderLength;i++){
                   posSpider[spiderX[i]][spiderY[i]]=true;
               }
               for(i=2;i<17;i++){
                   for(j=3;j<13;j++){
                       if(posSpider[i][j]==true)
                           SpiderDown.paintIcon(this,g, i*30, j*30);
                    }
               }
                
                spiderAutomove();
                break;
            case 4:
                j=4;
                side[15][3]=true;
                for(i=3;i<16;i++){
                    if(i==6||i==12||i==14) continue;
                    side[i][j]=true;
                }
                j++;
                side[2][j]=true;
                for(i=3;i<16;i+=2){
                    side[i][j]=true;
                }
                j++;
                side[13][j]=true;
                j++;
                for(i=3;i<16;i++){
                    if(i==8||i==12) continue;
                    side[i][j]=true;
                }
                j++;
                side[11][j]=true;
                j++;
                for(i=3;i<8;i+=2){
                    side[i][j]=true;
                }
                i=8;
                side[i][j]=true;
                for(i=11;i<17;i++){
                    if(i==14) continue;
                    side[i][j]=true;
                }
                j++;
                side[13][j]=true;
                j++;
                for(i=2;i<8;i++){
                    side[i][j]=true;
                }
                for(i=11;i<=15;i+=2){
                    side[i][j]=true;
                }
                j++;
                side[11][j]=true;
                for(i=0;i<20;i++){
                   for(j=0;j<20;j++){
                       web[i][j]=false;
                   }
               }
               for(i=0;i<webLength;i++){
                   web[webX[i]][webY[i]]=true;
               }
               for(i=2;i<17;i++){
                   for(j=3;j<13;j++){
                       if(side[i][j]==true)
                           Box2.paintIcon(this, g, i*30, j*30);
                       if(web[i][j]==true){
                           SpiderWeb.paintIcon(this, g, i*30, j*30);
                       }
                   }
               }
               for(i=0;i<20;i++){
                   for(j=0;j<20;j++){
                       posSpider[i][j]=false;
                   }
               }
               
               for(i=0;i<spiderLength;i++){
                   posSpider[spiderX[i]][spiderY[i]]=true;
               }
               for(i=2;i<17;i++){
                   for(j=3;j<13;j++){
                       if(posSpider[i][j]==true)
                           SpiderDown.paintIcon(this,g, i*30, j*30);
                    }
               }
               spiderAutomove();
               
               break;
            case 5:
                i=2;
                side[i][4]=true;
                side[i][7]=true;
                i++;
                for(j=4;j<13;j++){
                    if(j==6||j==10) continue;
                    side[i][j]=true;
                }        
                i++;
                side[i][9]=true;
                i++;
                for(j=4;j<12;j++){
                    if(j==8) continue;
                    side[i][j]=true;
                }
                i++;
                side[i][7]=true;
                i++;
                for(j=3;j<6;j++){
                    side[i][j]=true;
                }
                for(j=7;j<12;j+=2){
                    side[i][j]=true;
                }
                i++;
                side[i][9]=true;
                i+=2;//i = 10
                side[i][5]=true;
                side[i][9]=true;
                i++;
                side[i][4]=true;
                for(j=5;j<12;j+=2){
                    side[i][j]=true;
                }
                i++;
                side[i][7]=true;
                side[i][11]=true;
                i++;
                side[i][4]=true;
                for(j=5;j<12;j+=2){
                    side[i][j]=true;
                }
                i++;//i=14
                side[i][5]=true;
                side[i][9]=true;
                i++;
                side[i][4]=true;
                for(j=5;j<12;j+=2){
                    side[i][j]=true;
                }
                i++;
                side[i][7]=true;
                side[i][11]=true;
                
                for(i=0;i<20;i++){
                   for(j=0;j<20;j++){
                       web[i][j]=false;
                   }
               }
               for(i=0;i<webLength;i++){
                   web[webX[i]][webY[i]]=true;
               }
               for(i=2;i<17;i++){
                   for(j=3;j<13;j++){
                       if(side[i][j]==true)
                           Box3.paintIcon(this, g, i*30, j*30);
                       if(web[i][j]==true){
                           SpiderWeb.paintIcon(this, g, i*30, j*30);
                       }
                   }
               }
               for(i=0;i<20;i++){
                   for(j=0;j<20;j++){
                       posSpider[i][j]=false;
                   }
               }
               
               for(i=0;i<spiderLength;i++){
                   posSpider[spiderX[i]][spiderY[i]]=true;
               }
               for(i=2;i<17;i++){
                   for(j=3;j<13;j++){
                       if(posSpider[i][j]==true)
                           SpiderDown.paintIcon(this,g, i*30, j*30);
                    }
               }
                spiderAutomove();
                break;
            case 6:
                break;
        }
        if(levelEnd()){
            timer.stop();
            level++; 
            if(lives==0)
                score=0;
            if(lives<3) lives++;
            while(level<=0){
                level++;
                lives=3;
            }
            maleX=300;
            maleY=360;
            femaleX=240;
            femaleY=360;
            int i,j;
            for(i=0;i<20;i++){
                for(j=0;j<20;j++){
                    close[i][j]=false;
                }
            }
            if(level==6){
                score+=50;
                highscore+=50;
                if(score>highscore)
                    highscore=score;
                score=0;
                GameFinished.paintIcon(this, g, 100, 200);
                Restart.paintIcon(this, g, 100, 260);
                level=1;
                lives=3;
            }
            else if(level>1){
                score+=50;
                spiderSetTrue();
                GameCompleted.paintIcon(this, g, 100, 200);
                NextLevel.paintIcon(this, g, 100, 260);
                for(i=2;i<17;i++){
                   for(j=4;j<13;j++){
                       side[i][j]=false;
                   }
               }
            }
        //Creating level for spider nest etc    
            g.dispose();
            if(level==1){
                webX[0]=4;
                webY[0]=6;
                webX[1]=14;
                webY[1]=8;
                webLength=2;
                spiderX[0]=2;
                spiderY[0]=3;
                spiderLength=1;
                close[15][3]=true;
                spiderSetTrue();
            }
            if(level==2){
                webLength=8;
                webX[0]=2;
                for(i=0;i<3;i++){
                    webY[i]=6;
                }
                webX[3]=4;
                webY[3]=8;
                webX[4]=10;
                webY[4]=8;
                webX[1]=8;
                webX[2]=16;
                webX[5]=2;
                webX[6]=7;
                webX[7]=14;
                for(i=5;i<=7;i++){
                    webY[i]=10;
                }
                spiderLength=2;
                spiderX[0]=5;
                spiderY[0]=3;
                spiderX[1]=12;
                spiderY[1]=6;
                close[16][4]=true;
                spiderSetTrue();
            }
            if(level==3){
                webLength=16;
                for(i=0;i<4;i++){
                    webX[i]=2;
                }                
                for(i=0;i<3;i++){
                    webY[i]=i+4;
                }
                webY[3]=10;
                webX[4]=3;
                webY[4]=8;
                for(i=5;i<7;i++){
                    webX[i]=4;
                }
                webY[5]=5;
                webY[6]=11;
                for(i=7;i<9;i++){
                    webX[i]=6;
                }
                webY[7]=3;
                webY[8]=8;
                webX[9]=webX[10]=8;
                webX[11]=webX[12]=10;
                webY[9]=5;
                webY[10]=10;
                webY[11]=6;
                webY[12]=10;
                webX[13]=12;
                webY[13]=8;
                webY[14]=6;
                webX[14]=16;
                webX[15]=15;
                webY[15]=10;
                spiderLength=4;
                spiderX[0]=2;
                spiderY[0]=3;
                spiderX[1]=16;
                spiderY[1]=3;
                spiderX[2]=10;
                spiderY[2]=8;
                spiderX[3]=11;
                spiderY[3]=6;
                close[10][7]=true;
                close[10][5]=true;
                close[12][5]=true;
                close[15][3]=true;
                close[14][7]=true;
                spiderSetTrue();
            }
            if(level==4){
                webLength=6;
                j=6;
                webX[0]=10;
                webX[1]=16;
                webY[0]=j;
                webY[1]=j;
                j+=2;
                webY[2]=j;
                webY[3]=j;
                webX[2]=6;
                webX[3]=14;
                j+=2;
                webX[4]=11;
                webX[5]=16;
                webY[4]=j;
                webY[5]=j;
                spiderLength=5;
                spiderX[0]=10;
                spiderY[0]=6;
                spiderX[1]=3;
                spiderY[1]=6;
                spiderX[2]=2;
                spiderY[2]=3;
                spiderX[3]=16;
                spiderY[3]=3;
                spiderX[4]=12;
                spiderY[4]=8;
                close[16][7]=true;
                close[15][6]=true;
                close[12][7]=true;
                close[14][9]=true;
                close[10][5]=true;
                spiderSetTrue();
            }
            if(level==5){
                webLength=12;
                j=6;
                for(i=0;i<3;i++){
                    webY[i]=j;
                }
                webX[0]=2;
                webX[1]=8;
                webX[2]=16;
                j+=2;
                for(i=3;i<7;i++){
                    webY[i]=j;
                }
                webX[3]=6;
                webX[4]=10;
                webX[5]=12;
                webX[6]=14;
                j+=2;
                for(i=7;i<11;i++){
                    webY[i]=j;
                }
                webX[7]=3;
                webX[8]=8;
                webX[9]=10;
                webX[10]=16;
                webY[11]=12;
                webX[11]=12;
                
                spiderLength=6;
                spiderX[0]=10;
                spiderY[0]=6;
                close[15][6]=true;
                spiderX[1]=15;
                spiderY[1]=3;
                close[11][10]=true;
                spiderX[2]=12;
                spiderY[2]=8;
                close[8][5]=true;
                close[6][5]=true;
                close[8][7]=true;
                close[4][7]=true;
                spiderX[3]=8;
                spiderY[3]=6;
                spiderX[4]=5;
                spiderY[4]=8;
                close[6][9]=true;
                close[6][11]=true;
                close[8][11]=true;
                spiderY[5]=10;
                spiderX[5]=6;
                spiderSetTrue();
            }
            if(level==6){
                level=1;
                webX[0]=4;
                webY[0]=6;
                webX[1]=14;
                webY[1]=8;
                webLength=2;
                spiderX[0]=2;
                spiderY[0]=3;
                spiderLength=1;
                close[15][3]=true;
                spiderSetTrue();
            }
        }
        collidesWithSpider();
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(up){
            maleY-=30;
            femaleY-=30;
            if(side[maleX/30][maleY/30]||web[maleX/30][maleY/30]){
                maleY+=30;
            }
            if(side[femaleX/30][femaleY/30]||web[femaleX/30][femaleY/30]){
                femaleY+=30;
            }
            up=false;
        }
        if(down){
            maleY+=30;
            if(side[maleX/30][maleY/30]||web[maleX/30][maleY/30]){
                maleY-=30;
            }
            femaleY+=30;
            if(side[femaleX/30][femaleY/30]||web[femaleX/30][femaleY/30]){
                femaleY-=30;
            }
            down=false;
        }
        if(right){
            maleX+=30;
            if(side[maleX/30][maleY/30]||web[maleX/30][maleY/30]){
                maleX-=30;
            }
            femaleX-=30;
           if(side[femaleX/30][femaleY/30]||web[femaleX/30][femaleY/30]){
                femaleX+=30;
            }
            right=false;
        }
        if(left){
            maleX-=30;
            if(side[maleX/30][maleY/30]||web[maleX/30][maleY/30]){
                maleX+=30;
            }
            femaleX+=30;
            if(side[femaleX/30][femaleY/30]||web[femaleX/30][femaleY/30]){
                femaleX-=30;
            }
            left=false;
        }
        
        //Attack the nest and spider
        if(UpAttack){
            int x,y;
            x=maleX/30;
            y=maleY/30;
            y--;
            for(int i=0;i<webLength;i++){
                if(webX[i]==x&&webY[i]==y){
                    webX[i]=20;
                    webY[i]=25;
                    score+=5;
                }
            }
            spiderDestroy(x,y);
            x=femaleX/30;
            y=femaleY/30;
            y--;
            for(int i=0;i<webLength;i++){
                if(webX[i]==x&&webY[i]==y){
                    webX[i]=20;
                    webY[i]=25;
                    score+=5;
                }
            }
            spiderDestroy(x,y);
            UpAttack=false;
        }
        if(DownAttack){
            int x,y;
            x=maleX/30;
            y=maleY/30;
            y++;
            for(int i=0;i<webLength;i++){
                if(webX[i]==x&&webY[i]==y){
                    webX[i]=20;
                    webY[i]=25;
                    score+=5;
                }
            }
            spiderDestroy(x,y);
            
            x=femaleX/30;
            y=femaleY/30;
            y++;
            for(int i=0;i<webLength;i++){
                if(webX[i]==x&&webY[i]==y){
                    webX[i]=20;
                    webY[i]=25;
                    score+=5;
                }
            }
            spiderDestroy(x,y);
            DownAttack=false;
        }
        if(RightAttack){
            int x,y;
            x=maleX/30;
            x++;
            y=maleY/30;
            for(int i=0;i<webLength;i++){
                if(webX[i]==x&&webY[i]==y){
                    webX[i]=20;
                    webY[i]=25;
                    score+=5;
                }
            }
            spiderDestroy(x,y);
            
            x=femaleX/30;
            x--;
            y=femaleY/30;
            for(int i=0;i<webLength;i++){
                if(webX[i]==x&&webY[i]==y){
                    webX[i]=20;
                    webY[i]=25;
                    score+=5;
                }
            }
            spiderDestroy(x,y);
            RightAttack=false;
        }
        if(LeftAttack){
            int x,y;
            x=maleX/30;
            x--;
            y=maleY/30;
            for(int i=0;i<webLength;i++){
                if(webX[i]==x&&webY[i]==y){
                    webX[i]=20;
                    webY[i]=25;
                    score+=5;
                }
            }
            spiderDestroy(x,y);
            
            x=femaleX/30;
            x++;
            y=femaleY/30;
            for(int i=0;i<webLength;i++){
                if(webX[i]==x&&webY[i]==y){
                    webX[i]=20;
                    webY[i]=25;
                    score+=5;
                }
            }
            spiderDestroy(x,y);
            LeftAttack=false;
        }
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            timer.start();
            direction=1;
            //level++;
            if(level==0){
                level++;
                maleX=300;
                maleY=360;
                femaleX=240;
                femaleY=360;
            }
            else if(level==-1){
                level=1;
                lives=3;
            }
            repaint();
        }
        if(e.getKeyCode()==KeyEvent.VK_A){
            left=true;
            direction = 2;
        }
        if(e.getKeyCode()==KeyEvent.VK_D){
            right=true;
            direction = 3;
        }
        if(e.getKeyCode()==KeyEvent.VK_W){
            up=true;
            direction = 4;
        }
        if(e.getKeyCode()==KeyEvent.VK_S){
            down=true;
            direction = 4;
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            LeftAttack=true;
            CaLeft=true;
            AttackTime=att;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            RightAttack=true;
            CaRight=true;
            AttackTime=att;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP){
            UpAttack=true;
            CaUp=true;
            AttackTime=att;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN){
            DownAttack=true;
            CaDown=true;
            AttackTime=att;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public boolean levelEnd(){
        if(level==-1){
            for(int i=0;i<spiderLength;i++){
                spiderX[i]=25;
                spiderY[i]=25;
            }
            return true;
        }
        if(maleX==270&&femaleX==270&&maleY==90&&femaleY==90)
            return true;
        return false;
    }
   
    public void spiderDestroy(int x, int y){
        for(int i=0;i<spiderLength;i++){
            if(spiderX[i]==x&&spiderY[i]==y){
                spiderX[i]=25;
                spiderY[i]=25;
                score+=10;
            }
        }
    }
    public void spiderAutomove(){
        spidertime++;
        if(spidertime>2){
            spidertime=0;
            for(int i=0;i<spiderLength;i++){
                if(spiderX[i]==25&&spiderY[i]==25){
                    spRight[i]=false;
                    spLeft[i]=false;
                    spUp[i]=false;
                    spDown[i]=false;
                    continue;   
                }
                
                if((side[spiderX[i]+1][spiderY[i]]==true||close[spiderX[i]+1][spiderY[i]]==true)&&spRight[i]==true){
                   spRight[i]=false;
                   spUp[i]=false;
                   spDown[i]=true;
                   spLeft[i]=false;
                }
                if((side[spiderX[i]][spiderY[i]+1]==true||close[spiderX[i]][spiderY[i]+1]==true)&&spDown[i]==true){
                   spRight[i]=false;
                   spUp[i]=false;
                   spDown[i]=false;
                   spLeft[i]=true;
                }
                if((side[spiderX[i]-1][spiderY[i]]==true||close[spiderX[i]-1][spiderY[i]]==true)&&spLeft[i]==true){
                   spRight[i]=false;
                   spUp[i]=true;
                   spDown[i]=false;
                   spLeft[i]=false;
                }
                if((side[spiderX[i]][spiderY[i]-1]==true||close[spiderX[i]][spiderY[i]-1]==true)&&spUp[i]==true){
                   spRight[i]=true;
                   spUp[i]=false;
                   spDown[i]=false;
                   spLeft[i]=false;
                   if(side[spiderX[i]+1][spiderY[i]]==true){
                       spRight[i]=false;
                       spLeft[i]=true;
                       if(side[spiderX[i]-1][spiderY[i]]==true){
                           spDown[i]=true;
                           spLeft[i]=false;
                           if(side[spiderX[i]][spiderY[i]+1]==true){
                               spDown[i]=false;
                           }
                       }
                   }
                }
                if(spLeft[i]){
                    spiderX[i]--;
                }
                if(spRight[i]){
                    spiderX[i]++;
                }
                if(spUp[i]){
                    spiderY[i]--;
                }
                if(spDown[i]){
                    spiderY[i]++;
                }
            }
        }
    }
    public void spiderSetTrue(){
        for(int i=0;i<spiderLength;i++){
            spRight[i]=true;
            spUp[i]=false;
            spDown[i]=false;
            spLeft[i]=false;
        }
    }
    
     public void collidesWithSpider(){
        for(int i=0;i<spiderLength;i++){
            if(livescount>0){
                livescount--;
                continue;
            }
            if(spiderX[i]*30==maleX&&spiderY[i]*30==maleY){
                lives--;
                livescount=30;
            }
            else if(spiderX[i]*30==femaleX&&spiderY[i]*30==femaleY){
                lives--;
                livescount=30;
            }
        }
    }
}
