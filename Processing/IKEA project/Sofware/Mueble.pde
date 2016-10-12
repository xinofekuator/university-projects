class Mueble extends Elemento { 
  private Figura f;
  private Comportamiento s;
  Mueble(int x, int y) {
    color cInterior=color(51,0,0);
    color cBorde=color(250, 250, 0);
    this.f=new Rectangulo(x, y, (int)random(30, 80), (int)random(30, 80));
    this.s=new Solido();
    this.f.setColorBorde(cBorde);
    this.f.setColorInterior(cInterior);
  }
  Mueble(Figura f1) {
    color cInterior=color(51,0,0);
    color cBorde=color(250, 250, 0);
    this.f=f1;
    this.s=new Solido();
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

