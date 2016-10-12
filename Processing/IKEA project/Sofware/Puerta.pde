//serán líneas horizontales o verticales
class Puerta extends Elemento {
  private Linea f;
  private Comportamiento s;
  Puerta(int x, int y, boolean posicion) {
    color cBorde=color(169, 218, 0);
    if (posicion) { //horizontal
      this.f = new Linea(x, y, 80, 0);
      this.f.setColorBorde(cBorde);
    }
    else {//vertical
      this.f = new Linea(x, y, 0, 80);
      this.f.setColorBorde(cBorde);
    }
    this.s=new Entrada();
  }
  Puerta(Linea f1) {
    color cBorde=color(169, 218, 0);
    this.f=f1;
    this.s=new Entrada();
    this.f.setColorBorde(cBorde);
  }
  Figura getFigura() {
    return this.f;
  }
  Comportamiento getComportamiento() {
    return this.s;
  }
  void display() {   //dibuja la habitación
    this.f.display();
  }
  boolean estaDentro(int x1, int y1) { //comprueba si el punto (x1,y1) está dentro del mueble
    return this.f.estaDentro(x1, y1);
  }
  boolean estaDentro(Elemento f1) { //comprueba si el punto (x1,y1) está dentro del mueble
    return this.f.estaDentro(f1.getFigura());
  }
  boolean choca(Elemento f1) { //comprueba si la Elemento que se le pasa choca con el mueble
    return this.f.choca(f1.getFigura());
  }
}

