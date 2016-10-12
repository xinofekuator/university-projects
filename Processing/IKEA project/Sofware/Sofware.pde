import java.awt.*;
import java.awt.event.*;
private int cFondo;//variables que indican los colores del fondo, los muebles y las habitaciones
private int largo = 1000;
private int ancho = 600;
private static final int N_PUERTAS=3;
private static final int N_MUEBLES=15;
private static final int N_HABITACIONES=3;
private static final int N_COLUMNAS=3;
private static int N_ELEMENTOS=N_MUEBLES+N_HABITACIONES+N_PUERTAS+N_COLUMNAS;
private Elemento[] lista_Elementos=new Elemento[N_ELEMENTOS];
int turno=0; //se usa en mousePressed
int xRel=0;//se usa para ver la posición relativa de mouseX
int yRel=0;//se usa para ver la posición relativa de mouseY
int index=-1;//se usa para saber que mueble vamos a mover (-1 indica que no se mueve ninguno)
int indiceChoque=-1;//se usa para saber qué mueble movemos cuando se empujan los muebles
int posX=0;//indica la posible posición de la x en el siguiente frame
int posY=0;//indica la posible posición de la y en el siguiente frame
color colorInterior;//color de los muebles antes de realizar clik
Robot robot; //sirve para que al chocar el ratón no cambie de posición (se inicializa en setup)
void setup() { //este método es invocado al comienzo de la ejecución del código para la inicialización
  frameRate(2000);
  size(largo, ancho);
  inicia();
  try { 
    robot = new Robot();
    robot.setAutoDelay(0);
  } 
  catch (Exception e) {
    e.printStackTrace();
  }
}


void inicia() { //inicializamos todas las Elementos
  cFondo=100;
  Figura nula_rect=new Rectangulo(0, 0, 0, 0); //para evitar el null pointer exception
  Linea nula_linea=new Linea(0, 0, 0, 0); //para evitar el null pointer exception
  for (int i = 0; i<N_HABITACIONES; i++) {
    lista_Elementos[i]=new Habitacion(nula_rect);
  }
  for (int i = N_HABITACIONES; i<N_MUEBLES+N_HABITACIONES; i++) {
    lista_Elementos[i]=new Mueble(nula_rect);
  }
  for (int i = N_MUEBLES+N_HABITACIONES; i<N_MUEBLES+N_HABITACIONES+N_PUERTAS; i++) {
    lista_Elementos[i]=new Puerta(nula_linea);
  }
  for (int i = N_MUEBLES+N_HABITACIONES+N_PUERTAS; i<N_MUEBLES+N_HABITACIONES+N_PUERTAS+N_COLUMNAS; i++) {
    lista_Elementos[i]=new Columna(nula_rect);
  }

  lista_Elementos[0] = new Habitacion(50, 50);
  lista_Elementos[1] = new Habitacion(450, 50);
  Figura a1 = new Rectangulo(150, 250, 600, 300);
  lista_Elementos[2] = new Habitacion(a1);
  lista_Elementos[N_MUEBLES+N_HABITACIONES]=new Puerta(300, 250, true);
  lista_Elementos[N_MUEBLES+N_HABITACIONES+1]=new Puerta(600, 250, true);
  lista_Elementos[N_MUEBLES+N_HABITACIONES+2]=new Puerta(450, 100, false);
  lista_Elementos[N_MUEBLES+N_HABITACIONES+N_COLUMNAS]=new Columna(600, 100);
  Figura a2 = new Rectangulo(450, 400, 100, 100);
  lista_Elementos[N_MUEBLES+N_HABITACIONES+N_COLUMNAS+1]=new Columna(a2);
  Figura a3 = new Circulo(200, 150, 20, 20);
  lista_Elementos[N_MUEBLES+N_HABITACIONES+N_COLUMNAS+2]=new Columna(a3);
}

void draw() { //este método es llamado en cada frame
  //robot.mouseMove(frame.getBounds().x + (frame.getBounds().width - largo), frame.getBounds().y + (frame.getBounds().height - ancho));
  background(cFondo);
  for (int i = 0; i<N_ELEMENTOS; i++) {
    lista_Elementos[i].display();
  }
}

