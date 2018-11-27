import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jdk.nashorn.internal.objects.Global;

import java.lang.*;
import java.io.*;
import java.util.*;
// TODO -- 2. Add ability to subtract study time if you do not feel it was benifical
// TODO -- 3. add functionality with the statusbar https://stackoverflow.com/questions/10663490/mac-status-bar-item-but-not-on-dock
// Turn into package then jar with a manifest file
public class BasicPanda {
    public static long GlobalCounter, DMCount, SECount, CSCount, PFLCount, startTimer, endTimer;
    public static int State, subject;
    public static String sub1, sub2, sub3, sub4;
    public static JFrame subtractTime;

    public BasicPanda(){
        constructGUI();       
    }
    public static void main(String[] args){
        sub1 = "Sub1";
        sub2 = "Sub2";
        sub3 = "Sub3";
        sub4 = "Sub4";
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "PandaMemoranda");
        readSaves();
        subject = 4; //Non-selected State
        BasicPanda basicPanda = new BasicPanda();
    }

    public void constructGUI(){
        //Frames
        JFrame frame = new JFrame("Panda Memoranda"); //Names the frame
        frame.setSize(420,600); //400x500 window size
        //Border
        //Border emptyBorder = BorderFactory.createEmptyBorder();
        //Panels
        JPanel panel = new JPanel(); //Creates the Panel that all text and buttons will be placed onto
        panel.setBackground(Color.white);
        //Labels 
        JLabel label = new JLabel("Click the panda to start/pause the timer"); //Creates the heading
        JLabel timeDM = new JLabel(); //The label for the time
        JLabel timeSE = new JLabel(); // Software Engineering time
        JLabel timeCS = new JLabel(); // Computer Systems time
        JLabel timePFL = new JLabel(); // PFL time
        JLabel timeOV = new JLabel(); // Overall time
        JLabel state = new JLabel(); //The label indicating which state the program is in
        JLabel errorLabel = new JLabel();
        JLabel studytimeStatement = new JLabel("The current time spent studying for each subject:");
        //Images
        ImageIcon pandaimg = new ImageIcon("images/panda.jpg");
        ImageIcon redimg = new ImageIcon("images/redimg.PNG");
        ImageIcon greenimg = new ImageIcon("images/greenimg.PNG");
        ImageIcon blueimg = new ImageIcon("images/blueimg.PNG");
        ImageIcon purpleimg = new ImageIcon("images/purpleimg.PNG");
        //Buttons
        JButton pandaButton = new JButton(pandaimg); pandaButton.setBorderPainted(false);
        JButton DMMRButton = new JButton(sub1, redimg);
        JButton CSButton = new JButton(sub2, blueimg); 
        JButton PFLButton = new JButton(sub4, purpleimg);
        JButton SEButton = new JButton(sub3, greenimg); 
        JButton save = new JButton("  SAVE  ");
        JButton subTime = new JButton("SUBTRACT TIME");
        JButton reset = new JButton("RESET STATS"); 
        JButton editSubs = new JButton("Change Subs");
        //SetLabels
        state.setText("Current State: Not Studying!");
        timeOV.setText("Time Studied Overall: " + GlobalCounter);
        long[] timesDM = convertTime(DMCount);
        long[] timesCS = convertTime(CSCount);
        long[] timesSE = convertTime(SECount);
        long[] timesPFL = convertTime(PFLCount);
        timeDM.setText(sub1  + ": " + timesDM[0] + " hours " + timesDM[1] + " mins " + timesDM[2] + " secs");
        timeSE.setText(sub3  + ": " + timesSE[0] + " hours " + timesSE[1] + " mins " + timesSE[2] + " secs");
        timeCS.setText(sub2  + ": " + timesCS[0] + " hours " + timesCS[1] + " mins " + timesCS[2] + " secs");
        timePFL.setText(sub4  + ": " + timesPFL[0] + " hours " + timesPFL[1] + " mins " + timesPFL[2] + " secs");
        //ButtonActions
        pandaButton.addActionListener(new ActionListener() { //The action the button will carry out when pressed
            public void actionPerformed(ActionEvent e) {
                if (State == 0){
                    startTimer = System.currentTimeMillis(); //Gets time from when user started studying
                    state.setText("Current State: Studying!");
                    State ++; //Switches state to studying
                    switch(subject){
                        case 0: panel.setBackground(Color.red); break;
                        case 1: panel.setBackground(Color.blue); break;
                        case 2: panel.setBackground(Color.green); break;
                        case 3: panel.setBackground(Color.magenta); break;
                        case 4:  //User did not select a subject and this cancels the timer reminding them to select a subject
                        errorLabel.setText("!!!!!!!!! You have forgotton to select a subject !!!!!!!!!"); //Displays error message
                        state.setText("Current State: Not Studying!");
                        panel.setBackground(Color.white);
                        State --;
                        break;
                    }
                } else {
                    endTimer = System.currentTimeMillis();
                    long difference = endTimer - startTimer; //Calculates time spent studying for that session
                    panel.setBackground(Color.white);
                    switch(subject){
                        case 0:
                            DMCount += difference/1000;
                            long[] timesDM = convertTime(DMCount);
                            timeDM.setText(sub1 + ": " + timesDM[0] + " hours " + timesDM[1] + " mins " + timesDM[2] + " secs");
                            break;
                        case 1: 
                            CSCount += difference/1000;
                            long[] timesCS = convertTime(CSCount);
                            timeCS.setText(sub2 + ": "  + timesCS[0] + " hours " + timesCS[1] + " mins " + timesCS[2] + " secs");
                            break;
                        case 2: 
                            SECount += difference/1000;
                            long[] timesSE = convertTime(SECount);
                            timeSE.setText(sub3  + ": " + timesSE[0] + " hours " + timesSE[1] + " mins " + timesSE[2] + " secs");
                            break;
                        case 3: 
                            PFLCount += difference/1000;
                            long[] timesPFL = convertTime(PFLCount);
                            timePFL.setText(sub4  + ": " + timesPFL[0] + " hours " + timesPFL[1] + " mins " + timesPFL[2] + " secs");
                            break;
                    }
                    GlobalCounter += (difference/1000);
                    timeOV.setText("Time Studied Overall: " + GlobalCounter);
                    state.setText("Current State: Not Studying!");
                    State --; //Switches state to not studying
                }
                
            }
         });
        DMMRButton.addActionListener(new ActionListener() { //Sets the current subject to be studied to the appropriate subject
            public void actionPerformed(ActionEvent e){
                subject = 0;
                errorLabel.setText(""); //Removes error label
            }
        });
        CSButton.addActionListener(new ActionListener() { //CS
            public void actionPerformed(ActionEvent e){
                subject = 1;
                errorLabel.setText("");
            }
        });
        SEButton.addActionListener(new ActionListener() { //SE
            public void actionPerformed(ActionEvent e){
                subject = 2;
                errorLabel.setText("");
            }
        });
        PFLButton.addActionListener(new ActionListener() { //PFL
            public void actionPerformed(ActionEvent e){
                subject = 3;
                errorLabel.setText("");
            }
        });
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                saveData();
            }
        });
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                reset(timeDM, timeCS, timeSE, timePFL, timeOV);
            }
        });
        subTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (State == 1){
                    errorLabel.setText("!!!!!!!!! Program Cannot be running !!!!!!!!!");
                } else if (State == 0){
                    errorLabel.setText("");
                    subTime(timeDM, timeCS, timeSE, timePFL, timeOV);
                }
            }
        });
        editSubs.addActionListener(new ActionListener() { //Sets the current subject to be studied to the appropriate subject
            public void actionPerformed(ActionEvent e){
                changeSubs(timeDM, timeCS, timeSE, timePFL, DMMRButton, CSButton, SEButton, PFLButton);
            }
        });
        panel.add(reset);panel.add(label); panel.add(pandaButton); panel.add(errorLabel);panel.add(state);panel.add(timeOV); //Fills the panel with all elements
        panel.add(DMMRButton);panel.add(CSButton);panel.add(SEButton);panel.add(PFLButton); panel.add(studytimeStatement);
        panel.add(timeDM);panel.add(timeCS);panel.add(timeSE);panel.add(timePFL);panel.add(save);panel.add(subTime);panel.add(editSubs);

        frame.add(panel); //Make sure Panel is last thing added
        frame.setVisible(true);
    }

    public static void saveData(){
        try {
            FileWriter saver = new FileWriter("saves.txt"); //Writes each subject count to a saves.txt
            saver.write(((int) GlobalCounter) + "\n");
            saver.write(((int) DMCount) + "\n");
            saver.write(((int) CSCount) + "\n");
            saver.write(((int) SECount) + "\n");
            saver.write(((int) PFLCount + "\n"));
            saver.write(sub1 + "\n");
            saver.write(sub2 + "\n");
            saver.write(sub3 + "\n");
            saver.write(sub4 + "\n");
            saver.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void readSaves(){ 
        String fileData = null;
        int cursubject = 0;
        String curint = "";
        try{
            fileData = readFileAsString("saves.txt"); //Fills fileData with data in saves.txt
        } catch(IOException e){
            e.printStackTrace();
        }
        int len = fileData.length(); 
        for (int i = 0; i < len; ++i){
            
            if (fileData.charAt(i) == '\n'){ //Once reached end of current line set appropriate subject to correct varibale
                switch(cursubject){
                    case 0: GlobalCounter = Integer.valueOf(curint); break;
                    case 1: DMCount = Integer.valueOf(curint);  break;
                    case 2: CSCount = Integer.valueOf(curint);  break;
                    case 3: SECount = Integer.valueOf(curint);  break;
                    case 4: PFLCount = Integer.valueOf(curint); break;
                    case 5: sub1 = curint; break;
                    case 6: sub2 = curint; break;
                    case 7: sub3 = curint; break;
                    case 8: sub4 = curint; break;
                }
                ++ cursubject; //Move onto next subject
                curint = "";
            } else {
                curint = curint + fileData.charAt(i); //Fill curint string with current line
            }
        }
    }
    private static String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024]; //
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){ //While there are still characters add them to fileData
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
    public static long[] convertTime(long time){
        long[] times = new long[3]; //Fills times with hours, mins, secs in that order
        times[0] = (time/60)/60;
        times[1] = ((time/60)-(times[0]*60));
        times[2] = (time-(times[0]*60*60)-(times[1]*60));
        return times;
    }
    public static void reset(JLabel timeDM, JLabel timeCS, JLabel timeSE, JLabel timePFL, JLabel timeOV){
        GlobalCounter = 0;
        DMCount = 0;
        CSCount = 0;
        SECount = 0;
        PFLCount = 0;
        timeDM.setText(sub1 + ": 0 hours 0 mins 0 secs");
        timeCS.setText(sub2 + ": 0 hours 0 mins 0 secs"); 
        timeSE.setText(sub3 + ": 0 hours 0 mins 0 secs");
        timePFL.setText(sub4 + ": 0 hours 0 mins 0 secs"); 
        timeOV.setText("Time Studied Overall: " + GlobalCounter);
    }
    public static void subTime(JLabel timeDM, JLabel timeCS, JLabel timeSE, JLabel timePFL, JLabel timeOV){
        
        //Frame And Panel
        JFrame subtractTime = new JFrame("Subtract Study Time");
        JPanel subtractTimePanel = new JPanel();
        subtractTime.setSize(420, 300);
        //Labels
        JLabel numLabel = new JLabel("Enter amount of time to be subtracted in minutes:");
        JLabel subjLabel = new JLabel("Enter the subject this is for (1,2,3,4):");
        //Buttons
        JButton submit = new JButton("Subtract");
        //TextFields
        JTextField getInt = new JTextField();
        getInt.setPreferredSize( new Dimension(60, 20));
        JTextField getSubj = new JTextField();
        getSubj.setPreferredSize( new Dimension(50, 20));
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                int time = Integer.parseInt(getInt.getText());
                int subject = Integer.parseInt(getSubj.getText());
                if (subject == 1){
                    DMCount = DMCount - (time*60);  
                    long[] timesDM = convertTime(DMCount);
                    timeDM.setText(sub1  + ": " + timesDM[0] + " hours " + timesDM[1] + " mins " + timesDM[2] + " secs"); 
                } else if (subject == 2){
                    CSCount = CSCount - (time*60);   
                    long[] timesCS = convertTime(CSCount);
                    timeCS.setText(sub2 + ": "  + timesCS[0] + " hours " + timesCS[1] + " mins " + timesCS[2] + " secs"); 
                } else if (subject == 3){
                    SECount = SECount - (time*60);   
                    long[] timesSE = convertTime(SECount);
                    timeSE.setText(sub3  + ": " + timesSE[0] + " hours " + timesSE[1] + " mins " + timesSE[2] + " secs"); 
                } else if (subject == 4){
                    PFLCount = PFLCount - (time*60);   
                    long[] timesPFL = convertTime(PFLCount);
                    timePFL.setText(sub4  + ": " + timesPFL[0] + " hours " + timesPFL[1] + " mins " + timesPFL[2] + " secs"); 
                }
                GlobalCounter -= (time*60);
                timeOV.setText("Time Studied Overall: " + GlobalCounter);
                subtractTime.dispatchEvent(new WindowEvent(subtractTime, WindowEvent.WINDOW_CLOSING));//Closes the window
            }
        });
        subtractTimePanel.add(numLabel);subtractTimePanel.add(getInt); //Line1
        subtractTimePanel.add(subjLabel);subtractTimePanel.add(getSubj); //Line2
        subtractTimePanel.add(submit); //Line3
        subtractTime.add(subtractTimePanel);
        subtractTime.setVisible(true);
    }
    public static void changeSubs(JLabel timeDM, JLabel timeCS, JLabel timeSE, JLabel timePFL, JButton DMMRButton, JButton CSButton, JButton SEButton, JButton PFLButton){
        //Frame And Panel
        JFrame changeLabl = new JFrame("Change Subject Labels");
        JPanel changeLablPanel = new JPanel();
        changeLabl.setSize(500, 300);
        //Labels
        JLabel numLabel = new JLabel("Enter subject label you wish you to change (1,2,3,4):");
        JLabel subjLabel = new JLabel("Enter new 4 letter long tag:");
        //Buttons
        JButton update = new JButton("update");
        //TextFields
        JTextField getSubj = new JTextField();
        JTextField newLabl = new JTextField();
        getSubj.setPreferredSize( new Dimension(60, 20));
        newLabl.setPreferredSize( new Dimension(50, 20));

        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                int subj = Integer.parseInt(getSubj.getText());
                String newlbl = newLabl.getText();
                if (subj == 1){
                    sub1 = newlbl;
                    long[] timesDM = convertTime(DMCount);
                    DMMRButton.setText(sub1);
                    timeDM.setText(sub1 + ": " + timesDM[0] + " hours " + timesDM[1] + " mins " + timesDM[2] + " secs");
                } else if (subj == 2){
                    sub2 = newlbl;
                    long[] timesCS = convertTime(DMCount);
                    CSButton.setText(sub2);
                    timeCS.setText(sub2 + ": " + timesCS[0] + " hours " + timesCS[1] + " mins " + timesCS[2] + " secs");
                } else if (subj == 3){
                    sub3 = newlbl;
                    SEButton.setText(sub3);
                    long[] timesSE = convertTime(DMCount);
                    timeSE.setText(sub3 + ": " + timesSE[0] + " hours " + timesSE[1] + " mins " + timesSE[2] + " secs");
                } else if (subj == 4){
                    sub4 = newlbl;
                    PFLButton.setText(sub4);
                    long[] timesPFL = convertTime(DMCount);
                    timePFL.setText(sub4 + ": " + timesPFL[0] + " hours " + timesPFL[1] + " mins " + timesPFL[2] + " secs");
                }
                saveData();
                changeLabl.dispatchEvent(new WindowEvent(changeLabl, WindowEvent.WINDOW_CLOSING));//Closes the window
            }
        });
        changeLablPanel.add(numLabel);changeLablPanel.add(getSubj); //Line1
        changeLablPanel.add(subjLabel);changeLablPanel.add(newLabl); //Line2
        changeLablPanel.add(update); //Line3
        changeLabl.add(changeLablPanel);
        changeLabl.setVisible(true);
    }
}