-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema tasks_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tasks_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tasks_db` DEFAULT CHARACTER SET utf8 ;
USE `tasks_db` ;

-- -----------------------------------------------------
-- Table `tasks_db`.`completed_task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tasks_db`.`completed_task` (
  `completed_task_id` INT(11) NOT NULL,
  `task_name` VARCHAR(200) NOT NULL,
  `complete_date` TIMESTAMP NOT NULL,
  `priority` INT(11) NOT NULL,
  `overdue` TINYINT(1) NOT NULL,
  PRIMARY KEY (`completed_task_id`),
  UNIQUE INDEX `idcompleted_task_UNIQUE` (`completed_task_id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tasks_db`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tasks_db`.`task` (
  `task_id` INT(11) NOT NULL,
  `task_name` VARCHAR(200) NOT NULL,
  `target_date` TIMESTAMP NOT NULL,
  `priority` INT(11) NOT NULL,
  `overdue` TINYINT(1) NOT NULL,
  PRIMARY KEY (`task_id`),
  UNIQUE INDEX `idtask_UNIQUE` (`task_id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
