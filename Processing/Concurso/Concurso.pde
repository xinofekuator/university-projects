import java.util.*;
import java.awt.Color ;
import javax.swing.JFrame;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.*;


//colores
private int cFondo;

private color verdeOscuro=color(0, 102, 0);
private color verdeClaro=color(178, 250, 102);
private color rojo=color(255, 0, 0);
private color rojoClaro=color(153, 0, 0);
private color amarillo=color(255, 255, 0);
private color amarilloClaro=color(255, 255, 51);
private color azul=color(0, 128, 255);
private color azulClaro=color(51, 153, 255);
private color violeta=color(255, 0, 255);
private color violetaClaro=color(255, 51, 255);
private color gris=color(160, 160, 160);
private color grisOscuro=color(96, 96, 96);
private color granate=color(200, 140, 140);
private color marron=color(204, 102, 0);
private color verde=color(0, 204, 0);
private color rosa=color(255, 0, 127);
private color naranja=color(255, 128, 0);
private color cyan=color(0, 255, 255);
private color verdePantano=color(0, 255, 128);

//dimensiones de la ventana
private int largo=1000;//1000
private int ancho=620;//600
private Boton pulsado=null;
private int N_TEMAS=0;//numero total de temas (se define después)
private List<String> temasSinPreguntas=new ArrayList<String>();//lista con los temas que se han quedado sin preguntas (se meten cuando se hace click en ellos)
//deben ser un numero par (preguntas+respuestas) Los numeros impares son las preguntas y los pares las respuestas
//el numero debera ser el de preguntas(la mitad del numero de imagenes que tengamos)

List<String> nombresTemas=new ArrayList<String>();

private List<Boton> botonesInicio = new ArrayList<Boton>();
private List<Boton> botonesMenu = new ArrayList<Boton>();
private List<Boton> botonesTema = new ArrayList<Boton>();
private List<Boton> botonesSolucion = new ArrayList<Boton>();
private Boton tiempo=null;

private Map<String, Tema> mapTemas=new HashMap<String, Tema>(); //la clave es el String y podemos obtener su valor (el tema) con un get

//Número máximo de temas será 16
private color[] listaTemasColores= {
  rojo, amarillo, verde, azul, naranja, violeta, cyan, granate, 
  verdeOscuro, verdePantano, violetaClaro, marron, azulClaro, verdeClaro, amarilloClaro, rojoClaro
};
private String textoError="";
private boolean continuarEnabled=false; //si no hay Json de los temas no aparecerá

private final int error=-1;
private final int inicio=0;
private final int menu=1;
private final int tema=2;
private final int solucion=3;
private int estado=0;
private int nSol=1; //guardo aqui la solucion que tengo que devolver
private Tema temaActual=null; //el tema actual en el que estoy
private int indiceTemaActual=-1; //el indice del tema en el que estoy

private long initialTime=0;
private long currentTime=0;

//para poner la pantalla de fondo en blanco en el modo presentación
void setPresentBG (int r, int g, int b) {
  ((JFrame) frame).getContentPane().setBackground(new Color(r, g, b));
}

