package org.skyve.impl.web.faces.beans;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.skyve.CORE;
import org.skyve.EXT;
import org.skyve.content.AttachmentContent;
import org.skyve.content.ContentManager;
import org.skyve.domain.Bean;
import org.skyve.impl.bind.BindUtil;
import org.skyve.impl.metadata.model.document.field.Content;
import org.skyve.impl.metadata.model.document.field.Field.IndexType;
import org.skyve.metadata.customer.Customer;
import org.skyve.metadata.model.Attribute;
import org.skyve.metadata.module.Module;
import org.skyve.util.Binder;
import org.skyve.util.Binder.TargetMetaData;

public class FacesContentUtil {
	
	/**
	 * Handle a file upload event and return the content id once uploaded.
	 * @param event the event to handle
	 * @param bean the driving bean
	 * @param binding the binding to upload
	 * @return the id of the uploaded content
	 * @throws Exception
	 */
	public static String handleFileUpload(FileUploadEvent event, Bean bean, String binding) throws Exception {
		UploadedFile file = event.getFile();
		Customer customer = CORE.getCustomer();
		
		String fileName = FilenameUtils.getName(file.getFileName());
		String customerName = customer.getName();
		Bean contentOwner = bean;
		String contentAttributeName = binding;
		int contentBindingLastDotIndex = binding.lastIndexOf('.');
		if (contentBindingLastDotIndex >= 0) { // compound binding
			contentOwner = (Bean) BindUtil.get(bean, binding.substring(0, contentBindingLastDotIndex));
			contentAttributeName = binding.substring(contentBindingLastDotIndex + 1);
		}

		// Always insert a new attachment content node into the content
		// repository on upload.
		// That way, if the change is discarded (not committed), it'll still
		// point to the original attachment.
		// Also, browser caching is simple as the URL is changed (as a
		// consequence of the content id change)
		String contentId = null;
		try (ContentManager cm = EXT.newContentManager()) {
			AttachmentContent content = new AttachmentContent(customerName, contentOwner.getBizModule(),
					contentOwner.getBizDocument(), contentOwner.getBizDataGroupId(), contentOwner.getBizUserId(),
					contentOwner.getBizId(), contentAttributeName, fileName, file.getInputstream());

			// Determine if we should index the content or not
			boolean index = true; // default
			Module module = customer.getModule(contentOwner.getBizModule());
			// NB - Could be a base document attribute
			TargetMetaData target = Binder.getMetaDataForBinding(customer, module,
					module.getDocument(customer, contentOwner.getBizDocument()), contentAttributeName);
			Attribute attribute = target.getAttribute();
			if (attribute instanceof Content) {
				IndexType indexType = ((Content) attribute).getIndex();
				index = ((indexType == null) || IndexType.textual.equals(indexType)
						|| IndexType.both.equals(indexType));
			}

			// NB Don't set the content id as we always want a new one
			cm.put(content, index);
			contentId = content.getContentId();
		}
		return contentId;
	}
}
