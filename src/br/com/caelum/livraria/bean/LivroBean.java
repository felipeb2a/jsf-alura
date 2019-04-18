package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;
import br.com.caelum.livraria.tx.Transacional;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class LivroBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();
	
	@Inject
	private LivroDao livroDao;
	
	@Inject
	private AutorDao autorDao;

	@Inject
	FacesContext context;
	
	private Integer autorId;

	private Integer livroId;

	private List<Livro> livros;
	
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");

	
//	private LivroDataModel livroDataModel = new LivroDataModel();
	
//	public LivroDataModel getLivroDataModel() {
//		return livroDataModel;
//	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}
	
	public List<String> getGeneros() {
	    return generos;
	}

	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = this.livroDao.listaTodos();
		}
		return livros;
	}

	public List<Autor> getAutores() {
		return this.autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void gravarAutor() {
		Autor autor = this.autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}

	//begin
	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			context.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor"));
		}

		if (this.livro.getId() == null) {
			livroDao.adiciona(this.livro);
			this.livros = this.livroDao.listaTodos();
		} else {
			this.livroDao.atualiza(this.livro);
		}

		this.livro = new Livro();
	}
	//commit

	@Transacional
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
		this.livroDao.remove(livro);
	}

	public void removerAutorDoLivro(Autor autor) {
		System.out.println("Removendo autor");
		this.livro.removeAutor(autor);
	}

	public void carregar(Livro livro) {
		System.out.println("Carregar livro");
		this.livro = livro;

	}

	public RedirectView formAutor() {
		System.out.println("Chamando o formulario do Autor");
		return new RedirectView("autor");
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}

	}

	public void carregarLivroPelaId() {
		this.livro = this.livroDao.buscaPorId(livroId);
	}

	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) { // java.util.Locale

		// tirando espaços do filtro
		String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

		System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

		// o filtro é nulo ou vazio?
		if (textoDigitado == null || textoDigitado.equals("")) {
			return true;
		}

		// elemento da tabela é nulo?
		if (valorColuna == null) {
			return false;
		}

		try {
			// fazendo o parsing do filtro para converter para Double
			Double precoDigitado = Double.valueOf(textoDigitado);
			Double precoColuna = (Double) valorColuna;

			// comparando os valores, compareTo devolve um valor negativo se o value é menor
			// do que o filtro
			return precoColuna.compareTo(precoDigitado) < 0;

		} catch (NumberFormatException e) {

			// usuario nao digitou um numero
			return false;
		}
	}
}
