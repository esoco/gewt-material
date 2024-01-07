//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2019 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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

import gwt.material.design.client.js.JsMaterialElement;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * A layout implementation that creates and manages a {@link MaterialTab}
 * widget.
 *
 * @author eso
 */
public class MaterialTabPanelLayout
	extends MaterialSwitchPanelLayout<MaterialPanel, MaterialTabItem> {

	private MaterialTab tabBar;

	@Override
	public void addPage(Component tabComponent, String stepTitle,
		boolean closeable) {
		String id = DOM.createUniqueId();
		MaterialTabItem tabItem = new MaterialTabItem();
		MaterialLink tabLink = new MaterialLink(stepTitle, id);
		MaterialPanel contentPanel = new MaterialPanel();
		Widget contentWidget = tabComponent.getWidget();

		contentPanel.getElement().setId(id);

		getPanelWidget().add(contentPanel);
		contentPanel.add(contentWidget);
		tabItem.add(tabLink);
		tabBar.add(tabItem);

		addContentWidget(contentWidget, tabItem);
	}

	@Override
	public MaterialPanel createPanelWidget(Container container,
		StyleData style) {
		MaterialPanel tabPanelContainer = new MaterialPanel();

		tabBar = new GewtMaterialTab();

		tabPanelContainer.add(tabBar);

		return tabPanelContainer;
	}

	@Override
	public int getSelectionIndex() {
		return tabBar.getTabIndex();
	}

	@Override
	public void setPageTitle(int index, String title) {
		MaterialTabItem tabItem =
			(MaterialTabItem) tabBar.getChildren().get(index);

		MaterialLink tabLink = (MaterialLink) tabItem.getChildren().get(0);

		tabLink.setText(title);
	}

	@Override
	public void setSelection(int index) {
		tabBar.setTabIndex(index);
	}

	/**
	 * Subclassed to fix a GMD bug.
	 *
	 * @author eso
	 */
	public static class GewtMaterialTab extends MaterialTab {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getTabIndex() {
			int tabIndex = super.getTabIndex();

			return tabIndex >= 0 ? tabIndex : 0;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void load() {
			// workaround for bug in GMD2.0
			// https://github.com/GwtMaterialDesign/gwt-material/issues/736
			JsMaterialElement.$(getElement()).find(".indicator").remove();
			super.load();
		}
	}
}
