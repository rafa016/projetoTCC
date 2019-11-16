CREATE DATABASE HorarioEscolar;
/* DROP DATABASE HorarioEscolar; */
USE HorarioEscolar;

CREATE TABLE Professores 
(
	id_prof INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nome_prof VARCHAR (150) NOT NULL,
    id_cor_primaria VARCHAR (20) NOT NULL,
    id_cor_secundaria VARCHAR (20) NOT NULL
);

CREATE TABLE Materias
(
	id_materia INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nome_materia VARCHAR (100) NOT NULL,
    abrev_nome_materia VARCHAR (20) NOT NULL
);

CREATE TABLE Salas 
(
	id_sala INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nome_sala VARCHAR (10) NOT NULL
);

CREATE TABLE Prof_Materias
(
	id_prof_mat INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    fk_id_prof INT NOT NULL,
	fk_id_materia INT NOT NULL,
    
    CONSTRAINT fk_id_prof FOREIGN KEY (fk_id_prof) REFERENCES Professores (id_prof),
	CONSTRAINT fk_id_materia FOREIGN KEY (fk_id_materia) REFERENCES Materias (id_materia)
);

CREATE TABLE Prof_Salas
(
	id_prof_salas INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	fk_id_profe INT NOT NULL,
    fk_id_sala INT NOT NULL,

	CONSTRAINT fk_id_profe FOREIGN KEY (fk_id_profe) REFERENCES Professores (id_prof),
    CONSTRAINT fk_id_sala FOREIGN KEY (fk_id_sala) REFERENCES Salas (id_sala)
);

CREATE TABLE Usuario
(
	id_usua INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    senha_usua VARCHAR (30) NOT NULL 
);

/* CREATE TABLE Horario
(
	id_tabela_horario INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	fk_id_prof INT NOT NULL,
	fk_id_salas INT NOT NULL,
    
    CONSTRAINT fk_id_prof FOREIGN KEY (fk_id_prof) REFERENCES Professores (id_prof),
    CONSTRAINT fk_id_salas FOREIGN KEY (fk_id_salas) REFERENCES Salas (id_sala)
);*/

INSERT INTO Usuario(senha_usua) VALUES ("1234");
INSERT INTO Usuario(senha_usua) VALUES ("admim");

SELECT * FROM Salas;
SELECT * FROM Usuario;
SELECT * FROM Materias;
SELECT * FROM Professores;
SELECT * FROM Prof_Salas;
SELECT * FROM Prof_Materias;





