-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: ubuassistant
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administradores`
--

DROP TABLE IF EXISTS `administradores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administradores` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `mail` varchar(300) NOT NULL,
  `contrasena` varchar(300) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administradores`
--

LOCK TABLES `administradores` WRITE;
/*!40000 ALTER TABLE `administradores` DISABLE KEYS */;
INSERT INTO `administradores` VALUES (1,'admin1@ubu.es','admin1'),(2,'admin2@ubu.es','admin2'),(3,'admin3@ubu.es','admin3');
/*!40000 ALTER TABLE `administradores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aprendizaje`
--

DROP TABLE IF EXISTS `aprendizaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aprendizaje` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `userid` varchar(1000) NOT NULL,
  `palabra1` varchar(1000) NOT NULL,
  `palabra2` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aprendizaje`
--

LOCK TABLES `aprendizaje` WRITE;
/*!40000 ALTER TABLE `aprendizaje` DISABLE KEYS */;
INSERT INTO `aprendizaje` VALUES (3,'170530192131486','wiki','http://www.ubu.es/servicio-de-informatica-y-comunicaciones/catalogo-de-servicios/red-wifi-vpn/wi-fi'),(4,'170601133013911','cccccc','http://www.ubu.es/master-universitario-en-acceso-la-abogacia'),(5,'170601180316915','verdad','http://www.ubu.es/cursos-de-verano-de-la-universidad-de-burgos');
/*!40000 ALTER TABLE `aprendizaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `casedescription`
--

DROP TABLE IF EXISTS `casedescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `casedescription` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `keyWord1` varchar(1000) DEFAULT NULL,
  `keyWord2` varchar(1000) DEFAULT NULL,
  `keyWord3` varchar(1000) DEFAULT NULL,
  `keyWord4` varchar(1000) DEFAULT NULL,
  `keyWord5` varchar(1000) DEFAULT NULL,
  `categoria` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=222 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `casedescription`
--

LOCK TABLES `casedescription` WRITE;
/*!40000 ALTER TABLE `casedescription` DISABLE KEYS */;
INSERT INTO `casedescription` VALUES (3,'v','ttttt',NULL,NULL,NULL,'ttt'),(4,'historia (online)','patrimonio','historia','artes','online','estudios'),(5,'historia','patrimonio',NULL,'artes','humanidades','estudios'),(7,'cyta','tecnologia','alimentos','salud',NULL,'estudios'),(8,'enfermeria','salud',NULL,NULL,NULL,'estudios'),(9,'terapia','ocupacional',NULL,'salud',NULL,'estudios'),(10,'dade','derecho','administracion','direccion','empresas','estudios'),(11,'derecho politica','politica','publica','doble','derecho','estudios'),(12,'ade','administracion','direccion','empresas','sociales','estudios'),(13,'politica (online)','politica','publica','online','sociales','estudios'),(14,'politica','ciencia','publica','gestion','sociales','estudios'),(15,'audiovisual','comunicacion',NULL,'juridicas','sociales','estudios'),(16,'derecho','unica',NULL,'juridicas','sociales','estudios'),(17,'educacion social','educacion','social','juridicas','sociales','estudios'),(18,'finanzas','contabilidad',NULL,'juridicas','sociales','estudios'),(19,'eduacion infantil','eduacion','infantil','maestro','sociales','estudios'),(20,'eduacion primaria','eduacion','primaria','maestro','sociales','estudios'),(21,'pedagogia',NULL,NULL,'juridicas','sociales','estudios'),(22,'relaciones laborales','relaciones','laborales','recursos','humanos','estudios'),(23,'turismo (online)','turismo','online','juridicas','sociales','estudios'),(24,'turismo',NULL,NULL,'juridicas','sociales','estudios'),(25,'civil y tecnica','civil','arquitectura','tecnica','doble','estudios'),(26,'caminos y tecnica','caminos','arquitectura','tecnica','doble','estudios'),(27,'mecanica y electronica','mecanica','electronica','automatica','doble','estudios'),(28,'tecnica','arquitectura',NULL,NULL,NULL,'estudios'),(29,'agroalimentaria','rural','medio','ingenieria',NULL,'estudios'),(30,'civil','ingenieria',NULL,NULL,NULL,'estudios'),(31,'organizacion industrial',NULL,'organizacion','industrial','ingenieria','estudios'),(32,'caminos','tecnologias','ingenieria',NULL,NULL,'estudios'),(33,'electronica','automatica','ingenieria',NULL,NULL,'estudios'),(34,'informatica (online)','informatica','online','ingenieria',NULL,'estudios'),(35,'informatica',NULL,NULL,'ingenieria',NULL,'estudios'),(36,'mecanica','ingenieria',NULL,NULL,NULL,'estudios'),(37,'patrimonio (master)','patrimonio','comunicacion','master','artes','estudios'),(38,'vino','cultura','enoturismo','master','semipresencial','estudios'),(39,'quimica (master)','quimica','avanzada','master','ciencias','estudios'),(40,'evolucion humana','evolucion','humana','master','interuniversitario','estudios'),(41,'electroquimica','ciencia','tecnologia','master','interuniversitario','estudios'),(42,'seguridad y biotecnologia','seguridad','biotecnologia','master','alimentarias','estudios'),(43,'formacion pedagogica didactica','pedagogica','didactica','curso','formacion','estudios'),(44,'abogacia','acceso','sociales','master',NULL,'estudios'),(45,'administracion empresas (master)','administracion','empresas','master','mba','estudios'),(46,'multimedia','desarrollo','comunicacion','master',NULL,'estudios'),(47,'contabilidad','avanzada','auditoria','master','cuentas','estudios'),(48,'cooperacion','internacional','desarrollo','master','interuniversitario','estudios'),(49,'derecho y administracion local','derecho','administracion','master','local','estudios'),(50,'educacion y sociedad inclusivas','educacion','sociedad','master','inclusivas','estudios'),(51,'economia empresa','investigacion','administracion','master','empresa','estudios'),(52,'profesor','educacion','secundaria','master','bachillerato','estudios'),(53,'informatica (master)','informatica','master','ingenieria',NULL,'estudios'),(54,'informatica (master, online)','informatica','master','ingenieria','online','estudios'),(55,'industrial (master)','industrial','master','ingenieria',NULL,'estudios'),(56,'gestion agrosostenible','agrosostenible','master','ingenieria','gestion','estudios'),(57,'caminos (master)','caminos','master','canales','puertos','estudios'),(58,'edificacion','inspeccion','master','enegetica','rehabilitacion','estudios'),(59,'edificacion (online)','inspeccion','master','online','rehabilitacion','estudios'),(60,'integridad','durabilidad','master','materiales','componentes','estudios'),(61,'titulos','propios',NULL,NULL,NULL,'estudios'),(62,'idiomas','cursos','lenguas','modernas','matricula','estudios'),(63,'internacionales','cursos','español',NULL,NULL,'estudios'),(64,'licenciaturas','extinguir','ingenierias',NULL,NULL,'estudios'),(65,'ubuabierta presenciales','cursos','presenciales','ubuabierta',NULL,'estudios'),(66,'ubuabierta online','cursos','presenciales','ubuabierta',NULL,'estudios'),(67,'nivel 0','cursos','0','ubuabierta','cero','estudios'),(68,'verano','cursos',NULL,'ubuabierta',NULL,'estudios'),(69,'infantiles','cursos','campus','ubuabierta',NULL,'estudios'),(70,'mayores','cursos','formacion','ubuabierta',NULL,'estudios'),(71,'departamentos','centros',NULL,NULL,NULL,'estudios'),(72,'becas','ayudas','bonificaciones',NULL,NULL,'estudios'),(73,'calendario','academico','festivos','docencia',NULL,'estudios'),(74,'tramites','academicos','expediente','compulsar','reconocimiento','estudios'),(75,'calidad','docencia',NULL,NULL,NULL,'estudios'),(76,'empleo','practicas','orientacion','emprender',NULL,'estudios'),(77,'formacion permanente','formacion','permanente','complementaria',NULL,'estudios'),(78,'curriculum docente','curriculum','docente','profesorado',NULL,'estudios'),(79,'estudios',NULL,NULL,NULL,NULL,'estudios'),(80,'ubuabierta',NULL,NULL,NULL,NULL,'estudios'),(81,'ciencia y biotecnologia','ciencia','alimentaria','biotecnologia','doctorado','estudios'),(82,'idustriales y civil','industriales','ingenieria','civil','doctorado','estudios'),(83,'quimica (doctorado)','quimica','avanzada',NULL,'doctorado','estudios'),(84,'termodinamica fluidos','termodinamica','fluidos','ingenieria','doctorado','estudios'),(85,'humanidades (doctorado)','humanidades','comunicacion',NULL,'doctorado','estudios'),(86,'electroquimica','ciencia','tecnologia',NULL,'doctorado','estudios'),(87,'sostenibilidad','eficiencia','energetica',NULL,'doctorado','estudios'),(88,'educacion (doctorado)','educacion',NULL,NULL,'doctorado','estudios'),(89,'economia empresa','economia','empresa',NULL,'doctorado','estudios'),(90,'evolucion humana (doctorado)','evolucion','paleoecologia','geofisicas','doctorado','estudios'),(91,'ciencias juridicas','juridicas','economicas','sociales','ciencias','estudios'),(92,'matricula',NULL,NULL,NULL,NULL,'admisión y matrícula'),(93,'acceso',NULL,NULL,NULL,NULL,'admisión y matrícula'),(94,'bachillerato','acceso',NULL,NULL,NULL,'admisión y matrícula'),(95,'formacion profesional','formacion','profesional','acceso',NULL,'admisión y matrícula'),(96,'mayores de 25','mayores','25','veinticinco','acceso','admisión y matrícula'),(97,'mayores de 45','mayores','45','cuarenta y cinco','acceso','admisión y matrícula'),(98,'mayores de 40','mayores','40','cuarenta','acceso','admisión y matrícula'),(99,'admision',NULL,NULL,NULL,NULL,'admisión y matrícula'),(100,'admision grados','admision','grados',NULL,NULL,'admisión y matrícula'),(101,'admision master','admision','master',NULL,NULL,'admisión y matrícula'),(102,'admision doctorado','admision','doctorado',NULL,NULL,'admisión y matrícula'),(103,'admision titulos propios','titulos','porpios','admision',NULL,'admisión y matrícula'),(104,'admision formacion permanente','formacion','permanente','admision',NULL,'admisión y matrícula'),(105,'admision visitantes','estudiantes','visitantes','admision',NULL,'admisión y matrícula'),(106,'admision estudios extranjeros','estudiantes','estudios','extranjeros','admision','admisión y matrícula'),(107,'matricula grados','matricula','grados',NULL,NULL,'admisión y matrícula'),(108,'matricula master','matricula','master',NULL,NULL,'admisión y matrícula'),(109,'matricula doctorado','matricula','doctorado',NULL,NULL,'admisión y matrícula'),(110,'matricula titulos propios','titulos','porpios','matricula',NULL,'admisión y matrícula'),(111,'matricula titulos extincion','titulos','extincion','matricula',NULL,'admisión y matrícula'),(112,'matricula internacional','estudiantes','internacionales','matricula',NULL,'admisión y matrícula'),(113,'matricula cursos','cursos','presenciales','online','matricula','admisión y matrícula'),(114,'matricula cursos verano','cursos','verano','matricula',NULL,'admisión y matrícula'),(115,'matricula mayores','formacion','mayores','matricula',NULL,'admisión y matrícula'),(116,'matricula curso espanol','curso','español','matricula','espanol','admisión y matrícula'),(117,'expedicion titulos','expedicion','titulos',NULL,NULL,'admisión y matrícula'),(118,'reconocimiento','creditos',NULL,NULL,NULL,'admisión y matrícula'),(119,'tesis','doctoral',NULL,NULL,NULL,'admisión y matrícula'),(120,'normativa','academica',NULL,NULL,NULL,'admisión y matrícula'),(121,'seguro escolar','seguro','escolar',NULL,NULL,'admisión y matrícula'),(122,'seguro accidentes','seguro','accidentes',NULL,NULL,'admisión y matrícula'),(123,'tarjeta','universitaria',NULL,NULL,NULL,'admisión y matrícula'),(124,'grupos','unidades','investigacion',NULL,NULL,'investigación'),(125,'gestion','investigacion',NULL,NULL,NULL,'investigación'),(126,'otri','transferencia','investigacion',NULL,NULL,'investigación'),(127,'UBUemprede','emprende','investigacion',NULL,NULL,'investigación'),(128,'parque cientifico','parque','cientifico','tecnologico','investigacion','investigación'),(129,'escuela','doctorado','investigacion',NULL,NULL,'investigación'),(130,'iccram','investigacion',NULL,NULL,NULL,'investigación'),(131,'campus excelencia internacional','investigacion',NULL,NULL,NULL,'investigación'),(132,'ayudas investigacion','ayudas','propias','investigacion',NULL,'investigación'),(133,'ayudas proyectos investigacion','ayudas','propias','investigacion',NULL,'investigación'),(134,'convocatorias investigacion','ayudas','investigacion','convocatorias',NULL,'investigación'),(135,'empresas','ayudas','investigacion',NULL,NULL,'investigación'),(136,'emprendimiento','ayudas','convocatorias','investigacion',NULL,'investigación'),(137,'ayudas UE','europa','investigacion','ayudas',NULL,'investigación'),(138,'observatorio','investigacion',NULL,NULL,NULL,'investigación'),(139,'tesis investigacion','investigacion','tesis','lectura','defensa','investigación'),(140,'propiedad','investigacion','industrial','intelectual',NULL,'investigación'),(141,'repositorio','investigacion','internacional',NULL,NULL,'investigación'),(142,'curriculum','cvn','curriculo','normalizado','investigacion','investigación'),(143,'sexenios ','investigacion','acreditacion',NULL,NULL,'investigación'),(144,'formacion','investigacion',NULL,NULL,NULL,'investigación'),(145,'divulgacion','investigacion',NULL,NULL,NULL,'investigación'),(146,'vocaciones','investigacion',NULL,NULL,NULL,'investigación'),(147,'ubuinvestiga','investigacion',NULL,NULL,NULL,'investigación'),(148,'informacion','internacional',NULL,NULL,NULL,'internacional'),(149,'convocatorias','internacional',NULL,NULL,NULL,'internacional'),(150,'estudiantes','internacional',NULL,NULL,NULL,'internacional'),(151,'pdi/pas','internacional','pdi','pas',NULL,'internacional'),(152,'dele','internacional','diploma','español',NULL,'internacional'),(153,'ccse','internacional','constitucionales','socioculturales',NULL,'internacional'),(154,'acreditacion idiomas','internacional','acreditacion','idiomas',NULL,'internacional'),(155,'cooperacion','internacional','solidaria',NULL,NULL,'internacional'),(156,'convenios','internacional',NULL,NULL,NULL,'internacional'),(157,'becas internacionales','internacional','becas','cooperacion',NULL,'internacional'),(158,'ububangalore','internacional','bangalore',NULL,NULL,'internacional'),(159,'ayudas pdi/pas','internacional','ayudas','pdi','pas','internacional'),(160,'tour','internacional',NULL,NULL,NULL,'internacional'),(161,'aula virtual espanol','internacional','aula','virtual','español','internacional'),(162,'alojamiento','internacional',NULL,NULL,NULL,'internacional'),(163,'tienda',NULL,NULL,NULL,NULL,'universidad'),(164,'salud',NULL,NULL,NULL,NULL,'universidad'),(165,'caubu','consejo','alumnos',NULL,NULL,'universidad'),(166,'defensor',NULL,NULL,NULL,NULL,'universidad'),(167,'debate','liga',NULL,NULL,NULL,'universidad'),(168,'diversidad',NULL,NULL,NULL,NULL,'universidad'),(169,'vocacional','orientacion','profesional',NULL,NULL,'universidad'),(170,'apoyo','orientacion','tutoria',NULL,NULL,'universidad'),(171,'voluntariado',NULL,NULL,NULL,NULL,'universidad'),(172,'transparencia',NULL,NULL,NULL,NULL,'universidad'),(173,'tablon','oficial',NULL,NULL,NULL,'universidad'),(174,'normativa',NULL,NULL,NULL,NULL,'universidad'),(175,'sede','electronica',NULL,NULL,NULL,'universidad'),(176,'instituciones','empresas',NULL,NULL,NULL,'universidad'),(177,'trabajo','universidad',NULL,NULL,NULL,'universidad'),(178,'catedras',NULL,NULL,NULL,NULL,'universidad'),(179,'ubu',NULL,NULL,NULL,NULL,'universidad'),(180,'organos','universitarios',NULL,NULL,NULL,'universidad'),(181,'departamentos','centros',NULL,NULL,NULL,'universidad'),(182,'servicios',NULL,NULL,NULL,NULL,'universidad'),(183,'fundacion',NULL,NULL,NULL,NULL,'universidad'),(184,'representacion',NULL,NULL,NULL,NULL,'universidad'),(185,'directorio','contacto','direccion','telefono','mail','universidad'),(186,'cultura','musica','cine','teatro','exposiciones','universidad'),(187,'deporte','bici','esqui','senderismo','actividades','universidad'),(188,'wifi','wi-fi','internet',NULL,NULL,'universidad'),(189,'ubunet',NULL,NULL,NULL,NULL,'universidad'),(190,'ubuvirtual',NULL,'virtual',NULL,NULL,'universidad'),(191,'asociaciones','estudiantes',NULL,NULL,NULL,'universidad'),(192,'ubuventajas','ventajas',NULL,NULL,NULL,'universidad'),(193,'secretaria','virtual',NULL,NULL,NULL,'universidad'),(194,'biblioteca','recursos','libros','ubucat',NULL,'universidad'),(195,'alumnos','antiguos',NULL,NULL,NULL,'universidad'),(196,'noticias',NULL,NULL,NULL,NULL,'universidad'),(197,'agenda',NULL,NULL,NULL,NULL,'universidad'),(198,'te interesa','interesa',NULL,NULL,NULL,'universidad'),(199,'correo','office365','365',NULL,NULL,'recursos'),(200,'intranet',NULL,NULL,NULL,NULL,'recursos'),(201,'repositorio','internacional',NULL,NULL,NULL,'recursos'),(202,'archivo','general',NULL,NULL,NULL,'recursos'),(203,'normativa',NULL,NULL,NULL,NULL,'recursos'),(204,'boubu','boletion','oficial',NULL,NULL,'recursos'),(205,'dircom','direccion','comunicaciones',NULL,NULL,'recursos'),(206,'gabinete','comunicacion',NULL,NULL,NULL,'recursos'),(207,'actualidad',NULL,NULL,NULL,NULL,'recursos'),(208,'tvubu','television',NULL,NULL,NULL,'recursos'),(209,'uburadio','radio',NULL,NULL,NULL,'recursos'),(210,'protocolo',NULL,NULL,NULL,NULL,'recursos'),(211,'divulgacion','cientifica',NULL,NULL,NULL,'recursos'),(212,'publicaciones','imagen','institucional',NULL,NULL,'recursos'),(213,'buscapersonas','busca','personas',NULL,NULL,'recursos'),(214,'privacidad',NULL,NULL,NULL,NULL,'recursos'),(215,'zona privada','zona','privada',NULL,NULL,'recursos'),(216,'twitter','redes','sociales',NULL,NULL,'recursos'),(217,'facebook','redes','sociales',NULL,NULL,'recursos'),(218,'instagram','redes','sociales',NULL,NULL,'recursos'),(219,'youtube','redes','sociales',NULL,NULL,'recursos'),(220,'ubuapp','app','aplicacion','movil',NULL,'recursos'),(221,'honoris','causa',NULL,NULL,NULL,'universidad');
/*!40000 ALTER TABLE `casedescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `casesolution`
--

DROP TABLE IF EXISTS `casesolution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `casesolution` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `answer` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=222 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `casesolution`
--

LOCK TABLES `casesolution` WRITE;
/*!40000 ALTER TABLE `casesolution` DISABLE KEYS */;
INSERT INTO `casesolution` VALUES (3,'ttt'),(4,'http://www.ubu.es/grado-oficial-online-en-historia-y-patrimonio'),(5,'http://www.ubu.es/grado-en-historia-y-patrimonio'),(7,'http://www.ubu.es/grado-en-ciencia-y-tecnologia-de-los-alimentos'),(8,'http://www.ubu.es/grado-en-enfermeria'),(9,'http://www.ubu.es/grado-en-terapia-ocupacional'),(10,'http://www.ubu.es/doble-grado-en-derecho-y-administracion-y-direccion-de-empresas'),(11,'http://www.ubu.es/doble-grado-en-derecho-y-en-ciencia-politica-y-gestion-publica'),(12,'http://www.ubu.es/grado-en-administracion-y-direccion-de-empresas-bilingue-espanol'),(13,'http://www.ubu.es/grado-oficial-online-en-ciencia-politica-y-gestion-publica'),(14,'http://www.ubu.es/grado-en-ciencia-politica-y-gestion-publica'),(15,'http://www.ubu.es/grado-en-comunicacion-audiovisual'),(16,'http://www.ubu.es/grado-en-derecho'),(17,'http://www.ubu.es/grado-en-educacion-social'),(18,'http://www.ubu.es/grado-en-finanzas-y-contabilidad'),(19,'http://www.ubu.es/grado-en-maestro-de-educacion-infantil'),(20,'http://www.ubu.es/grado-en-maestro-de-educacion-primaria'),(21,'http://www.ubu.es/grado-en-pedagogia'),(22,'http://www.ubu.es/grado-en-relaciones-laborales-y-recursos-humanos'),(23,'http://www.ubu.es/grado-oficial-online-en-turismo'),(24,'http://www.ubu.es/grado-en-turismo'),(25,'http://www.ubu.es/doble-grado-en-ingenieria-civil-y-en-arquitectura-tecnica'),(26,'http://www.ubu.es/doble-grado-en-ingenieria-de-tecnologias-de-caminos-y-en-arquitectura-tecnica'),(27,'http://www.ubu.es/doble-grado-en-ingenieria-mecanica-e-ingenieria-electronica-industrial-y-automatica'),(28,'http://www.ubu.es/grado-en-arquitectura-tecnica'),(29,'http://www.ubu.es/grado-en-ingenieria-agroalimentaria-y-del-medio-rural'),(30,'http://www.ubu.es/grado-en-ingenieria-civil'),(31,'http://www.ubu.es/grado-en-ingenieria-de-organizacion-industrial-espanol-y-bilingue-en-ingles'),(32,'http://www.ubu.es/grado-en-ingenieria-de-tecnologias-de-caminos'),(33,'http://www.ubu.es/grado-en-ingenieria-electronica-industrial-y-automatica'),(34,'http://www.ubu.es/grado-oficial-online-en-ingenieria-informatica'),(35,'http://www.ubu.es/grado-en-ingenieria-informatica'),(36,'http://www.ubu.es/grado-en-ingenieria-mecanica'),(37,'http://www.ubu.es/master-universitario-en-patrimonio-y-comunicacion'),(38,'http://www.ubu.es/master-universitario-en-cultura-del-vino-enoturismo-en-la-cuenca-del-duero-semipresencial'),(39,'http://www.ubu.es/master-universitario-en-quimica-avanzada'),(40,'http://www.ubu.es/master-universitario-en-evolucion-humana-interuniversitario'),(41,'http://www.ubu.es/master-universitario-en-electroquimica-ciencia-y-tecnologia-interuniversitario'),(42,'http://www.ubu.es/master-universitario-en-seguridad-y-biotecnologia-alimentarias'),(43,'http://www.ubu.es/curso-de-formacion-pedagogica-y-didactica'),(44,'http://www.ubu.es/master-universitario-en-acceso-la-abogacia'),(45,'http://www.ubu.es/master-universitario-en-administracion-de-empresas-mba'),(46,'http://www.ubu.es/master-universitario-en-comunicacion-y-desarrollo-multimedia'),(47,'http://www.ubu.es/master-universitario-en-contabilidad-avanzada-y-auditoria-de-cuentas-semipresencial'),(48,'http://www.ubu.es/estudios/oferta-de-estudios/masteres-universitarios-oficiales/ciencias-sociales-y-juridicas/master-universitario-en-cooperacion-internacional-para-el-desarrollo-interuniversitario'),(49,'http://cms.ual.es/UAL/estudios/masteres/MASTER7073'),(50,'http://www.ubu.es/master-universitario-en-educacion-y-sociedad-inclusivas'),(51,'http://www.ubu.es/facultad-de-ciencias-economicas-y-empresariales/informacion-academica/masteres/master-universitario-en-investigacion-en-administracion-y-economia-de-la-empresa-interuniversitario'),(52,'http://www.ubu.es/master-universitario-en-profesor-de-educacion-secundaria-obligatoria-y-bachillerato-formacion-profesional-y-ensenanza-de-idiomas'),(53,'http://www.ubu.es/master-universitario-en-ingenieria-informatica'),(54,'http://www.ubu.es/master-universitario-online-en-ingenieria-informatica'),(55,'http://www.ubu.es/master-universitario-en-ingenieria-industrial'),(56,'http://www.ubu.es/master-universitario-en-ingenieria-y-gestion-agrosostenible-semipresencial'),(57,'http://www.ubu.es/master-universitario-en-ingenieria-de-caminos-canales-y-puertos'),(58,'http://www.ubu.es/master-universitario-en-inspeccion-rehabilitacion-y-eficiencia-energetica-en-la-edificacion'),(59,'http://www.ubu.es/master-universitario-online-en-inspeccion-rehabilitacion-y-eficiencia-energetica-en-la-edificacion'),(60,'http://www.ubu.es/master-universitario-en-integridad-y-durabilidad-de-materiales-componentes-y-estructuras-interuniversitario'),(61,'http://www.ubu.es/estudios/oferta-de-estudios/titulos-propios'),(62,'http://www.ubu.es/centro-de-lenguas-modernas-modern-language-centre'),(63,'http://www.ubu.es/cursos-internacionales-cursos-de-espanol'),(64,'http://www.ubu.es/estudios/oferta-de-estudios/licenciaturas-e-ingenierias-extinguir'),(65,'http://www.ubu.es/ubuabierta-cursos-de-extension-universitaria/cursos-ubuabierta/cursos-ubuabierta-presenciales'),(66,'http://www.ubu.es/ubuabierta-cursos-de-extension-universitaria/cursos-ubuabierta/cursos-ubuabierta-online'),(67,'http://www.ubu.es/ubuabierta-cursos-de-extension-universitaria/cursos-0-cursos-complementarios-de-preparacion-para-el-inicio-de-una-carrera-universitaria'),(68,'http://www.ubu.es/cursos-de-verano-de-la-universidad-de-burgos'),(69,'http://www.ubu.es/ubuabierta-cursos-de-extension-universitaria/campus-infantiles'),(70,'http://www.ubu.es/ubuabierta-cursos-de-extension-universitaria/formacion-de-mayores'),(71,'http://www.ubu.es/la-universidad/organizacion/centros-y-departamentos'),(72,'http://www.ubu.es/ayudas-y-becas'),(73,'http://www.ubu.es/vicerrectorado-de-politicas-academicas/ordenacion-academica/calendarios-academicos'),(74,'http://www.ubu.es/acceso-admision-y-matricula/tramites-academicos'),(75,'http://www.ubu.es/vicerrectorado-de-profesorado-y-personal-de-administracion-y-servicios/calidad-de-la-docencia'),(76,'http://www.ubu.es/servicio-de-empleo-universitario-unidad-de-empleo'),(77,'http://www.ubu.es/acceso-admision-y-matricula/admision/formacion-permanente-y-complementaria'),(78,'http://www.ubu.es/vicerrectorado-de-ordenacion-academica-y-calidad/acceso-al-curriculum-abreviado-del-profesorado-de-la-ubu'),(79,'http://www.ubu.es/estudios/oferta-de-estudios'),(80,'http://www.ubu.es/ubuabierta-cursos-de-extension-universitaria'),(81,'http://www.ubu.es/doctorado-en-avances-en-ciencia-y-biotecnologia-alimentarias'),(82,'http://www.ubu.es/doctorado-en-tecnologias-industriales-e-ingenieria-civil'),(83,'http://www.ubu.es/doctorado-en-quimica-avanzada'),(84,'http://www.ubu.es/escuela-de-doctorado/programas-de-doctorado/oferta-de-estudios-de-la-escuela-de-doctorado-curso-academico-20152016/investigacion-en-ingenieria-termodinamica-de-fluidos'),(85,'http://www.ubu.es/doctorado-en-humanidades-y-comunicacion'),(86,'http://www.ubu.es/escuela-de-doctorado/programas-de-doctorado/oferta-de-estudios-de-la-escuela-de-doctorado-curso-academico-20152016/electroquimica-ciencia-y-tecnologia-interuniversitario'),(87,'http://www.ubu.es/escuela-de-doctorado/programas-de-doctorado/oferta-de-estudios-de-la-escuela-de-doctorado-curso-academico-20152016/eficiencia-energetica-y-sostenibilidad-en-ingenieria-y'),(88,'http://www.ubu.es/doctorado-en-educacion'),(89,'http://www.ubu.es/escuela-de-doctorado/programas-de-doctorado/oferta-de-estudios-de-la-escuela-de-doctorado-curso-academico-20152016/economia-de-la-empresa-interuniversitario'),(90,'http://www.ubu.es/doctorado-interuniversitario-de-evolucion-humana-paleoecologia-del-cuaternario-y-tecnicas-geofisicas-aplicadas-la-investigacion'),(91,'http://www.ubu.es/doctorado-en-ciencias-juridicas-economicas-y-sociales'),(92,'http://www.ubu.es/acceso-admision-y-matricula/matricula'),(93,'http://www.ubu.es/acceso-admision-y-matricula/acceso'),(94,'http://www.ubu.es/acceso-admision-y-matricula/acceso/prueba-de-acceso-ensenanzas-universitarias-bachillerato'),(95,'http://www.ubu.es/acceso-admision-y-matricula/acceso/prueba-de-acceso-ensenanzas-universitarias-ciclos-formativos-de-grado-superior'),(96,'http://www.ubu.es/acceso-admision-y-matricula/acceso/pruebas-de-acceso-mayores-de-25-anos'),(97,'http://www.ubu.es/acceso-admision-y-matricula/acceso/pruebas-de-acceso-mayores-de-45-anos'),(98,'http://www.ubu.es/acceso-admision-y-matricula/acceso/acceso-experiencia-laboral-y-profesional-mayores-de-40-anos'),(99,'http://www.ubu.es/acceso-admision-y-matricula/admision'),(100,'http://www.ubu.es/acceso-admision-y-matricula/admision/admision-grado'),(101,'http://www.ubu.es/acceso-admision-y-matricula/admision/admision-master'),(102,'http://www.ubu.es/escuela-de-doctorado/preinscripcion-y-admision-doctorado-rd-992011-curso-academico-20162017'),(103,'http://www.ubu.es/estudios/oferta-de-estudios/titulos-propios-de-posgrado/admision-y-matricula'),(104,'http://www.ubu.es/acceso-admision-y-matricula/admision/formacion-permanente-y-complementaria'),(105,'http://www.ubu.es/acceso-admision-y-matricula/admision/estudiantes-visitantes'),(106,'http://www.ubu.es/acceso-admision-y-matricula/admision/estudiantes-con-estudios-extranjeros'),(107,'http://www.ubu.es/acceso-admision-y-matricula/matricula/matricula-de-grado'),(108,'http://www.ubu.es/acceso-admision-y-matricula/matricula/matricula-de-master'),(109,'http://www.ubu.es/escuela-de-doctorado/matricula-de-doctorado'),(110,'https://limbo.ubu.es/campusvirtual/MatriculaEP/index.asp'),(111,'http://www.ubu.es/acceso-admision-y-matricula/matricula/matricula-de-estudios-de-primer-y-segundo-ciclo'),(112,'http://www.ubu.es/servicio-de-relaciones-internacionales/estudiantes-internacionales/admision-matricula'),(113,'https://tarjeta.ubu.es:18443/UbuAbierta/ppal.jsp'),(114,'https://tarjeta.ubu.es:18443/preinscCV/ppal.jsp'),(115,'http://www.ubu.es/ubuabierta-cursos-de-extension-universitaria/formacion-de-mayores'),(116,'http://www.ubu.es/cursos-internacionales-cursos-de-espanol/cursos/formulario-de-admision-y-matricula'),(117,'http://www.ubu.es/acceso-admision-y-matricula/tramites-academicos/expedicion-de-titulos-y-set'),(118,'http://www.ubu.es/acceso-admision-y-matricula/tramites-academicos/reconocimiento-de-creditos'),(119,'http://www.ubu.es/escuela-de-doctorado/tesis-doctoral'),(120,'http://www.ubu.es/servicio-de-gestion-academica/normativa-en-gestion-academica'),(121,'http://www.ubu.es/servicio-de-gestion-academica/servicios-de-gestion-academica/seguro-escolar'),(122,'http://www.ubu.es/acceso-admision-y-matricula/matricula/matricula-de-grado/seguro'),(123,'http://www.ubu.es/servicio-de-gestion-academica/servicios-de-gestion-academica/tarjeta-universitaria-inteligente'),(124,'http://www.ubu.es/otri-transferencia/grupos-de-investigacion-y-unidades-de-investigacion-consolidada'),(125,'http://www.ubu.es/servicio-de-gestion-de-la-investigacion'),(126,'http://www.ubu.es/otri-transferencia'),(127,'http://www.ubu.es/ubuemprende'),(128,'http://www.ubu.es/parque-cientifico-tecnologico'),(129,'http://www.ubu.es/escuela-de-doctorado'),(130,'http://www.ubu.es/iccram'),(131,'http://ceitriangular.org/'),(132,'http://www.ubu.es/te-interesa/busqueda?keys=&field_term_interest_tid=122&field_portal_father_article_target_id=All'),(133,'http://www.ubu.es/te-interesa/busqueda?keys=&field_term_interest_tid=123&field_portal_father_article_target_id=All'),(134,'https://investigacion.ges.ubu.es/Investigacion/publico/convocatorias/EditarConsultaEntidadCv.do?modulo=cur&inicioNav=1'),(135,'http://www.ubu.es/otri-transferencia/convocatorias-y-ayudas-colaboracion-universidad-empresa/convocatorias-nacionales-universidad-empresa'),(136,'http://www.ubu.es/ubuemprende/convocatorias-prototipo'),(137,'http://www.ubu.es/investigacion/convocatorias-y-ayudas/fondos-de-la-ue'),(138,'http://www3.ubu.es/ubuinvestiga/?page_id=329'),(139,'http://www.ubu.es/escuela-de-doctorado/tesis-doctoral/lectura-y-defensa-de-tesis'),(140,'http://www.ubu.es/otri-transferencia/propiedad-industrial-e-intelectual'),(141,'http://riubu.ubu.es/'),(142,'http://www.ubu.es/investigacion/apoyo-al-investigador/cvn-curriculum-vitae-normalizado'),(143,'http://www.ubu.es/aprendizaje-e-investigacion/sexenios-y-acreditacion'),(144,'http://www.ubu.es/divulgacion-cientifica-ucci-ubu/formacion'),(145,'http://www.ubu.es/divulgacion-cientifica-ucci-ubu/divulgacion-cientifica'),(146,'http://www.ubu.es/divulgacion-cientifica-ucci-ubu/vocaciones-cientificas'),(147,'http://ubuinvestiga.ubu.es/'),(148,'http://www.ubu.es/servicio-de-relaciones-internacionales'),(149,'http://www.ubu.es/becas-de-movilidad?keys=&field_portal_father_article_target_id=All&field_term_interest_tid=148'),(150,'http://www.ubu.es/servicio-de-relaciones-internacionales/estudiantes-ubu-internacional'),(151,'http://www.ubu.es/servicio-de-relaciones-internacionales/paspdi-internacional'),(152,'http://www.ubu.es/cursos-internacionales-cursos-de-espanol/dele-diploma-de-espanol-como-lengua-extranjera'),(153,'http://www.ubu.es/cursos-internacionales-cursos-de-espanol/ccse-conocimientos-constitucionales-y-socioculturales-de-espana'),(154,'http://www.ubu.es/centro-de-lenguas-modernas-modern-language-centre/acreditacion-de-idiomas'),(155,'http://www.ubu.es/centro-de-cooperacion-y-accion-solidaria'),(156,'http://www.ubu.es/centro-de-cooperacion-y-accion-solidaria/informacion-general/convenios'),(157,'http://www.ubu.es/becas-de-cooperacion'),(158,'http://ubu-bangalore.blogspot.com.es/'),(159,'http://www.ubu.es/centro-de-cooperacion-y-accion-solidaria/becas-convocatorias-y-ayudas/ubu-ayudas-para-acciones-de-cooperacion-universitaria-al-desarrollo-para-el-pdipas-2014'),(160,'https://www.youtube.com/watch?v=kF2-pjk1G4g&noredirect=1'),(161,'http://www.ave.cvc.cervantes.es/login.asp?numtira=4&s='),(162,'http://www.ubu.es/servicio-de-informacion-y-extension-universitaria/servicios-unidad-de-informacion/alojamiento-para-universitarios'),(163,'http://www3.ubu.es/ubuespacio/'),(164,'http://www.ubu.es/servicio-de-informacion-y-orientacion-en-salud-joven-universitaria'),(165,'http://www.ubu.es/caubu-consejo-de-alumnos-de-la-universidad-de-burgos'),(166,'http://www.ubu.es/defensor-universitario'),(167,'http://www.ubu.es/servicio-de-informacion-y-extension-universitaria/servicios-unidad-de-extension-universitaria/ligas-de-debate'),(168,'http://www.ubu.es/unidad-de-atencion-la-diversidad'),(169,'http://www.ubu.es/servicio-de-empleo-universitario-unidad-de-empleo/orientacion-coaching-y-formacion/orientacion-vocacional-y-profesional'),(170,'http://www.ubu.es/servicio-de-estudiantes-y-extension-universitaria/servicios-unidad-de-informacion/orientacion-y-tutoria-de-apoyo'),(171,'http://www.ubu.es/voluntariado'),(172,'http://www.ubu.es/portal-de-transparencia'),(173,'http://www.ubu.es/registro-e-informacion/tablon-oficial-de-la-ubu'),(174,'http://www.ubu.es/normativa'),(175,'https://sede.ubu.es/opencms/opencms/es'),(176,'http://www.ubu.es/empresas-e-instituciones'),(177,'http://www.ubu.es/trabaja-en-la-ubu'),(178,'http://www.ubu.es/la-universidad/la-ubu/catedras-de-la-universidad-de-burgos'),(179,'http://www.ubu.es/la-universidad/la-ubu'),(180,'http://www.ubu.es/la-universidad/organizacion/organos-universitarios'),(181,'http://www.ubu.es/la-universidad/organizacion/centros-y-departamentos'),(182,'http://www.ubu.es/la-universidad/organizacion/servicios-universitarios'),(183,'http://www.fundacionubu.com/es/portada/'),(184,'http://www.ubu.es/la-universidad/organizacion/organos-universitarios/organos-de-representacion'),(185,'http://www.ubu.es/la-universidad/organizacion/directorio'),(186,'http://www.ubu.es/cultura'),(187,'http://www.ubu.es/deportes'),(188,'http://www.ubu.es/servicio-de-informatica-y-comunicaciones/catalogo-de-servicios/red-wifi-vpn/wi-fi'),(189,'https://ubunet.ubu.es/co/pag/index.htm'),(190,'https://ubuvirtual.ubu.es/'),(191,'http://www.ubu.es/servicio-de-informacion-y-extension-universitaria/servicios-unidad-de-informacion/asociaciones-de-estudiantes'),(192,'http://www.ubu.es/ubuventajas'),(193,'https://secretariavirtual.ubu.es'),(194,'http://www.ubu.es/biblioteca'),(195,'http://www.aaaaubu.es/'),(196,'http://www.ubu.es/noticias'),(197,'http://www.ubu.es/agenda'),(198,'http://www.ubu.es/te-interesa'),(199,'https://login.microsoftonline.com'),(200,'https://www.ubu.es/user?destination=node/46604'),(201,'http://riubu.ubu.es/'),(202,'http://www.ubu.es/archivo-general'),(203,'http://www.ubu.es/normativa'),(204,'http://www.ubu.es/normativa/normativa-de-gobierno-de-la-universidad/boletin-oficial-de-la-ubu-boubu'),(205,'http://www.ubu.es/direccion-de-comunicacion-dircom'),(206,'http://www.ubu.es/gabinete-de-comunicacion'),(207,'http://www3.ubu.es/actualidad/'),(208,'http://www3.ubu.es/actualidad/tvubu/'),(209,'http://www3.ubu.es/actualidad/uburadio/'),(210,'http://www.ubu.es/unidad-de-protocolo'),(211,'http://www.ubu.es/divulgacion-cientifica-ucci-ubu'),(212,'http://www.ubu.es/servicio-de-publicaciones-e-imagen-institucional'),(213,'https://www3.ubu.es/buscapersonas/'),(214,'http://www.ubu.es/politica-de-privacidad'),(215,'https://www.ubu.es/user'),(216,'https://twitter.com/UBUEstudiantes'),(217,'https://www.facebook.com/Universidad.Burgos'),(218,'https://www.instagram.com/universidadburgos/'),(219,'https://www.youtube.com/user/tvubu'),(220,'https://play.google.com/store/apps/details?id=com.pocketuniversity.ubu'),(221,'http://www.ubu.es/unidad-de-protocolo/actos-academicos-solemnes/doctorado-honoris-causa');
/*!40000 ALTER TABLE `casesolution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `frases`
--

DROP TABLE IF EXISTS `frases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frases` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `frase` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `frases`
--

LOCK TABLES `frases` WRITE;
/*!40000 ALTER TABLE `frases` DISABLE KEYS */;
INSERT INTO `frases` VALUES (1,'Hola soy UBUassistant, ¿en qué puedo ayudarte?'),(2,'Muy buenas, me llamo UBUassistant y estoy lista para ayudarte ¡adelante!'),(3,'Hola, si necesitas ayuda aquí me tienes, aquí tienes a UBUassistant'),(4,'Bienvenido, ante cualquier duda de tu universidad UBUassistant al rescate'),(5,'UBUassistant te da la bienvenida, ¿Empezamos?, pregunta cualquier duda sobre la UBU'),(6,'Tal vez esto te ayude'),(7,'Prueba con la siguiente información'),(8,'Espero que esto te ayude'),(9,'Esto es lo que he encontrado al respecto'),(10,'Aquí tienes mis respuestas');
/*!40000 ALTER TABLE `frases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logger`
--

DROP TABLE IF EXISTS `logger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logger` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userid` varchar(1000) NOT NULL,
  `fecha` datetime NOT NULL,
  `keyWord1` varchar(1000) DEFAULT NULL,
  `keyWord2` varchar(1000) DEFAULT NULL,
  `keyWord3` varchar(1000) DEFAULT NULL,
  `keyWord4` varchar(1000) DEFAULT NULL,
  `keyWord5` varchar(1000) DEFAULT NULL,
  `categoria` varchar(1000) DEFAULT NULL,
  `respuesta` varchar(1000) DEFAULT NULL,
  `num_busquedas` int(10) NOT NULL,
  `num_votos` int(10) DEFAULT NULL,
  `valoracion_total` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logger`
--

LOCK TABLES `logger` WRITE;
/*!40000 ALTER TABLE `logger` DISABLE KEYS */;
INSERT INTO `logger` VALUES (7,'170601180937821','2017-06-01 18:09:41','becas',NULL,NULL,NULL,NULL,'estudios','http://www.ubu.es/ayudas-y-becas',1,1,5),(8,'170601180937821','2017-06-01 18:09:43','becas internacionales',NULL,NULL,NULL,NULL,'internacional','http://www.ubu.es/becas-de-cooperacion',1,1,5),(9,'170601180937821','2017-06-01 18:09:48','esqui',NULL,NULL,NULL,NULL,'universidad','http://www.ubu.es/deportes',1,1,5),(10,'170601180937821','2017-06-01 18:09:55','informatica (master, online)',NULL,NULL,NULL,NULL,'estudios','http://www.ubu.es/master-universitario-online-en-ingenieria-informatica',1,1,5),(11,'170601180937821','2017-06-01 18:10:04','verano',NULL,NULL,NULL,NULL,'estudios','http://www.ubu.es/cursos-de-verano-de-la-universidad-de-burgos',1,0,0);
/*!40000 ALTER TABLE `logger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saludos`
--

DROP TABLE IF EXISTS `saludos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saludos` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `saludo` varchar(1000) NOT NULL,
  `respuesta` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saludos`
--

LOCK TABLES `saludos` WRITE;
/*!40000 ALTER TABLE `saludos` DISABLE KEYS */;
INSERT INTO `saludos` VALUES (1,'Hola','Hola, estoy preparada para responder, adelánte'),(2,'Buenos dias','Buenos días, ponme a prueba con tus preguntas'),(3,'Buenas tardes','Buenas tardes, ¿tienes alguna duda? No dudes en preguntarme'),(4,'Buenas noches','Buenas noches, ¿qué te apetece preguntar?'),(5,'Buenas','Muy buenas serán si te contesto correctamente'),(6,'Adios','¡Adiós!, espero haberte servido de ayuda'),(7,'Hasta luego','Hasta luego, espero que volvamos a vernos'),(8,'Ciao','¡Arrivederci!'),(9,'Eres una pesada','No te pongas así, seguro que podemos tener una conversación productiva'),(10,'Callate','Lo siento, solo intentaba ayudar');
/*!40000 ALTER TABLE `saludos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-01 18:10:29
