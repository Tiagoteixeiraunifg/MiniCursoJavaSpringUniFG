


-- -----------------------------------------------------
-- Table Alunos
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Alunos (
  id_Aluno INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  sobrenome VARCHAR(100) NULL,
  idade VARCHAR(3) NULL,
  telefone VARCHAR(20) NULL,
  email VARCHAR(100) NULL,
  datacadastro DATETIME NULL,
  dataatualizacao DATETIME NULL,
  PRIMARY KEY (id_Aluno))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table biblioteca
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS biblioteca (
  id_biblioteca INT NOT NULL AUTO_INCREMENT,
  aluno_id_aluno INT NOT NULL,
  status VARCHAR(45) NOT NULL,
  dataretirada DATETIME NULL,
  datadevolucao DATETIME NULL,
  datareservainicio DATETIME NULL,
  datareservafim DATETIME NULL,
  datacadastro DATETIME NULL,
  dataatualizacao DATETIME NULL,
  INDEX fk_biblioteca_Alunos1_idx (aluno_id_aluno ASC) VISIBLE,
  PRIMARY KEY (id_biblioteca, aluno_id_aluno),
  CONSTRAINT fk_biblioteca_Alunos1
    FOREIGN KEY (aluno_id_aluno)
    REFERENCES Alunos (id_Aluno)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Autores
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Autores (
  id_Autor INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  idade VARCHAR(3) NOT NULL,
  datacadastro DATETIME NULL,
  dataatualizacao DATETIME NULL,
  PRIMARY KEY (id_Autor))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Livros
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Livros (
  id_Livro INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  status VARCHAR(45) NOT NULL,
  datacadastro DATETIME NULL,
  dataatualizacao DATETIME NULL,
  PRIMARY KEY (id_Livro))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table relacionamento_livro_autor
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS relacionamento_livro_autor (
  id INT NOT NULL AUTO_INCREMENT,
  rel_id_Livro INT NOT NULL,
  rel_id_Autor INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_RelacionamentoLivroAutor_Autores1_idx (rel_id_Autor ASC) VISIBLE,
  INDEX fk_RelacionamentoLivroAutor_Livros1_idx (rel_id_Livro ASC) VISIBLE,
  CONSTRAINT fk_RelacionamentoLivroAutor_Autores1
    FOREIGN KEY (rel_id_Autor)
    REFERENCES Autores (id_Autor)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_RelacionamentoLivroAutor_Livros1
    FOREIGN KEY (rel_id_Livro)
    REFERENCES Livros (id_Livro)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table relacionamento_biblioteca_livro
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS relacionamento_biblioteca_livro (
  id INT NOT NULL AUTO_INCREMENT,
  rel_Id_Livro INT NULL,
  rel_id_Biblioteca INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_relacionamento_biblioteca_livro_biblioteca1_idx (rel_id_Biblioteca ASC) VISIBLE,
  INDEX fk_relacionamento_biblioteca_livro_Livros1_idx (rel_Id_Livro ASC) VISIBLE,
  CONSTRAINT fk_relacionamento_biblioteca_livro_biblioteca1
    FOREIGN KEY (rel_id_Biblioteca)
    REFERENCES biblioteca (id_biblioteca)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_relacionamento_biblioteca_livro_Livros1
    FOREIGN KEY (rel_Id_Livro)
    REFERENCES Livros (id_Livro)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
