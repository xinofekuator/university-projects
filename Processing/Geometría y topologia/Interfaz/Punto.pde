//Clase punto usada sólo para obtener las coordenadas y para la parte gráfica
//En las triangulaciones se usa la clase Vertice (está en el DCEL)

class Punto {
  private color cInterior;
  private int x, y;
  private int grosor;

  Punto(int x, int y) {
    this.x=x;
    this.y=y;
    this.cInterior=0;
    this.grosor=3;
  }

  void display() {
    fill(cInterior);
    stroke(cInterior);
    ellipse(this.x, this.y, grosor, grosor);
  }

  public int getX() {
    return this.x;
  }
  public int getY() {
    return this.y;
  }

  public void setX(int x) {
    this.x=x;
  }
  public void setY(int y) {
    this.y=y;
  }

  void setColor(int cInterior) {
    this.cInterior=cInterior;
  }

  int getGrosor() {
    return this.grosor;
  }

  void setGrosor(int grosor) {
    this.grosor=grosor;
  }
}

