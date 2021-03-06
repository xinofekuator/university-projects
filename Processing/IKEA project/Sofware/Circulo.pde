//No tendrá origen, serán rectángulos genéricos
//De este modo separamos el color y el tamaño del comportamiento del mueble

class Circulo extends Figura {
  private color cInterior;
  private color cBorde;
  private int x, y, xLen, yLen;

  Circulo(int x, int y, int xLen, int yLen) {
    this.x=x;
    this.y=y;
    this.xLen=xLen;
    this.yLen=yLen;
  }
  void display() {   //dibuja el mueble en la posición dada
    stroke(cBorde);
    fill(cInterior);
    ellipse(this.x+(this.xLen/2), this.y+(this.yLen/2), this.xLen, this.yLen);
  }

  int getX() {
    return this.x;
  }
  int getY() {
    return this.y;
  }
  void setX(int x) {
    this.x=x;
  }
  void setY(int y) {
    this.y=y;
  }
  int getXLen() {
    return this.xLen;
  }
  int getYLen() {
    return this.yLen;
  }
  void setXLen(int xLen) {
    this.xLen=xLen;
  }
  void setYLen(int yLen) {
    this.yLen=yLen;
  }
  color getColorBorde() {
    return this.cBorde;
  }
  color getColorInterior() {
    return this.cInterior;
  }
  void setColorBorde(color borde) {
    this.cBorde=borde;
  }
  void setColorInterior(color interior) {
    this.cInterior=interior;
  }

  boolean estaDentro(int x1, int y1) { //comprueba si el punto (x,y) está dentro del rectángulo
    boolean resultado=false;
    //poner mayor igual y menor igual
    if (x1>=this.x && x1<=(this.x+this.xLen) && y1>=this.y && y1<=(this.y+this.yLen)) {
      resultado=true;
    }
    return resultado;
  }

  boolean estaDentro(Figura f) {
    boolean v1, v2, v3, v4, resultado;
    int xFigura, yFigura, xLenFigura, yLenFigura;
    resultado=false;
    xFigura=f.getX();
    yFigura=f.getY();
    xLenFigura=f.getXLen();
    yLenFigura=f.getYLen();

    v1=this.estaDentro(xFigura, yFigura);
    v2=this.estaDentro(xFigura, yFigura + yLenFigura);
    v3=this.estaDentro(xFigura + xLenFigura, yFigura);
    v4=this.estaDentro(xFigura + xLenFigura, yFigura + yLenFigura);
    if (v1&&v2&&v3&&v4) {
      resultado=true;
    }
    return resultado;
  }


  boolean choca(Figura f) { //comprueba si la Elemento f choca con el rectángulo
    //primero vemos si los vértices de nuestro rectángulo está todos fuera o dentro de la Elemento que va a chocar.
    //en caso de que no sea así habrá choque
    //si la Elemento f es más pequeña que el rectángulo puede haber choque y que se devuelva false
    //para solucionar lo anterior cuando se quiera comprobar un choque habrá que llamar a los de ambas Elementos y realizar una doble comprobación
    boolean v1, v2, v3, v4, resultado;
    int xFigura, yFigura, xLenFigura, yLenFigura;
    resultado=true;
    xFigura=f.getX();
    yFigura=f.getY();
    xLenFigura=f.getXLen();
    yLenFigura=f.getYLen();

    v1=this.estaDentro(xFigura, yFigura);
    v2=this.estaDentro(xFigura, yFigura + yLenFigura);
    v3=this.estaDentro(xFigura + xLenFigura, yFigura);
    v4=this.estaDentro(xFigura + xLenFigura, yFigura + yLenFigura);
    if ((v1&&v2&&v3&&v4) || (!v1&&!v2&&!v3&&!v4)) {
      resultado=false;
    }
    return resultado;
  }
}

