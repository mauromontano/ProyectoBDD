# UBICACIONES
	
	INSERT INTO ubicaciones(pais,estado,ciudad,huso)
	VALUES ('Argentina', 'Buenos Aires', 'Bahia Blanca', 0);
	INSERT INTO ubicaciones(pais,estado,ciudad,huso) 
	VALUES ('Argentina', 'Córdoba', 'Rio Cuarto', 0);
	INSERT INTO ubicaciones(pais,estado,ciudad,huso)
	VALUES ('Argentina', 'Buenos Aires', 'Ezeiza', 0);
 	INSERT INTO ubicaciones(pais,estado,ciudad,huso) 
	VALUES ('Argentina', 'Buenos Aires', 'Mar del Plata', 0);
	INSERT INTO ubicaciones(pais,estado,ciudad,huso)
	VALUES ('Argentina', 'Mendoza', 'San Rafael', 0);
	
#---------------------------------------------------------------------------------------------------------------------------
# AEROPUERTOS


	INSERT INTO aeropuertos(codigo,nombre,telefono,direccion,pais,estado,ciudad)
	VALUES ('AEP', 'Aeroparque Jorge Newbery', '(54)45765300', 'direaep', 'Argentina', 'Buenos Aires', 'Bahia Blanca');
	INSERT INTO aeropuertos(codigo,nombre,telefono,direccion,pais,estado,ciudad)
	VALUES ('COR', 'Ing.Aer.A.L.V. Taravella', '(54)03514750874', 'direcor', 'Argentina', 'Córdoba', 'Rio Cuarto');
	INSERT INTO aeropuertos(codigo,nombre,telefono,direccion,pais,estado,ciudad)
	VALUES ('EZE', 'Ministro Pistarini', '(54)44802514', 'direeze', 'Argentina', 'Buenos Aires', 'Ezeiza');
	INSERT INTO aeropuertos(codigo,nombre,telefono,direccion,pais,estado,ciudad)
	VALUES ('MDQ', 'Brig. Gral. Bartolomé de Colina', '(54)02234785811', 'diremdq', 'Argentina', 'Buenos Aires', 'Mar del Plata');
	INSERT INTO aeropuertos(codigo,nombre,telefono,direccion,pais,estado,ciudad) 
	VALUES ('MDZ', 'Gdor. Francisco Gabrielli', '(54)02614480017', 'diremdz', 'Argentina', 'Mendoza', 'San Rafael');


#---------------------------------------------------------------------------------------------------------------------------
# VUELOS_PROGRAMADOS
    # dos vuelos de Buenos aires a Cordoba
	INSERT INTO vuelos_programados(numero, aeropuerto_salida, aeropuerto_llegada)
	VALUES ('BC1', 'AEP', 'COR');
	INSERT INTO vuelos_programados(numero, aeropuerto_salida, aeropuerto_llegada)
	VALUES ('BC2', 'AEP', 'COR');
	
	# dos vuelos de Cordoba a Buenos Aires
	INSERT INTO vuelos_programados(numero, aeropuerto_salida, aeropuerto_llegada)
	VALUES ('CB1', 'COR', 'AEP');
	INSERT INTO vuelos_programados(numero, aeropuerto_salida, aeropuerto_llegada)
	VALUES ('CB2', 'COR', 'AEP');
	    
	# un vuelo de Buenos aires a Mar del Plata
	INSERT INTO vuelos_programados(numero, aeropuerto_salida, aeropuerto_llegada)
	VALUES ('BM', 'AEP', 'MDQ');

	# un vuelo de  Mar del Plata a Buenos aires
	INSERT INTO vuelos_programados(numero, aeropuerto_salida, aeropuerto_llegada)
	VALUES ('MB', 'MDQ', 'AEP');
	
		
