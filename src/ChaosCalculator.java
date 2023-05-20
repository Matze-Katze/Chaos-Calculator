import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ChaosCalculator extends JFrame implements ActionListener{
	
	private boolean isDark=true,isRadian=true;
	private int chaosNumber=0;
	private Thread chaosThread;
	
	private JPanel inputPanelPanel=new JPanel();
	private JPanel inputPanel=new JPanel();			
	private JPanel settingsPanel=new JPanel();
	private JPanel buttonsPanelPanel=new JPanel();
	private JPanel buttonsPanel=new JPanel();
	
	private JLabel xLabel=new JLabel("Operand x:");
	private JLabel yLabel=new JLabel("Operand y:");
	private JLabel resultLabel=new JLabel("Result:");
	private JTextField xField=new JTextField("");
	private JTextField yField=new JTextField("");
	private JTextField resultField=new JTextField("");
	
	private JCheckBox darkModeCheck=new JCheckBox("Dark Mode");  
	private JCheckBox chaosCheck=new JCheckBox("¢̵̡̨̪̣̰̄̏̉͊̚h̷̨̳̪͚̗̽͌̌̾̂ą̴͕͖̲͇̊̑̒͒͐̚ð̵̹̩̤͚̭̀̋̏́̈§̷̡̣͓͍̜̈́̅͒̄͂");  
	private ButtonGroup angleGroup=new ButtonGroup();
	private JRadioButton radianButton = new JRadioButton("radian");
	private JRadioButton degreeButton = new JRadioButton("degree");
	
	private JButton[] buttonArray;
	private JButton clearButton;
	
	public ChaosCalculator(){
		URL iconURL = getClass().getResource("/icon.jpg");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setAlwaysOnTop(true);
		this.setTitle("Calculator");		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		angleGroup.add(degreeButton);
		angleGroup.add(radianButton);
		radianButton.setSelected(isRadian);
		darkModeCheck.setSelected(isDark);
		darkModeCheck.addItemListener((ItemEvent e)->darkModeSwitch());  
		chaosCheck.addMouseListener(new MouseAdapter() {   //chaos fun stuff
			@Override
			public void mouseClicked(MouseEvent e) {
				if(chaosThread!=null&&chaosThread.isAlive())   // 50/50 dont respond if chaosThread still alive
					if(new Random().nextBoolean())
						return;
				chaosThread=new Thread(()->{
					try {
						Thread.sleep(new Random().nextInt(chaosNumber*500+1)); //rndm sleep with increasing range 0.5-3.5sek
					} catch (InterruptedException e1) {
					}
					switch(chaosNumber!=7?chaosNumber++:(int)(Math.random()*6.7+0.6)) {    // once 0-6 then rndm mix with few 0=nothing and few 7=close  
						case 1:
							drawFore(new Color(0, 0, 0, 0)); 	//transparent foreground
							break;
						case 2:
							drawBack(rndmColor());		//rndm background
							break;
						case 3:
							rndmizeComponentOrder(buttonsPanel);
							break;
						case 4:
							drawBack(new Color(0, 0, 0, 0)); 
							break;
						case 5:
							drawFore(rndmColor());
							break;
						case 6:
							rndmizeComponentOrder(getContentPane());
							break;
						case 7:
							close();
							break;
					}
				});
				chaosThread.start();
			}
		});
		
		xField.setToolTipText("I'm operand X: a real number");
		yField.setToolTipText("I'm operand Y: a real number");
		resultField.setToolTipText("I'm the Result: the realest number of them all");
		resultField.setEditable(false);
		inputPanel.setPreferredSize(new Dimension(380,120));
		inputPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		inputPanel.setLayout(new GridLayout(3, 2,0,10));;
		inputPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
			
		inputPanel.add(xLabel);
		inputPanel.add(xField);
		inputPanel.add(yLabel);
		inputPanel.add(yField);
		inputPanel.add(resultLabel);	
		inputPanel.add(resultField);
		inputPanelPanel.add(inputPanel);

		radianButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isRadian=true;
			}
		});
		degreeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isRadian=false;
			}
		});
		
		settingsPanel.add(degreeButton);
		settingsPanel.add(radianButton);
		settingsPanel.add(darkModeCheck);
		settingsPanel.add(chaosCheck);
		
		buttonArray =new JButton[8];
		buttonsPanel.setPreferredSize(new Dimension(380,80));
		buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		buttonsPanel.setLayout(new GridLayout(2, 4,10,10));
		buttonArray[0]=new JButton("+");
		buttonArray[1]=new JButton("*");
		buttonArray[2]=new JButton("-");
		buttonArray[3]=new JButton("/");
		buttonArray[4]=new JButton("sin");
		buttonArray[5]=new JButton("cos");
		buttonArray[6]=new JButton("x^y");
		buttonArray[7]=new JButton("log2");
		for(JButton jbutton:buttonArray)
			if(jbutton!=null) {
				jbutton.addActionListener(this);
				buttonsPanel.add(jbutton);
			}
		buttonsPanelPanel.add(buttonsPanel);
		
		clearButton=new JButton("CLEAR");
		clearButton.addActionListener((ActionEvent e)->{
			resultField.setText("");
			xField.setText("");
			yField.setText("");
			});
		
		getContentPane().add(inputPanelPanel);
		getContentPane().add(settingsPanel);
		getContentPane().add(buttonsPanelPanel);
		getContentPane().add(clearButton);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		pack();
		this.setVisible(true);
		darkModeSwitch();
	}

	public void darkModeSwitch() {
		if(isDark) {
			drawMode(Color.GREEN,Color.BLACK);
		}else {
			drawMode(Color.BLACK,new Color(240, 240, 240));
			xField.setBackground(Color.WHITE);
			yField.setBackground(Color.WHITE);
			resultField.setBackground(Color.WHITE);
		}
		isDark=!isDark;
	}
	
	public void drawBack(Color back) {
		Component[] comps=getContentPane().getComponents();
		for(Component comp:comps)
			if(comp instanceof AbstractButton)
				drawMode(comp.getForeground(),back);
	}
	
	public void drawFore(Color fore) {
		drawMode(fore,getContentPane().getBackground());
	}
	public void drawMode(Color fore,Color back) {
		getContentPane().setBackground(back);
		inputPanel.setBorder(BorderFactory.createLineBorder(fore));
		buttonsPanel.setBorder(BorderFactory.createLineBorder(fore));
		drawMode(fore,back,getContentPane().getComponents());
	}
	
	private static void drawMode(Color f,Color b,Component[] comps) {
		for(Component comp:comps) {
			comp.setBackground(b);
			comp.setForeground(f);
			if(comp instanceof JTextField) {
				if(b.getRed()+b.getBlue()+b.getGreen()>80*3)					//if not very dark
					comp.setBackground(Color.WHITE);
				else
					comp.setBackground(new Color(150,150,150));
			}
			if(comp instanceof JPanel) {
				drawMode(f,b,((JPanel)comp).getComponents());
			}
		}
	}
	
	public Color rndmColor() {
		return new Color(new Random().nextInt(0xffffff));
	}
	public void rndmizeComponentOrder(Container cont) {
		Component[] compArray=cont.getComponents();
		for(Component comp:compArray)
			cont.remove(comp);
		shuffleArray(compArray);
		for(Component comp:compArray) {
			cont.add(comp);
		}
		cont.validate();
	}
	
	public String cutIntDecimals(double x) {
		if((int)x==x)
			return (int)(x)+"";
		return x+"";
	}
	
	public void close() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	private static <T> void shuffleArray(T[] array){		//basic Fisher–Yates shuffle
	    int index;
		T temp;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        temp = array[index];
	        array[index] = array[i];
	        array[i] = temp;
	    }
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton source=(JButton)e.getSource();
		double x=0,y=0;
		try { x=Double.parseDouble(xField.getText()); }
		catch (NumberFormatException except){
	    	resultField.setText("ERROR:"+except.getMessage()+" for X");
	    	except.printStackTrace();
	    	return;
	    }
		if(!"sincoslog2".contains(source.getText()))try { y=Double.parseDouble(yField.getText()); }
		catch (NumberFormatException except){
	    	resultField.setText("ERROR:"+except.getMessage()+" for Y");
	    	except.printStackTrace();
	    	return;
	    }
		switch(source.getText()){
			case "+":
				resultField.setText(cutIntDecimals(x+y));
				break;
			case "*":
				resultField.setText(cutIntDecimals(x*y));
				break;
			case "-":
				resultField.setText(cutIntDecimals(x-y));
				break;
			case "/":
				resultField.setText(cutIntDecimals(x/y));
				break;
			case "sin":
				if(!isRadian)
					x=Math.toRadians(x);
				resultField.setText(cutIntDecimals(Math.sin(x)));
				break;
			case "cos":
				if(!isRadian)
					x=Math.toRadians(x);
				resultField.setText(cutIntDecimals(Math.cos(x)));
				break;
			case "x^y":
				resultField.setText(cutIntDecimals(Math.pow(x, y)));
				break;
			case "log2":
				resultField.setText(cutIntDecimals(Math.log(x) / Math.log(2)));
				break;
		}
	}
}
