class Entrada extends Comportamiento { //no se arrastra, ni choca, ni permite crear cosas dentro, pero anula otros objetos para que las Elementos lo atraviesen (es una entrada)

  boolean seCrea() {
    return false;
  }
  boolean seArrastra() {
    return false;
  }
  boolean esEntrada() {
    return true;
  }
  boolean choca() {
    return false;
  }
  boolean ElementosDentro() {
    return false;
  }
  boolean noElementosDentro() {
    return false;
  }
}

