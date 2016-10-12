
public class Estrella {
	
	private String identificador;
	private double campoMagnetico;
	private int indiceColor;
	
	public Estrella(String identificador,double campoMagnetico,int indiceColor){
		this.identificador=identificador;
		this.campoMagnetico=campoMagnetico;
		this.indiceColor=indiceColor;
	}

	public String getIdentificador(){
		return identificador;
	}
	public double getCampoMagnetico(){
		return campoMagnetico;
	}
	public int getIndiceColor(){
		return indiceColor;
	}
	
	public void desplazar(){
		this.campoMagnetico=campoMagnetico-5.5;
		this.indiceColor=indiceColor-1;
	}
	
	public boolean equals(Estrella estrella){
		return this.identificador.equals(estrella.identificador);
	}
	
}
