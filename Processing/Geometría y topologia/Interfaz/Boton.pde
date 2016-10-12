//Clase que define los botones usados en la interfaz

class Boton {
  private int tamTexto;
  private int tipo; //0 de clikar y pulsar, 1 se mantiene pulsado para numero de puntos, 2 se mantiene pulsado para el tipo de triangulación
  private color cInterior;
  private int x, y, ancho, alto;
  private String texto;

  Boton(int x, int y, String texto) {
    this.x=x;
    this.y=y;
    this.alto=80;
    this.ancho=150;
    this.cInterior=color(110, 170, 250);
    this.texto=texto;
    this.tamTexto=24;
    this.tipo=0;
  }

  void display() {
    fill(cInterior);
    //stroke(cInterior);
    rect(this.x, this.y, this.ancho, this.alto);
    if (texto!=null) {
      textSize(tamTexto);
      textAlign(CENTER,CENTER);
      fill(0);
      text(this.texto, this.x, this.y, this.ancho, this.alto);
    }
  }

  public int getX() {
    return this.x;
  }
  public int getY() {
    return this.y;
  }
  
  public String getTexto(){
    return this.texto;
  }
  
  public void setTexto(String texto){
    this.texto=texto;
  }

  void setColor(int cInterior) {
    this.cInterior=cInterior;
  }
  
  void setTamTexto(int tamTexto){
    this.tamTexto=tamTexto;
  }
  
  int getTipo(){
    return this.tipo;
  }
  
  void setTipo(int tipo){
    this.tipo=tipo;  
  }
  
  void cambiarTam(int alto,int ancho){
    this.alto=alto;
    this.ancho=ancho;
  }
  
  boolean estaDentro(int x,int y){
    boolean resultado=false;
    if(x>this.x && x<this.x+this.ancho && y>this.y && y<this.y+this.alto){
      resultado=true;
    }
    return resultado;
  }
}

