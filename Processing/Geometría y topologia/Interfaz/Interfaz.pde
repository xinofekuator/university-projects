//Clase donde se implementa la interfaz gráfica

import java.util.*;

private int cFondo;
private int largo=1100;//1000
private int ancho=650;//600
private int nPuntos=0; //para crear puntos aleatorios
private String tipoTriangulacion="Incremental";
private Boton pulsado=null;//para crear puntos aleatorios
private Boton pulsado2=null;//para elegir el algoritmo de triangulación

private boolean enPunto=false; //Ver si estoy en un punto para moverlo
private Punto puntoABorrar=null; //punto que tengo que borrar

private Boton numCaras=null;
private Boton numAristas=null;
private Boton numVertices=null;

private List<Punto> puntos = new ArrayList<Punto>();
private List<Boton> botones = new ArrayList<Boton>();
private List<Arista> cierre = new ArrayList<Arista>();
private List<SemiArista> aristasTriangulacion = new ArrayList<SemiArista>();
private List<SemiArista> aristasDelaunay = new ArrayList<SemiArista>();
private DCEL DCELDelaunay=new DCEL(); 
private HashMap<Cara, Punto> voronoi = new HashMap<Cara, Punto>();
private HashMap<SemiArista, Punto> voronoiCierre = new HashMap<SemiArista, Punto>();
private List<Punto> puntosVoronoi= new ArrayList<Punto>();
private List<Arista> aristasVoronoi= new ArrayList<Arista>();

void setup() {
  size(largo, ancho);
  inicia();
}

void inicia() {
  cFondo=200;
  botones.add(new Boton(825, 50, "Envolvente"));
  //botones.add(new Boton(825, 50+80+25, "Triangular"));

  Boton triangularBoton=new Boton(825, 50+80+25, "Triangular");
  triangularBoton.cambiarTam(40, 150);
  triangularBoton.setTamTexto(20);
  botones.add(triangularBoton);
  Boton incremental=new Boton(825, 40+50+80+25, "Incremental");
  incremental.cambiarTam(40, 75);
  incremental.setTamTexto(10);
  incremental.setTipo(2);
  Boton delaunay=new Boton(900, 40+50+80+25, "Delaunay");
  delaunay.cambiarTam(40, 75);
  delaunay.setTamTexto(10);
  delaunay.setTipo(2);
  botones.add(incremental);
  botones.add(delaunay);

  botones.add(new Boton(825, 50+160+50, "Voronoi"));  
  //botones.add(new Boton(825, 50+240+75, "Puntos"));
  Boton p0=new Boton(825, 50+240+75, "Puntos");
  p0.cambiarTam(40, 150);
  p0.setTamTexto(20);
  Boton p1=new Boton(825, 40+50+240+75, "20");
  p1.cambiarTam(40, 50);
  p1.setTamTexto(14);
  p1.setTipo(1);
  Boton p2=new Boton(875, 40+50+240+75, "200");
  p2.cambiarTam(40, 50);
  p2.setTamTexto(14);
  p2.setTipo(1);
  Boton p3=new Boton(925, 40+50+240+75, "1000");
  p3.cambiarTam(40, 50);
  p3.setTamTexto(14);
  p3.setTipo(1);
  botones.add(p0);
  botones.add(p1);
  botones.add(p2);
  botones.add(p3);

  botones.add(new Boton(825, 50+320+100, "Borrar"));

  //Botones contadores
  //Botones de tipo -1 (no pasa nada al pasar por encima) para el número de caras,aristas y vértices
  numVertices=new Boton(50+107, 575, "0");
  numVertices.cambiarTam(50, 60);
  numVertices.setTamTexto(18);
  numVertices.setTipo(-1);
  numAristas=new Boton(50+167+107, 575, "0");
  numAristas.cambiarTam(50, 60);
  numAristas.setTamTexto(18);
  numAristas.setTipo(-1);
  numCaras=new Boton(50+167+167+107, 575, "0");
  numCaras.cambiarTam(50, 60);
  numCaras.setTamTexto(18);
  numCaras.setTipo(-1);
  botones.add(numVertices);
  botones.add(numAristas);
  botones.add(numCaras);
}

