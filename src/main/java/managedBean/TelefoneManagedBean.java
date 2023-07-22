package managedBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DaoTelefone;
import dao.DaoUsuario;
import model.TelefoneUser;
import model.UsuarioPessoa;

@ManagedBean (name ="telefoneManagedBean")
@ViewScoped
public class TelefoneManagedBean {
	
	private UsuarioPessoa user = new UsuarioPessoa();
	private DaoUsuario<UsuarioPessoa> daoUsuario = new DaoUsuario<UsuarioPessoa>();
	private DaoTelefone<TelefoneUser> daoTelefone = new DaoTelefone<TelefoneUser>();
	private TelefoneUser telefoneUser = new TelefoneUser();
	
	
	
	public TelefoneUser getTelefoneUser() {
		return telefoneUser;
	}
	
	public void setTelefoneUser(TelefoneUser telefoneUser) {
		this.telefoneUser = telefoneUser;
	}
	
	public UsuarioPessoa getUser() {
		return user;
	}
	
	public void setUser(UsuarioPessoa user) {
		this.user = user;
	}	
	
	public void setDaoTelefone(DaoTelefone<TelefoneUser> daoTelefone) {
		this.daoTelefone = daoTelefone;
	}
	
	public DaoTelefone<TelefoneUser> getDaoTelefone() {
		return daoTelefone;
	}
	
	
	
	@PostConstruct
	public void init() {
		
		String codUser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigoUser");	
		//System.out.println(codUser);
		user = daoUsuario.pesquisar(Long.parseLong(codUser), UsuarioPessoa.class); 
	}
	
	public String salvar () {
		telefoneUser.setUsuarioPessoa(user);
		if (daoTelefone.countPhones(telefoneUser) < 3 ) {
			daoTelefone.salvar(telefoneUser);
			
			telefoneUser = new TelefoneUser();
			user = daoUsuario.pesquisar(user.getId(), UsuarioPessoa.class); 
			
			FacesContext.getCurrentInstance().
			addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Phone saved successfully."));
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SAlvo com Sucesso."))  ;
		}
		else { FacesContext.getCurrentInstance().
			addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informação", 
			"Maximum number of Phones: 3. You muss delete at least one."));}
		return "";
	}
	
	public String removeTelefone() throws Exception {
		daoTelefone.deletarPorId(telefoneUser);	
		user = daoUsuario.pesquisar(user.getId(), UsuarioPessoa.class); 
		telefoneUser = new TelefoneUser();
		FacesContext.getCurrentInstance().
		addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", "Phone deleted successfully."));
		
		return "";
	}

}
