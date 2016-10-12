package comparadores;

import java.util.Comparator;

/**
 * A class implementing a comparator for alumnos.
 * Alumnos are compared first using their "resultados",
 * and if equal then using their name, and if equal then
 * using their matriculas.
 * <p><strong>YOU SHOULD MODIFY THIS CLASS.</strong>
 */
// One can assume that fields are not null
public class CompareAlumnos implements Comparator<Alumno> {
    /**
     * Compares two alumnos. The comparison should be done by
     * first comparing the resultados, then, if the resultados compare equal,
     * compare the names (using the standard string comparison operator
     * <a href="http://docs.oracle.com/javase/6/docs/api/java/lang/String.html#compareTo(java.lang.String)"><code>compareTo</code></a>
     * in the <a href="http://docs.oracle.com/javase/6/docs/api/java/lang/String.html"><code>java.lang.String</code> class</a>),
     * then if the names are also equal, comparing the matriculas
     * using the standard string comparison operator.
     * <p>
     * Two resultados <code>r1</code> and <code>r2</code>
     * should be compared by computing their mean values, and 
     * comparing them using <strong>integer arithmetic 
     * without rounding</strong>. 
     * That is, if <code>r1</code> is 5,1,7
     * then its mean value is (5+1+7)/3 = 13/3 = 4 (<em>note, no rounding</em>).
     * Moreover, if <code>r2</code> is 4,4,4 then its mean value is
     * (4+2+6)/3 = 12/3 = 4. Thus, the two resultados <code>r1</code> and
     * <code>r2</code> compare equal.
     * <p>
     * You may assume that every alumno has at least one result
     * (i.e., the length of resultados is non-zero).
     * <p>
     * The function returns an integer less than 0 if alumno <code>a1</code>
     * compares less than alumno <code>a2</code>, 
     * 0 if the alumnos compare equal,
     * and an integer greater than 0 if alumno <code>a1</code> compares
     * greater than alumno <code>a2</code>.
     * <p><strong> YOU SHOULD MODIFY THIS FUNCTION.</strong>
     */
    public int compare(Alumno a1, Alumno a2) {
    	int nombresComp=a1.nombre().compareTo(a2.nombre());
    	int matriculasComp=a1.matricula().compareTo(a2.matricula());
    	boolean mediaMenor=media(a1)<media(a2);
    	boolean mediaIgual=media(a1)==media(a2);
    	if(mediaIgual&&nombresComp==0&&matriculasComp==0)
    		return 0;
    	else if(mediaMenor || (mediaIgual&&nombresComp<0)||(mediaIgual&&nombresComp==0&&matriculasComp<0))
    		return -1;
    	else
    		return 1;
    }
    private double media(Alumno a1){
    	return sumaArray(a1.resultados())/a1.resultados().length;
    }
    private int sumaArray(int[] array){
    	int suma=0;
    	for(int i=0;i<=array.length-1;i++){
    		suma=suma+array[i];
    	}
    	return suma;
    }
}
