package modules.admin.DataMaintenance;

import org.skyve.util.DataBuilder;
import org.skyve.util.test.DataFactory;
import org.skyve.util.test.SkyveFactory;
import org.skyve.util.test.SkyveFixture;
import org.skyve.util.test.SkyveFixture.FixtureType;

import modules.admin.domain.DataMaintenance;

@SkyveFactory(testAction = false)
public class DataMaintenanceFactory extends DataFactory {

	@SkyveFixture(types = FixtureType.crud)
	public static DataMaintenance crudInstance() throws Exception {
		DataMaintenance bean = new DataBuilder().build(DataMaintenance.MODULE_NAME, DataMaintenance.DOCUMENT_NAME);
		bean.setModDocName(null);
		bean.setRestorePreProcess(null);
		bean.setAuditModuleName(null);
		bean.setAuditDocumentName(null);

		return bean;
	}
}
