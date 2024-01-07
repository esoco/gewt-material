//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2016 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//	  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
package de.esoco.ewt.impl.gwt.material.layout;

import gwt.material.design.client.base.MaterialWidget;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.component.SwitchPanel.SwitchPanelLayout;
import de.esoco.ewt.style.StyleData;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A base class for GWT Material switch panels that manages the content widgets
 * and the wrapping widgets that contain them and that can be switched between.
 *
 * @author eso
 */
public abstract class MaterialSwitchPanelLayout<P extends IndexedPanel & HasWidgets, W extends MaterialWidget>
	extends SwitchPanelLayout {

	private P panelWidget;

	private Map<Widget, W> switchedComponents = new HashMap<>();

	@Override
	public final HasWidgets createLayoutContainer(Container container,
		StyleData style) {
		panelWidget = createPanelWidget(container, style);

		return panelWidget;
	}

	@Override
	public int getPageCount() {
		return panelWidget.getWidgetCount();
	}

	@Override
	public int getPageIndex(Component groupComponent) {
		return panelWidget.getWidgetIndex(groupComponent.getWidget());
	}

	@Override
	public void removeWidget(HasWidgets container, Widget widget) {
		super.removeWidget(container, widget);

		switchedComponents.remove(widget);
	}

	/**
	 * Adds a content widget with the associated wrapping switch widget.
	 *
	 * @param widget        The content widget
	 * @param wrapperWidget The widget that wraps the switched component (not
	 *                      the component widget itself)
	 */
	protected void addContentWidget(Widget widget, W wrapperWidget) {
		switchedComponents.put(widget, wrapperWidget);
	}

	/**
	 * Must be implemented to create the widget for the switched panel.
	 *
	 * @param container The parent GEWT container
	 * @param style     The panel widget style
	 * @return A new container widget
	 */
	protected abstract P createPanelWidget(Container container,
		StyleData style);

	/**
	 * Returns the panel widget.
	 *
	 * @return The panel widget
	 */
	protected final P getPanelWidget() {
		return panelWidget;
	}
}
