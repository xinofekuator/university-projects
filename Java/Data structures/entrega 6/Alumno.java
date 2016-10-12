package comparadores;

/**
 * A class recording test results for students.
 * An "alumno" has three attributes: <code>nombre</code>, 
 * <code>matricula</code>, and <code>resultados</code>.
 * The <code>resultados</code> attribute is an array of non-negative
 * integers, codifying a number of test results.
 */
public class Alumno {
    private String nombre;
    private String matricula;
    private int[] resultados;

    /** Creates an alumno, providing values for the name, matricula
     * and resultados atttributes.
     */
    public Alumno(String nombre, String matricula, int[] resultados) {
	this.nombre = nombre;
	this.matricula = matricula;
	this.resultados = resultados;
    }

    /** Returns the resultados. */
    public int[] resultados() {
	return resultados;
    }

    /** Returns the name. */
    public String nombre() {
	return nombre;
    }

    /** Returns the matricula. */
    public String matricula() {
	return matricula;
    }

    /* Prettyprints an alumno. */
    public String toString() {
	String resultString = "";
	for (Integer result : resultados)
	    if (resultString == "") 
		resultString = result.toString();
	    else
		resultString = resultString + "," + result.toString();
	return "{name="+nombre+",matricula="+matricula+",resultados=["+resultString+"]}";
    }
}
