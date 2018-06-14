package fr.aoufi.springmvcsecurity.runtime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Ce d�mo montre l'utilisation de la classe BCryptPasswordEncoder, d�finie en tant que bean 
 * dans notre configuration pour la s�curit� de l'application, qui impl�mente l'interface PasswordEncoder et 
 * fournit un hachage de mot de passe. 
 * 
 * Le hachage de mot de passe est l'une des principales consid�rations lors de la conception d'une application s�curis�e.
 * Il est bas� sur l'algorithme BCrypt qui g�n�re une cha�ne al�atoire de longueur 60, pour 
 * chaque appel aura un r�sultat diff�rent, nous devons donc nous assurer que le mot de passe sera stock� 
 * dans une colonne qui peut l'accueillir.  
 * Une erreur courante consiste � cr�er une colonne d'une longueur diff�rente, puis � obtenir une erreur 
 * de nom d'utilisateur ou de mot de passe invalide au moment de l'authentification.
 * 
 */

public class PasswordEncoding {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("abc1"));
	}
}