void mousePressed() { //al hacer click se crea un mueble si es posible
  //se guardan las coordenadas del ratón(se usarán en mouseDragged)
  boolean ElementoAMover=false;

  for (int i = 0; i<N_ELEMENTOS && !ElementoAMover; i++) {
    if (lista_Elementos[i].getComportamiento().seArrastra()) { //guardo la posición relativa al ratón p lo voy a mover luego
      ElementoAMover=lista_Elementos[i].estaDentro(mouseX, mouseY);
      if (ElementoAMover) {
        xRel=mouseX-lista_Elementos[i].getFigura().getX();
        yRel=mouseY-lista_Elementos[i].getFigura().getY();
        index=i;
      }
    }
  }
  if (index>0) {
    colorInterior=lista_Elementos[index].getFigura().getColorInterior();
  }
  boolean obstaculo=false;
  boolean dentroRoom=false;
  boolean puerta=false;
  boolean choque=false;
  int indiceChoque= -1;

  Elemento aux=new Mueble(mouseX, mouseY);
  //se comprueba que de crearse un mueble estaría dentro de una habitación u cualquier Elemento que permita crear Elementos dentro
  for (int i = 0; i<N_ELEMENTOS &&!dentroRoom; i++) {
    if (lista_Elementos[i].getComportamiento().ElementosDentro()) {
      dentroRoom=lista_Elementos[i].estaDentro(mouseX, mouseY);
    }
  }
  //se comprueba que no estaría dentro de otro mueble u otra Elemento que no permita crear Elementos dentro
  for (int i = 0; i<N_ELEMENTOS  &&!obstaculo; i++) {
    if (lista_Elementos[i].getComportamiento().noElementosDentro()) {
      obstaculo=lista_Elementos[i].estaDentro(mouseX, mouseY) || aux.estaDentro(lista_Elementos[i]) || lista_Elementos[i].estaDentro(aux);
    }
  }

  //si lo anterior se cumple se comprueba que no choque con otro mueble u otra habitación (u otra Elemento que choque) y vemos si está en una entrada
  if (!obstaculo && dentroRoom) {
    //boolean choque=false;
    /*for (int i = 0; i<N_ELEMENTOS &&!choque; i++) {
     if (lista_Elementos[i].getComportamiento().choca()) {
     choque=lista_Elementos[i].choca(aux) || aux.choca(lista_Elementos[i]) ;
     }
     }
     for (int i = 0; i<N_ELEMENTOS && !puerta; i++) {
     if (lista_Elementos[i].getComportamiento().esEntrada()) {
     puerta=lista_Elementos[i].choca(aux);
     }*/
    for (int i = 0; i<N_ELEMENTOS  && !choque; i++) {
      if (lista_Elementos[i].getComportamiento().choca() && lista_Elementos[i].getComportamiento().seArrastra()) {
        choque=lista_Elementos[i].choca(aux) || aux.choca(lista_Elementos[i]);
        if (choque) {
          indiceChoque=i;
        }
      }
    }
    for (int i = 0; i<N_ELEMENTOS && !choque; i++) {
      if (index!=i && lista_Elementos[i].getComportamiento().choca() ) {
        choque=lista_Elementos[i].choca(aux) || aux.choca(lista_Elementos[i]);
      }
    }
    //se comprueba si choca con alguna puerta
    for (int i = 0; i<N_ELEMENTOS && !puerta; i++) {
      if (lista_Elementos[i].getComportamiento().esEntrada()) {
        if (indiceChoque == -1) {
          puerta=lista_Elementos[i].choca(aux);
        }
      }
    }
    if (!choque || puerta) { //si no choca con nada o si choca pero está en la zona de una puerta entonces se puede crear
      //en cada click sucesivo crea, si puede, hasta N_MUEBLES y los sucesivos se sobreescribirán en los primeros
      boolean encontrado = false;
      for (int i = 0; i<N_ELEMENTOS && !encontrado; i++) {
        if (lista_Elementos[0+turno].getComportamiento().seCrea()) {
          lista_Elementos[0+turno]=aux;
          encontrado=true;
        }
        turno = (turno+1) % N_ELEMENTOS;
      }
    }
  }
}


