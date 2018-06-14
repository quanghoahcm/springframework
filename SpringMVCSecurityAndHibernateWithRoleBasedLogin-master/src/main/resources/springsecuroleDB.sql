drop database springsecuroleDB

create database springsecuroleDB
use springsecuroleDB

/*La table T_USER pour stocker tous les utilisateurs de l'application */
create table T_USER (
   id 		   Integer  	NOT NULL AUTO_INCREMENT,
   USERNAME    VARCHAR(30)  NOT NULL,
   PASSWORD    VARCHAR(100) NOT NULL,
   FIRSTNAME   VARCHAR(30)  NOT NULL,
   LASTNAME    VARCHAR(30)  NOT NULL,
   EMAIL       VARCHAR(30)  NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (USERNAME)
);
   
/* La table T_ROLE contient tous les r�les possibles */ 
create table T_ROLE (
   id   Integer      NOT NULL AUTO_INCREMENT,
   TYPE VARCHAR(30) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (TYPE)
);
   
/* T_USER_ROLE : La table de jointure pour la relaltion MANY-TO-MANY */  
CREATE TABLE T_USER_ROLE (
    USER_ID Integer NOT NULL,
    ROLE_ID Integer NOT NULL,
    PRIMARY KEY (User_id, Role_id),
    CONSTRAINT FK_T_USER FOREIGN KEY (USER_ID) REFERENCES T_USER (id),
    CONSTRAINT FK_T_ROLE FOREIGN KEY (ROLE_ID) REFERENCES T_ROLE (id)
);
  
/* Remplir la table T_ROLE  */ 
INSERT INTO T_ROLE(type)
VALUES ('ADMIN');
  
INSERT INTO T_ROLE(type)
VALUES ('DBA');

INSERT INTO T_ROLE(type)
VALUES ('USER');
  
/* Ins�rtion d'un utilisateur dont le r�le est administrateur qui va cr�er et modifier le r�le d'autres utilisateurs de l'application � l'aide de l'interface graphique */
/* Pour g�n�rer la version crypt� du mot de passe: abc1 , il faut ex�cut� la m�thode main dans la classe PasswordEncoding */
INSERT INTO T_USER(USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL)
VALUES ('taoufi','$2a$10$8un3L3OtmXWASm26VIGWJekKUqrrWgnAs2x9UPTXqb8UVG.hG9yH', 'Tarik','Aoufi','taoufi@xyz.com');
 
  
/* Remplir la table de jointure */
INSERT INTO T_USER_ROLE(USER_ID, ROLE_ID)   SELECT user.id, role.id FROM T_USER user, T_ROLE role where user.USERNAME='tarik' and role.TYPE='ADMIN';

 
/* La table T_PERSISTENT_LOGIN est utilis�e pour stocker les �l�ments li�s au service souviens-moi */
CREATE TABLE T_PERSISTENT_LOGIN (
    USER_NAME VARCHAR(64) NOT NULL,
    SERIES    VARCHAR(64) NOT NULL,
    TOKEN 	  VARCHAR(64) NOT NULL,
    LAST_USED TIMESTAMP   NOT NULL,
    PRIMARY KEY (SERIES)
);