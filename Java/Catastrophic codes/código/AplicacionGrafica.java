import javax.swing.*;

import cod_convolucional.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AplicacionGrafica extends JFrame implements ActionListener {
	
	private Automata automataTotal;
	
	private String error="";
	

	private JButton bDecode,bCode;
	private JLabel labelInicio,labelDiagrama,resultLabel,viterbiLabel,nLabel,mLabel;
	private JTextField inputText,nText,mText;
	private JTextArea diagramText,viterbiText,resultText;
	private JPanel principal,inputPanel,diagramPanel,diagramInputPanel,viterbiPanel;
	private JScrollPane diagramScroll,viterbiScroll,resultScroll;
	
	public AplicacionGrafica(){
		super("Simulador de códigos convolucionales");
		Container content = this.getContentPane();

		
		content.setLayout(new GridLayout());

		this.setSize(new Dimension(100,100));
		this.setPreferredSize(new Dimension(100,100));
		
		//Panel Principal
		
		principal=new JPanel(new GridLayout(0,1));
		JPanel inputPanel=new JPanel(new FlowLayout());
		JPanel diagramPanel=new JPanel(new FlowLayout());
		JPanel diagramInputPanel=new JPanel(new FlowLayout());
		JPanel buttonsPanel=new JPanel(new FlowLayout());
		JPanel resultPanel=new JPanel(new FlowLayout());
		JPanel viterbiPanel=new JPanel(new FlowLayout());
		
		this.labelInicio=new JLabel("Introduzca la secuencia de bits:");inputPanel.add(labelInicio);
		this.inputText = new JTextField("",20);inputPanel.add(inputText);
		this.nLabel=new JLabel("Valor de n (número de salidas)");inputPanel.add(nLabel);
		this.nText = new JTextField("2",5);inputPanel.add(nText);
		this.mLabel=new JLabel("Valor de m (número de estados)");inputPanel.add(mLabel);
		this.mText = new JTextField("4",5);inputPanel.add(mText);
		this.labelDiagrama=new JLabel("Introduzca el diagrama de estados del codificador:");diagramPanel.add(labelDiagrama);
		this.diagramText = new JTextArea("0000 2111\n0001 2110\n1010 3101\n1011 3100\n",5,20);diagramText.setLineWrap(true);diagramPanel.add(diagramText);
		this.diagramScroll=new JScrollPane(diagramText,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);diagramPanel.add(diagramScroll);
		
	    this.bCode=new JButton("Codificar");buttonsPanel.add(bCode);bCode.addActionListener(this);
	    this.bDecode=new JButton("Decodificar");buttonsPanel.add(bDecode);bDecode.addActionListener(this);
	    
	    this.resultLabel=new JLabel("Resultado:");resultPanel.add(resultLabel);
	    this.resultText = new JTextArea("",8,20);resultText.setLineWrap(true);resultPanel.add(resultText);
		this.resultScroll=new JScrollPane(resultText,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);resultPanel.add(resultScroll);
	    
	    this.viterbiLabel=new JLabel("Detalles: ");viterbiPanel.add(viterbiLabel);
		this.viterbiText = new JTextArea("",8,40);viterbiText.setLineWrap(true);viterbiPanel.add(viterbiText);
		this.viterbiScroll=new JScrollPane(viterbiText,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);viterbiPanel.add(viterbiScroll);
	    
		principal.add(inputPanel);
		inputPanel.add(diagramPanel);
		inputPanel.add(diagramInputPanel);
		principal.add(buttonsPanel);
		principal.add(resultPanel);
		resultPanel.add(viterbiPanel);

		this.getContentPane().add(principal);
		//pack();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);

		this.setSize(6*java.awt.Toolkit.getDefaultToolkit().getScreenSize().width/7,6*java.awt.Toolkit.getDefaultToolkit().getScreenSize().height/7);
	}

	private ArrayList<ArrayList<ArrayList<Integer>>> getLines (String diagramText){
		ArrayList<ArrayList<ArrayList<Integer>>> nodos = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<ArrayList<Integer>> nodo = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> auxNodo1 =new ArrayList<Integer>();
		ArrayList<Integer> auxNodo2 =new ArrayList<Integer>();
		int indice=0;
		for (int i=0;i<=diagramText.length()-1;i++){		
			if(diagramText.charAt(i)=='\n' || i==diagramText.length()-1){
				//System.out.println("NODO 1: "+ auxNodo1.size());
				//System.out.println("NODO 2: "+ auxNodo2.size());
				nodo.add(auxNodo1);
				nodo.add(auxNodo2);
				nodos.add(nodo);
				auxNodo1 =new ArrayList<Integer>();
				auxNodo2 =new ArrayList<Integer>();
				nodo=new ArrayList<ArrayList<Integer>>();
				indice=0;
			}
			else if(diagramText.charAt(i)==' '){
				indice=1;
			}
			else{
				if(indice==0){
					auxNodo1.add(Character.getNumericValue(diagramText.charAt(i)));
				}
				else{
					auxNodo2.add(Character.getNumericValue(diagramText.charAt(i)));
				}
			}
		}
		return nodos;
	}
	
	private ArrayList<Nodo> getAutomata(ArrayList<ArrayList<ArrayList<Integer>>> lines){
		
		ArrayList<Nodo> automata=new ArrayList<Nodo>();
		
		for (ArrayList<ArrayList<Integer>> s:lines){
			if(s.get(0).size()!=s.get(1).size()){
				System.out.println("ERROR");
			}
			int[] aux1=new int[s.get(0).size()];
			int[] aux2=new int[s.get(1).size()];
			int indice=0;
			for (Integer i:s.get(0)){
				aux1[indice]=i;
				indice++;
			}
			indice=0;
			for (Integer i:s.get(1)){
				aux2[indice]=i;
				indice++;
			}
			automata.add(new Nodo(aux1,aux2));
		}
		//System.out.println("SIZE AUTOMATA: "+ automata.size());
		return automata;
	}
	
	private Nodo[] automataToNodo(ArrayList<Nodo> nodos){
		Nodo[] resultado = new Nodo[nodos.size()];
		int indice=0;
		for (Nodo nodo:nodos){
			resultado[indice]=nodo;
			indice++;
		}
		return resultado;
	}
	
	private int[] stringToIntegers(String s){
		int[] resultado = new int[s.length()]; 
		for (int i=0;i<=s.length()-1;i++){		
			resultado[i]=Character.getNumericValue(s.charAt(i));
		}
		return resultado;
	}
	
	private String intToString(int[] intLista){
		String resultado="";
		for(int i=0;i<=intLista.length-1;i++){
			resultado=resultado+Integer.toString(intLista[i]);
		}
		return resultado;
	}

	private boolean checkInputs(){
		boolean resultado=false;
		boolean nulos1=this.nText==null || this.mText==null || this.diagramText==null || this.inputText==null;
		error="Algún campo de entrada nulo";
		if(!nulos1){
			error="Algún campo de entrada vacío";
			boolean nulos2=nText.getText().equals("") || mText.getText().equals("") || diagramText.getText().equals("") || inputText.getText().equals("");
			if(!nulos2){
				try{
					Integer n=Integer.parseInt(nText.getText());
					Integer m=Integer.parseInt(mText.getText());
					int lenDiagram=((2+n)*2+2)*m;
					boolean diagram=diagramText.getText().length()==lenDiagram;
					if(diagram){
						resultado=true;
						String entrada=this.inputText.getText();
						for(int i=0;i<=entrada.length()-1;i++){
							//System.out.println(entrada.charAt(i));
							if(entrada.charAt(i)!='1' && entrada.charAt(i)!='0'){
								resultado=false;
								error="La entrada debe ser binaria";
							}
						}
					}
					else{
						error="Error en el diagrama de estados";
					}
				}
				catch(NumberFormatException e){
					error="n y m deben ser números";
				}
			}
		}
		return resultado;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.bDecode){
			if(!checkInputs()){
				resultText.setText(error);
			}
			else{
				String b =diagramText.getText();
				//System.out.println("TAMAÑO TOTAL: "+b.length());
				String input=inputText.getText()+"00000000000";
				ArrayList<ArrayList<ArrayList<Integer>>> texto=getLines(b);
				ArrayList<Nodo> automataList=getAutomata(texto);
				Nodo[] automata=automataToNodo(automataList);
				//diagramText.setText(Integer.toString(stringToIntegers(input)[1]));
				Integer n=Integer.parseInt(nText.getText());
				this.automataTotal=new Automata(automata,n);
				ArrayList<Integer>[][] matriz=automataTotal.Viterbi(stringToIntegers(input));
				automataTotal.imprimeViterbi(stringToIntegers(input), matriz);
				resultText.setText(intToString(automataTotal.getCorregido()));
				viterbiText.setText(automataTotal.getViterbiText());
			}
		}
		else if (e.getSource()==this.bCode){
			if(!checkInputs()){
				resultText.setText(error);
			}
			else{
				String b =diagramText.getText();
				//System.out.println("TAMAÑO TOTAL: "+b.length());
				String input=inputText.getText()+"00000000000";
				ArrayList<ArrayList<ArrayList<Integer>>> texto=getLines(b);
				ArrayList<Nodo> automataList=getAutomata(texto);
				Nodo[] automata=automataToNodo(automataList);
				//diagramText.setText(Integer.toString(stringToIntegers(input)[1]));
				Integer n=Integer.parseInt(nText.getText());
				this.automataTotal=new Automata(automata,n);
				int[] resultado=automataTotal.Codificar(stringToIntegers(input));
				resultText.setText(intToString(resultado));
			}
		}		
	}

	
	public static void main(String[] args)  {
		new AplicacionGrafica();
	}

}
