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

import gwt.material.design.client.ui.MaterialTab;
import gwt.material.design.client.ui.MaterialTabItem;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.style.StyleData;


/********************************************************************
 * A layout implementation that creates and manages a {@link MaterialTab}
 * widget.
 *
 * @author eso
 */
public class MaterialTabPanelLayout
	extends MaterialSwitchPanelLayout<MaterialTab, MaterialTabItem>
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addPage(Component rTabComponent,
						String    sStepTitle,
						boolean   bCloseable)
	{
		MaterialTabItem aTabItem = new MaterialTabItem();

		aTabItem.add(rTabComponent.getWidget());
		getPanelWidget().add(aTabItem);

		addContentWidget(rTabComponent.getWidget(), aTabItem);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public MaterialTab createPanelWidget(Container rContainer, StyleData rStyle)
	{
		return new MaterialTab();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getSelectionIndex()
	{
		return getPanelWidget().getTabIndex();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setPageTitle(int nIndex, String sTitle)
	{
//		MaterialTabItem rTabItem =
//			(MaterialTabItem) getPanelWidget().getWidget(nIndex);
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
