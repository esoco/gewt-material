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

	private MaterialTab aTabBar;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPage(Component rTabComponent, String sStepTitle,
		boolean bCloseable) {
		String sId = DOM.createUniqueId();
		MaterialTabItem aTabItem = new MaterialTabItem();
		MaterialLink aTabLink = new MaterialLink(sStepTitle, sId);
		MaterialPanel aContentPanel = new MaterialPanel();
		Widget rContentWidget = rTabComponent.getWidget();

		aContentPanel.getElement().setId(sId);

		getPanelWidget().add(aContentPanel);
		aContentPanel.add(rContentWidget);
		aTabItem.add(aTabLink);
		aTabBar.add(aTabItem);

		addContentWidget(rContentWidget, aTabItem);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MaterialPanel createPanelWidget(Container rContainer,
		StyleData rStyle) {
		MaterialPanel aTabPanelContainer = new MaterialPanel();

		aTabBar = new GewtMaterialTab();

		aTabPanelContainer.add(aTabBar);

		return aTabPanelContainer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSelectionIndex() {
		return aTabBar.getTabIndex();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPageTitle(int nIndex, String sTitle) {
		MaterialTabItem rTabItem =
			(MaterialTabItem) aTabBar.getChildren().get(nIndex);

		MaterialLink rTabLink = (MaterialLink) rTabItem.getChildren().get(0);

		rTabLink.setText(sTitle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSelection(int nIndex) {
		aTabBar.setTabIndex(nIndex);
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
			int nTabIndex = super.getTabIndex();

			return nTabIndex >= 0 ? nTabIndex : 0;
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
