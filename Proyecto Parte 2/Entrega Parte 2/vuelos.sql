#Borro la base de datos asi no genera errores en caso de ya existir una copia.
DROP DATABASE vuelos;

# Creo de la Base de Datos
CREATE DATABASE vuelos;

# selecciono la base de datos sobre la cual voy a hacer modificaciones
USE vuelos;

#------------------------------------------------------------------------------------
# Creacion de tablas para las entidades

CREATE TABLE ubicaciones (
	ciudad VARCHAR(45) NOT NULL,
    estado VARCHAR(45) NOT NULL,
    pais VARCHAR(45) NOT NULL,
    huso SMALLINT(2) SIGNED NOT NULL,
    
    CONSTRAINT pk_ubicaciones
    PRIMARY KEY (ciudad, estado, pais),
	CONSTRAINT rango_huso CHECK (huso >= -12 AND huso <= 12)
    
) ENGINE=InnoDB;

CREATE TABLE aeropuertos (
	nombre VARCHAR(45) NOT NULL,
    telefono VARCHAR(45) NOT NULL,
    direccion VARCHAR(45) NOT NULL,
    codigo VARCHAR(45) NOT NULL,
	ciudad VARCHAR(45) NOT NULL,
    estado VARCHAR(45) NOT NULL,
    pais VARCHAR(45) NOT NULL,
    
    CONSTRAINT pk_aeropuertos
    PRIMARY KEY (codigo),
	
	CONSTRAINT fk_aeropuerto_ubicaciones
	FOREIGN KEY (ciudad,estado,pais) REFERENCES ubicaciones(ciudad,estado,pais)
	ON DELETE RESTRICT ON UPDATE CASCADE
    
) ENGINE=InnoDB;

CREATE TABLE vuelos_programados(
	numero VARCHAR(45) NOT NULL,
    aeropuerto_salida VARCHAR(45) NOT NULL,
	aeropuerto_llegada VARCHAR(45) NOT NULL,
	
	CONSTRAINT pk_vuelos_programados
	PRIMARY KEY (numero),
	
	CONSTRAINT fk_vuelos_programados_llegada
	FOREIGN KEY (aeropuerto_llegada) REFERENCES aeropuertos(codigo)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT fk_vuelos_programados_salida
	FOREIGN KEY (aeropuerto_salida) REFERENCES aeropuertos(codigo)
	ON DELETE RESTRICT ON UPDATE CASCADE
	
) ENGINE=InnoDB;

CREATE TABLE modelos_avion(
	modelo VARCHAR(45) NOT NULL,
	fabricante VARCHAR(45) NOT NULL,
	cant_asientos SMALLINT unsigned NOT NULL,
	cabinas SMALLINT unsigned NOT NULL,
	
	CONSTRAINT pk_modelos_avion
	PRIMARY KEY (modelo)
	
) ENGINE=InnoDB;

CREATE TABLE salidas(
	dia ENUM('Do','Lu','Ma','Mi','Ju','Vi','Sa') NOT NULL,
	hora_llega TIME NOT NULL DEFAULT '00:00:00',
	hora_sale TIME NOT NULL DEFAULT '00:00:00',
	vuelo VARCHAR(45) NOT NULL,
	modelo_avion VARCHAR(45) NOT NULL,
	
	CONSTRAINT pk_salidas
	PRIMARY KEY (vuelo,dia),
	
	CONSTRAINT fk_salida_vuelo
	FOREIGN KEY (vuelo) REFERENCES vuelos_programados (numero)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT fk_salida_modelo
	FOREIGN KEY (modelo_avion) REFERENCES modelos_avion (modelo)
	ON DELETE RESTRICT ON UPDATE CASCADE
	
) ENGINE=InnoDB;

