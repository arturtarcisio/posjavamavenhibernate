package br.com.arturtcs.posjavamavenhibernate;

import java.util.List;

import org.junit.Test;

import br.com.arturtcs.posjavamavenhibernate.dao.DaoGeneric;
import br.com.arturtcs.posjavamavenhibernate.model.TelefoneUser;
import br.com.arturtcs.posjavamavenhibernate.model.UsuarioPessoa;

public class TesteHibernate {

	@Test
	public void testeHibernateUtil() {
		HibernateUtil.getEntityManager();
	}

	@Test
	public void daoGenericSalvar() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();

		pessoa.setEmail("murilo@gmail.com");
		pessoa.setIdade(2);
		pessoa.setLogin("murilo");
		pessoa.setNome("Murilo");
		pessoa.setSenha("hagf");
		pessoa.setSobrenome("Casé da Silva");

		dao.salvar(pessoa);
		System.out.println("Pessoa cadastrada com sucesso!");

	}

	@Test
	public void daoGenericPesquisar() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa = dao.pesquisar(1L, UsuarioPessoa.class);
		System.out.println(pessoa);
	}

	@Test
	public void daoGenericUpdate() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = dao.pesquisar(1L, UsuarioPessoa.class);

		pessoa.setIdade(27);
		pessoa.setSenha("kkkkkk");

		pessoa = dao.updateMerge(pessoa);

		System.out.println(pessoa);

	}

	@Test
	public void daoGenericDelete() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();		
		dao.deletarPorId(5L, UsuarioPessoa.class);
		System.out.println("Deletado com sucesso!");
		
	}

	
	@Test
	public void daoGenericListar() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = dao.listar(UsuarioPessoa.class);
		
		for(UsuarioPessoa usuarios : lista) {
			System.out.println(usuarios);
			System.out.println("---------------------------------------------------------------------------------");
		}
		
	}
	
	@Test
	public void testeQueryList() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = dao.getEm().createQuery(" from UsuarioPessoa where nome = 'Artur Tarcísio'").getResultList();
		
		for(UsuarioPessoa usuarios : lista) {
			System.out.println(usuarios);
			System.out.println("----------------------------------------------------------------------------------");
		}
	}
	
	@Test
	public void testeQueryListMaxResult() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = dao.getEm().createQuery(" from UsuarioPessoa order by id").setMaxResults(2).getResultList();
		for(UsuarioPessoa usuarios : lista) {
			System.out.println(usuarios);
			System.out.println("-----------------------------------------------------------------------------------");
		}
	}
	
	//metodo de teste de um parametro que vem passado da tela do usuario
	@Test
	public void testeQueryListParameter() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = dao.getEm().createQuery("from UsuarioPessoa where nome = :nome or sobrenome = :sobrenome")
				.setParameter("nome", "Jubileu")
				.setParameter("sobrenome", "Casé da Silva")
				.getResultList();
		
		for(UsuarioPessoa usuarios : lista) {
			System.out.println(usuarios);
			System.out.println("-------------------------------------------------------------------------------");
		}
	}
	
	@Test
	public void testeQuerySomaIdade() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
		
		Long somaIdade = (Long) dao.getEm().createQuery("select sum(u.idade) from UsuarioPessoa u ").getSingleResult();
		
		System.out.println("A somda de todas as idades é: " + somaIdade);
	}
	
	@Test
	public void testeNamedQuery1() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = dao.getEm().createNamedQuery("UsuarioPessoa.findall").getResultList();
		for(UsuarioPessoa usuarios : lista) {
			System.out.println(usuarios);
			System.out.println("----------------------------------------------------------------------------------------");
		}
	}
	
	@Test
	public void testeNamedQuery2() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = dao.getEm().createNamedQuery("UsuarioPessoa.buscaPorNome")
				.setParameter("nome", "Artur Tarcísio")
				.getResultList();
		for(UsuarioPessoa usuarios : lista) {
			System.out.println(usuarios);
			System.out.println("----------------------------------------------------------------------------------------");
		}
	}
	
	@Test
	public void testeGravaTelefone() {
		DaoGeneric dao = new DaoGeneric();
		
		UsuarioPessoa pessoa = (UsuarioPessoa) dao.pesquisar(7L, UsuarioPessoa.class);
		
		TelefoneUser fone = new TelefoneUser();
		fone.setTipo("celular");
		fone.setNumero("(81)988709080");
		fone.setUsuarioPessoa(pessoa);
		
		dao.salvar(fone);
		
		System.out.println("Telefone salvo com sucesso!");
		System.out.println(fone);
		System.out.println(pessoa);
		
	}
	
	@Test
	public void testeConsultaTelefone() {
		DaoGeneric<UsuarioPessoa> dao = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa = (UsuarioPessoa) dao.pesquisar(1L, UsuarioPessoa.class);
		
		for(TelefoneUser telefones : pessoa.getTelefoneUsers()) {
			//System.out.println(telefones);
			System.out.println(telefones.getNumero());
			System.out.println(telefones.getTipo());
			System.out.println(telefones.getUsuarioPessoa().getNome());
			System.out.println(telefones.getUsuarioPessoa().getIdade());
			System.out.println("----------------------------------------------------------------------------");
		}
	}
	
	@Test
	public void listarTelefones() {
		DaoGeneric<TelefoneUser> dao = new DaoGeneric<TelefoneUser>();
		List<TelefoneUser> lista = dao.listar(TelefoneUser.class);
		for(TelefoneUser telefones : lista) {
			System.out.println(telefones);
			System.out.println("-----------------------------------------------------------------------------");
		}
		
	}
	
	
}
