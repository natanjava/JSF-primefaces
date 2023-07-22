package managedBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import dao.DaoEmail;
import dao.DaoUsuario;
import model.EmailUser;
import model.UsuarioPessoa;


@ManagedBean (name="usuarioPessoaManagedBean")
@RequestScoped
public class UsuarioPessoaManagedBean {
	
	public UsuarioPessoaManagedBean() {
		// TODO Auto-generated constructor stub
	}
	
	private UsuarioPessoa pessoa = new UsuarioPessoa();	
	private DaoUsuario<UsuarioPessoa> daoGeneric = new DaoUsuario<UsuarioPessoa>();	
	private List<UsuarioPessoa> listPessoas = new ArrayList<UsuarioPessoa>();
	private BarChartModel barChartModel = new BarChartModel();
	private EmailUser emailUser = new EmailUser();
	private DaoEmail<EmailUser> daoEmail = new DaoEmail<EmailUser>();
	private String campoPesquisa;
	private String campoPesquisaS;
	
	
	
	public String getCampoPesquisaS() {
		return campoPesquisaS;
	}
	
	public void setCampoPesquisaS(String campoPesquisaS) {
		this.campoPesquisaS = campoPesquisaS;
	}
	
	public String getCampoPesquisa() {
		return campoPesquisa;
	}
	
	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}

	public EmailUser getEmailUser() {
		return emailUser;
	}
	
	public void setEmailUser(EmailUser emailUser) {
		this.emailUser = emailUser;
	}
	
	public BarChartModel getBarChartModel() {
		return barChartModel;
	}
	
	public void setBarChartModel(BarChartModel barChartModel) {
		this.barChartModel = barChartModel;
	}
	
	public UsuarioPessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(UsuarioPessoa pessoa) {
		this.pessoa = pessoa;
	}	
	
	public List<UsuarioPessoa> getListPessoas() {
		return listPessoas;
	}	
	
	
	
	@PostConstruct
	public void init () {
		listPessoas = daoGeneric.listr(UsuarioPessoa.class);
		montarGrafico();
	}

	private void montarGrafico() {
		barChartModel = new BarChartModel();
		
		ChartSeries userAge = new ChartSeries(); /* Grupo de funcionarios */
		for (UsuarioPessoa usuarioPessoa : listPessoas) {
			userAge.set(usuarioPessoa.getNome(), usuarioPessoa.getIdade());  /*Adiciona salarios */
		}		
		barChartModel.addSeries(userAge); /*Adiciona o grupo no bar model*/
		barChartModel.setTitle("Age Chart"); /* Titulo */
	}
	
	
	public String salvar () {
		//daoGeneric.salvar(pessoa);
		if (daoGeneric.countPessoas() < 20 ) {
			daoGeneric.updateMerge(pessoa);
			listPessoas.add(pessoa);
			pessoa = new UsuarioPessoa();
			init();
			FacesContext.getCurrentInstance().
					addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "User saved successfully."));
		}
		else { FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info:", 
		"For technical reasons, this Test System supports only 20 users. If you want to register more users, delete some one."));
		} 
		return "";
	}	
	
	public String novo () {
		pessoa = new UsuarioPessoa(); 
		return "";
	}
	
	public String excluir () {
		try {
			daoGeneric.removerUserCascade(pessoa);
			listPessoas.remove(pessoa);
			getListPessoas();
			pessoa = new UsuarioPessoa();
			init();
			FacesContext.getCurrentInstance().
			addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Removed successfully."));
		} catch (Exception e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
			FacesContext.getCurrentInstance().
			addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "There are phones registered to this user."));
			}
			else {
				e.printStackTrace();
			}
		}
		return  "";
	}
	
	public void addEmail () {
		emailUser.setUsuarioPessoa(pessoa);
		if (daoEmail.countEmails(emailUser) < 3 ) {
			emailUser = daoEmail.updateMerge(emailUser);
			pessoa.getEmails().add(emailUser);
			emailUser = new EmailUser();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ",
			"Email saved succesfully"));
		}
		else {
			FacesContext.getCurrentInstance().
			addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info: ",
			"Maximum number of emails: 3. You muss delete at least one."));
		}
	}
	
	public void removerEmail () throws Exception {
		String codigoEmail = FacesContext.getCurrentInstance().getExternalContext().
							 getRequestParameterMap().get("codigoEmail");
		EmailUser userRemove = new EmailUser();
		userRemove.setId(Long.parseLong(codigoEmail));
		daoEmail.deletarPorId(userRemove);
		pessoa.getEmails().remove(userRemove);	
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:",
		"Removed succesfully."));
	}
	
	public void pesquisarNome () {
		listPessoas = daoGeneric.pesquisarNome(campoPesquisa);
		montarGrafico();
	}
	
	public void pesquisarSobrenome () {
		listPessoas = daoGeneric.pesquisarSobrenome(campoPesquisaS);
		montarGrafico();
	}
	
	public void pesquisarTudo() {
		listPessoas = daoGeneric.listr(UsuarioPessoa.class);
		campoPesquisa="";
		campoPesquisaS="";
		montarGrafico();
	}
	
	
	
	public void upload (FileUploadEvent imageUpload) {
		String imageStr = "data:image/png;base64," + DatatypeConverter.printBase64Binary(imageUpload.getFile().getContents());
		pessoa.setImagem(imageStr);
	}
	
	public void download() throws IOException {
		pessoa = new UsuarioPessoa();
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String idForDownload = params.get("idForDownload");
			pessoa = daoGeneric.pesquisar(Long.parseLong(idForDownload), UsuarioPessoa.class); // xxx
		
		if (pessoa.getImagem() != null ) {
			byte [] imagem = new Base64().decodeBase64(pessoa.getImagem().split("\\,")[1]);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.addHeader("Content-Disposition", "attachment; filename=download.png");
			response.setContentType("application/octet-stream");
			response.setContentLength(imagem.length);
			response.getOutputStream().write(imagem);
			response.getOutputStream().flush();
			FacesContext.getCurrentInstance().responseComplete();
		}
		else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alert","User withou photo."));
		}
		init();
		
	}

}
	

	
	
	
	
	


