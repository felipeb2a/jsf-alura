package br.com.caelum.livraria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public String efetuarLogin() {
		System.out.println("Fazendo login do usuario" + this.usuario.getEmail());
		boolean existe = new UsuarioDao().existe(this.usuario);
		if(existe ) {
			return "livro?faces-redirect-true";			
		}
		
		return null;
		
	}

	
}
