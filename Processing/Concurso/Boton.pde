//código para los bordes redondeados
void roundedRect(float x, float y, float w, float h, float rx, float ry)
{
  beginShape();
  vertex(x, y+ry); //top of left side 
  bezierVertex(x, y, x, y, x+rx, y); //top left corner

  vertex(x+w-rx, y); //right of top side 
  bezierVertex(x+w, y, x+w, y, x+w, y+ry); //top right corner

  vertex(x+w, y+h-ry); //bottom of right side
  bezierVertex(x+w, y+h, x+w, y+h, x+w-rx, y+h); //bottom right corner

  vertex(x+rx, y+h); //left of bottom side
  bezierVertex(x, y+h, x, y+h, x, y+h-ry); //bottom left corner

  endShape(CLOSE);
} 

class Boton {
  private int tamTexto;
  private int tipo; //0 de clikar y pulsar, 1 se mantiene pulsado
  private color cInterior, cTexto, cEncima, cClick;
  private int x, y, ancho, alto;
  private String texto;
  private int clickState; //indica si se ha hecho click para devolver el color inicial (0 si no se ha clikado, 1 si se ha clikado y 2 si hay que devolverle los colores iniciales)
  private boolean oculto;
  private String textoAuxiliar; //para guardar el texto cuando lo oculto y poder recuperarlo luego

  Boton(int x, int y, String texto) {
    this.x=x;
    this.y=y;
    this.alto=46;
    this.ancho=400;
    this.cInterior=color(110, 170, 250);
    this.cEncima=color(150, 220, 250);
    this.cClick=color(200, 140, 140);
    this.cTexto=color(0, 0, 0);
    this.texto=texto;
    this.tamTexto=18;
    this.tipo=0;
    this.clickState=0;
    this.oculto=false;
    this.textoAuxiliar=texto;
  }

  Boton(int x, int y, String texto, color cInterior, color cEncima, color cClick) {
    this.x=x;
    this.y=y;
    this.alto=45;
    this.ancho=400;
    this.cInterior=cInterior;
    this.cEncima=cEncima;
    this.cClick=cClick;
    this.cTexto=color(0, 0, 0);
    this.texto=texto;
    this.tamTexto=18;
    this.tipo=0;
    this.clickState=0;
    this.oculto=false;
    this.textoAuxiliar=texto;//el que dibuja (a veces puede ser nulo)
  }

  void display() {
    if (clickState==2) { //ya se ha clickado y se ha hecho un display (se vuelve por tanto a los valores de color iniciales)
      color aux=this.cInterior;
      this.cInterior=this.cClick;
      this.cClick=aux;
      this.clickState=0;
    }
    if (clickState==1) { //sólo se suma cuando se ha clikado
      clickState++;
    }
    if (!oculto) {
      this.textoAuxiliar=this.texto;
    }
    fill(this.cInterior);
    //stroke(cInterior);
    //rect(this.x, this.y, this.ancho, this.alto); //para un rectangulo normal
    roundedRect(this.x, this.y, this.ancho, this.alto, 10, 10);
    if (this.textoAuxiliar!=null) {
      textSize(tamTexto);
      textAlign(CENTER, CENTER);
      fill(cTexto);
      text(this.textoAuxiliar, this.x, this.y, this.ancho, this.alto);
    }
  }

  int getX() {
    return this.x;
  }
  int getY() {
    return this.y;
  }

  String getTexto() {
    return this.texto;
  }

  String getTextoAuxiliar() {
    return this.textoAuxiliar;
  }

  void setTexto(String texto) {
    this.texto=texto;
  }

  color getColor() {
    return this.cInterior;
  }

  void setColor(int cInterior) {
    this.cInterior=cInterior;
  }

  void setColorEncima(int cEncima) {
    this.cEncima=cEncima;
  }

  color getColorEncima() {
    return this.cEncima;
  }

  void setColorClick(int cEncima) {
    this.cClick=cClick;
  }

  color getColorClick() {
    return this.cClick;
  }

  void setTamTexto(int tamTexto) {
    this.tamTexto=tamTexto;
  }

  void setColorTexto(color cTexto) {
    this.cTexto=cTexto;
  }

  int getTipo() {
    return this.tipo;
  }

  void setTipo(int tipo) {
    this.tipo=tipo;
  }

  void cambiarTam(int alto, int ancho) {
    this.alto=alto;
    this.ancho=ancho;
  }

  boolean estaDentro(int x, int y) {
    boolean resultado=false;
    if (x>this.x && x<this.x+this.ancho && y>this.y && y<this.y+this.alto) {
      resultado=true;
    }
    return resultado;
  }

  void clickar() {
    color aux=this.cInterior;
    this.cInterior=this.cClick;
    this.cClick=aux;
    this.clickState=1;
  }

  void ocultarTexto() {
    this.oculto=true;
    this.textoAuxiliar="";
  }

  void desocultarTexto() {
    this.oculto=false;
  }
}