CREATE TABLE instancias_vuelo(
	estado VARCHAR(45),
	fecha DATE NOT NULL DEFAULT '1990-09-01',
	dia ENUM('Do','Lu','Ma','Mi','Ju','Vi','Sa') NOT NULL,
	vuelo VARCHAR(45) NOT NULL,
	
	CONSTRAINT pk_instancias_vuelo
	PRIMARY KEY (vuelo,fecha),
	
	CONSTRAINT fk_instancias_salidas
	FOREIGN KEY (vuelo,dia) REFERENCES salidas (vuelo,dia)
	ON DELETE RESTRICT ON UPDATE CASCADE
	
) ENGINE=InnoDB;

CREATE TABLE clases(
	nombre VARCHAR(45) NOT NULL,
	porcentaje DECIMAL(2,2) unsigned NOT NULL,
	
	CONSTRAINT pk_clases
    PRIMARY KEY (nombre),
	CONSTRAINT porcentaje_clases_valido CHECK (porcentaje >= 0 AND porcentaje <= 0.99)

) ENGINE=InnoDB;

CREATE TABLE comodidades(
	codigo SMALLINT unsigned NOT NULL,
	descripcion TEXT(255) NOT NULL,
	
	CONSTRAINT pk_comodidades
	PRIMARY KEY (codigo)
	
) ENGINE=InnoDB;

CREATE TABLE pasajeros(
	doc_tipo VARCHAR(45) NOT NULL,
	doc_nro INT unsigned NOT NULL,
	apellido VARCHAR(45) NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,
	nacionalidad VARCHAR(45) NOT NULL,
    
	CONSTRAINT pk_pasajeros
	PRIMARY KEY(doc_nro,doc_tipo)
    
) ENGINE=InnoDB;

CREATE TABLE empleados(
	legajo INT unsigned NOT NULL,
	password CHAR(32) NOT NULL,
	doc_tipo VARCHAR(45) NOT NULL,
	doc_nro INT unsigned NOT NULL,
	apellido VARCHAR(45) NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,
    
	CONSTRAINT pk_empleados
	PRIMARY KEY(legajo)
    
) ENGINE=InnoDB;

CREATE TABLE reservas(
	numero SMALLINT unsigned NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL DEFAULT '1990-09-01', 
	vencimiento DATE NOT NULL DEFAULT '1991-09-26',
	estado VARCHAR(45) NOT NULL,
	doc_tipo VARCHAR(45) NOT NULL,
	doc_nro INT unsigned NOT NULL,
	legajo INT unsigned NOT NULL,
	
	CONSTRAINT pk_reservas
	PRIMARY KEY (numero),
	
	CONSTRAINT fk_reservas_pasajeros
	FOREIGN KEY (doc_nro,doc_tipo) REFERENCES pasajeros(doc_nro,doc_tipo)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT fk_reservas_empleados
	FOREIGN KEY (legajo) REFERENCES empleados(legajo)
	ON DELETE RESTRICT ON UPDATE CASCADE
	
) ENGINE=InnoDB;

#-------------------------------------------------------------------------
# Creación Tablas para las relaciones


CREATE TABLE brinda(
	vuelo VARCHAR(45) NOT NULL,
	dia ENUM('Do','Lu','Ma','Mi','Ju','Vi','Sa') NOT NULL,
	clase VARCHAR(45) NOT NULL,
	precio DECIMAL(7,2) unsigned NOT NULL,
	cant_asientos SMALLINT unsigned NOT NULL,
	
	PRIMARY KEY (vuelo, dia, clase),

	CONSTRAINT pk_brinda_salidas
	FOREIGN KEY (vuelo,dia) REFERENCES salidas (vuelo,dia)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_brinda_clase
	FOREIGN KEY (clase) REFERENCES clases (nombre)
	ON DELETE RESTRICT ON UPDATE CASCADE
    
) ENGINE=InnoDB;

CREATE TABLE posee(
	clase VARCHAR(45) NOT NULL,
	comodidad SMALLINT unsigned NOT NULL,
	
	PRIMARY KEY (clase, comodidad),
	
	CONSTRAINT pk_posee_clase
	FOREIGN KEY (clase) REFERENCES clases (nombre)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT pk_posee_comodidad
	FOREIGN KEY (comodidad) REFERENCES comodidades (codigo)
	ON DELETE RESTRICT ON UPDATE CASCADE
	
) ENGINE=InnoDB;


