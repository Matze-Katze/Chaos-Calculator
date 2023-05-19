import java.awt.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class RechnerApplikation extends JFrame implements ActionListener{
	private boolean dark=true,radian=true;
	private Thread chaos;
	private int chaosNumber=0;
	private JTextField inputX=new JTextField("");
	private JTextField inputY=new JTextField("");
	private JTextField result=new JTextField("");
	private JButton[] buttArray;
	private JButton Plussy;
	
	private JPanel inputPanePane=new JPanel();
	private JPanel inputPane=new JPanel();			
	private JPanel settings=new JPanel();
	private JPanel buttonsPane=new JPanel();
	private JPanel buttons=new JPanel();

	
	private JLabel opX=new JLabel("Operand x:");
	private JLabel opY=new JLabel("Operand y:");
	private JLabel Lresult=new JLabel("Result:");
	private JCheckBox darkMode=new JCheckBox("Dark Mode");  
	private JCheckBox CHAOS=new JCheckBox("¢̵̡̨̪̣̰̄̏̉͊̚h̷̨̳̪͚̗̽͌̌̾̂ą̴͕͖̲͇̊̑̒͒͐̚ð̵̹̩̤͚̭̀̋̏́̈§̷̡̣͓͍̜̈́̅͒̄͂");  
	private ButtonGroup angleGroup=new ButtonGroup();
	private JRadioButton radianButton = new JRadioButton("radian");
	private JRadioButton degreeButton = new JRadioButton("degree");
	public RechnerApplikation() {
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage("icon.jpg"));
		this.setTitle("Calculator");		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		angleGroup.add(degreeButton);
		angleGroup.add(radianButton);
		radianButton.setSelected(radian);
		darkMode.setSelected(dark);
		darkMode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				darkModeSwitch();
			}
		});
		CHAOS.addMouseListener(new MouseAdapter() {   //kleine spielerei
			@Override
			public void mouseClicked(MouseEvent e) {
				if(chaos!=null&&chaos.isAlive())   // 50/50 dont respond if chaosThread still alive
					if(new Random().nextBoolean())
						return;
				chaos=new Thread(()->{
					try {
						Thread.sleep(new Random().nextInt(chaosNumber+1)*500); //rndm sleep with increasing range 0.5-3.5sek
					} catch (InterruptedException e1) {
					}
					switch(chaosNumber!=7?chaosNumber++:(int)(Math.random()*6.7+0.6)) {    // einmal 0-6 dann rndm mix wenig 0 und wenig 7  
						case 1:
							drawMode(new Color(0, 0, 0, 0),null); //transparent foreground
							break;
						case 2:
							int r=(int) (Math.random()*241);
							int g=(int) (Math.random()*241);
							int b=(int) (Math.random()*241);
							drawMode(null,new Color(r,g,b));
							break;
						case 3:
							rndmizeComponentOrder(buttons);
							break;
						case 4:
							drawMode(null,new Color(0, 0, 0, 0)); //transparent background
							break;
						case 5:
							int r2=(int) (Math.random()*241);
							int g2=(int) (Math.random()*241);
							int b2=(int) (Math.random()*241);
							drawMode(new Color(r2,g2,b2),null);
							break;
						case 6:
							rndmizeComponentOrder(getContentPane());
							break;
						case 7:
							close();
							break;
					}
					});
				chaos.start();
			}
		});
		
		
		inputX.setToolTipText("ich bin der Operand X: eine reelle Zahl");
		inputY.setToolTipText("ich bin der Operand Y: eine reelle Zahl");
		result.setToolTipText("ich bin das Ergebnis: die reellste Zahl");
		result.setEditable(false);
//		inputPane.setMinimumSize(new Dimension(350,90));
		inputPane.setPreferredSize(new Dimension(380,120));
		inputPane.setBorder(BorderFactory.createLineBorder(Color.black));
		inputPane.setLayout(new GridLayout(3, 2,0,10));;
		inputPanePane.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
//			momPane.setLayout(new BoxLayout(momPane, BoxLayout.Y_AXIS));
//			if(this instanceof RechnerApplikation)
			
		inputPane.add(opX);
		inputPane.add(inputX);
		inputPane.add(opY);
		inputPane.add(inputY);
		inputPane.add(Lresult);	
		inputPane.add(result);
		inputPanePane.add(inputPane);

		radianButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				radian=true;
			}
		});
		degreeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				radian=false;
			}
		});
		
		settings.add(degreeButton);
		settings.add(radianButton);
		settings.add(darkMode);
		settings.add(CHAOS);
		
		buttArray =new JButton[8];
		buttons.setPreferredSize(new Dimension(380,80));
		buttons.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.setLayout(new GridLayout(2, 4,10,10));