void mouseDragged() {

  boolean mov_permitido=false; //comprueba si el movimiento no es muy brusco(necesario para no sobrepasar paredes)
  boolean choque=false;
  boolean choqueMueble=false;
  boolean choquePared=false; //por si choca con un mueble y una pared a la vez
  boolean puerta=false;

  if (index>=0) {

    color colorPulsado =color(150, 0, 0);
    lista_Elementos[index].getFigura().setColorInterior(colorPulsado);
    posX=mouseX-xRel;
    posY=mouseY-yRel;
    mov_permitido=true;

    int oldX=lista_Elementos[index].getFigura().getX();
    int oldY=lista_Elementos[index].getFigura().getY();
    int oldXLen=lista_Elementos[index].getFigura().getXLen();
    int oldYLen=lista_Elementos[index].getFigura().getYLen();

    if ((abs(posX-oldX) > 15 || abs(posY-oldY)>15)) {
      int auxX = round((posX-oldX)*0.5);
      int auxY = round((posY-oldY)*0.5);
      posX = mouseX -xRel - auxX ;
      posY = mouseY -yRel - auxY ;
      robot.mouseMove(frame.getBounds().x + (frame.getBounds().width - largo)+ posX + xRel -3, frame.getBounds().y + (frame.getBounds().height - ancho)+ posY + yRel-3);
    }

    Rectangulo a1 = new Rectangulo(posX, posY, oldXLen, oldYLen);
    if ((lista_Elementos[index].getComportamiento().choca() && (abs(posX-oldX) >= 15 || abs(posY-oldY)>=15))) {
      choque=true;
      puerta=false;
      mov_permitido=false;
    }

    lista_Elementos[index].getFigura().setX(posX);
    lista_Elementos[index].getFigura().setY(posY);
    if (mov_permitido) {
      //se comprueba si choca con alguna habitación o mueble

      for (int i = 0; i<N_ELEMENTOS  && !choqueMueble; i++) {
        if (index!=i && lista_Elementos[i].getComportamiento().choca() && lista_Elementos[i].getComportamiento().seArrastra()) {
          choqueMueble=lista_Elementos[i].choca(lista_Elementos[index]) || lista_Elementos[index].choca(lista_Elementos[i]);
          if (choqueMueble) {
            indiceChoque=i;
          }
        }
      }
      for (int i = 0; i<N_ELEMENTOS && !choquePared; i++) {
        if (index!=i && lista_Elementos[i].getComportamiento().choca() && !lista_Elementos[i].getComportamiento().seArrastra()) {
          choquePared=lista_Elementos[i].choca(lista_Elementos[index]) || lista_Elementos[index].choca(lista_Elementos[i]);
          if (choquePared) {
            //choque = true;
          }
        }
      }
      //se comprueba si choca con alguna puerta
      for (int i = 0; i<N_ELEMENTOS && !puerta; i++) { 
        if (lista_Elementos[i].getComportamiento().esEntrada()) {
          if (choquePared) {
            puerta=lista_Elementos[i].choca(lista_Elementos[index]);
          }
          /*if (puerta && choqueMultiple && indiceChoque>=0) {
           indiceChoque=-1;
           puerta=false;
           }*/
        }
      }
    }
    //se restaura la posición inicial siempre que choque y no haya una puerta
    if (choque || ((choquePared) && !puerta && !choqueMueble) || (choqueMueble)) {

      lista_Elementos[index].getFigura().setX(oldX);
      lista_Elementos[index].getFigura().setY(oldY);
      //Se fija la posición del ratón
      //Calculamos la posición (0,0) de la ventana y le sumamos a la antigua posición la pos relativa del ratón
      //los 3 se ponen para compensar errores y que vaya más fluido
      robot.mouseMove(frame.getBounds().x + (frame.getBounds().width - largo)+ oldX +xRel-3, frame.getBounds().y + (frame.getBounds().height - ancho)+ oldY + yRel-3);

      if (choquePared && !puerta) {
        choqueMueble=false;
      }
      if (choqueMueble) {
        boolean mov_permitido2=true;
        boolean choque2=false;
        boolean choqueMueble2=false;
        boolean choquePared2=false;
        boolean puerta2=false;
        int viejaX=lista_Elementos[indiceChoque].getFigura().getX();
        int viejaY=lista_Elementos[indiceChoque].getFigura().getY();
        int viejaXLen=lista_Elementos[indiceChoque].getFigura().getXLen();
        int viejaYLen=lista_Elementos[indiceChoque].getFigura().getYLen();
        int nuevaX=viejaX + (posX-oldX);
        int nuevaY=viejaY + (posY-oldY);

        Rectangulo a2 = new Rectangulo(nuevaX, nuevaY, viejaXLen, viejaYLen);
        if (lista_Elementos[indiceChoque].getComportamiento().choca() && !a2.choca(lista_Elementos[indiceChoque].getFigura())) {
          choque2=true;
          puerta2=false;
          mov_permitido2=false;
        }

        if (mov_permitido2) {
          lista_Elementos[indiceChoque].getFigura().setX(nuevaX);
          lista_Elementos[indiceChoque].getFigura().setY(nuevaY);
          for (int i = 0; i<N_ELEMENTOS && !choqueMueble2; i++) {
            if (indiceChoque!=i && indiceChoque!=index && lista_Elementos[i].getComportamiento().choca() && lista_Elementos[i].getComportamiento().seArrastra()) {
              choqueMueble2=lista_Elementos[i].choca(lista_Elementos[indiceChoque]) || lista_Elementos[indiceChoque].choca(lista_Elementos[i]);
            }
          }
          for (int i = 0; i<N_ELEMENTOS && !choquePared2; i++) {
            if (indiceChoque!=i && indiceChoque!=index && lista_Elementos[i].getComportamiento().choca() && !lista_Elementos[i].getComportamiento().seArrastra()) {
              choquePared2=lista_Elementos[i].choca(lista_Elementos[indiceChoque]) || lista_Elementos[indiceChoque].choca(lista_Elementos[i]);
            }
          }
          //se comprueba si choca con alguna puerta
          for (int i = 0; i<N_ELEMENTOS && !puerta2; i++) {
            if (lista_Elementos[i].getComportamiento().esEntrada()) {
              if (choquePared2) {
                puerta2=lista_Elementos[i].choca(lista_Elementos[indiceChoque]);
              }
            }
          }
          if (choque2 || (choquePared2 && !puerta2) || (choquePared2 && puerta2 && choqueMueble2) || choqueMueble2) {
            lista_Elementos[indiceChoque].getFigura().setX(viejaX);
            lista_Elementos[indiceChoque].getFigura().setY(viejaY);
          }
        }
      }
    }
  }
}

void mouseReleased() { //reseteamos el index que volverá a iniciarse cuando clickemos en una Elemento que se pueda mover
  if (index>0) {
    lista_Elementos[index].getFigura().setColorInterior(colorInterior);
    index=-1;
  }
  if (indiceChoque>0) {
    indiceChoque=-1;
  }
}