void draw() {
  frameRate(60);
  background(cFondo);

  //zona de dibujo
  fill(255);
  rect(50, 50, 700, 500);
  //puntos
  for (Punto i : puntos) {
    i.display();
  }

  for (Arista a:cierre) {
    if (a!=null) {
      //p.setColor(color(255, 0, 0));
      a.display();
    }
  }
  for (SemiArista s :aristasTriangulacion) {
    if (s!=null && s.getPosterior()!=null) {
      s.display();
    }
    else {
      print("Error en el display");
    }
  }
  for (SemiArista s :aristasDelaunay) {
    if (s!=null && s.getPosterior()!=null) {
      s.display();
    }
    else {
      print("Error en el display");
    }
  }
  for (Punto p: puntosVoronoi) {
    p.setColor(color(255, 0, 0));
    p.display();
  }
  for (Arista a : aristasVoronoi) {
    if (a.getA()!=null && a.getB()!=null) {
      stroke(color(250, 0, 0));
      a.display();
      stroke(0);
    }
  }
  //Rectángulos del fondo (para que no se salgan los dibujos de la zona de dibujo)
  fill(200);
  stroke(200);
  rect(0, 0, 49-1, ancho);
  rect(750+1, 0, largo-750, ancho);
  rect(0, 0, largo, 50-1);
  rect(0, 550+1, largo, ancho-550);
  stroke(0);
  //barra del menú
  fill(color(100, 100, 200));
  rect(800, 25, 200, 550);
  //barra de caras,vértices y aristas
  rect(50, 575, 501, 50);
  //Se reservan 60 de cada uno de los tres rectángulos para el número y los otros 107 para el 
  textSize(18);

  rect(50, 575, 167, 50);
  fill(0);
  text("Vértices", 50, 575, 107, 50);
  fill(color(100, 100, 200));
  rect(50+167, 575, 167, 50);
  fill(0);
  text("Aristas", 50+167, 575, 107, 50);
  fill(color(100, 100, 200));
  rect(50+167+167, 575, 167, 50);
  fill(0);
  text("Caras", 50+167+167, 575, 107, 50);
  fill(color(100, 100, 200));

  //Rectángulo del autor
  rect(600, 575, 150, 50);
  //Nombre del autor
  textSize(10);
  fill(0);
  text("Aplicación realizada por: \nIgnacio Amaya", 600, 575, 150, 50);
  fill(color(100, 100, 200));
  for (Boton b : botones) {
    if (b.estaDentro(mouseX, mouseY)) {
      if (b.getTipo()!=-1) {
        b.setColor(color(150, 220, 250)); //color más claro al pasar el ratón por encima
      }
    }
    b.display();
    if (b.getTipo()!=-1) {
      b.setColor(color(110, 170, 250)); //devuelve el color natural al no estar el ratón encima
    }
  }
}

void mouseDragged() {
  double grosor=1.5; //contando con que el grosor es 3
  //boolean enPunto=false;
  Punto aux=null;
  if (puntos.size()<=20) {
    if (enPunto) {
      puntos.remove(puntoABorrar);
      Punto nuevoPunto=new Punto(mouseX, mouseY);
      puntos.add(nuevoPunto);
      puntoABorrar=nuevoPunto;
    }

    if (!cierre.isEmpty()) {
      dibujarCierre();
    }
    if (!aristasTriangulacion.isEmpty()) {
      dibujarTriangulacion();
    }
    if (!aristasDelaunay.isEmpty()) {
      dibujarDelaunay();
    }
    if (!puntosVoronoi.isEmpty() && !aristasVoronoi.isEmpty()) {
      puntosVoronoi.clear();
      aristasVoronoi.clear();
      dibujarVoronoi();
    }
  }
}