void setup() {
  size(largo, ancho);
  setPresentBG (255, 255, 255) ;

  //DETECTAR LOS TEMAS

  String sketchPath=sketchPath("temas"); //Poner aquí dentro Temas, ya que será la carpeta donde deberán estar todos los temas
  File file = new File(sketchPath);
  String[] names = file.list();
  for (String name : names)
  {
    if (new File(sketchPath + "\\" + name).isDirectory())
    {
      //print(name);
      nombresTemas.add(name);
    }
  }

  N_TEMAS=nombresTemas.size();
  List<Tema> temas=new ArrayList<Tema>();
  for (String s:nombresTemas) {
    String imgRaiz= "temas" + "/" + s + "/" + s + "-page-";
    //print(imgRaiz+"\n");
    //Calcular el número de preguntas que hay
    File fileTema = new File(sketchPath + "\\" + s);
    FilenameFilter filtro=new FiltroPregunta(s, s + "-page-");
    String[] preguntas=fileTema.list(filtro);
    int numeroPreguntas=preguntas.length;
    if (numeroPreguntas%2 != 0) {
      print("ERROR EN EL NÚMERO DE PREGUNTAS"); //Dar el error en texto y poner la pantalla a error
      estado=error;
      textoError=textoError + "\n" + "Error en el número de preguntas y respuestas del tema " + s + ". " + "\n";
      //detectará todos los errores que encuentre;
    }
    //private Tema calculo3=new Tema(N_CALCULO3, "calculo3/ProyectoVertical_CalculoIII-page-");
    //print(numeroPreguntas);
    temas.add(new Tema(s, numeroPreguntas/2, imgRaiz));
    mapTemas.put(s, temas.get(temas.size()-1)); //lo mapea con el último elemento añadido
  }
  //mapTemas.put(StringCalculo3, calculo3);
  if (N_TEMAS<=0) {
    print("número insuficiente de temas");
    estado=error;
    textoError=textoError + "\n" + "Número insuficiente de temas."+"\n";
  }
  else if (N_TEMAS<=8) {
    int espacioOcupado=46*N_TEMAS+16*(N_TEMAS+1); //46 es la anchura de los botones y 16 el espacio de separación entre cada uno
    int posInicio=(ancho-espacioOcupado)/2;
    for (String nombreTema:nombresTemas) {
      Tema temaAux=mapTemas.get(nombreTema);
      int indice=temas.indexOf(temaAux);
      Boton botonAux=new Boton(70, posInicio+indice*(46+16), nombreTema, listaTemasColores[indice], gris, grisOscuro);
      if (nombreTema.length() >35) {
        botonAux.setTamTexto(18-(nombreTema.length()/12)); //por cada 12 caraceteres se disminuye en uno la fuente
      }
      botonesMenu.add(botonAux);
    }
  }
  else if (N_TEMAS<=16) {
    int numeroTemasColumna2=N_TEMAS/2;
    int numeroTemasColumna1=N_TEMAS-numeroTemasColumna2;
    int anchoColumna1=70;
    int anchoColumna2=70*2+200; //cada botón tiene 200 de largo y 70 de separación horizontal
    for (String nombreTema:nombresTemas) {
      Tema temaAux=mapTemas.get(nombreTema);
      int indice=temas.indexOf(temaAux);
      Boton botonAux=null;
      if (indice<=numeroTemasColumna1-1) {
        int espacioOcupado=46*numeroTemasColumna1+16*(numeroTemasColumna1+1); //siempre se hará la fórmula anterior para el caso de 8 temas
        int posInicio=(ancho-espacioOcupado)/2;
        botonAux=new Boton(anchoColumna1, posInicio+indice*(46+16), nombreTema, listaTemasColores[indice], gris, grisOscuro);
        botonAux.cambiarTam(46, 250);
        if (nombreTema.length() >20) {
          botonAux.setTamTexto(18-(nombreTema.length()/12)); //por cada 12 caraceteres se disminuye en uno la fuente
        }
        botonesMenu.add(botonAux);
      }
      else {
        int espacioOcupado=46*numeroTemasColumna2+16*(numeroTemasColumna2+1); //siempre se hará la fórmula anterior para el caso de 8 temas
        int posInicio=(ancho-espacioOcupado)/2;
        botonAux=new Boton(anchoColumna2, posInicio+(indice-numeroTemasColumna1)*(46+16), nombreTema, listaTemasColores[indice], gris, grisOscuro);
        botonAux.cambiarTam(46, 250);
        if (nombreTema.length() >23) {
          botonAux.setTamTexto(18-(nombreTema.length()/12)); //por cada 12 caraceteres se disminuye en uno la fuente
        }
        botonesMenu.add(botonAux);
      }
    }
  }
  else {
    print("El número de temas máximo es 16");
    estado=error;
    textoError=textoError + "\n" + "El número máximo de temas es 16."+ "\n";
  }
  //Cargo los datos previos
  for (String nombreTema : nombresTemas)
  {
    try {
      JSONObject jsonTema = loadJSONObject("data/"+ nombreTema +".json");
      Tema temaAux=mapTemas.get(nombreTema);
      temaAux.setJSON(jsonTema);
      //Si llega hasta aquí ha cargado correctamente un tema
      continuarEnabled=true;
    }
    catch (Exception e) {
      print("ERROR");
    }
  }
  inicia();
}

