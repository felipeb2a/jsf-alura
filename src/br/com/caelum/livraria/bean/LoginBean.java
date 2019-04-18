package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;
import br.com.caelum.livraria.util.RedirectView;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class LoginBean implements Serializable{

	private Usuario usuario = new Usuario();
	
	@Inject
	UsuarioDao dao;
	
	@Inject
	FacesContext context;

	public Usuario getUsuario() {
		return usuario;
	}

	public RedirectView efetuarLogin() {
		
		System.out.println("Fazendo login do usuario - " + this.usuario.getEmail());
		
		boolean existe = this.dao.existe(this.usuario);
		
		if(existe ) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", usuario);
			
			return new RedirectView("livro");
//			return "livro?faces-redirect-true";			
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("usuario não encontrado"));
		
		return new RedirectView("login");
		
	}
	
	public RedirectView deslogar() {
		
		System.out.println("Deslogando usuario");
		context.getExternalContext().getSessionMap().remove("usuarioLogado");		
		
		return new RedirectView("login");
//		return "login?faces-redirect-true";
		
	}

	
}
