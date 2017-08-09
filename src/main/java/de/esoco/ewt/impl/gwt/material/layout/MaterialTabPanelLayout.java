//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2017 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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

import gwt.material.design.client.constants.Flex;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * A layout implementation that creates and manages a {@link MaterialTab}
 * widget.
 *
 * @author eso
 */
public class MaterialTabPanelLayout
	extends MaterialSwitchPanelLayout<MaterialRow, MaterialTabItem>
{
	//~ Instance fields --------------------------------------------------------

	private MaterialTab aTabBar;

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addPage(Component rTabComponent,
						String    sStepTitle,
						boolean   bCloseable)
	{
		String		    sId			   = "tab" + aTabBar.getChildren().size();
		MaterialTabItem aTabItem	   = new MaterialTabItem();
		MaterialLink    aTabLink	   = new MaterialLink(sStepTitle, sId);
		MaterialColumn  aContentColumn = new MaterialColumn();
		Widget		    rContentWidget = rTabComponent.getWidget();

		aContentColumn.getElement().setId(sId);
		aContentColumn.setGrid("s12");
		aTabItem.setFlex(Flex.NONE);
		aTabItem.setWaves(WavesType.DEFAULT);

		aTabItem.add(aTabLink);
		aTabBar.add(aTabItem);
		aContentColumn.add(rContentWidget);
		getPanelWidget().add(aContentColumn);

		addContentWidget(rContentWidget, aTabItem);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public MaterialRow createPanelWidget(Container rContainer, StyleData rStyle)
	{
		MaterialRow    aTabPanelContainer = new MaterialRow();
		MaterialColumn aTabBarColumn	  = new MaterialColumn();

		aTabBar = new MaterialTab();

		aTabBarColumn.setGrid("s12");
		aTabBarColumn.add(aTabBar);
		aTabPanelContainer.add(aTabBarColumn);

		return aTabPanelContainer;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getSelectionIndex()
	{
		return aTabBar.getTabIndex();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setPageTitle(int nIndex, String sTitle)
	{
		MaterialTabItem rTabItem =
			(MaterialTabItem) aTabBar.getChildren().get(nIndex);

		MaterialLink rTabLink = (MaterialLink) rTabItem.getChildren().get(0);

		rTabLink.setText(sTitle);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setSelection(int nIndex)
	{
		getPanelWidget().setTabIndex(nIndex);
	}
}