#---------------------------------------------------------------------------------------------------------------------------
# MODELOS_AVION

	INSERT INTO modelos_avion(modelo, fabricante, cabinas, cant_asientos)
	VALUES ('B-737', 'Boeing', 2, 60);
	INSERT INTO modelos_avion(modelo, fabricante,cabinas, cant_asientos) 
	VALUES ('B-747', 'Boeing', 2, 90);
	INSERT INTO modelos_avion(modelo, fabricante,cabinas, cant_asientos) 
	VALUES ('B-757', 'Boeing', 2, 120);
	INSERT INTO modelos_avion(modelo, fabricante,cabinas, cant_asientos) 
	VALUES ('B-767', 'Boeing', 2, 150);

#---------------------------------------------------------------------------------------------------------------------------
# SALIDAS

	INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
	VALUES ('BC1', 'Lu', '08:00:00', '09:00:00', 'B-737');
	INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
	VALUES ('BC1', 'Ju', '18:00:00', '19:00:00', 'B-737');
	
    INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
	VALUES ('BC2', 'Mi', '09:00:00', '10:00:00', 'B-747');
	INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
	VALUES ('BC2', 'Ma', '17:00:00', '18:10:00', 'B-737');

    INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
	VALUES ('CB1', 'Do', '08:00:00', '09:00:00', 'B-737');
	INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
	VALUES ('CB1', 'Vi', '18:00:00', '19:00:00', 'B-737');
	
    INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
	VALUES ('CB2', 'Lu', '09:00:00', '10:00:00', 'B-747');
	INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
	VALUES ('CB2', 'Sa', '17:00:00', '18:10:00', 'B-737');

    INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion)
	VALUES ('BM', 'Ju', '09:00:00', '9:35:00', 'B-757');
	INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion)
	VALUES ('BM', 'Lu', '17:00:00', '17:40:00', 'B-757');

	INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
	VALUES ('MB', 'Ma', '09:00:00', '9:35:00', 'B-757');
	INSERT INTO salidas(vuelo, dia, hora_sale, hora_llega, modelo_avion) 
	VALUES ('MB', 'Sa', '19:00:00', '19:40:00', 'B-757');

#---------------------------------------------------------------------------------------------------------------------------
# INSTANCIAS_VUELO


	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
	VALUES ('BC1',' 2019-09-01','Lu', 'a tiempo');
	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado)
	VALUES ('BC1',' 2019-07-08','Ju', 'demorado');

	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
	VALUES ('BC2',' 2019-10-08','Mi', 'a tiempo');
	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
	VALUES ('BC2',' 2019-11-09','Ma', 'demorado');

	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
	VALUES ('CB1',' 2019-02-08','Do', 'a tiempo');
	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
	VALUES ('CB1',' 2019-04-01','Vi', 'demorado');

	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
	VALUES ('CB2',' 2019-07-29','Lu', 'a tiempo');
	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
	VALUES ('CB2',' 2019-09-18','Sa', 'demorado');

	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
	VALUES ('BM', ' 2017-02-09','Ju', 'a tiempo');
	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
	VALUES ('BM', ' 2017-02-03','Lu', 'demorado');

	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
	VALUES ('MB', ' 2018-06-20','Ma', 'a tiempo');
	INSERT INTO instancias_vuelo( vuelo, fecha, dia, estado) 
	VALUES ('MB', ' 2018-05-03','Sa', 'demorado');

#---------------------------------------------------------------------------------------------------------------------------
# CLASES

	INSERT INTO clases(nombre, porcentaje) VALUES ('Turista', 0.67);
	INSERT INTO clases(nombre, porcentaje) VALUES ('Ejecutiva', 0.5);
	INSERT INTO clases(nombre, porcentaje) VALUES ('Primera', 0.34);

#---------------------------------------------------------------------------------------------------------------------------
# COMODIDADES

	INSERT INTO comodidades(codigo,descripcion) VALUES (1, 'Asientos grandes');
	INSERT INTO comodidades(codigo,descripcion) VALUES (2, 'Comida rica');
	INSERT INTO comodidades(codigo,descripcion) VALUES (3, 'Azafata amable');
	INSERT INTO comodidades(codigo,descripcion) VALUES (4, 'Internet');

