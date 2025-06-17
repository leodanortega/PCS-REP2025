-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: construccion
-- ------------------------------------------------------
-- Server version	8.4.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `documento_final`
--

DROP TABLE IF EXISTS `documento_final`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documento_final` (
  `idDocumento` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  `fecha` date NOT NULL,
  `tipoDocumento` int NOT NULL,
  PRIMARY KEY (`idDocumento`),
  KEY `fk_documentoFinal_tipoDocumentoFinal_idx` (`tipoDocumento`),
  CONSTRAINT `fk_documentoFinal_tipoDocumentoFinal` FOREIGN KEY (`tipoDocumento`) REFERENCES `tipo_documento_final` (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento_final`
--

LOCK TABLES `documento_final` WRITE;
/*!40000 ALTER TABLE `documento_final` DISABLE KEYS */;
/*!40000 ALTER TABLE `documento_final` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documento_inicial`
--

DROP TABLE IF EXISTS `documento_inicial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documento_inicial` (
  `idDocumento` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  `tipoDocumento` int NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`idDocumento`),
  KEY `fk_documentoInicial_tipoDocumentoInicial_idx` (`tipoDocumento`),
  CONSTRAINT `fk_documentoInicial_tipoDocumentoInicial` FOREIGN KEY (`tipoDocumento`) REFERENCES `tipo_documento_inicial` (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento_inicial`
--

LOCK TABLES `documento_inicial` WRITE;
/*!40000 ALTER TABLE `documento_inicial` DISABLE KEYS */;
/*!40000 ALTER TABLE `documento_inicial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrega_documento`
--

DROP TABLE IF EXISTS `entrega_documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrega_documento` (
  `idEntrega` int NOT NULL,
  `idDocumento` int NOT NULL,
  `tipoDocumento` enum('Inicial','Final') NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  `idExpediente` int NOT NULL,
  PRIMARY KEY (`idEntrega`),
  KEY `fk_entregaDocumento_expediente_idx` (`idExpediente`),
  CONSTRAINT `fk_entregaDocumento_expediente` FOREIGN KEY (`idExpediente`) REFERENCES `expediente` (`idExpediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrega_documento`
--

LOCK TABLES `entrega_documento` WRITE;
/*!40000 ALTER TABLE `entrega_documento` DISABLE KEYS */;
/*!40000 ALTER TABLE `entrega_documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiante` (
  `idUsuario` int NOT NULL,
  `calificacion` decimal(2,0) NOT NULL,
  `creditos` int NOT NULL,
  `estado` varchar(45) NOT NULL,
  `semestre` int NOT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `idUsuario_idx` (`idUsuario`),
  CONSTRAINT `fk_estudiante_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` VALUES (1,80,250,'Activo',9);
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluacion_presentacion`
--

DROP TABLE IF EXISTS `evaluacion_presentacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluacion_presentacion` (
  `idEvaluacion` int NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `fecha` date NOT NULL,
  `calificacion` decimal(2,0) NOT NULL,
  `idExpediente` int NOT NULL,
  PRIMARY KEY (`idEvaluacion`),
  KEY `fk_evaluacionPresentacion_expediente_idx` (`idExpediente`),
  CONSTRAINT `fk_evaluacionPresentacion_expediente` FOREIGN KEY (`idExpediente`) REFERENCES `expediente` (`idExpediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluacion_presentacion`
--

LOCK TABLES `evaluacion_presentacion` WRITE;
/*!40000 ALTER TABLE `evaluacion_presentacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluacion_presentacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expediente`
--

DROP TABLE IF EXISTS `expediente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expediente` (
  `idExpediente` int NOT NULL AUTO_INCREMENT,
  `idEstudiante` int NOT NULL,
  `idGrupoEE` int NOT NULL,
  `idPeriodo` int NOT NULL,
  `califaciones` varchar(45) NOT NULL,
  `horas` varchar(45) NOT NULL,
  `informe` varchar(45) NOT NULL,
  PRIMARY KEY (`idExpediente`),
  KEY `fk_expediente_estudiante_idx` (`idEstudiante`),
  KEY `fk_expediente_grupoee_idx` (`idGrupoEE`),
  KEY `fk_expediente_periodo_idx` (`idPeriodo`),
  CONSTRAINT `fk_expediente_estudiante` FOREIGN KEY (`idEstudiante`) REFERENCES `estudiante` (`idUsuario`),
  CONSTRAINT `fk_expediente_grupoee` FOREIGN KEY (`idGrupoEE`) REFERENCES `grupoee` (`NRC`),
  CONSTRAINT `fk_expediente_periodo` FOREIGN KEY (`idPeriodo`) REFERENCES `periodo` (`idPeriodo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expediente`
--

LOCK TABLES `expediente` WRITE;
/*!40000 ALTER TABLE `expediente` DISABLE KEYS */;
/*!40000 ALTER TABLE `expediente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expediente_proyecto`
--

DROP TABLE IF EXISTS `expediente_proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expediente_proyecto` (
  `idExpediente` int NOT NULL,
  `idProyecto` int NOT NULL,
  KEY `fk_expedienteProyecto_expediente_idx` (`idExpediente`),
  KEY `fk_expedienteProyecto_proyecto_idx` (`idProyecto`),
  KEY `llave` (`idExpediente`,`idProyecto`),
  CONSTRAINT `fk_expedienteProyecto_expediente` FOREIGN KEY (`idExpediente`) REFERENCES `expediente` (`idExpediente`),
  CONSTRAINT `fk_expedienteProyecto_proyecto` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expediente_proyecto`
--

LOCK TABLES `expediente_proyecto` WRITE;
/*!40000 ALTER TABLE `expediente_proyecto` DISABLE KEYS */;
/*!40000 ALTER TABLE `expediente_proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experiencia_educativa`
--

DROP TABLE IF EXISTS `experiencia_educativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `experiencia_educativa` (
  `idExperiencia` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `creditos` int NOT NULL,
  `horario` varchar(45) NOT NULL,
  PRIMARY KEY (`idExperiencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experiencia_educativa`
--

LOCK TABLES `experiencia_educativa` WRITE;
/*!40000 ALTER TABLE `experiencia_educativa` DISABLE KEYS */;
/*!40000 ALTER TABLE `experiencia_educativa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupoee`
--

DROP TABLE IF EXISTS `grupoee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupoee` (
  `NRC` int NOT NULL,
  `idProfesor` int NOT NULL,
  `idExperiencia` int NOT NULL,
  PRIMARY KEY (`NRC`),
  KEY `fk_grupoee_profesor_idx` (`idProfesor`),
  KEY `fk_grupoee_experienciaEducativa_idx` (`idExperiencia`),
  CONSTRAINT `fk_grupoee_experienciaEducativa` FOREIGN KEY (`idExperiencia`) REFERENCES `experiencia_educativa` (`idExperiencia`),
  CONSTRAINT `fk_grupoee_profesor` FOREIGN KEY (`idProfesor`) REFERENCES `profesor` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupoee`
--

LOCK TABLES `grupoee` WRITE;
/*!40000 ALTER TABLE `grupoee` DISABLE KEYS */;
/*!40000 ALTER TABLE `grupoee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizacion_vinculada`
--

DROP TABLE IF EXISTS `organizacion_vinculada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizacion_vinculada` (
  `idOrganizacion` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `correo` varchar(45) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `RFC` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  PRIMARY KEY (`idOrganizacion`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizacion_vinculada`
--

LOCK TABLES `organizacion_vinculada` WRITE;
/*!40000 ALTER TABLE `organizacion_vinculada` DISABLE KEYS */;
INSERT INTO `organizacion_vinculada` VALUES (1,'Universidad Veracruzana','uv@uv.mx','Universidad Veracruzana Xalapa','18927389273','123978098123','Privada'),(2,'asdas','dasda','sdasd','asda','12313','Pública'),(3,'asd','asdasdasd','asdasd','asdasd','123132','Pública');
/*!40000 ALTER TABLE `organizacion_vinculada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodo`
--

DROP TABLE IF EXISTS `periodo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `periodo` (
  `idPeriodo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  PRIMARY KEY (`idPeriodo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodo`
--

LOCK TABLES `periodo` WRITE;
/*!40000 ALTER TABLE `periodo` DISABLE KEYS */;
/*!40000 ALTER TABLE `periodo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesor`
--

DROP TABLE IF EXISTS `profesor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesor` (
  `idUsuario` int NOT NULL,
  PRIMARY KEY (`idUsuario`),
  CONSTRAINT `fk_profesor_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesor`
--

LOCK TABLES `profesor` WRITE;
/*!40000 ALTER TABLE `profesor` DISABLE KEYS */;
/*!40000 ALTER TABLE `profesor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyecto`
--

DROP TABLE IF EXISTS `proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proyecto` (
  `idProyecto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `metodologia` varchar(45) NOT NULL,
  `espacios` varchar(45) NOT NULL,
  `departamento` varchar(45) NOT NULL,
  `idResponsable` int NOT NULL,
  `idOrganizacion` int NOT NULL,
  PRIMARY KEY (`idProyecto`),
  KEY `fk_proyecto_responsableProyecto_idx` (`idResponsable`),
  KEY `fk_proyecto_organizacionVinculada_idx` (`idOrganizacion`),
  CONSTRAINT `fk_proyecto_organizacionVinculada` FOREIGN KEY (`idOrganizacion`) REFERENCES `organizacion_vinculada` (`idOrganizacion`),
  CONSTRAINT `fk_proyecto_responsableProyecto` FOREIGN KEY (`idResponsable`) REFERENCES `responsable_proyecto` (`idResponsable`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyecto`
--

LOCK TABLES `proyecto` WRITE;
/*!40000 ALTER TABLE `proyecto` DISABLE KEYS */;
/*!40000 ALTER TABLE `proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporte`
--

DROP TABLE IF EXISTS `reporte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte` (
  `idReporte` int NOT NULL AUTO_INCREMENT,
  `calificacion` decimal(10,0) NOT NULL,
  `estado` varchar(45) NOT NULL,
  `fecha` date NOT NULL,
  `horas` int NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `idTipoReporte` int NOT NULL,
  `idExpediente` int NOT NULL,
  PRIMARY KEY (`idReporte`),
  KEY `fk_reporte_tipoReporte_idx` (`idTipoReporte`),
  KEY `fk_reporte_expediente_idx` (`idExpediente`),
  CONSTRAINT `fk_reporte_expediente` FOREIGN KEY (`idExpediente`) REFERENCES `expediente` (`idExpediente`),
  CONSTRAINT `fk_reporte_tipoReporte` FOREIGN KEY (`idTipoReporte`) REFERENCES `tipo_reporte` (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte`
--

LOCK TABLES `reporte` WRITE;
/*!40000 ALTER TABLE `reporte` DISABLE KEYS */;
/*!40000 ALTER TABLE `reporte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `responsable_proyecto`
--

DROP TABLE IF EXISTS `responsable_proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `responsable_proyecto` (
  `idResponsable` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apePaterno` varchar(45) NOT NULL,
  `apeMaterno` varchar(45) NOT NULL,
  `correo` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `puesto` varchar(45) NOT NULL,
  `idOrganizacion` int NOT NULL,
  PRIMARY KEY (`idResponsable`),
  KEY `fk_responsableProyecto_organizacionVinculada_idx` (`idOrganizacion`),
  CONSTRAINT `fk_responsableProyecto_organizacionVinculada` FOREIGN KEY (`idOrganizacion`) REFERENCES `organizacion_vinculada` (`idOrganizacion`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responsable_proyecto`
--

LOCK TABLES `responsable_proyecto` WRITE;
/*!40000 ALTER TABLE `responsable_proyecto` DISABLE KEYS */;
INSERT INTO `responsable_proyecto` VALUES (1,'asd','asd','asd','asd','asd','asd',1);
/*!40000 ALTER TABLE `responsable_proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_documento_final`
--

DROP TABLE IF EXISTS `tipo_documento_final`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_documento_final` (
  `idTipo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_documento_final`
--

LOCK TABLES `tipo_documento_final` WRITE;
/*!40000 ALTER TABLE `tipo_documento_final` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_documento_final` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_documento_inicial`
--

DROP TABLE IF EXISTS `tipo_documento_inicial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_documento_inicial` (
  `idTipo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_documento_inicial`
--

LOCK TABLES `tipo_documento_inicial` WRITE;
/*!40000 ALTER TABLE `tipo_documento_inicial` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_documento_inicial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_reporte`
--

DROP TABLE IF EXISTS `tipo_reporte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_reporte` (
  `idTipo` int NOT NULL,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idTipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_reporte`
--

LOCK TABLES `tipo_reporte` WRITE;
/*!40000 ALTER TABLE `tipo_reporte` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_reporte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apePaterno` varchar(45) DEFAULT NULL,
  `apeMaterno` varchar(45) DEFAULT NULL,
  `correo` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `rol` varchar(45) NOT NULL,
  `identificador` varchar(45) NOT NULL,
  `contrasenia` varchar(45) NOT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Leonardo Daniel','Ortega','Teoba','leonardo@gmail.com','2283232694','estudiante','S23014097','081205'),(2,'Pyra','The','Aegis','pyra@gmail.com','1237987978','coordinador','Pyra','081205');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-16 21:53:36
