class Elemento { //los muebles, las habitaciones y las puertas implementan esta interfaz
  //HACER CLASE PADRE
  private Figura f;
  private Comportamiento s;
  Elemento() {
    this.f = new Figura();
    this.s = new Comportamiento();
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