void inicia() {
  cFondo=255;
  //Cambio el tamaño del tema de calculo3
  //calculo3.setX(-20);
  //calculo3.setY(-150);
  //calculo3.setEscalar(1.4);

  Boton ini_1=new Boton(525+175, 50+45*8+15*8, "Empezar", gris, grisOscuro, grisOscuro);
  ini_1.cambiarTam(45, 180);
  ini_1.setTamTexto(24);
  botonesInicio.add(ini_1);

  Boton continuar=new Boton(525+175, 50+45*7+15*7, "Continuar", gris, grisOscuro, grisOscuro);
  continuar.cambiarTam(45, 180);
  continuar.setTamTexto(24);
  if (continuarEnabled) {
    botonesInicio.add(continuar);
  }
  Boton escoger_tema=new Boton(470+175, 50+45*8+15*8, "Escoger tema", gris, grisOscuro, grisOscuro);
  escoger_tema.cambiarTam(45, 250);
  escoger_tema.setTamTexto(24);
  botonesMenu.add(escoger_tema);

  Boton deshacer=new Boton(890-60, 50, "<-", gris, grisOscuro, grisOscuro);
  deshacer.cambiarTam(45, 60);
  deshacer.setTamTexto(24);
  botonesMenu.add(deshacer);

  //temaAleatorio=new Boton(800, 300, "", marron, gris, marron); //el cClick se usara como color de retorno
  //Boton solucion=new Boton(600, 50+45*8+15*8, "Solución", gris, grisOscuro, grisOscuro); VALOR ANTIGUO ABAJO
  Boton solucion=new Boton(820, 250, "Solución", gris, grisOscuro, grisOscuro);
  solucion.cambiarTam(45, 150);
  solucion.setTamTexto(24);
  botonesTema.add(solucion);

  //tiempo=new Boton(200, 50+45*8+15*8, "0", gris, grisOscuro, grisOscuro); VALOR ANTIGUO ABAJO
  tiempo=new Boton(820,350, "0", gris, grisOscuro, grisOscuro);
  tiempo.cambiarTam(45, 150);
  tiempo.setTamTexto(24);
  tiempo.setTipo(1); //tipo 1 para saber que al pulsar reinicio el contador
  botonesTema.add(tiempo);

  Boton volver=new Boton(820, 250, "Volver", gris, grisOscuro, grisOscuro);
  volver.cambiarTam(45, 150);
  volver.setTamTexto(24);
  botonesSolucion.add(volver);
}

void draw() {
  frameRate(60);
  background(cFondo);
  switch(estado) {
  case error:
    displayError();
    break;
  case inicio:
    displayInicio();
    break;
  case menu:
    displayMenu();
    break;
  case tema:
    displayTema();
    break;
  case solucion:
    displaySolucion();
    break;
  default:
    //error
    println("Error interno al pasar de una pantalla a otra.");
    exit();
    break;
  }
}

void mousePressed() {
  switch(estado) {    
  case inicio:
    actionsInicio();
    break;
  case menu:
    actionsMenu();
    break;
  case tema:
    actionsTema();
    break;
  case solucion:
    actionsSolucion();
    break;
  default:
    //error
    println("Salida de la aplicación tras un error."); //Si se está en la pantalla de error y se hace click se sale de la aplicación.
    exit();
    break;
  }
}

