//Clase encargada de realizar el diagrama de Voronoi

class Voronoi {

  private DCEL voronoi;
  private HashMap<Cara, Punto> diccionario;
  private HashMap<SemiArista, Punto> diccionarioCierre;

  Voronoi(DCEL triangulacion) {
    List<Vertice> vAux=new ArrayList<Vertice>(triangulacion.getVertices());
    List<SemiArista> sAux=new ArrayList<SemiArista>(triangulacion.getSemiAristas());
    List<Cara> cAux=new ArrayList<Cara>(triangulacion.getCaras());
    this.voronoi=new DCEL(vAux, sAux, cAux);

    this.diccionario=new HashMap<Cara, Punto>();
    this.diccionarioCierre=new HashMap<SemiArista, Punto>();
  }


  public DCEL getVoronoi() {
    return this.voronoi;
  }

  public HashMap<Cara, Punto> getDiccionario() {
    return this.diccionario;
  }
  public HashMap<SemiArista, Punto> getDiccionarioCierre() {
    return this.diccionarioCierre;
  }

  //El triangulo es ABC y A es el origen de la semiArista s
  public Punto getCircuncentro(SemiArista s) {
    Punto solucion;
    SemiArista Sa=s;
    SemiArista Sb=Sa.getPosterior();
    SemiArista Sc=Sb.getPosterior();
    Punto a=Sa.getOrigen().getC();
    Punto b=Sb.getOrigen().getC();
    Punto c=Sc.getOrigen().getC();
    //coeficientes de la mediatriz de AB por el punto medio
    if ((b.getY()-a.getY()!=0) && (c.getY()-b.getY()!=0)) {

      double x1 = (0.5)*(a.getX()+b.getX());
      double y1 = (0.5)*(a.getY()+b.getY());
      double x2 = (0.5)*(b.getX()+c.getX());
      double y2 = (0.5)*(b.getY()+c.getY());

      double m1 = - (double)(b.getX() -a.getX()) / (double)(b.getY() - a.getY());
      double m2 = - (double) (c.getX() -b.getX()) / (double) (c.getY() - b.getY());
      double n1 = y1 - m1*x1;
      double n2 = y2 - m2*x2;
      double Px = (n2 - n1)/(m1 - m2);
      double Py = m1*Px + n1;

      solucion=new Punto((int)Math.round(Px), (int)Math.round(Py));
    }
    else {
      //En este caso en el que dos son perpendiculares nos aprovechamos del hecho de que las
      //dos siguientes no lo van a ser (pues de otra forma no sería un triángulo)
      //Así nos ahorramos hacer los casos aparte
      //print("Arista perpendicular");
      solucion=getCircuncentro(Sb);
    }
    return solucion;
  }

  public Punto getPuntoMedio(SemiArista s) { //Quizás haya que darle el punto también
    Punto solucion;
    SemiArista Sa=s;
    SemiArista Sb=Sa.getPosterior();
    Punto a=Sa.getOrigen().getC();
    Punto b=Sb.getOrigen().getC();

    double x1 = (0.5)*(a.getX()+b.getX());
    double y1 = (0.5)*(a.getY()+b.getY());

    Punto medio=new Punto((int)Math.round(x1), (int)Math.round(y1));

    return medio;
  }

  //Le paso los dos puntos que determinan una recta y a partir del valor 'x' obtengo su coordenada 'y' en esa recta
  //Dar los puntos en el orden de la dirección del vector (el primero será el circuncentro y el segundo el pto medio de la arista)
  //Se usa para dibujar puntos muy lejanos y así pintar el diagrama de Voronoi
  public Integer sustituirEnRecta(int x, Punto a, Punto b) {

    int x0= a.getX();
    int y0=a.getY();
    int x1=b.getX();
    int y1=b.getY();

    Integer resultadoRedondeado=null;
    if (x1!=x0) {
      double m= ( (double) (y1-y0)/ (double) (x1-x0));
      double resultado=m*(x-x0)+y0;
      resultadoRedondeado=new Integer((int)Math.round(resultado));
    }
    return resultadoRedondeado;
  }

