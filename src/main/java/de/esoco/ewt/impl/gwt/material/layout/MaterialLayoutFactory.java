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

import de.esoco.ewt.component.Container;
import de.esoco.ewt.component.MainView.MainViewLayout;
import de.esoco.ewt.layout.GenericLayout;
import de.esoco.ewt.layout.LayoutFactory.DefaultLayoutFactory;
import de.esoco.ewt.layout.LayoutMapper;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.UserInterfaceProperties.Layout;


/********************************************************************
 * A factory and mapper that creates layouts or maps GEWT layouts to new layouts
 * that are based on GwtMaterialDesign widgets.
 *
 * @author eso
 */
public class MaterialLayoutFactory extends DefaultLayoutFactory
	implements LayoutMapper
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public GenericLayout createLayout(Container rParentContainer,
									  StyleData rContainerStyle,
									  Layout    eLayout)
	{
		GenericLayout aLayout;

		switch (eLayout)
		{
// currently not working because of JS error in GwtMaterial
//			case SPLIT:
//				aLayout = new MaterialSplitPanelLayout();
//				break;

			case STACK:
				aLayout = new MaterialStackPanelLayout();
				break;

			case CARD:
				aLayout = new MaterialCardLayout();
				break;

			case GRID_ROW:
			case GRID_COLUMN:
				aLayout = new MaterialGridLayout(eLayout);
				break;

			case LIST:
				aLayout = new MaterialListLayout();
				break;

			case LIST_ITEM:
				aLayout = new MaterialListItemLayout();
				break;

			case MENU:
				aLayout = new MaterialMenuLayout();
				break;

			case HEADER:
			case CONTENT:
			case FOOTER:
				aLayout = new MaterialContentLayout(eLayout);
				break;

			default:
				aLayout =
					super.createLayout(rParentContainer,
									   rContainerStyle,
									   eLayout);
		}

		return aLayout;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public GenericLayout mapLayout(Container rContainer, GenericLayout aLayout)
	{
		if (aLayout instanceof MainViewLayout)
		{
			aLayout = new MaterialMainViewLayout();
		}

		return aLayout;
	}
}
