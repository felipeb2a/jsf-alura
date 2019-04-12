package br.com.caelum.livraria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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

	public RedirectView efetuarLogin() {
		System.out.println("Fazendo login do usuario" + this.usuario.getEmail());
		boolean existe = new UsuarioDao().existe(this.usuario);
		if(existe ) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("usuarioLogado", usuario);
			
			return new RedirectView("livro");
//			return "livro?faces-redirect-true";			
		}
		
		return null;
		
	}
	
	public RedirectView deslogar() {
		
		System.out.println("Deslogando usuario");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");		
		
		return new RedirectView("login");
//		return "login?faces-redirect-true";
		
	}

	
}
