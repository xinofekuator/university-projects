
public class CentroEspacial {
	
	private String identificador;
	private SateliteArtificial[] satelitesSupervisados;
	
	public CentroEspacial(String identificador,SateliteArtificial[] satelitesSupervisados){
		this.identificador=identificador;
		this.satelitesSupervisados=satelitesSupervisados;
	}
	public String getIdentificador(){
		return identificador;
	}
	public String informacionEstrellas(){
		String resultado = satelitesSupervisados[0].toString();
		for(int i=1;i<=satelitesSupervisados.length-1;i++){
			resultado=resultado + this.satelitesSupervisados[i].toString();
		}
		return resultado;
	}
	
	public boolean comprobarSatelites(){
		boolean estadoSatelites;
		if ((this.satelitesSupervisados.length>=1 || this.satelitesDistintos())&& this.satelitesValidos())
			estadoSatelites=true;
		else
			estadoSatelites=false;
		return estadoSatelites;
			
	}
	
	public boolean satelitesDistintos(){
		boolean resultado=true;
		for(int i=0;i<=satelitesSupervisados.length-1&&resultado;i++){
			for(int a=i+1;a<=satelitesSupervisados.length-1&&resultado;a++){
				if (this.satelitesSupervisados[i].equals(satelitesSupervisados[a]))
					resultado=false;
			}
		}
		return resultado;
	}
	public boolean satelitesValidos(){
		boolean resultado=true;
		for(int i=0;i<=this.satelitesSupervisados.length-1&&resultado;i++){
			if (this.satelitesSupervisados==null)
				resultado=false;
		}
		return resultado;
	}
	
}
