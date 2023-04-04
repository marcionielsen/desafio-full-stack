-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema dbdesafiofullstack
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dbdesafiofullstack
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dbdesafiofullstack` DEFAULT CHARACTER SET utf8 ;
USE `dbdesafiofullstack` ;

-- -----------------------------------------------------
-- Table `dbdesafiofullstack`.`EMPRESA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbdesafiofullstack`.`EMPRESA` (
  `ID_EMPRESA` INT NOT NULL AUTO_INCREMENT,
  `CNPJ` VARCHAR(50) NOT NULL,
  `NOME_FANTASIA` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID_EMPRESA`),
  UNIQUE INDEX `CNPJ_UNIQUE` (`CNPJ` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbdesafiofullstack`.`ENDERECO_EMPRESA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbdesafiofullstack`.`ENDERECO_EMPRESA` (
  `ID_ENDERECO_EMPRESA` INT NOT NULL AUTO_INCREMENT,
  `ID_EMPRESA` INT NOT NULL,
  `CEP` VARCHAR(10) NULL,
  `UF` VARCHAR(2) NULL,
  `CIDADE` VARCHAR(100) NULL,
  `BAIRRO` VARCHAR(100) NULL,
  `LOGRADOURO` VARCHAR(100) NULL,
  `NUMERO` VARCHAR(5) NULL,
  `COMPLEMENTO` VARCHAR(100) NULL,
  PRIMARY KEY (`ID_ENDERECO_EMPRESA`),
  INDEX `fk_ENDERECO_EMPRESA_EMPRESA_idx` (`ID_EMPRESA` ASC),
  CONSTRAINT `fk_ENDERECO_EMPRESA_EMPRESA`
    FOREIGN KEY (`ID_EMPRESA`)
    REFERENCES `dbdesafiofullstack`.`EMPRESA` (`ID_EMPRESA`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbdesafiofullstack`.`FORNECEDOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbdesafiofullstack`.`FORNECEDOR` (
  `ID_FORNECEDOR` INT NOT NULL AUTO_INCREMENT,
  `CNPJ_CPF` VARCHAR(50) NOT NULL,
  `TIPO_PESSOA` CHAR(1) NOT NULL,
  `NUMERO_RG` VARCHAR(50) NULL,
  `DATA_NASCIMENTO` DATETIME NULL,
  `NOME` VARCHAR(100) NOT NULL,
  `EMAIL` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`ID_FORNECEDOR`),
  UNIQUE INDEX `CNPJ_CPF_UNIQUE` (`CNPJ_CPF` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbdesafiofullstack`.`ENDERECO_FORNECEDOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbdesafiofullstack`.`ENDERECO_FORNECEDOR` (
  `ID_ENDERECO_FORNECEDOR` INT NOT NULL AUTO_INCREMENT,
  `ID_FORNECEDOR` INT NOT NULL,
  `CEP` VARCHAR(10) NULL,
  `UF` VARCHAR(2) NULL,
  `CIDADE` VARCHAR(100) NULL,
  `BAIRRO` VARCHAR(100) NULL,
  `LOGRADOURO` VARCHAR(100) NULL,
  `NUMERO` VARCHAR(5) NULL,
  `COMPLEMENTO` VARCHAR(100) NULL,
  PRIMARY KEY (`ID_ENDERECO_FORNECEDOR`),
  INDEX `fk_ENDERECO_FORNECEDOR_FORNECEDOR1_idx` (`ID_FORNECEDOR` ASC),
  CONSTRAINT `fk_ENDERECO_FORNECEDOR_FORNECEDOR1`
    FOREIGN KEY (`ID_FORNECEDOR`)
    REFERENCES `dbdesafiofullstack`.`FORNECEDOR` (`ID_FORNECEDOR`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dbdesafiofullstack`.`EMPRESA_FORNECEDOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dbdesafiofullstack`.`EMPRESA_FORNECEDOR` (
  `ID_EMPRESA` INT NOT NULL,
  `ID_FORNECEDOR` INT NOT NULL,
  PRIMARY KEY (`ID_EMPRESA`, `ID_FORNECEDOR`),
  INDEX `fk_EMPRESA_has_FORNECEDOR_FORNECEDOR1_idx` (`ID_FORNECEDOR` ASC),
  INDEX `fk_EMPRESA_has_FORNECEDOR_EMPRESA1_idx` (`ID_EMPRESA` ASC),
  CONSTRAINT `fk_EMPRESA_has_FORNECEDOR_EMPRESA1`
    FOREIGN KEY (`ID_EMPRESA`)
    REFERENCES `dbdesafiofullstack`.`EMPRESA` (`ID_EMPRESA`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EMPRESA_has_FORNECEDOR_FORNECEDOR1`
    FOREIGN KEY (`ID_FORNECEDOR`)
    REFERENCES `dbdesafiofullstack`.`FORNECEDOR` (`ID_FORNECEDOR`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