void displayError() {
  fill(0);
  rect(0, 0, largo, ancho);
  fill(255);
  textSize(26);
  text(textoError, largo/4, ancho/6, 2*largo/4, 5*ancho/6);
}

void displayInicio() {
  //imagen de fondo
  PImage appLogo = loadImage("/imagenes/contestMakerLogo.jpg");
  image(appLogo, 50, 220, appLogo.width*0.75, appLogo.height*0.75);
  //PImage img = loadImage("/imagenes/logoUPMTuneado.jpg");
  //image(img, 680, 200, img.width/3, img.height/3);
  for (Boton b : botonesInicio) {
    color interior=b.getColor();
    color encima=b.getColorEncima();
    if (b.estaDentro(mouseX, mouseY)) {
      if (b.getTipo()!=-1) {
        b.setColor(encima); //color al pasar el ratón por encima
      }
    }
    b.display();
    if (b.getTipo()!=-1) {
      b.setColor(interior); //devuelve el color natural al no estar el ratón encima
    }
  }
}

void displayMenu() {
  PImage img = loadImage("/imagenes/logoUPM.jpg");
  image(img, 680, 200, img.width/3, img.height/3);
  //zona de dibujo
  //fill(20);
  //rect(20, 25, 500, 570);
  //temaAleatorio.display();
  for (Boton b : botonesMenu) {
    b.display(); //para resetear el clickState si se había clickado
    color interior=b.getColor();
    color encima=b.getColorEncima();
    if (b.estaDentro(mouseX, mouseY)) {
      if (b.getTipo()==0) {
        b.setColor(encima); //color más claro al pasar el ratón por encima
      }
    }
    b.display();
    if (b.getTipo()==0) {
      b.setColor(interior); //devuelve el color natural al no estar el ratón encima
    }
  }
}


void displayTema() {
  //primero desocultamos todos los botones que podrían estar ocultos
  for (Boton b1 : botonesMenu) {
    b1.desocultarTexto();
  }

  temaActual.display(nSol);

  currentTime=System.currentTimeMillis();
  int segundos=(int)Math.round((currentTime-initialTime)/1000.0);
  for (Boton b : botonesTema) {
    b.display(); //para resetear el clickState si se había clickado
    color interior=b.getColor();
    color encima=b.getColorEncima();
    if (b.estaDentro(mouseX, mouseY)) {
      if (b.getTipo()!=-1) {
        b.setColor(encima); //color más claro al pasar el ratón por encima
      }
    }
    b.display();
    if (b.getTipo()!=-1) {
      b.setColor(interior); //devuelve el color natural al no estar el ratón encima
    }
  }

  if (segundos <= 60) {
    tiempo.setTexto("Tiempo: " + String.valueOf(60-segundos));
    tiempo.display();

    if (segundos > 50) {
      tiempo.setColorTexto(color(255, 0, 0));
      tiempo.display();
      tiempo.setColorTexto(color(0, 0, 0));
    }
  }
  else {
    color aux=tiempo.getColor();
    tiempo.setColor(color(250, 200, 200));
    tiempo.display();
    tiempo.setColor(aux);
  }
}

void displaySolucion() {
  temaActual.displaySol(nSol);
  for (Boton b : botonesSolucion) {
    b.display(); //para resetear el clickState si se había clickado
    color interior=b.getColor();
    color encima=b.getColorEncima();
    if (b.estaDentro(mouseX, mouseY)) {
      if (b.getTipo()!=-1) {
        b.setColor(color(encima)); //color más claro al pasar el ratón por encima
      }
    }
    b.display();
    if (b.getTipo()!=-1) {
      b.setColor(color(interior)); //devuelve el color natural al no estar el ratón encima
    }
  }
}

