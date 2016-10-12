
public class SateliteArtificial {
	
	private String identificador;
	private Estrella estrellaMonotorizada;
	
	private static int ordenCreacion=0;
	
	public SateliteArtificial(Estrella estrellaMonotorizada){
		ordenCreacion++;
		this.identificador="Sat"+ordenCreacion;
		this.estrellaMonotorizada=estrellaMonotorizada;
	}

	public String getIdentificador(){
		return identificador;
	}
	public Estrella getEstrella(){
		return estrellaMonotorizada;
	}
	public void setEstrella(Estrella estrella){
		this.estrellaMonotorizada=estrella;
	}
	
	public double darTemperatura(){
		return estrellaMonotorizada.getIndiceColor()*2.1;
	}
	public boolean hayAlertaTormentaMagnetica(){
		return estrellaMonotorizada.getCampoMagnetico()>=1000;
	}
	public boolean equals(SateliteArtificial satelite){
		return this.estrellaMonotorizada.equals(satelite.getEstrella());
	}
	public String toString(){
		String informacionEstrellas;
		if (this.hayAlertaTormentaMagnetica())
			informacionEstrellas=this.getEstrella().getIdentificador()+" ("+
			this.darTemperatura()+") ALERTA!\n";
		else
			informacionEstrellas=this.getEstrella().getIdentificador()+" ("+
			this.darTemperatura()+")\n";
		return informacionEstrellas;
	}
	}

