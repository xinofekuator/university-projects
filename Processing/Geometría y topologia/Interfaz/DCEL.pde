class DCEL {
  //deberían ser public??
  private List<Vertice> vertices;
  private List<SemiArista> semiAristas;
  private List<Cara> caras;

  DCEL(List<Vertice> vertices, List<SemiArista> semiAristas, List<Cara> caras) {
    this.vertices=new ArrayList<Vertice>(vertices);
    this.semiAristas=new ArrayList<SemiArista>(semiAristas);
    this.caras=new ArrayList<Cara>(caras);
  }
  DCEL(DCEL d) {
    this.vertices=d.vertices;
    this.semiAristas=d.semiAristas;
    this.caras=d.caras;
  }
  DCEL() {
    this.vertices=new ArrayList<Vertice>();
    this.semiAristas=new ArrayList<SemiArista>();
    this.caras=new ArrayList<Cara>();
  }

  public List<Vertice> getVertices() {
    return this.vertices;
  }

  public List<SemiArista> getSemiAristas() {
    return this.semiAristas;
  }

  public List<Cara> getCaras() {
    return this.caras;
  }
}

class Vertice {
  private Punto c;
  private SemiArista s; //semiArista que sale de él

  public Vertice() {
    this.c=null;
    this.s=null;
  }

  public void actualizar(Punto c, SemiArista s) {
    this.c=c;
    this.s=s;
  }

  public Punto getC() {
    return this.c;
  }

  public SemiArista getS() {
    return this.s;
  }

  public void setC(Punto c) {
    this.c=c;
  }

  public void setS(SemiArista s) {
    this.s=s;
  }
}

class SemiArista {
  private Vertice origen;
  private SemiArista gemela, anterior, posterior;
  private Cara cara;

  public SemiArista() {
    this.origen=null;
    this.gemela=null;
    this.anterior=null;
    this.posterior=null;
    this.cara=null;
  }

  public void actualizar(Vertice origen, SemiArista gemela, SemiArista anterior, SemiArista posterior, Cara cara) {
    this.origen=origen;
    this.gemela=gemela;
    this.anterior=anterior;
    this.posterior=posterior;
    this.cara=cara;
  }

  public void display() {
    Vertice aux=this.posterior.getOrigen();
    if (aux==null) {
      print("Error al dibujar una arista");
    }
    line(this.origen.getC().getX(), this.origen.getC().getY(), aux.getC().getX(), aux.getC().getY());
  }


  public Vertice getOrigen() {
    return this.origen;
  }

  public void setOrigen(Vertice origen) {
    this.origen=origen;
  }

  public SemiArista getGemela() {
    return this.gemela;
  }

  public void setGemela(SemiArista gemela) {
    this.gemela=gemela;
  }

  public SemiArista getAnterior() {
    return this.anterior;
  }

  public void setAnterior(SemiArista anterior) {
    this.anterior=anterior;
  }

  public SemiArista getPosterior() {
    return this.posterior;
  }

  public void setPosterior(SemiArista posterior) {
    this.posterior=posterior;
  }

  public Cara getCara() {
    return this.cara;
  }

  public void setCara(Cara cara) {
    this.cara=cara;
  }
}

class Cara {
  private SemiArista semiarista;

  Cara() {
    this.semiarista=null;
  }

  void actualizar(SemiArista semiarista) {
    this.semiarista=semiarista;
  }

  public SemiArista getSemiArista() {
    return this.semiarista;
  }

  public void setSemiArista(SemiArista semiarista) {
    this.semiarista=semiarista;
  }
}