void mousePressed() {
  double grosor=1.5; //contando con que el grosor es 3
  enPunto=false;
  for (Punto p:puntos) {
    if (mouseX>p.getX()-grosor*2 && mouseX<p.getX()+grosor*2 && mouseY>p.getY()-grosor*2 && mouseY<p.getY()+grosor*2) {
      enPunto=true;
      puntoABorrar=p;
    }
  }
  if ((mouseX>50+grosor && mouseX<750-grosor && mouseY>50+grosor && mouseY<550-grosor) && !enPunto) {
    Punto aux=new Punto(mouseX, mouseY);
    puntos.add(aux);
  }
  for (Boton b : botones) {
    if (b.estaDentro(mouseX, mouseY)) {
      if (b.getTipo()==0) {
        b.setColor(color(200, 140, 140));
        frameRate(5);
        b.display();
      }
      if (b.getTipo()==1) {
        if (pulsado==null) {
          b.setColor(color(200, 140, 140));
          nPuntos=Integer.parseInt(b.getTexto());
          pulsado=b;
          b.setTipo(-1);
        }

        if (pulsado!=b) {
          b.setColor(color(200, 140, 140));
          nPuntos=Integer.parseInt(b.getTexto());
          b.setTipo(-1);
          pulsado.setTipo(1);
          pulsado=b;
        }
      }

      if (b.getTipo()==2) {
        if (pulsado2==null) {
          b.setColor(color(200, 140, 140));
          tipoTriangulacion=b.getTexto();
          pulsado2=b;
          b.setTipo(-1);
        }
        if (pulsado2!=b) {
          b.setColor(color(200, 140, 140));
          tipoTriangulacion=b.getTexto();
          b.setTipo(-1);
          pulsado2.setTipo(2);
          pulsado2=b;
        }
      }

      String texto=b.getTexto();
      if (texto=="Borrar") {
        puntos.clear();
        cierre.clear();
        aristasTriangulacion.clear();
        aristasDelaunay.clear();
        puntosVoronoi.clear();
        aristasVoronoi.clear();
        numCaras.setTexto("0");
        numVertices.setTexto("0");
        numAristas.setTexto("0");
      }
      if (texto=="Puntos") {
        puntos.clear();
        cierre.clear();
        aristasTriangulacion.clear();
        aristasDelaunay.clear();
        puntosVoronoi.clear();
        aristasVoronoi.clear();
        Random rand=new Random();
        for (int i=1;i<=nPuntos;i++) {
          //int xaux=rand.nextInt(698) + 52; //ver dimensiones del área de dibujo
          //int yaux=rand.nextInt(498) + 52; //ver dimensiones del área de dibujo
          int xaux=rand.nextInt(678) + 62; //ver dimensiones del área de dibujo
          int yaux=rand.nextInt(478) + 62; //ver dimensiones del área de dibujo
          Punto aux=new Punto(xaux, yaux);
          puntos.add(aux);
        }
        numVertices.setTexto(Integer.toString(nPuntos));
      }
      if (texto=="Envolvente") {
        cierre.clear();
        aristasTriangulacion.clear();
        aristasDelaunay.clear();
        puntosVoronoi.clear();
        aristasVoronoi.clear();

        cierre=dibujarCierre();

        numVertices.setTexto(Integer.toString(puntos.size()));
        numAristas.setTexto(Integer.toString(cierre.size()));
      }
      if (texto=="Triangular") {
        cierre.clear();
        aristasTriangulacion.clear();
        aristasDelaunay.clear();
        puntosVoronoi.clear();
        aristasVoronoi.clear();

        if (tipoTriangulacion=="Incremental") {
          dibujarTriangulacion();
        }
        if (tipoTriangulacion=="Delaunay") {
          dibujarDelaunay();
        }
      }
      if (texto=="Voronoi") {
        //cierre.clear();
        cierre.clear();
        aristasTriangulacion.clear();
        aristasDelaunay.clear();
        puntosVoronoi.clear();
        aristasVoronoi.clear();
        voronoiCierre.clear();

        dibujarDelaunay();

        dibujarVoronoi();
      }
    }
  }
}

public List<Arista> dibujarCierre() {
  CierreConvexo q=new CierreConvexo(puntos);
  Punto[] extremos = new Punto[4];
  extremos=q.extremos(); //arriba, abajo, izda, dcha
  if (extremos!=null) {
    if (extremos[0]!=null && extremos[1]!=null && extremos[2]!=null && extremos[3]!=null) {
      q.cerrar(q.getPuntos(), extremos[0], extremos[2], 1);
      q.cerrar(q.getPuntos(), extremos[2], extremos[1], 1);
      q.cerrar(q.getPuntos(), extremos[1], extremos[3], 2);
      q.cerrar(q.getPuntos(), extremos[3], extremos[0], 2);
    }
    if (extremos[0]!=null && extremos[1]!=null && extremos[2]==null && extremos[3]==null) {
      q.cerrar(q.getPuntos(), extremos[0], extremos[1], 1);
      q.cerrar(q.getPuntos(), extremos[1], extremos[0], 2);
    }
    if (extremos[0]!=null && extremos[1]!=null && extremos[2]!=null && extremos[3]==null) {
      q.cerrar(q.getPuntos(), extremos[0], extremos[2], 1);
      q.cerrar(q.getPuntos(), extremos[2], extremos[1], 1);
      q.cerrar(q.getPuntos(), extremos[1], extremos[0], 2);
    }          
    if (extremos[0]!=null && extremos[1]!=null && extremos[2]==null && extremos[3]!=null) {
      q.cerrar(q.getPuntos(), extremos[0], extremos[1], 1);
      q.cerrar(q.getPuntos(), extremos[1], extremos[3], 2);
      q.cerrar(q.getPuntos(), extremos[3], extremos[0], 2);
    }
  }
  cierre=q.getCierre();
  //Añadir para tratar los puntos en línea

  List<Punto> puntosEnLinea=new ArrayList<Punto>();
  puntosEnLinea=q.getPuntosEnLinea();
  for (Punto p: puntosEnLinea) {
    puntos.remove(p);
  }

  for (Arista a:cierre) {
    if (a!=null) {
      //p.setColor(color(255, 0, 0));
      a.display();
    }
  }
  return cierre;
}