//		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
//		buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
//		String[] buttonString= {"+"};
		buttArray[0]=new JButton("+");
		buttArray[1]=new JButton("*");
		buttArray[2]=new JButton("-");
		buttArray[3]=new JButton("/");
		buttArray[4]=new JButton("sin");
		buttArray[5]=new JButton("cos");
		buttArray[6]=new JButton("x^y");
		buttArray[7]=new JButton("log2");
		for(JButton jbutton:buttArray)
			if(jbutton!=null) {
				jbutton.addActionListener(this);
				buttons.add(jbutton);
			}
		buttonsPane.add(buttons);
		
		Plussy=new JButton("CLEAR");
		Plussy.addActionListener((ActionEvent e)->{
			result.setText("");
			inputX.setText("");
			inputY.setText("");
			});
		
//		setContentPane(new BasicBackgroundPanel(img));
		getContentPane().add(inputPanePane);
		getContentPane().add(settings);
		getContentPane().add(buttonsPane);
		getContentPane().add(Plussy);
//			getContentPane().setLayout(new GridLayout(2, 2,20,20));
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		pack();
		this.setVisible(true);
		darkModeSwitch();
	}

	public void darkModeSwitch() {
		if(dark) {
			drawMode(Color.GREEN,Color.BLACK);
		}else {
			drawMode(Color.BLACK,new Color(240, 240, 240));
			inputX.setBackground(Color.WHITE);
			inputY.setBackground(Color.WHITE);
			result.setBackground(Color.WHITE);
		}
		dark=!dark;
	}
	public void drawMode(Color fore,Color back) {
		if(back==null)
			back=getContentPane().getBackground();
		if(fore==null)
			fore=Plussy.getForeground();
		getContentPane().setBackground(back);
		inputPane.setBorder(BorderFactory.createLineBorder(fore));
		buttons.setBorder(BorderFactory.createLineBorder(fore));
		drawMode(fore,back,getContentPane().getComponents());
	}
	public static void drawMode(Color fore,Color back,Component[] comps) {
		for(Component comp:comps) {
			comp.setBackground(back);
			comp.setForeground(fore);
			if(comp instanceof JTextField) {
				int red=back.getRed();
				int blue=back.getBlue();
				int green=back.getGreen();
				if(red+blue+green>80*3)					//if not very dark
					comp.setBackground(Color.WHITE);
				else
					comp.setBackground(new Color(150,150,150)); //kinda dark
			}
			if(comp instanceof JPanel) {
				drawMode(fore,back,((JPanel)comp).getComponents());
			}
		}
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
	public void actionPerformed(ActionEvent e) {
		JButton source=(JButton)e.getSource();
		double x=0,y=0;
		try { x=Double.parseDouble(inputX.getText()); }
		catch (NumberFormatException except){
	    	result.setText("ERROR:"+except.getMessage()+" for X");
	    	except.printStackTrace();
	    	return;
	    }
		if(!"sincoslog2".contains(source.getText()))try { y=Double.parseDouble(inputY.getText()); }
		catch (NumberFormatException except){
	    	result.setText("ERROR:"+except.getMessage()+" for Y");
	    	except.printStackTrace();
	    	return;
	    }
		switch(source.getText()){
			case "+":
				result.setText(ganzeDouble(x+y));
				break;
			case "*":
				result.setText(ganzeDouble(x*y));
				break;
			case "-":
				result.setText(ganzeDouble(x-y));
				break;
			case "/":
				result.setText(ganzeDouble(x/y));
				break;
			case "sin":
				if(!radian)
					x=Math.toRadians(x);
				result.setText(ganzeDouble(Math.sin(x)));
				break;
			case "cos":
				if(!radian)
					x=Math.toRadians(x);
				result.setText(ganzeDouble(Math.cos(x)));
				break;
			case "x^y":
				result.setText(ganzeDouble(Math.pow(x, y)));
				break;
			case "log2":
				result.setText(ganzeDouble(Math.log(x) / Math.log(2)));
				break;
		}
	}
	public String ganzeDouble(double x) {
		if((int)x==x)
			return (int)(x)+"";
		return x+"";
	}
	public void close() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	private static <T> void shuffleArray(T[] array)
	{
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
//	public class BasicBackgroundPanel extends JPanel{
//	    private Image background;
//	    public BasicBackgroundPanel(Image background){
//	        this.background = background;
////	        setPreferredSize(new Dimension(400,900));
//	        setLayout(new BorderLayout());
//	    }
//	    @Override
//	    protected void paintComponent(Graphics g){
//	        super.paintComponent(g);
////	        g.drawImage(background, 0, 0, null); // image full size
//	        g.drawImage(background, 0, 0, getWidth(), getHeight(), null); // image scaled
//	    }
//	    @Override
//	    public Dimension getPreferredSize(){
//	        return new Dimension(background.getWidth(this), background.getHeight(this));
//	    }
//	}
}