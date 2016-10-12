class FiltroPregunta implements FilenameFilter {

  private String nombreTema;
  private String preguntaRaiz;

  FiltroPregunta(String nombreTema,String preguntaRaiz) {
    this.nombreTema=nombreTema;
    this.preguntaRaiz=preguntaRaiz;
  }

  public boolean accept(File dir, String name) {
    boolean resultado;
    //Pattern pat = Pattern.compile("^"+preguntaRaiz+"[\\d]*");
    Pattern pat = Pattern.compile(preguntaRaiz + ".*");
    Matcher mat = pat.matcher(name);
    return mat.matches();
  }
}

