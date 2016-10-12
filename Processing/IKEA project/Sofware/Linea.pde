class Linea extends Figura { //sólo contempla líneas horizontales o verticales

  private color cInterior;
  private color cBorde;
  private int longitud;
  private int x, y, xLen, yLen; //xLen=0 si es vertical y yLen=0 si es horizontal
  Linea(int x, int y, int xLen, int yLen) {
    this.x=x;
    this.y=y;
    this.xLen=xLen;
    this.yLen=yLen;
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
  void display() {   //dibuja la línea en la posicion dada
    stroke(cBorde);
    if (yLen==0) { //horizontal
      line(this.x, this.y, this.x+this.xLen, this.y);
    }
    if (xLen==0) {//vertical
      line(this.x, this.y, this.x, this.y+this.yLen);
    }
  }
  boolean estaDentro(int x1, int y1) {//comprueba si el punto (x,y) está dentro de la franja creada por la línea 
    boolean resultado=false;
    if (yLen==0 && x1>=this.x && x1<=this.x+this.xLen) {
      resultado=true;
    }
    else if (xLen==0 && y1>=this.y && y1<=this.y+this.yLen) {
      resultado=true;
    }
    else { //si la recta no es horizontal o vetical dará false
      resultado=false;
    }
    return resultado;
  }

  boolean estaDentro(Figura f) {
    return this.choca(f);
  }
  boolean choca(Figura f) { //comprueba si el rectángulo que se le pasa choca con la puerta
    //los datos que se le pasan son del objeto que quiere ver si se choca
    //chocará cuando esté en el rango de valores que varían (nuestras puertas son horizontales o verticales, por lo que
    //sólo variará o el eje x o el eje y) y cuando el eje fijo esté en medio de dos de los vértices del rectángulo.
    int x1, y1, xLen1, yLen1;
    boolean v1, v2, v3, v4, res;
    res=false;
    x1=f.getX();
    y1=f.getY();
    xLen1=f.getXLen();
    yLen1=f.getYLen();
    v1=this.estaDentro(x1, y1);
    v2=this.estaDentro(x1, y1+yLen1);
    v3=this.estaDentro(x1+xLen1, y1);
    v4=this.estaDentro(x1+xLen1, y1+yLen1);
    if ((v1&&v2&&v3&&v4)) { //si todos los puntos están en la franja de la línea
      if (this.yLen==0) {//recta horizontal
        if ((y1-this.y)*((y1+yLen1)-this.y) < 0) {
          res=true;
        }
      }
      else if (this.xLen==0) {//recta vertical
        if ((x1-this.x)*((x1+xLen1)-this.x) < 0) {
          res=true;
        }
      }

      else {
        res=false;
      }
    }
    return res;
  }
}

