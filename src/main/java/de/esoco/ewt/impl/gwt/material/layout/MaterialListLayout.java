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
import gwt.material.design.client.constants.CollapsibleType;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.ListLayoutStyle;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.StyleProperties.LIST_LAYOUT_STYLE;


/********************************************************************
 * GWT Material implementation for list-style layouts.
 *
 * @author eso
 */
public class MaterialListLayout extends AbstractMaterialLayout
{
	//~ Instance fields --------------------------------------------------------

	private ListLayoutStyle eListStyle;

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addWidget(HasWidgets rContainer,
						  Widget	 rWidget,
						  StyleData  rStyleData,
						  int		 nIndex)
	{
		if (eListStyle == ListLayoutStyle.SIMPLE)
		{
			if (!(rWidget instanceof MaterialCollectionItem))
			{
				MaterialCollectionItem aItem = new MaterialCollectionItem();

				aItem.add(rWidget);
				rWidget = aItem;
			}
		}
		else if (!(rWidget instanceof MaterialCollapsibleItem))
		{
			rWidget = new MaterialCollapsibleItem(rWidget);
		}

		super.addWidget(rContainer, rWidget, rStyleData, nIndex);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	protected MaterialWidget creatMaterialLayoutContainer(
		Container rContainer,
		StyleData rContainerStyle)
	{
		eListStyle =
			rContainerStyle.getProperty(LIST_LAYOUT_STYLE,
										ListLayoutStyle.SIMPLE);

		MaterialWidget aContainerWidget;

		if (eListStyle == ListLayoutStyle.SIMPLE)
		{
			aContainerWidget = new MaterialCollection();
		}
		else
		{
			MaterialCollapsible aCollapsible = new MaterialCollapsible();

			aCollapsible.setType(CollapsibleType.POPOUT);
			aContainerWidget = aCollapsible;
		}

		return aContainerWidget;
	}
}
