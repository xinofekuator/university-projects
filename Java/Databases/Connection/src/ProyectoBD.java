import java.sql.*;

public class ProyectoBD {

	//Parametros usados en las pruebas
	//mysql://telemaco.ls.fi.upm.es:3306 201344 grupo442013 Ejemplo 2119(este ultimo es nuestro customer id);

	public static void main(String[] args){

		//Devuelve un mensaje de error si no se introducen los parametros necesarios
		if(args.length!=5){
			System.out.println("Parametros del main incorrectos. Se deben pasar 5 paramentros: HOST,USR,PASSWD,TABLA,CUSTOMER_ID ");
			return;
		}
		else{
			try{
				Class.forName("com.mysql.jdbc.Driver");
				String host = args[0];
				String usr = args[1];
				String passwd = args[2];
				String tabla = args[3];
				
				//PARTE 1
				//Creamos la conexion con la base de datos JavaBD y realizamos un select sobre la tabla dada
				//Tambien obtenemos los metadatos de nuestra base de datos y del resultado de nuestra consulta
				//Al final obtenemos dos ResultSet de los que sacaremos la informacion que nos piden
				//Iremos concatenando las respuestas en el String resultado hasta obtener el String que nos piden
				
				Connection con = 
						DriverManager.getConnection("jdbc:"+host+"/JavaBD?user="+usr+"&password="+passwd);

				Statement sen = con.createStatement();
				DatabaseMetaData metaBase = con.getMetaData(); 
				ResultSet rs = sen.executeQuery("select * from " + tabla);
				ResultSet claves = metaBase.getPrimaryKeys(null, null, tabla);

				ResultSetMetaData meta = rs.getMetaData();

				String resultado=tabla+"; ";
				int nCols = meta.getColumnCount();
				
				resultado = resultado + nCols + "; ";
				while(claves.next()){
					String aux = claves.getString("COLUMN_NAME");
					resultado = resultado + aux;
					resultado = (!claves.isLast()) ?(resultado + ", ") : (resultado = resultado + "; ");
				}

				for(int i=1;i<=nCols;i++){
					String nombre = meta.getColumnName(i);
					String tipo = meta.getColumnTypeName(i);
					int precision = meta.getPrecision(i);
					resultado = resultado + nombre + ", "+ tipo + ", " + precision;
					if(i != nCols){ 
						resultado = resultado + "; " ;  
					}
				}

				rs.close();
				sen.close();
				con.close();

				//Cerramos las conexiones y creamos una nueva con la tabla PryBBDD para invocar al procedimiento de entrega
				//mediante un CallableStatement
				
				Connection conEntrega = 
						DriverManager.getConnection("jdbc:"+host+"/ProyBBDD?user="+usr+"&password="+passwd);
				CallableStatement callEntrega = conEntrega.prepareCall("{call Entrega(?,?)}");
				callEntrega.setInt(1, 9);
				callEntrega.setString(2, resultado);
				callEntrega.execute();

				callEntrega.close();
				conEntrega.close();
				
				System.out.println("Informacion de los metadatos de la tabla "+tabla+" enviados correctamente.");

				//PARTE 2
				//Creamos la conexion con sakila. Tambien creamos un CallableStatement para invocar el procedimiento RentStatus(en
				//este caso a diferencia del ejercicio anterior decidimos no crear conexion con ProyBBDD)
				//Tambien obtenemos el valor de la funcion inventory_in_stock y de esta forma no realizamos el alquiler
				//en caso de que nos devuelva 0 y en caso contrario hacemos una inserccion en la tabla rental.
				
				Connection sakila = 
						DriverManager.getConnection("jdbc:"+host+"/sakila?user="+usr+"&password="+passwd);
				Statement alquiler = sakila.createStatement();

				CallableStatement rentStatus = sakila.prepareCall("{call ProyBBDD.RentStatus(?)}");

				int disp;
				ResultSet inventoryInStock = alquiler.executeQuery("SELECT inventory_in_stock(201344);");
				
				//ALQUILER
				if(inventoryInStock.next()){
					disp= inventoryInStock.getInt(1);			
				}
				else{
					System.out.println("Fallo en inventory_in_stock(201344)");
					return;
				}

				if (disp !=0){
					int mod1 = alquiler.executeUpdate("INSERT INTO sakila.rental(rental_date,inventory_id,customer_id,staff_id) " +
							" values(current_date(),201344,2119,1);");
					System.out.println("Filas modificadas al alquilar: " + mod1);
					rentStatus.setInt(1, 1); 
					rentStatus.execute();  //enviamos la info de que hemos podido realizar el alquiler
				}
				else{
					rentStatus.setInt(1, 0); 
					rentStatus.execute();  //enviamos la info de que no hemos podido realizar el alquiler	
					System.out.println("No ha sido posible realizar el alquiler");
					return;
				}
				
				rentStatus.close();
				
				
				//Calculamos el ID. 
				//Esto es necesario porque necesitaremos esta info para la devolucion, pues el valor de last_insert_id cambiara
				//tras hacer la insercion del pago y no nos valdra
				
				int id = 0;
				ResultSet idAlquiler = alquiler.executeQuery("SELECT last_insert_id()");
				if (idAlquiler.next()){
					id = idAlquiler.getInt(1);
					idAlquiler.close();
				}
				else{
					System.out.println("Fallo en last_insert_id()");
					idAlquiler.close();
					return;
				}
				
				//Pago	
				//Realizamos un insert en la tabla payment con el id del alquiler previo y el importe calculado por
				//la funcion get_customer_balance
				int mod2 = alquiler.executeUpdate("INSERT INTO sakila.payment(customer_id,staff_id,rental_id,amount,payment_date)" +
						" values (2119,1,last_insert_id(),get_customer_balance(2119,current_date()),current_date());");
				System.out.println("Filas modificadas en el pago: " + mod2);

				//Devolucion
				//Realizamos un update de la tabla de alquileres en la fila de nuestro id que habiamos guardado 
				//Introducimos la fecha de devolucion
				int mod3 = alquiler.executeUpdate("UPDATE sakila.rental SET sakila.rental.return_date = current_date() " +
						"WHERE sakila.rental.rental_id = " + id);
				System.out.println("Filas modificadas en la devolucion: " + mod3);

				alquiler.close();
				sakila.close();
				System.out.println("Alquiler,pago y devolucion realizados correctamente");

			}catch(ClassNotFoundException ex){
				System.out.println("Driver no encontrado");
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}catch(SQLException ex){
				System.out.println("Error con la BD");
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
}

