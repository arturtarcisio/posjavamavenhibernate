package br.com.arturtcs.posjavamavenhibernate.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.arturtcs.posjavamavenhibernate.dao.DaoTelefone;
import br.com.arturtcs.posjavamavenhibernate.dao.DaoUsuario;
import br.com.arturtcs.posjavamavenhibernate.model.TelefoneUser;
import br.com.arturtcs.posjavamavenhibernate.model.UsuarioPessoa;

@ManagedBean(name = "telefoneManagedBean")
@ViewScoped
public class TelefoneManagedBean {

	private UsuarioPessoa usuario = new UsuarioPessoa();
	private TelefoneUser telefone = new TelefoneUser();
	
	private DaoUsuario<UsuarioPessoa> daoUser = new DaoUsuario<UsuarioPessoa>();
	private DaoTelefone<TelefoneUser> daoFone = new DaoTelefone<TelefoneUser>();

	@PostConstruct
	public void init() {
		String coduser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("codigouser");
		usuario = daoUser.pesquisar(Long.parseLong(coduser), UsuarioPessoa.class);
	}
	
	public String salvar() {
		telefone.setUsuarioPessoa(usuario);
		daoFone.salvar(telefone);
		telefone = new TelefoneUser();
		usuario = daoUser.pesquisar(usuario.getId(), UsuarioPessoa.class);
		FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Telefone cadastrado com sucesso!"));
		
		return "";
	}
	
	public String novoTelefone() {
		telefone = new TelefoneUser();
		return "";
	}
	
	public String removeTelefone() {
		try {
			telefone.setUsuarioPessoa(usuario);
			daoFone.deletarPorId(telefone);
			usuario = daoUser.pesquisar(usuario.getId(), UsuarioPessoa.class);
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Telefone excluído com sucesso!"));
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		telefone = new TelefoneUser();
		return "";
	}	

	public UsuarioPessoa getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioPessoa usuario) {
		this.usuario = usuario;
	}

	public DaoTelefone<TelefoneUser> getDaoFone() {
		return daoFone;
	}

	public void setDaoFone(DaoTelefone<TelefoneUser> daoFone) {
		this.daoFone = daoFone;
	}

	public TelefoneUser getTelefone() {
		return telefone;
	}

	public void setTelefone(TelefoneUser telefone) {
		this.telefone = telefone;
	}

}
