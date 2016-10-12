class Tema {

  private int n; //van de 0 a n-1 y son los identificadores de cada transparencia de beamer
  private String nombre, imgRaiz;
  private Map<Integer, Boolean> imgLista;
  private float xOrigen;
  private float yOrigen;
  private float escalar;
  JSONObject json;

  Tema(String nombre, int n, String imgRaiz) {
    this.n=n;
    this.nombre=nombre;
    this.imgRaiz=imgRaiz;
    imgLista = new HashMap<Integer, Boolean>();
    for (int i=0;i<n;i++) {
      imgLista.put(new Integer(i), true);
    }
    this.xOrigen=0;
    this.yOrigen=0;
    this.escalar=1.8;
  }

  public void setX(float x) {
    this.xOrigen=x;
  }
  public void setY(float y) {
    this.yOrigen=y;
  }
  public void setEscalar(float escalar) {
    this.escalar=escalar;
  }

  public void setJSON(JSONObject json) {
    this.json=json;
  }

  public int getRandomNumber() {
    //Se actualiza la imgLista con el Json (es algo redundante,ya que sólo es útil en la primera pasada)
    try {
      json=loadJSONObject("data/" + nombre + ".json");
      JSONArray values = json.getJSONArray(imgRaiz);
      for (Integer i:imgLista.keySet()) {
        JSONObject pregunta=values.getJSONObject(i);
        int id = pregunta.getInt("id");
        Boolean estado = pregunta.getBoolean("estado");
        imgLista.put(new Integer(id), estado);
      }
    }
    catch(Exception e) {
      print(e);
      print("ERROR CARGANDO EL JSON\n");
    }

    Random rand=new Random();
    int num=-1; 
    boolean terminado=true;
    for (int i=0;i<n;i++) {
      if (imgLista.get(new Integer(i))==true) {
        terminado=false;
      }
    }

    if (!terminado) {
      num=rand.nextInt(n);
      Integer aux=new Integer(num);
      while (imgLista.get (aux)!=true) {
        num=rand.nextInt(n);
        aux=new Integer(num);
      }

      //print("Numero: ");
      //print(num);

      imgLista.put(aux, new Boolean(false));
    }

    JSONArray values = new JSONArray();

    for (Integer i:imgLista.keySet()) {
      JSONObject pregunta = new JSONObject();
      pregunta.setInt("id", i);
      pregunta.setBoolean("estado", imgLista.get(i));
      values.setJSONObject(i, pregunta);
    }

    json = new JSONObject();
    json.setJSONArray(imgRaiz, values);

    saveJSONObject(json, "data/"+ nombre +".json");

    return num+1; //se suma 1 para que no salga -1 en la fórmula del display cuando num=0
  }

  public void display(int num) {
    PImage img;
    String aux=imgRaiz;
    int numImg=num*2 -1; //numero de la imagen
    if (numImg<100 && numImg>=10) {
      aux=aux.concat("0");
    }
    if (numImg<10) {
      aux=aux.concat("0");
      aux=aux.concat("0");
    }
    aux=aux.concat(Integer.toString(numImg));
    aux=aux.concat(".jpg");
    img = loadImage(aux);
    image(img, xOrigen, yOrigen, img.width/escalar, img.height/escalar);
  }

  public void displaySol(int num) {
    PImage img;
    String aux=imgRaiz;
    int numImg=num*2; //numero de la imagen
    if (numImg<100 && numImg>=10) {
      aux=aux.concat("0");
    }
    if (numImg<10) {
      aux=aux.concat("0");
      aux=aux.concat("0");
    }
    aux=aux.concat(Integer.toString(numImg));
    aux=aux.concat(".jpg");
    img = loadImage(aux);
    image(img, xOrigen, yOrigen, img.width/escalar, img.height/escalar);
    //image(img, 0, 0);
  }

  public int getN() {
    return this.n;
  }
  public String getImgRaiz() {
    return this.imgRaiz;
  }
  public String getNombre() {
    return this.nombre;
  }
}

