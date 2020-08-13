package br.com.arturtcs.posjavamavenhibernate.dao;

import br.com.arturtcs.posjavamavenhibernate.model.UsuarioPessoa;

public class DaoUsuario<E> extends DaoGeneric<UsuarioPessoa> {
	
	public void removerUsuario(UsuarioPessoa pessoa) throws Exception {
		getEm().getTransaction().begin();
		String sqlDeleteFone = "delete from TelefoneUser where usuariopessoa_id = " + pessoa.getId();
		getEm().createNativeQuery(sqlDeleteFone).executeUpdate();
		getEm().getTransaction().commit();
		
		super.deletarPorId(pessoa);
	}

}
