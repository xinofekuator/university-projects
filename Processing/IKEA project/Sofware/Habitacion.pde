//No puede haber habitaciones dentro de habitaciones

class Habitacion extends Elemento {
  private Figura f;
  private Comportamiento s;
  Habitacion(int x, int y) {
    color cInterior=color(242,228,117);
    color cBorde=color(0, 0, 0);
    this.f=new Rectangulo(x, y, 400, 200);
    this.s=new Fijo();
    this.f.setColorBorde(cBorde);
    this.f.setColorInterior(cInterior);
  }
  Habitacion(Figura f1) {
    color cInterior=color(242,228,117);
    color cBorde=color(0, 0, 0);
    this.f=f1;
    this.s=new Fijo();
    this.f.setColorBorde(cBorde);
    this.f.setColorInterior(cInterior);
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

