class  Figura { //los muebles, las habitaciones y las puertas implementan esta interfaz
  //toda Figura incluirá su tamaño y sus colores, pero no su situación
  //una Figura se puede dibujar muchas veces por distintas Elementos y su situación la proporcionará la Elemento
  private color cInterior;
  private color cBorde;
  private int x, y, xLen, yLen;
  Figura() {
    this.x=0;
    this.y=0;
    this.xLen=0;
    this.yLen=0;
  }
  void display() {   //dibuja el mueble en la posición dada
    stroke(cBorde);
    fill(cInterior);
    rect(this.x, this.y, this.xLen, this.yLen);
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
    //comprobamos a continuación que no tenga todos los vértices fuera pero lo atraviese(se puede dar al crear muebles no al moverlos)
    if ((this.x<=xFigura && xFigura<=this.x+this.xLen) 
      && (this.x<=xFigura+xLenFigura && xFigura+xLenFigura<=this.x+this.xLen) 
      && (yFigura<=this.y && this.y<=yFigura+yLenFigura) 
      && (yFigura<=this.y+this.yLen && this.y+this.yLen<=yFigura+yLenFigura)) {
      resultado=true;
    }
    return resultado;
  }
}