#---------------------------------------------------------------------------------------------------------------------------
# PASAJEROS

	INSERT INTO pasajeros(doc_tipo, doc_nro, nombre, apellido, direccion, telefono, nacionalidad) 
	VALUES ('DNI', 1, 'Julieta', 'Marcos', 'Roca 850', '02932 424565', 'Argentina');
	INSERT INTO pasajeros(doc_tipo, doc_nro, nombre, apellido, direccion, telefono, nacionalidad)
	VALUES ('DNI', 2, 'Luciano', 'Tamargo', 'Alem 222', '0291 45222', 'Argentino');
	INSERT INTO pasajeros(doc_tipo, doc_nro, nombre, apellido, direccion, telefono, nacionalidad)
	VALUES ('DNI', 3, 'Diego', 'Garcia', '12 de Octubre 333', '0291 45333', 'Argentino');
	INSERT INTO pasajeros(doc_tipo, doc_nro, nombre, apellido, direccion, telefono, nacionalidad)
	VALUES ('DNI', 4, 'Juan', 'Perez', 'Belgrano 14', '0291 4556733', 'Argentino');
	INSERT INTO pasajeros(doc_tipo, doc_nro, nombre, apellido, direccion, telefono, nacionalidad)
	VALUES ('DNI', 5, 'Julian', 'Gomez', 'Donado 533', '0291 4543563', 'Argentino');
	
#---------------------------------------------------------------------------------------------------------------------------
# EMPLEADOS
    
	INSERT INTO empleados(legajo, password, doc_tipo, doc_nro, nombre, apellido, direccion, telefono) 
	VALUES (101,md5('emp101'),'DNI', 101, 'Nombre_Emp101', 'Apellido_Emp101', 'Dir_Emp101' , '0291-4540101');
	INSERT INTO empleados(legajo, password, doc_tipo, doc_nro, nombre, apellido, direccion, telefono) 
	VALUES (102,md5('emp102'),'DNI', 102, 'Nombre_Emp102', 'Apellido_Emp102', 'Dir_Emp102' , '0291-4540102');
	INSERT INTO empleados(legajo, password, doc_tipo, doc_nro, nombre, apellido, direccion, telefono) 
	VALUES (103,md5('emp103'),'DNI', 103, 'Nombre_Emp103', 'Apellido_Emp103', 'Dir_Emp103' , '0291-4540103');
	INSERT INTO empleados(legajo, password, doc_tipo, doc_nro, nombre, apellido, direccion, telefono) 
	VALUES (104,md5('emp104'),'DNI', 104, 'Nombre_Emp104', 'Apellido_Emp104', 'Dir_Emp104' , '0291-4540104');
	INSERT INTO empleados(legajo, password, doc_tipo, doc_nro, nombre, apellido, direccion, telefono) 
	VALUES (105,md5('emp105'),'DNI', 105, 'Nombre_Emp105', 'Apellido_Emp105', 'Dir_Emp105' , '0291-4540105');
	

