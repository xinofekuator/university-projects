//clase encargada de realizar el cierre convexo

class CierreConvexo {

  private List<Punto> puntos;
  private List<Punto> aux;
  private List<Arista> cierre;
  private List<Punto> puntosEnLinea;

  CierreConvexo(List<Punto> puntos) {
    this.puntos=new ArrayList<Punto>(puntos);
    aux=new ArrayList<Punto>();
    cierre=new ArrayList<Arista>();
    puntosEnLinea=new ArrayList<Punto>();
  }

  public List<Punto> getPuntosEnLinea() {
    return this.puntosEnLinea;
  }
  public List<Arista> getCierre() {
    return this.cierre;
  }
  public List<Punto> getPuntos() {
    return this.puntos;
  }

  //Nos devuelve el punto más arriba y el más abajo(también el más a la izquierda y el más a la derecha en caso de existir y ser distintos de los anteriores)
  public Punto[] extremos() {
    Punto[] resultado=new Punto[4];

    if (this.puntos.isEmpty()) {
      resultado=null;
    }
    else {
      Punto arriba=this.puntos.get(0);
      Punto abajo=this.puntos.get(0);
      Punto izda=this.puntos.get(0);
      Punto dcha=this.puntos.get(0);

      for (Punto p:this.puntos) {
        //el de más arriba tendrá coordenadas menores
        if (p.getY() < arriba.getY()) {
          arriba=p;
        }
        //el de más abajo tendrá coordenadas mayores
        if (p.getY() > abajo.getY()) {
          abajo=p;
        }
        if (p.getX() < izda.getX()) {
          izda=p;
        }
        if (p.getX() > dcha.getX()) {
          dcha=p;
        }
      }

      resultado[0]=arriba;
      this.puntos.remove(arriba);
      if (abajo!=arriba) {
        resultado[1]=abajo;
        this.puntos.remove(abajo);
      }
      if (izda!=arriba && izda!=abajo) {
        resultado[2]=izda;
        this.puntos.remove(izda);
      }
      if (dcha!=arriba && dcha!=abajo && dcha!=izda) {
        resultado[3]=dcha;
        this.puntos.remove(dcha);
      }
    }
    return resultado;
  }

  //El área signada nos dice si el punto 'c' está a la derecha o a la izquierda de la recta 'ab' (negativo derecha, giro en sentido horario).
  //En Processing el positivo será la derecha debido a que la representación es simétrica a la usual.
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

  //Se usa la ecuación de una recta que pasa por dos puntos (x-x1)*(y2-y1)-(y-y1)*(x2-x1)=0 si el punto está en la recta
  //Donde a=(x1,y1) y b=(x2,y2)
  public double distanciaRecta(Punto a, Punto b, Punto c) {
    int eq1=b.getY()-a.getY();
    int eq2=-(b.getX()-a.getX());
    int eq3=(b.getY()-a.getY())*(-a.getX())+(a.getY())*(b.getX()-a.getX());
    double numerador=(double)Math.abs(eq1*c.getX()+eq2*c.getY()+eq3);
    double denominador=Math.sqrt(Math.pow(eq1, 2)+Math.pow(eq2, 2));
    return numerador/denominador;
  }

  //si tipo es 1 se comprueba por la izquierda y si es 2 se comprueba por la derecha (algo para el caso 0 cuando no ha encontrado????? HACER CASO RECTA SI PAUX ES NULL)
  public void cerrar(List<Punto> lista, Punto a, Punto b, int tipo) {
    List<Punto> listaAux=new ArrayList<Punto>();
    Punto pAux=null;
    double aux=0;
    for (Punto p:lista) {
      if (areaSignada(a, b, p)==1) {
        double valor = distanciaRecta(a, b, p);
        listaAux.add(p);
        if (valor > aux) {
          aux=valor;
          pAux=p;
        }
      }
    }

    if (pAux!=null) {
      //Arista aristaAux=new Arista(a, pAux);
      //cierre.add(aristaAux);
      listaAux.remove(pAux);
      cerrar(listaAux, a, pAux, tipo);
      cerrar(listaAux, pAux, b, tipo);
    }
    else {
      //listaAux.size()=0
      for (Punto p:lista) {
        if (areaSignada(a, b, p)==0) {
          puntosEnLinea.add(p);
        }
      }
      Arista aristaAux=new Arista(a, b);
      cierre.add(aristaAux);
    }
  }
}