void actionsInicio() {
  for (Boton b : botonesInicio) {
    if (b.estaDentro(mouseX, mouseY)) {
      String texto=b.getTexto();
      if (texto=="Empezar") {
        String dataAdress=sketchPath("data");   
        File backUpDirectory = new File(dataAdress);
        File[] backUpFiles = backUpDirectory.listFiles();
        for (File backUpFile : backUpFiles)
        {
          if (backUpFile.delete())
            print("El fichero ha sido borrado satisfactoriamente");
          else
            System.out.println("El fichero no puede ser borrado");
        }
        b.clickar();
        frameRate(5);
        estado=1;
        b.display();
      }
      else if (texto=="Continuar") {
        b.clickar();
        frameRate(5);
        estado=1;
        b.display();
      }
      else {
        print("Se ha pulsado un botón inesperado.");
      }
    }
  }
}
void actionsMenu() {
  for (Boton b : botonesMenu) {
    if (b.estaDentro(mouseX, mouseY)) {
      String texto=b.getTexto();
      if (texto=="<-") {
        for (Boton b1 : botonesMenu) {
          b1.desocultarTexto();
        }
      }
      else if (texto=="Escoger tema") {
        Random rand=new Random();
        boolean temaNoRepetido=false; //no puede repetir el mismo tema que se había cogido
        boolean temaConPreguntas=false;
        while (!temaNoRepetido || !temaConPreguntas) {
          int num=rand.nextInt(N_TEMAS); //devuelve un numero de 0 a N_TEMAS-1 (incluido)
          if (num!=indiceTemaActual) {
            temaNoRepetido=true;
            if (!temasSinPreguntas.contains(nombresTemas.get(num))) {
              temaConPreguntas=true;
              indiceTemaActual=num;
            }
          }
        }
        //temaAleatorio.setTexto(listaTemas[indiceTemaActual]); //indiceTemaActual empieza en 1, por lo que hay que restarle uno para evitar que se salga de rango
        //temaAleatorio.setColor(listaTemasColores[indiceTemaActual]);
        b.clickar();
        frameRate(5);
        b.display();
        //temaAleatorio.display();
        for (Boton b1 : botonesMenu) {
          if ((b1.getTexto() != nombresTemas.get(indiceTemaActual)) && (b1.getTexto() != "Escoger tema") && (b1.getTexto() != "<-")) {
            b1.ocultarTexto();
          }
          else {
            b1.desocultarTexto(); //lo hago una vez innecesariamente con el boton de escoger menu
          }
        }
      }
      else {
        temaActual=mapTemas.get(texto);
        if (b.getTipo()==0 && temaActual.getN()>0 && b.getTextoAuxiliar()!="") {
          nSol=temaActual.getRandomNumber();
          if (nSol>0) {
            b.clickar();
            frameRate(5);
            estado=2;
            b.display();
            initialTime=System.currentTimeMillis();
          }
          else {
            b.setColor(color(250, 200, 200));
            b.setTipo(1);
          }
          //temaAleatorio.setTexto(""); //reseteamos el selector de temas
          //color click=temaAleatorio.getColorClick();
          //temaAleatorio.setColor(click); //volvemos al color inicial (está guardado en cClick)
        }
        else { //ya no quedan más preguntas
          if (b.getTextoAuxiliar()!="") {
            b.setColor(color(0, 0, 0)); //pone de color negro las que no tienen más preguntas
            temasSinPreguntas.add(temaActual.getNombre());
            b.setTipo(1);
          }
        }
      }
    }
  }
}

void actionsTema() {
  for (Boton b : botonesTema) {
    if (b.estaDentro(mouseX, mouseY)) {
      if (b.getTipo()==0) {
        b.clickar();
        frameRate(5);
        estado=3;
        b.display();
      }
      else {
        initialTime=System.currentTimeMillis();
        currentTime=System.currentTimeMillis();
      }
    }
  }
}

void actionsSolucion() {
  for (Boton b : botonesSolucion) {
    if (b.estaDentro(mouseX, mouseY)) {
      if (b.getTipo()==0) {
        b.clickar();
        frameRate(5);
        estado=1;
        b.display();
      }
    }
  }
}