CREATE TABLE reserva_vuelo_clase(
	numero SMALLINT unsigned NOT NULL,
	vuelo VARCHAR(45) NOT NULL,
	fecha_vuelo DATE NOT NULL DEFAULT '1995-09-01',
	clase VARCHAR(45) NOT NULL,
	
	PRIMARY KEY(numero, vuelo, fecha_vuelo),
	
	CONSTRAINT fk_reserva_numero
	FOREIGN KEY (numero) REFERENCES reservas (numero)
	ON DELETE RESTRICT ON UPDATE CASCADE,
    
	CONSTRAINT fk_reserva_instanciaVuelo
	FOREIGN KEY (vuelo,fecha_vuelo) REFERENCES instancias_vuelo (vuelo,fecha)
	ON DELETE RESTRICT ON UPDATE CASCADE,
    
	CONSTRAINT fk_reserva_clase
	FOREIGN KEY (clase) REFERENCES clases (nombre)
	ON DELETE RESTRICT ON UPDATE CASCADE
	
) ENGINE=InnoDB;

	
#------------------------------------------------------------------
#Creacion de vistas

	CREATE VIEW datos_vuelos AS 
    SELECT s.vuelo as nro_vuelo, iv.fecha, s.modelo_avion AS avion, s.dia, s.hora_sale as salida, 
    s.hora_llega as llegada, 
	TIMEDIFF(s.hora_llega,s.hora_sale) as tiempo_estimado,
	asalida.codigo AS cod_salida, asalida.nombre AS aero_salida , asalida.ciudad AS ciudad_salida,
    asalida.estado AS estado_salida , asalida.pais AS pais_salida,
	allegada.codigo AS cod_llegada, allegada.nombre AS aero_llegada, 
    allegada.ciudad AS  ciudad_llegada, allegada.estado AS estado_llegada, allegada.pais AS pais_llegada, 
    b.clase, b.precio,	ROUND(b.cant_asientos+(c.porcentaje*b.cant_asientos)) as total_asientos
    FROM  brinda AS b, instancias_vuelo as iv, salidas as s, vuelos_programados as v, clases as c,  
          aeropuertos as asalida, aeropuertos as allegada 
    WHERE v.aeropuerto_salida=asalida.codigo AND v.aeropuerto_llegada=allegada.codigo 
          AND v.numero=s.vuelo AND s.vuelo=b.vuelo AND s.dia=b.dia AND b.clase=c.nombre 
          AND s.vuelo=iv.vuelo AND s.dia=iv.dia AND b.clase=c.nombre;

	CREATE VIEW vuelos_disponibles AS
	SELECT v.*, v.total_asientos - count(rvc.numero) as asientos_disponibles
	FROM  datos_vuelos as v LEFT JOIN reserva_vuelo_clase as rvc ON
      v.nro_vuelo= rvc.vuelo AND v.fecha=rvc.fecha_vuelo AND v.clase= rvc.clase
	GROUP BY v.nro_vuelo,v.fecha, v.clase;


#------------------------------------------------------------------------------------

# Creacion de usuarios

#admin
	CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
    GRANT ALL PRIVILEGES ON vuelos.* TO 'admin'@localhost;
#empleado
	CREATE USER 'empleado'@'%' IDENTIFIED BY 'empleado';
	GRANT SELECT ON vuelos.* TO 'empleado';
	GRANT INSERT, DELETE, UPDATE ON vuelos.reservas TO 'empleado';
	GRANT INSERT, DELETE, UPDATE ON vuelos.pasajeros TO  'empleado';
	GRANT INSERT, DELETE, UPDATE ON vuelos.reserva_vuelo_clase TO 'empleado';
#cliente
	CREATE USER 'cliente'@'%' IDENTIFIED BY 'cliente';
	GRANT SELECT ON vuelos.vuelos_disponibles TO 'cliente';