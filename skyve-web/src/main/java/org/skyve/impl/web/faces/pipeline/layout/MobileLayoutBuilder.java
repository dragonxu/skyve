package org.skyve.impl.web.faces.pipeline.layout;

import javax.faces.component.UIComponent;

import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.mobile.component.field.Field;
import org.skyve.impl.metadata.Container;
import org.skyve.impl.metadata.view.container.HBox;
import org.skyve.impl.metadata.view.container.form.Form;
import org.skyve.impl.metadata.view.container.form.FormColumn;
import org.skyve.impl.metadata.view.container.form.FormItem;
import org.skyve.impl.metadata.view.container.form.FormRow;

public class MobileLayoutBuilder extends TabularLayoutBuilder {
	@Override
	public UIComponent viewLayout(UIComponent component) {
		if (component != null) {
			return component;
		}

		return null;
	}

	@Override
	public UIComponent tabLayout(UIComponent component) {
		if (component != null) {
			return component;
		}

		return null;
	}
	
	@Override
	public UIComponent addedTab(UIComponent component, UIComponent tab) {
		if (component != null) {
			return component;
		}

		return tab.getParent();
	}
	
	@Override
	public UIComponent hboxLayout(UIComponent component, HBox hbox) {
		if (component != null) {
			return component;
		}

		// HBox is a Panel grid with 1 column when mobile
		PanelGrid result = panelGrid(hbox.getPixelWidth(),
										null,
										null, // the parent container sets the percentage width
										hbox.getPixelHeight(),
										hbox.getPercentageHeight(),
										hbox.getInvisibleConditionName(),
										hbox.getWidgetId());
		result.setColumns(1);
		return result;
	}

	@Override
	public UIComponent formLayout(UIComponent component, Form form) {
		if (component != null) {
			return component;
		}

		return panelGroup(false, false, true, form.getInvisibleConditionName(), form.getWidgetId());
	}
	
	@Override
	public UIComponent formRowLayout(UIComponent component, FormRow row) {
		if (component != null) {
			return component;
		}

		return null;
	}
	
	@Override
	public UIComponent addFormRowLayout(UIComponent component, UIComponent formLayout, UIComponent rowLayout) {
		if (component != null) {
			return component;
		}

		formLayout.getChildren().add(rowLayout);
		return rowLayout;
	}

	@Override
	public UIComponent addedFormRowLayout(UIComponent component, UIComponent rowLayout) {
		if (component != null) {
			return component;
		}

		return null;
	}

	private UIComponent field = null;
	
	@Override
	public void layoutFormItemLabel(UIComponent formOrRowLayout,
										UIComponent formItemComponent,
										Form currentForm,
										FormItem currentFormItem,
										FormColumn currentFormColumn,
										String widgetLabel,
										boolean widgetRequired,
										String widgetInvisible,
										String widgetHelpText) {
		field = field(widgetInvisible);
		OutputLabel l = label(widgetLabel, null, widgetRequired);
		field.getChildren().add(l);
		formOrRowLayout.getChildren().add(field);
	}
	
	@Override
	public void layoutFormItemWidget(UIComponent formOrRowLayout,
										UIComponent formItemComponent,
										Form currentForm,
										FormItem currentFormItem,
										FormColumn currentFormColumn,
										String widgetLabel,
										boolean widgetRequired,
										String widgetInvisible,
										String widgetHelpText) {
		// No field defined if showLabel="false", so make one and attach it
		if (field == null) {
			field = field(widgetInvisible);
			formOrRowLayout.getChildren().add(field);
		}
		field.getChildren().add(formItemComponent);
		field = null;
	}
	
	@Override
	public UIComponent addToContainer(UIComponent component,
										Container viewContainer,
										UIComponent container, 
										UIComponent componentToAdd, 
										Integer pixelWidth, 
										Integer responsiveWidth,
										Integer percentageWidth,
										String widgetInvisible) {
		if (component != null) {
			return component;
		}

		container.getChildren().add(componentToAdd);
		return componentToAdd;
	}
	
	@Override
	public UIComponent addedToContainer(UIComponent component, Container viewContainer, UIComponent container) {
		if (component != null) {
			return component;
		}

		return container.getParent(); // account for the previously pushed component
	}

	private Field field(String invisibleConditionName) {
		Field result = (Field) a.createComponent(Field.COMPONENT_TYPE);
		setId(result, null);
		setInvisible(result, invisibleConditionName, null);
		return result;
	}
}
