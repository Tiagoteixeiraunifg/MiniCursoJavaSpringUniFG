


-- -----------------------------------------------------
-- Table Alunos
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS alunos (
  id_aluno INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  sobrenome VARCHAR(100) NULL,
  idade VARCHAR(3) NULL,
  telefone VARCHAR(20) NULL,
  email VARCHAR(100) NULL,
  datacadastro DATETIME NULL,
  dataatualizacao DATETIME NULL,
  PRIMARY KEY (id_aluno))
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
    REFERENCES alunos (id_aluno)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Autores
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS autores (
  id_autor INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  idade VARCHAR(3) NOT NULL,
  datacadastro DATETIME NULL,
  dataatualizacao DATETIME NULL,
  PRIMARY KEY (id_Autor))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table Livros
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS livros (
  id_livro INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  status VARCHAR(45) NOT NULL,
  datacadastro DATETIME NULL,
  dataatualizacao DATETIME NULL,
  PRIMARY KEY (id_livro))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table relacionamento_livro_autor
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS relacionamento_livro_autor (
  id INT NOT NULL AUTO_INCREMENT,
  rel_id_Livro INT NOT NULL,
  rel_id_Autor INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_RelacionamentoLivroAutor_Autores1_idx (rel_id_autor ASC) VISIBLE,
  INDEX fk_RelacionamentoLivroAutor_Livros1_idx (rel_id_livro ASC) VISIBLE,
  CONSTRAINT fk_RelacionamentoLivroAutor_Autores1
    FOREIGN KEY (rel_id_autor)
    REFERENCES autores (id_autor)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_RelacionamentoLivroAutor_Livros1
    FOREIGN KEY (rel_id_livro)
    REFERENCES livros (id_livro)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table relacionamento_biblioteca_livro
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS relacionamento_biblioteca_livro (
  id INT NOT NULL AUTO_INCREMENT,
  rel_Id_livro INT NULL,
  rel_id_biblioteca INT NOT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB;
