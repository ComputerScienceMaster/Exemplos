-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema JusHelp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema JusHelp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `JusHelp` DEFAULT CHARACTER SET utf8 ;
USE `JusHelp` ;

-- -----------------------------------------------------
-- Table `JusHelp`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JusHelp`.`User` (
  `loginUser` VARCHAR(200) NOT NULL,
  `passwordUser` VARCHAR(250) NOT NULL,
  `fullNameUser` VARCHAR(200) NOT NULL,
  `generalRegister` VARCHAR(45) NULL,
  `rgState` VARCHAR(45) NULL,
  `rgEmiter` VARCHAR(45) NULL,
  `emailUser` VARCHAR(200) NOT NULL,
  `oab` VARCHAR(60) NULL,
  `maritalStatus` VARCHAR(100) NULL,
  `phone` VARCHAR(60) NULL,
  `birthDate` DATE NULL,
  `CEP` VARCHAR(45) NULL,
  `city` VARCHAR(155) NULL,
  `state` VARCHAR(45) NULL,
  `address1` VARCHAR(200) NULL,
  `address2` VARCHAR(200) NULL,
  `complement` VARCHAR(100) NULL,
  `nationality` VARCHAR(45) NULL,
  `imageUser` MEDIUMBLOB NULL,
  PRIMARY KEY (`loginUser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JusHelp`.`Administrator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JusHelp`.`Administrator` (
  `loginAdministrator` VARCHAR(200) NOT NULL,
  `passwordAdministrator` VARCHAR(200) NULL,
  PRIMARY KEY (`loginAdministrator`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JusHelp`.`Client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JusHelp`.`Client` (
  `cpfRegister` VARCHAR(20) NOT NULL,
  `fullName` VARCHAR(500) NULL,
  `nationality` VARCHAR(200) NULL,
  `birthDate` DATE NULL,
  `MaritalStatus` VARCHAR(100) NULL,
  `job` VARCHAR(350) NULL,
  `ctps` VARCHAR(45) NULL,
  `generalRegister` VARCHAR(20) NULL,
  `rgState` VARCHAR(50) NULL,
  `rgEmiter` VARCHAR(50) NULL,
  `district` VARCHAR(200) NULL,
  `CEP` VARCHAR(35) NULL,
  `Address1` VARCHAR(150) NULL,
  `Address2` VARCHAR(150) NULL,
  `city` VARCHAR(200) NULL,
  `state` VARCHAR(200) NULL,
  `county` VARCHAR(200) NULL,
  `votersTitle` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NULL,
  `email` VARCHAR(100) NULL,
  `User_loginUser` VARCHAR(200) NOT NULL,
  INDEX `fk_Client_User1_idx` (`User_loginUser` ASC),
  PRIMARY KEY (`cpfRegister`),
  CONSTRAINT `fk_Client_User1`
    FOREIGN KEY (`User_loginUser`)
    REFERENCES `JusHelp`.`User` (`loginUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JusHelp`.`Company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JusHelp`.`Company` (
  `cnpj` VARCHAR(45) NOT NULL,
  `companyName` VARCHAR(500) NOT NULL,
  `foundationDate` DATE NULL,
  `CEP` VARCHAR(45) NULL,
  `city` VARCHAR(155) NULL,
  `state` VARCHAR(45) NULL,
  `email` VARCHAR(25) NULL,
  `phone` VARCHAR(20) NULL,
  `User_loginUser` VARCHAR(200) NOT NULL,
  INDEX `fk_Company_User1_idx` (`User_loginUser` ASC),
  PRIMARY KEY (`cnpj`),
  CONSTRAINT `fk_Company_User1`
    FOREIGN KEY (`User_loginUser`)
    REFERENCES `JusHelp`.`User` (`loginUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JusHelp`.`ParnerAttorney`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JusHelp`.`ParnerAttorney` (
  `cpfRegister` VARCHAR(45) NOT NULL,
  `fullName` VARCHAR(500) NULL,
  `generalRegister` VARCHAR(45) NULL,
  `birthDate` DATE NULL,
  `oab` VARCHAR(60) NULL,
  `CEP` VARCHAR(45) NULL,
  `User_loginUser` VARCHAR(200) NOT NULL,
  INDEX `fk_ParnerAttorney_User1_idx` (`User_loginUser` ASC),
  PRIMARY KEY (`cpfRegister`),
  CONSTRAINT `fk_ParnerAttorney_User1`
    FOREIGN KEY (`User_loginUser`)
    REFERENCES `JusHelp`.`User` (`loginUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JusHelp`.`Tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JusHelp`.`Tag` (
  `idTag` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  `level` INT NULL,
  `parentId` INT NULL,
  PRIMARY KEY (`idTag`),
  INDEX `fk_Part_Part1_idx` (`parentId` ASC),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC),
  CONSTRAINT `fk_Part_Part1`
    FOREIGN KEY (`parentId`)
    REFERENCES `JusHelp`.`Tag` (`idTag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JusHelp`.`DocumentModel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JusHelp`.`DocumentModel` (
  `idDocumentModel` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NULL,
  `summary` VARCHAR(300) NULL,
  `corpus` LONGTEXT NULL,
  `Tag_idTag` INT NOT NULL,
  PRIMARY KEY (`idDocumentModel`),
  INDEX `fk_DocumentModel_Tag1_idx` (`Tag_idTag` ASC),
  CONSTRAINT `fk_DocumentModel_Tag1`
    FOREIGN KEY (`Tag_idTag`)
    REFERENCES `JusHelp`.`Tag` (`idTag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JusHelp`.`DocumentPart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JusHelp`.`DocumentPart` (
  `idDocumentPart` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NULL,
  `summary` VARCHAR(500) NULL,
  `corpus` LONGTEXT NULL,
  `Part_idPart` INT NOT NULL,
  PRIMARY KEY (`idDocumentPart`),
  INDEX `fk_DocumentPart_Part1_idx` (`Part_idPart` ASC),
  CONSTRAINT `fk_DocumentPart_Part1`
    FOREIGN KEY (`Part_idPart`)
    REFERENCES `JusHelp`.`Tag` (`idTag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JusHelp`.`Trial`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JusHelp`.`Trial` (
  `idTrial` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `initialDate` DATE NULL,
  `expectedFinalDate` DATE NULL,
  `User_loginUser` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`idTrial`),
  INDEX `fk_Trial_User1_idx` (`User_loginUser` ASC),
  CONSTRAINT `fk_Trial_User1`
    FOREIGN KEY (`User_loginUser`)
    REFERENCES `JusHelp`.`User` (`loginUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JusHelp`.`Document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JusHelp`.`Document` (
  `idDocument` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(1000) NULL,
  `summary` VARCHAR(2000) NULL,
  `Corpus` LONGTEXT NULL,
  `Tag_idTag` INT NOT NULL,
  `Client_cpfRegister` VARCHAR(20) NULL,
  `Company_cnpj` VARCHAR(45) NULL,
  `DocumentModel_idDocumentModel` INT NOT NULL,
  `Trial_idTrial` INT NULL,
  PRIMARY KEY (`idDocument`),
  INDEX `fk_Document_Tag1_idx` (`Tag_idTag` ASC),
  INDEX `fk_Document_Client1_idx` (`Client_cpfRegister` ASC),
  INDEX `fk_Document_Company1_idx` (`Company_cnpj` ASC),
  INDEX `fk_Document_DocumentModel1_idx` (`DocumentModel_idDocumentModel` ASC),
  INDEX `fk_Document_Trial1_idx` (`Trial_idTrial` ASC),
  CONSTRAINT `fk_Document_Tag1`
    FOREIGN KEY (`Tag_idTag`)
    REFERENCES `JusHelp`.`Tag` (`idTag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Document_Client1`
    FOREIGN KEY (`Client_cpfRegister`)
    REFERENCES `JusHelp`.`Client` (`cpfRegister`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Document_Company1`
    FOREIGN KEY (`Company_cnpj`)
    REFERENCES `JusHelp`.`Company` (`cnpj`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Document_DocumentModel1`
    FOREIGN KEY (`DocumentModel_idDocumentModel`)
    REFERENCES `JusHelp`.`DocumentModel` (`idDocumentModel`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Document_Trial1`
    FOREIGN KEY (`Trial_idTrial`)
    REFERENCES `JusHelp`.`Trial` (`idTrial`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JusHelp`.`Variable`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JusHelp`.`Variable` (
  `idVariable` INT NOT NULL AUTO_INCREMENT,
  `variableName` VARCHAR(150) NULL,
  `variableContent` VARCHAR(150) NULL,
  `Trial_idTrial` INT NOT NULL,
  PRIMARY KEY (`idVariable`),
  INDEX `fk_Variable_Trial1_idx` (`Trial_idTrial` ASC),
  CONSTRAINT `fk_Variable_Trial1`
    FOREIGN KEY (`Trial_idTrial`)
    REFERENCES `JusHelp`.`Trial` (`idTrial`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
