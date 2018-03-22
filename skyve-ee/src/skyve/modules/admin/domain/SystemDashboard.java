package modules.admin.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractTransientBean;

/**
 * System Dashboard
 * 
 * @stereotype "transient"
 */
@XmlType
@XmlRootElement
public class SystemDashboard extends AbstractTransientBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "admin";
	/** @hidden */
	public static final String DOCUMENT_NAME = "SystemDashboard";



	@Override
	@XmlTransient
	public String getBizModule() {
		return SystemDashboard.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return SystemDashboard.DOCUMENT_NAME;
	}

	public static SystemDashboard newInstance() {
		try {
			return CORE.getUser().getCustomer().getModule(MODULE_NAME).getDocument(CORE.getUser().getCustomer(), DOCUMENT_NAME).newInstance(CORE.getUser());
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new DomainException(e);
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof SystemDashboard) && 
					this.getBizId().equals(((SystemDashboard) o).getBizId()));
	}
}
