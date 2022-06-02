package sisop_old.socket;

import java.io.Serializable;

public class Utente implements Comparable<Utente>, Serializable {
	private static final long serialVersionUID = 3006163414047208670L;
	public String nome;
	private String password;
	
	public Utente(String nome) {
		this.nome = nome;
	}
	
	@Override
	public int compareTo(Utente u) {
		return this.nome.compareTo(u.nome);
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o == this) return true;
		if(!(o instanceof Utente)) return false;
		Utente u = (Utente)o;
		return this.nome.equals(u.nome);
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	} 
	
	@Override
	public int hashCode() {
		return nome.hashCode();
	}//hashCode 
	
	@Override
	public String toString() {
		return nome;
	}
}
