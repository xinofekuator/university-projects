//Clase sólo usada a la hora de realizar la representación gráfica y para guardar el cierre convexo
//Para las operaciones de triangulación se usa la clase SemiArista (ver DCEL)

class Arista {
  //sentido positivo(antihorario) al dibujarse en processing
  private Punto a;
  private Punto b;
  
  Arista(Punto a,Punto b){
    this.a=a;
    this.b=b;
  }
  
  public void display(){
    line(this.a.getX(),this.a.getY(),this.b.getX(),this.b.getY());
  }
  
  public Punto getA(){
    return this.a;
  }
  
  public Punto getB(){
    return this.b;
  }
}

