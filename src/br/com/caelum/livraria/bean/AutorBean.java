package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class AutorBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();
	
	@Inject
	private AutorDao dao; //CDI faz new AutorDao() e injeta
	
	private Integer autorId;

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregarAutorPelaId() {
		this.autor = this.dao.buscaPorId(autorId);
	}

	public List<Autor> getAutores() {
		return this.dao.listaTodos();
	}

	public Autor getAutor() {
		return autor;
	}
	
	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public RedirectView gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor.getId() == null) {
			this.dao.adiciona(this.autor);
		} else {
			this.dao.atualiza(this.autor);
		}

		this.autor = new Autor();

		return new RedirectView("livro");
	}

	public void remover(Autor autor) {
		System.out.println("Removendo autor");
		this.dao.remove(autor);
	}

	public void carregar(Autor autor) {
		System.out.println("Carregar autor");
		this.autor = autor;
	}
}
