//clase donde se realizan la triangulación incremental y la de Delaunay

class Triangulacion {

  private List<Arista> cierre;
  private List<Punto> puntos;
  private DCEL triangulacion;
  private DCEL delaunay;
  private List<Punto> noEncontrados; //para borrar los que dan problemas

  Triangulacion(List<Arista> cierre, List<Punto> puntos) {
    this.cierre=new ArrayList<Arista>(cierre);
    this.puntos=new ArrayList<Punto>(puntos);
    this.noEncontrados=new ArrayList<Punto>();
    this.triangulacion=new DCEL();
    this.delaunay=triangulacion;
  }

  public DCEL getTriangulacion() {
    return this.triangulacion;
  }
  public DCEL getDelaunay() {
    return this.delaunay;
  }
  
  public List<Punto> getNoEncontrados(){
    return this.noEncontrados;
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

  //Comprobar que no es triángulo o que no hay nada dentro del cierre (caso fácil)
  //Realizamos la triangulación incremental
  public void triangular() {

    //borramos los puntos del cierre(sólo hará falta quitar el primero, ya que están duplicados en las aristas)
    for (Arista a:cierre) {
      Punto aux1=a.getA();
      puntos.remove(aux1);
    }
    //caso en el que sólo tenemos los puntos del cierre convexo y hay como mínimo 3 puntos en el cierre
    //En el caso de 2 puntos no hago DCEL (podría hacerlo, pero no tiene mucho sentido)
    if (puntos.size()==0 && cierre.size()>=3) {
      //Primero hacemos el DCEL del primer triángulo (si no hay más hemos terminado)
      List<Vertice> v=triangulacion.getVertices();
      List<Cara> c=triangulacion.getCaras();
      List<SemiArista> s=triangulacion.getSemiAristas();
      Arista aPrimera=cierre.get(0);
      cierre.remove(aPrimera);
      Arista aSegunda=cierre.get(0);
      cierre.remove(aSegunda);
      Arista aFinal=cierre.get(cierre.size()-1);
      cierre.remove(aFinal);

      Punto primero1=aPrimera.getA();
      Punto primero2=aPrimera.getB();
      //Punto segundo1=aSegunda.getA(); No es necesario porque coincide con primero2
      Punto segundo2=aSegunda.getB();

      Vertice v1=new Vertice();
      Vertice v2=new Vertice();
      Vertice v3=new Vertice();
      SemiArista interiorIni1=new SemiArista();
      SemiArista interiorIni2=new SemiArista();
      SemiArista interiorIni3=new SemiArista();
      SemiArista exteriorIni4=new SemiArista();
      SemiArista exteriorIni5=new SemiArista();
      SemiArista exteriorIni6=new SemiArista();
      Cara cExteriorIni=new Cara();
      Cara cInteriorIni=new Cara();

      //Vertice(Punto coordenadas, SemiArista semiaristaSaliente)
      v1.actualizar(primero1, interiorIni1);
      v2.actualizar(primero2, interiorIni2);
      v3.actualizar(segundo2, interiorIni3);
      //public SemiArista(Vertice origen, SemiArista gemela, SemiArista anterior, SemiArista posterior, Cara cara)
      interiorIni1.actualizar(v1, exteriorIni4, interiorIni3, interiorIni2, cInteriorIni);
      interiorIni2.actualizar(v2, exteriorIni6, interiorIni1, interiorIni3, cInteriorIni);
      interiorIni3.actualizar(v3, exteriorIni5, interiorIni2, interiorIni1, cInteriorIni);
      exteriorIni4.actualizar(v2, interiorIni1, exteriorIni6, exteriorIni5, cExteriorIni);
      exteriorIni5.actualizar(v1, interiorIni3, exteriorIni4, exteriorIni6, cExteriorIni);
      exteriorIni6.actualizar(v3, interiorIni2, exteriorIni5, exteriorIni4, cExteriorIni);
      //Cara(SemiArista semiarista)
      cInteriorIni.actualizar(interiorIni1);
      cExteriorIni.actualizar(exteriorIni4);

      Vertice verticeAux=v3;
      SemiArista nuevaInterna=exteriorIni5;
      SemiArista nuevaPosterior=exteriorIni6;
      SemiArista nuevaAnterior=exteriorIni4;

      v.add(v1);
      v.add(v2);
      v.add(v3);
      s.add(interiorIni1);
      s.add(interiorIni2);
      s.add(interiorIni3);
      s.add(exteriorIni4);      
      s.add(exteriorIni5);      
      s.add(exteriorIni6);
      c.add(cExteriorIni);
      c.add(cInteriorIni);

      //Una vez he borrado las 3 anteriores (en un inicio era mayor que 4)
      //Hay puntos dentro del cierre
      if (cierre.size()>0) {
        Arista aPenultima=cierre.get(cierre.size()-1);
        cierre.remove(aPenultima);
        //Vamos añadiendo triángulos hasta que no queden más
        for (Arista a:cierre) {        
          //print("BUCLE");
          //Triángulos intermedios
          //se añaden en cada iteración 4 semiaristas,1 cara y 1 vértice
          //Punto p1=a.getA();
          Punto p2=a.getB();
          Vertice vertice=new Vertice();
          SemiArista interior1=new SemiArista();
          SemiArista interior2=new SemiArista();
          SemiArista exterior3=new SemiArista();
          SemiArista exterior4=new SemiArista();
          Cara cInterior=new Cara();

          vertice.actualizar(p2, interior1);
          interior1.actualizar(vertice, exterior3, interior2, nuevaInterna, cInterior);
          interior2.actualizar(verticeAux, exterior4, nuevaInterna, interior1, cInterior);
          exterior3.actualizar(v1, interior1, nuevaAnterior, exterior4, cExteriorIni);
          exterior4.actualizar(vertice, interior2, exterior3, nuevaPosterior, cExteriorIni);
          cInterior.actualizar(nuevaInterna);

          nuevaInterna.setAnterior(interior1);
          nuevaInterna.setPosterior(interior2);
          nuevaInterna.setCara(cInterior);
          nuevaPosterior.setAnterior(exterior4);
          nuevaAnterior.setPosterior(exterior3);

          int nuevaInt=s.indexOf(nuevaInterna);
          int nuevaPos=s.indexOf(nuevaPosterior);
          int nuevaAnt=s.indexOf(nuevaAnterior);
          v.add(vertice);        
          s.add(interior1);     
          s.add(interior2);       
          s.add(exterior3);   
          s.add(exterior4);
          s.set(nuevaInt, nuevaInterna);
          s.set(nuevaPos, nuevaPosterior);
          s.set(nuevaAnt, nuevaAnterior);
          c.add(cInterior);

          verticeAux=vertice;
          nuevaInterna=exterior3;
          nuevaPosterior=exterior4;
        }
        //Último triángulo
        //se añaden 1 vértice, 4 semiAristas y 1 cara

        Punto f1=aFinal.getA();
        //Punto f2=aFinal.getB();
        Vertice vFinal=new Vertice();
        SemiArista interiorFinal1=new SemiArista();
        SemiArista interiorFinal2=new SemiArista();
        SemiArista exteriorFinal3=new SemiArista();      
        SemiArista exteriorFinal4=new SemiArista();
        Cara cInteriorFinal=new Cara();

        vFinal.actualizar(f1, interiorFinal1);
        interiorFinal1.actualizar(vFinal, exteriorFinal3, interiorFinal2, nuevaInterna, cInteriorFinal);
        interiorFinal2.actualizar(verticeAux, exteriorFinal4, nuevaInterna, interiorFinal1, cInteriorFinal);
        exteriorFinal3.actualizar(v1, interiorFinal1, nuevaAnterior, exteriorFinal4, cExteriorIni);
        exteriorFinal4.actualizar(vFinal, interiorFinal2, exteriorFinal3, nuevaPosterior, cExteriorIni);
        cInteriorFinal.actualizar(nuevaInterna);

        nuevaInterna.setAnterior(interiorFinal1);
        nuevaInterna.setPosterior(interiorFinal2);
        nuevaInterna.setCara(cInteriorFinal);
        nuevaAnterior.setPosterior(exteriorFinal3);
        nuevaPosterior.setAnterior(exteriorFinal4);

        int nuevaInt=s.indexOf(nuevaInterna);
        int nuevaPos=s.indexOf(nuevaPosterior);
        int nuevaAnt=s.indexOf(nuevaAnterior);
        v.add(vFinal);
        s.add(interiorFinal1);
        s.add(interiorFinal2);
        s.add(exteriorFinal3);
        s.add(exteriorFinal4);
        s.set(nuevaInt, nuevaInterna);
        s.set(nuevaPos, nuevaPosterior);
        s.set(nuevaAnt, nuevaAnterior);  
        c.add(cInteriorFinal);
      }
    }

    //Caso en el que hay mínimo tres puntos en el cierre y tenemos algún punto dentro
    //En este if sólo se realiza el triángulo inicial (luego se hará el caso recursivo)
    if (cierre.size()>2 && puntos.size()>0) {
      //caso unir punto con cierre convexo
      //ATENCIÓN: Se diferencian 3 casos porque dependiendo de si añadimo el primer triángulo, los intermedios o el final
      //las operaciones para modificar el DCEL cambian
      List<Vertice> v=triangulacion.getVertices();
      List<Cara> c=triangulacion.getCaras();
      List<SemiArista> s=triangulacion.getSemiAristas();
      Punto pInicial=puntos.get(0);
      puntos.remove(pInicial);
      Arista aInicial=cierre.get(0);
      cierre.remove(aInicial);
      //Se quita la última arista porque se trata por separado
      //No guardamos el punto porque coincide con el primero del incial
      Arista aFinal=cierre.get(cierre.size()-1);
      cierre.remove(aFinal);
      //PRIMER TRIÁNGULO
      //se añaden en cada paso 6 semiaristas,2 caras y 3 vértices
      Punto pInicial1=aInicial.getA();
      Punto pInicial2=aInicial.getB();

      Vertice v1=new Vertice();
      Vertice v2=new Vertice();
      Vertice v3=new Vertice();
      SemiArista interiorIni1=new SemiArista();
      SemiArista interiorIni2=new SemiArista();
      SemiArista interiorIni3=new SemiArista();
      SemiArista exteriorIni4=new SemiArista();
      SemiArista exteriorIni5=new SemiArista();
      SemiArista exteriorIni6=new SemiArista();
      Cara cExteriorIni=new Cara();
      Cara cInteriorIni=new Cara();

      //Vertice(Punto coordenadas, SemiArista semiaristaSaliente)
      v1.actualizar(pInicial, interiorIni1);
      v2.actualizar(pInicial1, interiorIni2);
      v3.actualizar(pInicial2, interiorIni3);
      //public SemiArista(Vertice origen, SemiArista gemela, SemiArista anterior, SemiArista posterior, Cara cara)
      interiorIni1.actualizar(v1, exteriorIni4, interiorIni3, interiorIni2, cInteriorIni);
      interiorIni2.actualizar(v2, exteriorIni6, interiorIni1, interiorIni3, cInteriorIni);
      interiorIni3.actualizar(v3, exteriorIni5, interiorIni2, interiorIni1, cInteriorIni);
      exteriorIni4.actualizar(v2, interiorIni1, exteriorIni6, exteriorIni5, cExteriorIni);
      exteriorIni5.actualizar(v1, interiorIni3, exteriorIni4, exteriorIni6, cExteriorIni);
      exteriorIni6.actualizar(v3, interiorIni2, exteriorIni5, exteriorIni4, cExteriorIni);
      //Cara(SemiArista semiarista)
      cInteriorIni.actualizar(interiorIni1);
      cExteriorIni.actualizar(exteriorIni4);

      Vertice verticeAux=v3;
      SemiArista nuevaInterna=exteriorIni5;
      SemiArista nuevaPosterior=exteriorIni6;
      SemiArista nuevaAnterior=exteriorIni4;

      v.add(v1);
      v.add(v2);
      v.add(v3);
      s.add(interiorIni1);
      s.add(interiorIni2);
      s.add(interiorIni3);
      s.add(exteriorIni4);      
      s.add(exteriorIni5);      
      s.add(exteriorIni6);
      c.add(cExteriorIni);
      c.add(cInteriorIni);      

      for (Arista a:cierre) {
        //TRIÁNGULOS INTERMEDIOS
        //se añaden en cada iteración 4 semiaristas,1 cara y 1 vértice
        Punto p1=a.getA();
        Punto p2=a.getB();
        Vertice vertice=new Vertice();
        SemiArista interior1=new SemiArista();
        SemiArista interior2=new SemiArista();
        SemiArista exterior3=new SemiArista();
        SemiArista exterior4=new SemiArista();
        Cara cInterior=new Cara();

        vertice.actualizar(p2, interior1);
        interior1.actualizar(vertice, exterior3, interior2, nuevaInterna, cInterior);
        interior2.actualizar(verticeAux, exterior4, nuevaInterna, interior1, cInterior);
        exterior3.actualizar(v1, interior1, nuevaAnterior, exterior4, cExteriorIni);
        exterior4.actualizar(vertice, interior2, exterior3, nuevaPosterior, cExteriorIni);
        cInterior.actualizar(nuevaInterna);

        nuevaInterna.setAnterior(interior1);
        nuevaInterna.setPosterior(interior2);
        nuevaInterna.setCara(cInterior);
        nuevaPosterior.setAnterior(exterior4);
        nuevaAnterior.setPosterior(exterior3);

        int nuevaInt=s.indexOf(nuevaInterna);
        int nuevaPos=s.indexOf(nuevaPosterior);
        int nuevaAnt=s.indexOf(nuevaAnterior);
        v.add(vertice);        
        s.add(interior1);     
        s.add(interior2);       
        s.add(exterior3);   
        s.add(exterior4);
        s.set(nuevaInt, nuevaInterna);
        s.set(nuevaPos, nuevaPosterior);
        s.set(nuevaAnt, nuevaAnterior);
        c.add(cInterior);

        verticeAux=vertice;
        nuevaInterna=exterior3;
        nuevaPosterior=exterior4;
      }
      //ÚLTIMO TRIÁNGULO
      //se añaden en cada iteración 2 semiaristas y 1 cara
      SemiArista interiorFinal1=new SemiArista();
      SemiArista exteriorFinal2=new SemiArista();
      Cara cInteriorFinal=new Cara();

      interiorFinal1.actualizar(verticeAux, exteriorFinal2, nuevaInterna, nuevaAnterior, cInteriorFinal);
      exteriorFinal2.actualizar(v2, interiorFinal1, exteriorIni6, nuevaPosterior, cExteriorIni); //exteriorIni6 es la anterior de la nuevaAnterior que pasa a estar en la cara interna
      cInteriorFinal.actualizar(nuevaAnterior);

      nuevaInterna.setAnterior(nuevaAnterior);
      nuevaInterna.setPosterior(interiorFinal1);
      nuevaInterna.setCara(cInteriorFinal);
      nuevaAnterior.setAnterior(interiorFinal1);
      nuevaAnterior.setPosterior(nuevaInterna);
      nuevaAnterior.setCara(cInteriorFinal);
      nuevaPosterior.setAnterior(exteriorFinal2);
      exteriorIni6.setPosterior(exteriorFinal2);

      int nuevaInt=s.indexOf(nuevaInterna);
      int nuevaPos=s.indexOf(nuevaPosterior);
      int nuevaAnt=s.indexOf(nuevaAnterior);
      int antNuevaAnt=s.indexOf(exteriorIni6);
      s.add(interiorFinal1);
      s.add(exteriorFinal2);
      s.set(nuevaInt, nuevaInterna);
      s.set(nuevaPos, nuevaPosterior);
      s.set(nuevaAnt, nuevaAnterior);  
      s.set(antNuevaAnt, exteriorIni6);
      c.add(cInteriorFinal);
    }

    //Hasta aquí lo único que hemos hecho es crear el DCEL inicial (a partir del cierre y un punto interior en caso de haber pts interiores)
    //Ahora en caso de quedar más puntos interiores realizamos el proceso recursivo
    if (puntos.size()>0) {
      int borrar=0;
      int aT=0;
      int bT=0;
      //print("||");
      for (Punto p:puntos) {
        List<Vertice> v=triangulacion.getVertices();
        List<Cara> c=triangulacion.getCaras();
        List<SemiArista> s=triangulacion.getSemiAristas();
        SemiArista aux1=null;
        SemiArista aux2=null;
        SemiArista aux3=null;
        int flag=-1; //Si vale 0 están alineados y si vale 1 está dentro del triángulo
        bT=c.size();
        if ((bT-aT)!=2) {
          //print(" SIZE:");
          //print(bT-aT);
        }
        aT=c.size();
        for (SemiArista semiAristaAux:s) {
          //si es la cara externa no lo hago
          if (c.indexOf(semiAristaAux.getCara())!=0) {
            aux1=semiAristaAux;
            aux2=aux1.getPosterior();
            aux3=aux2.getPosterior();
            //En el DCEL el sentido de los internos es el positivo (contrario a las agujas del reloj)
            Punto p1Triangulo=aux1.getOrigen().getC();
            Punto p2Triangulo=aux2.getOrigen().getC();
            Punto p3Triangulo=aux3.getOrigen().getC(); 
            //
            if (enTriangulo(p1Triangulo, p2Triangulo, p3Triangulo, p)==1) {
              flag=0;
              break;
            }
            else if (enTriangulo(p1Triangulo, p2Triangulo, p3Triangulo, p)==0) {
              //Veo en que triángulo está (ver explicación en el método triangularAuxDeg)
              //En el código actual no se usa, pero sería útil implementarlo en un futuro
              if (areaSignada(p1Triangulo, p2Triangulo, p)==0) {
                flag=1;
              }
              else if (areaSignada(p2Triangulo, p3Triangulo, p)==0) {
                flag=2;
              }
              else { 
                flag=3;
              }
              break;
            }
            else {
              continue;
            }
          }
        }
        if (flag==0) {
          triangularAux(p, aux1, aux2, aux3, this.triangulacion);
        }
        else if (flag>0) {
          triangularAuxDeg(p, aux1, aux2, aux3, this.triangulacion, flag);
          //Los añadimos a no encontrados para borrarlos
          //Habría que quitarlo en caso de implementar correctamente triangularAuxDeg
          noEncontrados.add(p);
          
        }
        else { 
          noEncontrados.add(p);         
          //print("FLAG");
          //print(flag);
        }
      }
      //print("TAM");
      //print(noEncontrados.size());
    }
  }
  //Se usa para añadir tres triángulos cuando aparece un punto dentro de uno de ellos
  //CASO RECURSIVO
  public void triangularAux(Punto p, SemiArista a1, SemiArista a2, SemiArista a3, DCEL triangulacion) {
    List<Vertice> v=triangulacion.getVertices();
    List<Cara> c=triangulacion.getCaras();
    List<SemiArista> s=triangulacion.getSemiAristas();

    Vertice v1=a1.getOrigen();
    Vertice v2=a2.getOrigen();
    Vertice v3=a3.getOrigen();

    //6 SemiAristas, 1 vertice y 3 caras
    Vertice vertice=new Vertice();
    SemiArista salienteP1=new SemiArista();
    SemiArista salienteP2=new SemiArista();
    SemiArista salienteP3=new SemiArista();
    SemiArista entranteP1=new SemiArista();
    SemiArista entranteP2=new SemiArista();
    SemiArista entranteP3=new SemiArista();
    Cara cT1=new Cara();
    Cara cT2=new Cara();
    Cara cT3=new Cara();

    vertice.actualizar(p, salienteP1);
    salienteP1.actualizar(v1, entranteP1, a3, entranteP3, cT3);
    salienteP2.actualizar(v2, entranteP2, a1, entranteP1, cT1);
    salienteP3.actualizar(v3, entranteP3, a2, entranteP2, cT2);
    entranteP1.actualizar(vertice, salienteP1, salienteP2, a1, cT1);
    entranteP2.actualizar(vertice, salienteP2, salienteP3, a2, cT2);
    entranteP3.actualizar(vertice, salienteP3, salienteP1, a3, cT3);
    cT1.actualizar(a1);
    cT2.actualizar(a2);
    cT3.actualizar(a3);

    Cara antiguaCara=a1.getCara();
    c.remove(antiguaCara);

    a1.setAnterior(entranteP1);
    a1.setPosterior(salienteP2);
    a1.setCara(cT1);
    a2.setAnterior(entranteP2);
    a2.setPosterior(salienteP3);
    a2.setCara(cT2);
    a3.setAnterior(entranteP3);
    a3.setPosterior(salienteP1);
    a3.setCara(cT3);

    int indice1=s.indexOf(a1);
    int indice2=s.indexOf(a2);
    int indice3=s.indexOf(a3);
    v.add(vertice);        
    s.add(salienteP1);     
    s.add(salienteP2);       
    s.add(salienteP3);   
    s.add(entranteP1);
    s.add(entranteP2);
    s.add(entranteP3);
    s.set(indice1, a1);
    s.set(indice2, a2);
    s.set(indice3, a3);
    c.add(cT1);
    c.add(cT2);
    c.add(cT3);
  }

  //CASO RECURSIVO para los puntos degenerados que están en triángulo con los otros tres
  //Se podría tratar el caso degenerado en el que los puntos aparecen en el triángulo
  //ESTE ES EL FALLO DE LOS PUNTOS NO TRIANGULADOS (estaban en triángulo con otras dos)
  //EXPLICACIÓN: no se veían en la triangulación incremental porque estaban en triángulo pero al no añadirlos al DCEL luego aparecen en Delaunay
  //Esta programado cómo sería, pero falla en Delaunay (hay algo incorrecto en el DCEL)
  //No me ha dado tiempo a detectar el fallo

  public void triangularAuxDeg(Punto p, SemiArista a1, SemiArista a2, SemiArista a3, DCEL triangulacion, int flag) {
    /*
    List<Vertice> v=triangulacion.getVertices();
     List<Cara> c=triangulacion.getCaras();
     List<SemiArista> s=triangulacion.getSemiAristas();
     SemiArista aux=null;
     switch(flag) {
     case 1:
     //Caso en el que el punto está en la semiArista a1
     aux=a1;
     break;
     case 2:
     //Caso en el que el punto está en la semiArista a2
     aux=a2;
     break;
     case 3:
     //Caso en el que el punto está en la semiArista a1
     aux=a3;
     break;
     }
     
     SemiArista auxAnterior=aux.getAnterior();
     SemiArista auxPosterior=aux.getPosterior();
     SemiArista auxGemela=aux.getGemela();
     SemiArista auxGemelaAnterior=auxGemela.getAnterior();
     SemiArista auxGemelaPosterior=auxGemela.getPosterior();
     Cara auxCara=aux.getCara();
     Cara auxGemelaCara=auxGemela.getCara();
     
     if (c.indexOf(auxGemelaCara)!=0) {
     //print("ENTRO");
     //Se añade un vértice, 4 semiAristas y 2 caras
     //Se elimina una semiArista 
     //Se modifican dos semiAristas
     Vertice vNuevo=new Vertice();
     SemiArista s1T1=new SemiArista();
     SemiArista s2T1=new SemiArista();
     SemiArista s1T2=new SemiArista();
     SemiArista s2T2=new SemiArista();
     Cara cT1=new Cara();
     Cara cT2=new Cara();
     
     vNuevo.actualizar(p, s1T1);
     s1T1.actualizar(vNuevo, s1T2, s2T1, auxAnterior, cT1);
     s1T2.actualizar(auxAnterior.getOrigen(), s1T1, auxPosterior, s2T2, cT2);
     //Las gemelas a null porque aún no están creadas (luego las fijo)
     s2T1.actualizar(aux.getOrigen(), null, auxAnterior, s1T1, cT1);
     s2T2.actualizar(vNuevo, null, s1T2, auxPosterior, cT2);
     cT1.actualizar(s1T1);
     cT2.actualizar(s1T2);
     
     auxAnterior.setAnterior(s1T1);
     auxAnterior.setPosterior(s2T1);
     auxAnterior.setCara(cT1);
     auxPosterior.setAnterior(s2T2);
     auxPosterior.setPosterior(s1T2);
     auxPosterior.setCara(cT2);
     
     v.add(vNuevo);
     s.add(s1T1);
     s.add(s1T2);
     s.add(s2T1);
     s.add(s2T2);
     c.add(cT1);
     
     s.remove(aux);
     c.remove(auxCara);
     //Se hace lo mismo en el triángulo de la gemela de aux
     //Se añaden 4 semiAristas y 2 caras (el vértice es el mismo de antes)
     //Se elimina una semiArista
     //Se modifican dos semiAristas
     SemiArista s1T1G=new SemiArista();
     SemiArista s2T1G=new SemiArista();
     SemiArista s1T2G=new SemiArista();
     SemiArista s2T2G=new SemiArista();
     Cara cT1G=new Cara();
     Cara cT2G=new Cara();
     
     s1T1G.actualizar(vNuevo, s1T2G, s2T1G, auxGemelaAnterior, cT1G);
     s1T2G.actualizar(auxGemelaAnterior.getOrigen(), s1T1G, auxGemelaPosterior, s2T2G, cT2G);
     //Al ser simétrico las gemelas son de la cara opuesta
     s2T1G.actualizar(auxGemela.getOrigen(), s2T2, auxGemelaAnterior, s1T1G, cT1G);
     s2T2G.actualizar(vNuevo, s2T1, s1T2G, auxGemelaPosterior, cT2G);
     cT1G.actualizar(s1T1G);
     cT2G.actualizar(s1T2G);
     
     auxGemelaAnterior.setAnterior(s1T1G);
     auxGemelaAnterior.setPosterior(s2T1G);
     auxGemelaAnterior.setCara(cT1G);
     auxGemelaPosterior.setAnterior(s2T2G);
     auxGemelaPosterior.setPosterior(s1T2G);
     auxGemelaPosterior.setCara(cT2G);
     
     cT1G.setSemiArista(s1T1G);
     cT2G.setSemiArista(s1T2G);
     
     s.add(s1T1G);
     s.add(s1T2G);
     s.add(s2T1G);
     s.add(s2T2G);
     c.add(cT1G);
     c.add(cT2G);
     
     s.remove(auxGemela);
     c.remove(auxGemelaCara);
     
     //FIJO LAS DOS GEMELAS NULL QUE  NO FIJE ANTES
     s2T1.setGemela(s2T2G);
     s2T2.setGemela(s2T1G);
     }*/
  }

  //DELAUNAY
  //Se le da en el sentido contrario a las agujas del reloj
  //Estará dentro si es mayor que cero
  //Por tanto, como en Processing todo es simétrico tendrá que ser negativo
  //Devolverá 1 si está dentro, 0 si está en la frontera y -1 si está fuera
  public int enCirculo(Punto a, Punto b, Punto c, Punto d) {

    double cA=Math.pow(a.getX(), 2) + Math.pow(a.getY(), 2);
    double cB=Math.pow(b.getX(), 2) + Math.pow(b.getY(), 2);
    double cC=Math.pow(c.getX(), 2) + Math.pow(c.getY(), 2);
    double cD=Math.pow(d.getX(), 2) + Math.pow(d.getY(), 2);

    double parte1=(b.getX()*c.getY()*cD + c.getX()*d.getY()*cB + d.getX()*b.getY()*cC)-(d.getX()*c.getY()*cB + b.getX()*d.getY()*cC + c.getX()*b.getY()*cD );
    double parte2=-a.getX()* ( (c.getY()*cD+d.getY()*cB+b.getY()*cC)-(c.getY()*cB+d.getY()*cC+b.getY()*cD) );
    double parte3=a.getY()* ( (c.getX()*cD+d.getX()*cB+b.getX()*cC)-(c.getX()*cB+d.getX()*cC+b.getX()*cD) );
    double parte4=-cA* ( (c.getX()*d.getY()+d.getX()*b.getY()+b.getX()*c.getY())-(c.getX()*b.getY()+d.getX()*c.getY()+b.getX()*d.getY()) );

    double valor=parte1+parte2+parte3+parte4;

    int resultado=-1;
    if (valor>0) {
      resultado=1;
    }
    else if (valor==0) {
      resultado=0;
    }
    else {
      resultado=-1;
    }
    return resultado;
  }
  public void flip(SemiArista sA, DCEL d) {
    List<Vertice> v=d.getVertices();
    List<Cara> c=d.getCaras();
    List<SemiArista> s=d.getSemiAristas();

    SemiArista a1T1=sA;
    SemiArista a2T1=a1T1.getPosterior();
    SemiArista a3T1=a2T1.getPosterior();
    SemiArista a1T2=sA.getGemela();
    SemiArista a2T2=a1T2.getPosterior();
    SemiArista a3T2=a2T2.getPosterior();

    //Añadir 2 semiAristas, borrar las dos caras y las dos semiAristas, añadir dos caras nuevas y cambiar las otras 4
    SemiArista t1=new SemiArista();
    SemiArista t2=new SemiArista();
    Cara cT1=new Cara();
    Cara cT2=new Cara();

    t1.actualizar(a3T2.getOrigen(), t2, a2T2, a3T1, cT1);
    t2.actualizar(a3T1.getOrigen(), t1, a2T1, a3T2, cT2);
    cT1.actualizar(t1);
    cT2.actualizar(t2);

    a2T1.setAnterior(a3T2);
    a2T1.setPosterior(t2);
    a2T1.setCara(cT2);
    a3T1.setAnterior(t1);
    a3T1.setPosterior(a2T2);
    a3T1.setCara(cT1);
    a2T2.setAnterior(a3T1);
    a2T2.setPosterior(t1);
    a2T2.setCara(cT1);
    a3T2.setAnterior(t2);
    a3T2.setPosterior(a2T1);
    a3T2.setCara(cT2);

    s.add(t1);
    s.add(t2);
    c.add(cT1);
    c.add(cT2);

    s.remove(a1T1);
    s.remove(a1T2);
    c.remove(a1T1.getCara());
    c.remove(a1T2.getCara());
  }

  public boolean ConvertirDelaunay() {
    boolean modificado=false;
    List<Vertice> v=this.delaunay.getVertices();
    List<Cara> c=this.delaunay.getCaras();
    List<SemiArista> s=this.delaunay.getSemiAristas();

    List<Vertice> vAux=new ArrayList<Vertice>(v);
    List<SemiArista> sAux=new ArrayList<SemiArista>(s);
    List<Cara> cAux=new ArrayList<Cara>(c);
    DCEL DCELAux=new DCEL(vAux, sAux, cAux);

    List<SemiArista> aristasCambiadas=new ArrayList<SemiArista>();
    List<Cara> carasCambiadas=new ArrayList<Cara>();
    for (SemiArista a:s) {
      SemiArista semiArista1T1=a;
      SemiArista semiArista2T1=a.getPosterior();
      SemiArista semiArista3T1=semiArista2T1.getPosterior();
      Punto p1T1=semiArista1T1.getOrigen().getC();
      Punto p2T1=semiArista2T1.getOrigen().getC();
      Punto p3T1=semiArista3T1.getOrigen().getC();

      SemiArista semiArista1T2=a.getGemela();
      SemiArista semiArista2T2=semiArista1T2.getPosterior();
      SemiArista semiArista3T2=semiArista2T2.getPosterior();
      Punto p1T2=semiArista1T2.getOrigen().getC();
      Punto p2T2=semiArista2T2.getOrigen().getC();
      Punto p3T2=semiArista3T2.getOrigen().getC();

      Cara cara1=semiArista1T1.getCara();
      Cara cara2=semiArista1T2.getCara();
      boolean estaEnCierre= (c.indexOf(semiArista1T2.getCara()))==0 || (c.indexOf(semiArista1T1.getCara()))==0;
      boolean noCambiada2= (aristasCambiadas.indexOf(semiArista1T1)==-1)&&(aristasCambiadas.indexOf(semiArista1T2)==-1);
      boolean noCambiada= (carasCambiadas.indexOf(cara1)==-1) && (carasCambiadas.indexOf(cara2)==-1);
      //boolean repetido=cambiadas.contains(semiArista1T2); //para que no mire dos veces(por culpa de la gemela)
      int posicionT1=enCirculo(p1T1, p2T1, p3T1, p3T2);
      int posicionT2=enCirculo(p1T2, p2T2, p3T2, p3T1);

      //Comprobar que no está en el cierre y que necesita ser flipada
      if ((posicionT1==1 || posicionT2==1) && !estaEnCierre && noCambiada2) {
        modificado=true;
        flip(semiArista1T1, DCELAux);
        aristasCambiadas.add(semiArista1T1);
        aristasCambiadas.add(semiArista2T1);
        aristasCambiadas.add(semiArista3T1);
        aristasCambiadas.add(semiArista1T2);
        aristasCambiadas.add(semiArista2T2);
        aristasCambiadas.add(semiArista3T2);
        carasCambiadas.add(cara1);
        carasCambiadas.add(cara2);
        //break;
      }
    }

    this.delaunay=DCELAux;

    return modificado;
  }
}

