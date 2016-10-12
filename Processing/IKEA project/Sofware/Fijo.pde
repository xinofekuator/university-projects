class Fijo extends Comportamiento {
  
  boolean seCrea() {
    return false;
  }
  boolean seArrastra() {
    return false;
  }
  boolean esEntrada() {
    return false;
  }
  boolean choca() {
    return true;
  }
  boolean ElementosDentro() {
    return true;
  }
  boolean noElementosDentro() {
    return false;
  }
}