public void dibujarTriangulacion() {
  cierre=dibujarCierre();
  Triangulacion t=new Triangulacion(cierre, puntos);
  t.triangular();
  DCEL strucTriangulacion=t.getTriangulacion();
  aristasTriangulacion=strucTriangulacion.getSemiAristas();

  aristasDelaunay.clear();
  
  //Se borran los que dan problemas
  for (Punto p: t.getNoEncontrados()) {
    puntos.remove(p);
  }

  for (SemiArista s:aristasTriangulacion) {
    if (s!=null) {
      if (s.getPosterior()!=null)
        s.display();
    }
    if (s.getPosterior()==null) {
      print("nulos en posterior");
    }
  }
  numCaras.setTexto(Integer.toString(strucTriangulacion.getCaras().size()));
  numVertices.setTexto(Integer.toString(strucTriangulacion.getVertices().size()));
  //print("PUNTOS VERTICES: ");
  //print(puntos.size());
  numAristas.setTexto(Integer.toString(strucTriangulacion.getSemiAristas().size()/2));
}

public void dibujarDelaunay() {
  cierre=dibujarCierre();
  Triangulacion t=new Triangulacion(cierre, puntos);
  t.triangular();
  boolean modificado1=true;
  
  //Se borran los que dan problemas
  for (Punto p: t.getNoEncontrados()) {
    puntos.remove(p);
  }

  while (modificado1) {
    //print("BUCLE ");
    modificado1=t.ConvertirDelaunay();
  }

  aristasDelaunay=t.getDelaunay().getSemiAristas();
  DCELDelaunay=t.getDelaunay();

  aristasTriangulacion.clear();
  cierre.clear();

  aristasTriangulacion.clear();
  for (SemiArista s:aristasDelaunay) {
    if (s!=null) {
      if (s.getPosterior()!=null)
        s.display();
    }
    if (s.getPosterior()==null) {
      print("nulos en posterior");
    }
  }
  numCaras.setTexto(Integer.toString(DCELDelaunay.getCaras().size()));
  numVertices.setTexto(Integer.toString(DCELDelaunay.getVertices().size()));
  numAristas.setTexto(Integer.toString(DCELDelaunay.getSemiAristas().size()/2));
}

public void dibujarVoronoi() {
  Voronoi vor=new Voronoi(DCELDelaunay);
  List<Cara> listaCaras=DCELDelaunay.getCaras();
  vor.realizarVoronoi();
  voronoi=vor.getDiccionario();
  voronoiCierre=vor.getDiccionarioCierre();
  for (Cara c:voronoi.keySet()) {
    Punto circuncentro=voronoi.get(c);
    puntosVoronoi.add(circuncentro);
    //circuncentro.display(); No hace falta, ya lo hace en el draw (si lo hago aquí salen fuera de la zona de dibujo)

    SemiArista s1=c.getSemiArista();
    SemiArista s2=s1.getPosterior();
    SemiArista s3=s2.getPosterior();
    Cara g1=s1.getGemela().getCara();
    Cara g2=s2.getGemela().getCara();
    Cara g3=s3.getGemela().getCara();
    if (listaCaras.indexOf(g1)!=0) {
      Punto circuncAux1=voronoi.get(g1);
      aristasVoronoi.add(new Arista(circuncentro, circuncAux1));
    }
    else {
      Punto circuncAux1=voronoiCierre.get(s1.getGemela());
      aristasVoronoi.add(new Arista(circuncentro, circuncAux1));
    }
    if (listaCaras.indexOf(g2)!=0) {
      Punto circuncAux2=voronoi.get(g2);
      aristasVoronoi.add(new Arista(circuncentro, circuncAux2));
    }
    else {
      Punto circuncAux2=voronoiCierre.get(s2.getGemela());
      aristasVoronoi.add(new Arista(circuncentro, circuncAux2));
    }
    if (listaCaras.indexOf(g3)!=0) {
      Punto circuncAux3=voronoi.get(g3);
      aristasVoronoi.add(new Arista(circuncentro, circuncAux3));
    }
    else {
      Punto circuncAux3=voronoiCierre.get(s3.getGemela());
      aristasVoronoi.add(new Arista(circuncentro, circuncAux3));
    }
  }
}

