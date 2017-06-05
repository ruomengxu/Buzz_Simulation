import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUI extends JFrame{
	private JButton btn = new JButton("Run");
	private static JTextField tf1 = new JTextField("1500");
//	private static JTextField tf2 = new JTextField("1140000");
	private static JTextField tf3 = new JTextField("1500");
    private static JTextField tf5 = new JTextField("3000");
	private static JTextField tf6 = new JTextField("10");
	JScrollPane resultmian = new JScrollPane();
	private JTextArea txta = new JTextArea("Georgia Institute of Technology\n");
	private static JTextArea txtb;
	private static JComboBox<String> choice1 = new JComboBox<>();
	private static JComboBox<String> choice2 = new JComboBox<>();
	private static JComboBox<Integer> choice3 = new JComboBox<>();
	private static DefaultCategoryDataset defaultdataset = new DefaultCategoryDataset();
    JLabel newone = null;
    private static int month;
    private static int area;
    private static int simtime;

    int outputflag = 0;
	
	
	public GUI() throws IOException{
    	setTitle("Hive Simulation System");
    	setSize(1050,800);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);//cross mark
    	setResizable(false);
    	JPanel main = new JPanel();
    	main.setBorder(new EmptyBorder(5,5,5,5));//main panel with 5 edge
    	GridLayout mainLayout = new GridLayout(1,2);
    	mainLayout.setHgap(15);
    	main.setLayout(mainLayout);
    	getContentPane().add(main);
    	JPanel left = new JPanel();
    	JPanel right = new JPanel();
    	main.add(left);
    	main.add(right);
    	BorderLayout rightLayout = new BorderLayout();
    	rightLayout.setVgap(5);
    	BorderLayout leftLayout = new BorderLayout();
    	leftLayout.setVgap(10);
    	left.setLayout(leftLayout);
    	right.setLayout(rightLayout);
    	Font font = new Font("Time New Rome", 0,24);
    	
    	JPanel ue = new JPanel();
    	JPanel shita = new JPanel();
    	BorderLayout ueLayout = new BorderLayout();
    	ueLayout.setHgap(5);
    	ueLayout.setVgap(5);
    	ue.setLayout(ueLayout);
    	BorderLayout shitaLayout = new BorderLayout();
    	shitaLayout.setVgap(10);
    	shita.setLayout(shitaLayout);
    	left.add(ue, BorderLayout.NORTH);
    	left.add(shita, BorderLayout.CENTER);
    	
    	JLabel lbl1 = new JLabel("Parameters");
    	btn.setSize(3,5);
    	lbl1.setFont(font);
    	ue.add(lbl1, BorderLayout.NORTH);
    	ue.add(btn, BorderLayout.EAST);
    	JPanel input = new JPanel();
    	GridLayout inputLayout = new GridLayout(7,2);
    	inputLayout.setVgap(5);
    	input.setLayout(inputLayout);
    	ue.add(input, BorderLayout.CENTER);
    	
    	choice1.addItem("Jan");
    	choice1.addItem("Feb");
    	choice1.addItem("Mar");
    	choice1.addItem("Apr");
    	choice1.addItem("May");
    	choice1.addItem("Jun");
    	choice1.addItem("Jul");
    	choice1.addItem("Aug");
    	choice1.addItem("Sep");
    	choice1.addItem("Oct");
    	choice1.addItem("Nov");
    	choice1.addItem("Dec");
    	
    	choice2.addItem("Midwest");
    	choice2.addItem("Southwest");

    	choice3.addItem(1);
    	choice3.addItem(2);
    	choice3.addItem(3);
    	
    	JLabel lbl1_1 = new JLabel("Initial Population :");
//    	JLabel lbl1_2 = new JLabel("Sperm Amount :");
    	JLabel lbl1_3 = new JLabel("Forager Population :");
    	JLabel lbl1_5 = new JLabel("Potential Eggs Laid (per day) :");
    	JLabel lbl1_6 = new JLabel("Forager Life Expectancy :");
    	JLabel lbl1_7 = new JLabel("Area:");
    	JLabel lbl1_8 = new JLabel("Start month :");
    	JLabel lbl1_9 = new JLabel("Simulation Time (year):");
    	input.add(lbl1_1);
    	input.add(tf1);
//    	input.add(lbl1_2);
//    	input.add(tf2);
    	input.add(lbl1_3);
    	input.add(tf3);
    	input.add(lbl1_5);
    	input.add(tf5);
    	input.add(lbl1_6);
    	input.add(tf6);
    	input.add(lbl1_7);
    	input.add(choice2);
    	input.add(lbl1_8);
    	input.add(choice1);
    	input.add(lbl1_9);
    	input.add(choice3);
    	
    	JLabel lbl2 = new JLabel("Graph");
    	lbl2.setFont(font);
    	shita.add(lbl2, BorderLayout.NORTH);
    	  	
    	JLabel lbl3 = new JLabel("Results");//label "result"
    	lbl3.setFont(font);
    	right.add(lbl3, BorderLayout.NORTH);
    	
    	txta.setEditable(false);
    	
    	right.add(resultmian, BorderLayout.CENTER);
    	resultmian.setViewportView(txta);
    
    	btn.addActionListener(new ActionListener(){		//action of each button
    		@SuppressWarnings({ "unused", "null" })
			public void actionPerformed(ActionEvent e){
    			Simulator.voidinstance();
    			setSize(1050, 700);
    			defaultdataset = new DefaultCategoryDataset();
    			if(notNumber(tf1.getText())||Integer.parseInt(tf1.getText())<1){
    				JOptionPane.showMessageDialog(null,"Wrong Population: Not an integer or out of range","Input Error", JOptionPane.ERROR_MESSAGE);
    				return;
    			}
//    			if(notNumber(tf2.getText())||Integer.parseInt(tf2.getText())<1){
//    				JOptionPane.showMessageDialog(null, "Wrong Sperm Number : Not an integer or out of range", "Input Error", JOptionPane.ERROR_MESSAGE);
//    				return;
//    			}
                if(notNumber(tf3.getText())||Integer.parseInt(tf3.getText())<1||Integer.parseInt(tf3.getText())>Integer.parseInt(tf1.getText())){
                    JOptionPane.showMessageDialog(null, "Wrong Forager Population : Not an integer or out of range", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
				if(notNumber(tf5.getText())||Integer.parseInt(tf5.getText())<1){
                        JOptionPane.showMessageDialog(null, "Wrong Potential Eggs: Not an integer or out of range", "Input Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(notNumber(tf6.getText())||Integer.parseInt(tf6.getText())<1){
					JOptionPane.showMessageDialog(null, "Wrong Life Expectation: Not an integer or out of range", "Input Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				switch(choice1.getSelectedItem().toString()){
    	        case "Jan":
    	        	month = 1;
    	        	break;
    	        case "Feb":
    	        	month = 2;
    	        	break;
    	        case "Mar":
    	        	month = 3;
    	        	break;
    	        case "Apr":
    	        	month = 4;
    	        	break;
    	        case "May":
    	        	month = 5;
    	        	break;
    	        case "Jun":
    	        	month = 6;
    	        	break;
    	        case "Jul":
    	        	month = 7;
    	        	break;
    	        case "Aug":
    	        	month = 8;
    	        	break;
    	        case "Sep":
    	        	month = 9;
    	        	break;
    	        case "Oct":
    	        	month = 10;
    	        	break;
    	        case "Nov":
    	        	month = 11;
    	        	break;
    	        case "Dec":
    	        	month = 12;
    	        	break;
    	        }
				
				switch(choice2.getSelectedItem().toString()){
				case "Midwest":
					area = 0;
					break;
				case "Southwest":
					area = 1;
					break;
				}
				simtime = (int)choice3.getSelectedItem();


				
    			txtb = new JTextArea("Hive Population Evolution :\n");
    			txtb.setEditable(false);
    			HiveSim.simStart(Integer.parseInt(tf1.getText()),Integer.parseInt(tf3.getText()),Integer.parseInt(tf5.getText()),Integer.parseInt(tf6.getText()));
    			resultmian.setViewportView(txtb);
    			
    	    	FileOutputStream outputStream = null;
				try {
					outputStream = new FileOutputStream("./src/"+outputflag+".png");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    	    	CategoryDataset dataset = defaultdataset;
    	    	JFreeChart jfreechart = ChartFactory.createLineChart("Hive Population Evolution", "time", "population", dataset);
    	    	jfreechart.setBackgroundPaint(Color.white);
    	    	CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
    	        categoryplot.setBackgroundPaint(Color.lightGray);  
    	        categoryplot.setRangeGridlinesVisible(false); 
    	        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();
    	        lineandshaperenderer.setBaseShapesVisible(true);  
    	        lineandshaperenderer.setDrawOutlines(true);  
    	        lineandshaperenderer.setUseFillPaint(true);  
    	        lineandshaperenderer.setBaseFillPaint(Color.white);   
    	        lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3F));  
    	        lineandshaperenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F)); 
    	        lineandshaperenderer.setSeriesShape(0,new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));  
    	        try {
    	        	
					ChartUtilities.writeChartAsPNG(outputStream, jfreechart,500, 400);
					outputStream.flush();
					if(outputStream!=null){
						outputStream.close();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
    	        
    	        if(newone!=null){
    	            newone.setSize(0,0);
    	        }
    	        newone = new JLabel(new ImageIcon("./src/"+ outputflag+".png"));
    	        outputflag++;
    	        
    	        shita.add(newone, BorderLayout.CENTER);
    	        setSize(1051, 800);
    	        
    	        
    		}
    	});
    	  	
	}
	
	public static void print(int t, int output){
		txtb.append(String.valueOf(output)+"\n");
		defaultdataset.addValue(output, "Population", String.valueOf(t));
	}
	
	public static void print2(String output){
		txtb.append(output);
	}
	
	public static int getIniPop(){
		return Integer.parseInt(tf1.getText());
	}
	
//	public static int getSpermNo(){
//		return Integer.parseInt(tf2.getText());
//	}
	
	public static int getForageNo(){
		return Integer.parseInt(tf3.getText());
	}

	public static int getSimtime() { return simtime;}


	public static int getMaxEgg(){
		return Integer.parseInt(tf5.getText());
	}
	
	public static int getStartMonth(){
		return month;
	}
	
	public static int getArea(){
		return area;
	}

	public static int getLifeExp() { return Integer.parseInt(tf6.getText());}
	
	public static boolean notNumber(String input){
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    Matcher isNum = pattern.matcher(input);
		if( !isNum.matches() ){
		    return true; 
	    } 
	    return false; 
	}
	
	public static void main(String[] args) throws IOException{
		GUI jiemian = new GUI();
    	jiemian.setVisible(true);
    	
	}
}