class Solido extends Comportamiento { //se puede arrastrar por la pantalla, choca con Elementos y no permite crear nada dentro
  boolean seCrea() {
    return true;
  }
  boolean seArrastra() {
    return true;
  }
  boolean esEntrada() {
    return false;
  }
  boolean choca() {
    return true;
  }
  boolean ElementosDentro() {
    return false;
  }
  boolean noElementosDentro() {
    return true;
  }
}