#---------------------------------------------------------------------------------------------------------------------------
# BRINDA

	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
	VALUES ('BC1', 'Lu', 'Turista',  100.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
	VALUES ('BC1', 'Lu', 'Primera',  200.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
	VALUES ('BC1', 'Lu', 'Ejecutiva', 300.00, 2);	
	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
	VALUES ('BC1', 'Ju', 'Turista',  100.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
	VALUES ('BC1', 'Ju', 'Primera',  200.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('BC1', 'Ju', 'Ejecutiva', 300.00, 2);	
	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
	VALUES ('BC2', 'Mi', 'Turista',  100.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
	VALUES ('BC2', 'Mi', 'Primera',  200.00, 3);	
		
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
	VALUES ('BC2', 'Ma', 'Turista',  100.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('BC2', 'Ma', 'Primera',  200.00, 3);	
	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('CB1', 'Do', 'Turista',  100.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('CB1', 'Do', 'Primera',  200.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('CB1', 'Do', 'Ejecutiva', 300.00, 2);	
	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('CB1', 'Vi', 'Turista',  100.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('CB1', 'Vi', 'Primera',  200.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('CB1', 'Vi', 'Ejecutiva', 300.00, 2);	
	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('CB2', 'Lu', 'Turista',  100.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('CB2', 'Lu', 'Primera',  200.00, 3);	
	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('CB2', 'Sa', 'Turista',  100.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('CB2', 'Sa', 'Primera',  200.00, 3);	
	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('BM', 'Ju', 'Turista',  100.00, 3);	
	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('BM', 'Lu', 'Turista',  100.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
	VALUES ('BM', 'Lu', 'Primera',  200.00, 3);	
	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
	VALUES ('MB', 'Ma', 'Turista',  100.00, 3);	
	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos)
	VALUES ('MB', 'Sa', 'Turista',  100.00, 3);	
	INSERT INTO brinda(vuelo, dia, clase, precio, cant_asientos) 
	VALUES ('MB', 'Sa', 'Primera',  200.00, 3);	
	
#---------------------------------------------------------------------------------------------------------------------------
# POSEE

	INSERT INTO posee(clase, comodidad) VALUES ('Turista', 3);
	
	INSERT INTO posee(clase, comodidad) VALUES ('Ejecutiva', 3);
	INSERT INTO posee(clase, comodidad) VALUES ('Ejecutiva', 4);	
	
	INSERT INTO posee(clase, comodidad) VALUES ('Primera', 1);
	INSERT INTO posee(clase, comodidad) VALUES ('Primera', 2);
	INSERT INTO posee(clase, comodidad) VALUES ('Primera', 3);
	INSERT INTO posee(clase, comodidad) VALUES ('Primera', 4);
	
#---------------------------------------------------------------------------------------------------------------------------
# RESERVAS 
# El empleado 10X realizo la reserva del pasajero X


        # reservas del vuelo BC1 
	INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (1, 'DNI', 1, 101,' 2019-09-01',' 2019-09-02', 'Confirmada');
	INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (2, 'DNI', 2, 102,' 2019-09-01',' 2019-09-02', 'Confirmada');	
	INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (3, 'DNI', 3, 103,' 2019-09-01',' 2019-09-02', 'Confirmada');	
	INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (4, 'DNI', 4, 104,' 2019-09-01',' 2019-09-02', 'En Espera');	

	INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (5, 'DNI', 5, 105,' 2019-07-08',' 2019-08-08', 'Confirmada');
        
        # reservas del vuelo CB2
	INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (6, 'DNI', 1, 101,' 2019-07-29',' 2019-08-29', 'Confirmada');
	INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (7, 'DNI', 2, 102,' 2019-07-29',' 2019-08-29', 'Confirmada');	
	INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (8, 'DNI', 3, 103,' 2019-07-29',' 2019-08-29', 'Confirmada');	
	INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (9, 'DNI', 4, 104,' 2019-07-29',' 2019-08-29', 'En Espera');	

	INSERT INTO reservas(numero, doc_tipo, doc_nro, legajo, fecha, vencimiento, estado) 
        VALUES (10, 'DNI', 5, 105,' 2019-09-18',' 2019-10-18', 'Confirmada');


#---------------------------------------------------------------------------------------------------------------------------
# RESERVA_VUELO_CLASE

        # reservas del vuelo BC1 
	INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (1, 'BC1', ' 2019-09-01', 'Turista');
	INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (2, 'BC1', ' 2019-09-01', 'Turista');
	INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (3, 'BC1', ' 2019-09-01', 'Turista');
	INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (4, 'BC1', ' 2019-09-01', 'Turista');

	INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (5, 'BC1', ' 2019-07-08', 'Primera');

        # reservas del vuelo CB2 
        INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (6, 'CB2',' 2019-07-29', 'Turista');
	INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (7, 'CB2',' 2019-07-29', 'Turista');
	INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (8, 'CB2',' 2019-07-29', 'Turista');
	INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (9, 'CB2', ' 2019-07-29', 'Turista');

	INSERT INTO reserva_vuelo_clase(numero, vuelo, fecha_vuelo, clase) 
        VALUES (10, 'CB2', ' 2019-09-18', 'Primera');
	
#--------------------------------------------------------------------------------------------------------------------------