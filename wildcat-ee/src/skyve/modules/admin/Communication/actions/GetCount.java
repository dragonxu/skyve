package modules.admin.Communication.actions;

import modules.admin.domain.Communication;

import org.skyve.metadata.controller.ServerSideAction;
import org.skyve.metadata.controller.ServerSideActionResult;
import org.skyve.web.WebContext;

public class GetCount implements ServerSideAction<Communication> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 60089863446674901L;

	@Override
	public ServerSideActionResult execute(Communication communication, WebContext webContext) throws Exception {

		String results = GetResults.getResults(communication);
		
		communication.setResults(results);
		
		return new ServerSideActionResult(communication);
	}
}