  //El área signada nos dice si el punto 'c' está a la derecha o a la izquierda de la recta 'ab' (negativos a la izquierda, giro en sentido horario).
  //En Processing el positivo será la izquierda debido a que la representación es simétrica a la usual.
  //Devolverá 1 si es positiva, -1 si es negativa y 0 si están alineados.
  public int areaSignada(Punto a, Punto b, Punto c) {
    int auxPos=b.getX()*c.getY()+c.getX()*a.getY()+a.getX()*b.getY();
    int auxNeg=a.getY()*b.getX()+b.getY()*c.getX()+a.getX()*c.getY();
    int area=auxPos-auxNeg;
    int solucion=0;
    if (area>0) {
      solucion=1;
    }
    else if (area<0) {
      solucion=-1;
    }
    else {
      solucion=0;
    }
    return solucion;
  }


  //Nos indica si el punto 'd' está dentro o fuera del triángulo ABC dado en sentido antihorario (sentido en el que recorríamos en el cierre)
  //Para ello deberá estar a la derecha de todos(las tres áreas signadas deberán ser positivas)
  //Como estamos en Processing y es simétrico hay que ver que sean negativas.
  //Devuelve 1 si está dentro, -1 si está fuera y 0 si está en la frontera
  public int enTriangulo(Punto a, Punto b, Punto c, Punto d) {
    int areaS1=areaSignada(a, b, d);
    int areaS2=areaSignada(b, c, d);
    int areaS3=areaSignada(c, a, d);
    int solucion=-1;

    if (areaS1+areaS2+areaS3==-3) {
      solucion=1;
    }
    //estará en la frontera si uno es cero y los otros -1 o si dos son cero y el otro -1
    else if (areaS1+areaS2+areaS3==-2 || (areaS1+areaS2+areaS3==-1 && areaS1*areaS2*areaS3==0)) {
      solucion=0;
    }
    else {
      solucion=-1;
    }
    return solucion;
  }

  public void realizarVoronoi() {
    List<Cara> caras=voronoi.getCaras();
    for (SemiArista s:voronoi.getSemiAristas()) {
      if (caras.indexOf(s.getCara())==0) {

        Punto p1=s.getOrigen().getC();
        Punto p2=s.getPosterior().getOrigen().getC();
        Punto auxCircun=getCircuncentro(s.getGemela());

        Punto auxMedio=getPuntoMedio(s);

        Integer puntoInf=null;
        //valor alto para que se vayan a infinito
        int valorInf=10000;
        int signo=0;
        int signo2=0;
        boolean casoDegenerado=false;
        //caso circuncentro dentro del triángulo
        if (areaSignada(p1, p2, auxCircun)==1) {
          signo= auxMedio.getX()-auxCircun.getX();
          signo2= auxMedio.getY()-auxCircun.getY();
          puntoInf=sustituirEnRecta(signo*valorInf, auxCircun, auxMedio);
        }
        //caso circuncentro fuera del triángulo 
        else if ((areaSignada(p1, p2, auxCircun)==-1)) {
          signo= auxCircun.getX()-auxMedio.getX();
          signo2= auxCircun.getY()-auxMedio.getY();
          puntoInf=sustituirEnRecta(signo*valorInf, auxCircun, auxMedio);
        }
        else {
          casoDegenerado=true;
          //caso en el que está alineado
          //habría que hacerlo pero como es muy raro y sólo pasará para muchos puntos (y seguramente caerá fuera del dibujo)
          //no lo hago porque no me da tiempo (habría que hacerlo)
        }
        //Creo el punto
        if (!casoDegenerado) {
          Punto nuevoPunto=null;
          if (puntoInf==null) {
            nuevoPunto=new Punto(0, signo2*valorInf);
          }
          else {
            nuevoPunto=new Punto(signo*valorInf, puntoInf.intValue());
          }

          this.diccionarioCierre.put(s, nuevoPunto);
        }
      }
    }
    for (Cara c:caras) {
      if (caras.indexOf(c)!=0) {
        Punto aux=getCircuncentro(c.getSemiArista());
        this.diccionario.put(c, aux);
      }
    }
  }
}

