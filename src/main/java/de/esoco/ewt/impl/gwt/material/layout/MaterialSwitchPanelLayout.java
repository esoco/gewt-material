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

	private P aPanelWidget;

	private Map<Widget, W> aSwitchedComponents = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final HasWidgets createLayoutContainer(Container rContainer,
		StyleData rStyle) {
		aPanelWidget = createPanelWidget(rContainer, rStyle);

		return aPanelWidget;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPageCount() {
		return aPanelWidget.getWidgetCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPageIndex(Component rGroupComponent) {
		return aPanelWidget.getWidgetIndex(rGroupComponent.getWidget());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeWidget(HasWidgets rContainer, Widget rWidget) {
		super.removeWidget(rContainer, rWidget);

		aSwitchedComponents.remove(rWidget);
	}

	/**
	 * Adds a content widget with the associated wrapping switch widget.
	 *
	 * @param rWidget        The content widget
	 * @param rWrapperWidget The widget that wraps the switched component (not
	 *                       the component widget itself)
	 */
	protected void addContentWidget(Widget rWidget, W rWrapperWidget) {
		aSwitchedComponents.put(rWidget, rWrapperWidget);
	}

	/**
	 * Must be implemented to create the widget for the switched panel.
	 *
	 * @param rContainer The parent GEWT container
	 * @param rStyle     The panel widget style
	 * @return A new container widget
	 */
	protected abstract P createPanelWidget(Container rContainer,
		StyleData rStyle);

	/**
	 * Returns the panel widget.
	 *
	 * @return The panel widget
	 */
	protected final P getPanelWidget() {
		return aPanelWidget;
	}
}
